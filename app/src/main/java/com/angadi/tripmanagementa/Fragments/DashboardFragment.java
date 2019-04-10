package com.angadi.tripmanagementa.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.angadi.tripmanagementa.Activities.Constant;
import com.angadi.tripmanagementa.Activities.HomeActivity;
import com.angadi.tripmanagementa.Activities.LoginActivity;
import com.angadi.tripmanagementa.Activities.Qrcoderesult;
import com.angadi.tripmanagementa.Activities.database.DatabaseHelper;
import com.angadi.tripmanagementa.Adapters.ProductListAdapter;
import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.Custum.EndlessScrollListener;
import com.angadi.tripmanagementa.Extras.Keys;
import com.angadi.tripmanagementa.Extras.VolleyMultipartRequest;
import com.angadi.tripmanagementa.Model.Product;
import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

public class DashboardFragment extends Fragment
{

    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    public static DatabaseHelper db;
    String jsonBody;

    ArrayList<Product> pojo_listArrayList = new ArrayList<>();
    Product pojoList;

    ProductListAdapter listviewAdapter;
    String  name,description,phone, price,website, location, validitystart,validitysend,offer,quality,quantity,email,photo,landline,stdcode,hashed_id,std_code,createdAt,attachment,product,id,favorite, currentPage,lastPage,hashed_id_url,lattitude,longitude;


