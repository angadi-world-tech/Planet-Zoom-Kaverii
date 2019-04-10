package com.angadi.tripmanagementa.Model;

public class Product
{
    String id;
    String type;
    String name;
    String email ;
    String plus_code;
    String latitude;
    String longitude;
    String location;
    String std_code ;
    String landline ;
    String website ;
    String description;
    String price ;
    String validity_start;
    String validity_end ;
    String quality ;
    String quantity;
    String offer ;
    String photo ;
    String created_at;
    String phone ;
    String attachment;
    String product;
    String products ;
    String hashed_id ;


    public String getHashed_id_url() {
        return hashed_id_url;
    }

    public void setHashed_id_url(String hashed_id_url) {
        this.hashed_id_url = hashed_id_url;
    }

    String hashed_id_url ;

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

    String favourite ;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlus_code() {
        return plus_code;
    }

    public void setPlus_code(String plus_code) {
        this.plus_code = plus_code;
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

    public String getStd_code() {
        return std_code;
    }

    public void setStd_code(String std_code) {
        this.std_code = std_code;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
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

    public String getValidity_start() {
        return validity_start;
    }

    public void setValidity_start(String validity_start) {
        this.validity_start = validity_start;
    }

    public String getValidity_end() {
        return validity_end;
    }

    public void setValidity_end(String validity_end) {
        this.validity_end = validity_end;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getHashed_id() {
        return hashed_id;
    }

    public void setHashed_id(String hashed_id) {
        this.hashed_id = hashed_id;
    }



    public Product(String id,String name,String phone,String email,String cretedAt,String location,String std_code, String landline,String website,String description,
                   String price,String validity_start,String validity_end,String quality,String quantity,String offer,String photo,String attachment,String product,
                   String hashed_id,String favourite,String hashed_id_url,String latitude,String longitude)
    {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.created_at = cretedAt;
        this. location = location;
        this. std_code = std_code;
        this. landline = landline ;
        this. website = website;
        this.description = description;
        this.price = price ;
        this. validity_start = validity_start;
        this. validity_end = validity_end;
        this. quality = quality;
        this. quantity = quantity;
        this.offer = offer ;
        this. photo= photo ;
        this.attachment = attachment;
        this.product = product;
        this.hashed_id = hashed_id ;
        this.favourite = favourite ;
        this.hashed_id_url = hashed_id_url ;
        this.latitude = latitude ;
        this.longitude = longitude ;


    }



}
