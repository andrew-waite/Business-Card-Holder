package andrew.mobiletechnology_assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CardDetailsMenu extends AppCompatActivity
{

    private BusinessCard businessCardDetails = null;
    private ListView list;
    private List<String> itemsForDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.itemsForDisplay = new ArrayList<String>();
        this.list = (ListView)findViewById(R.id.listview);

        if(this.businessCardDetails == null) { this.businessCardDetails = getIntent().getParcelableExtra("BusinessCardFromListMenu"); }

    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        this.createListView();
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
            Intent intent = new Intent(CardDetailsMenu.this, LoadCaptureImageMenu.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Have to clear the list and heading before calling in onResume, otherwise doubling of data in list and heading will occur
     */
    private void createListView()
    {
        TextView headingTextView = (TextView)findViewById(R.id.textView5);
        headingTextView.setText(""); //Clear the heading before resuming the activity
        headingTextView.setText(headingTextView.getText() + " " + this.businessCardDetails.getFirstName() + " " + this.businessCardDetails.getLastName());

        this.itemsForDisplay.clear(); //Clear the list before resuming the activity

        this.itemsForDisplay.add("Full Name:  " + this.businessCardDetails.getFirstName() + " " + this.businessCardDetails.getLastName());
        this.itemsForDisplay.add("Email:  " + this.businessCardDetails.getEmail());
        this.itemsForDisplay.add("Mobile Number:  " + this.businessCardDetails.getMobileNumber());
        this.itemsForDisplay.add("Address:  " + this.businessCardDetails.getAddress());
        this.itemsForDisplay.add("Company name:  " + this.businessCardDetails.getCompanyName());
        this.itemsForDisplay.add("Website:  " + this.businessCardDetails.getWebsite());
        //Create an adapter for the listView and add the ArrayList to the adapter.
        list.setAdapter(new ArrayAdapter<>(CardDetailsMenu.this, android.R.layout.simple_list_item_1, this.itemsForDisplay));
    }

    public void toView(View view)
    {
        Intent intent = new Intent(CardDetailsMenu.this, EditDetailsMenu.class);
        intent.putExtra("BusinessCard", this.businessCardDetails);
        startActivityForResult(intent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK)
        {
            this.businessCardDetails = data.getParcelableExtra("BusinessCard");
            Log.println(Log.DEBUG, "A", "Called success");
        }
    }
}
