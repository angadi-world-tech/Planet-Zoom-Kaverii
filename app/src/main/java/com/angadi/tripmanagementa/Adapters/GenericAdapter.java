package com.angadi.tripmanagementa.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by alexey.herashchenko on 07.07.2015.
 */
public class GenericAdapter<T> extends ArrayAdapter<T> {

    protected LayoutInflater mInflater;

    public GenericAdapter(Context context, List<T> objects) {
        super(context, 0 , objects);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public GenericAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


}
