
package com.angadi.tripmanagementa.Activities.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Likes {

    public static final String TABLE_NAME_LIKES_SB = "tbl_likes_sb";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PRODUCT_ARRAY_COUNT = "product_array_count";
    public static final String COLUMN_PRODUCT_ARRAY_COUNT_NOW = "product_array_count_now";
    public static final String COLUMN_POSITIVE = "positive";
    public static final String COLUMN_NEGATIVE = "negative";
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
    @SerializedName("positive")
    @Expose
    private Integer positive;
    @SerializedName("negative")
    @Expose
    private Integer negative;
    @SerializedName("time_date")
    @Expose
    private String time_date;

    // Create table SQL query
    public static final String CREATE_TABLE_LIKES_SB =
            "CREATE TABLE " + TABLE_NAME_LIKES_SB + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_PRODUCT_ARRAY_COUNT + " INTEGER,"
                    + COLUMN_PRODUCT_ARRAY_COUNT_NOW + " INTEGER,"
                    + COLUMN_POSITIVE + " INTEGER,"
                    + COLUMN_NEGATIVE + " INTEGER,"
                    + COLUMN_TIME_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Likes() {

    }

    public Likes(int id, int product_array_count, int product_array_count_now, int positive, int negative, String time_date) {
        this.id = id;
        this.product_array_count = product_array_count;
        this.product_array_count_now = product_array_count_now;
        this.positive = positive;
        this.negative = negative;
        this.time_date = time_date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTime_date() {
        return time_date;
    }

    public void setTime_date(String time_date) {
        this.time_date = time_date;
    }

    public Integer getPositive() {
        return positive;
    }

    public void setPositive(Integer positive) {
        this.positive = positive;
    }

    public Integer getNegative() {
        return negative;
    }

    public void setNegative(Integer negative) {
        this.negative = negative;
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
}
