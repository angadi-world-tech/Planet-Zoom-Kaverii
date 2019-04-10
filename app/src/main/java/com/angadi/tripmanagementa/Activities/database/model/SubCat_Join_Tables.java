package com.angadi.tripmanagementa.Activities.database.model;

public class SubCat_Join_Tables {

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PRODUCT_ID = "product_id";
    public static final String COLUMN_PRODUCT_NAME = "product_name";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_TOTAL = "total";
    public static final String COLUMN_TIMESTAMP = "date_time";

    private int id;
    private String product_id;
    private String product_name;
    private String quantity;
    private String price;
    private String total;
    private String timestamp;

    public SubCat_Join_Tables() {

    }

    public SubCat_Join_Tables(int id, String product_id, String product_name, String quantity, String price, String total, String timestamp) {
        this.id = id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.timestamp = timestamp;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getId() {
        return id;
    }

    public String getProductName() {
        return product_name;
    }

    public void setProductName(String product_name) {
        this.product_name = product_name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
