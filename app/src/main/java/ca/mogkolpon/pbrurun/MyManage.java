package ca.mogkolpon.pbrurun;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by lap324-04 on 6/14/16 AD.
 */
public class MyManage {

    //Explicit
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    public static final String user_table = "userTABLE";
    public static final String colunm_id = "_id";
    public static final String colunm_name = "Name";
    public static final String colunm_user = "User";
    public static final String colunm_password = "Password";
    public static final String colunm_acata = "Avata";
    public static final String colunm_gold = "Gold";


    public MyManage(Context context) {
        myOpenHelper = new MyOpenHelper(context);
        sqLiteDatabase = myOpenHelper.getWritableDatabase();

    }//Constructor

    public long addNewUser(String strId,
                           String strName,
                           String strUser,
                           String strPassword,
                           String strAvata,
                           String strGold) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(colunm_id, strId);
        contentValues.put(colunm_name, strName);
        contentValues.put(colunm_user, strUser);
        contentValues.put(colunm_password, strPassword);
        contentValues.put(colunm_acata, strAvata);
        contentValues.put(colunm_gold, strGold);

        return sqLiteDatabase.insert(user_table, null, contentValues);
    }



}//Main Class
