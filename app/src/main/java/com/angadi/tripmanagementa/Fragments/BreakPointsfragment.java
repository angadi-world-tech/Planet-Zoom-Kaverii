package com.angadi.tripmanagementa.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.angadi.tripmanagementa.Activities.HomeActivity;
import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.Model.MyPagerAdapter;
import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

public class BreakPointsfragment extends Fragment
{
    MyPagerAdapter ViewPageradapter;

    @BindView(R.id.LinLayoutBack) LinearLayout LinLayoutBack;
    @BindView(R.id.LinlayoutPlanetZoom) LinearLayout LinlayoutPlanetZoom;
    @BindView(R.id.LinLayoutChat) LinearLayout LinLayoutChat;
    @BindView(R.id.vpPager) ViewPager vpPager;
    @BindView(R.id.tablayout) TabLayout tablayout;
    @BindView(R.id.LinlayoutFood) LinearLayout LinlayoutFood;
    @BindView(R.id.TextviewFood) TextView TextviewFood;
    @BindView(R.id.ImageviewFood) ImageView ImageviewFood;
    @BindView(R.id.LinLayoutRestaurants) LinearLayout LinLayoutRestaurants;
    @BindView(R.id.TextViewResturants) TextView TextViewResturants;
    @BindView(R.id.ImageviewResturants) ImageView ImageviewResturants;
    @BindView(R.id.LinlayoutHotel) LinearLayout LinlayoutHotel;
    @BindView(R.id.TextviewHotels) TextView TextviewHotels;
    @BindView(R.id.ImageviewHotels) ImageView ImageviewHotels;
    @BindView(R.id.LinlayoutNearBy) LinearLayout LinlayoutNearBy;
    @BindView(R.id.TextViewNearby) TextView TextViewNearby;
    @BindView(R.id.ImageViewNearby) ImageView ImageViewNearby;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_breakpoints, container, false);
        ButterKnife.bind(this, view);
        Fabric.with(getActivity(), new Crashlytics());
        ViewPageradapter = new MyPagerAdapter(getChildFragmentManager());
        vpPager.setAdapter(ViewPageradapter);
        vpPager.setOffscreenPageLimit(4);
        tablayout.setupWithViewPager(vpPager);


        TripManagement.saveValueuToPreferences(getActivity(),"BreakFastFragment","BreakFastFragment");




        TagIntilization();


        return view;
    }

    @OnClick(R.id.LinLayoutBack)
    public void setLinLayoutBack()
    {

        LocationSearchFragment locationSearchFragment = new LocationSearchFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_content,locationSearchFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.LinlayoutPlanetZoom)
    public void setLinlayoutPlanetZoom()
    {
        startActivity(new Intent(getActivity(), HomeActivity.class));
    }

    @OnClick(R.id.LinLayoutChat)
    public void setLinLayoutChat()
    {
        ChatListFragment chatListFragment = new ChatListFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_content,chatListFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.LinlayoutFood)
    public void setLinlayoutFood()
    {
        Log.d("hhhhhh", String.valueOf(LinlayoutFood.getTag()));
        if (LinlayoutFood.getTag()=="TagFoodGreen")
        {
            LinlayoutFood.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
            TextviewFood.setTextColor(getActivity().getResources().getColor(R.color.orange));
            ImageviewFood.setImageResource(R.mipmap.food_orange);
            LinlayoutFood.setTag("TagFoodOrange");
        }
        else if (LinlayoutFood.getTag()=="TagFoodOrange")
        {

            LinlayoutFood.setBackgroundResource(R.drawable.green_layout_border);
            TextviewFood.setTextColor(getActivity().getResources().getColor(R.color.white));
            ImageviewFood.setImageResource(R.mipmap.food_white_icon);
            LinlayoutFood.setTag("TagFoodGreen");

            if (LinLayoutRestaurants.getTag()=="TagRestuarantsGreen")
            {
                LinLayoutRestaurants.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
                TextViewResturants.setTextColor(getActivity().getResources().getColor(R.color.orange));
                ImageviewResturants.setImageResource(R.mipmap.restaurant_orange_icon);
                LinLayoutRestaurants.setTag("TagRestuarantsOrange");
            }

            if (LinlayoutHotel.getTag()=="TagHotelsGreen")
            {
                LinlayoutHotel.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
                TextviewHotels.setTextColor(getActivity().getResources().getColor(R.color.orange));
                ImageviewHotels.setImageResource(R.mipmap.hotels_orange_icon);
                LinlayoutHotel.setTag("TagHotelsOrange");

            }

            if (LinlayoutNearBy.getTag()=="TagNearByGreen")
            {
                LinlayoutNearBy.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
                TextViewNearby.setTextColor(getActivity().getResources().getColor(R.color.orange));
                ImageViewNearby.setImageResource(R.mipmap.nearby_orange_icon);
                LinlayoutNearBy.setTag("TagNearByOrange");

            }

        }

    }

    @OnClick(R.id.LinLayoutRestaurants)
    public void setLinLayoutRestaurants()
    {
        if (LinLayoutRestaurants.getTag()=="TagRestuarantsGreen")
        {

            LinLayoutRestaurants.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
            TextViewResturants.setTextColor(getActivity().getResources().getColor(R.color.orange));
            ImageviewResturants.setImageResource(R.mipmap.restaurant_orange_icon);
            LinLayoutRestaurants.setTag("TagRestuarantsOrange");



        }
        else if (LinLayoutRestaurants.getTag()=="TagRestuarantsOrange")
        {
            LinLayoutRestaurants.setBackgroundResource(R.drawable.green_layout_border);
            TextViewResturants.setTextColor(getActivity().getResources().getColor(R.color.white));
            ImageviewResturants.setImageResource(R.mipmap.restaurant_white_icon);
            LinLayoutRestaurants.setTag("TagRestuarantsGreen");

            if (LinlayoutFood.getTag()=="TagFoodGreen")
            {
                LinlayoutFood.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
                TextviewFood.setTextColor(getActivity().getResources().getColor(R.color.orange));
                ImageviewFood.setImageResource(R.mipmap.food_orange);
                LinlayoutFood.setTag("TagFoodOrange");
            }

            if (LinlayoutHotel.getTag()=="TagHotelsGreen")
            {
                LinlayoutHotel.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
                TextviewHotels.setTextColor(getActivity().getResources().getColor(R.color.orange));
                ImageviewHotels.setImageResource(R.mipmap.hotels_orange_icon);
                LinlayoutHotel.setTag("TagHotelsOrange");

            }

            if (LinlayoutNearBy.getTag()=="TagNearByGreen")
            {
                LinlayoutNearBy.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
                TextViewNearby.setTextColor(getActivity().getResources().getColor(R.color.orange));
                ImageViewNearby.setImageResource(R.mipmap.nearby_orange_icon);
                LinlayoutNearBy.setTag("TagNearByOrange");

            }
        }
    }


    @OnClick(R.id.LinlayoutHotel)
    public void setLinlayoutHotel()
    {
        if (LinlayoutHotel.getTag()=="TagHotelsGreen")
        {
            LinlayoutHotel.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
            TextviewHotels.setTextColor(getActivity().getResources().getColor(R.color.orange));
            ImageviewHotels.setImageResource(R.mipmap.hotels_orange_icon);
            LinlayoutHotel.setTag("TagHotelsOrange");

        }
        else if (LinlayoutHotel.getTag()=="TagHotelsOrange")
        {

            LinlayoutHotel.setBackgroundResource(R.drawable.green_layout_border);
            TextviewHotels.setTextColor(getActivity().getResources().getColor(R.color.white));
            ImageviewHotels.setImageResource(R.mipmap.hotels_white_icon);
            LinlayoutHotel.setTag("TagHotelsGreen");

            if (LinlayoutFood.getTag()=="TagFoodGreen")
            {
                LinlayoutFood.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
                TextviewFood.setTextColor(getActivity().getResources().getColor(R.color.orange));
                ImageviewFood.setImageResource(R.mipmap.food_orange);
                LinlayoutFood.setTag("TagFoodOrange");
            }

            else if (LinLayoutRestaurants.getTag()=="TagRestuarantsGreen")
            {
                LinLayoutRestaurants.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
                TextViewResturants.setTextColor(getActivity().getResources().getColor(R.color.orange));
                ImageviewResturants.setImageResource(R.mipmap.restaurant_orange_icon);
                LinLayoutRestaurants.setTag("TagRestuarantsOrange");
            }
           else if (LinlayoutNearBy.getTag()=="TagNearByGreen")
            {
                LinlayoutNearBy.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
                TextViewNearby.setTextColor(getActivity().getResources().getColor(R.color.orange));
                ImageViewNearby.setImageResource(R.mipmap.nearby_orange_icon);
                LinlayoutNearBy.setTag("TagNearByOrange");

            }
        }


    }

    @OnClick(R.id.LinlayoutNearBy)
    public void setLinlayoutNearBy()
    {
        if (LinlayoutNearBy.getTag()=="TagNearByGreen")
        {
            LinlayoutNearBy.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
            TextViewNearby.setTextColor(getActivity().getResources().getColor(R.color.orange));
            ImageViewNearby.setImageResource(R.mipmap.nearby_orange_icon);
            LinlayoutNearBy.setTag("TagNearByOrange");

        }
        else if (LinlayoutNearBy.getTag()=="TagNearByOrange")
        {

            LinlayoutNearBy.setBackgroundResource(R.drawable.green_layout_border);
            TextViewNearby.setTextColor(getActivity().getResources().getColor(R.color.white));
            ImageViewNearby.setImageResource(R.mipmap.nearby_white_icon);
            LinlayoutNearBy.setTag("TagNearByGreen");

            if (LinlayoutFood.getTag()=="TagFoodGreen")
            {
                LinlayoutFood.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
                TextviewFood.setTextColor(getActivity().getResources().getColor(R.color.orange));
                ImageviewFood.setImageResource(R.mipmap.food_orange);
                LinlayoutFood.setTag("TagFoodOrange");
            }
            else if (LinLayoutRestaurants.getTag()=="TagRestuarantsGreen")
            {
                LinLayoutRestaurants.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
                TextViewResturants.setTextColor(getActivity().getResources().getColor(R.color.orange));
                ImageviewResturants.setImageResource(R.mipmap.restaurant_orange_icon);
                LinLayoutRestaurants.setTag("TagRestuarantsOrange");
            }
            else  if (LinlayoutHotel.getTag()=="TagHotelsGreen")
            {
                LinlayoutHotel.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
                TextviewHotels.setTextColor(getActivity().getResources().getColor(R.color.orange));
                ImageviewHotels.setImageResource(R.mipmap.hotels_orange_icon);
                LinlayoutHotel.setTag("TagHotelsOrange");

            }
        }

        }

    public void TagIntilization()
    {
        LinlayoutFood.setTag("TagFoodGreen");
        LinLayoutRestaurants.setTag("TagRestuarantsOrange");
        LinlayoutHotel.setTag("TagHotelsOrange");
        LinlayoutNearBy.setTag("TagNearByOrange");


    }



    @Override
    public void onResume()
    {
        super.onResume();
        if(getView() == null)
        {
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK)
                {
                    // handle back button's click listener
                    setLinLayoutBack();

                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onStart()
    {
        super.onStart();

    }
}
