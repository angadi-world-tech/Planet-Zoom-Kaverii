package com.angadi.tripmanagementa.Database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.angadi.tripmanagementa.Fragments.Uploadadd_fragment;

import static android.provider.BaseColumns._ID;

public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = DbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "image.db";
    private static final int DATABASE_VERSION = 1;
    Context context;
    SQLiteDatabase db;
    ContentResolver mContentResolver;

    public final static String COLUMN_NAME = "imagename";

    public final static String TABLE_NAME = "imagetable";
    public final static String WEBSITE= Uploadadd_fragment.textwebsite;
    public final static String LOCATION=Uploadadd_fragment.textlocation;
    public final static String CONTACT=Uploadadd_fragment.textcontactnumber;
    public final static String DESCRIPTION=Uploadadd_fragment.textdescription;
    public final static String PRICE=Uploadadd_fragment.textprice;
    public final static String TIME=Uploadadd_fragment.texttime;
    public final static String CATEGORY=Uploadadd_fragment.textcategory;





    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mContentResolver = context.getContentResolver();

        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_IMAGE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " BLOB NOT NULL " + " );";



//        String SQL_CREATE_IMAGE_TABLE =     "CREATE TABLE " + TABLE_NAME + " ( " +
//                _ID + " integer primary key autoincrement ," +
//                COLUMN_NAME + " BLOB NOT NULL , " +
//                WEBSITE + " TEXT NOT NULL , " +
//                LOCATION + " TEXT NOT NULL " +
//                CONTACT + " TEXT NOT NULL" +
//                DESCRIPTION + "TEXT NOT NULL" +
//                PRICE + " TEXT NOT NULL" +
//                TIME + " TEXT NOT NULL" +
//                CATEGORY + " TEXT NOT NULL" +
//                " ) ";

        db.execSQL(SQL_CREATE_IMAGE_TABLE);

        Log.d(TAG, "Database Created Successfully" );

    }

    public void addToDb(byte[] image){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,   image);
        Log.d("CVVVVV", String.valueOf(cv));
        db.insert( TABLE_NAME, null, cv );
    }


    public long Adddetails(String web, String location , String contact, String description, String price, String time, String category){
        ContentValues values = new ContentValues();
        values.put(WEBSITE, web);
        values.put(LOCATION, location);
        values.put(CONTACT, contact);
        values.put(DESCRIPTION, description);
        values.put(PRICE, price);
        values.put(TIME, time);
        values.put(CATEGORY, category);
        return db.insert(DATABASE_NAME, null, values);
    }
//    public Cursor selectRecords() {
//        String[] cols = new String[] {WEBSITE, LOCATION,CONTACT,DESCRIPTION,PRICE,TIME,CATEGORY};
//        Cursor mCursor = db.query(true, DATABASE_NAME,cols,null
//                , null, null, null, null, null);
//        if (mCursor != null) {
//            mCursor.moveToFirst();
//        }
//        return mCursor; // iterate to get each value.
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


}


