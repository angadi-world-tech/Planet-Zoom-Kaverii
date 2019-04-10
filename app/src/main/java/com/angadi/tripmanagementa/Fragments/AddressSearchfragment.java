package com.angadi.tripmanagementa.Fragments;


import android.app.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.angadi.tripmanagementa.Activities.HomeActivity;
import com.angadi.tripmanagementa.Activities.LoginActivity;
import com.angadi.tripmanagementa.Activities.Qrcoderesult;
import com.angadi.tripmanagementa.Adapters.PlacesAutoCompleteAdapter;
import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.Extras.Keys;
import com.angadi.tripmanagementa.Extras.VolleyMultipartRequest;
import com.angadi.tripmanagementa.Model.DirectionsJSONParser;
import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;
import static com.google.android.gms.maps.CameraUpdateFactory.newLatLng;
import static com.google.android.gms.maps.CameraUpdateFactory.zoomTo;

public class AddressSearchfragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener {
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    SupportMapFragment mapFragment;
    public static String full_address="";
    public static  Address address_source = null, address_destination = null,address_current = null;
    public static LatLng  latLng_source=null,latLng_destination=null,latlngcurrent = null;
    private static final int LOCATION_REQUEST_CODE = 101;
    private LatLng delhi = null;
    private LatLng chandigarh = null;
    List<Marker> markers = new ArrayList<Marker>();

    double currentLatitude;
    double currentLongitude;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String description = "";
    AutoCompleteTextView autocomplete, autocomplete1;
    private static final String TAG = AddressSearchfragment.class.getSimpleName();
    List<HashMap<String, String>> path;
    PolylineOptions lineOptions;



