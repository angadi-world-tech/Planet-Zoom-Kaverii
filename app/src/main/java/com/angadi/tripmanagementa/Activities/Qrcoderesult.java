package com.angadi.tripmanagementa.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.media.Rating;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.angadi.tripmanagementa.Activities.database.DatabaseHelper;
import com.angadi.tripmanagementa.Adapters.GroupsAdapter;
import com.angadi.tripmanagementa.Adapters.Imagerecyclerviewadapter;
import com.angadi.tripmanagementa.Adapters.SubproductsRecyclerviewadapter;
import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.Extras.Keys;
import com.angadi.tripmanagementa.Extras.VolleyMultipartRequest;
import com.angadi.tripmanagementa.Fragments.ChatListFragment;
import com.angadi.tripmanagementa.Fragments.RegisterloginFragment;
import com.angadi.tripmanagementa.Fragments.ChatDetailsFragment;
import com.angadi.tripmanagementa.Fragments.ProductsLocationFragment;
import com.angadi.tripmanagementa.Model.Groups;
import com.angadi.tripmanagementa.Model.ImagesRecycler;
import com.angadi.tripmanagementa.Model.SubproductsRecyclerviewPojo;
import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.fabric.sdk.android.Fabric;
import io.intercom.android.sdk.Intercom;

import static com.angadi.tripmanagementa.Activities.HomeActivity.BarcodeObject;

public class Qrcoderesult extends AppCompatActivity {

    IntentResult result;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    RatingBar ratingBar;
    Barcode barcode;
    TextView TextviewLike, TextviewdisLike;
    public static DatabaseHelper db;

    public static List<ImagesRecycler> imageList;

     Typeface robotic_regular,montserrat_medium,montserrat_bold,montserrat_regular;


    LinearLayout LinlayoutLike,LinLayoutSubmitrating;

    String  value;

    ArrayList<Groups> PojoarrayListgroup  = new ArrayList<>();
    Groups PojoGroup;

    ImageView ImageviewFavHeart;

    String fpg_IdTFromscanresult;


    TextView TVClose,TV_Invali;
    Dialog dialog_invalid_qr;
    LinearLayout LinLayoutClose;

