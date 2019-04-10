package com.angadi.tripmanagementa.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.angadi.tripmanagementa.Model.ImagesRecycler;
import com.angadi.tripmanagementa.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ImageViewPageradapter extends PagerAdapter
{

    Context context;

    LayoutInflater layoutInflater;

    String[] slide_descs ;



    public ImageViewPageradapter(Context context,String[] slide_descs)
    {
        this.context = context;
        this.slide_descs = slide_descs;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }





    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        View itemView = layoutInflater.inflate(R.layout.images_viewpager_adapterlayout, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.image);
       // imageView.setImageResource(slide_descs);

        Picasso.with(context).load(slide_descs.toString()).into(imageView);

        container.addView(itemView);

        //listening to image click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_LONG).show();
            }
        });

        return itemView;
    }

    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((LinearLayout)object);

    }



    @Override
    public int getCount()
    {
        return slide_descs.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }





}
