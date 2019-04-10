package com.angadi.tripmanagementa.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.angadi.tripmanagementa.Model.Orders;
import com.angadi.tripmanagementa.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrdersAdapter extends BaseAdapter
{
    ArrayList<Orders> ordersArrayList;
    Context context;
    LayoutInflater layoutInflater;
    TextView ProductNameTextview,ProductOrderIdTextview,ProductQtyTextview,ProductPriceTextview;
    ImageView ProductImage;
    Typeface montserrat_regular,montserrat_bold;
    String  id, product_id, status, name, photo, latitude, longitude, location, sub_total, gst, tax, created_at;

    public OrdersAdapter(Context context, ArrayList<Orders> ordersArrayList,String  id, String product_id, String status,String  name, String photo,String  latitude,String  longitude,String  location, String sub_total, String gst, String tax,String  created_at)
    {
        this.context = context;
        this.ordersArrayList = ordersArrayList;
        layoutInflater = LayoutInflater.from(context);
        this.id = id;
        this.product_id = product_id;
        this.status = status;
        this.name = name;
        this.photo = photo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.sub_total = sub_total;
        this.gst = gst;
        this.tax = tax;
        this.created_at = created_at;

    }

    @Override
    public int getCount()
    {
        return ordersArrayList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
       View view = layoutInflater.inflate(R.layout.orders_adapter_layout, null);
       montserrat_regular = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Regular.ttf");
        montserrat_bold = Typeface.createFromAsset(context.getAssets(), "fonts/MONTSERRAT-BOLD.OTF");
        ProductImage = (ImageView)view.findViewById(R.id.ProductImage);
        ProductOrderIdTextview = (TextView) view.findViewById(R.id.ProductOrderIdTextview);
        ProductNameTextview = (TextView)view.findViewById(R.id.ProductNameTextview);
        ProductQtyTextview = (TextView)view.findViewById(R.id.ProductQtyTextview);
        ProductPriceTextview = (TextView)view.findViewById(R.id.ProductPriceTextview);

        ProductNameTextview.setTypeface(montserrat_bold);
        ProductOrderIdTextview.setTypeface(montserrat_regular);
        ProductQtyTextview.setTypeface(montserrat_regular);
        ProductPriceTextview.setTypeface(montserrat_regular);

        ProductNameTextview.setText(ordersArrayList.get(position).getName());
        ProductOrderIdTextview.setText("Order Id: "+ordersArrayList.get(position).getId());
        ProductPriceTextview.setText("Price: "+ordersArrayList.get(position).getSub_total());
        ProductQtyTextview.setText("Qty:1");
        Picasso.with(context).load(ordersArrayList.get(position).getPhoto()).into(ProductImage);

        return view;
    }
}
