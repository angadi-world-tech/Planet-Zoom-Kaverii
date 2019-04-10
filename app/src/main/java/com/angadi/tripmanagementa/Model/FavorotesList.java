package com.angadi.tripmanagementa.Model;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.angadi.tripmanagementa.Activities.Qrcoderesult;
import com.angadi.tripmanagementa.Adapters.GroupsAdapter;
import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.Extras.Keys;
import com.angadi.tripmanagementa.Extras.VolleyMultipartRequest;
import com.angadi.tripmanagementa.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FavorotesList
{

    String id;
    String fpg_id;
    String created_at;
    String  product;
    String  type;
    String name;
    String  plus_code;
    String location;
    String photo;
    String isd_code;
    String phone;
    String std_code;
    String landline;
    String email;
    String website;
    String description;
    String price;
    String validity_start;
    String validity_end;
    String quality;
    String quantity;
    String offer;
    String attachment;
    String image;
    String hashed_id;
    String count;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAvearage() {
        return avearage;
    }

    public void setAvearage(String avearage) {
        this.avearage = avearage;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    String avearage;
    String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFpg_id() {
        return fpg_id;
    }

    public void setFpg_id(String fpg_id) {
        this.fpg_id = fpg_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
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

    public String getIsd_code() {
        return isd_code;
    }

    public void setIsd_code(String isd_code) {
        this.isd_code = isd_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getHashed_id() {
        return hashed_id;
    }

    public void setHashed_id(String hashed_id) {
        this.hashed_id = hashed_id;
    }
    public FavorotesList(String id, String fpg_id, String created_at, String  product, String  type, String name,
            String  plus_code, String location, String photo, String isd_code, String phone, String std_code, String landline,
            String email, String website, String description, String price, String validity_start, String validity_end,
            String quality, String quantity, String offer, String attachment, String image, String hashed_id,String count,
                         String avarage, String value)
    {
       this.id = id;
        this. fpg_id= fpg_id;
        this. created_at= created_at;
        this.  product= product;
        this.  type= type;
        this. name= name;
        this.  plus_code= plus_code;
        this. location = location;
        this. photo= photo;
        this.isd_code= isd_code;
        this. phone= phone;
        this. std_code= std_code;
        this. landline= landline;
        this. email= email;
        this.website= website;
        this. description= description;
        this.price= price;
        this. validity_start= validity_start;
        this. validity_end= validity_end;
        this.quality= quality;
        this.quantity= quantity;
        this. offer= offer;
        this. attachment= attachment;
        this. image= image;
        this. hashed_id= hashed_id;
        this. count= count;
        this. avearage = avarage;
        this. value= value;

    }





}
