package com.angadi.tripmanagementa.Activities.database.model;

public class mSubCategory {

    public static final String TABLE_NAME_SUB_CATEGORY = "tbl_sub_category";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PRODUCT_IMAGE = "product_image";
    public static final String COLUMN_PRODUCT_NAME = "product_name";
    public static final String COLUMN_PRODUCT_PRICE = "product_price";
    public static final String COLUMN_PRODUCT_TIME_DATE = "product_time_date";

    private int id;
    private String product_image;
    private String product_name;
    private String product_price;
    private String product_time_date;

    // Create table SQL query
    public static final String CREATE_TABLE_SUB_CATEGORY =
            "CREATE TABLE " + TABLE_NAME_SUB_CATEGORY + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_PRODUCT_IMAGE + " TEXT,"
                    + COLUMN_PRODUCT_NAME + " TEXT,"
                    + COLUMN_PRODUCT_PRICE + " TEXT,"
                    + COLUMN_PRODUCT_TIME_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public mSubCategory() {

    }

    public mSubCategory(int id, String product_image, String product_name, String product_price, String product_time_date) {
        this.id = id;
        this.product_image = product_image;
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_time_date = product_time_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_time_date() {
        return product_time_date;
    }

    public void setProduct_time_date(String product_time_date) {
        this.product_time_date = product_time_date;
    }
}
