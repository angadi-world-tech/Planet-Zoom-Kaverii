package com.angadi.tripmanagementa.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.angadi.tripmanagementa.Model.ImagesRecycler;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageViewPageradapterDum extends PagerAdapter {
    Context mContext;
    List<ImagesRecycler> imagesRecyclers;

    public ImageViewPageradapterDum(Context applicationContext, List<ImagesRecycler> imageList) {
        this.mContext = applicationContext;
        this.imagesRecyclers = imageList;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImagesRecycler imagesRecycler = imagesRecyclers.get(position);

        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Picasso.with(mContext).load(imagesRecycler.getImage()).into(imageView);

        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

    @Override
    public int getCount() {
        return imagesRecyclers.size();
    }
}
