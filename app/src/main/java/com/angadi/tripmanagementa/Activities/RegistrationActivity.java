package com.angadi.tripmanagementa.Activities;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.Custum.Blocker;
import com.angadi.tripmanagementa.Custum.GPSTracker;
import com.angadi.tripmanagementa.Extras.Keys;
import com.angadi.tripmanagementa.Fragments.LocationSearchFragment;
import com.angadi.tripmanagementa.Model.SignUpArray;
import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.hbb20.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;
import io.intercom.android.sdk.Intercom;
import io.intercom.android.sdk.identity.Registration;


import com.android.volley.Response;

import static com.angadi.tripmanagementa.Activities.MapsActivity.REQUEST_CHECK_SETTINGS;


public class RegistrationActivity extends AppCompatActivity
{
    Spinner spinner_state,spinner_city;
    RelativeLayout bottom_otp_security;

    private long mLastClickTime = 0;
    boolean GpsStatus;
    GPSTracker gps;
    double latitude ;
    double longitude;


    String term_cond,devicetoken;

    TextView TVClose,TV_Invali;
    Dialog dialog_invalid_qr;


    EditText nameEdittext,emailEdittext,mobileEdittext,passwordEdittext,conformpasswordEdittext,upldadharEdittext,addressEdittext;
    CountryCodePicker ccp;
    EditText cityEdittext,stateEdittext;
    ArrayList<String> list_state = new ArrayList<>();
    ArrayList<String> list_city = new ArrayList<>();
    int count = 0;

    ArrayList<SignUpArray> a1;
    ProgressDialog dialog;
    RequestQueue requestQueue;

    LocationManager locationManager;



    @BindView(R.id.RippleCreateAccount) RippleView RippleCreateAccount;
   // @BindView(R.id.bottom) RelativeLayout bottom;
    //@BindView(R.id.create_account_button) Button create_account_button;
    @BindView(R.id.LinLayoutcamera) LinearLayout LinLayoutcamera;
    @BindView(R.id.TextViewLogin) TextView TextViewLogin;
    @BindView(R.id.nameTV) TextView nameTV;
    @BindView(R.id.nameEdLinLayout) LinearLayout nameEdLinLayout;
    @BindView(R.id.nameView) View nameView;
    @BindView(R.id.nameImageView) ImageView nameImageView;

   @BindView(R.id.emailimageView) ImageView emailimageView;
   @BindView(R.id.pswrdImageView) ImageView pswrdImageView;
   @BindView(R.id.confirmpswrdImageView) ImageView confirmpswrdImageView;
   @BindView(R.id.upldadharImageView) ImageView upldadharImageView;
   @BindView(R.id.addressImageView) ImageView addressImageView;
   @BindView(R.id.cityimageView) ImageView cityimageView;
   @BindView(R.id.stateimageView) ImageView stateimageView;


    @BindView(R.id.emailTV) TextView emailTV;
    @BindView(R.id.mobileTV) TextView mobileTV;
    @BindView(R.id.passwordTV) TextView passwordTV;
    @BindView(R.id.confirmpasswordTV) TextView confirmpasswordTV;
    @BindView(R.id.upldadharTV) TextView upldadharTV;
    @BindView(R.id.addressTV) TextView addressTV;
    @BindView(R.id.cityTV) TextView cityTV;
    @BindView(R.id.stateTV) TextView stateTV;


    //confirmpasswordEdLinLayout
    @BindView(R.id.emailEdLinLayout) LinearLayout emailEdLinLayout;
    @BindView(R.id.mobileEdLinLayout) LinearLayout mobileEdLinLayout;
    @BindView(R.id.passwordEdLinLayout) LinearLayout passwordEdLinLayout;
    @BindView(R.id.confirmpasswordEdLinLayout) LinearLayout confirmpasswordEdLinLayout;


