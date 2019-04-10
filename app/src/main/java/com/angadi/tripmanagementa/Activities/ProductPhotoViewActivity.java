package com.angadi.tripmanagementa.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.angadi.tripmanagementa.R;
import com.squareup.picasso.Picasso;

public class ProductPhotoViewActivity extends AppCompatActivity
{
    ImageView image;
    RelativeLayout LinlayoutBackPhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_photo_view);
        image = (ImageView)findViewById(R.id.image);
        LinlayoutBackPhoto = (RelativeLayout)findViewById(R.id.LinlayoutBackPhoto);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window =this. getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));
        }


        String photo = getIntent().getStringExtra("Photo");

        Picasso.with(this).load(photo).into(image);

        Log.e("jhjhjh",photo);

        LinlayoutBackPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                onBackPressed();
            }
        });


    }
}
