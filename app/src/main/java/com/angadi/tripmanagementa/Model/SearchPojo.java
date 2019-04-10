package com.angadi.tripmanagementa.Model;

public class SearchPojo
{
    String id;
    String name;
    String lattitude;
    String longitude;
    String location;
    String pluscode;
    String photo;
    String distance;

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

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
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

    public String getPluscode() {
        return pluscode;
    }

    public void setPluscode(String pluscode) {
        this.pluscode = pluscode;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
    public SearchPojo(String id,String name,String lattitude,String longitude,String location,String pluscode,String photo,String distance)
    {
      this.id = id;
      this.name = name;
      this.lattitude = lattitude;
      this.longitude = longitude;
      this.location = location;
      this.pluscode = pluscode;
      this.photo = photo;
      this.distance = distance;
    }

}
