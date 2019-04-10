package com.angadi.tripmanagementa.Fragments;

import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.angadi.tripmanagementa.Activities.HomeActivity;
import com.angadi.tripmanagementa.Adapters.OrdersAdapter;
import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.Extras.Keys;
import com.angadi.tripmanagementa.Extras.VolleyMultipartRequest;
import com.angadi.tripmanagementa.Model.Orders;
import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;

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

public class HistoryFragment extends Fragment
{
    @BindView(R.id.LinLayoutBack) LinearLayout LinLayoutBack;
    @BindView(R.id.LinLayoutListview) LinearLayout LinLayoutListview;
    @BindView(R.id.LinlayoutEmptyContainer) LinearLayout LinlayoutEmptyContainer;
    @BindView(R.id.TVNoOredersYet) TextView TVNoOredersYet;
    @BindView(R.id.HeaderTectview) TextView HeaderTectview;
    @BindView(R.id.OrdersListview) ListView OrdersListview;
    String OrderId,  product_id,  status,  name,  photo,  latitude,  longitude,  location, sub_total,  gst,  tax,  created_at;

    Typeface montserrat_bold,montserrat_regular;
    ArrayList<Orders> ordersArrayList= new ArrayList<>();
    Orders orderPojo;

    ProgressDialog progressDialog;
    OrdersAdapter ordersAdapter;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.layout_history,container,false);
        ButterKnife.bind(this,view);
        Fabric.with(getActivity(), new Crashlytics());

        montserrat_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MONTSERRAT-BOLD.OTF");
        montserrat_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Montserrat-Regular.ttf");

        setFonts();

        getOrderHistoryURL(1);

        return view;

    }

    private void setFonts()
    {
        HeaderTectview.setTypeface(montserrat_bold);
        TVNoOredersYet.setTypeface(montserrat_regular);
    }

    @OnClick(R.id.LinLayoutBack)
    public void setLinLayoutBack()
    {
        startActivity(new Intent(getActivity(),HomeActivity.class));
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
                    startActivity(new Intent(getActivity(),HomeActivity.class));
                    // handle back button's click listener
                    return true;
                }
                return false;
            }
        });
    }

    private void getOrderHistoryURL(int page)
    {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String getHistoryURL = TripManagement.BASE_URL+"orders?page="+page;
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
                                JSONArray jsonArray_orders = jsonObjectData.getJSONArray(Keys.orders);
                                Log.d("RespjsonArray_product", jsonObjectData.toString());


                                if (jsonArray_orders != null && jsonArray_orders.length() > 0)
                                {
                                    LinLayoutListview.setVisibility(View.VISIBLE);
                                    LinlayoutEmptyContainer.setVisibility(View.GONE);

                                    for (int i = 0; i < jsonArray_orders.length(); i++)
                                    {
                                        JSONObject jsonObject11 = jsonArray_orders.getJSONObject(i);
                                        OrderId = jsonObject11.getString(Keys.id);
                                         product_id = jsonObject11.isNull(Keys.product_id) ? "" : jsonObject11.getString(Keys.product_id);
                                         status = jsonObject11.isNull(Keys.status) ? "" : jsonObject11.getString(Keys.status);
                                         name = jsonObject11.getString(Keys.name);
                                         photo = jsonObject11.isNull(Keys.photo) ? "" : jsonObject11.getString(Keys.photo);
                                         latitude = jsonObject11.isNull(Keys.latitude) ? "" : jsonObject11.getString(Keys.latitude);
                                         longitude = jsonObject11.isNull(Keys.longitude) ? "" : jsonObject11.getString(Keys.longitude);
                                        location = jsonObject11.isNull(Keys.location) ? "" : jsonObject11.getString(Keys.location);
                                        sub_total = jsonObject11.isNull(Keys.sub_total) ? "" : jsonObject11.getString(Keys.sub_total);
                                        gst = jsonObject11.isNull(Keys.gst) ? "" : jsonObject11.getString(Keys.gst);
                                        tax = jsonObject11.isNull(Keys.tax) ? "" : jsonObject11.getString(Keys.tax);
                                        created_at = jsonObject11.isNull(Keys.created_at) ? "" : jsonObject11.getString(Keys.created_at);





                                        orderPojo = new Orders(OrderId, product_id,status,name, photo, latitude, longitude, location, sub_total, gst, tax, created_at);
                                        ordersArrayList.add(orderPojo);



                                    }

                                    ordersAdapter = new OrdersAdapter(getActivity(), ordersArrayList, OrderId, product_id, status, name, photo, latitude, longitude, location, sub_total, gst, tax, created_at);
                                    OrdersListview.setAdapter(ordersAdapter);

                                    OrdersListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                                        {
                                            OrderDetailsFragment orderDetailsFragment = new OrderDetailsFragment();
                                            Bundle bundle = new Bundle();
                                            bundle.putString("OrderId",OrderId);
                                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                            fragmentTransaction.add(R.id.frame_content,orderDetailsFragment);
                                            fragmentTransaction.addToBackStack(null);
                                            orderDetailsFragment.setArguments(bundle);
                                            fragmentTransaction.commit();


                                        }
                                    });



                                }
                                else
                                {
                                    LinLayoutListview.setVisibility(View.GONE);
                                    LinlayoutEmptyContainer.setVisibility(View.VISIBLE);
                                }
                            }
                            else if (jsonObject.getInt(Keys.success) == 0)
                            {
                                JSONObject jsonObject_errors = jsonObject.getJSONObject(Keys.errors);
                                //// JSONArray jsonArray_name = jsonObject_errors.getJSONArray(Keys.name);
                                Toast.makeText(getActivity(), ""+jsonObject_errors, Toast.LENGTH_SHORT).show();
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

}
