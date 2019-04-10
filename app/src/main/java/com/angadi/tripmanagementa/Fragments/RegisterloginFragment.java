package com.angadi.tripmanagementa.Fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.angadi.tripmanagementa.Activities.LoginActivity;
import com.angadi.tripmanagementa.Activities.RegistrationActivity;
import com.angadi.tripmanagementa.R;
import com.angadi.tripmanagementa.Activities.WelcomeActivity;
import com.crashlytics.android.Crashlytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;
import io.intercom.android.sdk.Intercom;

public class RegisterloginFragment extends Fragment
{
    @Nullable
    @BindView(R.id.LinLayoutRegister) Button LinLayoutRegister;
    @Nullable
    @BindView(R.id.LinLayoutLogin) Button LinLayoutLogin;
    TextView copyright;
    FrameLayout frameLayout;





    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.layout_register_login, container, false);
        ButterKnife.bind(this,view);
        Fabric.with(getActivity(), new Crashlytics());

        copyright = (TextView)view.findViewById(R.id.copyright);



        Typeface montserrat_medium = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MONTSERRAT-MEDIUM.OTF");
        LinLayoutRegister.setTypeface(montserrat_medium);
        LinLayoutLogin.setTypeface(montserrat_medium);
        copyright.setTypeface(montserrat_medium);
        Intercom.client().setLauncherVisibility(Intercom.Visibility.GONE);


        frameLayout = (FrameLayout)view.findViewById(R.id.content);
        WelcomeActivity.btnNext.setVisibility(View.GONE);
        WelcomeActivity.btnSkip.setVisibility(View.GONE);

        copyright.setText("\u00a9"+ "angadiworldtech.com");
        copyright.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.angadiworldtech.com/"));
                startActivity(intent);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getActivity(). getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.black));


        }

        return view;
    }

        @OnClick(R.id.LinLayoutRegister)
        public void Registrationclick()
        {

            startActivity(new Intent(getActivity(),RegistrationActivity.class));
            copyright.setText("\u00a9"+ "angadiworldtech.com");

        }

        @OnClick(R.id.LinLayoutLogin)
        public void LoginButtonClick()
        {
            startActivity(new Intent(getActivity(),LoginActivity.class));
            copyright.setText("\u00a9"+ "angadiworldtech.com");
        }

}
