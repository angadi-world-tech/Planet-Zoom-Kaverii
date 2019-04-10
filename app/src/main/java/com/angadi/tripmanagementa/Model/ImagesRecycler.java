package com.angadi.tripmanagementa.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImagesRecycler implements Serializable
{
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

   String image;






    public ImagesRecycler(String image)
    {
        this.image = image;
    }

    public ImagesRecycler(Parcel in)
    {
        image = in.readString();
    }




}
