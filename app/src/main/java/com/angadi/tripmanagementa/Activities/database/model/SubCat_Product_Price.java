package com.angadi.tripmanagementa.Activities.database.model;

public class SubCat_Product_Price {

    public static final String TABLE_NAME_PRODUCT_PRICE = "tbl_sub_cat_pp";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_TIMESTAMP = "date_time";

    private int id;
    private String price;
    private String timestamp;

    public static final String CREATE_TABLE_PRICE =
            "CREATE TABLE " + TABLE_NAME_PRODUCT_PRICE + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_PRICE + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public SubCat_Product_Price() {

    }

    public SubCat_Product_Price(int id, String price, String timestamp) {
        this.id = id;
        this.price = price;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
