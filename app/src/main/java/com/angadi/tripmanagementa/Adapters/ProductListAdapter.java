package com.angadi.tripmanagementa.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.angadi.tripmanagementa.Model.Product;
import com.angadi.tripmanagementa.R;

import java.util.ArrayList;

public class ProductListAdapter extends BaseAdapter
{
    ArrayList<Product> pojo_listArrayList;
    Context context;
    LayoutInflater layoutInflater;
    String name, description,phone,website,validitystart,
            quality,price,quantity,email,location,photo,hashed_id,validity_end,stdCode,landline,createdAt,offer,id,attachemnt;



    public ProductListAdapter(Context context, ArrayList<Product> pojo_listArrayList, String id,String name,String phone,String email, String createdAt, String location,
                              String std_code, String landline, String website,String description, String price,  String validitystart,
                              String validityend, String quality,String quantity, String offer, String photo,String hashed_id,String attachment,String product)
    {
        this.context = context;
        this.pojo_listArrayList = pojo_listArrayList;
        this.layoutInflater = LayoutInflater.from(context);
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.createdAt = createdAt;
        this.location = location;
        this.stdCode = std_code;
        this.landline = landline;
        this.website = website;
        this.description = description;
        this.price = price;
        this.validitystart = validitystart;
        this.validity_end = validityend;
        this.quality = quality;
        this.quantity = quantity;
        this.offer = offer;
        this.photo = photo;
        this.hashed_id = hashed_id;
        this.attachemnt = attachment;
        this.hashed_id = product;
        }


    @Override
    public int getCount()
    {
        return pojo_listArrayList.size();
    }

    @Override
    public Object getItem(int i)
    {
        return i;
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        view = layoutInflater.inflate(R.layout.layout_products_item, null);
        TextView   TextViewproductName = (TextView)view.findViewById(R.id.ProductName);

        Typeface montserrat_regular = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Regular.ttf");


        TextViewproductName.setTypeface(montserrat_regular);

        if (pojo_listArrayList.get(i).getName()!=null)
        {
            TextViewproductName.setText(pojo_listArrayList.get(i).getName());
        }
       // Toast.makeText(context, pojo_listArrayList.get(i).getName(), Toast.LENGTH_SHORT).show();

        return view;
    }
}

