package com.angadi.tripmanagementa.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.Extras.Keys;
import com.angadi.tripmanagementa.Extras.VolleyMultipartRequest;
import com.angadi.tripmanagementa.Fragments.RegistrationThanksFragment;
import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;
import io.intercom.android.sdk.Intercom;

public class VarifyOTPActiivty extends AppCompatActivity
{


    @BindView(R.id.varify_button)Button varify_button;
    @BindView(R.id.LinLayoutResendOTP) LinearLayout LinLayoutResendOTP;
    @BindView(R.id.EdittextFirst) EditText EdittextFirst;
    @BindView(R.id.EdittextSecond) EditText EdittextSecond;
    @BindView(R.id.EdittextThird) EditText EdittextThird;
    @BindView(R.id.EdittextFourth) EditText EdittextFourth;
    @BindView(R.id.EdittextFifth) EditText EdittextFifth;
    @BindView(R.id.EdittextSixth) EditText EdittextSixth;
    @BindView(R.id.TextviewLogout)
    TextView TextviewLogout;

    Context context = VarifyOTPActiivty.this;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_otp_security1);
        ButterKnife.bind(this,this);
        Fabric.with(this, new Crashlytics());


        WelcomeActivity.btnNext.setVisibility(View.GONE);
        WelcomeActivity.btnSkip.setVisibility(View.GONE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));
        }


        EdittextFirst.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(EdittextFirst.getText().toString().length()==1)     //size as per your requirement
                {
                    EdittextSecond.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        EdittextSecond.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(EdittextSecond.getText().toString().length()==1)     //size as per your requirement
                {
                    EdittextThird.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        EdittextThird.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(EdittextThird.getText().toString().length()==1)     //size as per your requirement
                {
                    EdittextFourth.requestFocus();

                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s)
            {
                // TODO Auto-generated method stub

            }

        });

        EdittextFourth.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(EdittextFourth.getText().toString().length()==1)     //size as per your requirement
                {
                    EdittextFourth.setCursorVisible(false);
                    hideSoftKeyboard(VarifyOTPActiivty.this);

                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s)
            {
                // TODO Auto-generated method stub

            }

        });
    }




    @OnClick(R.id.varify_button)
    public void setvarify_button()
    {
        VarifyOTP();

        }

    @OnClick(R.id.LinLayoutResendOTP)
    public void setLinLayoutResendOTP()
    {
       // Toast.makeText(this, "Resend OTP", Toast.LENGTH_SHORT).show();

        ResendOTP();

    }
    public static void hideSoftKeyboard(Activity activity)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null)
        {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

        }
    }

    private void VarifyOTP()
    {
        //getting the tag from the edittext
        //final String tags = editTextTags.getText().toString().trim();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String VarifyOTPURL = TripManagement.BASE_URL +"user/verify/otp";

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, VarifyOTPURL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        progressDialog.dismiss();
                        Log.e("StringRequest--->", String.valueOf(response));
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            Log.e("StringRequestdata--->", String.valueOf(jsonObject));
                            if (jsonObject.getInt(Keys.success) == 1)
                            {
                                InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(INPUT_METHOD_SERVICE);
                                if (getCurrentFocus() != null)
                                {
                                    inputMethodManager.hideSoftInputFromWindow( getCurrentFocus().getWindowToken(), 0);
                                }
                                JSONObject jsonObjectData = jsonObject.getJSONObject(Keys.data);
                                JSONObject jsonObjectUser = jsonObjectData.getJSONObject(Keys.user);
                                String phone_varified = jsonObjectUser.getString(Keys.phone_verified);
                                String email_varified = jsonObjectUser.getString(Keys.email_verified);

                                TripManagement.saveValueuToPreferences( context,"email_verified",email_varified);
                                TripManagement.saveValueuToPreferences( context,"phone_verified",phone_varified);


                                if (phone_varified.equals("0") && email_varified.equals("0"))
                                {
                                    Toast.makeText(context, "Phone and email varification is pending", Toast.LENGTH_SHORT).show();
                                }
                               else if (phone_varified.equals("1") && email_varified.equals("1"))
                                {
                                    startActivity(new Intent(context,Qrcoderesult.class));
                                }
                                else if (email_varified.equals("1") && phone_varified.equals("0"))
                                {
                                    Toast.makeText(context, "Phone varification is pending", Toast.LENGTH_SHORT).show();
                                }
                                else if (email_varified.equals("0") && phone_varified.equals("1"))
                                {
                                  // startActivity(new Intent(context,VarifyEmailActivity.class));

                                    Intent intent = new Intent(context,VarifyEmailActivity.class);
                                    intent.putExtra("phone_varified",phone_varified);
                                    intent.putExtra("email_varified",email_varified);
                                    startActivity(intent);

                                }
                                }
                            else if (jsonObject.getInt(Keys.success) == 0)
                            {
                                JSONObject jsonObject_errors = jsonObject.getJSONObject(Keys.errors);
                              //  JSONArray jsonArray_name = jsonObject_errors.getJSONArray(Keys.name);
                               // Toast.makeText(HomeActivity.this, ""+jsonObject_errors.getJSONArray(Keys.name).get(0), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(VarifyOTPActiivty.this, e.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("Exeption",e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(INPUT_METHOD_SERVICE);
                        if (getCurrentFocus() != null)
                        {
                            inputMethodManager.hideSoftInputFromWindow( getCurrentFocus().getWindowToken(), 0);
                        }
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put(Keys.otp,EdittextFirst.getText().toString()+EdittextSecond.getText().toString()+EdittextThird.getText().toString()+EdittextFourth.getText().toString()+EdittextFifth.getText().toString()+EdittextSixth.getText().toString());

                return params;
            }

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Keys.Authorization,"Bearer "+TripManagement.readValueFromPreferences(VarifyOTPActiivty.this,"accesstoken",""));
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData()
            {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                //  params.put(Keys.avatar, new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    private void ResendOTP()
    {
        //getting the tag from the edittext
        //final String tags = editTextTags.getText().toString().trim();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String ResendOTPURL = TripManagement.BASE_URL +"user/resend/sms";

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, ResendOTPURL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        progressDialog.dismiss();
                        Log.e("StringRequest--->", String.valueOf(response));
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            Log.e("StringRequestdata--->", String.valueOf(jsonObject));
                            if (jsonObject.getInt(Keys.success) == 1)
                            {
                                InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(INPUT_METHOD_SERVICE);
                                if (getCurrentFocus() != null)
                                {
                                    inputMethodManager.hideSoftInputFromWindow( getCurrentFocus().getWindowToken(), 0);
                                }
                                JSONObject jsonObjectData = jsonObject.getJSONObject(Keys.data);
                                JSONObject jsonObjectUser = jsonObjectData.getJSONObject(Keys.user);

                                Toast.makeText(context, "OTP has been resent", Toast.LENGTH_SHORT).show();
                            }
                            else if (jsonObject.getInt(Keys.success) == 0)
                            {
                                JSONObject jsonObject_errors = jsonObject.getJSONObject(Keys.errors);
                                //  JSONArray jsonArray_name = jsonObject_errors.getJSONArray(Keys.name);
                                // Toast.makeText(HomeActivity.this, ""+jsonObject_errors.getJSONArray(Keys.name).get(0), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("Exeption",e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(INPUT_METHOD_SERVICE);
                        if (getCurrentFocus() != null)
                        {
                            inputMethodManager.hideSoftInputFromWindow( getCurrentFocus().getWindowToken(), 0);
                        }
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                // params.put(Keys.name,UsernameEdittext.getText().toString());

                return params;
            }

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Keys.Authorization,"Bearer "+TripManagement.readValueFromPreferences(VarifyOTPActiivty.this,"accesstoken",""));
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData()
            {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                //  params.put(Keys.avatar, new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @OnClick(R.id.TextviewLogout)
    public void setTextviewLogout()
    {
        TripManagement.saveValueuToPreferences(this,"LogoutClicked","LogoutClicked");


        startActivity(new Intent(VarifyOTPActiivty.this,Qrcoderesult.class));

        TripManagement.saveValueuToPreferences(this,"LoginSuccess","");
        TripManagement.saveValueuToPreferences(this,"Rate","");
        TripManagement.saveValueuToPreferences(this,"GENDER","");
        TripManagement.saveValueuToPreferences(this,"male","");
        TripManagement.saveValueuToPreferences(this,"female","");

        Intercom.client().setLauncherVisibility(Intercom.Visibility.GONE);

    }
}
