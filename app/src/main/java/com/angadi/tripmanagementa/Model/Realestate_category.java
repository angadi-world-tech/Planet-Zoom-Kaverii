package com.angadi.tripmanagementa.Model;

public class Realestate_category {

    private String category_name;
    private  int flag;


    public Realestate_category(int flag , String category_name) {
        this.category_name = category_name;
        this.flag = flag;

    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
