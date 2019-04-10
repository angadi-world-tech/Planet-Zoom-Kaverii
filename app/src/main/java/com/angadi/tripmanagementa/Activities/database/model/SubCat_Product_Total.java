package com.angadi.tripmanagementa.Activities.database.model;

public class SubCat_Product_Total {

    public static final String TABLE_NAME_PRODUCT_TOTAL = "tbl_sub_cat_pt";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TOTAL = "total";
    public static final String COLUMN_TIMESTAMP = "date_time";

    private int id;
    private String total;
    private String timestamp;

    public static final String CREATE_TABLE_TOTAL =
            "CREATE TABLE " + TABLE_NAME_PRODUCT_TOTAL + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TOTAL + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public SubCat_Product_Total() {

    }

    public SubCat_Product_Total(int id, String total, String timestamp) {
        this.id = id;
        this.total = total;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
