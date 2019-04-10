
package com.angadi.tripmanagementa.Activities.database.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Products {

    public static final String TABLE_NAME_PRODUCTS_SB = "tbl_products_sb";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PRODUCT_ARRAY_COUNT = "product_array_count";
    public static final String COLUMN_PRODUCT_ARRAY_COUNT_NOW = "product_array_count_now";
    public static final String COLUMN_PRODUCT_ID = "p_id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PLUS_CODE = "plus_code";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_PHOTO = "photo";
    public static final String COLUMN_ISD_CODE = "isd_code";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_STD_CODE = "std_code";
    public static final String COLUMN_LANDLINE = "landline";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_WEBSITE = "website";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_VALIDITY_START = "validity_start";
    public static final String COLUMN_VALIDITY_END = "validity_end";
    public static final String COLUMN_QUALITY = "quality";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_OFFER = "offer";
    public static final String COLUMN_ATTACHMENT = "attachment";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_GST = "gst";
    public static final String COLUMN_HASHED_ID = "hashed_id";
    public static final String COLUMN_HASHED_ID_URL = "hashed_id_url";
    public static final String COLUMN_FAVORITE = "favourite";
    public static final String COLUMN_TIME_DATE = "time_date";

    @SerializedName("p_id")
    @Expose
    private Integer p_id;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("product_array_count")
    @Expose
    private Integer product_array_count;
    @SerializedName("product_array_count_now")
    @Expose
    private Integer product_array_count_now;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("plus_code")
    @Expose
    private String plusCode;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("isd_code")
    @Expose
    private String isdCode;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("std_code")
    @Expose
    private String stdCode;
    @SerializedName("landline")
    @Expose
    private String landline;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("validity_start")
    @Expose
    private String validityStart;
    @SerializedName("validity_end")
    @Expose
    private String validityEnd;
    @SerializedName("quality")
    @Expose
    private String quality;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("offer")
    @Expose
    private String offer;
    @SerializedName("attachment")
    @Expose
    private String attachment;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("gst")
    @Expose
    private String gst;
    @SerializedName("hashed_id")
    @Expose
    private String hashedId;
    @SerializedName("hashed_id_url")
    @Expose
    private String hashedIdUrl;
    @SerializedName("favourite")
    @Expose
    private String favourite;
    @SerializedName("time_date")
    @Expose
    private String time_date;

    // Create table SQL query
    public static final String CREATE_TABLE_PRODUCTS_SB =
            "CREATE TABLE " + TABLE_NAME_PRODUCTS_SB + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_PRODUCT_ARRAY_COUNT + " INTEGER,"
                    + COLUMN_PRODUCT_ARRAY_COUNT_NOW + " INTEGER,"
                    + COLUMN_PRODUCT_ID + " INTEGER,"
                    + COLUMN_USER_ID + " INTEGER,"
                    + COLUMN_TYPE + " INTEGER,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_PLUS_CODE + " TEXT,"
                    + COLUMN_LATITUDE + " REAL,"
                    + COLUMN_LONGITUDE + " REAL,"
                    + COLUMN_LOCATION + " TEXT,"
                    + COLUMN_PHOTO + " TEXT,"
                    + COLUMN_ISD_CODE + " TEXT,"
                    + COLUMN_PHONE + " TEXT,"
                    + COLUMN_STD_CODE + " TEXT,"
                    + COLUMN_LANDLINE + " TEXT,"
                    + COLUMN_EMAIL + " TEXT,"
                    + COLUMN_WEBSITE + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_PRICE + " TEXT,"
                    + COLUMN_VALIDITY_START + " TEXT,"
                    + COLUMN_VALIDITY_END + " TEXT,"
                    + COLUMN_QUALITY + " TEXT,"
                    + COLUMN_QUANTITY + " INTEGER,"
                    + COLUMN_OFFER + " TEXT,"
                    + COLUMN_ATTACHMENT + " TEXT,"
                    + COLUMN_IMAGE + " TEXT,"
                    + COLUMN_CREATED_AT + " TEXT,"
                    + COLUMN_GST + " TEXT,"
                    + COLUMN_HASHED_ID + " TEXT,"
                    + COLUMN_HASHED_ID_URL + " TEXT,"
                    + COLUMN_FAVORITE + " TEXT,"
                    + COLUMN_TIME_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Products() {

    }

    public Products(Integer p_id, Integer id, Integer product_array_count, Integer product_array_count_now, Integer userId, Integer type, String name, String plusCode, Double latitude, Double longitude, String location, String photo, String isdCode, String phone, String stdCode, String landline, String email, String website, String description, String price, String validityStart, String validityEnd, String quality, Integer quantity, String offer, String attachment, String image, String createdAt, String gst, String hashedId, String hashedIdUrl, String favourite, String time_date) {
        this.p_id = p_id;
        this.id = id;
        this.product_array_count = product_array_count;
        this.product_array_count_now = product_array_count_now;
        this.userId = userId;
        this.type = type;
        this.name = name;
        this.plusCode = plusCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.photo = photo;
        this.isdCode = isdCode;
        this.phone = phone;
        this.stdCode = stdCode;
        this.landline = landline;
        this.email = email;
        this.website = website;
        this.description = description;
        this.price = price;
        this.validityStart = validityStart;
        this.validityEnd = validityEnd;
        this.quality = quality;
        this.quantity = quantity;
        this.offer = offer;
        this.attachment = attachment;
        this.image = image;
        this.createdAt = createdAt;
        this.gst = gst;
        this.hashedId = hashedId;
        this.hashedIdUrl = hashedIdUrl;
        this.favourite = favourite;
        this.time_date = time_date;
    }

    public Integer getP_id() {
        return p_id;
    }

    public void setP_id(Integer p_id) {
        this.p_id = p_id;
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

    public String getTime_date() {
        return time_date;
    }

    public void setTime_date(String time_date) {
        this.time_date = time_date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlusCode() {
        return plusCode;
    }

    public void setPlusCode(String plusCode) {
        this.plusCode = plusCode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getIsdCode() {
        return isdCode;
    }

    public void setIsdCode(String isdCode) {
        this.isdCode = isdCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStdCode() {
        return stdCode;
    }

    public void setStdCode(String stdCode) {
        this.stdCode = stdCode;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getValidityStart() {
        return validityStart;
    }

    public void setValidityStart(String validityStart) {
        this.validityStart = validityStart;
    }

    public String getValidityEnd() {
        return validityEnd;
    }

    public void setValidityEnd(String validityEnd) {
        this.validityEnd = validityEnd;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getHashedId() {
        return hashedId;
    }

    public void setHashedId(String hashedId) {
        this.hashedId = hashedId;
    }

    public String getHashedIdUrl() {
        return hashedIdUrl;
    }

    public void setHashedIdUrl(String hashedIdUrl) {
        this.hashedIdUrl = hashedIdUrl;
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

}
