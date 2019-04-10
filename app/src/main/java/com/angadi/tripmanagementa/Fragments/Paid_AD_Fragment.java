package com.angadi.tripmanagementa.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.angadi.tripmanagementa.Activities.Real_estate;
import com.angadi.tripmanagementa.Adapters.ItemAdapter;
import com.angadi.tripmanagementa.SpaceDecoration;
import com.angadi.tripmanagementa.Model.Item;
import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

import static android.view.View.VISIBLE;


public class Paid_AD_Fragment extends Fragment {

    private LinearLayout LinLayoutBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_paid__ad_, container, false);
        Fabric.with(getActivity(), new Crashlytics());

        LinLayoutBack = view.findViewById(R.id.LinLayoutBack);

        LinLayoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Backpresses();
            }
        });
        return view;
    }

    private void Backpresses() {
//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame1, new Uploadadd_fragment()).commit();
        Intent intent= new Intent(getActivity(),Real_estate.class);
        startActivity(intent);
//        getActivity().overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up);

    }


}
/**
 * Adding few albums for testing
 */

/**
 * RecyclerView item decoration - give equal margin around grid item
 */
