package com.angadi.tripmanagementa.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.Extras.Keys;
import com.angadi.tripmanagementa.Fragments.Create_PasswordFragmnet;
import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;
import io.intercom.android.sdk.Intercom;
import io.intercom.android.sdk.identity.Registration;

public class LoginActivity extends AppCompatActivity
{
    EditText EditTextUniqueId,EditTextPassword;
    View activityRootView;

    TextView TVClose,TV_Invali;
    Dialog dialog_invalid_qr;

    String LoginURL = TripManagement.BASE_URL+"login";
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String devicetoken;

    Typeface montserrat_bold,montserrat_regular;



    @BindView(R.id.TV_Forgotpassword)TextView TV_Forgotpassword;
    @BindView(R.id.TV_change_password)TextView TV_change_password;
    @BindView(R.id.LinlayoutUniqueIDTV) LinearLayout LinlayoutUniqueIDTV;
    @BindView(R.id.LinlayoutUniqueIDField) LinearLayout LinlayoutUniqueIDField;
    @BindView(R.id.TextViewUniqieID) TextView TextViewUniqieID;
    @BindView(R.id.TextViewAdgarPassword) TextView TextViewAdgarPassword;
    @BindView(R.id.Imageviewpassword) ImageView Imageviewpassword;
    @BindView(R.id.ImageviewUniqueId) ImageView ImageviewUniqueId;
    @BindView(R.id.ViewPassword) View ViewPassword;
    @BindView(R.id.ViewUniqueID) View ViewUniqueID;
    @BindView(R.id.varify_button) Button varify_button;
    @BindView(R.id.TV_ForgotUniqueID) TextView TV_ForgotUniqueID;
    @BindView(R.id.Linlayoutfirst) LinearLayout Linlayoutfirst;
    @BindView(R.id.TextViewIndicationForlogin) TextView TextViewIndicationForlogin;
    @BindView(R.id.TvSignup) TextView TvSignup;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        ButterKnife.bind(this);
        Fabric.with(this, new Crashlytics());

        EditTextUniqueId = (EditText)findViewById(R.id.EditTextUniqueId);
        EditTextPassword = (EditText)findViewById(R.id.EditTextPassword);
        activityRootView = findViewById(R.id.root_view);


