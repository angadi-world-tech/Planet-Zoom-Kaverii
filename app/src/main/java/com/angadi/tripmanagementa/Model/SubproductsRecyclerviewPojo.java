package com.angadi.tripmanagementa.Model;

public class SubproductsRecyclerviewPojo
{
    String id = "id";
    String name = "name";
    String price = "proce";
    String photo = "photo";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo)
    {
        this.photo = photo;
    }

    public SubproductsRecyclerviewPojo(String subproductID,String subproductname,String subproductprice,String subproductphoto)
    {
        this.id = subproductID;
        this.name = subproductname;
        this.price = subproductprice;
        this.photo = subproductphoto;
    }


}
