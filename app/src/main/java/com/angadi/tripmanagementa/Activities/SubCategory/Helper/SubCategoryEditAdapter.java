package com.angadi.tripmanagementa.Activities.SubCategory.Helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.angadi.tripmanagementa.Activities.Constant;
import com.angadi.tripmanagementa.Activities.database.DatabaseHelper;
import com.angadi.tripmanagementa.Activities.database.model.SubProduct;
import com.angadi.tripmanagementa.R;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Objects;

public class SubCategoryEditAdapter extends RecyclerView.Adapter<SubCategoryEditAdapter.ViewHolder> {
    public ArrayList<SubProduct> SubProductArrayList;
    private Context mContext;
    CardView card, card1;
    private DatabaseHelper db;
    ArrayList<SubProduct> SubProductLists = new ArrayList<SubProduct>();

    public SubCategoryEditAdapter(ArrayList<SubProduct> SubProductArrayList, Context mContext) {
        this.SubProductArrayList = SubProductArrayList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sub_cat_row_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
            db = new DatabaseHelper(mContext);
            Log.d("checkidds ","Adap : "+SubProductArrayList.get(i).getSp_id());
            Log.d("checkidds ","Adap : "+SubProductArrayList.get(i).getName());
            Log.d("checkidds ","Adap : "+SubProductArrayList.get(i).getPrice());
            Log.d("checkidds ","Adap : "+SubProductArrayList.get(i).getPhoto());
            Log.d("checkidds ","Adap : "+SubProductArrayList.get(i).getTime_date());
            viewHolder.cust_txt_product_name.setText(""+SubProductArrayList.get(i).getName());
            viewHolder.cust_txt_product_price.setText("Rs."+SubProductArrayList.get(i).getPrice());
            viewHolder.cust_txt_product_date_time.setText(""+Constant.formatDate(SubProductArrayList.get(i).getTime_date()));
            if(!Objects.equals(SubProductArrayList.get(i).getPhoto(), "")) {
                Glide.with(mContext).load(SubProductArrayList.get(i).getPhoto()).into(viewHolder.imageView1);
            } else {
                Glide.with(mContext).load(R.drawable.imageplaceholder).into(viewHolder.imageView1);
            }

    }

    @Override
    public int getItemCount() {
        System.out.println("checkidds : " + "Adap : " + SubProductArrayList.size());
        return SubProductArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextViewSFProDisplayRegular cust_txt_product_name;
        public TextViewSFProDisplayMedium cust_txt_product_date_time, cust_txt_product_edit, cust_txt_product_delete;
        public TextViewSFProDisplaySemibold cust_txt_product_price;
        public RoundedImageView imageView1;
        public ImageView cust_img_edit, cust_img_delete;

        public ViewHolder(View view) {
            super(view);
            cust_txt_product_name = (TextViewSFProDisplayRegular) view.findViewById(R.id.cust_txt_product_name);
            cust_txt_product_date_time = (TextViewSFProDisplayMedium) view.findViewById(R.id.cust_txt_product_date_time);
            cust_txt_product_edit = (TextViewSFProDisplayMedium) view.findViewById(R.id.cust_txt_product_edit);
            cust_txt_product_delete = (TextViewSFProDisplayMedium) view.findViewById(R.id.cust_txt_product_delete);
            cust_txt_product_price = (TextViewSFProDisplaySemibold) view.findViewById(R.id.cust_txt_product_price);
            imageView1 = (RoundedImageView) view.findViewById(R.id.imageView1);
            cust_img_edit = (ImageView) view.findViewById(R.id.cust_img_edit);
            cust_img_delete = (ImageView) view.findViewById(R.id.cust_img_delete);

            //imageView1.OnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            switch (view.getId()) {
                case R.id.imageView1:
                    if (position == 0) {

                    }
                    break;
            }
        }
    }

}