       // WelcomeActivity.btnNext.setVisibility(View.GONE);
      //  WelcomeActivity.btnSkip.setVisibility(View.GONE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window =this. getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));
        }

         montserrat_bold = Typeface.createFromAsset(getAssets(), "fonts/MONTSERRAT-BOLD.OTF");
        Typeface montserrat_regular = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");

        TextViewIndicationForlogin.setTypeface(montserrat_bold);
        TextViewUniqieID.setTypeface(montserrat_regular);
        EditTextUniqueId.setTypeface(montserrat_regular);
        TextViewAdgarPassword.setTypeface(montserrat_regular);
        EditTextPassword.setTypeface(montserrat_regular);
        TV_change_password.setTypeface(montserrat_regular);
        TV_Forgotpassword.setTypeface(montserrat_regular);
        varify_button.setTypeface(montserrat_regular);
        TV_ForgotUniqueID.setTypeface(montserrat_regular);
        TvSignup.setTypeface(montserrat_regular);

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



        dialog_invalid_qr = new Dialog(LoginActivity.this);
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



        ShowEditTextFunctionas();

    }

    @OnClick(R.id.TV_Forgotpassword)
    public void setTV_Forgotpassword()
    {

       startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
    }

    @OnClick(R.id.TV_change_password)
    public void setTV_Changepassword()
    {
        Create_PasswordFragmnet varify_email = new Create_PasswordFragmnet();
        FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.add(R.id.content, varify_email);
        ft.commit();
    }

    public void ShowEditTextFunctionas()
    {
        EditTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (i2>0)
                {

                    Imageviewpassword.setImageResource(R.mipmap.password_blue_icon);
                    ViewPassword.setVisibility(View.INVISIBLE);
                    EditTextPassword.setCursorVisible(true);
                }
                else if (i2==0 && EditTextPassword.length()==0)
                {

                    Imageviewpassword.setImageResource(R.mipmap.orange_password_icon);
                    ViewPassword.setVisibility(View.VISIBLE);
                    EditTextPassword.setCursorVisible(false);

                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });


        //EditText UniqueID

        EditTextUniqueId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (i2>0)
                {

                    ImageviewUniqueId.setImageResource(R.mipmap.profile_blue_icon);
                    ViewUniqueID.setVisibility(View.INVISIBLE);
                    EditTextUniqueId.setCursorVisible(true);
                }
                else  if (i2==0 && EditTextUniqueId.getText().toString().length()==0)
                {
                    ImageviewUniqueId.setImageResource(R.mipmap.profile_orange_icon);
                    ViewUniqueID.setVisibility(View.VISIBLE);
                    EditTextUniqueId.setCursorVisible(false);

                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (EditTextUniqueId.getText().toString().length()==0)
                {
                    ImageviewUniqueId.setImageResource(R.mipmap.profile_orange_icon);
                    ViewUniqueID.setVisibility(View.VISIBLE);
                    EditTextUniqueId.setCursorVisible(false);

                }
            }
        });


        }

    @OnClick(R.id.varify_button)
    public void setvarify_button()
    {
        if (EditTextUniqueId.length()==0 && EditTextPassword.length()==0)
         {
            // Toast.makeText( this, "Please fill the details", Toast.LENGTH_SHORT).show();
             TV_Invali.setText("Please fill the details");
             dialog_invalid_qr.show();

         }
         else if (EditTextUniqueId.length()==0 || EditTextPassword.length()==0)
         {
            // Toast.makeText( this, "Please fill  all the details", Toast.LENGTH_SHORT).show();

             TV_Invali.setText("Please fill the details");
             dialog_invalid_qr.show();

         }
         else
         {
             LoginURL();
         }

    }

    public void LoginURL()
    {
        progressDialog = new ProgressDialog( this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, LoginURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.e("LOGIN_RESPONCE",response);
                progressDialog.dismiss();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    if (jsonObject.getInt(Keys.success) == 1)
                    {
                        TripManagement.saveValueuToPreferences( LoginActivity.this,"LoginSuccess","LoginSuccess");

                        JSONObject jsonObject_data = jsonObject.getJSONObject(Keys.data);
                        JSONObject jsonObject_user = jsonObject_data.getJSONObject(Keys.user);
                        String UserId = jsonObject_user.getString(Keys.id);
                        String email_varified = jsonObject_user.getString(Keys.email_verified);
                        String phone_varified = jsonObject_user.getString(Keys.phone_verified);
                        Log.e("1234",email_varified);
                        Log.e("12345",phone_varified);

                        Registration registration = Registration.create().withUserId(UserId);
                        Intercom.client().registerIdentifiedUser(registration);


                        TripManagement.saveValueuToPreferences(LoginActivity.this,"UserIdForChatCompare",jsonObject_user.getString(Keys.id));

                         TripManagement.saveValueuToPreferences( LoginActivity.this,"UserName",jsonObject_user.getString(Keys.name));
                         TripManagement.saveValueuToPreferences( LoginActivity.this,"UniqueID",jsonObject_user.getString(Keys.unique_id));
                         TripManagement.saveValueuToPreferences(LoginActivity.this,"GENDER", String.valueOf(jsonObject_user.getInt(Keys.gender)));

                        TripManagement.saveValueuToPreferences( LoginActivity.this,"accesstoken",jsonObject_data.getString(Keys.access_token));
                        TripManagement.saveValueuToPreferences( LoginActivity.this,"email_verified",jsonObject_user.getString(Keys.email_verified));
                        TripManagement.saveValueuToPreferences( LoginActivity.this,"phone_verified",jsonObject_user.getString(Keys.phone_verified));

                        Log.d("Aceess_login",TripManagement.readValueFromPreferences( LoginActivity.this,"accesstoken",""));
                        Log.d("Gender",TripManagement.readValueFromPreferences( LoginActivity.this,"GENDER",""));

                        InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(INPUT_METHOD_SERVICE);
                        if (getCurrentFocus() != null)
                        {
                            inputMethodManager.hideSoftInputFromWindow( getCurrentFocus().getWindowToken(), 0);
                        }
                        startActivity(new Intent( LoginActivity.this, Qrcoderesult.class));
                        finish();
                    }
                    else if (jsonObject.getInt(Keys.success) == 0)
                    {
                        TV_Invali.setText("Invalid Credentials");
                        dialog_invalid_qr.show();
                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

                }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progressDialog.dismiss();

                TV_Invali.setText(R.string.check_your_internet);
                dialog_invalid_qr.show();
                Log.e("Error", String.valueOf(error));


               // Toast.makeText( LoginActivity.this, R.string.check_your_internet, Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            protected Map<String,String> getParams()
            {
                Map<String,String> params = new HashMap<String, String>();
                params.put(Keys.username,EditTextUniqueId.getText().toString());
                params.put(Keys.password,EditTextPassword.getText().toString());
                params.put(Keys.device, "0");
                params.put(Keys.device_token, devicetoken);



                return params;

            }
        };

        requestQueue = Volley.newRequestQueue( this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);



        //adding the request to volley


        }

    @OnClick(R.id.TV_ForgotUniqueID)
    public void setTV_ForgotUniqueID()
    {

       startActivity(new Intent(LoginActivity.this,ForgotUniqueIDActivity.class));
    }

    @OnClick(R.id.Linlayoutfirst)
    public void setLinlayoutfirst()
    {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @OnClick(R.id.TvSignup)
    public void setTvSignup()
    {
        startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
    }


    @Override
    public void onResume()
    {
        super.onResume();
    }


}
