package com.angadi.tripmanagementa.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angadi.tripmanagementa.Activities.LoginActivity;
import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.R;
import com.angadi.tripmanagementa.Activities.WelcomeActivity;
import com.crashlytics.android.Crashlytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

public class RegistrationThanksFragment extends Fragment
{
    @BindView(R.id.bottomRelLayout) RelativeLayout bottomRelLayout;
    @BindView(R.id.login_button) Button login_button;
    @BindView(R.id.UserId) TextView UserId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.layout_reg_thanku, container, false);
        ButterKnife.bind(this,view);

        Fabric.with(getActivity(), new Crashlytics());

        WelcomeActivity.btnNext.setVisibility(View.GONE);
        WelcomeActivity.btnSkip.setVisibility(View.GONE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getActivity(). getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.black));
        }

        UserId.setText(TripManagement.readValueFromPreferences(getActivity(),"USERID",""));

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        return view;
    }

    @OnClick(R.id.login_button)
    public void setLogin_button()
    {

        startActivity(new Intent(getActivity(),LoginActivity.class));

    }
    @OnClick(R.id.bottomRelLayout)
    public void setBottomRelLayout()
    {
        startActivity(new Intent(getActivity(),LoginActivity.class));
    }

    public static void hideSoftKeyboard(Activity activity)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
