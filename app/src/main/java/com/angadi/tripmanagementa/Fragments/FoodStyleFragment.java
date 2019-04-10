package com.angadi.tripmanagementa.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class FoodStyleFragment extends Fragment
{
    private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static FoodStyleFragment newInstance(int page, String title)
    {
        FoodStyleFragment fragmentFirst = new FoodStyleFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_breakfast,container,false);
        Fabric.with(getActivity(), new Crashlytics());
        return view;
    }
}
