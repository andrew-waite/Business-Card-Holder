package andrew.mobiletechnology_assingment1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CardDetailsMenu extends AppCompatActivity {

    private BusinessCard businessCardDetails;
    private ListView list;
    private List<String> itemsForDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.itemsForDisplay = new ArrayList<String>();
        this.list = (ListView)findViewById(R.id.listview);

       this.businessCardDetails = getIntent().getExtras().getParcelable("BusinessCard");

        this.createListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createListView()
    {
        this.itemsForDisplay.add("Full Name:  " + this.businessCardDetails.getFirstName() + " " + this.businessCardDetails.getLastName());
        this.itemsForDisplay.add("Email:  " + this.businessCardDetails.getEmail() == null ? "Not Found!" : this.businessCardDetails.getEmail());
        this.itemsForDisplay.add("Mobile Number:  " + this.businessCardDetails.getMobileNumber() == null ? "Not Found!" : this.businessCardDetails.getMobileNumber());
        this.itemsForDisplay.add("Work Number:  " +  this.businessCardDetails.getWorkNumber() == null ? "Not Found!" : this.businessCardDetails.getWorkNumber());
        this.itemsForDisplay.add("Website:  " + this.businessCardDetails.getWebsite() == null ? "Not Found!" : this.businessCardDetails.getWebsite());
        this.itemsForDisplay.add("Company name:  " + this.businessCardDetails.getcompanyName() == null ? "Not Found!" : this.businessCardDetails.getcompanyName());
        //Create an adapter for the listView and add the ArrayList to the adapter.
        list.setAdapter(new ArrayAdapter<>(CardDetailsMenu.this, android.R.layout.simple_list_item_1, this.itemsForDisplay));
    }
}
