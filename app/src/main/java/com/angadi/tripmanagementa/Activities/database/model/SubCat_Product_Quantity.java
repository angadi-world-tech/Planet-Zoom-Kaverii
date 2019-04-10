package com.angadi.tripmanagementa.Activities.database.model;

public class SubCat_Product_Quantity {

    public static final String TABLE_NAME_PRODUCT_QUANTITY = "tbl_sub_cat_pq";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_TIMESTAMP = "date_time";

    private int id;
    private String quantity;
    private String timestamp;

    public static final String CREATE_TABLE_QUANTITY =
            "CREATE TABLE " + TABLE_NAME_PRODUCT_QUANTITY + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_QUANTITY + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public SubCat_Product_Quantity() {

    }

    public SubCat_Product_Quantity(int id, String quantity, String timestamp) {
        this.id = id;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
