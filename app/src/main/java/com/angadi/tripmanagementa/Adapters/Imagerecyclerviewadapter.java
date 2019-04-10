package com.angadi.tripmanagementa.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Picture;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.angadi.tripmanagementa.Activities.Imagesactivity;
import com.angadi.tripmanagementa.Activities.Qrcoderesult;
import com.angadi.tripmanagementa.Model.ImagesRecycler;
import com.angadi.tripmanagementa.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.intercom.android.sdk.blocks.models.Image;

public class Imagerecyclerviewadapter  extends RecyclerView.Adapter<Imagerecyclerviewadapter.MyViewHolder>
{

    private List<ImagesRecycler> catogoryList;
    private ImageView[] imageViews;

    String image;
    Context context;
    ViewPager vp;
    Dialog dialog_morque;



    public Imagerecyclerviewadapter(List<ImagesRecycler> list, Context context)
    {
        this.context = context;
        this.catogoryList = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_recycler, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position)
    {
      final  ImagesRecycler imagesRecycler = catogoryList.get(position);





        Picasso.with(context).load(imagesRecycler.getImage()).into(holder.imageView);

        final ArrayList<String> arrayimage = new ArrayList<>();
        arrayimage.add(imagesRecycler.getImage());

        Log.e("Arraysize", String.valueOf(catogoryList.size()));
        Log.e("Image", imagesRecycler.getImage());
        Log.e("arrayimage", arrayimage.toString());

        final String[] aa = arrayimage.toArray(new String[0]);

        dialog_morque = new Dialog(context);


        holder.imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(context,Imagesactivity.class);
                intent.putExtra("i",imagesRecycler.getImage());
                intent.putExtra("position",position);
                context.startActivity(intent);



            }
        });
    }

    @Override
    public int getItemCount()
    {
        return catogoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        ImageView imageView;
        public MyViewHolder(View view)
        {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image);
           // context = view.getContext();
        }
    }

    public void showImage(String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url) && url.length() > 0) {
            url = url.replaceAll(" ", "");
        }
        if (TextUtils.isEmpty(url)) {
            return;
        }
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).load(url)
                .into(imageView);
//        Picasso.with(this).load(url)
//                .error(R.mipmap.ic_loding_error)
//                .into(imageView);
    }

    public int[] convertStringArraytoIntArray(String[] sarray) throws Exception
    {
        if (sarray != null)
        {
            int intarray[] = new int[sarray.length];
            for (int i = 0; i < sarray.length; i++)
            {
                intarray[i] = Integer.parseInt(sarray[i]);

            }
            return intarray;
        } return null;
    }



}