    public static final int PATTERN_DASH_LENGTH_PX = 20;
    public static final int PATTERN_GAP_LENGTH_PX = 20;
    public static final PatternItem DOT = new Dot();
    public static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    public static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    public static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);



    @BindView(R.id.LinLayoutCurrentlocation) LinearLayout LinLayoutCurrentlocation;
    @BindView(R.id.Linlayoutback) LinearLayout Linlayoutback;
    @BindView(R.id.LinlayoutEmptyContainer) LinearLayout LinlayoutEmptyContainer;
    @BindView(R.id.LinlayoutMap) LinearLayout LinlayoutMap;
    @BindView(R.id.extviewDescription) TextView extviewDescription;
    @BindView(R.id.TextviewEmptyContainer) TextView TextviewEmptyContainer;
    @BindView(R.id.IndicationTextSearchproduct) TextView IndicationTextSearchproduct;
    @BindView(R.id.ButtonOk) Button ButtonOk;


    TextView TVClose,TV_Invali;
    Dialog dialog_invalid_qr;
    LinearLayout LinLayoutClose;





    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
         mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map1);
         mapFragment.getMapAsync(this);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_addressfragment, container, false);
        ButterKnife.bind(this, view);
        Fabric.with(getActivity(), new Crashlytics());

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        final Typeface montserrat_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Montserrat-Regular.ttf");
        Typeface montserrat_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MONTSERRAT-BOLD.OTF");




        autocomplete = (AutoCompleteTextView) view.findViewById(R.id.autocomplete);
        autocomplete1 = (AutoCompleteTextView) view.findViewById(R.id.autocomplete1);

        autocomplete.setTypeface(montserrat_regular);
        TextviewEmptyContainer.setTypeface(montserrat_regular);
        ButtonOk.setTypeface(montserrat_regular);
        IndicationTextSearchproduct.setTypeface(montserrat_bold);


        hideSoftKeyboard(getActivity());
        checkLocationandAddToMap();


        dialog_invalid_qr = new Dialog(getActivity());
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

        return view;
    }

    @OnClick(R.id.LinLayoutCurrentlocation)
    public void setLinLayoutCurrentlocation()
    {
        if (!autocomplete.getText().toString().isEmpty())
        {
            GetProducts();
        }
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        checkLocationandAddToMap();
        }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        checkLocationandAddToMap();
        mMap.getUiSettings().setZoomControlsEnabled(true);
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getActivity(), R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(currentLatitude, currentLongitude)));


    }

    public void checkLocationandAddToMapCurrent()
    {
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Requesting the Location permission
            ActivityCompat.requestPermissions(getActivity(), new String[] {
                    android.Manifest.permission.ACCESS_FINE_LOCATION
            }, LOCATION_REQUEST_CODE);
            return;
        }
        //Fetching the last known location using the Fus

        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null)
        {
            setaddress(location.getLatitude(),location.getLongitude());
        }

    }

    private void checkLocationandAddToMap()
    {
        //Checking if the user has granted the permission
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Requesting the Location permission
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION
            }, LOCATION_REQUEST_CODE);
            return;
        }
        //Fetching the last known location using the Fus

        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);


        if (location != null)
        {
            Log.e("TAG", "GPS is on");
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();

            LatLng loc1 = new LatLng(currentLatitude, currentLongitude);
           // mMap.addMarker(new MarkerOptions().position(loc1).title("You are Here").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatitude, currentLongitude), 15));
            mMap.animateCamera(zoomTo(50), 1500, null);

        }
    }

    public void onStart() {
        super.onStart();
        //  Toast.makeText(getActivity(), "onStart", Toast.LENGTH_SHORT).show();
        googleApiClient.connect();
        String location_source = autocomplete.getText().toString();
        List<Address> addressList = null;
        if (location_source != null || !location_source.equals("")) {
            Geocoder geocoder = new Geocoder(getActivity());
            try {
                addressList = geocoder.getFromLocationName(location_source, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addressList != null && addressList.size() > 0) {
                address_destination = addressList.get(0);
                latLng_destination = new LatLng(address_destination.getLatitude(), address_destination.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng_destination).title(address_destination.getAddressLine(0)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).draggable(false).visible(true));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng_destination));


                hideSoftKeyboard(getActivity());
            }
        }


    }

    public void onStop() {
        super.onStop();

        googleApiClient.disconnect();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case LOCATION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED)
                {
                    checkLocationandAddToMap();
                } else
                    Toast.makeText(getActivity(), "Location Permission Denied", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null)
        {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

        }    }









    public String setaddress(double latitude, double longitude)
    {
        Geocoder geocoder;
        List<Address> addressList;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {
            addressList = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                Log.e("adresskavch","-->"+address);
                Log.e("street_deatial","-->"+address.getAddressLine(0));
                Log.e("city_name","-->"+address.getLocality());
                Log.e("state_name","-->"+address.getAdminArea());
                Log.e("countryCode","-->"+address.getCountryCode());

                address_current = addressList.get(0);
                latlngcurrent = new LatLng(address_current.getLatitude(),address_current.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latlngcurrent).title(address_current.getAddressLine(0)).draggable(false).visible(true));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address_current.getLatitude(), address_current.getLongitude()), 15));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latlngcurrent));


                if (address.getAddressLine(1) == null)
                {
                    full_address=address.getAddressLine(0);
                    autocomplete.setText(full_address);

                }
                else if (address.getAdminArea()== null)
                {
                    full_address=address.getAddressLine(0)+" "+address.getAddressLine(1);
                    Log.e("ADDRESS",full_address);
                    autocomplete.setText(full_address);

                }
                else if (address.getLocality()== null)
                {
                    full_address = address.getAddressLine(0)+" "+address.getAddressLine(1)+" "+" "+address.getAdminArea();
                    autocomplete.setText(full_address);

                }
                else
                {
                    full_address = address.getAddressLine(0)+" "+address.getAddressLine(1)+" "+address.getLocality()+" "+address.getAdminArea();
                    autocomplete.setText(full_address);
                }

            }
        } catch (IOException e) {

            Toast.makeText(getActivity(), "Please Reboot/Restart your Device to get Current Address", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return null;
    }

    @OnClick(R.id.Linlayoutback)
    public void setLinlayoutback()
    {
        startActivity(new Intent(getActivity(), Qrcoderesult.class));
    }





    private void GetProducts()
    {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String searchURL =TripManagement.BASE_URL+"product/search?keyword="+autocomplete.getText().toString()+"&latitude="+currentLatitude+"&longitude="+currentLongitude;
        Log.e("SearchURL",searchURL);


        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, searchURL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        markers.clear();
                        progressDialog.dismiss();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            if (jsonObject.getInt(Keys.success) == 1)
                            {
                                JSONObject jsonObjectData = jsonObject.getJSONObject(Keys.data);
                                Log.e("JsonObjectDataForSearchAPI", String.valueOf(jsonObjectData));
                                JSONArray jsonArrayProducts = jsonObjectData.getJSONArray(Keys.products);
                                Log.e("SIZE", String.valueOf(markers.size()));

                                if (jsonArrayProducts != null && jsonArrayProducts.length()>0)
                                {
                                    LinlayoutMap.setVisibility(View.VISIBLE);
                                    LinlayoutEmptyContainer.setVisibility(View.GONE);

                                    for (int i=0;i<jsonArrayProducts.length();i++)
                                    {
                                        JSONObject object = jsonArrayProducts.getJSONObject(i);
                                        final String productID = object.getString(Keys.id);
                                        String productName = object.getString(Keys.name);
                                        Log.e("ProductName",productName);
                                        double lattitude = object.getDouble(Keys.latitude);
                                        double longitude = object.getDouble(Keys.longitude);
                                        String location = object.getString(Keys.location);
                                        String pluscode = object.getString(Keys.plus_code);
                                        String photo = object.getString(Keys.photo);
                                        String distance = object.getString(Keys.distance);

                                        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                                        if (getActivity().getCurrentFocus() != null)
                                        {
                                            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                                        }


                                        LatLng latlng = new LatLng(lattitude, longitude);

                                        Marker marker =  mMap.addMarker(new MarkerOptions().title(productName)
                                                .position(new LatLng(lattitude, longitude))
                                        );
                                        marker.setTag(productID);
                                        markers.add(marker);

                                        Log.e("SIZEE", String.valueOf(markers.size()));
                                        // Moving CameraPosition to last clicked position
                                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));

                                        // Setting the zoom level in the map on last position is clicked
                                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latlng));

                                        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                                        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                            @Override
                                            public void onInfoWindowClick(Marker marker)
                                            {
                                                TripManagement.saveValueuToPreferences(getActivity(),"FromMarkerWindow","FromMarkerWindow");
                                                Intent intent = new Intent(getActivity(),Qrcoderesult.class);
                                                Toast.makeText(getActivity(), ""+marker.getId(), Toast.LENGTH_SHORT).show();
                                                Toast.makeText(getActivity(), ""+marker.getTitle(), Toast.LENGTH_SHORT).show();
                                                intent.putExtra("ProductID",(String) marker.getTag());
                                                startActivity(intent);

                                            }
                                        });
                                        }

                                }
                                else
                                {

                                    LinlayoutMap.setVisibility(View.GONE);
                                    LinlayoutEmptyContainer.setVisibility(View.VISIBLE);

                                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity(). getSystemService(INPUT_METHOD_SERVICE);
                                    if (getActivity().getCurrentFocus() != null)
                                    {
                                        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                                    }


                                }

                                }
                            else if (jsonObject.getInt(Keys.success) == 0)
                            {
                                JSONObject jsonObject_errors = jsonObject.getJSONObject(Keys.errors);
                                if (jsonObject_errors.has(Keys.latitude))
                                {
                                    TV_Invali.setText(jsonObject_errors.getJSONArray(Keys.latitude).get(0).toString());
                                    dialog_invalid_qr.show();

                                }
                                else if (jsonObject_errors.has(Keys.longitude))
                                {
                                    TV_Invali.setText(jsonObject_errors.getJSONArray(Keys.longitude).get(0).toString());
                                    dialog_invalid_qr.show();


                                }
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

    @OnClick(R.id.ButtonOk)
    public void setButtonOk()
    {
        autocomplete.setText("");
        LinlayoutMap.setVisibility(View.VISIBLE);
        LinlayoutEmptyContainer.setVisibility(View.GONE);
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

                    setLinlayoutback();
                    // handle back button's click listener
                    return true;
                }
                return false;
            }
        });
    }
}
