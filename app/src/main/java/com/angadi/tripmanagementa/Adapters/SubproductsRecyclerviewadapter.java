package com.angadi.tripmanagementa.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.angadi.tripmanagementa.Model.ImagesRecycler;
import com.angadi.tripmanagementa.Model.SubproductsRecyclerviewPojo;
import com.angadi.tripmanagementa.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import butterknife.BindView;

public class SubproductsRecyclerviewadapter extends RecyclerView.Adapter<SubproductsRecyclerviewadapter.MyViewHolder>
{

    private List<SubproductsRecyclerviewPojo> catogoryList;
    Context context;
    int countQuantity = 0;


    public SubproductsRecyclerviewadapter(List<SubproductsRecyclerviewPojo> list, Context context)
    {
        this.context = context;
        this.catogoryList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_subproducts_scanresult, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position)
    {
        final SubproductsRecyclerviewPojo imagesRecycler = catogoryList.get(position);
        for(int i=1;i<=catogoryList.size();i++){
            holder.TextviewSlnumber.setText(String.valueOf(position+1));
        }


        holder.TextviewProductName.setText(imagesRecycler.getName());
        Log.e("Name",imagesRecycler.getName());


        holder.Textviewprice.setText(imagesRecycler.getPrice());
    }

    @Override
    public int getItemCount() {
        return catogoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView TextviewSlnumber,TextviewProductName,Textviewprice,TextviewSubTotal;

        EditText Edittextproductcount;

        public MyViewHolder(View view)
        {
            super(view);

            TextviewSlnumber = (TextView) view.findViewById(R.id.TextviewSlnumber);
            TextviewProductName = (TextView) view.findViewById(R.id.TextviewProductName);
            Textviewprice = (TextView) view.findViewById(R.id.Textviewprice);
            TextviewSubTotal = (TextView) view.findViewById(R.id.TextviewSubTotal);
            Edittextproductcount = (EditText) view.findViewById(R.id.Edittextproductcount);



        }
    }





}
