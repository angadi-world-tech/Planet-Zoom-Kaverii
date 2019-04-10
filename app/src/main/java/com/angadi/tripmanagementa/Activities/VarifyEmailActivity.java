package com.angadi.tripmanagementa.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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

public class VarifyEmailActivity extends AppCompatActivity
{
    @BindView(R.id.TvEmail) TextView TvEmail;
    @BindView(R.id.TextviewLogout) TextView TextviewLogout;
    @BindView(R.id.LinLayoutResendEmail) LinearLayout LinLayoutResendEmail;
    @BindView(R.id.Linayoutvarify) LinearLayout Linayoutvarify;
    @BindView(R.id.varify_button) Button varify_button;
    ProgressDialog progressDialog;
    Context context = VarifyEmailActivity.this;
    private static final int RC_BARCODE_CAPTURE = 10001;

    String phone_varified,email_varified ;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_varifyemail);
        ButterKnife.bind(this,this);
        Fabric.with(this, new Crashlytics());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));
        }

        TvEmail.setText("Varification link has been sent to "+TripManagement.readValueFromPreferences(this,"UserEmail",""));

//        String phone_varified = getIntent().getStringExtra("phone_varified");
//        String email_varified = getIntent().getStringExtra("email_varified");
//
//        Log.e("phone_varified",phone_varified);
//        Log.e("email_varified",email_varified);




        }

        @OnClick(R.id.LinLayoutResendEmail)
        public void setLinLayoutResendEmail()
        {
            ResendEmail();
        }

    private void ResendEmail()
    {
        //getting the tag from the edittext
        //final String tags = editTextTags.getText().toString().trim();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String ResendOTPURL = TripManagement.BASE_URL +"user/resend/email";

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


                                Toast.makeText(context, "Email has been resent", Toast.LENGTH_SHORT).show();
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
                params.put(Keys.Authorization,"Bearer "+TripManagement.readValueFromPreferences(context,"accesstoken",""));
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

    @OnClick(R.id.Linayoutvarify)
    public void setLinayoutvarify()
    {
        ViewProfile();

    }


    @Override
    protected void onResume()
    {
        super.onResume();

    }

    private void ViewProfile()
    {


        //getting the tag from the edittext
        //final String tags = editTextTags.getText().toString().trim();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String getProfileURL = TripManagement.BASE_URL+"user";

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, getProfileURL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        progressDialog.dismiss();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            Log.e("UserDetails", String.valueOf(jsonObject));
                            if (jsonObject.getInt(Keys.success) == 1)
                            {
                                JSONObject jsonObjectData = jsonObject.getJSONObject(Keys.data);
                                JSONObject jsonObjectUser = jsonObjectData.getJSONObject(Keys.user);
                                String UserID = jsonObjectUser.getString("id");
                                String phone_varified = jsonObjectUser.getString(Keys.phone_verified);
                                String email_varified = jsonObjectUser.getString(Keys.email_verified);
                                Log.e("HOMEUserId",UserID);

                                if (phone_varified.equals("1") && email_varified.equals("1"))
                                {
                                    startActivity(new Intent(context,Qrcoderesult.class));
                                }
                                else if (phone_varified.equals("1") && email_varified.equals("0"))
                                {
                                    Toast.makeText(context, "Email is Unvarified", Toast.LENGTH_SHORT).show();
                                }
                                else if (phone_varified.equals("1") && email_varified.equals("0"))
                                {
                                    Toast.makeText(context, "Phone is Unvarified", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else if (jsonObject.getInt(Keys.success) == 0)
                            {
                                JSONObject jsonObject_errors = jsonObject.getJSONObject(Keys.errors);
                                JSONArray jsonArray_name = jsonObject_errors.getJSONArray(Keys.name);
                                Toast.makeText(context, ""+jsonObject_errors.getJSONArray(Keys.name).get(0), Toast.LENGTH_SHORT).show();
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
                // params.put(Keys.name,UsernameTextview.getText().toString());

                return params;
            }

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Keys.Authorization,"Bearer "+TripManagement.readValueFromPreferences(context,"accesstoken",""));
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */

        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    @OnClick(R.id.varify_button)
    public void setvarify_button()
    {
        ViewProfile();
    }

    @OnClick(R.id.TextviewLogout)
    public void setTextviewLogout()
    {
        TripManagement.saveValueuToPreferences(this,"LogoutClicked","LogoutClicked");


        startActivity(new Intent(VarifyEmailActivity.this,Qrcoderesult.class));

        TripManagement.saveValueuToPreferences(this,"LoginSuccess","");
        TripManagement.saveValueuToPreferences(this,"Rate","");
        TripManagement.saveValueuToPreferences(this,"GENDER","");
        TripManagement.saveValueuToPreferences(this,"male","");
        TripManagement.saveValueuToPreferences(this,"female","");

        Intercom.client().setLauncherVisibility(Intercom.Visibility.GONE);

    }
}
