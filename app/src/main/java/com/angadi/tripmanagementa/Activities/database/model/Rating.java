
package com.angadi.tripmanagementa.Activities.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rating {

    public static final String TABLE_NAME_RATING_SB = "tbl_rating_sb";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PRODUCT_ARRAY_COUNT = "product_array_count";
    public static final String COLUMN_PRODUCT_ARRAY_COUNT_NOW = "product_array_count_now";
    public static final String COLUMN_COUNT = "count";
    public static final String COLUMN_AVERAGE = "average";
    public static final String COLUMN_VALUE = "value";
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
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("average")
    @Expose
    private Integer average;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("time_date")
    @Expose
    private String time_date;

    // Create table SQL query
    public static final String CREATE_TABLE_RATING_SB =
            "CREATE TABLE " + TABLE_NAME_RATING_SB + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_PRODUCT_ARRAY_COUNT + " INTEGER,"
                    + COLUMN_PRODUCT_ARRAY_COUNT_NOW + " INTEGER,"
                    + COLUMN_COUNT + " INTEGER,"
                    + COLUMN_AVERAGE + " INTEGER,"
                    + COLUMN_VALUE + " TEXT,"
                    + COLUMN_TIME_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Rating() {

    }

    public Rating(Integer id, Integer product_array_count, Integer product_array_count_now, Integer count, Integer average, String value, String time_date) {
        this.id = id;
        this.product_array_count = product_array_count;
        this.product_array_count_now = product_array_count_now;
        this.count = count;
        this.average = average;
        this.value = value;
        this.time_date = time_date;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getAverage() {
        return average;
    }

    public void setAverage(Integer average) {
        this.average = average;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
