package com.angadi.tripmanagementa.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.angadi.tripmanagementa.Adapters.ItemAdapter;
import com.angadi.tripmanagementa.SpaceDecoration;
import com.angadi.tripmanagementa.Model.Item;
import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;


public class Search_fragment extends Fragment {
    private final String android_version_names[] = {
            "Donut",
            "Eclair",
            "Froyo",
            "Gingerbread",
            "Honeycomb",
            "Ice Cream ",
            "Jelly Bean",
            "KitKat",
            "Lollipop",
            "Marshmallow",
            "Hotel","Shopping mall","Travel Trip","Mega Mart"
    };
    LinearLayout LinLayoutBack;
    private String TAG = "SignupFragment";


    private final String android_image_urls[] = {
            "http://api.learn2crack.com/android/images/donut.png",
            "http://api.learn2crack.com/android/images/eclair.png",
            "http://api.learn2crack.com/android/images/froyo.png",
            "http://api.learn2crack.com/android/images/ginger.png",
            "http://api.learn2crack.com/android/images/honey.png",
            "http://api.learn2crack.com/android/images/icecream.png",
            "http://api.learn2crack.com/android/images/jellybean.png",
            "http://api.learn2crack.com/android/images/kitkat.png",
            "http://api.learn2crack.com/android/images/lollipop.png",
            "http://api.learn2crack.com/android/images/marshmallow.png",
            "http://api.learn2crack.com/android/images/marshmallow.png",
            "http://api.learn2crack.com/android/images/marshmallow.png",
            "http://api.learn2crack.com/android/images/marshmallow.png",
            "http://api.learn2crack.com/android/images/marshmallow.png"




    };


    RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_fragment, container, false);

        Fabric.with(getActivity(), new Crashlytics());

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        LinLayoutBack = (LinearLayout)view.findViewById(R.id.LinLayoutBack);

        initViews();
        LinLayoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Backpresses();
            }
        });
        return view;
    }

    private void Backpresses() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame1, new Uploadadd_fragment()).commit();
        getActivity().overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up);

    }

    private void initViews(){
        recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
//        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        ArrayList<Item> androidVersions = prepareData();

        //get screen height
        int spaceInpixel=4;
        recyclerView.addItemDecoration(new SpaceDecoration(spaceInpixel));
        ItemAdapter adapter = new ItemAdapter(getActivity(),androidVersions);

        recyclerView.setAdapter(adapter);

    }
    private ArrayList<Item> prepareData(){

        ArrayList<Item> android_version = new ArrayList<>();
        for(int i=0;i<android_version_names.length;i++){
            Item androidVersion = new Item();
            androidVersion.setAndroid_version_name(android_version_names[i]);
            androidVersion.setAndroid_image_url(android_image_urls[i]);
            android_version.add(androidVersion);
        }
        return android_version;
    }
}
    /**
     * Adding few albums for testing
     */

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
