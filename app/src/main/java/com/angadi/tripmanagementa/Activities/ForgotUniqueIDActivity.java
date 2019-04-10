package com.angadi.tripmanagementa.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.angadi.tripmanagementa.Activities.LoginActivity;
import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.Extras.Keys;
import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;
import com.hbb20.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

public class ForgotUniqueIDActivity extends AppCompatActivity
{
    EditText emailEdittext,mobileEdittext;
    @BindView(R.id.forgotpswrd_submit_button) Button forgotpswrd_submit_button;
    @BindView(R.id.TextViewLogin) TextView TextViewLogin;
    @BindView(R.id.emailimageView) ImageView emailimageView;
    @BindView(R.id.emailTV) TextView emailTV;
    @BindView(R.id.emailView) View emailView;
    @BindView(R.id.RelLayoutFirst) RelativeLayout RelLayoutFirst;
    @BindView(R.id.TextViewIndicationForForgotUniqueId) TextView  TextViewIndicationForForgotUniqueId;

    TextView TVClose,TV_Invali;
    Dialog dialog_invalid_qr;
    LinearLayout LinLayoutClose;


    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    String HttpUrl = TripManagement.BASE_URL+"forgot/unique_id";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_forgot_uniqueid);


        ButterKnife.bind(this);
        Fabric.with(this, new Crashlytics());


        emailEdittext = (EditText)findViewById(R.id.emailEdittext);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window =this. getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));
        }



        Typeface montserrat_bold = Typeface.createFromAsset(getAssets(), "fonts/MONTSERRAT-BOLD.OTF");
        Typeface montserrat_regular = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");

        TextViewIndicationForForgotUniqueId.setTypeface(montserrat_bold);
        emailTV.setTypeface(montserrat_regular);
        emailEdittext.setTypeface(montserrat_regular);
        forgotpswrd_submit_button.setTypeface(montserrat_regular);
        TextViewLogin.setTypeface(montserrat_regular);


        dialog_invalid_qr = new Dialog(ForgotUniqueIDActivity.this);
        dialog_invalid_qr.setContentView(R.layout.layout_alert_dialog);

        TVClose = (TextView) dialog_invalid_qr.findViewById(R.id.TVClose);
        TV_Invali = (TextView) dialog_invalid_qr.findViewById(R.id.TV_Invali);
        LinLayoutClose = (LinearLayout)dialog_invalid_qr.findViewById(R.id.LinLayoutClose);
        TVClose.setTypeface(montserrat_regular);
        TV_Invali.setTypeface(montserrat_regular);
        dialog_invalid_qr.setCanceledOnTouchOutside(false);
        LinLayoutClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog_invalid_qr.dismiss();

            }
        });

        EditTextAnimation();

    }



    public void EditTextAnimation()
    {
        emailEdittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus)
            {
                if (hasFocus)
                {
                    emailEdittext.setCursorVisible(true);
                }

            }
        });

        emailEdittext.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction())
                    emailimageView.setImageResource(R.mipmap.envolope_blue_icon);
                emailView.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        emailEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (i2==0)
                {
                    emailimageView.setImageResource(R.mipmap.envolope_orange_icon);
                    emailView.setVisibility(View.VISIBLE);
                    emailEdittext.setCursorVisible(false);

                }
                else
                {
                    emailimageView.setImageResource(R.mipmap.envolope_blue_icon);
                    emailView.setVisibility(View.INVISIBLE);
                    emailEdittext.setCursorVisible(true);

                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

    }

    @OnClick(R.id.forgotpswrd_submit_button)
    public void forgotpswrd_submit_button()
    {
        if (emailEdittext.length()==0 )
        {

            TV_Invali.setText("Email is required");
            dialog_invalid_qr.show();

        }
        else
        {
            ForgotUniqueID();
        }

    }

    @OnClick(R.id.TextViewLogin)
    public void setTextViewLogin()
    {
        startActivity(new Intent(this,LoginActivity.class));
    }

    @OnClick(R.id.RelLayoutFirst)
    public void setRelLayoutFirst()
    {
      hideSoftKeyboard(this);
    }

    public static void hideSoftKeyboard(Activity activity)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void ForgotUniqueID()
    {

        // Showing progress dialog at user regist ration time.
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String ServerResponse)
                    {
                        // Toast.makeText(getActivity(), ServerResponse.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("URLLLLL",HttpUrl);
                        Log.d("Responce",ServerResponse);

                        progressDialog.dismiss();
                        JSONObject jsonObject = null;
                        try
                        {
                            jsonObject = new JSONObject(ServerResponse);

                            if (jsonObject.getInt("success") == 1)
                            {
                                Toast.makeText(ForgotUniqueIDActivity.this, "Unique ID has been sent to your Registered Email.", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(ForgotUniqueIDActivity.this,LoginActivity.class));
                                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                if (getCurrentFocus() != null)
                                {
                                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                                }
                            }
                            else if (jsonObject.getInt("success") == 0)
                            {
                                JSONObject jsonObject_error = jsonObject.getJSONObject(Keys.errors);
                                if (jsonObject_error.has(Keys.email))
                                {
                                    TV_Invali.setText(jsonObject_error.getJSONArray(Keys.email).get(0).toString());
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

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(ForgotUniqueIDActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();
                        Log.e("no_response","no_response"+volleyError);

                    }
                })
        {
            @Override
            public Map<String, String> getParams() throws AuthFailureError
            {


                Map<String, String> params = new HashMap<String, String>();
                params.put(Keys.email, emailEdittext.getText().toString());


                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }
}
