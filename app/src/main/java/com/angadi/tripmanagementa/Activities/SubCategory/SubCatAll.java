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
import com.angadi.tripmanagementa.Activities.database.DatabaseHelper;
import com.angadi.tripmanagementa.Activities.database.model.mSubCategory;
import com.angadi.tripmanagementa.R;

import java.util.ArrayList;
import java.util.List;

public class SubCatAll extends AppCompatActivity {

    RecyclerView verticalRecyclerView;
    LinearLayout verticalscroll;
    private DatabaseHelper db;
    String DbCount = "";
    SubCategoryListAllAdapter adapter;
    public static ArrayList<mSubCategory> mSubCategoryList = new ArrayList<mSubCategory>();

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

        DbCount = String.valueOf(db.getSubCategoryCount());
        Log.d("checkidd ","DB_Count : "+DbCount);

        if (DbCount.equals("0") || DbCount.equals("NULL") || DbCount.equals("")) {
            SubCatAll.this.finish();
            startActivity(new Intent(SubCatAll.this, PicturePreviewActivity.class));
        } else {
            subProduct();
        }

        verticalRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                verticalRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Constant.recyclerviewPosition = String.valueOf(position);
                Constant.mSubCategorysEd = mSubCategoryList.get(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }

        }));

    }

    public void onBackPressed() {
        super.onBackPressed();
        SubCatAll.this.finish();
        startActivity(new Intent(SubCatAll.this, PicturePreviewActivity.class));
    }

    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    //showNoteDialog(true, notesList.get(position), position);
                } else {
                    //deleteNote(position);
                }
            }
        });
        builder.show();
    }

    public void subProduct() {
        verticalRecyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(SubCatAll.this, 2);
        verticalRecyclerView.setLayoutManager(layoutManager);

        List<mSubCategory> mSubCategories = db.getAllSubCategory();
            mSubCategoryList.clear();
            for (mSubCategory mSubCategory : mSubCategories) {
                mSubCategory mSubCategorys = new mSubCategory();
                try {
                    System.out.println(mSubCategory.getId());
                    Log.d("checkidd",""+mSubCategory.getId());
                    mSubCategorys.setId(mSubCategory.getId());
                    mSubCategorys.setProduct_image(mSubCategory.getProduct_image());
                    mSubCategorys.setProduct_name(mSubCategory.getProduct_name());
                    mSubCategorys.setProduct_price(mSubCategory.getProduct_price());
                    mSubCategorys.setProduct_time_date(mSubCategory.getProduct_time_date());
                    Log.d("checkidd",""+mSubCategory.getId());
                    Log.d("checkidd",""+mSubCategory.getProduct_image());
                    Log.d("checkidd",""+mSubCategory.getProduct_name());
                    Log.d("checkidd",""+mSubCategory.getProduct_price());
                    Log.d("checkidd",""+mSubCategory.getProduct_time_date());
                    mSubCategoryList.add(mSubCategorys);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("checkidd ","Exception : "+e.getMessage());
                }
            }

            Log.d("checkidd ","ListSize : "+mSubCategoryList.size());

            adapter = new SubCategoryListAllAdapter(mSubCategoryList, getApplicationContext());
            verticalRecyclerView.setAdapter(adapter);
    }

}
