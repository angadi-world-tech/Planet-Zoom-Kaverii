package com.angadi.tripmanagementa.Fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.angadi.tripmanagementa.Activities.HomeActivity;
import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

public class NotificationFragment extends Fragment
{

    @BindView(R.id.LinLayoutBack) LinearLayout LinLayoutBack;
    @BindView(R.id.LinlayoutPlanetZoom) LinearLayout LinlayoutPlanetZoom;
    @BindView(R.id.LinlayoutChat) LinearLayout LinlayoutChat;
    @BindView(R.id.LinlayoutNotificationDetails) LinearLayout LinlayoutNotificationDetails;
    @BindView(R.id.LinlayoutnotificatinoList) LinearLayout LinlayoutnotificatinoList;
    @BindView(R.id.AppNotiClickedTV) TextView AppNotiClickedTV;
    @BindView(R.id.AppNotiUnClickedTV) TextView AppNotiUnClickedTV;
    @BindView(R.id.PlanetZoomNotiUnClickedTv) TextView PlanetZoomNotiUnClickedTv;
    @BindView(R.id.PlanetZoomNotiClickedTv) TextView PlanetZoomNotiClickedTv;
    @BindView(R.id.NameLinLayout) TextView NameLinLayout;
    @BindView(R.id.id_name) TextView id_name;
    @BindView(R.id.tv1) TextView tv1;
    @BindView(R.id.tv2) TextView tv2;



    boolean IslistOpen = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.layout_notification, container,false);
        ButterKnife.bind(this,view);
        Fabric.with(getActivity(), new Crashlytics());

        Typeface montserrat_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Montserrat-Regular.ttf");
        Typeface montserrat_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MONTSERRAT-BOLD.OTF");

        NameLinLayout.setTypeface(montserrat_regular);
        AppNotiClickedTV.setTypeface(montserrat_regular);
        AppNotiUnClickedTV.setTypeface(montserrat_regular);
        PlanetZoomNotiUnClickedTv.setTypeface(montserrat_regular);
        PlanetZoomNotiClickedTv.setTypeface(montserrat_regular);
        id_name.setTypeface(montserrat_regular);
        tv1.setTypeface(montserrat_bold);
        tv2.setTypeface(montserrat_regular);



        LinlayoutnotificatinoList.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                LinlayoutnotificatinoList.setVisibility(View.GONE);
                LinlayoutNotificationDetails.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    @OnClick(R.id.LinLayoutBack)
    public void setLinLayoutBack()
    {
//        if (LinlayoutNotificationDetails.getVisibility()==View.VISIBLE)
//        {
//            LinlayoutnotificatinoList.setVisibility(View.VISIBLE);
//        }
//        else if (LinlayoutNotificationDetails.getVisibility()==View.GONE)
//        {
//            startActivity(new Intent(getActivity(), HomeActivity.class));
//        }

        if(IslistOpen)
        {
            startActivity(new Intent(getActivity(), HomeActivity.class));
            IslistOpen = false;
        }
        else
        {
            LinlayoutnotificatinoList.setVisibility(View.VISIBLE);
            IslistOpen = true;

        }



    }

    @OnClick(R.id.LinlayoutPlanetZoom)
    public void setLinlayoutPlanetZoom()
    {
        startActivity(new Intent(getActivity(), HomeActivity.class));
    }

    @OnClick(R.id.LinlayoutChat)
    public void setLinlayoutChat()
    {
        ChatListFragment chatListFragment = new ChatListFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_content,chatListFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.AppNotiClickedTV)
    public void setAppNotiClickedTV()
    {
        AppNotiClickedTV.setVisibility(View.GONE);
        AppNotiUnClickedTV.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.AppNotiUnClickedTV)
    public void setAppNotiUnClickedTV()
    {
        AppNotiClickedTV.setVisibility(View.VISIBLE);
        AppNotiUnClickedTV.setVisibility(View.GONE);

        PlanetZoomNotiUnClickedTv.setVisibility(View.VISIBLE);
        PlanetZoomNotiClickedTv.setVisibility(View.GONE);
    }

    @OnClick(R.id.PlanetZoomNotiUnClickedTv)
    public void setPlanetZoomNotiUnClickedTv()
    {
        PlanetZoomNotiUnClickedTv.setVisibility(View.GONE);
        PlanetZoomNotiClickedTv.setVisibility(View.VISIBLE);

        AppNotiClickedTV.setVisibility(View.GONE);
        AppNotiUnClickedTV.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.PlanetZoomNotiClickedTv)
    public void setPlanetZoomNotiClickedTv()
    {
        PlanetZoomNotiUnClickedTv.setVisibility(View.VISIBLE);
        PlanetZoomNotiClickedTv.setVisibility(View.GONE);
    }


    @Override
    public void onResume()
    {
        super.onResume();
        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK)
                {
                   // startActivity(new Intent(getActivity(),HomeActivity.class));
                    setLinLayoutBack();
                    // handle back button's click listener
                    return true;
                }
                return false;
            }
        });
    }





}
