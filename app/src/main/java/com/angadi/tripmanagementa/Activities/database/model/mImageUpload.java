package com.angadi.tripmanagementa.Activities.database.model;

public class mImageUpload {

    public static final String TABLE_NAME_IMAGE_UPLOAD = "tbl_img_upload";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_IMAGE_PATH = "image_path";
    public static final String COLUMN_IMAGE_URI = "image_uri";
    public static final String COLUMN_IMAGE_TYPE = "image_type";
    public static final String COLUMN_TIME_DATE = "time_date";

    private int id;
    private String image_path;
    private String image_uri;
    private String image_type;
    private String time_date;

    // Create table SQL query
    public static final String CREATE_TABLE_IMAGE_UPLOAD =
            "CREATE TABLE " + TABLE_NAME_IMAGE_UPLOAD + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_IMAGE_PATH + " TEXT,"
                    + COLUMN_IMAGE_URI + " TEXT,"
                    + COLUMN_IMAGE_TYPE + " TEXT,"
                    + COLUMN_TIME_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public mImageUpload() {

    }

    public mImageUpload(int id, String image_path, String image_uri, String image_type, String time_date) {
        this.id = id;
        this.image_path = image_path;
        this.image_uri = image_uri;
        this.image_type = image_type;
        this.time_date = time_date;
    }

    public String getImage_uri() {
        return image_uri;
    }

    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getImage_type() {
        return image_type;
    }

    public void setImage_type(String image_type) {
        this.image_type = image_type;
    }

    public String getTime_date() {
        return time_date;
    }

    public void setTime_date(String time_date) {
        this.time_date = time_date;
    }
}
