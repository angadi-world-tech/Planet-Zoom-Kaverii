package com.angadi.tripmanagementa.Model;

/**
 * Created by alexey.herashchenko on 09.07.2015.
 */
public class SlideMenuItem {

    protected int mImage;
    protected int mImagePressed;
    protected String mTitle;

    public SlideMenuItem(int image, int imagePressed, String title) {
        this.mImage = image;
        this.mImagePressed = imagePressed;
        this.mTitle = title;
    }

    public int getItemImage() {
        return mImage;
    }

    public int getImagePressed() {
        return mImagePressed;
    }

    public String getItemTitle() {
        return mTitle;
    }
}
