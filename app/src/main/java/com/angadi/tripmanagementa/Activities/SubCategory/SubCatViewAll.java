package com.angadi.tripmanagementa.Activities.SubCategory;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.angadi.tripmanagementa.Activities.Constant;
import com.angadi.tripmanagementa.Activities.PicturePreviewActivity;
import com.angadi.tripmanagementa.Activities.SubCategory.Helper.RecyclerTouchListener;
import com.angadi.tripmanagementa.Activities.SubCategory.Helper.SubCategoryListAllAdapter;
import com.angadi.tripmanagementa.Activities.SubCategory.Helper.SubCategoryViewAllAdapter;
import com.angadi.tripmanagementa.Activities.database.DatabaseHelper;
import com.angadi.tripmanagementa.Activities.database.model.SubProduct;
import com.angadi.tripmanagementa.Activities.database.model.mSubCategory;
import com.angadi.tripmanagementa.R;

import java.util.ArrayList;
import java.util.List;

public class SubCatViewAll extends AppCompatActivity {

    RecyclerView verticalRecyclerView;
    LinearLayout verticalscroll;
    private DatabaseHelper db;
    String DbCount = "";
    SubCategoryViewAllAdapter adapter;
    ArrayList<SubProduct> mSubProductList = new ArrayList<SubProduct>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_cat_all);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        verticalRecyclerView = (RecyclerView)findViewById(R.id.verticalRecyclerView);
        verticalscroll = (LinearLayout)findViewById(R.id.verticalscroll);

        db = new DatabaseHelper(this);

        subProduct();

    }

    public void onBackPressed() {
        super.onBackPressed();
        SubCatViewAll.this.finish();
    }


    public void subProduct() {
        verticalRecyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(SubCatViewAll.this, 2);
        verticalRecyclerView.setLayoutManager(layoutManager);

        List<SubProduct> mSubProduct = db.getSubProduct_SB_PACN(Integer.parseInt(Constant.getDashboardProductPosition));
        mSubProductList.clear();
        for (SubProduct mSubProducts : mSubProduct) {
            SubProduct msbSubProducts = new SubProduct();
            try {
                msbSubProducts.setId(mSubProducts.getId());
                msbSubProducts.setProduct_array_count(mSubProducts.getProduct_array_count());
                msbSubProducts.setProduct_array_count_now(mSubProducts.getProduct_array_count_now());
                msbSubProducts.setSub_product_array_count(mSubProducts.getSub_product_array_count());
                msbSubProducts.setSub_product_array_count_now(mSubProducts.getSub_product_array_count_now());
                msbSubProducts.setSp_id(mSubProducts.getSp_id());
                msbSubProducts.setName(mSubProducts.getName());
                msbSubProducts.setPrice(mSubProducts.getPrice());
                msbSubProducts.setPhoto(mSubProducts.getPhoto());
                msbSubProducts.setTime_date(mSubProducts.getTime_date());

                Log.d("checkidd : "," Sub Products : "+msbSubProducts.getId());
                Log.d("checkidd : "," Sub Products : "+msbSubProducts.getProduct_array_count());
                Log.d("checkidd : "," Sub Products : "+msbSubProducts.getProduct_array_count_now());
                Log.d("checkidd : "," Sub Products : "+msbSubProducts.getSub_product_array_count());
                Log.d("checkidd : "," Sub Products : "+msbSubProducts.getSub_product_array_count_now());

                mSubProductList.add(msbSubProducts);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("checkidd ","Exception : "+e.getMessage());
            }
        }

            Log.d("checkidd ","ListSize : "+mSubProductList.size());

            adapter = new SubCategoryViewAllAdapter(mSubProductList, getApplicationContext());
            verticalRecyclerView.setAdapter(adapter);
    }

}