    String ProductId, GroupId,user_id,phone,description;
    String GroupFavorites = TripManagement.BASE_URL+"favourite/product/groups";
    String AddToFavorites = TripManagement.BASE_URL+"favourite/product";

    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    private static final int RC_BARCODE_CAPTURE = 10001;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcoderesult);
        Fabric.with(this, new Crashlytics());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));
        }


        db = new DatabaseHelper(this);

        db.del_ImageUpload();
        db.del_SubCategory();

        robotic_regular = Typeface.createFromAsset(getAssets(), "fonts/Jokerman-Regular.ttf");
        montserrat_medium = Typeface.createFromAsset(getAssets(), "fonts/MONTSERRAT-MEDIUM.OTF");
        montserrat_bold = Typeface.createFromAsset(getAssets(), "fonts/MONTSERRAT-BOLD.OTF");
        montserrat_regular = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");

        Log.e("Phone",TripManagement.readValueFromPreferences(this,"phone_verified",""));

        dialog_invalid_qr = new Dialog(Qrcoderesult.this);
        dialog_invalid_qr.setContentView(R.layout.layout_alert_dialog);

        TripManagement.saveValueuToPreferences(Qrcoderesult.this,"ShareScanResuldImages","");

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
        if (isNetworkAvailable())
        {
            ViewProfile();
        }
        else
        {
            TV_Invali.setText("No Internet Connection");
            dialog_invalid_qr.show();

            LinLayoutClose.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    dialog_invalid_qr.dismiss();
                    finish();

                }
            });
        }



    }

    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed()
    {

      if(TripManagement.readValueFromPreferences(this,"Scanned","").equals("Scanned"))
      {
          TripManagement.saveValueuToPreferences(this,"Scanned","");

          Intent  intent = getIntent();
          String action = intent.getAction();
          Uri data = intent.getData();

          if (data != null)
          {
              String Data = data.toString();
              Log.d("DeepLinking ","action : "+ action);
              Log.d("DeepLinking ","URI : "+ Data);
              String result = Data.substring(Data.lastIndexOf("=") + 1);
              System.out.println("DeepLinking " + result);
          } else {
               startActivity(new Intent(Qrcoderesult.this,HomeActivity.class));

          }
          }
      else
      {
          finish();
      }

      }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == RC_BARCODE_CAPTURE)
        {

            if (resultCode == CommonStatusCodes.SUCCESS)
            {

                if (data != null)
                {
                    barcode = data.getParcelableExtra(BarcodeObject);

                    String newHashedId = barcode.displayValue.substring(barcode.displayValue.lastIndexOf("/") + 1);
                    Log.e("ExtractedHashedId",newHashedId);
                    Log.e("ActuallHashedId",barcode.displayValue);
                    ViewProductDetails(newHashedId);
                    TripManagement.saveValueuToPreferences(Qrcoderesult.this,"BarcodeValue",barcode.displayValue);

                } else
                    {

                   // Toast.makeText(this, "Barcode Failed!", Toast.LENGTH_LONG).show();
                    finish();

                }
            } else
                {

               // Toast.makeText(this, "Barcode error!", Toast.LENGTH_LONG).show();
                }
        }

        else
            {
               // super.onActivityResult(requestCode, resultCode, data);
            }
    }




    public void ViewProductDetails(String result)
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String GetProductURL = TripManagement.BASE_URL+"product/scan/"+result;
        Log.e("scan URL",GetProductURL);


        final StringRequest stringRequest = new StringRequest(Request.Method.GET, GetProductURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

                progressDialog.dismiss();

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);

                    Log.e("productDetailsResponce", String.valueOf(jsonObject));
                    if (jsonObject.getInt(Keys.success) == 1)
                    {

                        final Dialog dialog = new Dialog(Qrcoderesult.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                        dialog.setContentView(R.layout.new_layout_scan);

                        TextView NameTextview = (TextView) dialog.findViewById(R.id.NameTextview);
                        // LinearLayout LinLayoutClose = (LinearLayout)dialog.findViewById(R.id.LinLayoutClose);
                        TextView TextviewDescription = (TextView)dialog.findViewById(R.id.TextviewDescription);
                        TextView descriptionIndicationTextview = (TextView)dialog.findViewById(R.id.descriptionIndicationTextview);
                        LinearLayout LinLayoutDescription = (LinearLayout)dialog.findViewById(R.id.LinLayoutDescription);
                        LinearLayout LinLayoutOffers = (LinearLayout)dialog.findViewById(R.id.LinLayoutOffers);
                        TextView TextviewOffers = (TextView)dialog.findViewById(R.id.TextviewOffers);
                        LinearLayout LinlayoutLocation = (LinearLayout)dialog.findViewById(R.id.LinlayoutLocation);
                        LinearLayout LinLayoutMobile = (LinearLayout)dialog.findViewById(R.id.LinLayoutMobile);
                        LinearLayout LinlayoutEmail = (LinearLayout)dialog.findViewById(R.id.LinlayoutEmail);
                        LinearLayout Linlayputfavorites = (LinearLayout)dialog.findViewById(R.id.Linlayputfavorites);
                        LinearLayout LinlayoutWebsite = (LinearLayout)dialog.findViewById(R.id.LinlayoutWebsite);
                        TextView Textviewoffers = (TextView)dialog.findViewById(R.id.Textviewoffers);
                        LinearLayout LinLayoutBack = (LinearLayout)dialog.findViewById(R.id.LinLayoutBack);
                        LinearLayout LinLayoutProductPhoto = (LinearLayout)dialog.findViewById(R.id.LinLayoutProductPhoto);
                          ratingBar = (RatingBar)dialog.findViewById(R.id.ratingBar);
                        ImageviewFavHeart = (ImageView)dialog.findViewById(R.id.ImageviewFavHeart);
                        LinearLayout LinLayoutChat = (LinearLayout)dialog.findViewById(R.id.LinLayoutChat);
                        TextView Textviewsubproducts = (TextView)dialog.findViewById(R.id.Textviewsubproducts);
                        LinearLayout LinLayoutsubproducts = (LinearLayout)dialog.findViewById(R.id.LinLayoutsubproducts);
                         LinlayoutLike = (LinearLayout)dialog.findViewById(R.id.LinlayoutLike);
                        LinearLayout DisLike = (LinearLayout)dialog.findViewById(R.id.DisLike);
                        LinearLayout LinLayoutshare = (LinearLayout)dialog.findViewById(R.id.LinLayoutshare);
                        TextView Textview_submit_rating = (TextView)dialog.findViewById(R.id.Textview_submit_rating);
                          LinLayoutSubmitrating = (LinearLayout)dialog.findViewById(R.id.LinLayoutSubmitrating);



                          RecyclerView recyclerviewsubproducts = (RecyclerView) dialog.findViewById(R.id.recyclerviewsubproducts);


                        Imagerecyclerviewadapter  imagerecyclerviewadapter;
                       // List<ImagesRecycler> imageList = new ArrayList<>();
                        ImagesRecycler list;

                        SubproductsRecyclerviewadapter subproductsRecyclerviewadapter;
                        List<SubproductsRecyclerviewPojo> subproductsList = new ArrayList<>();
                        SubproductsRecyclerviewPojo subproductsRecyclerviewPojo;



                        TextView TextviewFavorites = (TextView)dialog.findViewById(R.id.TextviewFavorites);
                         TextviewLike = (TextView)dialog.findViewById(R.id.TextviewLike);
                         TextviewdisLike = (TextView)dialog.findViewById(R.id.TextviewdisLike);
                        TextView TextviewShare = (TextView)dialog.findViewById(R.id.TextviewShare);
                        final TextView TextViewRatings = (TextView)dialog.findViewById(R.id.TextViewRatings);
                        TextView RateUsIndicationtextview = (TextView)dialog.findViewById(R.id.RateUsIndicationtextview);
                        TextView Textviewattachments = (TextView)dialog.findViewById(R.id.Textviewattachments);
                        LinearLayout LinLayoutArrachments = (LinearLayout)dialog.findViewById(R.id.LinLayoutArrachments);

                        RecyclerView recyclerviewimages = (RecyclerView)dialog.findViewById(R.id.recyclerviewimages);


                        TextviewFavorites.setTypeface(robotic_regular);
                        TextviewLike.setTypeface(robotic_regular);
                        TextviewdisLike.setTypeface(robotic_regular);
                        TextviewShare.setTypeface(robotic_regular);
                        TextViewRatings.setTypeface(robotic_regular);
                        Textview_submit_rating.setTypeface(robotic_regular);
                        RateUsIndicationtextview.setTypeface(robotic_regular);
                        NameTextview.setTypeface(montserrat_bold);



                        TextviewDescription.setTypeface(montserrat_regular);
                        TextviewOffers.setTypeface(montserrat_regular);


                        descriptionIndicationTextview.setTypeface(montserrat_medium);
                        Textviewoffers.setTypeface(montserrat_medium);
                        Textviewattachments.setTypeface(montserrat_medium);

                        LinlayoutLike.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                Like();
                            }
                        });
                        DisLike.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                DisLike();
                            }
                        });




                        LinLayoutBack.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                dialog.cancel();
                                onBackPressed();

                            }
                        });

                        dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
                        {
                            @Override
                            public void onCancel(DialogInterface dialogInterface)
                            {
                                startActivity(new Intent(Qrcoderesult.this,Qrcoderesult.class));
                            }
                        });

                        dialog.show();



                        JSONObject jsonObjectData = jsonObject.getJSONObject(Keys.data);
                        final JSONObject jsonObjectProduct = jsonObjectData.getJSONObject(Keys.product);
                        ProductId = jsonObjectProduct.getString(Keys.id);
                       // String  user_id = jsonObjectProduct.getString(Keys.user_id);
                        String  name = jsonObjectProduct.getString(Keys.name);
                        String  plus_code = jsonObjectProduct.getString(Keys.plus_code);
                        final String  latitude = jsonObjectProduct.getString(Keys.latitude);
                        final String  location = jsonObjectProduct.getString(Keys.location);
                        final String  longitude = jsonObjectProduct.getString(Keys.longitude);
                        final String  photo = jsonObjectProduct.getString(Keys.photo);
                        String  isd_code = jsonObjectProduct.getString(Keys.isd_code);
                        phone = jsonObjectProduct.isNull(Keys.phone) ? "" :jsonObjectProduct.getString(Keys.phone);
                        String  std_code = jsonObjectProduct.getString(Keys.std_code);
                        String  landline = jsonObjectProduct.getString(Keys.landline);
                        final String  email = jsonObjectProduct.isNull(Keys.email) ? "" :jsonObjectProduct.getString(Keys.email);
                        final String  website = jsonObjectProduct.isNull(Keys.website) ? "" :jsonObjectProduct.getString(Keys.website);
                         description = jsonObjectProduct.isNull(Keys.description) ? "" : jsonObjectProduct.getString(Keys.description);
                        String  price = jsonObjectProduct.getString(Keys.std_code);
                        String  validity_start = jsonObjectProduct.getString(Keys.validity_start);
                        String  validity_end = jsonObjectProduct.getString(Keys.validity_end);
                        String  quality = jsonObjectProduct.getString(Keys.quality);
                        String  quantity = jsonObjectProduct.getString(Keys.quantity);
                        final String  hashedId = jsonObjectProduct.getString(Keys.hashed_id);
                        String offer = jsonObjectProduct.isNull(Keys.offer) ? "" : jsonObjectProduct.getString(Keys.offer);
                        user_id = jsonObjectProduct.isNull(Keys.user_id) ? "" : jsonObjectProduct.getString(Keys.user_id);


                         if (TripManagement.readValueFromPreferences(Qrcoderesult.this,"UserIdForChatCompare","").equals(user_id))
                         {
                             LinLayoutChat.setVisibility(View.GONE);

                         }

                         JSONArray jsonArray_image = null;
                        imageList = new ArrayList<>();

                         try {
                             jsonArray_image = jsonObjectProduct.getJSONArray(Keys.image);

                             Textviewattachments.setVisibility(View.VISIBLE);
                             LinLayoutArrachments.setVisibility(View.VISIBLE);
                             for ( int i = 0; i < jsonArray_image.length(); i++ )
                             {
                                 try
                                 {
                                     String  value = jsonArray_image.getString(i);
                                     list = new ImagesRecycler(value);
                                     imageList.add(list);
                                     imagerecyclerviewadapter = new Imagerecyclerviewadapter(imageList,Qrcoderesult.this);
                                     Log.e("gggg",list.toString());
                                     RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Qrcoderesult.this,LinearLayoutManager.HORIZONTAL, false);
                                     recyclerviewimages.setLayoutManager(mLayoutManager);
                                     recyclerviewimages.setItemAnimator(new DefaultItemAnimator());
                                     recyclerviewimages.setAdapter(imagerecyclerviewadapter);
                                 }
                                 catch (JSONException e)
                                 {
                                     e.printStackTrace();
                                     Log.e("Exeption",e.toString());
                                 }
                             }

                         }
                         catch (JSONException e)
                         {
                             e.printStackTrace();
                         }

                         JSONArray jsonArray_sub_products = jsonObjectProduct.getJSONArray(Keys.sub_products);
                         for (int i=0;i<jsonArray_sub_products.length();i++)
                         {
                             if (jsonArray_sub_products!=null && jsonArray_sub_products.length()>0)
                             {

                                 Textviewsubproducts.setVisibility(View.VISIBLE);
                                 LinLayoutsubproducts.setVisibility(View.VISIBLE);

                                 JSONObject object = jsonArray_sub_products.getJSONObject(i);
                                 String id = object.getString(Keys.id);
                                 String productName = object.getString(Keys.name);
                                 String productprice = object.getString(Keys.price);
                                 String productPhoto = object.getString(Keys.photo);

                                 subproductsRecyclerviewPojo = new SubproductsRecyclerviewPojo(id,productName,productprice,productPhoto);
                                 subproductsList.add(subproductsRecyclerviewPojo);

                                 subproductsRecyclerviewadapter = new SubproductsRecyclerviewadapter(subproductsList,Qrcoderesult.this);

                                 RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Qrcoderesult.this,LinearLayoutManager.VERTICAL, false);
                                 recyclerviewsubproducts.setLayoutManager(mLayoutManager);
                                 recyclerviewsubproducts.setItemAnimator(new DefaultItemAnimator());
                                 recyclerviewsubproducts.setAdapter(subproductsRecyclerviewadapter);

                             }
                             else
                             {
                                 Textviewsubproducts.setVisibility(View.GONE);
                                 LinLayoutsubproducts.setVisibility(View.GONE);
                             }
                         }




                      //  ArrayList<String> imgUrl = new ArrayList<>();

                       ImageView imageView = null;





                           final String favorites = jsonObjectProduct.isNull(Keys.favourite) ? "" : jsonObjectProduct.getString(Keys.favourite);
                        if (favorites!="")
                        {
                            ImageviewFavHeart.setImageResource(R.mipmap.faved_heart);
                        }
                        else
                        {
                            ImageviewFavHeart.setImageResource(R.mipmap.fav_orange_new);
                        }

                        LinLayoutChat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                dialog.dismiss();
                                CreateChatURL();
                            }
                        });

                        Linlayputfavorites.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {

                                if (favorites != "")
                                {
                                   // ImageviewFavHeart.setImageResource(R.mipmap.faved_heart);

                                    try {
                                        JSONObject jsonObjectFav = jsonObjectProduct.getJSONObject(Keys.favourite);
                                         fpg_IdTFromscanresult  = jsonObjectFav.getString(Keys.fpg_id);
                                        Log.e("jjjj",fpg_IdTFromscanresult);

                                        AlertDialog alertDialog = new AlertDialog.Builder(Qrcoderesult.this).create();
                                        alertDialog.setTitle("Alert");
                                        alertDialog.setMessage("Remove From Favorites?");
                                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Remove",
                                                new DialogInterface.OnClickListener()
                                                {
                                                    public void onClick(DialogInterface dialog, int which)
                                                    {
                                                        dialog.dismiss();
                                                        RemoveFromfavorites();
                                                    }
                                                });

                                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                                                new DialogInterface.OnClickListener()
                                                {
                                                    public void onClick(DialogInterface dialog, int which)
                                                    {
                                                        dialog.dismiss();

                                                    }
                                                });
                                        alertDialog.show();



                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else
                                    {
                                       // ImageviewFavHeart.setImageResource(R.mipmap.fav_orange_new);
                                          GetGroups();

                                    }
                            }
                        });

                        JSONObject jsonObject_rating = jsonObjectProduct.getJSONObject(Keys.rating);
                        String count = jsonObject_rating.getString(Keys.count);
                        final String average = jsonObject_rating.getString(Keys.average);
                        final String value = jsonObject_rating.isNull(Keys.value) ? ""  :jsonObject_rating.getString(Keys.value);
                        TextView RatingTextview = (TextView)dialog.findViewById(R.id.RatingTextview);
                        RatingTextview.setText(average);
                        if (value!="")
                        {
                            ratingBar.setRating(Float.parseFloat(value));
                            ratingBar.setIsIndicator(true);
                        }

                        RatingTextview.setTypeface(montserrat_regular);

                        JSONObject jsonObject_likes = jsonObjectProduct.getJSONObject(Keys.likes);
                        String possitive = jsonObject_likes.getString(Keys.positive);
                        TextviewLike.setText(possitive);
                        String negetive = jsonObject_likes.getString(Keys.negative);
                        TextviewdisLike.setText(negetive);

                        //rb

                        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
                            @Override
                            public void onRatingChanged(RatingBar ratingBar, float v, boolean b)
                            {
                                Log.e("Ratings", value);


                                if (value != "")
                                {
                                    LinLayoutSubmitrating.setVisibility(View.GONE);
                                    ratingBar.setIsIndicator(true);
                                    Toast.makeText(Qrcoderesult.this, "You have already rated this product", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    LinLayoutSubmitrating.setVisibility(View.VISIBLE);
                                    TripManagement.saveValueuToPreferences(Qrcoderesult.this,"Rate", String.valueOf(v));
                                }


                            }
                        });
                        Textview_submit_rating.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                String UserRate = TripManagement.readValueFromPreferences(Qrcoderesult.this,"Rate","");
                                Log.e("UserRate",UserRate);

                                ratingBar.setIsIndicator(true);

                                Rate(Math.round(Float.parseFloat(UserRate)),TextViewRatings);
                            }
                        });

                        LinLayoutProductPhoto.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {

                                Intent intent = new Intent(Qrcoderesult.this,ProductPhotoViewActivity.class);
                                intent.putExtra("Photo",photo);
                                startActivity(intent);

                            }
                        });


                       LinlayoutLocation.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                dialog.dismiss();
                                ProductsLocationFragment productsLocationFragment = new ProductsLocationFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("location",location);
                                bundle.putString("latitude",latitude);
                                bundle.putString("longitude",longitude);
                                bundle.putString("Photo",photo);
                                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.add(R.id.QRActivity_FameID, productsLocationFragment);
                                fragmentTransaction.addToBackStack(null);
                                 productsLocationFragment.setArguments(bundle);
                                fragmentTransaction.commit();
                            }
                        });

                        final ImageView capturedPhotoImageview = (ImageView)dialog.findViewById(R.id.capturedPhotoImageview);
                        if (photo!=null)
                        {
                            Picasso.with(Qrcoderesult.this).load(photo).into(capturedPhotoImageview);

                        }

                        LinLayoutshare.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                dialog.dismiss();
                                TripManagement.saveValueuToPreferences(Qrcoderesult.this,"ShareScanResuldImages","ShareScanResuldImages");
                                ChatListFragment chatListFragment = new ChatListFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("BitmapImageShare", String.valueOf(hashedId));
                                Log.e("hashedId",hashedId);
                                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.add(R.id.QRActivity_FameID, chatListFragment);
                                fragmentTransaction.addToBackStack(null);
                                chatListFragment.setArguments(bundle);
                                fragmentTransaction.commit();

                            }
                        });


                        ImageView Imagearray0 = (ImageView) dialog.findViewById(R.id.Imagearray0);
                        ImageView Imagearray1 = (ImageView) dialog.findViewById(R.id.Imagearray1);
                        ImageView Imagearray2 = (ImageView) dialog.findViewById(R.id.Imagearray2);
                        ImageView Imagearray3 = (ImageView) dialog.findViewById(R.id.imagePhoto);

                        NameTextview.setText(name);

                        //Start of Description
                        if (description == "")
                        {
                            LinLayoutDescription.setVisibility(View.GONE);
                            descriptionIndicationTextview.setVisibility(View.GONE);
                        }
                        else
                        {

                            LinLayoutDescription.setVisibility(View.VISIBLE);
                            TextviewDescription.setText(description);
                            descriptionIndicationTextview.setVisibility(View.VISIBLE);
                            TextviewDescription.setSelected(true);


                            LinLayoutDescription.setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View view)
                                {
                                    final Dialog dialog_morque = new Dialog(Qrcoderesult.this);
                                    dialog_morque.setContentView(R.layout.layout_morquee);
                                    TextView tv = (TextView)dialog_morque.findViewById(R.id.tv);
                                    Button TVClose = (Button) dialog_morque.findViewById(R.id.TVClose);
                                    tv.setTypeface(montserrat_regular);
                                    TVClose.setTypeface(montserrat_regular);
                                    tv.setText(description);
                                    dialog_morque.show();

                                    TVClose.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View view)
                                        {
                                           dialog_morque.dismiss();
                                        }
                                    });

                                }
                            });



                        }
                        //End of Desription

                        //Start of Offers
                        if (offer == "")
                        {
                            LinLayoutOffers.setVisibility(View.GONE);
                            Textviewoffers.setVisibility(View.GONE);
                        }
                        else
                        {
                            LinLayoutOffers.setVisibility(View.VISIBLE);
                            TextviewOffers.setText(offer);
                            TextviewOffers.setSelected(true);
                            Textviewoffers.setVisibility(View.VISIBLE);
                        }

                        //End of Offers


                        // Phone

                        LinLayoutMobile.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                if (phone == "")
                                {

                                }
                                else
                                {
                                    AlertDialog alertDialog = new AlertDialog.Builder(Qrcoderesult.this).create();
                                    alertDialog.setTitle(R.string.alert);
                                    alertDialog.setMessage("Do you want to make a call?");
                                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                                            new DialogInterface.OnClickListener()
                                            {
                                                public void onClick(DialogInterface dialog, int which)
                                                {
                                                    dialog.dismiss();
                                                   callPhoneNumber();
                                                }
                                            });

                                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                                            new DialogInterface.OnClickListener()
                                            {
                                                public void onClick(DialogInterface dialog, int which)
                                                {
                                                    dialog.dismiss();

                                                }
                                            });
                                    alertDialog.show();



                                }
                            }

                        });



                        //Phone


                        // Email

                        LinlayoutEmail.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                if (email == "")
                                {

                                }
                                else
                                {
                                    startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+email)));
                                   // startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:angadiworldtech@gmail.com")));

                                }
                            }
                        });


                        //Email


                        // Website

                        LinlayoutWebsite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                if (website == "")
                                {

                                }
                                else
                                {
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                                    intent.setData(Uri.parse(website));
                                    startActivity(intent);                                }

                            }
                        });

                        //Website
                    }
                    else if (jsonObject.getInt(Keys.success) == 0)
                    {
                        final Dialog dialog_invalid_qr = new Dialog(Qrcoderesult.this);
                        dialog_invalid_qr.setContentView(R.layout.layout_invalidqr);

                        TextView TVClose = (TextView) dialog_invalid_qr.findViewById(R.id.TVClose);
                        TextView TV_Invali = (TextView) dialog_invalid_qr.findViewById(R.id.TV_Invali);
                        TVClose.setTypeface(montserrat_regular);
                        TV_Invali.setTypeface(montserrat_regular);
                        dialog_invalid_qr.show();
                        dialog_invalid_qr.setCanceledOnTouchOutside(false);

                        TVClose.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                dialog_invalid_qr.dismiss();
                                startActivity(new Intent(Qrcoderesult.this,Qrcoderesult.class));
                            }
                        });


                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(Qrcoderesult.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }
                , new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progressDialog.dismiss();
                Toast.makeText(Qrcoderesult.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("error",error.toString());


                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null)
                {
                    Log.e("Volley", "Error. HTTP Status Code:"+networkResponse.statusCode);
                }

                if (error instanceof TimeoutError)
                {
                    Log.e("Volley", "TimeoutError");
                }else if(error instanceof NoConnectionError){
                    Log.e("Volley", "NoConnectionError");
                } else if (error instanceof AuthFailureError) {
                    Log.e("Volley", "AuthFailureError");
                } else if (error instanceof ServerError) {
                    Log.e("Volley", "ServerError");
                } else if (error instanceof NetworkError) {
                    Log.e("Volley", "NetworkError");
                } else if (error instanceof ParseError) {
                    Log.e("Volley", "ParseError");
                }
                }
        }

        )
        {
            @Override
            protected Map<String,String> getParams()
            {
                Map<String,String> params = new HashMap<String, String>();
                return params;

            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                    volleyError = error;
                }
                return volleyError;
            }

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Keys.Authorization,"Bearer "+TripManagement.readValueFromPreferences(Qrcoderesult.this,"accesstoken",""));
                return params;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy()
        {
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

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    //Second

    public void ViewProductDetailsWindow(String ProductId1)
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String GetProductURL = TripManagement.BASE_URL+"product/"+ProductId1;
        Log.e("scan URL",GetProductURL);





        final StringRequest stringRequest = new StringRequest(Request.Method.GET, GetProductURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

                progressDialog.dismiss();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    if (jsonObject.getInt(Keys.success) == 1)
                    {

                        final Dialog dialog = new Dialog(Qrcoderesult.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                        dialog.setContentView(R.layout.new_layout_scan);

                        TextView NameTextview = (TextView) dialog.findViewById(R.id.NameTextview);
                        // LinearLayout LinLayoutClose = (LinearLayout)dialog.findViewById(R.id.LinLayoutClose);
                        TextView TextviewDescription = (TextView)dialog.findViewById(R.id.TextviewDescription);
                        TextView descriptionIndicationTextview = (TextView)dialog.findViewById(R.id.descriptionIndicationTextview);
                        LinearLayout LinLayoutDescription = (LinearLayout)dialog.findViewById(R.id.LinLayoutDescription);
                        LinearLayout LinLayoutOffers = (LinearLayout)dialog.findViewById(R.id.LinLayoutOffers);
                        TextView TextviewOffers = (TextView)dialog.findViewById(R.id.TextviewOffers);
                        LinearLayout LinlayoutLocation = (LinearLayout)dialog.findViewById(R.id.LinlayoutLocation);
                        LinearLayout LinLayoutMobile = (LinearLayout)dialog.findViewById(R.id.LinLayoutMobile);
                        LinearLayout LinlayoutEmail = (LinearLayout)dialog.findViewById(R.id.LinlayoutEmail);
                        LinearLayout Linlayputfavorites = (LinearLayout)dialog.findViewById(R.id.Linlayputfavorites);
                        LinearLayout LinlayoutWebsite = (LinearLayout)dialog.findViewById(R.id.LinlayoutWebsite);
                        TextView Textviewoffers = (TextView)dialog.findViewById(R.id.Textviewoffers);
                        LinearLayout LinLayoutBack = (LinearLayout)dialog.findViewById(R.id.LinLayoutBack);
                        LinearLayout LinLayoutProductPhoto = (LinearLayout)dialog.findViewById(R.id.LinLayoutProductPhoto);
                         ratingBar = (RatingBar)dialog.findViewById(R.id.ratingBar);
                        ImageviewFavHeart = (ImageView)dialog.findViewById(R.id.ImageviewFavHeart);
                        LinearLayout LinLayoutChat = (LinearLayout)dialog.findViewById(R.id.LinLayoutChat);
                        LinearLayout DisLike = (LinearLayout)dialog.findViewById(R.id.DisLike);
                        LinlayoutLike = (LinearLayout)dialog.findViewById(R.id.LinlayoutLike);
                        TextView Textview_submit_rating = (TextView)dialog.findViewById(R.id.Textview_submit_rating);
                        LinearLayout LinLayoutshare = (LinearLayout)dialog.findViewById(R.id.LinLayoutshare);
                        LinLayoutSubmitrating = (LinearLayout)dialog.findViewById(R.id.LinLayoutSubmitrating);




                        Imagerecyclerviewadapter  imagerecyclerviewadapter;
                        List<ImagesRecycler> imageList = new ArrayList<>();
                        ImagesRecycler list;



                        TextView TextviewFavorites = (TextView)dialog.findViewById(R.id.TextviewFavorites);
                         TextviewLike = (TextView)dialog.findViewById(R.id.TextviewLike);
                         TextviewdisLike = (TextView)dialog.findViewById(R.id.TextviewdisLike);
                        TextView TextviewShare = (TextView)dialog.findViewById(R.id.TextviewShare);
                      final   TextView TextViewRatings = (TextView)dialog.findViewById(R.id.TextViewRatings);
                        TextView RateUsIndicationtextview = (TextView)dialog.findViewById(R.id.RateUsIndicationtextview);
                        TextView Textviewattachments = (TextView)dialog.findViewById(R.id.Textviewattachments);
                        LinearLayout LinLayoutArrachments = (LinearLayout)dialog.findViewById(R.id.LinLayoutArrachments);

                        RecyclerView recyclerviewimages = (RecyclerView)dialog.findViewById(R.id.recyclerviewimages);


                        TextviewFavorites.setTypeface(robotic_regular);
                        TextviewLike.setTypeface(robotic_regular);
                        TextviewdisLike.setTypeface(robotic_regular);
                        TextviewShare.setTypeface(robotic_regular);
                        TextViewRatings.setTypeface(robotic_regular);
                        RateUsIndicationtextview.setTypeface(robotic_regular);
                        NameTextview.setTypeface(montserrat_bold);



                        TextviewDescription.setTypeface(montserrat_regular);
                        TextviewOffers.setTypeface(montserrat_regular);


                        descriptionIndicationTextview.setTypeface(montserrat_medium);
                        Textviewoffers.setTypeface(montserrat_medium);
                        Textviewattachments.setTypeface(montserrat_medium);

                        LinlayoutLike.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {

                                Like();
                            }
                        });
                        DisLike.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                DisLike();
                            }
                        });




                        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
                            @Override
                            public void onRatingChanged(RatingBar ratingBar, float v, boolean b)
                            {

                            }
                        });


                        LinLayoutBack.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                dialog.cancel();

                            }
                        });

                        dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
                        {
                            @Override
                            public void onCancel(DialogInterface dialogInterface)
                            {
                                startActivity(new Intent(Qrcoderesult.this,Qrcoderesult.class));
                            }
                        });

                        dialog.show();



                        JSONObject jsonObjectData = jsonObject.getJSONObject(Keys.data);
                        final JSONObject jsonObjectProduct = jsonObjectData.getJSONObject(Keys.product);
                        ProductId = jsonObjectProduct.getString(Keys.id);
                        // String  user_id = jsonObjectProduct.getString(Keys.user_id);
                        String  name = jsonObjectProduct.getString(Keys.name);
                        String  plus_code = jsonObjectProduct.getString(Keys.plus_code);
                        final String  latitude = jsonObjectProduct.getString(Keys.latitude);
                        final String  location = jsonObjectProduct.getString(Keys.location);
                        final String  longitude = jsonObjectProduct.getString(Keys.longitude);
                        final String  photo = jsonObjectProduct.getString(Keys.photo);
                        String  isd_code = jsonObjectProduct.getString(Keys.isd_code);
                        phone = jsonObjectProduct.isNull(Keys.phone) ? "" :jsonObjectProduct.getString(Keys.phone);
                        String  std_code = jsonObjectProduct.getString(Keys.std_code);
                        String  landline = jsonObjectProduct.getString(Keys.landline);
                        final String  email = jsonObjectProduct.isNull(Keys.email) ? "" :jsonObjectProduct.getString(Keys.email);
                        final String  website = jsonObjectProduct.isNull(Keys.website) ? "" :jsonObjectProduct.getString(Keys.website);
                        description = jsonObjectProduct.isNull(Keys.description) ? "" : jsonObjectProduct.getString(Keys.description);
                        String  price = jsonObjectProduct.getString(Keys.std_code);
                        String  validity_start = jsonObjectProduct.getString(Keys.validity_start);
                        String  validity_end = jsonObjectProduct.getString(Keys.validity_end);
                        String  quality = jsonObjectProduct.getString(Keys.quality);
                        String  quantity = jsonObjectProduct.getString(Keys.quantity);
                        final String  hashedId = jsonObjectProduct.getString(Keys.hashed_id);
                        String offer = jsonObjectProduct.isNull(Keys.offer) ? "" : jsonObjectProduct.getString(Keys.offer);
                        user_id = jsonObjectProduct.isNull(Keys.user_id) ? "" : jsonObjectProduct.getString(Keys.user_id);

                        if (TripManagement.readValueFromPreferences(Qrcoderesult.this,"UserIdForChatCompare","").equals(user_id))
                        {
                            LinLayoutChat.setVisibility(View.GONE);
                        }

                        JSONArray jsonArray_image = null;

                        try
                        {
                            jsonArray_image = jsonObjectProduct.getJSONArray(Keys.image);

                            Textviewattachments.setVisibility(View.VISIBLE);
                            LinLayoutArrachments.setVisibility(View.VISIBLE);
                            for ( int i = 0; i < jsonArray_image.length(); i++ )
                            {
                                try
                                {
                                    String  value = jsonArray_image.getString(i);
                                    list = new ImagesRecycler(value);
                                    imageList.add(list);
                                    imagerecyclerviewadapter = new Imagerecyclerviewadapter(imageList,Qrcoderesult.this);
                                    Log.e("gggg",list.toString());
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Qrcoderesult.this,LinearLayoutManager.HORIZONTAL, false);
                                    recyclerviewimages.setLayoutManager(mLayoutManager);
                                    recyclerviewimages.setItemAnimator(new DefaultItemAnimator());
                                    recyclerviewimages.setAdapter(imagerecyclerviewadapter);
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                    Log.e("Exeption",e.toString());
                                }
                            }

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }



                        //  ArrayList<String> imgUrl = new ArrayList<>();

                        ImageView imageView = null;





                        final String favorites = jsonObjectProduct.isNull(Keys.favourite) ? "" : jsonObjectProduct.getString(Keys.favourite);
                        if (favorites!="")
                        {
                            ImageviewFavHeart.setImageResource(R.mipmap.faved_heart);
                        }
                        else
                        {
                            ImageviewFavHeart.setImageResource(R.mipmap.fav_orange_new);
                        }

                        LinLayoutChat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                dialog.dismiss();
                                CreateChatURL();
                            }
                        });

                        Linlayputfavorites.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {

                                if (favorites != "")
                                {
                                    // ImageviewFavHeart.setImageResource(R.mipmap.faved_heart);

                                    try {
                                        JSONObject jsonObjectFav = jsonObjectProduct.getJSONObject(Keys.favourite);
                                        fpg_IdTFromscanresult  = jsonObjectFav.getString(Keys.fpg_id);
                                        Log.e("jjjj",fpg_IdTFromscanresult);

                                        AlertDialog alertDialog = new AlertDialog.Builder(Qrcoderesult.this).create();
                                        alertDialog.setTitle("Alert");
                                        alertDialog.setMessage("Remove From Favorites?");
                                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Remove",
                                                new DialogInterface.OnClickListener()
                                                {
                                                    public void onClick(DialogInterface dialog, int which)
                                                    {
                                                        dialog.dismiss();
                                                        RemoveFromfavorites();
                                                    }
                                                });

                                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                                                new DialogInterface.OnClickListener()
                                                {
                                                    public void onClick(DialogInterface dialog, int which)
                                                    {
                                                        dialog.dismiss();

                                                    }
                                                });
                                        alertDialog.show();



                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else
                                {
                                    // ImageviewFavHeart.setImageResource(R.mipmap.fav_orange_new);
                                    GetGroups();

                                }
                            }
                        });

                        JSONObject jsonObject_rating = jsonObjectProduct.getJSONObject(Keys.rating);
                        String count = jsonObject_rating.getString(Keys.count);
                        final String average = jsonObject_rating.getString(Keys.average);
                        final String value = jsonObject_rating.isNull(Keys.value) ? ""  :jsonObject_rating.getString(Keys.value);
                        TextView RatingTextview = (TextView)dialog.findViewById(R.id.RatingTextview);
                        RatingTextview.setText(average);
                        if (value!="")
                        {
                            ratingBar.setRating(Float.parseFloat(value));
                            ratingBar.setIsIndicator(true);
                        }

                        RatingTextview.setTypeface(montserrat_regular);

                        JSONObject jsonObject_likes = jsonObjectProduct.getJSONObject(Keys.likes);
                        String possitive = jsonObject_likes.getString(Keys.positive);
                        TextviewLike.setText(possitive);
                        String negetive = jsonObject_likes.getString(Keys.negative);
                        TextviewdisLike.setText(negetive);

                        //rb

                        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
                            @Override
                            public void onRatingChanged(RatingBar ratingBar, float v, boolean b)
                            {
                                Log.e("Ratings", value);


                                if (value != "")
                                {
                                    LinLayoutSubmitrating.setVisibility(View.GONE);
                                    ratingBar.setIsIndicator(true);
                                    Toast.makeText(Qrcoderesult.this, "You have already rated this product", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    LinLayoutSubmitrating.setVisibility(View.VISIBLE);
                                    TripManagement.saveValueuToPreferences(Qrcoderesult.this,"Rate", String.valueOf(v));
                                }


                            }
                        });
                        Textview_submit_rating.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                String UserRate = TripManagement.readValueFromPreferences(Qrcoderesult.this,"Rate","");
                                Log.e("UserRate",UserRate);

                                ratingBar.setIsIndicator(true);

                                Rate(Math.round(Float.parseFloat(UserRate)),TextViewRatings);
                            }
                        });





                        LinLayoutProductPhoto.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {

                                Intent intent = new Intent(Qrcoderesult.this,ProductPhotoViewActivity.class);
                                intent.putExtra("Photo",photo);
                                startActivity(intent);

                            }
                        });






                        LinlayoutLocation.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                dialog.dismiss();
                                ProductsLocationFragment productsLocationFragment = new ProductsLocationFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("location",location);
                                bundle.putString("latitude",latitude);
                                bundle.putString("longitude",longitude);
                                bundle.putString("Photo",photo);
                                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.add(R.id.QRActivity_FameID, productsLocationFragment);
                                fragmentTransaction.addToBackStack(null);
                                productsLocationFragment.setArguments(bundle);
                                fragmentTransaction.commit();
                            }
                        });

                        ImageView capturedPhotoImageview = (ImageView)dialog.findViewById(R.id.capturedPhotoImageview);
                        if (photo!=null)
                        {
                            Picasso.with(Qrcoderesult.this).load(photo).into(capturedPhotoImageview);

                        }

                        LinLayoutshare.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                dialog.dismiss();
                                TripManagement.saveValueuToPreferences(Qrcoderesult.this,"ShareScanResuldImages","ShareScanResuldImages");
                                ChatListFragment chatListFragment = new ChatListFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("BitmapImageShare", String.valueOf(hashedId));
                                Log.e("hashedId",hashedId);
                                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.add(R.id.QRActivity_FameID, chatListFragment);
                                fragmentTransaction.addToBackStack(null);
                                chatListFragment.setArguments(bundle);
                                fragmentTransaction.commit();

                            }
                        });


                        ImageView Imagearray0 = (ImageView) dialog.findViewById(R.id.Imagearray0);
                        ImageView Imagearray1 = (ImageView) dialog.findViewById(R.id.Imagearray1);
                        ImageView Imagearray2 = (ImageView) dialog.findViewById(R.id.Imagearray2);
                        ImageView Imagearray3 = (ImageView) dialog.findViewById(R.id.imagePhoto);

                        NameTextview.setText(name);

                        //Start of Description
                        if (description == "")
                        {
                            LinLayoutDescription.setVisibility(View.GONE);
                            descriptionIndicationTextview.setVisibility(View.GONE);
                        }
                        else
                        {

                            LinLayoutDescription.setVisibility(View.VISIBLE);
                            TextviewDescription.setText(description);
                            descriptionIndicationTextview.setVisibility(View.VISIBLE);
                            TextviewDescription.setSelected(true);


                            LinLayoutDescription.setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View view)
                                {
                                    final Dialog dialog_morque = new Dialog(Qrcoderesult.this);
                                    dialog_morque.setContentView(R.layout.layout_morquee);
                                    TextView tv = (TextView)dialog_morque.findViewById(R.id.tv);
                                    Button TVClose = (Button) dialog_morque.findViewById(R.id.TVClose);
                                    tv.setTypeface(montserrat_regular);
                                    TVClose.setTypeface(montserrat_regular);
                                    tv.setText(description);
                                    dialog_morque.show();

                                    TVClose.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View view)
                                        {
                                            dialog_morque.dismiss();
                                        }
                                    });

                                }
                            });



                        }
                        //End of Desription

                        //Start of Offers
                        if (offer == "")
                        {
                            LinLayoutOffers.setVisibility(View.GONE);
                            Textviewoffers.setVisibility(View.GONE);
                        }
                        else
                        {
                            LinLayoutOffers.setVisibility(View.VISIBLE);
                            TextviewOffers.setText(offer);
                            TextviewOffers.setSelected(true);
                            Textviewoffers.setVisibility(View.VISIBLE);
                        }

                        //End of Offers


                        // Phone

                        LinLayoutMobile.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                if (phone == "")
                                {

                                }
                                else
                                {
                                    AlertDialog alertDialog = new AlertDialog.Builder(Qrcoderesult.this).create();
                                    alertDialog.setTitle(R.string.alert);
                                    alertDialog.setMessage("Do you want to make a call?");
                                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                                            new DialogInterface.OnClickListener()
                                            {
                                                public void onClick(DialogInterface dialog, int which)
                                                {
                                                    dialog.dismiss();
                                                    callPhoneNumber();
                                                }
                                            });

                                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                                            new DialogInterface.OnClickListener()
                                            {
                                                public void onClick(DialogInterface dialog, int which)
                                                {
                                                    dialog.dismiss();

                                                }
                                            });
                                    alertDialog.show();
                                }
                            }

                        });



                        //Phone


                        // Email

                        LinlayoutEmail.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                if (email == "")
                                {

                                }
                                else
                                {
                                    startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+email)));
                                    // startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:angadiworldtech@gmail.com")));

                                }
                            }
                        });


                        //Email


                        // Website

                        LinlayoutWebsite.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                if (website == "")
                                {

                                }
                                else
                                {
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                                    intent.setData(Uri.parse(website));
                                    startActivity(intent);                                }

                            }
                        });

                        //Website
                    }
                    else if (jsonObject.getInt(Keys.success) == 0)
                    {
                        final Dialog dialog_invalid_qr = new Dialog(Qrcoderesult.this);
                        dialog_invalid_qr.setContentView(R.layout.layout_invalidqr);

                        TextView TVClose = (TextView) dialog_invalid_qr.findViewById(R.id.TVClose);
                        TextView TV_Invali = (TextView) dialog_invalid_qr.findViewById(R.id.TV_Invali);
                        TVClose.setTypeface(montserrat_regular);
                        TV_Invali.setTypeface(montserrat_regular);
                        dialog_invalid_qr.show();
                        dialog_invalid_qr.setCanceledOnTouchOutside(false);

                        TVClose.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                dialog_invalid_qr.dismiss();
                                startActivity(new Intent(Qrcoderesult.this,Qrcoderesult.class));
                            }
                        });


                    }
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(Qrcoderesult.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }
                , new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                progressDialog.dismiss();
                Toast.makeText(Qrcoderesult.this, error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("error",error.toString());


                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null)
                {
                    Log.e("Volley", "Error. HTTP Status Code:"+networkResponse.statusCode);
                }

                if (error instanceof TimeoutError)
                {
                    Log.e("Volley", "TimeoutError");
                }else if(error instanceof NoConnectionError){
                    Log.e("Volley", "NoConnectionError");
                } else if (error instanceof AuthFailureError) {
                    Log.e("Volley", "AuthFailureError");
                } else if (error instanceof ServerError) {
                    Log.e("Volley", "ServerError");
                } else if (error instanceof NetworkError) {
                    Log.e("Volley", "NetworkError");
                } else if (error instanceof ParseError) {
                    Log.e("Volley", "ParseError");
                }
            }
        }

        )
        {
            @Override
            protected Map<String,String> getParams()
            {
                Map<String,String> params = new HashMap<String, String>();
                return params;

            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
                    VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                    volleyError = error;
                }
                return volleyError;
            }

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Keys.Authorization,"Bearer "+TripManagement.readValueFromPreferences(Qrcoderesult.this,"accesstoken",""));
                return params;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy()
        {
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

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void callPhoneNumber()
    {
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(Qrcoderesult.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(Qrcoderesult.this, new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                startActivity(callIntent);

            }
            else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                startActivity(callIntent);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults)
    {
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhoneNumber();
            } else {
                Log.d("TAG", "Permission not Granted");
            }
        }
    }
    private void GetGroups()
    {
        //getting the tag from the edittext
        //final String tags = editTextTags.getText().toString().trim();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, GroupFavorites,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        progressDialog.dismiss();

                        GroupsAdapter groupsAdapter;
                        final Dialog  dialogGroups = new Dialog(Qrcoderesult.this);

                        dialogGroups.setContentView(R.layout.dialog_groups);
                        dialogGroups.show();

                        ListView ListViewGroups = (ListView)dialogGroups.findViewById(R.id.ListViewGroups);

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            if (jsonObject.getInt(Keys.success) == 1)
                            {
                                JSONObject jsonObjectData = jsonObject.getJSONObject(Keys.data);
                                JSONArray jsonArrayGroups = jsonObjectData.getJSONArray(Keys.groups);

                                for (int i=0;i<jsonArrayGroups.length();i++)
                                {
                                    JSONObject jsonObject11 = jsonArrayGroups.getJSONObject(i);
                                    String id = jsonObject11.getString(Keys.id);
                                    String name = jsonObject11.getString(Keys.name);

                                    PojoGroup = new Groups(id,name);
                                    PojoarrayListgroup.add(PojoGroup);


                                    groupsAdapter = new GroupsAdapter(Qrcoderesult.this,PojoarrayListgroup,id,name);
                                    ListViewGroups.setAdapter(groupsAdapter);


                                    ListViewGroups.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                    {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                                        {
                                             dialogGroups.dismiss();
                                             GroupId = PojoarrayListgroup.get(i).getId();
                                             AddTofavorites();


                                        }
                                    });

                                    TripManagement.saveValueuToPreferences(Qrcoderesult.this,"GroupIdFav",PojoarrayListgroup.get(i).getId());
                                    TripManagement.saveValueuToPreferences(Qrcoderesult.this,"GroupIdFav",GroupId);


                                    }
                                }
                            else if (jsonObject.getInt(Keys.success) == 0)
                            {
                                JSONObject jsonObject_errors = jsonObject.getJSONObject(Keys.errors);

                                Toast.makeText(Qrcoderesult.this, ""+jsonObject_errors.getJSONArray(Keys.name).get(0), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(Qrcoderesult.this, e.toString(), Toast.LENGTH_SHORT).show();
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
                params.put(Keys.Authorization,"Bearer "+TripManagement.readValueFromPreferences(Qrcoderesult.this,"accesstoken",""));
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */

            };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    private void AddTofavorites()
    {
        //getting the tag from the edittext
        //final String tags = editTextTags.getText().toString().trim();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, AddToFavorites,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        Log.d("FavoritesURL",AddToFavorites);
                        progressDialog.dismiss();
                        Log.e("StringRequest--->", String.valueOf(response));
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            Log.e("StringRequestdata--->", jsonObject.toString());
                            if (jsonObject.getInt(Keys.success) == 1)
                            {


                                JSONObject jsonObjectData = jsonObject.getJSONObject(Keys.data);
                                ImageviewFavHeart.setImageResource(R.mipmap.faved_heart);
                                Toast.makeText(Qrcoderesult.this, "Added To Favorites", Toast.LENGTH_SHORT).show();



                            }
                            else if (jsonObject.getInt(Keys.success) == 0)
                            {
                                JSONObject jsonObject_errors = jsonObject.getJSONObject(Keys.errors);
                                Toast.makeText(Qrcoderesult.this, ""+jsonObject_errors, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(Qrcoderesult.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
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
                params.put(Keys.product_id, ProductId);
                params.put(Keys.fpg_id,GroupId);


                return params;
            }

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Keys.Authorization,"Bearer "+TripManagement.readValueFromPreferences(Qrcoderesult.this,"accesstoken",""));
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

    private void RemoveFromfavorites()
    {
        //getting the tag from the edittext
        //final String tags = editTextTags.getText().toString().trim();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, AddToFavorites,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        Log.d("FavoritesURL",AddToFavorites);
                        progressDialog.dismiss();
                        Log.e("StringRequest--->", String.valueOf(response));
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            Log.e("StringRequestdata--->", jsonObject.toString());
                            if (jsonObject.getInt(Keys.success) == 1)
                            {


                                JSONObject jsonObjectData = jsonObject.getJSONObject(Keys.data);
                                ImageviewFavHeart.setImageResource(R.mipmap.fav_orange_new);
                                Toast.makeText(Qrcoderesult.this, "Removed From Favorites", Toast.LENGTH_SHORT).show();

                            }
                            else if (jsonObject.getInt(Keys.success) == 0)
                            {
                                JSONObject jsonObject_errors = jsonObject.getJSONObject(Keys.errors);
                                Toast.makeText(Qrcoderesult.this, ""+jsonObject_errors, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(Qrcoderesult.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
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
                params.put(Keys.product_id, ProductId);
                return params;
            }

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Keys.Authorization,"Bearer "+TripManagement.readValueFromPreferences(Qrcoderesult.this,"accesstoken",""));
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

    private void CreateChatURL() {
        //getting the tag from the edittext
        //final String tags = editTextTags.getText().toString().trim();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String CreateUserURL = TripManagement.BASE_URL+"chat";
        Log.e("CreateFirScan",CreateUserURL);

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, CreateUserURL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        progressDialog.dismiss();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            if (jsonObject.getInt(Keys.success) == 1) {
                                JSONObject jsonObject_data = jsonObject.getJSONObject(Keys.data);

                                JSONObject jsonObject_chat = jsonObject_data.getJSONObject(Keys.chat);


                                String chatIdFromCreateAPI = jsonObject_chat.getString(Keys.id);
                                String status = jsonObject_chat.getString(Keys.status);
                                String created_at = jsonObject_chat.getString(Keys.created_at);
                                String updated_at = jsonObject_chat.getString(Keys.updated_at);

                                JSONObject jsonObject_recipient = jsonObject_chat.getJSONObject(Keys.recipient);
                                String Recipient = jsonObject_recipient.getString(Keys.id);
                                String uniqueIdFromCreateAPI = jsonObject_recipient.getString(Keys.unique_id);
                                String nameFromCreateAPI = jsonObject_recipient.getString(Keys.name);
                                String avatarFromCreateAPI = jsonObject_recipient.getString(Keys.avatar);


                                ChatDetailsFragment chatDetailsFragment = new ChatDetailsFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("ChatId", chatIdFromCreateAPI);
                                bundle.putString("ProfileFromList", avatarFromCreateAPI);
                                bundle.putString("uniqueIDFromList", uniqueIdFromCreateAPI);
                                bundle.putString("NameFromList", nameFromCreateAPI);

                                TripManagement.saveValueuToPreferences(Qrcoderesult.this,"QRCHAT","QRCHAT");

                                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.add(R.id.QRActivity_FameID, chatDetailsFragment);
                                fragmentTransaction.addToBackStack(null);
                                chatDetailsFragment.setArguments(bundle);
                                fragmentTransaction.commit();

                            } else if (jsonObject.getInt(Keys.success) == 0)
                            {
                                JSONObject jsonObject_errors = jsonObject.getJSONObject(Keys.errors);
                                // JSONArray jsonArray_name = jsonObject_errors.getJSONArray(Keys.recipient_user_id);
                                Toast.makeText(Qrcoderesult.this, "" + jsonObject_errors, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Exeption2", e.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Qrcoderesult.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Keys.recipient_user_id, user_id);

                return params;
            }

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Keys.Authorization, "Bearer " + TripManagement.readValueFromPreferences(Qrcoderesult.this, "accesstoken", ""));
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */

        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    public void ViewProfile()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
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
                                String UserEmail = jsonObjectUser.getString(Keys.email);
                                TripManagement.saveValueuToPreferences(Qrcoderesult.this,"UserEmail",UserEmail);

                                String phone_varified = jsonObjectUser.getString(Keys.phone_verified);
                                String email_varified = jsonObjectUser.getString(Keys.email_verified);
                                TripManagement.saveValueuToPreferences( Qrcoderesult.this,"email_verified",email_varified);
                                TripManagement.saveValueuToPreferences( Qrcoderesult.this,"phone_verified",phone_varified);


                                if (TripManagement.readValueFromPreferences(Qrcoderesult.this,"LogoutClicked","").equals("LogoutClicked") && !TripManagement.readValueFromPreferences(Qrcoderesult.this,"FromMarkerWindow","").equals("FromMarkerWindow"))
                                {
                                    if (TripManagement.readValueFromPreferences(Qrcoderesult.this,"LoginSuccess","").equals("LoginSuccess") && !TripManagement.readValueFromPreferences(Qrcoderesult.this,"FromMarkerWindow","").equals("FromMarkerWindow"))
                                    {
                                        TripManagement.saveValueuToPreferences(Qrcoderesult.this,"Scanned","");
                                        if (TripManagement.readValueFromPreferences(Qrcoderesult.this,"phone_verified","").equals("0") && TripManagement.readValueFromPreferences(Qrcoderesult.this,"email_verified","").equals("0"))
                                        {
                                            startActivity(new Intent(Qrcoderesult.this,VarifyOTPActiivty.class));
                                        }
                                        else  if (TripManagement.readValueFromPreferences(Qrcoderesult.this,"phone_verified","").equals("1") && TripManagement.readValueFromPreferences(Qrcoderesult.this,"email_verified","").equals("1"))
                                        {
                                            if (TripManagement.readValueFromPreferences(Qrcoderesult.this,"LoadScanResFromChatDetails","").equals("LoadScanResFromChatDetails"))
                                            {
                                                TripManagement.saveValueuToPreferences(Qrcoderesult.this,"LoadScanResFromChatDetails","");
                                                ViewProductDetails(TripManagement.readValueFromPreferences(Qrcoderesult.this,"BarcodeValue",""));
                                            }
                                            else
                                            {

                                                Intent  intent = getIntent();
                                                String action = intent.getAction();
                                                Uri data = intent.getData();

                                                if (data != null)
                                                {
                                                    String Data = data.toString();
                                                    Log.e("DeepLinking ","action : "+ action);
                                                    Log.e("DeepLinking ","URI : "+ Data);
                                                    String result = Data.substring(Data.lastIndexOf("=") + 1);
                                                    System.out.println("DeepLinking " + result);
                                                    ViewProductDetails(result);
                                                } else {
                                                    // startActivity(new Intent(Qrcoderesult.this,HomeActivity.class));
                                                    Intent intent1 = new Intent(Qrcoderesult.this, HomeActivity.class);
                                                    startActivityForResult(intent1, RC_BARCODE_CAPTURE);
                                                }

                                            }
                                        }
                                        else  if (TripManagement.readValueFromPreferences(Qrcoderesult.this,"phone_verified","").equals("0") && (TripManagement.readValueFromPreferences(Qrcoderesult.this,"email_verified","").equals("1")))
                                        {
                                            startActivity(new Intent(Qrcoderesult.this,VarifyOTPActiivty.class));
                                        }
                                        else  if (TripManagement.readValueFromPreferences(Qrcoderesult.this,"phone_verified","").equals("1") && (TripManagement.readValueFromPreferences(Qrcoderesult.this,"email_verified","").equals("0")))
                                        {
                                             startActivity(new Intent(Qrcoderesult.this,VarifyEmailActivity.class));

                                        }
                                    }
                                    else
                                    {
                                        RegisterloginFragment fr = new RegisterloginFragment();
                                        FragmentTransaction ft = Qrcoderesult.this.getSupportFragmentManager().beginTransaction();
                                        ft.addToBackStack(null);
                                        ft.add(R.id.QRActivity_FameID, fr);
                                        ft.commit();
                                    }
                                }
                                else if (TripManagement.readValueFromPreferences(Qrcoderesult.this,"LoginSuccess","").equals("LoginSuccess") && !TripManagement.readValueFromPreferences(Qrcoderesult.this,"FromMarkerWindow","").equals("FromMarkerWindow"))
                                {
                                    if (TripManagement.readValueFromPreferences(Qrcoderesult.this,"phone_verified","").equals("0") && TripManagement.readValueFromPreferences(Qrcoderesult.this,"email_verified","").equals("0"))
                                    {

                                        startActivity(new Intent(Qrcoderesult.this,VarifyOTPActiivty.class));
                                    }
                                    else  if (TripManagement.readValueFromPreferences(Qrcoderesult.this,"phone_verified","").equals("1") && TripManagement.readValueFromPreferences(Qrcoderesult.this,"email_verified","").equals("1"))
                                    {
                                        if (TripManagement.readValueFromPreferences(Qrcoderesult.this,"LoadScanResFromChatDetails","").equals("LoadScanResFromChatDetails"))
                                        {
                                            TripManagement.saveValueuToPreferences(Qrcoderesult.this,"LoadScanResFromChatDetails","");
                                            ViewProductDetails(TripManagement.readValueFromPreferences(Qrcoderesult.this,"BarcodeValue",""));
                                        }
                                        else
                                        {


                                           Intent  intent = getIntent();
                                            String action = intent.getAction();
                                            Uri data = intent.getData();

                                            if (data != null)
                                            {
                                                String Data = data.toString();
                                                Log.e("DeepLinking ","action : "+ action);
                                                Log.d("DeepLinking ","URI : "+ Data);
                                                String result = Data.substring(Data.lastIndexOf("=") + 1);
                                                ViewProductDetails(result);
                                                System.out.println("DeepLinking " + result);
                                            } else {
                                               // startActivity(new Intent(Qrcoderesult.this,HomeActivity.class));
                                                Intent intent1 = new Intent(Qrcoderesult.this, HomeActivity.class);
                                                startActivityForResult(intent1, RC_BARCODE_CAPTURE);
                                                Log.e("NoDeeeplink ","NoDeeeplink");
                                            }

                                        }
                                    }
                                    else  if (TripManagement.readValueFromPreferences(Qrcoderesult.this,"phone_verified","").equals("0") && (TripManagement.readValueFromPreferences(Qrcoderesult.this,"email_verified","").equals("1")))
                                    {
                                        startActivity(new Intent(Qrcoderesult.this,VarifyOTPActiivty.class));
                                    }
                                    else  if (TripManagement.readValueFromPreferences(Qrcoderesult.this,"phone_verified","").equals("1") && (TripManagement.readValueFromPreferences(Qrcoderesult.this,"email_verified","").equals("0")))
                                    {
                                         startActivity(new Intent(Qrcoderesult.this,VarifyEmailActivity.class));

                                    }
                                }
                                else if (TripManagement.readValueFromPreferences(Qrcoderesult.this,"FromMarkerWindow","").equals("FromMarkerWindow"))
                                {

                                    TripManagement.saveValueuToPreferences(Qrcoderesult.this,"FromMarkerWindow","");

                                    String productId = getIntent().getStringExtra("ProductID");
                                    ViewProductDetailsWindow(productId);

                                }


                            }
                            else if (jsonObject.getInt(Keys.success) == 0)
                            {
                                JSONObject jsonObject_errors = jsonObject.getJSONObject(Keys.errors);
                                Toast.makeText(Qrcoderesult.this, ""+jsonObject_errors, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(Qrcoderesult.this, e.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("Exeption",e.toString());

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Log.e("Volley_error", String.valueOf(error.networkResponse.statusCode));
                        if (error.networkResponse.statusCode == TripManagement.ERRCODE_401)
                        {
                            TV_Invali.setText("Authentication failed");
                            dialog_invalid_qr.show();

                            TVClose.setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View view)
                                {
                                    dialog_invalid_qr.dismiss();
                                    TripManagement.saveValueuToPreferences(Qrcoderesult.this,"LogoutClicked","LogoutClicked");
                                    startActivity(new Intent(Qrcoderesult.this,LoginActivity.class));
                                    TripManagement.saveValueuToPreferences(Qrcoderesult.this,"LoginSuccess","");
                                    TripManagement.saveValueuToPreferences(Qrcoderesult.this,"Rate","");
                                    TripManagement.saveValueuToPreferences(Qrcoderesult.this,"GENDER","");
                                    TripManagement.saveValueuToPreferences(Qrcoderesult.this,"male","");
                                    TripManagement.saveValueuToPreferences(Qrcoderesult.this,"female","");
                                    Intercom.client().setLauncherVisibility(Intercom.Visibility.GONE);
                                }
                            });
                        }
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
                params.put(Keys.Authorization,"Bearer "+TripManagement.readValueFromPreferences(Qrcoderesult.this,"accesstoken",""));
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */

        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    public void Like()
    {
        //getting the tag from the edittext
        //final String tags = editTextTags.getText().toString().trim();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        final String likeURL = TripManagement.BASE_URL + "product/"+ProductId+"/like/1";
        Log.e("LikeURL",likeURL);

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, likeURL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        progressDialog.dismiss();
                        Log.e("LikeReponce", String.valueOf(response));
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            Log.e("LikeReponce", String.valueOf(jsonObject));
                            if (jsonObject.getInt(Keys.success) == 1)
                            {
                                Toast.makeText(Qrcoderesult.this, "You liked the Product", Toast.LENGTH_SHORT).show();
                                JSONObject jsonObjectData = jsonObject.getJSONObject(Keys.data);
                                JSONObject jsonObject_likes = jsonObjectData.getJSONObject(Keys.likes);
                                String possitive = jsonObject_likes.isNull(Keys.positive) ? "" : jsonObject_likes.getString(Keys.positive);
                                String negative = jsonObject_likes.isNull(Keys.negative) ? "" : jsonObject_likes.getString(Keys.negative);
                                Log.e("possitive",possitive);
                                Log.e("negative",negative);

                               if (possitive != "" && negative!="")
                               {
                                   TextviewLike.setText(possitive);
                                   TextviewdisLike.setText(negative);

                               }
                               else
                               {
                                   TextviewLike.setText("Like");
                                   TextviewdisLike.setText("Dislike");
                               }

                            }
                            else if (jsonObject.getInt(Keys.success) == 0)
                            {
                                if (jsonObject.getInt(Keys.status) == 400)
                                {
                                    TV_Invali.setText(jsonObject.get(Keys.message).toString());
                                    dialog_invalid_qr.show();

                                }
                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(Qrcoderesult.this, e.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("Exeption",e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(INPUT_METHOD_SERVICE);

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


                return params;
            }

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Keys.Authorization,"Bearer "+TripManagement.readValueFromPreferences(Qrcoderesult.this,"accesstoken",""));
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

    public void DisLike()
    {
        //getting the tag from the edittext
        //final String tags = editTextTags.getText().toString().trim();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        final String likeURL = TripManagement.BASE_URL + "product/"+ProductId+"/like/0";
        Log.e("DislikeURl",likeURL);

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, likeURL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        progressDialog.dismiss();
                        Log.e("DislikeURlRequest--->", String.valueOf(response));
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            Log.e("DislikeURl", String.valueOf(jsonObject));
                            if (jsonObject.getInt(Keys.success) == 1)
                            {
                                Toast.makeText(Qrcoderesult.this, "You disliked the Product", Toast.LENGTH_SHORT).show();

                                JSONObject jsonObjectData = jsonObject.getJSONObject(Keys.data);
                                JSONObject jsonObject_likes = jsonObjectData.getJSONObject(Keys.likes);
                                String possitive = jsonObject_likes.isNull(Keys.positive) ? "" : jsonObject_likes.getString(Keys.positive);
                                String negative = jsonObject_likes.isNull(Keys.negative) ? "" : jsonObject_likes.getString(Keys.negative);
                                Log.e("possitive",possitive);
                                Log.e("negative",negative);

                                if (possitive != "" && negative!="")
                                {
                                    TextviewLike.setText(possitive);
                                    TextviewdisLike.setText(negative);

                                }
                                else
                                {
                                    TextviewLike.setText("Like");
                                    TextviewdisLike.setText("Dislike");
                                }

                            }
                            else if (jsonObject.getInt(Keys.success) == 0)
                            {
                                if (jsonObject.getInt(Keys.status) == 400)
                                {
                                    TV_Invali.setText(jsonObject.get(Keys.message).toString());
                                    dialog_invalid_qr.show();

                                }
                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(Qrcoderesult.this, e.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("Exeption",e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(INPUT_METHOD_SERVICE);

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


                return params;
            }

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Keys.Authorization,"Bearer "+TripManagement.readValueFromPreferences(Qrcoderesult.this,"accesstoken",""));
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

    public void Rate(final int rating, final TextView textView)
    {
        //getting the tag from the edittext
        //final String tags = editTextTags.getText().toString().trim();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        final String RatingURL = TripManagement.BASE_URL + "product/"+ProductId+"/rate";
        Log.e("RatingURL",RatingURL);

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, RatingURL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        progressDialog.dismiss();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            Log.e("RateReponce", String.valueOf(jsonObject));
                            if (jsonObject.getInt(Keys.success) == 1)
                            {
                                Toast.makeText(Qrcoderesult.this, "You rated the Product", Toast.LENGTH_SHORT).show();
                                 LinLayoutSubmitrating.setVisibility(View.GONE);
                                JSONObject jsonObjectData = jsonObject.getJSONObject(Keys.data);
                                JSONObject jsonObject_rate = jsonObjectData.getJSONObject(Keys.rate);
                                String rate_value = jsonObject_rate.isNull(Keys.value) ? "" : jsonObject_rate.getString(Keys.value);
                                JSONObject jsonObject_rating = jsonObjectData.getJSONObject(Keys.rating);

                                String rating_count = jsonObject_rating.isNull(Keys.count) ? "" : jsonObject_rating.getString(Keys.count);
                                String rating_average = jsonObject_rating.isNull(Keys.average) ? "" : jsonObject_rating.getString(Keys.average);
                                String rating_value = jsonObject_rating.isNull(Keys.value) ? "" : jsonObject_rating.getString(Keys.value);


                                 textView.setText(rating_average);




                            }
                            else if (jsonObject.getInt(Keys.success) == 0)
                            {
                                if (jsonObject.getInt(Keys.status) == 400)
                                {
                                    LinLayoutSubmitrating.setVisibility(View.GONE);
                                    ratingBar.setRating(0F);
                                    TV_Invali.setText(jsonObject.get(Keys.message).toString());
                                    dialog_invalid_qr.show();

                                }
                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(Qrcoderesult.this, e.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("Exeption",e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(INPUT_METHOD_SERVICE);

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
                params.put(Keys.value, String.valueOf(rating));

                return params;
            }

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Keys.Authorization,"Bearer "+TripManagement.readValueFromPreferences(Qrcoderesult.this,"accesstoken",""));
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


}