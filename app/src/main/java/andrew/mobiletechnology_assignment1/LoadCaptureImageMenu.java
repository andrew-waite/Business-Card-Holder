package andrew.mobiletechnology_assignment1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.microsoft.projectoxford.vision.VisionServiceClient;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.LanguageCodes;
import com.microsoft.projectoxford.vision.contract.Line;
import com.microsoft.projectoxford.vision.contract.OCR;
import com.microsoft.projectoxford.vision.contract.Region;
import com.microsoft.projectoxford.vision.contract.Word;
import com.microsoft.projectoxford.vision.rest.VisionServiceException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import andrew.mobiletechnology_assignment1.database.DBHandler;
import andrew.mobiletechnology_assignment1.helper.StringHelper;
import andrew.mobiletechnology_assingment1.R;

public class LoadCaptureImageMenu extends AppCompatActivity
{
    private VisionServiceClient client;

    private static final int REQUEST_IMAGE_CODE = 100;


    //URI of image the user selected
    private Uri imageURI;

    //Bitmap of the image the user selected
    private Bitmap imageBitmap;

    //The string returned from the vision API
    private String apiResponse  = "";

    private DBHandler dbHandler;

    private BusinessCard businessCard;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_capture_image_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(this.client == null)
        this.client = new VisionServiceRestClient(getString(R.string.visionAPIKey));

        this.dbHandler =  new DBHandler(this, null, null, 1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openGallery(View view)
    {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image Application");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { pickIntent });

        startActivityForResult(chooserIntent, REQUEST_IMAGE_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent data)
    {
        if(requestCode == REQUEST_IMAGE_CODE && responseCode == RESULT_OK)
        {
            this.imageURI = data.getData();

            try
            {
                this.imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), this.imageURI);

                setContentView(findViewById(R.id.loading_splash_screen)); //Show splash screen
                this.apiResponse = this.process();
                setContentView(R.layout.activity_load_capture_image_menu); //Show this screen again
                this.extractData();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (VisionServiceException e)
            {
                e.printStackTrace();
            }
        }
    }

    private String process() throws VisionServiceException, IOException
    {
        Gson gson = new Gson();

        // Put the image into an input stream for detection.
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());

        OCR ocr;
        ocr = this.client.recognizeText(inputStream, LanguageCodes.AutoDetect, true);

        String result = gson.toJson(ocr);
        return result;
    }

    public void extractData()
    {
        Gson gson = new Gson();
        OCR ocr = gson.fromJson(this.apiResponse, OCR.class);

        businessCard = new BusinessCard(0, "", "", "", "", "", "", "");

        Region region = ocr.regions.get(0);

        businessCard.setFirstName(region.lines.get(0).words.get(0).text);
        businessCard.setLastName(region.lines.get(0).words.get(1).text);

        for(Word word : region.lines.get(1).words)
        {
            businessCard.setCompanyName(businessCard.getCompanyName() + " " + word.text);
        }

        businessCard.setAddress("Not Found!");
        businessCard.setMobileNumber(StringHelper.regexMatch(region, "^d+$"));
        businessCard.setEmail(StringHelper.regexMatch(region, "(?i)^([a-z0-9_.-]+)@([da-z.-]+).([a-z.]{2,6})$"));
        businessCard.setWebsite(StringHelper.regexMatch(region, "(?i)^www."));

        this.pushToDatabase();

    }

    public void pushToDatabase()
    {
        SQLiteDatabase dbw = dbHandler.getWritableDatabase();

        dbw.execSQL("INSERT INTO CARDS (FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS, MOBILE_PHONE, EMAIL, WEBSITE) VALUES ('" + this.businessCard.getFirstName()+"', '" + this.businessCard.getLastName()+"', '" + this.businessCard.getCompanyName() + "', '" + this.businessCard.getAddress()+"', '" + this.businessCard.getMobileNumber() + "', '" + this.businessCard.getEmail() + "', '" + this.businessCard.getWebsite()+"');");
        Cursor cursor = dbHandler.getReadableDatabase().rawQuery("SELECT MAX(ID) FROM CARDS", null);
        businessCard.setID(cursor.getInt(0));

        Intent intent = new Intent(LoadCaptureImageMenu.this, CardDetailsMenu.class);
        intent.putExtra("BusinessCardFromListMenu", this.businessCard);
        startActivity(intent);
    }
}
