package com.angadi.tripmanagementa.Fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.angadi.tripmanagementa.Activities.Qrcoderesult;
import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

public class CreateEcardFragmnet extends Fragment
{


    @BindView(R.id.LinLayoutBack) LinearLayout LinLayoutBack;
    @BindView(R.id.LinLayoutShare) LinearLayout LinLayoutShare;
    @BindView(R.id.visitingcardIndicationTV) TextView visitingcardIndicationTV;

    Typeface montserrat_bold,montserrat_regular;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.layout_creavisitingcard,container,false);
        ButterKnife.bind(this, view);
        Fabric.with(getActivity(), new Crashlytics());

          montserrat_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MONTSERRAT-BOLD.OTF");
          montserrat_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Montserrat-Regular.ttf");

          setFonts();


        return view;
    }

    @OnClick(R.id.LinLayoutBack)
    public void setLinLayoutBack()
    {
        startActivity(new Intent(getActivity(),Qrcoderesult.class));
    }

    @OnClick(R.id.LinLayoutShare)
    public void setLinLayoutShare()
    {

    }

    public void setFonts()
    {
        visitingcardIndicationTV.setTypeface(montserrat_bold);
    }
}