   //addressView
    @BindView(R.id.emailView) View emailView;
    @BindView(R.id.mobileView) View mobileView;
    @BindView(R.id.passwordView) View passwordView;
    @BindView(R.id.confirmpasswordView) View confirmpasswordView;
    @BindView(R.id.upldAdharView) View upldAdharView;
    @BindView(R.id.addressView) View addressView;
    @BindView(R.id.cityView) View cityView;
    @BindView(R.id.stateView) View stateView;
    @BindView(R.id.Scroll) ScrollView Scroll;
    @BindView(R.id.RelLayoutFirst) RelativeLayout RelLayoutFirst;

    @BindView(R.id.LinlayoutMale) LinearLayout LinlayoutMale;
    @BindView(R.id.LinLayoutFemale) LinearLayout LinLayoutFemale;

    @BindView(R.id.ImagevieFemale) ImageView ImagevieFemale;
    @BindView(R.id.ImageviewMale) ImageView ImageviewMale;
    @BindView(R.id.TextviewTermsCond) TextView TextviewTermsCond;
    @BindView(R.id.TextviewIndicationForRegister) TextView TextviewIndicationForRegister;
    @BindView(R.id.TextViewIndicationForGender) TextView TextViewIndicationForGender;
    @BindView(R.id.TextViewIndicationForMale) TextView TextViewIndicationForMale;
    @BindView(R.id.TextViewIndicationForFeMale) TextView TextViewIndicationForFeMale;
    @BindView(R.id.TextViewIndicationForState) TextView TextViewIndicationForState;
    @BindView(R.id.text_state) TextView text_state;
    @BindView(R.id.TextViewIndicationForCity) TextView TextViewIndicationForCity;
    @BindView(R.id.text_city) TextView text_city;
    @BindView(R.id.terms) TextView terms;
    @BindView(R.id.Alreadyhaveanacoount) TextView Alreadyhaveanacoount;
    @BindView(R.id.create_account_button) Button create_account_button;
    @BindView(R.id.LinlayoutTerms) LinearLayout LinlayoutTerms;
    @BindView(R.id.mCheckbox) CheckBox mCheckbox;


