package com.angadi.tripmanagementa.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.angadi.tripmanagementa.Adapters.ImageViewPageradapter;
import com.angadi.tripmanagementa.R;

public class ImagesViewPager extends Fragment
{


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.images_viewpager,container,false);




        return view;
    }
}
