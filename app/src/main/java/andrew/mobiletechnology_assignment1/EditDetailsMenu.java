package andrew.mobiletechnology_assignment1;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import andrew.mobiletechnology_assignment1.database.DBHandler;

public class EditDetailsMenu extends AppCompatActivity
{

    private BusinessCard businessCard;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.dbHandler = new DBHandler(this, "CardDatabaseTwo.db", null, 1);
        this.businessCard = this.getIntent().getParcelableExtra("BusinessCard");
        this.populateFields();

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

        if (id == R.id.actions_quick_access_load || id == R.id.actions_quick_access_capture)
        {
            Intent intent = new Intent(EditDetailsMenu.this, LoadCaptureImageMenu.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void populateFields()
    {
        (((EditText)findViewById(R.id.text_firstname))).setText(this.businessCard.getFirstName());
        (((EditText)findViewById(R.id.text_lastname))).setText(this.businessCard.getLastName());
        (((EditText)findViewById(R.id.text_email))).setText(this.businessCard.getEmail());
        (((EditText)findViewById(R.id.text_mobilephone))).setText(this.businessCard.getMobileNumber());
        (((EditText)findViewById(R.id.text_address))).setText(this.businessCard.getAddress());
        (((EditText)findViewById(R.id.text_companyname))).setText(this.businessCard.getAddress());
        (((EditText)findViewById(R.id.text_website))).setText(this.businessCard.getWebsite());

    }

    public void cancel(View view)
    {
        finish();
    }

    public void save(View view)
    {
        this.businessCard.setFirstName(((EditText)findViewById(R.id.text_firstname)).getText().toString());
        this.businessCard.setLastName(((EditText)findViewById(R.id.text_lastname)).getText().toString());
        this.businessCard.setEmail(((EditText)findViewById(R.id.text_email)).getText().toString());
        this.businessCard.setMobileNumber(((EditText)findViewById(R.id.text_mobilephone)).getText().toString());
        this.businessCard.setAddress(((EditText)findViewById(R.id.text_address)).getText().toString());
        this.businessCard.setCompanyName(((EditText)findViewById(R.id.text_companyname)).getText().toString());
        this.businessCard.setWebsite(((EditText)findViewById(R.id.text_website)).getText().toString());

        SQLiteDatabase dbw = this.dbHandler.getWritableDatabase();
        dbw.execSQL("UPDATE CARDS SET FIRST_NAME='" + this.businessCard.getFirstName() + "',LAST_NAME='" + this.businessCard.getLastName() + "',EMAIL='" + this.businessCard.getEmail() + "',MOBILE_PHONE='" + this.businessCard.getMobileNumber() + "',ADDRESS='" + this.businessCard.getAddress() + "',COMPANY_NAME='" + this.businessCard.getCompanyName() + "',WEBSITE='" + this.businessCard.getWebsite() + "'WHERE ID=" + this.businessCard.getId() + ";");

        /*Intent intent = new Intent(EditDetailsMenu.this, CardDetailsMenu.class);
        intent.putExtra("BusinessCard", this.businessCard);
        startActivity(intent);*/


        Intent intent = new Intent();
        intent.putExtra("BusinessCard", this.businessCard);
        setResult(RESULT_OK, intent);
        finish();
    }
}