    @BindView(R.id.LinlayoutSingleMale) LinearLayout LinlayoutSingleMale;
    @BindView(R.id.LinlayoutSingleFeMale) LinearLayout LinlayoutSingleFeMale;
    @BindView(R.id.LinlayoutBothGender) LinearLayout LinlayoutBothGender;
    @BindView(R.id.RellayoutEditFemale) RelativeLayout RellayoutEditFemale;
    @BindView(R.id.RellayoutEditMale) RelativeLayout RellayoutEditMale;





    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registration);

        ButterKnife.bind(this);

        Fabric.with(this, new Crashlytics());

        Typeface montserrat_bold = Typeface.createFromAsset(getAssets(), "fonts/MONTSERRAT-BOLD.OTF");
        Typeface montserrat_regular = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {

                            return;
                        }

                        // Get new Instance ID token
                         devicetoken = task.getResult().getToken();
                        Log.e("Token-->",devicetoken);

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
//                        Log.d(TAG, msg);
//                        Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


        gps = new GPSTracker(this);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();







         ImageviewMale.setImageResource(R.mipmap.radio_button_unselected);
         ImagevieFemale.setImageResource(R.mipmap.radio_button_unselected);



        nameEdittext = (EditText)findViewById(R.id.nameEdittext);
        emailEdittext = (EditText)findViewById(R.id.emailEdittext);
        mobileEdittext = (EditText)findViewById(R.id.mobileEdittext);
        passwordEdittext = (EditText)findViewById(R.id.passwordEdittext);
        conformpasswordEdittext = (EditText)findViewById(R.id.conformpasswordEdittext);
        cityEdittext = (EditText)findViewById(R.id.cityEdittext);
        stateEdittext = (EditText)findViewById(R.id.stateEdittext);
        upldadharEdittext = (EditText)findViewById(R.id.upldadharEdittext);
        addressEdittext = (EditText)findViewById(R.id.addressEdittext);
        ccp = (CountryCodePicker)findViewById(R.id.ccp);

        requestQueue = Volley.newRequestQueue(this);


        WelcomeActivity.btnNext.setVisibility(View.GONE);
        WelcomeActivity.btnSkip.setVisibility(View.GONE);


        dialog = new ProgressDialog(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        dialog_invalid_qr = new Dialog(RegistrationActivity.this);
        dialog_invalid_qr.setContentView(R.layout.layout_alert_dialog);

        TVClose = (TextView) dialog_invalid_qr.findViewById(R.id.TVClose);
        TV_Invali = (TextView) dialog_invalid_qr.findViewById(R.id.TV_Invali);
        TVClose.setTypeface(montserrat_regular);
        TV_Invali.setTypeface(montserrat_regular);
        dialog_invalid_qr.setCanceledOnTouchOutside(false);
        TVClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog_invalid_qr.dismiss();

            }
        });





        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));
        }



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 100);

        }

        TextviewIndicationForRegister.setTypeface(montserrat_bold);
        TextViewIndicationForGender.setTypeface(montserrat_regular);
        TextViewIndicationForMale.setTypeface(montserrat_regular);
        TextViewIndicationForFeMale.setTypeface(montserrat_regular);
        nameTV.setTypeface(montserrat_regular);
        emailTV.setTypeface(montserrat_regular);
        emailEdittext.setTypeface(montserrat_regular);
        mobileTV.setTypeface(montserrat_regular);
        mobileEdittext.setTypeface(montserrat_regular);
        passwordTV.setTypeface(montserrat_regular);
        passwordEdittext.setTypeface(montserrat_regular);
        confirmpasswordTV.setTypeface(montserrat_regular);
        conformpasswordEdittext.setTypeface(montserrat_regular);
        TextViewIndicationForState.setTypeface(montserrat_regular);
        text_state.setTypeface(montserrat_regular);
        TextViewIndicationForCity.setTypeface(montserrat_regular);
        text_city.setTypeface(montserrat_regular);
        addressTV.setTypeface(montserrat_regular);
        addressEdittext.setTypeface(montserrat_regular);
        terms.setTypeface(montserrat_regular);
        TextviewTermsCond.setTypeface(montserrat_regular);
        Alreadyhaveanacoount.setTypeface(montserrat_regular);
        TextViewLogin.setTypeface(montserrat_regular);
        create_account_button.setTypeface(montserrat_regular);
        nameEdittext.setTypeface(montserrat_regular);
        cityTV.setTypeface(montserrat_regular);
        stateTV.setTypeface(montserrat_regular);


        mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
               if (mCheckbox.isChecked())
               {

                   term_cond = "1";
               }
               else
                   {
                       term_cond = "0";
                   }

            }
        });



        ShowEditTextsFunctions();

        Scroll.fullScroll(View.FOCUS_DOWN);


        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null)
        {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        }

        GPSStatus();


        if (GpsStatus == true)
        {

            setaddress(latitude,longitude);

        }
        else
        {

            displayLocationSettingsRequest(this);
        }

    }

    @OnClick(R.id.LinLayoutcamera)
    public void setLinLayoutcamera()
    {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(RegistrationActivity.this, "camera permission granted", Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(RegistrationActivity.this, "camera permission denied", Toast.LENGTH_LONG).show();

            }

        }
    }

    public void ShowEditTextsFunctions()
    {



        // nameEditText
        nameEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count)
            {
                if (count>0)
                {

                    nameImageView.setImageResource(R.mipmap.profile_blue_icon);
                    nameView.setVisibility(View.INVISIBLE);
                    nameEdittext.setCursorVisible(true);


                }
                else if (count==0 && nameEdittext.getText().toString().length()==0)
                {

                    nameImageView.setImageResource(R.mipmap.profile_orange_icon);
                    nameView.setVisibility(View.VISIBLE);
                    nameEdittext.setCursorVisible(false);

                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                 if (nameEdittext.getText().toString().length()==0)
                 {
                     nameImageView.setImageResource(R.mipmap.profile_orange_icon);
                     nameView.setVisibility(View.VISIBLE);
                     nameEdittext.setCursorVisible(false);
                 }
            }
        });


        //EmailEditText

        emailEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count)
            {
                if (count>0)
                {

                    emailimageView.setImageResource(R.mipmap.envolope_blue_icon);
                    emailView.setVisibility(View.INVISIBLE);
                    emailEdittext.setCursorVisible(true);


                }
                else  if (count==0 && emailEdittext.getText().toString().length()==0)
                {
                    emailimageView.setImageResource(R.mipmap.envolope_orange_icon);
                    emailView.setVisibility(View.VISIBLE);
                    emailEdittext.setCursorVisible(false);

                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (emailEdittext.getText().toString().length()==0)
                {
                    emailimageView.setImageResource(R.mipmap.envolope_orange_icon);
                    emailView.setVisibility(View.VISIBLE);
                    emailEdittext.setCursorVisible(false);

                }
            }
        });
        conformpasswordEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (i2>0)
                {


                    confirmpswrdImageView.setImageResource(R.mipmap.password_blue_icon);
                    confirmpasswordView.setVisibility(View.INVISIBLE);
                    conformpasswordEdittext.setCursorVisible(true);


                }
                else if (i2==0 && conformpasswordEdittext.getText().toString().length()==0)
                {

                    confirmpswrdImageView.setImageResource(R.mipmap.orange_password_icon);
                    confirmpasswordView.setVisibility(View.VISIBLE);
                    conformpasswordEdittext.setCursorVisible(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (conformpasswordEdittext.getText().toString().length()==0)
                {
                    confirmpswrdImageView.setImageResource(R.mipmap.orange_password_icon);
                    confirmpasswordView.setVisibility(View.VISIBLE);
                    conformpasswordEdittext.setCursorVisible(false);
                }
            }
        });

        //PasswordEditText
        passwordEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (i2>0)
                {
                    pswrdImageView.setImageResource(R.mipmap.password_blue_icon);
                    passwordView.setVisibility(View.INVISIBLE);
                    passwordEdittext.setCursorVisible(true);
                }
                else if (i2==0 && passwordEdittext.length()==0)
                {
                    pswrdImageView.setImageResource(R.mipmap.orange_password_icon);
                    passwordView.setVisibility(View.VISIBLE);
                    passwordEdittext.setCursorVisible(false);

                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (passwordEdittext.length()==0)
                {
                    pswrdImageView.setImageResource(R.mipmap.orange_password_icon);
                    passwordView.setVisibility(View.VISIBLE);
                    passwordEdittext.setCursorVisible(false);

                }
            }
        });

        //AddressEditText
        addressEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (i2>0)
                {

                    addressImageView.setImageResource(R.mipmap.location_blue_icon);
                    addressView.setVisibility(View.INVISIBLE);
                    addressEdittext.setCursorVisible(true);
                }
                else  if (i2==0 && addressEdittext.getText().toString().length()==0)
                {
                    addressImageView.setImageResource(R.mipmap.location_orange_icon);
                    addressView.setVisibility(View.VISIBLE);
                    addressEdittext.setCursorVisible(false);

                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (addressEdittext.getText().toString().length()==0)
                {
                    addressImageView.setImageResource(R.mipmap.location_orange_icon);
                    addressView.setVisibility(View.VISIBLE);
                    addressEdittext.setCursorVisible(false);

                }
            }
        });

        //stateEdittExt
        stateEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (i2>0)
                {
                    stateimageView.setImageResource(R.mipmap.location_blue_icon);
                    stateView.setVisibility(View.INVISIBLE);
                    stateEdittext.setCursorVisible(true);

                }
                else if (i2==0 && stateEdittext.getText().toString().length()==0)
                {

                    stateimageView.setImageResource(R.mipmap.location_orange_icon);
                    stateView.setVisibility(View.VISIBLE);
                    stateEdittext.setCursorVisible(false);

                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (stateEdittext.getText().toString().length()==0)
                {

                    stateimageView.setImageResource(R.mipmap.location_orange_icon);
                    stateView.setVisibility(View.VISIBLE);
                    stateEdittext.setCursorVisible(false);

                }
            }
        });
        //CityEcitText
        cityEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (i2>0)
                {

                    cityimageView.setImageResource(R.mipmap.location_blue_icon);
                    cityView.setVisibility(View.INVISIBLE);
                    cityEdittext.setCursorVisible(true);


                }
                else  if (i2==0 && cityEdittext.getText().toString().length()==0)
                {
                    cityimageView.setImageResource(R.mipmap.location_orange_icon);
                    cityView.setVisibility(View.VISIBLE);
                    cityEdittext.setCursorVisible(false);

                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (cityEdittext.getText().toString().length()==0)
                {
                    cityimageView.setImageResource(R.mipmap.location_orange_icon);
                    cityView.setVisibility(View.VISIBLE);
                    cityEdittext.setCursorVisible(false);

                }
            }
        });
    }


    @OnClick(R.id.TextViewLogin)
    public void  setTextViewLogin()
    {
        startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
    }

    public void UserRegistration()
    {

        // Showing progress dialog at user regist ration time.
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        final String HttpUrl = TripManagement.BASE_URL+"register";

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String ServerResponse)
                    {
                        Log.d("URLLLLL",HttpUrl);
                        Log.e("Responce",ServerResponse);

                        progressDialog.dismiss();
                        JSONObject jsonObject = null;
                        try
                        {
                            jsonObject = new JSONObject(ServerResponse);

                            if (jsonObject.getInt("success") == 1)
                            {

                                JSONObject userJson = jsonObject.getJSONObject(Keys.data);
                                JSONObject jsonObject_user = userJson.getJSONObject(Keys.user);
                                String email_verified  = jsonObject_user.getString(Keys.email_verified);
                                String phone_verified  = jsonObject_user.getString(Keys.phone_verified);

                                if (phone_verified.equals("1") && email_verified.equals("1"))
                                {
                                    Toast.makeText(RegistrationActivity.this, "k1", Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(RegistrationActivity.this,Qrcoderesult.class));
                                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                    if (getCurrentFocus() != null)
                                    {
                                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                                    }

                                    }
                                else   if (phone_verified.equals("0") && email_verified.equals("0"))
                                {
                                    startActivity(new Intent(RegistrationActivity.this,VarifyOTPActiivty.class));
                                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                    if (getCurrentFocus() != null)
                                    {
                                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                                    }
                                }

                                else   if (phone_verified.equals("1") && email_verified.equals("0"))
                                {
                                    startActivity(new Intent(RegistrationActivity.this,VarifyEmailActivity.class));
                                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                    if (getCurrentFocus() != null)
                                    {
                                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                                    }
                                }

                                else if (phone_verified.equals("0") && email_verified.equals("1"))
                                {
                                    startActivity(new Intent(RegistrationActivity.this,VarifyOTPActiivty.class));
                                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                    if (getCurrentFocus() != null)
                                    {
                                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                                    }
                                }



                                TripManagement.saveValueuToPreferences(RegistrationActivity.this,"UserName",jsonObject_user.getString(Keys.name));
                                TripManagement.saveValueuToPreferences(RegistrationActivity.this,"UserEmail",jsonObject_user.getString(Keys.email));
                                TripManagement.saveValueuToPreferences(RegistrationActivity.this,"UniqueID",jsonObject_user.getString(Keys.unique_id));
                                TripManagement.saveValueuToPreferences(RegistrationActivity.this,"accesstoken",userJson.getString(Keys.access_token));
                                TripManagement.saveValueuToPreferences(RegistrationActivity.this,"LoginSuccess","LoginSuccess");
                                TripManagement.saveValueuToPreferences(RegistrationActivity.this,"GENDER", String.valueOf(jsonObject_user.getInt(Keys.gender)));

                                Registration registration = Registration.create().withUserId(jsonObject_user.getString(Keys.id));
                                Intercom.client().registerIdentifiedUser(registration);

                            }
                           else if (jsonObject.getInt("success") == 0)
                            {

                                JSONObject jsonObject_error = jsonObject.getJSONObject(Keys.errors);
                                if (jsonObject_error.has(Keys.email))
                                {
                                   TV_Invali.setText(""+jsonObject_error.getJSONArray(Keys.email).get(0));
                                   dialog_invalid_qr.show();
                                }
                               else if (jsonObject_error.has(Keys.phone))
                                {
                                   TV_Invali.setText(""+jsonObject_error.getJSONArray(Keys.phone).get(0));
                                   dialog_invalid_qr.show();

                                }
                               else if (jsonObject_error.has(Keys.password))
                               {
                                   TV_Invali.setText(""+jsonObject_error.getJSONArray(Keys.password).get(0));
                                   dialog_invalid_qr.show();

                               }
                               else if (jsonObject_error.has(Keys.gender))
                                {
                                    TV_Invali.setText(""+jsonObject_error.getJSONArray(Keys.gender).get(0));
                                    dialog_invalid_qr.show();
                                }

                            }


                            } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                        }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError volleyError)
                    {
                        Log.e("VolleyError",volleyError.toString());

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        TV_Invali.setText("Check Your Internet Connection");
                        dialog_invalid_qr.show();


                    }
                })
        {
            @Override
            public Map<String, String> getParams() throws AuthFailureError
            {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put(Keys.name, nameEdittext.getText().toString());
                if (TripManagement.readValueFromPreferences(RegistrationActivity.this,"male","").equals("male"))
                {

                    params.put(Keys.gender,"1");
                }
                else if (TripManagement.readValueFromPreferences(RegistrationActivity.this,"female","").equals("female"))
                {
                    params.put(Keys.gender,"0");
                }
                params.put(Keys.email, emailEdittext.getText().toString());
                params.put(Keys.isd_code, "91");
                params.put(Keys.phone, mobileEdittext.getText().toString());
                params.put(Keys.password, passwordEdittext.getText().toString());
                params.put(Keys.password_confirmation, conformpasswordEdittext.getText().toString());
                params.put(Keys.city, cityEdittext.getText().toString());
                params.put(Keys.state, stateEdittext.getText().toString());
                params.put(Keys.address, addressEdittext.getText().toString());
                params.put(Keys.device, "0");
                params.put(Keys.device_token, devicetoken);
                params.put(Keys.terms_and_conditions, term_cond);


                return params;
            }

            };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }


    @OnClick(R.id.RelLayoutFirst)
    public void setRelLayoutFirst()
    {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null)
        {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


        @OnClick(R.id.RippleCreateAccount)
        public void setRippleCreateAccount()
        {
             Blocker mBlocker = new Blocker();

            if (!mBlocker.block(4000))
            {
                // do your action

                if (nameEdittext.length() == 0 && emailEdittext.length() == 0 && passwordEdittext.length() == 0 && conformpasswordEdittext.length() == 0 && stateEdittext.length() == 0 && cityEdittext.length() == 0 && addressEdittext.length() == 0) {
                    TV_Invali.setText("Please fill all the deatails");
                    dialog_invalid_qr.show();
                } else if (nameEdittext.length() == 0 || emailEdittext.length() == 0 || passwordEdittext.length() == 0 || conformpasswordEdittext.length() == 0 || stateEdittext.length() == 0 || cityEdittext.length() == 0 || addressEdittext.length() == 0) {
                    TV_Invali.setText("Please fill all the deatails");
                    dialog_invalid_qr.show();
                } else if (!conformpasswordEdittext.getText().toString().equals(passwordEdittext.getText().toString())) {
                    TV_Invali.setText("Passwords do not match");
                    dialog_invalid_qr.show();

                } else if (!mCheckbox.isChecked()) {
                    TV_Invali.setText("Please accept the terms and condtions");
                    dialog_invalid_qr.show();


                }
                else if (LinlayoutBothGender.getVisibility() == View.VISIBLE)
                {
                    TV_Invali.setText("Please select your gender");
                    dialog_invalid_qr.show();
                }
                else {

                    UserRegistration();
                }
            }




        }

    @OnClick(R.id.LinlayoutMale)
    public void setLinlayoutMale()
    {
        TripManagement.saveValueuToPreferences(this,"male","male");
        TripManagement.saveValueuToPreferences(this,"female","");

        LinlayoutBothGender.setVisibility(View.GONE);
        LinlayoutSingleMale.setVisibility(View.VISIBLE);



    }

   @OnClick(R.id.LinLayoutFemale)
    public void setLinLayoutFemale()
   {
       TripManagement.saveValueuToPreferences(this,"female","female");
       TripManagement.saveValueuToPreferences(this,"male","");

       LinlayoutBothGender.setVisibility(View.GONE);
       LinlayoutSingleFeMale.setVisibility(View.VISIBLE);
   }

   @OnClick(R.id.RellayoutEditFemale)
   public void setRellayoutEditFemale()
   {
       LinlayoutBothGender.setVisibility(View.VISIBLE);
       LinlayoutSingleFeMale.setVisibility(View.GONE);
   }

   @OnClick(R.id.RellayoutEditMale)
   public void setRellayoutEditMale()
   {
       LinlayoutBothGender.setVisibility(View.VISIBLE);
       LinlayoutSingleMale.setVisibility(View.GONE);
   }

    @OnClick(R.id.LinlayoutTerms)
    public void setLinlayoutTerms()
   {
       Uri uri = Uri.parse("https://www.planetzoom.co.in/tc"); // missing 'http://' will cause crashed
       Intent intent = new Intent(Intent.ACTION_VIEW, uri);
       startActivity(intent);
   }

   /* public boolean onKey(View v, int keyCode, KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN)
        {
            if(keyCode == KeyEvent.KEYCODE_ENTER)
            {
                RippleCreateAccount.performClick(); // recherche is my button
                return true;
            }


        }

        return false;
    }
*/

    public void GPSStatus(){
        locationManager = (LocationManager)this.getSystemService(this.LOCATION_SERVICE);
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public  void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(RegistrationActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }


    public String setaddress(double latitude, double longitude)
    {

        Geocoder geocoder;
        List<Address> addressList;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addressList = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addressList != null && addressList.size() > 0)
            {
                Address address = addressList.get(0);
                LocationSearchFragment.address_current = addressList.get(0);
                LocationSearchFragment.latlngcurrent = new LatLng( LocationSearchFragment.address_current.getLatitude(), LocationSearchFragment.address_current.getLongitude());




                if (address.getAddressLine(1) == null)
                {
                    LocationSearchFragment.full_address=address.getAddressLine(0);
                    addressEdittext.setText(LocationSearchFragment.full_address);
                    stateEdittext.setText(address.getAdminArea());
                    cityEdittext.setText(address.getLocality());

                    Log.e("11",address.getAdminArea());
                    Log.e("13",address.getFeatureName());
                    Log.e("12",address.getLocality());

                }
                else if (address.getAdminArea()== null)
                {
                    LocationSearchFragment.full_address=address.getAddressLine(0)+" "+address.getAddressLine(1);

                    stateEdittext.setText( LocationSearchFragment.full_address);

                }
                else if (address.getLocality()== null)
                {
                    LocationSearchFragment.full_address=address.getAddressLine(0)+" "+address.getAddressLine(1)+" "+" "+address.getAdminArea();
                    cityEdittext.setText( LocationSearchFragment.full_address);

                }
                else
                {
                    LocationSearchFragment.full_address=address.getAddressLine(0)+" "+address.getAddressLine(1)+" "+address.getLocality()+" "+address.getAdminArea();
                    addressEdittext.setText( LocationSearchFragment.full_address);
                }

            }



        } catch (IOException e) {

            Toast.makeText(this, "Please Reboot/Restart your Device to get Current Address", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return null;
    }


}
