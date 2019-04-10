package com.angadi.tripmanagementa.Model;

public class Groups
{
    String id;
    String name;

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




    public Groups(String id,String name)
    {
        this.id = id;
        this.name = name;
    }


}
