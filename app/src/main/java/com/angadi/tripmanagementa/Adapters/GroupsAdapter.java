package com.angadi.tripmanagementa.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.angadi.tripmanagementa.Model.Groups;
import com.angadi.tripmanagementa.R;

import java.util.ArrayList;

public class GroupsAdapter extends BaseAdapter
{

    ArrayList<Groups> GroupsList;
    Context context;
    LayoutInflater layoutInflater;

    String id,name;


    public GroupsAdapter(Context context,ArrayList<Groups> GroupsList,  String id, String name)
    {
        this. context = context;
        this.GroupsList = GroupsList;
        this.id = id;
        this.name = name;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return GroupsList.size();
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
        view = layoutInflater.inflate(R.layout.groups_dapter_layout, null);

        TextView ProductName = (TextView)view.findViewById(R.id.ProductName);
        ProductName.setText(GroupsList.get(i).getName());

        return view;
    }
}
