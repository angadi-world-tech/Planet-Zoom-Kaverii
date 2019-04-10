package com.angadi.tripmanagementa.Activities.database.model;

public class SubCat_Product_ID {

    public static final String TABLE_NAME_PRODUCT_ID = "tbl_sub_cat_pid";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PRODUCT_ID = "product_id";
    public static final String COLUMN_TIMESTAMP = "date_time";

    private int id;
    private String product_id;
    private String timestamp;

    public static final String CREATE_TABLE_PRODUCT_ID =
            "CREATE TABLE " + TABLE_NAME_PRODUCT_ID + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_PRODUCT_ID + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public SubCat_Product_ID() {

    }

    public SubCat_Product_ID(int id, String product_id, String timestamp) {
        this.id = id;
        this.product_id = product_id;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
