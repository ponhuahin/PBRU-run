package ca.mogkolpon.pbrurun;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lap324-04 on 6/14/16 AD.
 */
public class MyOpenHelper extends SQLiteOpenHelper{
    public static final String database_name = "pbru_run.db";
    public static final int database_version = 1;
    public static final String create_user_table = "create table userTABLE(" +
            "_id integer primary key," +
            "Name text," +
            "User text ," +
            "Password text," +
            "Avata text," +
            "Gold text);";

    public MyOpenHelper(Context context) {
        super(context, database_name, null,database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(create_user_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
