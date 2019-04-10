package com.angadi.tripmanagementa.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.angadi.tripmanagementa.Model.Realestate_category;
import com.angadi.tripmanagementa.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Realestate_category_Adapter  extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Realestate_category> worldpopulationlist = null;
    private ArrayList<Realestate_category> arraylist;

    public Realestate_category_Adapter(Context context,
                                       List<Realestate_category> worldpopulationlist) {
        mContext = context;
        this.worldpopulationlist = worldpopulationlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Realestate_category>();
        this.arraylist.addAll(worldpopulationlist);
    }

    public class ViewHolder {
        TextView category;
        ImageView flag;
    }


    @Override
    public int getCount() {
        return worldpopulationlist.size();
    }

    @Override
    public Realestate_category getItem(int position) {
        return worldpopulationlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.realestate_listview_category_item, null);
            // Locate the TextViews in listview_item.xml
            holder.category = (TextView) view.findViewById(R.id.category);

            // Locate the ImageView in listview_item.xml
            holder.flag = (ImageView) view.findViewById(R.id.flag);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.category.setText(worldpopulationlist.get(position).getCategory_name());

        // Set the results into ImageView
        holder.flag.setImageResource(worldpopulationlist.get(position)
                .getFlag());
        // Listen for ListView Item Click
//        view.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // Send single item click data to SingleItemView Class
//                Intent intent = new Intent(mContext, SingleItemView.class);
//                // Pass all data rank
//                intent.putExtra("rank",
//                        (worldpopulationlist.get(position).getRank()));
//                // Pass all data country
//                intent.putExtra("country",
//                        (worldpopulationlist.get(position).getCountry()));
//                // Pass all data population
//                intent.putExtra("population",
//                        (worldpopulationlist.get(position).getPopulation()));
//                // Pass all data flag
//                intent.putExtra("flag",
//                        (worldpopulationlist.get(position).getFlag()));
//                // Start SingleItemView Class
//                mContext.startActivity(intent);
//            }
//        });

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        worldpopulationlist.clear();
        if (charText.length() == 0) {
            worldpopulationlist.addAll(arraylist);
        } else {
            for (Realestate_category wp : arraylist) {
                if (wp.getCategory_name().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    worldpopulationlist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
