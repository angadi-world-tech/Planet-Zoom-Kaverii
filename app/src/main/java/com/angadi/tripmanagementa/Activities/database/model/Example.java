
package com.angadi.tripmanagementa.Activities.database.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

    public static final String TABLE_NAME_EXAMPLE_SB = "tbl_example_sb";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SUCCESS = "success";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_TIME_DATE = "time_date";

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("time_date")
    @Expose
    private String time_date;

    // Create table SQL query
    public static final String CREATE_TABLE_EXAMPLE_SB =
            "CREATE TABLE " + TABLE_NAME_EXAMPLE_SB + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_SUCCESS + " INTEGER,"
                    + COLUMN_STATUS + " INTEGER,"
                    + COLUMN_TIME_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Example() {

    }

    public Example(int id, int success, int status, String time_date) {
        this.id = id;
        this.success = success;
        this.status = status;
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

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
