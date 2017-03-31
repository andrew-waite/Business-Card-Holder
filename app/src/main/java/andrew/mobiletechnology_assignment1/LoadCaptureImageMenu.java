package andrew.mobiletechnology_assignment1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.microsoft.projectoxford.vision.VisionServiceClient;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.LanguageCodes;
import com.microsoft.projectoxford.vision.contract.OCR;
import com.microsoft.projectoxford.vision.contract.Region;
import com.microsoft.projectoxford.vision.contract.Word;
import com.microsoft.projectoxford.vision.rest.VisionServiceException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import andrew.mobiletechnology_assignment1.database.DBHandler;
import andrew.mobiletechnology_assignment1.helper.ImageHelper;
import andrew.mobiletechnology_assignment1.helper.StringHelper;

public class LoadCaptureImageMenu extends AppCompatActivity
{
    private VisionServiceClient client;

    private static final int REQUEST_IMAGE_CODE = 10;
    private static final int REQUEST_CAMERA_CODE = 102;

    //URI of image the user selected
    private Uri imageURI;
    private Uri photoURI;

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
        this.client = new VisionServiceRestClient(getString(R.string.subscription_key));

        this.dbHandler =  new DBHandler(this, "CardDatabase.db", null, 1);
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
        if (id == R.id.actions_quick_access_load || id == R.id.quick_access_capture)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openGallery(View view)
    {
        /*Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image Application");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { pickIntent });

        startActivityForResult(chooserIntent, REQUEST_IMAGE_CODE);*/

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(intent, REQUEST_IMAGE_CODE);
        }


    }

    public void openCamera(View view)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null)
        {
            // Save the photo taken to a temporary file.
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try
            {
                File file = File.createTempFile("IMG_", ".jpg", storageDir);
                this.photoURI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, this.photoURI);
                startActivityForResult(intent, REQUEST_CAMERA_CODE);
            }
            catch (IOException e)
            {
               Log.println(Log.ERROR, "OpenCameraException", e.getMessage());
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int responseCode, Intent data)
    {
       // super.onActivityResult(requestCode, responseCode, data);

        if(requestCode == REQUEST_CAMERA_CODE && responseCode == RESULT_OK)
        {
            this.imageURI = this.photoURI;
            this.imageBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(this.imageURI, getContentResolver());
            Log.println(Log.DEBUG, "Image", "got the request code from the camera");
            new processRequest(this).execute();
        }
        if(requestCode == REQUEST_IMAGE_CODE && responseCode == RESULT_OK && data != null)
        {
            // this.imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), this.imageURI);
            //this.imageURI = data.getData();
            this.imageURI = data.getData();
            this.imageBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(this.imageURI, getContentResolver());
            new processRequest(this).execute();
        }
    }

    public String process() throws VisionServiceException, IOException
    {
        Gson gson = new Gson();

        // Put the image into an input stream for detection.
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        this.imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());

        OCR ocr = this.client.recognizeText(inputStream, LanguageCodes.AutoDetect, true); //Boolean is for orientation detection. True to try and fix orientation, false to leave it.

        String result = gson.toJson(ocr);
        return result;
    }

    public void extractData()
    {
        Gson gson = new Gson();
        OCR ocr = gson.fromJson(this.apiResponse, OCR.class);

        businessCard = new BusinessCard(0, "Not Found!", "Not Found!", "Not Found!", "Not Found!", "Not Found!", "Not Found!", "Not Found!");

        //TODO: FIX THIS FUCKING SHIT

            //businessCard.setMobileNumber(StringHelper.regexMatch(region, "^d+$"));
           // businessCard.setEmail(StringHelper.regexMatch(region, "(?i)^([a-z0-9_.-]+)@([da-z.-]+).([a-z.]{2,6})$"));
           // businessCard.setWebsite(StringHelper.regexMatch(region, "^(http:\\/\\/|https:\\/\\/)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$"));

       // Log.println(Log.DEBUG, "TextRecognition", "Regions size: " + ocr.regions.size());

        this.pushToDatabase();
    }

    public void pushToDatabase()
    {
        SQLiteDatabase dbw = dbHandler.getWritableDatabase();

        dbw.execSQL("INSERT INTO CARDS (FIRST_NAME, LAST_NAME, COMPANY_NAME, ADDRESS, MOBILE_PHONE, EMAIL, WEBSITE) VALUES ('" + this.businessCard.getFirstName()+"', '" + this.businessCard.getLastName()+"', '" + this.businessCard.getCompanyName() + "', '" + this.businessCard.getAddress()+"', '" + this.businessCard.getMobileNumber() + "', '" + this.businessCard.getEmail() + "', '" + this.businessCard.getWebsite()+"');");
        Cursor cursor = dbHandler.getReadableDatabase().rawQuery("SELECT MAX(ID) FROM CARDS", null);
        cursor.moveToFirst();
        businessCard.setID(cursor.getInt(0));

        Log.println(Log.DEBUG, "Database", "Pushed OCR Data to database");

        Intent intent = new Intent(LoadCaptureImageMenu.this, CardDetailsMenu.class);
        intent.putExtra("BusinessCardFromListMenu", this.businessCard);
        startActivity(intent);
    }

    private class processRequest extends AsyncTask<String, String, String>
    {
        private Exception exception = null; //Error message, if any
        private ProgressDialog progressDialog;

        public processRequest(Context context)
        {
            progressDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute()
        {
            progressDialog.setMessage("Recognising Text, Please Wait");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args)
        {
            try
            {
                return process();
            }
            catch (Exception exception)
            {
                this.exception = exception; //The error that occurred
            }

            return null;
        }

        @Override
        protected void onPostExecute(String data)
        {
            if (this.exception != null)
            {
                Log.println(Log.ERROR, "ProjectOxfordAPI", this.exception.getMessage());
                this.exception = null;
            }
            else
            {
                if(progressDialog.isShowing()) progressDialog.dismiss();
                apiResponse = data;
                extractData();
            }
        }
    }
}
