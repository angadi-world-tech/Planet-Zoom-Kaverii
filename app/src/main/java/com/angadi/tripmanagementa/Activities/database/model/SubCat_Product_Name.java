package com.angadi.tripmanagementa.Activities.database.model;

public class SubCat_Product_Name {

    public static final String TABLE_NAME_PRODUCT_NAME = "tbl_sub_cat_pn";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PRODUCT_NAME = "product_name";
    public static final String COLUMN_TIMESTAMP = "date_time";

    private int id;
    private String product_name;
    private String timestamp;

    public static final String CREATE_TABLE_PRODUCT_NAME =
            "CREATE TABLE " + TABLE_NAME_PRODUCT_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_PRODUCT_NAME + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public SubCat_Product_Name() {

    }

    public SubCat_Product_Name(int id, String product_name, String timestamp) {
        this.id = id;
        this.product_name = product_name;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
