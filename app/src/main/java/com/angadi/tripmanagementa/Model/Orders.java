package com.angadi.tripmanagementa.Model;

public class Orders
{
    String id;
    String product_id;
    String status;
    String name;
    String photo;
    String latitude;
    String longitude;
    String location;
    String sub_total;
    String gst;
    String tax;
    String created_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSub_total() {
        return sub_total;
    }

    public void setSub_total(String sub_total) {
        this.sub_total = sub_total;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Orders(String id, String product_id, String status, String name, String photo, String latitude, String longitude, String location,
                  String sub_total, String gst, String tax, String created_at)
    {
        this.id = id;
        this.product_id = product_id;
        this.status = status;
        this.name = name;
        this.photo = photo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.sub_total = sub_total;
        this.gst = gst;
        this.tax = tax;
        this.created_at = created_at;

    }







}