    @BindView(R.id.LinLayoutBack) LinearLayout LinLayoutBack;
    @BindView(R.id.LinlayoutEmptyContainer) LinearLayout LinlayoutEmptyContainer;
    @BindView(R.id.LinlayoutGridview) LinearLayout LinlayoutGridview;
    @BindView(R.id.recyclerview) GridView listview;
    @BindView(R.id.DashboardIndicationTV) TextView DashboardIndicationTV;
    @BindView(R.id.TvNoChatsyet) TextView TvNoChatsyet;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.dashboard,container ,false);
        ButterKnife.bind(this, view);
        Fabric.with(getActivity(), new Crashlytics());

        db = new DatabaseHelper(getActivity());

        final Typeface montserrat_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MONTSERRAT-BOLD.OTF");
        final Typeface montserrat_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Montserrat-Regular.ttf");
        DashboardIndicationTV.setTypeface(montserrat_bold);
        TvNoChatsyet.setTypeface(montserrat_regular);


        getHistoryURL(1);

        return view;
    }

    @OnClick(R.id.LinLayoutBack)
    public void setLinLayoutBack()
    {
        startActivity(new Intent(getActivity(),Qrcoderesult.class));
    }

    private void getHistoryURL(int page)
    {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String getHistoryURL = TripManagement.BASE_URL+"products?page="+page;
        Log.e("ProductsList",getHistoryURL);

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, getHistoryURL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        progressDialog.dismiss();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            Log.e("Responce---->", jsonObject.toString());
                            if (jsonObject.getInt(Keys.success) == 1)
                            {

                                Log.e("Responce---->", jsonObject.toString());
                                JSONObject jsonObjectData = jsonObject.getJSONObject(Keys.data);
                                String next_page_url = jsonObjectData.getString(Keys.next_page_url);
                                int currentPage = jsonObjectData.getInt(Keys.current_page);
                               int   lastPage = jsonObjectData.getInt(Keys.last_page);
                                JSONArray jsonArray_product = jsonObjectData.getJSONArray(Keys.products);
                                Log.d("RespjsonArray_product", jsonObjectData.toString());

                                jsonBody = jsonObject.toString();

                                db.del_DataSB();
                                db.del_ExampleSB();
                                db.del_LikesSB();
                                db.del_ProductsSB();
                                db.del_RatingSB();
                                db.del_SubProductSB();

                                JSONDataExtract();


                                if (jsonArray_product != null && jsonArray_product.length() > 0)
                                {
                                    LinlayoutGridview.setVisibility(View.VISIBLE);
                                    LinlayoutEmptyContainer.setVisibility(View.GONE);

                                    for (int i = 0; i < jsonArray_product.length(); i++)
                                    {
                                        JSONObject jsonObject11 = jsonArray_product.getJSONObject(i);
                                        String id = jsonObject11.getString(Keys.id);
                                        name = jsonObject11.getString(Keys.name);
                                        description = jsonObject11.isNull(Keys.description) ? "" : jsonObject11.getString(Keys.description);
                                        email = jsonObject11.isNull(Keys.email) ? "" : jsonObject11.getString(Keys.email);
                                        phone = jsonObject11.isNull(Keys.phone) ? "" : jsonObject11.getString(Keys.phone);
                                        price = jsonObject11.isNull(Keys.price) ? "" : jsonObject11.getString(Keys.price);
                                        website = jsonObject11.isNull(Keys.website) ? "" : jsonObject11.getString(Keys.website);
                                        quality = jsonObject11.isNull(Keys.quality) ? "" : jsonObject11.getString(Keys.quality);
                                        quantity = jsonObject11.isNull(Keys.quantity) ? "" : jsonObject11.getString(Keys.quantity);
                                        validitystart = jsonObject11.isNull(Keys.validity_start) ? "" : jsonObject11.getString(Keys.validity_start);
                                        location = jsonObject11.isNull(Keys.location) ? "" : jsonObject11.getString(Keys.location);
                                        photo = jsonObject11.isNull(Keys.photo) ? "" : jsonObject11.getString(Keys.photo);
                                        offer = jsonObject11.isNull(Keys.offer) ? "" : jsonObject11.getString(Keys.offer);
                                        validitysend = jsonObject11.isNull(Keys.validity_end) ? "" : jsonObject11.getString(Keys.validity_end);
                                        stdcode = jsonObject11.isNull(Keys.std_code) ? "" : jsonObject11.getString(Keys.std_code);
                                        landline = jsonObject11.isNull(Keys.landline) ? "" : jsonObject11.getString(Keys.landline);
                                        hashed_id = jsonObject11.isNull(Keys.hashed_id) ? "" : jsonObject11.getString(Keys.hashed_id);
                                        createdAt = jsonObject11.isNull(Keys.created_at) ? "" : jsonObject11.getString(Keys.created_at);
                                        hashed_id_url = jsonObject11.isNull(Keys.hashed_id_url) ? "" : jsonObject11.getString(Keys.hashed_id_url);
                                        lattitude = jsonObject11.isNull(Keys.latitude) ? "" : jsonObject11.getString(Keys.latitude);
                                        longitude = jsonObject11.isNull(Keys.longitude) ? "" : jsonObject11.getString(Keys.longitude);




                                        pojoList = new Product(id, name, phone, email, createdAt, location, stdcode, landline, website, description, price, validitystart, validitysend, quality, quantity, offer, photo, attachment, product, hashed_id, favorite,hashed_id_url,lattitude,longitude);
                                        pojo_listArrayList.add(pojoList);



                                    }

                                    listviewAdapter = new ProductListAdapter(getActivity(), pojo_listArrayList, id, name, phone, email, createdAt, location, std_code, landline, website, description, price, validitystart, validitysend, quality, quantity, offer, photo, attachment, product, hashed_id);
                                    listview.setAdapter(listviewAdapter);

                                    if (lastPage > currentPage)
                                    {
                                        listview.setOnScrollListener(new EndlessScrollListener() {
                                            @Override
                                            public boolean onLoadMore(int page, int totalItemsCount) {
                                                // Triggered only when new data needs to be appended to the list
                                                // Add whatever code is needed to append new items to your AdapterView
                                                if (page>1 && page<3)
                                                {
                                                    getHistoryURL(page);
                                                }


                                                // or loadNextDataFromApi(totalItemsCount);
                                                return true; // ONLY if more data is actually being loaded; false otherwise.
                                            }
                                        });
                                    }

                                    listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                        @Override
                                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l)
                                        {

                                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                                            alertDialog.setTitle("Alert");
                                            alertDialog.setMessage("Are your sure you want to delete?");
                                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Delete",
                                                    new DialogInterface.OnClickListener()
                                                    {
                                                        public void onClick(DialogInterface dialog, int which)
                                                        {
                                                            dialog.dismiss();
                                                            Deleteproduct(pojo_listArrayList.get(i).getId());
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
                                            return true;
                                        }
                                    });



                                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                    {
                                        @Override
                                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                                            TripManagement.saveValueuToPreferences(getActivity(), "ClickedOnList", "ClickedOnList");
                                            Constant.productDetailQRFragmentStatus = "Edit";
                                            Constant.getDashboardProductPosition = String.valueOf(i);
                                            ProductDetailsQRFragment dashboardFragment = new ProductDetailsQRFragment();
                                            Bundle bundle = new Bundle();
                                            bundle.putString("Name", pojo_listArrayList.get(i).getName());
                                            bundle.putString("id", pojo_listArrayList.get(i).getId());
                                            bundle.putString("Desrciption", pojo_listArrayList.get(i).getDescription());
                                            bundle.putString("Phone", pojo_listArrayList.get(i).getPhone());
                                            bundle.putString("Price", pojo_listArrayList.get(i).getPrice());
                                            bundle.putString("Website", pojo_listArrayList.get(i).getWebsite());
                                            bundle.putString("Email", pojo_listArrayList.get(i).getEmail());
                                            bundle.putString("Location", pojo_listArrayList.get(i).getLocation());
                                            bundle.putString("Photo", pojo_listArrayList.get(i).getPhoto());
                                            bundle.putString("ValidityStart", pojo_listArrayList.get(i).getValidity_start());
                                            bundle.putString("ValidityEnd", pojo_listArrayList.get(i).getValidity_end());
                                            bundle.putString("stdcode", pojo_listArrayList.get(i).getStd_code());
                                            bundle.putString("landline", pojo_listArrayList.get(i).getLandline());
                                            bundle.putString("hashed_id", pojo_listArrayList.get(i).getHashed_id());
                                            TripManagement.saveValueuToPreferences(getActivity(), "hashed_id", pojo_listArrayList.get(i).getHashed_id_url());
                                            bundle.putString("createdAt", pojo_listArrayList.get(i).getCreated_at());
                                            bundle.putString("quality", pojo_listArrayList.get(i).getQuality());
                                            bundle.putString("quantity", pojo_listArrayList.get(i).getQuantity());
                                            bundle.putString("latitude", pojo_listArrayList.get(i).getLatitude());
                                            bundle.putString("longitude", pojo_listArrayList.get(i).getLongitude());


                                            TripManagement.saveValueuToPreferences(getActivity(), "ProductIDforUpdate", pojo_listArrayList.get(i).getId());

                                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                            fragmentTransaction.add(R.id.frame_content, dashboardFragment);
                                            fragmentTransaction.addToBackStack(null);
                                            dashboardFragment.setArguments(bundle);
                                            fragmentTransaction.commit();


                                            // Toast.makeText(getActivity(), hj, Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                }
                                else
                                    {
                                        LinlayoutGridview.setVisibility(View.GONE);
                                        LinlayoutEmptyContainer.setVisibility(View.VISIBLE);


                                        //list is empty
                                }
                            }
                                else if (jsonObject.getInt(Keys.success) == 0)
                                {
                                    JSONObject jsonObject_errors = jsonObject.getJSONObject(Keys.errors);
                                    //// JSONArray jsonArray_name = jsonObject_errors.getJSONArray(Keys.name);
                                    Toast.makeText(getActivity(), ""+jsonObject_errors.getJSONArray(Keys.name).get(0), Toast.LENGTH_SHORT).show();
                                }




                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                params.put(Keys.Authorization,"Bearer "+TripManagement.readValueFromPreferences(getActivity(),"accesstoken",""));
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */

        };

        //adding the request to volley
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }


    @Override
    public void onResume()
    {
        super.onResume();
        if(getView() == null)
        {
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK)
                {
                    // handle back button's click listener
                    setLinLayoutBack();

                    return true;
                }
                return false;
            }
        });
    }

    private void Deleteproduct(String productId)
    {
        //getting the tag from the edittextd
        //final String tags = editTextTags.getText().toString().trim();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String DeleteproductURl = TripManagement.BASE_URL+"product/"+productId+"/delete";
        Log.e("DeleteproductURl",DeleteproductURl);

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, DeleteproductURl,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        progressDialog.dismiss();
                        Log.d("StringRequest--->", String.valueOf(response));
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            Log.d("StringRequestdata--->", String.valueOf(response.data));
                            if (jsonObject.getInt(Keys.success) == 1)
                            {
                                getHistoryURL(1);
                            }
                            else if (jsonObject.getInt(Keys.success) == 0)
                            {
                                Toast.makeText(getActivity(), ""+jsonObject.getString(Keys.message), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            // Toast.makeText(HomeActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                //  params.put(Keys.name,UsernameEdittext.getText().toString());

                return params;
            }

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Keys.Authorization,"Bearer "+TripManagement.readValueFromPreferences(getActivity(),"accesstoken",""));
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
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }


    public void JSONDataExtract() {
        try {
            JSONObject getJsonBody = new JSONObject(jsonBody);
            int dataSuccess =  getJsonBody.getInt("success");
            int dataStatus =  getJsonBody.getInt("status");

            db.insertExample_SB(dataSuccess, dataStatus);

            JSONObject getDataJSON = getJsonBody.getJSONObject("data");
            JSONArray getProductArr = getDataJSON.getJSONArray("products");
            int ProductArrayCount = getProductArr.length();
            for(int tec=0; tec < getProductArr.length(); tec++) {
                JSONObject getProductObj = getProductArr.getJSONObject(tec);
                int id = getProductObj.getInt("id");
                int user_id = getProductObj.getInt("user_id");
                int type = getProductObj.getInt("type");
                String name = getProductObj.isNull("name") ? "" : getProductObj.getString("name");
                String plus_code = getProductObj.isNull("plus_code") ? "" : getProductObj.getString("plus_code");
                double latitude = getProductObj.getDouble("latitude");
                double longitude = getProductObj.getDouble("longitude");
                String location = getProductObj.isNull("location") ? "" : getProductObj.getString("location");
                String photo = getProductObj.isNull("photo") ? "" : getProductObj.getString("photo");
                String isd_code = getProductObj.isNull("isd_code") ? "" : getProductObj.getString("isd_code");
                String phone = getProductObj.isNull("phone") ? "" : getProductObj.getString("phone");
                String std_code = getProductObj.isNull("std_code") ? "" : getProductObj.getString("std_code");
                String landline = getProductObj.isNull("landline") ? "" : getProductObj.getString("landline");
                String email = getProductObj.isNull("email") ? "" : getProductObj.getString("email");
                String website = getProductObj.isNull("website") ? "" : getProductObj.getString("website");
                String description = getProductObj.isNull("description") ? "" : getProductObj.getString("description");
                String price = getProductObj.isNull("price") ? "" : getProductObj.getString("price");
                String validity_start = getProductObj.isNull("validity_start") ? "" : getProductObj.getString("validity_start");
                String validity_end = getProductObj.isNull("validity_end") ? "" : getProductObj.getString("validity_end");
                String quality = getProductObj.isNull("quality") ? "" : getProductObj.getString("quality");
                int quantity = getProductObj.getInt("quantity");
                String offer = getProductObj.isNull("offer") ? "" : getProductObj.getString("offer");
                String attachment = getProductObj.isNull("attachment") ? "" : getProductObj.getString("attachment");
                String image = getProductObj.isNull("image") ? "" : getProductObj.getString("image");
                String created_at = getProductObj.isNull("created_at") ? "" : getProductObj.getString("created_at");
                String gst = getProductObj.isNull("gst") ? "" : getProductObj.getString("gst");
                String hashed_id = getProductObj.isNull("hashed_id") ? "" : getProductObj.getString("hashed_id");
                String hashed_id_url = getProductObj.isNull("hashed_id_url") ? "" : getProductObj.getString("hashed_id_url");

                JSONObject getRatingInfo = getProductObj.getJSONObject("rating");
                int count = getRatingInfo.getInt("count");
                int average = getRatingInfo.getInt("average");
                String value = getRatingInfo.isNull("value") ? "" : getRatingInfo.getString("value");

                db.insertRating_SB(ProductArrayCount, tec, count, average, value);

                JSONObject getLikesInfo = getProductObj.getJSONObject("likes");
                int negative = getLikesInfo.getInt("negative");
                int positive = getLikesInfo.getInt("positive");

                db.insertLikes_SB(ProductArrayCount, tec, positive, negative);

                String favourite = getProductObj.isNull("favourite") ? "" : getProductObj.getString("favourite");

                db.insertProducts_SB(ProductArrayCount, tec, id, user_id, type, name, plus_code, latitude, longitude, location,
                        photo, isd_code, phone, std_code, landline, email, website, description, price, validity_start, validity_end, quality,
                        quantity, offer, attachment, image, created_at, gst, hashed_id, hashed_id_url, favourite);

                JSONArray getSubProductsArr = getProductObj.getJSONArray("sub_products");
                int SubProductArrayCount = getSubProductsArr.length();
                for (int ch=0; ch < getSubProductsArr.length(); ch++) {
                    JSONObject getSubProductObj = getSubProductsArr.getJSONObject(ch);
                    int spid = getSubProductObj.getInt("id");
                    String spname = getSubProductObj.isNull("name") ? "" : getSubProductObj.getString("name");
                    String spprice = getSubProductObj.isNull("price") ? "" : getSubProductObj.getString("price");
                    String spphoto = getSubProductObj.isNull("photo") ? "" : getSubProductObj.getString("photo");

                    db.insertSubProduct_SB(ProductArrayCount, tec, SubProductArrayCount, ch,
                            spid, spname, spprice, spphoto);
                }
            }

            int current_page =  getDataJSON.getInt("current_page");
            int last_page =  getDataJSON.getInt("last_page");
            String next_page_url = getDataJSON.isNull("next_page_url") ? "" : getDataJSON.getString("next_page_url");

            db.insertData_SB(current_page, last_page, next_page_url);

            Log.d("JSON_Extract","Success");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("JSON_Extract",""+e.getMessage());
        }
    }



}
