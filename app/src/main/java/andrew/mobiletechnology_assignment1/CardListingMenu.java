package andrew.mobiletechnology_assignment1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import andrew.mobiletechnology_assignment1.adapters.ImageListAdapter;
import andrew.mobiletechnology_assignment1.database.DBHandler;

public class CardListingMenu extends AppCompatActivity
{
    private DBHandler dbHandler;

    ListView list;
    private static List<BusinessCard> businessCards;
    private ArrayList<BusinessCard> itemsForDisplay;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_lisiting_menu);
        businessCards = new ArrayList<>();
        this.itemsForDisplay = new ArrayList<>();
        list = (ListView)findViewById(R.id.listview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHandler = new DBHandler(this, "CardDatabaseTwo.db", null, 1);
       // SQLiteDatabase dbw = dbHandler.getWritableDatabase();

        //Insert some dummy data into the database
        //dbw.execSQL("INSERT INTO CARDS(FIRST_NAME, LAST_NAME) VALUES ('KENNY', 'G');");
        //dbw.execSQL("INSERT INTO CARDS(FIRST_NAME, LAST_NAME, WEBSITE) VALUES ('Andrew', 'Waite', 'eaglehawk.com.au');");
    }

    @Override
    public void onResume()
    {
        super.onResume();
        createListView();
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
            Intent intent = new Intent(CardListingMenu.this, LoadCaptureImageMenu.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createListView()
    {
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM CARDS" , null);

        businessCards.clear();

        //Reads all data from table CARDS and creates BusinessCard objects from that
        if(cursor.moveToFirst())
        {
            do
            {
                /**
                 * Perform a is null check on each field before adding it to the object, as a Parceable cannot serialize an object with null attributes
                 */
                BusinessCard card = new BusinessCard(0, "", "", "", "", "", "", "");
                card.setID(cursor.getInt(0));
                card.setFirstName(cursor.getString(1));
                card.setLastName(cursor.getString(2));
                card.setEmail(cursor.getString(3) == null ? "Not Found!" : cursor.getString(3));
                card.setMobileNumber(cursor.getString(4) == null ? "Not Found!" : cursor.getString(4));
                card.setAddress(cursor.getString(5) == null ? "Not Found!" : cursor.getString(5));
                card.setCompanyName(cursor.getString(6) == null ? "Not Found!" : cursor.getString(6));
                card.setWebsite(cursor.getString(7) == null ? "Not Found!" : cursor.getString(7));

                businessCards.add(card);
            }
            while(cursor.moveToNext());
        }

        if(businessCards.size() == 0)
        {
            new AlertDialog.Builder(this).setTitle("You have no business cards stored")
                                         .setMessage("Use the Add button below to add some")
                                         .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which)
                                            {
                                                // do nothing
                                            }
                                         })
                                         .setIcon(android.R.drawable.ic_dialog_alert)
                                         .show();
        }
        else
        {

            this.itemsForDisplay.clear();

            for(BusinessCard card : businessCards)
            {
                this.itemsForDisplay.add(card);
            }

            //Create an adapter for the listView and add the ArrayList to the adapter.
            //list.setAdapter(new ArrayAdapter<>(CardListingMenu.this, android.R.layout.simple_list_item_1, this.itemsForDisplay));
            list.setAdapter(new ImageListAdapter(CardListingMenu.this, android.R.layout.simple_list_item_1, this.itemsForDisplay));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
                {
                    //args2 is the listViews Selected index

                    Intent intent = new Intent(CardListingMenu.this, CardDetailsMenu.class);
                    intent.putExtra("BusinessCardFromListMenu", CardListingMenu.this.businessCards.get(arg2));
                    startActivity(intent);

                }
            });
        }
    }

    public void toView(View view)
    {
        Intent intent = new Intent(CardListingMenu.this, LoadCaptureImageMenu.class);
        startActivity(intent);
    }

}
