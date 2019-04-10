package com.angadi.tripmanagementa.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.angadi.tripmanagementa.Adapters.ImageViewPageradapter;
import com.angadi.tripmanagementa.Adapters.ImageViewPageradapterDum;
import com.angadi.tripmanagementa.Model.ImagesRecycler;
import com.angadi.tripmanagementa.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class Imagesactivity extends AppCompatActivity
{

    LinearLayout LinlayoutBack;
    ViewPager vpPager;

    ImageView imageview;
    List list;
   // ImageViewPageradapter viewPageradapter;
   ImageViewPageradapterDum viewPageradapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.images_viewpager);

        final int position = getIntent().getIntExtra("position",0);
        Log.e("POS", String.valueOf(position));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window =this. getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));
        }


        imageview = (ImageView)findViewById(R.id.imageview);


        LinlayoutBack = (LinearLayout)findViewById(R.id.LinlayoutBack);
        vpPager = (ViewPager)findViewById(R.id.vpPager);


        String ii = getIntent().getStringExtra("i");

        //imageview.setImageResource(StringToBitMap(ii));

        Picasso.with(this).load(ii).into(imageview);

        viewPageradapter = new ImageViewPageradapterDum(Imagesactivity.this, Qrcoderesult.imageList);
        vpPager.setVisibility(View.GONE);


        Log.e("iiii",ii);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                vpPager.setAdapter(viewPageradapter);
                vpPager.setCurrentItem(position);
                vpPager.setVisibility(View.VISIBLE);

            }
        },1000);




       // viewPageradapter = new ImageViewPageradapter(this,list);
       // vpPager.setAdapter(viewPageradapter);


        LinlayoutBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
              onBackPressed();
            }
        });


//        Intent intent = getIntent();
//
//        String jsonArray = null;
//        if (intent!=null)
//        {
//             jsonArray = intent.getStringExtra("jsonArray");
//             JSONArray jsonArray1 = null;
//
//            try {
//                jsonArray1 = new JSONArray(jsonArray);
//                String[] strArr = new String[jsonArray1.length()];
//
//                for (int i = 0; i < jsonArray1.length(); i++)
//                {
//                    strArr[i] = jsonArray1.getString(i);
//
//                    processLine(strArr);
//                }
//
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            Log.e("arra123",jsonArray);
//        }

        }

    private void processLine(String[] strings)
    {
        Integer[] intarray=new Integer[strings.length];
        int i=0;
        for(String str:strings)
        {
            try {
                intarray[i]=Integer.parseInt(str);
                i++;


            } catch (NumberFormatException e)
            {
                throw new IllegalArgumentException("Not a number: " + str + " at index " + i, e);
            }


        }
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}
