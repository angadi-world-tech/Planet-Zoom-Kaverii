package com.angadi.tripmanagementa.Activities.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.angadi.tripmanagementa.Activities.database.model.Data;
import com.angadi.tripmanagementa.Activities.database.model.Example;
import com.angadi.tripmanagementa.Activities.database.model.Likes;
import com.angadi.tripmanagementa.Activities.database.model.Products;
import com.angadi.tripmanagementa.Activities.database.model.Rating;
import com.angadi.tripmanagementa.Activities.database.model.SubCat;
import com.angadi.tripmanagementa.Activities.database.model.SubCat_Join_Tables;
import com.angadi.tripmanagementa.Activities.database.model.SubCat_Product_ID;
import com.angadi.tripmanagementa.Activities.database.model.SubCat_Product_Name;
import com.angadi.tripmanagementa.Activities.database.model.SubCat_Product_Price;
import com.angadi.tripmanagementa.Activities.database.model.SubCat_Product_Quantity;
import com.angadi.tripmanagementa.Activities.database.model.SubCat_Product_Total;
import com.angadi.tripmanagementa.Activities.database.model.SubProduct;
import com.angadi.tripmanagementa.Activities.database.model.mImageUpload;
import com.angadi.tripmanagementa.Activities.database.model.mSubCategory;
import com.angadi.tripmanagementa.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 31;

    // Database Name
    private static final String DATABASE_NAME = "db_angadi_trip";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(SubCat.CREATE_TABLE);
        db.execSQL(SubCat_Product_ID.CREATE_TABLE_PRODUCT_ID);
        db.execSQL(SubCat_Product_Name.CREATE_TABLE_PRODUCT_NAME);
        db.execSQL(SubCat_Product_Quantity.CREATE_TABLE_QUANTITY);
        db.execSQL(SubCat_Product_Price.CREATE_TABLE_PRICE);
        db.execSQL(SubCat_Product_Total.CREATE_TABLE_TOTAL);
        db.execSQL(mSubCategory.CREATE_TABLE_SUB_CATEGORY);
        db.execSQL(mImageUpload.CREATE_TABLE_IMAGE_UPLOAD);
        db.execSQL(Data.CREATE_TABLE_DATA_SB);
        db.execSQL(Example.CREATE_TABLE_EXAMPLE_SB);
        db.execSQL(Likes.CREATE_TABLE_LIKES_SB);
        db.execSQL(Products.CREATE_TABLE_PRODUCTS_SB);
        db.execSQL(Rating.CREATE_TABLE_RATING_SB);
        db.execSQL(SubProduct.CREATE_TABLE_SUB_PRODUCT_SB);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + SubCat.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SubCat_Product_ID.TABLE_NAME_PRODUCT_ID);
        db.execSQL("DROP TABLE IF EXISTS " + SubCat_Product_Name.TABLE_NAME_PRODUCT_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SubCat_Product_Quantity.TABLE_NAME_PRODUCT_QUANTITY);
        db.execSQL("DROP TABLE IF EXISTS " + SubCat_Product_Price.TABLE_NAME_PRODUCT_PRICE);
        db.execSQL("DROP TABLE IF EXISTS " + SubCat_Product_Total.TABLE_NAME_PRODUCT_TOTAL);
        db.execSQL("DROP TABLE IF EXISTS " + mSubCategory.TABLE_NAME_SUB_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + mImageUpload.TABLE_NAME_IMAGE_UPLOAD);
        db.execSQL("DROP TABLE IF EXISTS " + Data.TABLE_NAME_DATA_SB);
        db.execSQL("DROP TABLE IF EXISTS " + Example.TABLE_NAME_EXAMPLE_SB);
        db.execSQL("DROP TABLE IF EXISTS " + Likes.TABLE_NAME_LIKES_SB);
        db.execSQL("DROP TABLE IF EXISTS " + Products.TABLE_NAME_PRODUCTS_SB);
        db.execSQL("DROP TABLE IF EXISTS " + Rating.TABLE_NAME_RATING_SB);
        db.execSQL("DROP TABLE IF EXISTS " + SubProduct.TABLE_NAME_SUB_PRODUCT_SB);

        // Create tables again
        onCreate(db);
    }

    public long insertData_SB(Integer current_page, Integer last_page, String next_page_url) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Data.COLUMN_CURRENT_PAGE, current_page);
        values.put(Data.COLUMN_LAST_PAGE, last_page);
        values.put(Data.COLUMN_NEXT_PAGE_URL, next_page_url);

        // insert row
        long id = db.insert(Data.TABLE_NAME_DATA_SB, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public long insertExample_SB(Integer success, Integer status) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Example.COLUMN_SUCCESS, success);
        values.put(Example.COLUMN_STATUS, status);

        // insert row
        long id = db.insert(Example.TABLE_NAME_EXAMPLE_SB, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public long insertLikes_SB(Integer product_array_count, Integer product_array_count_now, Integer positive, Integer negative) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Likes.COLUMN_PRODUCT_ARRAY_COUNT, product_array_count);
        values.put(Likes.COLUMN_PRODUCT_ARRAY_COUNT_NOW, product_array_count_now);
        values.put(Likes.COLUMN_POSITIVE, positive);
        values.put(Likes.COLUMN_NEGATIVE, negative);

        // insert row
        long id = db.insert(Likes.TABLE_NAME_LIKES_SB, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public long insertRating_SB(Integer product_array_count, Integer product_array_count_now, Integer count, Integer average, String value) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Rating.COLUMN_PRODUCT_ARRAY_COUNT, product_array_count);
        values.put(Rating.COLUMN_PRODUCT_ARRAY_COUNT_NOW, product_array_count_now);
        values.put(Rating.COLUMN_COUNT, count);
        values.put(Rating.COLUMN_AVERAGE, average);
        values.put(Rating.COLUMN_VALUE, value);

        // insert row
        long id = db.insert(Rating.TABLE_NAME_RATING_SB, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public long insertSubProduct_SB(Integer product_array_count, Integer product_array_count_now, Integer sub_product_array_count, Integer sub_product_array_count_now, Integer sp_id, String name, String price, String photo) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(SubProduct.COLUMN_PRODUCT_ARRAY_COUNT, product_array_count);
        values.put(SubProduct.COLUMN_PRODUCT_ARRAY_COUNT_NOW, product_array_count_now);
        values.put(SubProduct.COLUMN_SUB_PRODUCT_ARRAY_COUNT, sub_product_array_count);
        values.put(SubProduct.COLUMN_SUB_PRODUCT_ARRAY_COUNT_NOW, sub_product_array_count_now);
        values.put(SubProduct.COLUMN_SUB_PRODUCT_ID, sp_id);
        values.put(SubProduct.COLUMN_SUB_PRODUCT_NAME, name);
        values.put(SubProduct.COLUMN_SUB_PRODUCT_PRICE, price);
        values.put(SubProduct.COLUMN_SUB_PRODUCT_PHOTO, photo);

        // insert row
        long id = db.insert(SubProduct.TABLE_NAME_SUB_PRODUCT_SB, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public long insertProducts_SB(Integer product_array_count, Integer product_array_count_now, Integer product_id, Integer user_id,
                                  Integer type, String name, String plus_code, double latitude, double longitude, String location,
                                  String photo, String isd_code, String phone, String std_code, String landine, String email,
                                  String website, String description, String price, String validity_start, String validity_end,
                                  String quality, Integer quantity, String offer, String attachment, String image, String created_at,
                                  String gst, String hashed_id, String hashed_id_url, String favorite) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Products.COLUMN_PRODUCT_ARRAY_COUNT, product_array_count);
        values.put(Products.COLUMN_PRODUCT_ARRAY_COUNT_NOW, product_array_count_now);
        values.put(Products.COLUMN_PRODUCT_ID, product_id);
        values.put(Products.COLUMN_USER_ID, user_id);
        values.put(Products.COLUMN_TYPE, type);
        values.put(Products.COLUMN_NAME, name);
        values.put(Products.COLUMN_PLUS_CODE, plus_code);
        values.put(Products.COLUMN_LATITUDE, latitude);
        values.put(Products.COLUMN_LONGITUDE, longitude);
        values.put(Products.COLUMN_LOCATION, location);
        values.put(Products.COLUMN_PHOTO, photo);
        values.put(Products.COLUMN_ISD_CODE, isd_code);
        values.put(Products.COLUMN_PHONE, phone);
        values.put(Products.COLUMN_STD_CODE, std_code);
        values.put(Products.COLUMN_LANDLINE, landine);
        values.put(Products.COLUMN_EMAIL, email);
        values.put(Products.COLUMN_WEBSITE, website);
        values.put(Products.COLUMN_DESCRIPTION, description);
        values.put(Products.COLUMN_PRICE, price);
        values.put(Products.COLUMN_VALIDITY_START, validity_start);
        values.put(Products.COLUMN_VALIDITY_END, validity_end);
        values.put(Products.COLUMN_QUALITY, quality);
        values.put(Products.COLUMN_QUANTITY, quantity);
        values.put(Products.COLUMN_OFFER, offer);
        values.put(Products.COLUMN_ATTACHMENT, attachment);
        values.put(Products.COLUMN_IMAGE, image);
        values.put(Products.COLUMN_CREATED_AT, created_at);
        values.put(Products.COLUMN_GST, gst);
        values.put(Products.COLUMN_HASHED_ID, hashed_id);
        values.put(Products.COLUMN_HASHED_ID_URL, hashed_id_url);
        values.put(Products.COLUMN_FAVORITE, favorite);

        // insert row
        long id = db.insert(Products.TABLE_NAME_PRODUCTS_SB, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public long insertProduct_ID(String product_id) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(SubCat_Product_ID.COLUMN_PRODUCT_ID, product_id);

        // insert row
        long id = db.insert(SubCat_Product_ID.TABLE_NAME_PRODUCT_ID, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public long insertProduct_Name(String product_name) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(SubCat_Product_Name.COLUMN_PRODUCT_NAME, product_name);

        // insert row
        long id = db.insert(SubCat_Product_Name.TABLE_NAME_PRODUCT_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public long insertQuantity(String quantity) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(SubCat_Product_Quantity.COLUMN_QUANTITY, quantity);

        // insert row
        long id = db.insert(SubCat_Product_Quantity.TABLE_NAME_PRODUCT_QUANTITY, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public long insertPrice(String price) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(SubCat_Product_Price.COLUMN_PRICE, price);

        // insert row
        long id = db.insert(SubCat_Product_Price.TABLE_NAME_PRODUCT_PRICE, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public long insertTotal(String total) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(SubCat_Product_Total.COLUMN_TOTAL, total);

        // insert row
        long id = db.insert(SubCat_Product_Total.TABLE_NAME_PRODUCT_TOTAL, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public long insertSubCat(String product_id, String product_name, String quantity, String price, String total) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(SubCat.COLUMN_PRODUCT_ID, product_id);
        values.put(SubCat.COLUMN_PRODUCT_NAME, product_name);
        values.put(SubCat.COLUMN_QUANTITY, quantity);
        values.put(SubCat.COLUMN_PRICE, price);
        values.put(SubCat.COLUMN_TOTAL, total);

        // insert row
        long id = db.insert(SubCat.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public long insertSubCategory(String product_image, String product_name, String price) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(mSubCategory.COLUMN_PRODUCT_IMAGE, product_image);
        values.put(mSubCategory.COLUMN_PRODUCT_NAME, product_name);
        values.put(mSubCategory.COLUMN_PRODUCT_PRICE, price);

        // insert row
        long id = db.insert(mSubCategory.TABLE_NAME_SUB_CATEGORY, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public long insertImageUpload(String image_url, String image_uri, String image_type) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(mImageUpload.COLUMN_IMAGE_PATH, image_url);
        values.put(mImageUpload.COLUMN_IMAGE_URI, image_uri);
        values.put(mImageUpload.COLUMN_IMAGE_TYPE, image_type);

        // insert row
        long id = db.insert(mImageUpload.TABLE_NAME_IMAGE_UPLOAD, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public SubCat_Product_ID getProduct_ID(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SubCat_Product_ID.TABLE_NAME_PRODUCT_ID,
                new String[]{SubCat_Product_ID.COLUMN_ID, SubCat_Product_ID.COLUMN_PRODUCT_ID, SubCat_Product_ID.COLUMN_TIMESTAMP},
                SubCat_Product_ID.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare subCat object
        SubCat_Product_ID subCatProductId = new SubCat_Product_ID(
                cursor.getInt(cursor.getColumnIndex(SubCat_Product_ID.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(SubCat_Product_ID.COLUMN_PRODUCT_ID)),
                cursor.getString(cursor.getColumnIndex(SubCat_Product_ID.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return subCatProductId;
    }

    public SubCat_Product_Name getProduct_Name(long id) {

        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SubCat_Product_Name.TABLE_NAME_PRODUCT_NAME,
                new String[]{SubCat_Product_Name.COLUMN_ID, SubCat_Product_Name.COLUMN_PRODUCT_NAME, SubCat_Product_Name.COLUMN_TIMESTAMP},
                SubCat_Product_Name.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare subCat object
        SubCat_Product_Name subCatProductName = new SubCat_Product_Name(
                cursor.getInt(cursor.getColumnIndex(SubCat_Product_Name.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(SubCat_Product_Name.COLUMN_PRODUCT_NAME)),
                cursor.getString(cursor.getColumnIndex(SubCat_Product_Name.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return subCatProductName;
    }

    public SubCat_Product_Quantity getQuantity(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SubCat_Product_Quantity.TABLE_NAME_PRODUCT_QUANTITY,
                new String[]{SubCat_Product_Quantity.COLUMN_ID, SubCat_Product_Quantity.COLUMN_QUANTITY, SubCat_Product_Quantity.COLUMN_TIMESTAMP},
                SubCat_Product_Quantity.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare subCat object
        SubCat_Product_Quantity subCatProductQuantity = new SubCat_Product_Quantity(
                cursor.getInt(cursor.getColumnIndex(SubCat_Product_Quantity.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(SubCat_Product_Quantity.COLUMN_QUANTITY)),
                cursor.getString(cursor.getColumnIndex(SubCat_Product_Quantity.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return subCatProductQuantity;
    }

    public SubCat_Product_Price getPrice(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SubCat_Product_Price.TABLE_NAME_PRODUCT_PRICE,
                new String[]{SubCat_Product_Price.COLUMN_ID, SubCat_Product_Price.COLUMN_PRICE, SubCat_Product_Price.COLUMN_TIMESTAMP},
                SubCat_Product_Price.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare subCat object
        SubCat_Product_Price subCatProductPrice = new SubCat_Product_Price(
                cursor.getInt(cursor.getColumnIndex(SubCat_Product_Price.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(SubCat_Product_Price.COLUMN_PRICE)),
                cursor.getString(cursor.getColumnIndex(SubCat_Product_Price.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return subCatProductPrice;
    }

    public SubCat_Product_Total getTotal(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SubCat_Product_Total.TABLE_NAME_PRODUCT_TOTAL,
                new String[]{SubCat_Product_Total.COLUMN_ID, SubCat_Product_Total.COLUMN_TOTAL, SubCat_Product_Total.COLUMN_TIMESTAMP},
                SubCat_Product_Total.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare subCat object
        SubCat_Product_Total subCatProductTotal = new SubCat_Product_Total(
                cursor.getInt(cursor.getColumnIndex(SubCat_Product_Total.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(SubCat_Product_Total.COLUMN_TOTAL)),
                cursor.getString(cursor.getColumnIndex(SubCat_Product_Total.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return subCatProductTotal;
    }

    public mImageUpload getImageUploadID(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(mImageUpload.TABLE_NAME_IMAGE_UPLOAD,
                new String[]{mImageUpload.COLUMN_ID, mImageUpload.COLUMN_IMAGE_PATH, mImageUpload.COLUMN_IMAGE_URI, mImageUpload.COLUMN_IMAGE_TYPE, mImageUpload.COLUMN_TIME_DATE},
                mImageUpload.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare subCat object
        mImageUpload mImageUploads = new mImageUpload(
                cursor.getInt(cursor.getColumnIndex(mImageUpload.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(mImageUpload.COLUMN_IMAGE_PATH)),
                cursor.getString(cursor.getColumnIndex(mImageUpload.COLUMN_IMAGE_URI)),
                cursor.getString(cursor.getColumnIndex(mImageUpload.COLUMN_IMAGE_TYPE)),
                cursor.getString(cursor.getColumnIndex(mImageUpload.COLUMN_TIME_DATE)));

        // close the db connection
        cursor.close();

        return mImageUploads;
    }

    @SuppressLint("LongLogTag")
    public boolean getImageUploadcID(String product_type) {
        SQLiteDatabase db = getWritableDatabase();
        String selectString = "SELECT * FROM " + mImageUpload.TABLE_NAME_IMAGE_UPLOAD + " WHERE " + mImageUpload.COLUMN_IMAGE_TYPE + " =?";

        // Add the String you are searching by here.
        // Put it in an array to avoid an unrecognized token error
        Cursor cursor = db.rawQuery(selectString, new String[] {product_type});

        boolean hasObject = false;
        if(cursor.moveToFirst()){
            hasObject = true;

            //region if you had multiple records to check for, use this region.

            int count = 0;
            while(cursor.moveToNext()){
                count++;
            }
            //here, count is records found
            Log.d("CheckIsDataAlreadyInDBorNot", String.format("%d records found", count));

            //endregion

        }

        cursor.close();          // Dont forget to close your cursor
        db.close();              //AND your Database!
        return hasObject;
    }

    public SubCat getSubCat(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SubCat.TABLE_NAME,
                new String[]{SubCat.COLUMN_ID, SubCat.COLUMN_PRODUCT_ID, SubCat.COLUMN_PRODUCT_NAME, SubCat.COLUMN_QUANTITY, SubCat.COLUMN_PRICE, SubCat.COLUMN_TOTAL, SubCat.COLUMN_TIMESTAMP},
                SubCat.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare subCat object
        SubCat subCat = new SubCat(
                cursor.getInt(cursor.getColumnIndex(SubCat.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(SubCat.COLUMN_PRODUCT_ID)),
                cursor.getString(cursor.getColumnIndex(SubCat.COLUMN_PRODUCT_NAME)),
                cursor.getString(cursor.getColumnIndex(SubCat.COLUMN_QUANTITY)),
                cursor.getString(cursor.getColumnIndex(SubCat.COLUMN_PRICE)),
                cursor.getString(cursor.getColumnIndex(SubCat.COLUMN_TOTAL)),
                cursor.getString(cursor.getColumnIndex(SubCat.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return subCat;
    }

    public Data getData_SB(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Data.TABLE_NAME_DATA_SB,
                new String[]{Data.COLUMN_ID, Data.COLUMN_CURRENT_PAGE, Data.COLUMN_LAST_PAGE, Data.COLUMN_NEXT_PAGE_URL, Data.COLUMN_TIME_DATE},
                Data.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare datas object
        Data datas = new Data(
                cursor.getInt(cursor.getColumnIndex(Data.COLUMN_ID)),
                cursor.getInt(cursor.getColumnIndex(Data.COLUMN_CURRENT_PAGE)),
                cursor.getInt(cursor.getColumnIndex(Data.COLUMN_LAST_PAGE)),
                cursor.getString(cursor.getColumnIndex(Data.COLUMN_NEXT_PAGE_URL)),
                cursor.getString(cursor.getColumnIndex(Data.COLUMN_TIME_DATE)));

        // close the db connection
        cursor.close();

        return datas;
    }

    public Example getExample_SB(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Example.TABLE_NAME_EXAMPLE_SB,
                new String[]{Example.COLUMN_ID, Example.COLUMN_SUCCESS, Example.COLUMN_STATUS, Example.COLUMN_TIME_DATE},
                Example.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare datas object
        Example examples = new Example(
                cursor.getInt(cursor.getColumnIndex(Example.COLUMN_ID)),
                cursor.getInt(cursor.getColumnIndex(Example.COLUMN_SUCCESS)),
                cursor.getInt(cursor.getColumnIndex(Example.COLUMN_STATUS)),
                cursor.getString(cursor.getColumnIndex(Example.COLUMN_TIME_DATE)));

        // close the db connection
        cursor.close();

        return examples;
    }

    public Likes getLikes_SB(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Likes.TABLE_NAME_LIKES_SB,
                new String[]{Likes.COLUMN_ID, Likes.COLUMN_PRODUCT_ARRAY_COUNT, Likes.COLUMN_PRODUCT_ARRAY_COUNT_NOW, Likes.COLUMN_POSITIVE, Likes.COLUMN_NEGATIVE, Likes.COLUMN_TIME_DATE},
                Likes.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare datas object
        Likes like = new Likes(
                cursor.getInt(cursor.getColumnIndex(Likes.COLUMN_ID)),
                cursor.getInt(cursor.getColumnIndex(Likes.COLUMN_PRODUCT_ARRAY_COUNT)),
                cursor.getInt(cursor.getColumnIndex(Likes.COLUMN_PRODUCT_ARRAY_COUNT_NOW)),
                cursor.getInt(cursor.getColumnIndex(Likes.COLUMN_POSITIVE)),
                cursor.getInt(cursor.getColumnIndex(Likes.COLUMN_NEGATIVE)),
                cursor.getString(cursor.getColumnIndex(Likes.COLUMN_TIME_DATE)));

        // close the db connection
        cursor.close();

        return like;
    }

    public Rating getRating_SB(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Rating.TABLE_NAME_RATING_SB,
                new String[]{Rating.COLUMN_ID, Rating.COLUMN_PRODUCT_ARRAY_COUNT, Rating.COLUMN_PRODUCT_ARRAY_COUNT_NOW, Rating.COLUMN_COUNT, Rating.COLUMN_AVERAGE, Rating.COLUMN_VALUE, Rating.COLUMN_TIME_DATE},
                Rating.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare datas object
        Rating ratings = new Rating(
                cursor.getInt(cursor.getColumnIndex(Rating.COLUMN_ID)),
                cursor.getInt(cursor.getColumnIndex(Rating.COLUMN_PRODUCT_ARRAY_COUNT)),
                cursor.getInt(cursor.getColumnIndex(Rating.COLUMN_PRODUCT_ARRAY_COUNT_NOW)),
                cursor.getInt(cursor.getColumnIndex(Rating.COLUMN_COUNT)),
                cursor.getInt(cursor.getColumnIndex(Rating.COLUMN_AVERAGE)),
                cursor.getString(cursor.getColumnIndex(Rating.COLUMN_VALUE)),
                cursor.getString(cursor.getColumnIndex(Rating.COLUMN_TIME_DATE)));

        // close the db connection
        cursor.close();

        return ratings;
    }

    public SubProduct getSubProduct_SB(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SubProduct.TABLE_NAME_SUB_PRODUCT_SB,
                new String[]{SubProduct.COLUMN_ID, SubProduct.COLUMN_PRODUCT_ARRAY_COUNT, SubProduct.COLUMN_PRODUCT_ARRAY_COUNT_NOW, SubProduct.COLUMN_SUB_PRODUCT_ARRAY_COUNT, SubProduct.COLUMN_SUB_PRODUCT_ARRAY_COUNT_NOW, SubProduct.COLUMN_SUB_PRODUCT_ID, SubProduct.COLUMN_SUB_PRODUCT_NAME, SubProduct.COLUMN_SUB_PRODUCT_PRICE, SubProduct.COLUMN_SUB_PRODUCT_PHOTO, SubProduct.COLUMN_TIME_DATE},
                SubProduct.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare datas object
        SubProduct subProducts = new SubProduct(
                cursor.getInt(cursor.getColumnIndex(SubProduct.COLUMN_ID)),
                cursor.getInt(cursor.getColumnIndex(SubProduct.COLUMN_PRODUCT_ARRAY_COUNT)),
                cursor.getInt(cursor.getColumnIndex(SubProduct.COLUMN_PRODUCT_ARRAY_COUNT_NOW)),
                cursor.getInt(cursor.getColumnIndex(SubProduct.COLUMN_SUB_PRODUCT_ARRAY_COUNT)),
                cursor.getInt(cursor.getColumnIndex(SubProduct.COLUMN_SUB_PRODUCT_ARRAY_COUNT_NOW)),
                cursor.getInt(cursor.getColumnIndex(SubProduct.COLUMN_SUB_PRODUCT_ID)),
                cursor.getString(cursor.getColumnIndex(SubProduct.COLUMN_SUB_PRODUCT_NAME)),
                cursor.getString(cursor.getColumnIndex(SubProduct.COLUMN_SUB_PRODUCT_PRICE)),
                cursor.getString(cursor.getColumnIndex(SubProduct.COLUMN_SUB_PRODUCT_PHOTO)),
                cursor.getString(cursor.getColumnIndex(SubProduct.COLUMN_TIME_DATE)));

        // close the db connection
        cursor.close();

        return subProducts;
    }

    public SubProduct getSubProduct_SB_NL_PACN(int id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SubProduct.TABLE_NAME_SUB_PRODUCT_SB,
                new String[]{SubProduct.COLUMN_ID, SubProduct.COLUMN_PRODUCT_ARRAY_COUNT, SubProduct.COLUMN_PRODUCT_ARRAY_COUNT_NOW, SubProduct.COLUMN_SUB_PRODUCT_ARRAY_COUNT, SubProduct.COLUMN_SUB_PRODUCT_ARRAY_COUNT_NOW, SubProduct.COLUMN_SUB_PRODUCT_ID, SubProduct.COLUMN_SUB_PRODUCT_NAME, SubProduct.COLUMN_SUB_PRODUCT_PRICE, SubProduct.COLUMN_SUB_PRODUCT_PHOTO, SubProduct.COLUMN_TIME_DATE},
                SubProduct.COLUMN_PRODUCT_ARRAY_COUNT_NOW + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare datas object
        SubProduct subProducts = new SubProduct(
                cursor.getInt(cursor.getColumnIndex(SubProduct.COLUMN_ID)),
                cursor.getInt(cursor.getColumnIndex(SubProduct.COLUMN_PRODUCT_ARRAY_COUNT)),
                cursor.getInt(cursor.getColumnIndex(SubProduct.COLUMN_PRODUCT_ARRAY_COUNT_NOW)),
                cursor.getInt(cursor.getColumnIndex(SubProduct.COLUMN_SUB_PRODUCT_ARRAY_COUNT)),
                cursor.getInt(cursor.getColumnIndex(SubProduct.COLUMN_SUB_PRODUCT_ARRAY_COUNT_NOW)),
                cursor.getInt(cursor.getColumnIndex(SubProduct.COLUMN_SUB_PRODUCT_ID)),
                cursor.getString(cursor.getColumnIndex(SubProduct.COLUMN_SUB_PRODUCT_NAME)),
                cursor.getString(cursor.getColumnIndex(SubProduct.COLUMN_SUB_PRODUCT_PRICE)),
                cursor.getString(cursor.getColumnIndex(SubProduct.COLUMN_SUB_PRODUCT_PHOTO)),
                cursor.getString(cursor.getColumnIndex(SubProduct.COLUMN_TIME_DATE)));

        // close the db connection
        cursor.close();

        return subProducts;
    }

    public List<SubProduct> getSubProduct_SB_PACN(int id) {
        List<SubProduct> sb_Pacn_SubProducts = new ArrayList<>();
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SubProduct.TABLE_NAME_SUB_PRODUCT_SB,
                new String[]{SubProduct.COLUMN_ID, SubProduct.COLUMN_PRODUCT_ARRAY_COUNT, SubProduct.COLUMN_PRODUCT_ARRAY_COUNT_NOW, SubProduct.COLUMN_SUB_PRODUCT_ARRAY_COUNT, SubProduct.COLUMN_SUB_PRODUCT_ARRAY_COUNT_NOW, SubProduct.COLUMN_SUB_PRODUCT_ID, SubProduct.COLUMN_SUB_PRODUCT_NAME, SubProduct.COLUMN_SUB_PRODUCT_PRICE, SubProduct.COLUMN_SUB_PRODUCT_PHOTO, SubProduct.COLUMN_TIME_DATE},
                SubProduct.COLUMN_PRODUCT_ARRAY_COUNT_NOW + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare datas object
        do {
            SubProduct subProducts = new SubProduct();
            subProducts.setId(cursor.getInt(cursor.getColumnIndex(SubProduct.COLUMN_ID)));
            subProducts.setProduct_array_count(cursor.getInt(cursor.getColumnIndex(SubProduct.COLUMN_PRODUCT_ARRAY_COUNT)));
            subProducts.setProduct_array_count_now(cursor.getInt(cursor.getColumnIndex(SubProduct.COLUMN_PRODUCT_ARRAY_COUNT_NOW)));
            subProducts.setSub_product_array_count(cursor.getInt(cursor.getColumnIndex(SubProduct.COLUMN_SUB_PRODUCT_ARRAY_COUNT)));
            subProducts.setSub_product_array_count_now(cursor.getInt(cursor.getColumnIndex(SubProduct.COLUMN_SUB_PRODUCT_ARRAY_COUNT_NOW)));
            subProducts.setId(cursor.getInt(cursor.getColumnIndex(SubProduct.COLUMN_SUB_PRODUCT_ID)));
            subProducts.setName(cursor.getString(cursor.getColumnIndex(SubProduct.COLUMN_SUB_PRODUCT_NAME)));
            subProducts.setPrice(cursor.getString(cursor.getColumnIndex(SubProduct.COLUMN_SUB_PRODUCT_PRICE)));
            subProducts.setPhoto(cursor.getString(cursor.getColumnIndex(SubProduct.COLUMN_SUB_PRODUCT_PHOTO)));
            subProducts.setTime_date(cursor.getString(cursor.getColumnIndex(SubProduct.COLUMN_TIME_DATE)));

            sb_Pacn_SubProducts.add(subProducts);
        } while (cursor.moveToNext());

        // close the db connection
        cursor.close();

        return sb_Pacn_SubProducts;
    }

    public Products getProducts_SB(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Products.TABLE_NAME_PRODUCTS_SB,
                new String[]{Products.COLUMN_ID, Products.COLUMN_PRODUCT_ARRAY_COUNT, Products.COLUMN_PRODUCT_ARRAY_COUNT_NOW,
                        Products.COLUMN_PRODUCT_ID, Products.COLUMN_USER_ID, Products.COLUMN_TYPE, Products.COLUMN_NAME,
                        Products.COLUMN_PLUS_CODE, Products.COLUMN_LATITUDE, Products.COLUMN_LONGITUDE, Products.COLUMN_LOCATION,
                        Products.COLUMN_PHOTO, Products.COLUMN_ISD_CODE, Products.COLUMN_PHONE, Products.COLUMN_STD_CODE,
                        Products.COLUMN_LANDLINE, Products.COLUMN_EMAIL, Products.COLUMN_WEBSITE, Products.COLUMN_DESCRIPTION,
                        Products.COLUMN_PRICE, Products.COLUMN_VALIDITY_START, Products.COLUMN_VALIDITY_END, Products.COLUMN_QUALITY,
                        Products.COLUMN_QUANTITY, Products.COLUMN_OFFER, Products.COLUMN_ATTACHMENT, Products.COLUMN_IMAGE,
                        Products.COLUMN_CREATED_AT, Products.COLUMN_GST, Products.COLUMN_HASHED_ID, Products.COLUMN_HASHED_ID_URL,
                        Products.COLUMN_FAVORITE, Products.COLUMN_TIME_DATE},
                Products.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare datas object
        Products products = new Products(
                cursor.getInt(cursor.getColumnIndex(Products.COLUMN_ID)),
                cursor.getInt(cursor.getColumnIndex(Products.COLUMN_PRODUCT_ARRAY_COUNT)),
                cursor.getInt(cursor.getColumnIndex(Products.COLUMN_PRODUCT_ARRAY_COUNT_NOW)),
                cursor.getInt(cursor.getColumnIndex(Products.COLUMN_PRODUCT_ID)),
                cursor.getInt(cursor.getColumnIndex(Products.COLUMN_USER_ID)),
                cursor.getInt(cursor.getColumnIndex(Products.COLUMN_TYPE)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_PLUS_CODE)),
                cursor.getDouble(cursor.getColumnIndex(Products.COLUMN_LATITUDE)),
                cursor.getDouble(cursor.getColumnIndex(Products.COLUMN_LONGITUDE)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_LOCATION)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_PHOTO)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_ISD_CODE)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_PHONE)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_STD_CODE)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_LANDLINE)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_EMAIL)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_WEBSITE)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_PRICE)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_VALIDITY_START)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_VALIDITY_END)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_QUALITY)),
                cursor.getInt(cursor.getColumnIndex(Products.COLUMN_QUANTITY)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_OFFER)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_ATTACHMENT)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_IMAGE)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_CREATED_AT)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_GST)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_HASHED_ID)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_HASHED_ID_URL)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_FAVORITE)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_TIME_DATE)));

        // close the db connection
        cursor.close();

        return products;
    }

    public Products getProducts_NL_SB(int id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Products.TABLE_NAME_PRODUCTS_SB,
                new String[]{Products.COLUMN_ID, Products.COLUMN_PRODUCT_ARRAY_COUNT, Products.COLUMN_PRODUCT_ARRAY_COUNT_NOW,
                        Products.COLUMN_PRODUCT_ID, Products.COLUMN_USER_ID, Products.COLUMN_TYPE, Products.COLUMN_NAME,
                        Products.COLUMN_PLUS_CODE, Products.COLUMN_LATITUDE, Products.COLUMN_LONGITUDE, Products.COLUMN_LOCATION,
                        Products.COLUMN_PHOTO, Products.COLUMN_ISD_CODE, Products.COLUMN_PHONE, Products.COLUMN_STD_CODE,
                        Products.COLUMN_LANDLINE, Products.COLUMN_EMAIL, Products.COLUMN_WEBSITE, Products.COLUMN_DESCRIPTION,
                        Products.COLUMN_PRICE, Products.COLUMN_VALIDITY_START, Products.COLUMN_VALIDITY_END, Products.COLUMN_QUALITY,
                        Products.COLUMN_QUANTITY, Products.COLUMN_OFFER, Products.COLUMN_ATTACHMENT, Products.COLUMN_IMAGE,
                        Products.COLUMN_CREATED_AT, Products.COLUMN_GST, Products.COLUMN_HASHED_ID, Products.COLUMN_HASHED_ID_URL,
                        Products.COLUMN_FAVORITE, Products.COLUMN_TIME_DATE},
                Products.COLUMN_PRODUCT_ARRAY_COUNT_NOW + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare datas object
        Products products = new Products(
                cursor.getInt(cursor.getColumnIndex(Products.COLUMN_ID)),
                cursor.getInt(cursor.getColumnIndex(Products.COLUMN_PRODUCT_ARRAY_COUNT)),
                cursor.getInt(cursor.getColumnIndex(Products.COLUMN_PRODUCT_ARRAY_COUNT_NOW)),
                cursor.getInt(cursor.getColumnIndex(Products.COLUMN_PRODUCT_ID)),
                cursor.getInt(cursor.getColumnIndex(Products.COLUMN_USER_ID)),
                cursor.getInt(cursor.getColumnIndex(Products.COLUMN_TYPE)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_PLUS_CODE)),
                cursor.getDouble(cursor.getColumnIndex(Products.COLUMN_LATITUDE)),
                cursor.getDouble(cursor.getColumnIndex(Products.COLUMN_LONGITUDE)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_LOCATION)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_PHOTO)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_ISD_CODE)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_PHONE)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_STD_CODE)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_LANDLINE)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_EMAIL)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_WEBSITE)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_PRICE)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_VALIDITY_START)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_VALIDITY_END)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_QUALITY)),
                cursor.getInt(cursor.getColumnIndex(Products.COLUMN_QUANTITY)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_OFFER)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_ATTACHMENT)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_IMAGE)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_CREATED_AT)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_GST)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_HASHED_ID)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_HASHED_ID_URL)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_FAVORITE)),
                cursor.getString(cursor.getColumnIndex(Products.COLUMN_TIME_DATE)));

        // close the db connection
        cursor.close();

        return products;
    }

    public List<Products> getProducts_SB_PACN(int id) {
        List<Products> sb_Pacn_Products = new ArrayList<>();
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Products.TABLE_NAME_PRODUCTS_SB,
                new String[]{Products.COLUMN_ID, Products.COLUMN_PRODUCT_ARRAY_COUNT, Products.COLUMN_PRODUCT_ARRAY_COUNT_NOW,
                        Products.COLUMN_PRODUCT_ID, Products.COLUMN_USER_ID, Products.COLUMN_TYPE, Products.COLUMN_NAME,
                        Products.COLUMN_PLUS_CODE, Products.COLUMN_LATITUDE, Products.COLUMN_LONGITUDE, Products.COLUMN_LOCATION,
                        Products.COLUMN_PHOTO, Products.COLUMN_ISD_CODE, Products.COLUMN_PHONE, Products.COLUMN_STD_CODE,
                        Products.COLUMN_LANDLINE, Products.COLUMN_EMAIL, Products.COLUMN_WEBSITE, Products.COLUMN_DESCRIPTION,
                        Products.COLUMN_PRICE, Products.COLUMN_VALIDITY_START, Products.COLUMN_VALIDITY_END, Products.COLUMN_QUALITY,
                        Products.COLUMN_QUANTITY, Products.COLUMN_OFFER, Products.COLUMN_ATTACHMENT, Products.COLUMN_IMAGE,
                        Products.COLUMN_CREATED_AT, Products.COLUMN_GST, Products.COLUMN_HASHED_ID, Products.COLUMN_HASHED_ID_URL,
                        Products.COLUMN_FAVORITE, Products.COLUMN_TIME_DATE},
                Products.COLUMN_PRODUCT_ARRAY_COUNT_NOW + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();
            // looping through all rows and adding to list
            do {
                // prepare datas object
                Products product = new Products();
                product.setId(cursor.getInt(cursor.getColumnIndex(Products.COLUMN_ID)));
                product.setProduct_array_count(cursor.getInt(cursor.getColumnIndex(Products.COLUMN_PRODUCT_ARRAY_COUNT)));
                product.setProduct_array_count_now(cursor.getInt(cursor.getColumnIndex(Products.COLUMN_PRODUCT_ARRAY_COUNT_NOW)));
                product.setP_id(cursor.getInt(cursor.getColumnIndex(Products.COLUMN_PRODUCT_ID)));
                product.setUserId(cursor.getInt(cursor.getColumnIndex(Products.COLUMN_USER_ID)));
                product.setType(cursor.getInt(cursor.getColumnIndex(Products.COLUMN_TYPE)));
                product.setName(cursor.getString(cursor.getColumnIndex(Products.COLUMN_NAME)));
                product.setPlusCode(cursor.getString(cursor.getColumnIndex(Products.COLUMN_PLUS_CODE)));
                product.setLatitude(cursor.getDouble(cursor.getColumnIndex(Products.COLUMN_LATITUDE)));
                product.setLongitude(cursor.getDouble(cursor.getColumnIndex(Products.COLUMN_LONGITUDE)));
                product.setLocation(cursor.getString(cursor.getColumnIndex(Products.COLUMN_LOCATION)));
                product.setPhoto(cursor.getString(cursor.getColumnIndex(Products.COLUMN_PHOTO)));
                product.setIsdCode(cursor.getString(cursor.getColumnIndex(Products.COLUMN_ISD_CODE)));
                product.setPhone(cursor.getString(cursor.getColumnIndex(Products.COLUMN_PHONE)));
                product.setStdCode(cursor.getString(cursor.getColumnIndex(Products.COLUMN_STD_CODE)));
                product.setLandline(cursor.getString(cursor.getColumnIndex(Products.COLUMN_LANDLINE)));
                product.setEmail(cursor.getString(cursor.getColumnIndex(Products.COLUMN_EMAIL)));
                product.setWebsite(cursor.getString(cursor.getColumnIndex(Products.COLUMN_WEBSITE)));
                product.setDescription(cursor.getString(cursor.getColumnIndex(Products.COLUMN_DESCRIPTION)));
                product.setPrice(cursor.getString(cursor.getColumnIndex(Products.COLUMN_PRICE)));
                product.setValidityStart(cursor.getString(cursor.getColumnIndex(Products.COLUMN_VALIDITY_START)));
                product.setValidityEnd(cursor.getString(cursor.getColumnIndex(Products.COLUMN_VALIDITY_END)));
                product.setQuality(cursor.getString(cursor.getColumnIndex(Products.COLUMN_QUALITY)));
                product.setQuantity(cursor.getInt(cursor.getColumnIndex(Products.COLUMN_QUANTITY)));
                product.setOffer(cursor.getString(cursor.getColumnIndex(Products.COLUMN_OFFER)));
                product.setAttachment(cursor.getString(cursor.getColumnIndex(Products.COLUMN_ATTACHMENT)));
                product.setImage(cursor.getString(cursor.getColumnIndex(Products.COLUMN_IMAGE)));
                product.setCreatedAt(cursor.getString(cursor.getColumnIndex(Products.COLUMN_CREATED_AT)));
                product.setGst(cursor.getString(cursor.getColumnIndex(Products.COLUMN_GST)));
                product.setHashedId(cursor.getString(cursor.getColumnIndex(Products.COLUMN_HASHED_ID)));
                product.setHashedIdUrl(cursor.getString(cursor.getColumnIndex(Products.COLUMN_HASHED_ID_URL)));
                product.setFavourite(cursor.getString(cursor.getColumnIndex(Products.COLUMN_FAVORITE)));
                product.setTime_date(cursor.getString(cursor.getColumnIndex(Products.COLUMN_TIME_DATE)));

                sb_Pacn_Products.add(product);
            } while (cursor.moveToNext());

        // close the db connection
        cursor.close();

        return sb_Pacn_Products;
    }

    public mSubCategory getSubCategory(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(mSubCategory.TABLE_NAME_SUB_CATEGORY,
                new String[]{mSubCategory.COLUMN_ID, mSubCategory.COLUMN_PRODUCT_IMAGE, mSubCategory.COLUMN_PRODUCT_NAME, mSubCategory.COLUMN_PRODUCT_PRICE, SubCat.COLUMN_TIMESTAMP},
                mSubCategory.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare subCat object
        mSubCategory mSubCategorys = new mSubCategory(
                cursor.getInt(cursor.getColumnIndex(mSubCategory.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(mSubCategory.COLUMN_PRODUCT_IMAGE)),
                cursor.getString(cursor.getColumnIndex(mSubCategory.COLUMN_PRODUCT_NAME)),
                cursor.getString(cursor.getColumnIndex(mSubCategory.COLUMN_PRODUCT_PRICE)),
                cursor.getString(cursor.getColumnIndex(mSubCategory.COLUMN_PRODUCT_TIME_DATE)));

        // close the db connection
        cursor.close();

        return mSubCategorys;
    }

    public List<SubCat_Product_ID> getAllProductID() {
        List<SubCat_Product_ID> subCatProductIds = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + SubCat_Product_ID.TABLE_NAME_PRODUCT_ID + " ORDER BY " +
                SubCat_Product_ID.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SubCat_Product_ID subCatProductId = new SubCat_Product_ID();
                subCatProductId.setId(cursor.getInt(cursor.getColumnIndex(SubCat_Product_ID.COLUMN_ID)));
                subCatProductId.setProduct_id(cursor.getString(cursor.getColumnIndex(SubCat_Product_ID.COLUMN_PRODUCT_ID)));
                subCatProductId.setTimestamp(cursor.getString(cursor.getColumnIndex(SubCat_Product_ID.COLUMN_TIMESTAMP)));

                subCatProductIds.add(subCatProductId);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return subCats list
        return subCatProductIds;
    }

    public List<mImageUpload> getAllImageUpload() {
        List<mImageUpload> mImageUploadList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + mImageUpload.TABLE_NAME_IMAGE_UPLOAD + " ORDER BY " +
                mImageUpload.COLUMN_TIME_DATE + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                mImageUpload mImageUploads = new mImageUpload();
                mImageUploads.setId(cursor.getInt(cursor.getColumnIndex(mImageUpload.COLUMN_ID)));
                mImageUploads.setImage_path(cursor.getString(cursor.getColumnIndex(mImageUpload.COLUMN_IMAGE_PATH)));
                mImageUploads.setImage_uri(cursor.getString(cursor.getColumnIndex(mImageUpload.COLUMN_IMAGE_URI)));
                mImageUploads.setImage_type(cursor.getString(cursor.getColumnIndex(mImageUpload.COLUMN_IMAGE_TYPE)));
                mImageUploads.setTime_date(cursor.getString(cursor.getColumnIndex(mImageUpload.COLUMN_TIME_DATE)));

                mImageUploadList.add(mImageUploads);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return subCats list
        return mImageUploadList;
    }

    public List<SubCat_Product_Name> getAllProductName() {
        List<SubCat_Product_Name> subCatProductNames = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + SubCat_Product_Name.TABLE_NAME_PRODUCT_NAME + " ORDER BY " +
                SubCat_Product_Name.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SubCat_Product_Name subCatProductName = new SubCat_Product_Name();
                subCatProductName.setId(cursor.getInt(cursor.getColumnIndex(SubCat_Product_Name.COLUMN_ID)));
                subCatProductName.setProduct_name(cursor.getString(cursor.getColumnIndex(SubCat_Product_Name.COLUMN_PRODUCT_NAME)));
                subCatProductName.setTimestamp(cursor.getString(cursor.getColumnIndex(SubCat_Product_Name.COLUMN_TIMESTAMP)));

                subCatProductNames.add(subCatProductName);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return subCats list
        return subCatProductNames;
    }

    public List<SubCat_Product_Quantity> getAllQuantity() {
        List<SubCat_Product_Quantity> subCatProductQuantities = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + SubCat_Product_Quantity.TABLE_NAME_PRODUCT_QUANTITY + " ORDER BY " +
                SubCat_Product_Quantity.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SubCat_Product_Quantity subCatProductQuantity = new SubCat_Product_Quantity();
                subCatProductQuantity.setId(cursor.getInt(cursor.getColumnIndex(subCatProductQuantity.COLUMN_ID)));
                subCatProductQuantity.setQuantity(cursor.getString(cursor.getColumnIndex(subCatProductQuantity.COLUMN_QUANTITY)));
                subCatProductQuantity.setTimestamp(cursor.getString(cursor.getColumnIndex(subCatProductQuantity.COLUMN_TIMESTAMP)));

                subCatProductQuantities.add(subCatProductQuantity);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return subCats list
        return subCatProductQuantities;
    }

    public List<SubCat_Product_Price> getAllPrice() {
        List<SubCat_Product_Price> subCatProductPrices = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + SubCat_Product_Price.TABLE_NAME_PRODUCT_PRICE + " ORDER BY " +
                SubCat_Product_Price.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SubCat_Product_Price subCatProductPrice = new SubCat_Product_Price();
                subCatProductPrice.setId(cursor.getInt(cursor.getColumnIndex(SubCat_Product_Price.COLUMN_ID)));
                subCatProductPrice.setPrice(cursor.getString(cursor.getColumnIndex(SubCat_Product_Price.COLUMN_PRICE)));
                subCatProductPrice.setTimestamp(cursor.getString(cursor.getColumnIndex(SubCat_Product_Price.COLUMN_TIMESTAMP)));

                subCatProductPrices.add(subCatProductPrice);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return subCats list
        return subCatProductPrices;
    }

    public List<SubCat_Product_Total> getAllTotal() {
        List<SubCat_Product_Total> subCatProductTotals = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + SubCat_Product_Total.TABLE_NAME_PRODUCT_TOTAL + " ORDER BY " +
                SubCat_Product_Total.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SubCat_Product_Total subCatProductTotal = new SubCat_Product_Total();
                subCatProductTotal.setId(cursor.getInt(cursor.getColumnIndex(SubCat_Product_Total.COLUMN_ID)));
                subCatProductTotal.setTotal(cursor.getString(cursor.getColumnIndex(SubCat_Product_Total.COLUMN_TOTAL)));
                subCatProductTotal.setTimestamp(cursor.getString(cursor.getColumnIndex(SubCat_Product_Total.COLUMN_TIMESTAMP)));

                subCatProductTotals.add(subCatProductTotal);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return subCats list
        return subCatProductTotals;
    }

    public List<SubCat> getAllSubCat() {
        List<SubCat> subCats = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + SubCat.TABLE_NAME + " ORDER BY " +
                SubCat.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SubCat subCat = new SubCat();
                subCat.setId(cursor.getInt(cursor.getColumnIndex(SubCat.COLUMN_ID)));
                subCat.setProduct_id(cursor.getString(cursor.getColumnIndex(SubCat.COLUMN_PRODUCT_ID)));
                subCat.setProductName(cursor.getString(cursor.getColumnIndex(SubCat.COLUMN_PRODUCT_NAME)));
                subCat.setQuantity(cursor.getString(cursor.getColumnIndex(SubCat.COLUMN_QUANTITY)));
                subCat.setPrice(cursor.getString(cursor.getColumnIndex(SubCat.COLUMN_PRICE)));
                subCat.setTotal(cursor.getString(cursor.getColumnIndex(SubCat.COLUMN_TOTAL)));
                subCat.setTimestamp(cursor.getString(cursor.getColumnIndex(SubCat.COLUMN_TIMESTAMP)));

                subCats.add(subCat);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return subCats list
        return subCats;
    }

    public List<Data> getAllDataSB() {
        List<Data> mDataArrayList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Data.TABLE_NAME_DATA_SB + " ORDER BY " +
                Data.COLUMN_TIME_DATE + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Data mDatas = new Data();
                mDatas.setId(cursor.getInt(cursor.getColumnIndex(Data.COLUMN_ID)));
                mDatas.setCurrentPage(cursor.getInt(cursor.getColumnIndex(Data.COLUMN_CURRENT_PAGE)));
                mDatas.setLastPage(cursor.getInt(cursor.getColumnIndex(Data.COLUMN_LAST_PAGE)));
                mDatas.setNextPageUrl(cursor.getString(cursor.getColumnIndex(Data.COLUMN_NEXT_PAGE_URL)));
                mDatas.setTime_date(cursor.getString(cursor.getColumnIndex(Data.COLUMN_TIME_DATE)));

                mDataArrayList.add(mDatas);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return subCats list
        return mDataArrayList;
    }

    public List<Example> getAllExampleSB() {
        List<Example> mExampleArrayList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Example.TABLE_NAME_EXAMPLE_SB + " ORDER BY " +
                Example.COLUMN_TIME_DATE + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Example mExamples = new Example();
                mExamples.setId(cursor.getInt(cursor.getColumnIndex(Example.COLUMN_ID)));
                mExamples.setSuccess(cursor.getInt(cursor.getColumnIndex(Example.COLUMN_SUCCESS)));
                mExamples.setStatus(cursor.getInt(cursor.getColumnIndex(Example.COLUMN_STATUS)));
                mExamples.setTime_date(cursor.getString(cursor.getColumnIndex(Example.COLUMN_TIME_DATE)));

                mExampleArrayList.add(mExamples);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return subCats list
        return mExampleArrayList;
    }

    public List<Likes> getAllLikesSB() {
        List<Likes> mLikesArrayList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Likes.TABLE_NAME_LIKES_SB + " ORDER BY " +
                Likes.COLUMN_TIME_DATE + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Likes mlike = new Likes();
                mlike.setId(cursor.getInt(cursor.getColumnIndex(Likes.COLUMN_ID)));
                mlike.setProduct_array_count(cursor.getInt(cursor.getColumnIndex(Likes.COLUMN_PRODUCT_ARRAY_COUNT)));
                mlike.setProduct_array_count_now(cursor.getInt(cursor.getColumnIndex(Likes.COLUMN_PRODUCT_ARRAY_COUNT_NOW)));
                mlike.setPositive(cursor.getInt(cursor.getColumnIndex(Likes.COLUMN_POSITIVE)));
                mlike.setNegative(cursor.getInt(cursor.getColumnIndex(Likes.COLUMN_NEGATIVE)));
                mlike.setTime_date(cursor.getString(cursor.getColumnIndex(Likes.COLUMN_TIME_DATE)));

                mLikesArrayList.add(mlike);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return subCats list
        return mLikesArrayList;
    }

    public List<Rating> getAllRatingSB() {
        List<Rating> mRatingArrayList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Rating.TABLE_NAME_RATING_SB + " ORDER BY " +
                Rating.COLUMN_TIME_DATE + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Rating ratings = new Rating();
                ratings.setId(cursor.getInt(cursor.getColumnIndex(Rating.COLUMN_ID)));
                ratings.setProduct_array_count(cursor.getInt(cursor.getColumnIndex(Rating.COLUMN_PRODUCT_ARRAY_COUNT)));
                ratings.setProduct_array_count_now(cursor.getInt(cursor.getColumnIndex(Rating.COLUMN_PRODUCT_ARRAY_COUNT_NOW)));
                ratings.setCount(cursor.getInt(cursor.getColumnIndex(Rating.COLUMN_COUNT)));
                ratings.setAverage(cursor.getInt(cursor.getColumnIndex(Rating.COLUMN_AVERAGE)));
                ratings.setValue(cursor.getString(cursor.getColumnIndex(Rating.COLUMN_VALUE)));
                ratings.setTime_date(cursor.getString(cursor.getColumnIndex(Rating.COLUMN_TIME_DATE)));

                mRatingArrayList.add(ratings);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return subCats list
        return mRatingArrayList;
    }

    public List<SubProduct> getAllSubProductSB() {
        List<SubProduct> mSubProductArrayList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + SubProduct.TABLE_NAME_SUB_PRODUCT_SB + " ORDER BY " +
                SubProduct.COLUMN_TIME_DATE + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SubProduct subProducts = new SubProduct();
                subProducts.setId(cursor.getInt(cursor.getColumnIndex(SubProduct.COLUMN_ID)));
                subProducts.setProduct_array_count(cursor.getInt(cursor.getColumnIndex(SubProduct.COLUMN_PRODUCT_ARRAY_COUNT)));
                subProducts.setProduct_array_count_now(cursor.getInt(cursor.getColumnIndex(SubProduct.COLUMN_PRODUCT_ARRAY_COUNT_NOW)));
                subProducts.setSub_product_array_count(cursor.getInt(cursor.getColumnIndex(SubProduct.COLUMN_SUB_PRODUCT_ARRAY_COUNT)));
                subProducts.setSub_product_array_count_now(cursor.getInt(cursor.getColumnIndex(SubProduct.COLUMN_SUB_PRODUCT_ARRAY_COUNT_NOW)));
                subProducts.setId(cursor.getInt(cursor.getColumnIndex(SubProduct.COLUMN_SUB_PRODUCT_ID)));
                subProducts.setName(cursor.getString(cursor.getColumnIndex(SubProduct.COLUMN_SUB_PRODUCT_NAME)));
                subProducts.setPrice(cursor.getString(cursor.getColumnIndex(SubProduct.COLUMN_SUB_PRODUCT_PRICE)));
                subProducts.setPhoto(cursor.getString(cursor.getColumnIndex(SubProduct.COLUMN_SUB_PRODUCT_PHOTO)));
                subProducts.setTime_date(cursor.getString(cursor.getColumnIndex(SubProduct.COLUMN_TIME_DATE)));

                mSubProductArrayList.add(subProducts);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return subCats list
        return mSubProductArrayList;
    }

    public List<Products> getAllProductsSB() {
        List<Products> mProductsArrayList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Products.TABLE_NAME_PRODUCTS_SB + " ORDER BY " +
                Products.COLUMN_TIME_DATE + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Products product = new Products();
                product.setId(cursor.getInt(cursor.getColumnIndex(Products.COLUMN_ID)));
                product.setProduct_array_count(cursor.getInt(cursor.getColumnIndex(Products.COLUMN_PRODUCT_ARRAY_COUNT)));
                product.setProduct_array_count_now(cursor.getInt(cursor.getColumnIndex(Products.COLUMN_PRODUCT_ARRAY_COUNT_NOW)));
                product.setP_id(cursor.getInt(cursor.getColumnIndex(Products.COLUMN_PRODUCT_ID)));
                product.setUserId(cursor.getInt(cursor.getColumnIndex(Products.COLUMN_USER_ID)));
                product.setType(cursor.getInt(cursor.getColumnIndex(Products.COLUMN_TYPE)));
                product.setName(cursor.getString(cursor.getColumnIndex(Products.COLUMN_NAME)));
                product.setPlusCode(cursor.getString(cursor.getColumnIndex(Products.COLUMN_PLUS_CODE)));
                product.setLatitude(cursor.getDouble(cursor.getColumnIndex(Products.COLUMN_LATITUDE)));
                product.setLongitude(cursor.getDouble(cursor.getColumnIndex(Products.COLUMN_LONGITUDE)));
                product.setLocation(cursor.getString(cursor.getColumnIndex(Products.COLUMN_LOCATION)));
                product.setPhoto(cursor.getString(cursor.getColumnIndex(Products.COLUMN_PHOTO)));
                product.setIsdCode(cursor.getString(cursor.getColumnIndex(Products.COLUMN_ISD_CODE)));
                product.setPhone(cursor.getString(cursor.getColumnIndex(Products.COLUMN_PHONE)));
                product.setStdCode(cursor.getString(cursor.getColumnIndex(Products.COLUMN_STD_CODE)));
                product.setLandline(cursor.getString(cursor.getColumnIndex(Products.COLUMN_LANDLINE)));
                product.setEmail(cursor.getString(cursor.getColumnIndex(Products.COLUMN_EMAIL)));
                product.setWebsite(cursor.getString(cursor.getColumnIndex(Products.COLUMN_WEBSITE)));
                product.setDescription(cursor.getString(cursor.getColumnIndex(Products.COLUMN_DESCRIPTION)));
                product.setPrice(cursor.getString(cursor.getColumnIndex(Products.COLUMN_PRICE)));
                product.setValidityStart(cursor.getString(cursor.getColumnIndex(Products.COLUMN_VALIDITY_START)));
                product.setValidityEnd(cursor.getString(cursor.getColumnIndex(Products.COLUMN_VALIDITY_END)));
                product.setQuality(cursor.getString(cursor.getColumnIndex(Products.COLUMN_QUALITY)));
                product.setQuantity(cursor.getInt(cursor.getColumnIndex(Products.COLUMN_QUANTITY)));
                product.setOffer(cursor.getString(cursor.getColumnIndex(Products.COLUMN_OFFER)));
                product.setAttachment(cursor.getString(cursor.getColumnIndex(Products.COLUMN_ATTACHMENT)));
                product.setImage(cursor.getString(cursor.getColumnIndex(Products.COLUMN_IMAGE)));
                product.setCreatedAt(cursor.getString(cursor.getColumnIndex(Products.COLUMN_CREATED_AT)));
                product.setGst(cursor.getString(cursor.getColumnIndex(Products.COLUMN_GST)));
                product.setHashedId(cursor.getString(cursor.getColumnIndex(Products.COLUMN_HASHED_ID)));
                product.setHashedIdUrl(cursor.getString(cursor.getColumnIndex(Products.COLUMN_HASHED_ID_URL)));
                product.setFavourite(cursor.getString(cursor.getColumnIndex(Products.COLUMN_FAVORITE)));
                product.setTime_date(cursor.getString(cursor.getColumnIndex(Products.COLUMN_TIME_DATE)));

                mProductsArrayList.add(product);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return subCats list
        return mProductsArrayList;
    }

    public List<mSubCategory> getAllSubCategory() {
        List<mSubCategory> mSubCategoryList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + mSubCategory.TABLE_NAME_SUB_CATEGORY + " ORDER BY " +
                mSubCategory.COLUMN_PRODUCT_TIME_DATE + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                mSubCategory mSubCategorys = new mSubCategory();
                mSubCategorys.setId(cursor.getInt(cursor.getColumnIndex(mSubCategory.COLUMN_ID)));
                mSubCategorys.setProduct_image(cursor.getString(cursor.getColumnIndex(mSubCategory.COLUMN_PRODUCT_IMAGE)));
                mSubCategorys.setProduct_name(cursor.getString(cursor.getColumnIndex(mSubCategory.COLUMN_PRODUCT_NAME)));
                mSubCategorys.setProduct_price(cursor.getString(cursor.getColumnIndex(mSubCategory.COLUMN_PRODUCT_PRICE)));
                mSubCategorys.setProduct_time_date(cursor.getString(cursor.getColumnIndex(mSubCategory.COLUMN_PRODUCT_TIME_DATE)));

                mSubCategoryList.add(mSubCategorys);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return subCats list
        return mSubCategoryList;
    }

    public int getProduct_Id_Count() {
        String countQuery = "SELECT  * FROM " + SubCat_Product_ID.TABLE_NAME_PRODUCT_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int getImageUploadCount() {
        String countQuery = "SELECT  * FROM " + mImageUpload.TABLE_NAME_IMAGE_UPLOAD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int getProduct_Name_Count() {
        String countQuery = "SELECT  * FROM " + SubCat_Product_Name.TABLE_NAME_PRODUCT_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int getProduct_Quantity_Count() {
        String countQuery = "SELECT  * FROM " + SubCat_Product_Quantity.TABLE_NAME_PRODUCT_QUANTITY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int getProduct_Price_Count() {
        String countQuery = "SELECT  * FROM " + SubCat_Product_Price.TABLE_NAME_PRODUCT_PRICE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int getProduct_Total_Count() {
        String countQuery = "SELECT  * FROM " + SubCat_Product_Total.TABLE_NAME_PRODUCT_TOTAL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int getSubCatCount() {
        String countQuery = "SELECT  * FROM " + SubCat.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int getSubCategoryCount() {
        String countQuery = "SELECT  * FROM " + mSubCategory.TABLE_NAME_SUB_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int getDataSBCount() {
        String countQuery = "SELECT  * FROM " + Data.TABLE_NAME_DATA_SB;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int getExampleSBCount() {
        String countQuery = "SELECT  * FROM " + Example.TABLE_NAME_EXAMPLE_SB;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int getLikesSBCount() {
        String countQuery = "SELECT  * FROM " + Likes.TABLE_NAME_LIKES_SB;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int getRatingsSBCount() {
        String countQuery = "SELECT  * FROM " + Rating.TABLE_NAME_RATING_SB;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int getSubProductSBCount() {
        String countQuery = "SELECT  * FROM " + SubProduct.TABLE_NAME_SUB_PRODUCT_SB;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int getProductsSBCount() {
        String countQuery = "SELECT  * FROM " + Products.TABLE_NAME_PRODUCTS_SB;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public int updateSubCat_Product_ID(SubCat_Product_ID subCatProductId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SubCat_Product_ID.COLUMN_PRODUCT_ID, subCatProductId.getProduct_id());
        // updating row
        return db.update(SubCat_Product_ID.TABLE_NAME_PRODUCT_ID, values, SubCat_Product_ID.COLUMN_ID + " = ?",
                new String[]{String.valueOf(subCatProductId.getId())});
    }

    public int updateImageUpload(mImageUpload mImageUploads) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(mImageUpload.COLUMN_IMAGE_PATH, mImageUploads.getImage_path());
        values.put(mImageUpload.COLUMN_IMAGE_URI, mImageUploads.getImage_uri());
        values.put(mImageUpload.COLUMN_IMAGE_TYPE, mImageUploads.getImage_type());
        // updating row
        return db.update(mImageUpload.TABLE_NAME_IMAGE_UPLOAD, values, mImageUpload.COLUMN_ID + " = ?",
                new String[]{String.valueOf(mImageUploads.getId())});
    }

    public int updateImageUploadNw(String imagePath, String image_uri, String imageType) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(mImageUpload.COLUMN_IMAGE_PATH, imagePath);
        values.put(mImageUpload.COLUMN_IMAGE_URI, image_uri);
        // updating row
        return db.update(mImageUpload.TABLE_NAME_IMAGE_UPLOAD, values, mImageUpload.COLUMN_IMAGE_TYPE + " = ?",
                new String[]{String.valueOf(imageType)});
    }

    public int updateSubCat_Product_Name(SubCat_Product_Name subCatProductName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SubCat_Product_Name.COLUMN_PRODUCT_NAME, subCatProductName.getProduct_name());
        // updating row
        return db.update(SubCat_Product_Name.TABLE_NAME_PRODUCT_NAME, values, SubCat_Product_Name.COLUMN_ID + " = ?",
                new String[]{String.valueOf(subCatProductName.getId())});
    }

    public int updateSubCat_Product_Quantity(SubCat_Product_Quantity subCatProductQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SubCat_Product_Quantity.COLUMN_QUANTITY, subCatProductQuantity.getQuantity());
        // updating row
        return db.update(SubCat_Product_Quantity.TABLE_NAME_PRODUCT_QUANTITY, values, SubCat_Product_Quantity.COLUMN_ID + " = ?",
                new String[]{String.valueOf(subCatProductQuantity.getId())});
    }

    public int updateSubCat_Product_Price(SubCat_Product_Price subCatProductPrice) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SubCat_Product_Price.COLUMN_PRICE, subCatProductPrice.getPrice());
        // updating row
        return db.update(SubCat_Product_Price.TABLE_NAME_PRODUCT_PRICE, values, SubCat_Product_Price.COLUMN_ID + " = ?",
                new String[]{String.valueOf(subCatProductPrice.getId())});
    }

    public int updateSubCat_Product_Total(SubCat_Product_Total subCatProductTotal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SubCat_Product_Total.COLUMN_TOTAL, subCatProductTotal.getTotal());
        // updating row
        return db.update(SubCat_Product_Total.TABLE_NAME_PRODUCT_TOTAL, values, SubCat_Product_Total.COLUMN_ID + " = ?",
                new String[]{String.valueOf(subCatProductTotal.getId())});
    }

    public int updateSubCat(SubCat subCat) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SubCat.COLUMN_PRODUCT_ID, subCat.getProduct_id());
        values.put(SubCat.COLUMN_PRODUCT_NAME, subCat.getProductName());
        values.put(SubCat.COLUMN_QUANTITY, subCat.getQuantity());
        values.put(SubCat.COLUMN_PRICE, subCat.getPrice());
        values.put(SubCat.COLUMN_TOTAL, subCat.getTotal());

        // updating row
        return db.update(SubCat.TABLE_NAME, values, SubCat.COLUMN_ID + " = ?",
                new String[]{String.valueOf(subCat.getId())});
    }

    public int updateSubCategory(mSubCategory mSubCategorys) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(mSubCategory.COLUMN_PRODUCT_IMAGE, mSubCategorys.getProduct_image());
        values.put(mSubCategory.COLUMN_PRODUCT_NAME, mSubCategorys.getProduct_name());
        values.put(mSubCategory.COLUMN_PRODUCT_PRICE, mSubCategorys.getProduct_price());

        // updating row
        return db.update(mSubCategory.TABLE_NAME_SUB_CATEGORY, values, mSubCategory.COLUMN_ID + " = ?",
                new String[]{String.valueOf(mSubCategorys.getId())});
    }

    public int updateDataSB(Data datas) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Data.COLUMN_CURRENT_PAGE, datas.getCurrentPage());
        values.put(Data.COLUMN_LAST_PAGE, datas.getLastPage());
        values.put(Data.COLUMN_NEXT_PAGE_URL, datas.getNextPageUrl());

        // updating row
        return db.update(Data.TABLE_NAME_DATA_SB, values, Data.COLUMN_ID + " = ?",
                new String[]{String.valueOf(datas.getId())});
    }

    public int updateExampleSB(Example examples) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Example.COLUMN_SUCCESS, examples.getSuccess());
        values.put(Example.COLUMN_STATUS, examples.getStatus());

        // updating row
        return db.update(Example.TABLE_NAME_EXAMPLE_SB, values, Example.COLUMN_ID + " = ?",
                new String[]{String.valueOf(examples.getId())});
    }

    public int updateLikesSB(Likes like) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Likes.COLUMN_PRODUCT_ARRAY_COUNT, like.getProduct_array_count());
        values.put(Likes.COLUMN_PRODUCT_ARRAY_COUNT_NOW, like.getProduct_array_count_now());
        values.put(Likes.COLUMN_POSITIVE, like.getPositive());
        values.put(Likes.COLUMN_NEGATIVE, like.getNegative());

        // updating row
        return db.update(Likes.TABLE_NAME_LIKES_SB, values, Likes.COLUMN_ID + " = ?",
                new String[]{String.valueOf(like.getId())});
    }

    public int updateRatingSB(Rating rating) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Rating.COLUMN_PRODUCT_ARRAY_COUNT, rating.getProduct_array_count());
        values.put(Rating.COLUMN_PRODUCT_ARRAY_COUNT_NOW, rating.getProduct_array_count_now());
        values.put(Rating.COLUMN_COUNT, rating.getCount());
        values.put(Rating.COLUMN_AVERAGE, rating.getAverage());
        values.put(Rating.COLUMN_VALUE, rating.getValue());

        // updating row
        return db.update(Rating.TABLE_NAME_RATING_SB, values, Rating.COLUMN_ID + " = ?",
                new String[]{String.valueOf(rating.getId())});
    }

    public int updateSubProductSB(SubProduct subProducts) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SubProduct.COLUMN_PRODUCT_ARRAY_COUNT, subProducts.getProduct_array_count());
        values.put(SubProduct.COLUMN_PRODUCT_ARRAY_COUNT_NOW, subProducts.getProduct_array_count_now());
        values.put(SubProduct.COLUMN_SUB_PRODUCT_ARRAY_COUNT, subProducts.getSub_product_array_count());
        values.put(SubProduct.COLUMN_SUB_PRODUCT_ARRAY_COUNT_NOW, subProducts.getSub_product_array_count_now());
        values.put(SubProduct.COLUMN_SUB_PRODUCT_ID, subProducts.getId());
        values.put(SubProduct.COLUMN_SUB_PRODUCT_NAME, subProducts.getName());
        values.put(SubProduct.COLUMN_SUB_PRODUCT_PRICE, subProducts.getPrice());
        values.put(SubProduct.COLUMN_SUB_PRODUCT_PHOTO, subProducts.getPhoto());

        // updating row
        return db.update(SubProduct.TABLE_NAME_SUB_PRODUCT_SB, values, SubProduct.COLUMN_ID + " = ?",
                new String[]{String.valueOf(subProducts.getId())});
    }

    public int updateProductsSB(Products product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SubProduct.COLUMN_PRODUCT_ARRAY_COUNT, product.getProduct_array_count());
        values.put(SubProduct.COLUMN_PRODUCT_ARRAY_COUNT_NOW, product.getProduct_array_count_now());
        values.put(Products.COLUMN_PRODUCT_ID, product.getP_id());
        values.put(Products.COLUMN_USER_ID, product.getUserId());
        values.put(Products.COLUMN_TYPE, product.getType());
        values.put(Products.COLUMN_NAME, product.getName());
        values.put(Products.COLUMN_PLUS_CODE, product.getPlusCode());
        values.put(Products.COLUMN_LATITUDE, product.getLatitude());
        values.put(Products.COLUMN_LONGITUDE, product.getLongitude());
        values.put(Products.COLUMN_LOCATION, product.getLocation());
        values.put(Products.COLUMN_PHOTO, product.getPhoto());
        values.put(Products.COLUMN_ISD_CODE, product.getIsdCode());
        values.put(Products.COLUMN_PHONE, product.getPhone());
        values.put(Products.COLUMN_STD_CODE, product.getStdCode());
        values.put(Products.COLUMN_LANDLINE, product.getLandline());
        values.put(Products.COLUMN_EMAIL, product.getEmail());
        values.put(Products.COLUMN_WEBSITE, product.getWebsite());
        values.put(Products.COLUMN_DESCRIPTION, product.getDescription());
        values.put(Products.COLUMN_PRICE, product.getPrice());
        values.put(Products.COLUMN_VALIDITY_START, product.getValidityStart());
        values.put(Products.COLUMN_VALIDITY_END, product.getValidityEnd());
        values.put(Products.COLUMN_QUALITY, product.getQuality());
        values.put(Products.COLUMN_QUANTITY, product.getQuantity());
        values.put(Products.COLUMN_OFFER, product.getOffer());
        values.put(Products.COLUMN_ATTACHMENT, product.getAttachment());
        values.put(Products.COLUMN_IMAGE, product.getImage());
        values.put(Products.COLUMN_CREATED_AT, product.getCreatedAt());
        values.put(Products.COLUMN_GST, product.getGst());
        values.put(Products.COLUMN_HASHED_ID, product.getHashedId());
        values.put(Products.COLUMN_HASHED_ID_URL, product.getHashedIdUrl());
        values.put(Products.COLUMN_FAVORITE, product.getFavourite());

        // updating row
        return db.update(Products.TABLE_NAME_PRODUCTS_SB, values, Products.COLUMN_ID + " = ?",
                new String[]{String.valueOf(product.getId())});
    }

    public int updateSubCategoryPi(mSubCategory mSubCategorys) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(mSubCategory.COLUMN_PRODUCT_IMAGE, mSubCategorys.getProduct_image());

        // updating row
        return db.update(mSubCategory.TABLE_NAME_SUB_CATEGORY, values, mSubCategory.COLUMN_ID + " = ?",
                new String[]{String.valueOf(mSubCategorys.getId())});
    }

    public int updateSubCategoryPn(mSubCategory mSubCategorys) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(mSubCategory.COLUMN_PRODUCT_NAME, mSubCategorys.getProduct_name());

        // updating row
        return db.update(mSubCategory.TABLE_NAME_SUB_CATEGORY, values, mSubCategory.COLUMN_ID + " = ?",
                new String[]{String.valueOf(mSubCategorys.getId())});
    }

    public int updateSubCategoryPp(mSubCategory mSubCategorys) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(mSubCategory.COLUMN_PRODUCT_PRICE, mSubCategorys.getProduct_price());

        // updating row
        return db.update(mSubCategory.TABLE_NAME_SUB_CATEGORY, values, mSubCategory.COLUMN_ID + " = ?",
                new String[]{String.valueOf(mSubCategorys.getId())});
    }

    public void deleteProduct_ID(SubCat_Product_ID subCatProductId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SubCat_Product_ID.TABLE_NAME_PRODUCT_ID, SubCat_Product_ID.COLUMN_ID + " = ?",
                new String[]{String.valueOf(subCatProductId.getId())});
        db.close();
    }

    public void deleteImageUpload(mImageUpload mImageUploads) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(mImageUpload.TABLE_NAME_IMAGE_UPLOAD, mImageUpload.COLUMN_ID + " = ?",
                new String[]{String.valueOf(mImageUploads.getId())});
        db.close();
    }

    public void deleteProduct_Name(SubCat_Product_Name subCatProductName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SubCat_Product_Name.TABLE_NAME_PRODUCT_NAME, SubCat_Product_Name.COLUMN_ID + " = ?",
                new String[]{String.valueOf(subCatProductName.getId())});
        db.close();
    }

    public void deleteProduct_Quantity(SubCat_Product_Quantity subCatProductQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SubCat_Product_Quantity.TABLE_NAME_PRODUCT_QUANTITY, SubCat_Product_Quantity.COLUMN_ID + " = ?",
                new String[]{String.valueOf(subCatProductQuantity.getId())});
        db.close();
    }

    public void deleteProduct_Price(SubCat_Product_Price subCatProductPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SubCat_Product_Price.TABLE_NAME_PRODUCT_PRICE, SubCat_Product_Price.COLUMN_ID + " = ?",
                new String[]{String.valueOf(subCatProductPrice.getId())});
        db.close();
    }

    public void deleteProduct_Total(SubCat_Product_Total subCatProductTotal) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SubCat_Product_Total.TABLE_NAME_PRODUCT_TOTAL, SubCat_Product_Total.COLUMN_ID + " = ?",
                new String[]{String.valueOf(subCatProductTotal.getId())});
        db.close();
    }

    public void deleteSubCat(SubCat subCat) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SubCat.TABLE_NAME, SubCat.COLUMN_ID + " = ?",
                new String[]{String.valueOf(subCat.getId())});
        db.close();
    }

    public void deleteSubCategory(mSubCategory mSubCategorys) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(mSubCategory.TABLE_NAME_SUB_CATEGORY, mSubCategory.COLUMN_ID + " = ?",
                new String[]{String.valueOf(mSubCategorys.getId())});
        db.close();
    }

    public void deleteDataSB(Data datas) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Data.TABLE_NAME_DATA_SB, Data.COLUMN_ID + " = ?",
                new String[]{String.valueOf(datas.getId())});
        db.close();
    }

    public void deleteExampleSB(Example examples) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Example.TABLE_NAME_EXAMPLE_SB, Example.COLUMN_ID + " = ?",
                new String[]{String.valueOf(examples.getId())});
        db.close();
    }

    public void deleteLikesSB(Likes like) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Likes.TABLE_NAME_LIKES_SB, Likes.COLUMN_ID + " = ?",
                new String[]{String.valueOf(like.getId())});
        db.close();
    }

    public void deleteRatingSB(Rating rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Rating.TABLE_NAME_RATING_SB, Rating.COLUMN_ID + " = ?",
                new String[]{String.valueOf(rating.getId())});
        db.close();
    }

    public void deleteSubProductSB(SubProduct subProduct) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SubProduct.TABLE_NAME_SUB_PRODUCT_SB, SubProduct.COLUMN_ID + " = ?",
                new String[]{String.valueOf(subProduct.getId())});
        db.close();
    }

    public void deleteProductsSB(Products products) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Products.TABLE_NAME_PRODUCTS_SB, Products.COLUMN_ID + " = ?",
                new String[]{String.valueOf(products.getId())});
        db.close();
    }

    public List<SubCat_Join_Tables> getAllSingleTables() {

        List<SubCat_Join_Tables> subCatJoinTablesList = new ArrayList<SubCat_Join_Tables>();

        String selectQuery = "SELECT " + SubCat_Product_ID.TABLE_NAME_PRODUCT_ID + " . " + SubCat_Product_ID.COLUMN_ID + " , " + SubCat_Product_ID.COLUMN_PRODUCT_ID + " , "
                + SubCat_Product_Name.TABLE_NAME_PRODUCT_NAME + " . " + SubCat_Product_Name.COLUMN_PRODUCT_NAME + " , "
                + SubCat_Product_Quantity.TABLE_NAME_PRODUCT_QUANTITY + " . " + SubCat_Product_Quantity.COLUMN_QUANTITY + " , "
                + SubCat_Product_Price.TABLE_NAME_PRODUCT_PRICE + " . " + SubCat_Product_Price.COLUMN_PRICE + " , "
                + SubCat_Product_Total.TABLE_NAME_PRODUCT_TOTAL + " . " + SubCat_Product_Total.COLUMN_TOTAL + " , "
                + SubCat_Product_Total.TABLE_NAME_PRODUCT_TOTAL + " . " + SubCat_Product_Total.COLUMN_TIMESTAMP
                + " FROM " + SubCat_Product_Total.TABLE_NAME_PRODUCT_TOTAL
                + " JOIN " + SubCat_Product_ID.TABLE_NAME_PRODUCT_ID + " ON " + SubCat_Product_ID.TABLE_NAME_PRODUCT_ID + " . " + SubCat_Product_ID.COLUMN_ID + " = " + SubCat_Product_Total.TABLE_NAME_PRODUCT_TOTAL + " . " + SubCat_Product_Total.COLUMN_ID
                + " JOIN " + SubCat_Product_Name.TABLE_NAME_PRODUCT_NAME + " ON " + SubCat_Product_Name.TABLE_NAME_PRODUCT_NAME + " . " + SubCat_Product_Name.COLUMN_ID + " = " + SubCat_Product_Total.TABLE_NAME_PRODUCT_TOTAL + " . " + SubCat_Product_Total.COLUMN_ID
                + " JOIN " + SubCat_Product_Price.TABLE_NAME_PRODUCT_PRICE + " ON " + SubCat_Product_Price.TABLE_NAME_PRODUCT_PRICE + " . " + SubCat_Product_Price.COLUMN_ID + " = " + SubCat_Product_Total.TABLE_NAME_PRODUCT_TOTAL + " . " + SubCat_Product_Total.COLUMN_ID
                + " JOIN " + SubCat_Product_Quantity.TABLE_NAME_PRODUCT_QUANTITY + " ON " + SubCat_Product_Quantity.TABLE_NAME_PRODUCT_QUANTITY + " . " + SubCat_Product_Quantity.COLUMN_ID + " = " + SubCat_Product_Total.TABLE_NAME_PRODUCT_TOTAL + " . " + SubCat_Product_Total.COLUMN_ID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SubCat_Join_Tables subCatJoinTables = new SubCat_Join_Tables();
                subCatJoinTables.setId(cursor.getInt(cursor.getColumnIndex(SubCat_Join_Tables.COLUMN_ID)));
                subCatJoinTables.setProduct_id(cursor.getString(cursor.getColumnIndex(SubCat_Join_Tables.COLUMN_PRODUCT_ID)));
                subCatJoinTables.setProductName(cursor.getString(cursor.getColumnIndex(SubCat_Join_Tables.COLUMN_PRODUCT_NAME)));
                subCatJoinTables.setQuantity(cursor.getString(cursor.getColumnIndex(SubCat_Join_Tables.COLUMN_QUANTITY)));
                subCatJoinTables.setPrice(cursor.getString(cursor.getColumnIndex(SubCat_Join_Tables.COLUMN_PRICE)));
                subCatJoinTables.setTotal(cursor.getString(cursor.getColumnIndex(SubCat_Join_Tables.COLUMN_TOTAL)));
                subCatJoinTables.setTimestamp(cursor.getString(cursor.getColumnIndex(SubCat_Join_Tables.COLUMN_TIMESTAMP)));

                subCatJoinTablesList.add(subCatJoinTables);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return subCats list
        return subCatJoinTablesList;
    }

    public void del_Product_ID() {
        SQLiteDatabase db = this.getWritableDatabase();
        String delQuery = "DELETE FROM " + SubCat_Product_ID.TABLE_NAME_PRODUCT_ID;
        db.execSQL(delQuery);
    }

    public void del_Product_Name() {
        SQLiteDatabase db = this.getWritableDatabase();
        String delQuery = "DELETE FROM " + SubCat_Product_Name.TABLE_NAME_PRODUCT_NAME;
        db.execSQL(delQuery);
    }

    public void del_Product_Quantity() {
        SQLiteDatabase db = this.getWritableDatabase();
        String delQuery = "DELETE FROM " + SubCat_Product_Quantity.TABLE_NAME_PRODUCT_QUANTITY;
        db.execSQL(delQuery);
    }

    public void del_Product_Price() {
        SQLiteDatabase db = this.getWritableDatabase();
        String delQuery = "DELETE FROM " + SubCat_Product_Price.TABLE_NAME_PRODUCT_PRICE;
        db.execSQL(delQuery);
    }

    public void del_Product_Total() {
        SQLiteDatabase db = this.getWritableDatabase();
        String delQuery = "DELETE FROM " + SubCat_Product_Total.TABLE_NAME_PRODUCT_TOTAL;
        db.execSQL(delQuery);
    }

    public void del_SubCat() {
        SQLiteDatabase db = this.getWritableDatabase();
        String delQuery = "DELETE FROM " + SubCat.TABLE_NAME;
        db.execSQL(delQuery);
    }

    public void del_ImageUpload() {
        SQLiteDatabase db = this.getWritableDatabase();
        String delQuery = "DELETE FROM " + mImageUpload.TABLE_NAME_IMAGE_UPLOAD;
        db.execSQL(delQuery);
    }

    public void del_DataSB() {
        SQLiteDatabase db = this.getWritableDatabase();
        String delQuery = "DELETE FROM " + Data.TABLE_NAME_DATA_SB;
        db.execSQL(delQuery);
    }

    public void del_ExampleSB() {
        SQLiteDatabase db = this.getWritableDatabase();
        String delQuery = "DELETE FROM " + Example.TABLE_NAME_EXAMPLE_SB;
        db.execSQL(delQuery);
    }

    public void del_LikesSB() {
        SQLiteDatabase db = this.getWritableDatabase();
        String delQuery = "DELETE FROM " + Likes.TABLE_NAME_LIKES_SB;
        db.execSQL(delQuery);
    }

    public void del_RatingSB() {
        SQLiteDatabase db = this.getWritableDatabase();
        String delQuery = "DELETE FROM " + Rating.TABLE_NAME_RATING_SB;
        db.execSQL(delQuery);
    }

    public void del_SubProductSB() {
        SQLiteDatabase db = this.getWritableDatabase();
        String delQuery = "DELETE FROM " + SubProduct.TABLE_NAME_SUB_PRODUCT_SB;
        db.execSQL(delQuery);
    }

    public void del_ProductsSB() {
        SQLiteDatabase db = this.getWritableDatabase();
        String delQuery = "DELETE FROM " + Products.TABLE_NAME_PRODUCTS_SB;
        db.execSQL(delQuery);
    }

    public void del_SubCategory() {
        SQLiteDatabase db = this.getWritableDatabase();
        String delQuery = "DELETE FROM " + mSubCategory.TABLE_NAME_SUB_CATEGORY;
        db.execSQL(delQuery);
    }
}