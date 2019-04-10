package com.angadi.tripmanagementa.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.angadi.tripmanagementa.Model.Item;
import com.angadi.tripmanagementa.R;

import java.util.ArrayList;



public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private ArrayList<Item> android;
    private Context context;



    public ItemAdapter(Context context, ArrayList<Item> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_item_design, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
//
        viewHolder.tv_android1.setText(android.get(i).getAndroid_version_name());
//        viewHolder.textView6.setText(android.get(i).getAndroid_version_name());
      //  viewHolder.textView1.setText(android.get(i).getAndroid_version_name());
        viewHolder.textView3.setText(android.get(i).getAndroid_version_name());
        viewHolder.textView4.setText(android.get(i).getAndroid_version_name());
        viewHolder.namehotel.setText("How to Develop a Turn-by-Turn Navigation App Like Waze. 5588 views. Kirill S. Ruby/JS ... This article will cover all the features and niceties of Waze and tell you how to create a map app like Waze. .... To implement social logins, use tools such as Facebook SDK and Twitter Kit.");

//        viewHolder.imageview2.setImageResource(R.drawable.mike_icon);
        viewHolder.img_android.setImageResource(R.mipmap.restaurant_gray);
        viewHolder.textviewchat.setImageResource(R.drawable.chat_icon);/*select a picture from drawable*/
       // Glide.with(context).load(android.get(i).getAndroid_image_url()).into(viewHolder.img_android);
//        Glide.with(context).load(android.get(i).getAndroid_image_url()).into(viewHolder.imageview2);


       viewHolder.ratingbar.setRating(3.67f);
        Log.e("link",""+android.get(i).getAndroid_image_url());
    }

    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_android,tv_android1,textView1,textView3,textView4,textView6,namehotel;
        private ImageView img_android,textviewchat;
        RatingBar ratingbar ;
        public ViewHolder(View view) {
            super(view);





            tv_android1 = (TextView)view.findViewById(R.id.title1);
           // textView1=(TextView)view.findViewById(R.id.textView1);
            textView3=(TextView)view.findViewById(R.id.textView3);
            textView4=(TextView)view.findViewById(R.id.textView4);
//            textView6=(TextView)view.findViewById(R.id.textView6);
            ratingbar = (RatingBar) view.findViewById(R.id.ratingbar);
            namehotel=(TextView)view.findViewById(R.id.namehotel);


            img_android = (ImageView) view.findViewById(R.id.thumbnail);
            textviewchat=(ImageView)view.findViewById(R.id.textviewchat);
        }
    }

}
