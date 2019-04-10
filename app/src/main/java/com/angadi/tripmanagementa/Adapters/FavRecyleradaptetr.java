package com.angadi.tripmanagementa.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.Model.Groups;
import com.angadi.tripmanagementa.R;

import java.util.List;

public class FavRecyleradaptetr extends  RecyclerView.Adapter<FavRecyleradaptetr.MyViewHolder>
{

    private List<Groups> catogoryList;
    String productId,fpgID;
    Context context;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_recyclerview_dapter_layout, parent, false);
        return new MyViewHolder(itemView);
    }


    public FavRecyleradaptetr(List<Groups> catogoryList, Context context, String id, String fpg_id) {
        this.catogoryList = catogoryList;
        this.context = context;
        this.productId = id;
        this.fpgID  = fpg_id;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position)
    {
        final Groups groups = catogoryList.get(position);
        holder.CatogoryName.setText(groups.getName());
        holder.Linlayoutcatogory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (TripManagement.readValueFromPreferences(context,"FPG","").equals(groups.getId()))
                {
                   Toast.makeText(context, groups.getName(), Toast.LENGTH_SHORT).show();
                }
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
        public TextView CatogoryName;
        LinearLayout Linlayoutcatogory;

        public MyViewHolder(View view)
        {
            super(view);
            CatogoryName = (TextView)view.findViewById(R.id.ProductName);
            Linlayoutcatogory = (LinearLayout)view.findViewById(R.id.Linlayoutcatogory);
            Typeface montserrat_regular = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Regular.ttf");
            CatogoryName.setTypeface(montserrat_regular);


        }
    }




}
