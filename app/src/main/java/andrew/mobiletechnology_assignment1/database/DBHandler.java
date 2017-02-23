package andrew.mobiletechnology_assignment1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

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
        SQLiteStatement statement = db.compileStatement("CREATE TABLE IF NOT EXISTS CARDS(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, FIRST_NAME TEXT NOT NULL, LAST_NAME TEXT NOT NULL, EMAIL TEXT DEFAULT NULL, MOBILE_PHONE VARCHAR(10) DEFAULT NULL, WORK_PHONE VARCHAR(10) DEFAULT NULL, COMPANY_NAME TEXT DEFAULT NULL);");
        statement.execute();
    }

    //Called when different (higher) dbVersion number is used
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
