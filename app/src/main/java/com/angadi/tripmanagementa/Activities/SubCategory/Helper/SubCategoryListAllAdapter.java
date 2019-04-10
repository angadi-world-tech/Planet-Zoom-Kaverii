package com.angadi.tripmanagementa.Activities.SubCategory.Helper;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.angadi.tripmanagementa.Activities.Constant;
import com.angadi.tripmanagementa.Activities.PicturePreviewActivity;
import com.angadi.tripmanagementa.Activities.SubCategory.SubCatUpdate;
import com.angadi.tripmanagementa.Activities.database.DatabaseHelper;
import com.angadi.tripmanagementa.Activities.database.model.mSubCategory;
import com.angadi.tripmanagementa.R;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class SubCategoryListAllAdapter extends RecyclerView.Adapter<SubCategoryListAllAdapter.ViewHolder> {
    public ArrayList<mSubCategory> mSubCategoryArrayList;
    private Context mContext;
    CardView card, card1;
    private DatabaseHelper db;
    ArrayList<mSubCategory> mSubCategoryLists = new ArrayList<mSubCategory>();

    public SubCategoryListAllAdapter(ArrayList<mSubCategory> mSubCategoryArrayList, Context mContext) {
        this.mSubCategoryArrayList = mSubCategoryArrayList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sub_cat_ver_row_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
            db = new DatabaseHelper(mContext);

            Log.d("checkidds ","Adap : "+mSubCategoryArrayList.get(i).getId());
            Log.d("checkidds ","Adap : "+mSubCategoryArrayList.get(i).getProduct_image());
            Log.d("checkidds ","Adap : "+mSubCategoryArrayList.get(i).getProduct_name());
            Log.d("checkidds ","Adap : "+mSubCategoryArrayList.get(i).getProduct_price());
            Log.d("checkidds ","Adap : "+mSubCategoryArrayList.get(i).getProduct_time_date());
            viewHolder.cust_txt_product_name.setText(""+mSubCategoryArrayList.get(i).getProduct_name());
            viewHolder.cust_txt_product_price.setText("Rs."+mSubCategoryArrayList.get(i).getProduct_price());
            viewHolder.cust_txt_product_date_time.setText(""+mSubCategoryArrayList.get(i).getProduct_time_date());
            Glide.with(mContext).load(mSubCategoryArrayList.get(i).getProduct_image())
                    .into(viewHolder.imageView1);

            viewHolder.cust_img_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Product_Info = mSubCategoryArrayList.get(i).getId() + " - " + mSubCategoryArrayList.get(i).getProduct_image() +
                            " - " + mSubCategoryArrayList.get(i).getProduct_name() + " - " + mSubCategoryArrayList.get(i).getProduct_price() +
                            " - " + mSubCategoryArrayList.get(i).getProduct_time_date();
                    Log.d("AngadiWorldTech", "" + Product_Info);

                    Constant.productID = String.valueOf(mSubCategoryArrayList.get(i).getId());
                    Constant.productImage = mSubCategoryArrayList.get(i).getProduct_image();
                    Constant.productName = mSubCategoryArrayList.get(i).getProduct_name();
                    Constant.productPrice = mSubCategoryArrayList.get(i).getProduct_price();
                    Constant.productTimeDate = mSubCategoryArrayList.get(i).getProduct_time_date();

                    v.getRootView().getContext().startActivity(new
                            Intent(v.getRootView().getContext(), SubCatUpdate.class));
                }
            });

            viewHolder.cust_txt_product_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Product_Info = mSubCategoryArrayList.get(i).getId() + " - " + mSubCategoryArrayList.get(i).getProduct_image() +
                            " - " + mSubCategoryArrayList.get(i).getProduct_name() + " - " + mSubCategoryArrayList.get(i).getProduct_price() +
                            " - " + mSubCategoryArrayList.get(i).getProduct_time_date();
                    Log.d("AngadiWorldTech", "" + Product_Info);

                    Constant.productID = String.valueOf(mSubCategoryArrayList.get(i).getId());
                    Constant.productImage = mSubCategoryArrayList.get(i).getProduct_image();
                    Constant.productName = mSubCategoryArrayList.get(i).getProduct_name();
                    Constant.productPrice = mSubCategoryArrayList.get(i).getProduct_price();
                    Constant.productTimeDate = mSubCategoryArrayList.get(i).getProduct_time_date();

                    v.getRootView().getContext().startActivity(new
                            Intent(v.getRootView().getContext(), SubCatUpdate.class));
                }
            });

            viewHolder.cust_img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Product_Info = mSubCategoryArrayList.get(i).getId() + " - " + mSubCategoryArrayList.get(i).getProduct_image() +
                            " - " + mSubCategoryArrayList.get(i).getProduct_name() + " - " + mSubCategoryArrayList.get(i).getProduct_price() +
                            " - " + mSubCategoryArrayList.get(i).getProduct_time_date();
                    Log.d("AngadiWorldTech", "" + Product_Info);

                    db.deleteSubCategory(mSubCategoryArrayList.get(i));

                    // removing the note from the list
                    mSubCategoryArrayList.remove(i);
                    notifyDataSetChanged();

                    String DbCount = String.valueOf(db.getSubCategoryCount());

                    if (DbCount.equals("0") || DbCount.equals("NULL") || DbCount.equals("")) {
                        PicturePreviewActivity.horizontalscroll.setVisibility(View.GONE);
                        PicturePreviewActivity.viewAll.setVisibility(View.GONE);

                        // Clearing older images from cache directory
                        // don't call this line if you want to choose multiple images in the same activity
                        // call this once the bitmap(s) usage is over
                        ImagePickerActivity.clearCache(mContext);

                        db.del_SubCategory();
                    } else {
                        PicturePreviewActivity.viewAll.setText("View All ("+DbCount+")");
                    }
                }
            });

            viewHolder.cust_txt_product_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Product_Info = mSubCategoryArrayList.get(i).getId() + " - " + mSubCategoryArrayList.get(i).getProduct_image() +
                            " - " + mSubCategoryArrayList.get(i).getProduct_name() + " - " + mSubCategoryArrayList.get(i).getProduct_price() +
                            " - " + mSubCategoryArrayList.get(i).getProduct_time_date();
                    Log.d("AngadiWorldTech", "" + Product_Info);

                    db.deleteSubCategory(mSubCategoryArrayList.get(i));

                    // removing the note from the list
                    mSubCategoryArrayList.remove(i);
                    notifyDataSetChanged();

                    String DbCount = String.valueOf(db.getSubCategoryCount());

                    if (DbCount.equals("0") || DbCount.equals("NULL") || DbCount.equals("")) {
                        PicturePreviewActivity.horizontalscroll.setVisibility(View.GONE);
                        PicturePreviewActivity.viewAll.setVisibility(View.GONE);

                        // Clearing older images from cache directory
                        // don't call this line if you want to choose multiple images in the same activity
                        // call this once the bitmap(s) usage is over
                        ImagePickerActivity.clearCache(mContext);

                        db.del_SubCategory();
                    } else {
                        PicturePreviewActivity.viewAll.setText("View All ("+DbCount+")");
                    }
                }
            });

    }

    @Override
    public int getItemCount() {
        System.out.println("checkidds : " + "Adap : " + mSubCategoryArrayList.size());
        return mSubCategoryArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
      return position;
    }

    public long getItemID(int position) {
        return position;
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