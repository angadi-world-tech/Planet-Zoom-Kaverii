package com.angadi.tripmanagementa.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class OrderDetailsFragment extends Fragment
{
    String OrderId,id,product_id,
            status,name,photo,latitude,longitude,location,sub_total,gst,tax,created_at,items_id,items_order_id,
            items_sub_product_id,items_quantity,items_availability,items_name,items_price,items_photo,items_created_at;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.layout_orderdetails,container,false);
        ButterKnife.bind(this,view);
        Fabric.with(getActivity(), new Crashlytics());

        OrderId = getArguments().getString("OrderId");

        ViewOrder();

        return view;
    }


    public void ViewOrder()
    {
        String ViewOrderURL = TripManagement.BASE_URL+"order/"+OrderId;
        Log.e("ViewOrderURL",ViewOrderURL);

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, ViewOrderURL, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response)
            {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(new String(response.data));
                    Log.e("Responce_ViewOrder",jsonObject.toString());

                    if (jsonObject.getInt(Keys.success) == TripManagement.SUCCESSCODE_1)
                    {
                        JSONObject jsonObjectData = jsonObject.getJSONObject(Keys.data);
                        JSONObject jsonObjectOrders = jsonObjectData.getJSONObject(Keys.order);

                         id = jsonObjectOrders.isNull(Keys.id)  ? "": jsonObjectOrders.getString(Keys.id);
                         product_id = jsonObjectOrders.isNull(Keys.product_id)  ? "": jsonObjectOrders.getString(Keys.product_id);
                         status = jsonObjectOrders.isNull(Keys.status)  ? "": jsonObjectOrders.getString(Keys.status);
                         name = jsonObjectOrders.isNull(Keys.name)  ? "": jsonObjectOrders.getString(Keys.name);
                         photo = jsonObjectOrders.isNull(Keys.photo)  ? "": jsonObjectOrders.getString(Keys.photo);
                         latitude = jsonObjectOrders.isNull(Keys.latitude)  ? "": jsonObjectOrders.getString(Keys.latitude);
                         longitude = jsonObjectOrders.isNull(Keys.longitude)  ? "": jsonObjectOrders.getString(Keys.longitude);
                         location = jsonObjectOrders.isNull(Keys.location)  ? "": jsonObjectOrders.getString(Keys.location);
                         sub_total = jsonObjectOrders.isNull(Keys.sub_total)  ? "": jsonObjectOrders.getString(Keys.sub_total);
                         gst = jsonObjectOrders.isNull(Keys.gst)  ? "": jsonObjectOrders.getString(Keys.gst);
                         tax = jsonObjectOrders.isNull(Keys.tax)  ? "": jsonObjectOrders.getString(Keys.tax);
                         created_at= jsonObjectOrders.isNull(Keys.created_at)  ? "": jsonObjectOrders.getString(Keys.created_at);

                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        }
        )
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Keys.Authorization, "Bearer " + TripManagement.readValueFromPreferences(getActivity(), "accesstoken", ""));
                return params;
            }
        };
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }
}
