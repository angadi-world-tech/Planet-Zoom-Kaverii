
package com.angadi.tripmanagementa.Activities.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubProduct {

    public static final String TABLE_NAME_SUB_PRODUCT_SB = "tbl_sub_product_sb";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PRODUCT_ARRAY_COUNT = "product_array_count";
    public static final String COLUMN_PRODUCT_ARRAY_COUNT_NOW = "product_array_count_now";
    public static final String COLUMN_SUB_PRODUCT_ARRAY_COUNT = "sub_product_array_count";
    public static final String COLUMN_SUB_PRODUCT_ARRAY_COUNT_NOW = "sub_product_array_count_now";
    public static final String COLUMN_SUB_PRODUCT_ID = "sp_id";
    public static final String COLUMN_SUB_PRODUCT_NAME = "name";
    public static final String COLUMN_SUB_PRODUCT_PRICE = "price";
    public static final String COLUMN_SUB_PRODUCT_PHOTO = "photo";
    public static final String COLUMN_TIME_DATE = "time_date";

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("product_array_count")
    @Expose
    private Integer product_array_count;
    @SerializedName("product_array_count_now")
    @Expose
    private Integer product_array_count_now;
    @SerializedName("sub_product_array_count")
    @Expose
    private Integer sub_product_array_count;
    @SerializedName("sub_product_array_count_now")
    @Expose
    private Integer sub_product_array_count_now;
    @SerializedName("sp_id")
    @Expose
    private Integer sp_id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("time_date")
    @Expose
    private String time_date;

    // Create table SQL query
    public static final String CREATE_TABLE_SUB_PRODUCT_SB =
            "CREATE TABLE " + TABLE_NAME_SUB_PRODUCT_SB + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_PRODUCT_ARRAY_COUNT + " INTEGER,"
                    + COLUMN_PRODUCT_ARRAY_COUNT_NOW + " INTEGER,"
                    + COLUMN_SUB_PRODUCT_ARRAY_COUNT + " INTEGER,"
                    + COLUMN_SUB_PRODUCT_ARRAY_COUNT_NOW + " INTEGER,"
                    + COLUMN_SUB_PRODUCT_ID + " INTEGER,"
                    + COLUMN_SUB_PRODUCT_NAME + " TEXT,"
                    + COLUMN_SUB_PRODUCT_PRICE + " TEXT,"
                    + COLUMN_SUB_PRODUCT_PHOTO + " TEXT,"
                    + COLUMN_TIME_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public SubProduct() {

    }

    public SubProduct(Integer id, Integer product_array_count, Integer product_array_count_now, Integer sub_product_array_count, Integer sub_product_array_count_now, Integer sp_id, String name, String price, String photo, String time_date) {
        this.id = id;
        this.product_array_count = product_array_count;
        this.product_array_count_now = product_array_count_now;
        this.sub_product_array_count = sub_product_array_count;
        this.sub_product_array_count_now = sub_product_array_count_now;
        this.sp_id = sp_id;
        this.name = name;
        this.price = price;
        this.photo = photo;
        this.time_date = time_date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProduct_array_count() {
        return product_array_count;
    }

    public void setProduct_array_count(Integer product_array_count) {
        this.product_array_count = product_array_count;
    }

    public Integer getProduct_array_count_now() {
        return product_array_count_now;
    }

    public void setProduct_array_count_now(Integer product_array_count_now) {
        this.product_array_count_now = product_array_count_now;
    }

    public Integer getSub_product_array_count() {
        return sub_product_array_count;
    }

    public void setSub_product_array_count(Integer sub_product_array_count) {
        this.sub_product_array_count = sub_product_array_count;
    }

    public Integer getSub_product_array_count_now() {
        return sub_product_array_count_now;
    }

    public void setSub_product_array_count_now(Integer sub_product_array_count_now) {
        this.sub_product_array_count_now = sub_product_array_count_now;
    }

    public Integer getSp_id() {
        return sp_id;
    }

    public void setSp_id(Integer sp_id) {
        this.sp_id = sp_id;
    }

    public String getTime_date() {
        return time_date;
    }

    public void setTime_date(String time_date) {
        this.time_date = time_date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
