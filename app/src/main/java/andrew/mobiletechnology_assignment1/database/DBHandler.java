package andrew.mobiletechnology_assignment1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Andrew on 22/02/2017.
 */

public class DBHandler extends SQLiteOpenHelper
{
    public DBHandler(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int dbVersion)
    {
        super(context, dbName, factory, dbVersion);
    }

    //Called when the database is first initialised
    @Override
    public void onCreate(SQLiteDatabase db)
    {

    }

    //Called when different (higher) dbVersion number is used
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
