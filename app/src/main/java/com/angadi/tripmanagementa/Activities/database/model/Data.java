
package com.angadi.tripmanagementa.Activities.database.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    public static final String TABLE_NAME_DATA_SB = "tbl_data_sb";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CURRENT_PAGE = "current_page";
    public static final String COLUMN_LAST_PAGE = "last_page";
    public static final String COLUMN_NEXT_PAGE_URL = "next_page_url";
    public static final String COLUMN_TIME_DATE = "time_date";

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("current_page")
    @Expose
    private Integer currentPage;
    @SerializedName("last_page")
    @Expose
    private Integer lastPage;
    @SerializedName("next_page_url")
    @Expose
    private String nextPageUrl;
    @SerializedName("time_date")
    @Expose
    private String time_date;

    // Create table SQL query
    public static final String CREATE_TABLE_DATA_SB =
            "CREATE TABLE " + TABLE_NAME_DATA_SB + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_CURRENT_PAGE + " INTEGER,"
                    + COLUMN_LAST_PAGE + " INTEGER,"
                    + COLUMN_NEXT_PAGE_URL + " TEXT,"
                    + COLUMN_TIME_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Data() {

    }

    public Data(int id, Integer currentPage, Integer lastPage, String nextPageUrl, String time_date) {
        this.id = id;
        this.currentPage = currentPage;
        this.lastPage = lastPage;
        this.nextPageUrl = nextPageUrl;
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

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getLastPage() {
        return lastPage;
    }

    public void setLastPage(Integer lastPage) {
        this.lastPage = lastPage;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

}
