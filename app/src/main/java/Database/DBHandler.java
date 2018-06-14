package Database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.asg.ashish.rine.Cart_Activity;

import java.util.ArrayList;

import Adapter.Cart_Adapter;
import Model.Cart_list;

public class DBHandler extends SQLiteOpenHelper {

    Cart_Adapter adapter;
    SQLiteDatabase db;

    private static final int DATABSE_VERSION=2;
    private static final String DATABSE_NAME ="cart.db";
    public static final String TABLE_NAME = "cartproducts";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_PID = "pid";
    public static final String COLUMN_IMG = "link";
    public static final String COLUMN_QUANTITY = "quantity";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABSE_NAME, factory, DATABSE_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v("CREATING TABLEEEEEEEEE", "TTTTTTTTTTTTTTTTTTTTTT");
        //String query = " CREATE TABLE " + TABLE_NAME + " ( " + COLUMN_PID + " INTEGER PRIMARY KEY, " + COLUMN_TITLE + "TEXT" + COLUMN_IMG + "TEXT" + COLUMN_QUANTITY + "TEXT"+");";
        //String query = " CREATE TABLE " + TABLE_NAME + " ( " + COLUMN_IMG + "TEXT" + COLUMN_PID + " INTEGER PRIMARY KEY, " + COLUMN_QUANTITY + " TEXT " + COLUMN_TITLE + " TEXT "+");";

        //db.execSQL(query);
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_PID + " TEXT PRIMARY KEY, " +
                COLUMN_TITLE + " TEXT NOT NULL, " +
                COLUMN_IMG + " TEXT NOT NULL, " +
                COLUMN_QUANTITY + " TEXT NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public void addProduct(Cart_list cart_list){
        ContentValues values = new ContentValues();
        values.put(COLUMN_PID,cart_list.getId());
        values.put(COLUMN_TITLE,cart_list.getTitle());
        values.put(COLUMN_IMG,cart_list.getImg());
        values.put(COLUMN_QUANTITY,cart_list.getCount());
        SQLiteDatabase db = getWritableDatabase();
        Log.v("VALUES ARE", values.toString());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteProduct(String PID){

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(" DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_PID + "=\"" + PID + "\";");
        db.close();
    }

    /*public void getfromDatabase(){
        String dbpid="", dbtitle="", dblink="", dbquantity="";
        SQLiteDatabase db = getWritableDatabase();
        String query = " SELECT * FROM " + TABLE_NAME + "WHERE 1";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("pid"))!=null){
                dbpid = c.getString(c.getColumnIndex("pid"));
                dbtitle = c.getString(c.getColumnIndex("title"));
                dblink = c.getString(c.getColumnIndex("link"));
                dbquantity = c.getString(c.getColumnIndex("quantity"));
                Cart_list arrayList = new Cart_list(dbpid,dbtitle,dblink,dbquantity);

                adapter = new Cart_Adapter(listItems, Cart_Activity.getApplicationContext());
                recyclerView.setAdapter(adapter);

            }
            c.moveToNext();
        }
    }*/
}
