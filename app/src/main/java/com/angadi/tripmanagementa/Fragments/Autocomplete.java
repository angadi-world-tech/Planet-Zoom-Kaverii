package com.angadi.tripmanagementa.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.angadi.tripmanagementa.Adapters.PlacesAutoCompleteAdapter;
import com.angadi.tripmanagementa.Activities.MapsActivity;
import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.Custum.GPSTracker;
import com.angadi.tripmanagementa.Model.DirectionsJSONParser;
import com.angadi.tripmanagementa.Model.PlaceAPI;
import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

import io.fabric.sdk.android.Fabric;

import static android.app.Activity.RESULT_OK;
import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;
import static com.google.android.gms.maps.CameraUpdateFactory.newLatLng;
import static com.google.android.gms.maps.CameraUpdateFactory.zoomTo;


public class Autocomplete extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener {
    AutoCompleteTextView autocompleteView;
    AutoCompleteTextView autocomplete_text;
    LinearLayout back_lin_layout;
    private  PlacesAutoCompleteAdapter mAdapter;

    protected static final String TAG = "Autocomplete";
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final int LOCATION_REQUEST_CODE = 101;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    ArrayList<String> resultList;
    PlaceAPI placeAPI = new PlaceAPI();
    RelativeLayout mapFirst;


    AlertDialog.Builder builder;
    RelativeLayout DescriptionLayout;
    GPSTracker gpsTracker;

    Point point;
    Button menuButton;
    LatLng  latLng_source=null,latLng_destination=null;
     Address address_source = null, address_destination = null;


    double currentLatitude ;
    double currentLongitude ;


    TextView TVkm;

    LinearLayout LinLayoutKM,MapLinLayout,LinlayoutAuto;


    //To calculate distance
  //  private LatLng delhi = new LatLng(28.6139, 77.2090);
    private LatLng delhi = null;
   // private LatLng chandigarh = new LatLng(30.7333, 76.7794);
   private LatLng chandigarh = null;
    private double pickup_latitude;
    private double pickup_longitude;
    LatLng currentLocation_latlng;
    LatLng pickupPoint_latlng;
    double distance;

    LatLng destination_latlng;


    private boolean isMapReady = false;
    private LocationRequest locationRequest;

    private Marker mCurrLocationMarker;


    public Autocomplete() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


      //  MapsActivity.button_next.setVisibility(View.GONE);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        // Inflate the layout_listview_item for this fragment
        final View v = inflater.inflate(R.layout.autocomplete, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

        Fabric.with(getActivity(), new Crashlytics());


        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        builder = new AlertDialog.Builder(getActivity());

        mapFirst = (RelativeLayout)v.findViewById(R.id.mapFirst);



         TVkm = (TextView)v.findViewById(R.id.TVkm);
         LinLayoutKM = (LinearLayout)v.findViewById(R.id.LinLayoutKM);
       // MapLinLayout = (LinearLayout)v.findViewById(R.id.MapLinLayout);
        menuButton = (Button)v.findViewById(R.id.menuButton);

        back_lin_layout = (LinearLayout) v.findViewById(R.id.backLinLayout);
        LinlayoutAuto = (LinearLayout)v.findViewById(R.id.LinlayoutAuto);


        gpsTracker = new GPSTracker(getActivity());


        back_lin_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                EventsFragment eventsFragment = new EventsFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.frame_content,eventsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        autocompleteView = (AutoCompleteTextView) v.findViewById(R.id.autocomplete);
        autocomplete_text = (AutoCompleteTextView) v.findViewById(R.id.autocomplete_text);


        autocompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get data associated with the specified position
                // in the list (AdapterView)
                String description1 = (String) parent.getItemAtPosition(position);
                 TripManagement.saveValueuToPreferences(getActivity(),"PostAddClick","");

                EventsPostAddFragment chatListFragment = new EventsPostAddFragment();
                Bundle bundle = new Bundle();
                bundle.putString("msg",(String) parent.getItemAtPosition(position));
                chatListFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.frame_content,chatListFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                hideSoftKeyboard(getActivity());

                }
        });

        autocompleteView.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.autocomplete_list_item));
        autocompleteView.requestLayout();



        autocomplete_text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get data associated with the specified position
                // in the list (AdapterView)
                String description = (String) parent.getItemAtPosition(position);
                onSearch1(v);

                Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
            }
        });

        autocomplete_text.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.autocomplete_list_item));

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (v!=null)
                {
                  sButton(v);
                }
                }
        });


        return v;
    }
    public void CloseDialog(View view, Dialog dialog)
    {
        dialog.dismiss();
    }
    public void sButton(View v)
    {

//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View dialogLayout = inflater.inflate(R.layout.layout_map_menu, null);
//        builder.setView(dialogLayout);
//        final Dialog dialog = builder.create();
//        dialog.show();
//
//        hideSoftKeyboard(getActivity());
//
//
//        Button closeButton = (Button) dialogLayout.findViewById(R.id.closeButton);
//        RadioButton radioButtonFood = (RadioButton)dialogLayout.findViewById(R.id.radioButtonFood);
//        RadioButton radipoButtonCafe = (RadioButton)dialogLayout.findViewById(R.id.radipoButtonCafe);
//        RadioButton radioButtonHalt = (RadioButton)dialogLayout.findViewById(R.id.radioButtonHalt);
//
//
//        radioButtonHalt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//
//               if (!autocompleteView.getText().toString().isEmpty() && !autocomplete_text.getText().toString().isEmpty())
//               {
//                   String uri = String.format(Locale.ENGLISH, "geo:"+ address_source.getLatitude()+"," +address_source.getLongitude()+"?q=lodge");
//                   Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                   Log.d("JJJJJJJJJJJJ",uri);
//                   startActivity(intent);
//               }
//               else if (!autocompleteView.getText().toString().isEmpty())
//               {
//                   Toast.makeText(getActivity(), "Destination can't be Empty ", Toast.LENGTH_SHORT).show();
//               }
//               else if (!autocomplete_text.getText().toString().isEmpty())
//               {
//
//                   Toast.makeText(getActivity(), "Source can't be Empty ", Toast.LENGTH_SHORT).show();
//               }
//               else
//               {
//                   Toast.makeText(getActivity(), " Source and Destination can't be Empty ", Toast.LENGTH_SHORT).show();
//               }
//
//
//            }
//        });
//
//        radipoButtonCafe.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//
//
//                if (!autocompleteView.getText().toString().isEmpty() && !autocomplete_text.getText().toString().isEmpty())
//                {
//                    String uri = String.format(Locale.ENGLISH, "geo:"+ address_source.getLatitude()+"," +address_source.getLongitude()+"?q=cafe");
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                    Log.d("JJJJJJJJJJJJ",uri);
//                    startActivity(intent);
//                }
//                else if (!autocompleteView.getText().toString().isEmpty())
//                {
//
//                    Toast.makeText(getActivity(), "Destination can't be Empty ", Toast.LENGTH_SHORT).show();
//                }
//                else if (!autocomplete_text.getText().toString().isEmpty())
//                {
//                    Toast.makeText(getActivity(), "Source can't be Empty ", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(getActivity(), "Source and Destination can't be Empty ", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });
//        radioButtonFood.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                if (!autocompleteView.getText().toString().isEmpty() && !autocomplete_text.getText().toString().isEmpty())
//                {
//                    String uri = String.format(Locale.ENGLISH, "geo:"+ address_source.getLatitude()+"," +address_source.getLongitude()+"?q=restaurants");
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                    Log.d("JJJJJJJJJJJJ",uri);
//                    startActivity(intent);
//                }
//                else if (!autocompleteView.getText().toString().isEmpty())
//                {
//                    Toast.makeText(getActivity(), "Destination can't be Empty ", Toast.LENGTH_SHORT).show();
//                }
//                else if (!autocomplete_text.getText().toString().isEmpty())
//                {
//
//                    Toast.makeText(getActivity(), "Source can't be Empty ", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(getActivity(), "Source and Destination can't be Empty ", Toast.LENGTH_SHORT).show();
//                }
//
//
//
//
//
//
//            }
//        });
//
//        closeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//
//                CloseDialog(v,dialog);
//
//            }
//        });
//
//










    }

    @Override
    public void onResume()
    {

        super.onResume();
        String location_source = autocomplete_text.getText().toString();
        List<Address> addressList = null;
        if (location_source!=null || !location_source.equals("")) {
            Geocoder geocoder = new Geocoder(getActivity());
            try {
                addressList = geocoder.getFromLocationName(location_source, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addressList != null) {
                address_destination = addressList.get(0);
                latLng_destination = new LatLng(address_destination.getLatitude(), address_destination.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng_destination).title(address_destination.getAddressLine(0)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).draggable(false).visible(true));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng_destination));

                abc();
                hideSoftKeyboard(getActivity());
            }




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
                        EventsFragment eventsFragment = new EventsFragment();
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.frame_content,eventsFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        // handle back button's click listener
                        return true;
                    }
                    return false;
                }
            });
        }




//   if (address_source!=null && address_destination!=null)
//   {
//       Polyline line = mMap.addPolyline(new PolylineOptions()
//               .add(new LatLng(address_source.getLatitude(),address_source.getLongitude()), new LatLng(address_destination.getLatitude(),address_destination.getLongitude()))
//               .width(5)
//               .color(Color.RED).geodesic(true));
//
//   }


    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {

        mMap = googleMap;
        checkLocationandAddToMap();
        mMap.getUiSettings().setZoomControlsEnabled(true);
        abc();


        }




    public void  abc()
    {
        if (address_source!=null && address_destination!=null)
        {

            String str_origin = "origin=" + address_source.getLatitude() + "," + address_source.getLongitude();
            String str_dest = "destination=" + address_destination.getLatitude() + "," + address_destination.getLongitude();
            String sensor = "sensor=false";
            String key = "AIzaSyCtt_MIf5EvzVAjE1oF152nuOG1sA_-WfI";
            String parameters = str_origin + "&" + str_dest + "&" + "key="+key;
            String output = "json";
            String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

            Log.d("onMapClick", url.toString());
            FetchUrl FetchUrl = new FetchUrl();
            FetchUrl.execute(url);
            mMap.moveCamera(newLatLng(latLng_source));
            mMap.animateCamera(zoomTo(7));

            Location delhi_location = new Location("Delhi");
            delhi_location.setLatitude(address_source.getLatitude());
            delhi_location.setLongitude(address_source.getLongitude());

            Location chandigarh_location = new Location("Chandigarh");
            chandigarh_location.setLatitude(address_destination.getLatitude());
            chandigarh_location.setLongitude(address_destination.getLongitude());

            double distance = (delhi_location.distanceTo(chandigarh_location)) * 0.000621371;

            TVkm.setText(String.valueOf(distance*1.6+" Kilo Meters"));
            LinLayoutKM.setVisibility(View.VISIBLE);
            delhi = new LatLng(address_source.getLatitude(),address_source.getLongitude());
            chandigarh = new LatLng(address_destination.getLatitude(),address_destination.getLongitude());
        }


    }

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        checkLocationandAddToMap();
        abc();

    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }




    public void onStart()
    {
        super.onStart();
      //  Toast.makeText(getActivity(), "onStart", Toast.LENGTH_SHORT).show();
        googleApiClient.connect();
        String location_source = autocomplete_text.getText().toString();
        List<Address> addressList = null;
        if (location_source!=null || !location_source.equals("")) {
            Geocoder geocoder = new Geocoder(getActivity());
            try {
                addressList = geocoder.getFromLocationName(location_source, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addressList != null) {
                address_destination = addressList.get(0);
                latLng_destination = new LatLng(address_destination.getLatitude(), address_destination.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng_destination).title(address_destination.getAddressLine(0)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).draggable(false).visible(true));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng_destination));

                abc();
                hideSoftKeyboard(getActivity());
            }
        }







    }

    public void onStop()
    {
        super.onStop();

        googleApiClient.disconnect();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                    checkLocationandAddToMap();
                } else
                    Toast.makeText(getActivity(), "Location Permission Denied", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
    private void checkLocationandAddToMap()
    {
        //Checking if the user has granted the permission
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
            Log.e("TAG", "GPS is on");
              currentLatitude = location.getLatitude();
              currentLongitude = location.getLongitude();

              double speed = location.getSpeed();
              Log.d("Speed", String.valueOf(speed));
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                double speed1 = location.getSpeedAccuracyMetersPerSecond();
                Log.d("Speed1", String.valueOf(speed1));
            }
            double speed2 = location.getTime();
            Log.d("Speed2", String.valueOf(speed2));


              moveMap();
            LatLng loc1 = new LatLng(currentLatitude, currentLongitude);
            mMap.addMarker(new MarkerOptions().position(loc1).title("You are Here"));
          //  mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatitude, currentLongitude), 15));
            mMap.animateCamera(zoomTo(50), 2000, null);

        }
    }



    //For distance calculations

    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {
            String data = "";

            try {
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);

        }
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class ParserTask extends AsyncTask<String, Integer,List<List<HashMap<String,String>>>>
    {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings)
        {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try{
                jObject = new JSONObject(strings[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

// Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result)
        {
            ArrayList points = null;
            PolylineOptions lineOptions = null;
            Polyline polyline = null;
            MarkerOptions markerOptions = new MarkerOptions();
            String distance = "";
            String duration = "";

            if(result.size()<1)
            {
                Toast.makeText(getActivity(), "No Points", Toast.LENGTH_SHORT).show();
                return;
            }

// Traversing through all the routes
            for(int i=0;i<result.size();i++){
               // points.clear();
                points = new ArrayList();
                lineOptions = new PolylineOptions();


// Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

// Fetching all the points in i-th route
                for(int j=0;j <path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    if(j==0){ // Get distance from the list
                        distance = (String)point.get("distance");
                        continue;
                    }else if(j==1){ // Get duration from the list
                        duration = (String)point.get("duration");
                        continue;
                    }


                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }

// Adding all the points in the route to LineOptions

                lineOptions.addAll(points);
                lineOptions.width(8);
                lineOptions.color(Color.BLUE);
                }
            TVkm.setText("Distance:"+distance + ", Duration:"+duration);
            polyline = mMap.addPolyline(lineOptions);

            }
    }






    private void moveMap() {

        //Creating a LatLng Object to store Coordinates
        currentLocation_latlng = new LatLng(currentLatitude, currentLatitude);
        Geocoder geocoder = new Geocoder(getActivity());
        try {
            List<Address> addressList = geocoder.getFromLocation(currentLatitude, currentLatitude, 1);
            String str = addressList.get(0).getSubLocality();
            mMap.addMarker(new MarkerOptions()
                    .position(currentLocation_latlng)//setting position
                    .title(str + "(Current Location)")
                    .draggable(true)).showInfoWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Moving the camera
        mMap.moveCamera(newLatLng(currentLocation_latlng));
        //Animating the camera
        mMap.animateCamera(zoomTo(15));


    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return strAdd;
    }






    /* adding Polylines between CurrentLOcation and PickUp_Location through this method*/
    private void AddPolyline() {
        String url = getDirectionsUrl(currentLocation_latlng, pickupPoint_latlng);
        DownloadTask downloadTask = new DownloadTask();
        /* Start downloading json data from Google Directions API*/
        downloadTask.execute(url);
        distance = CalculateByDistance(currentLocation_latlng, pickupPoint_latlng);
        // String dis = new Double(distance).toString();
        //DistanceBetween_CurrentLocation_to_PickupPoint.setText("Distance:" + distance + "KM");

    }


    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(s);
        }

        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }
    }


    /* Finding Distance between Current Location and PickUp_Point through this Method*/
    private double CalculateByDistance(LatLng latLng, LatLng latLng1) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = latLng.latitude;
        double lat2 = latLng1.latitude;
        double lon1 = latLng.longitude;
        double lon2 = latLng1.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = Double.parseDouble(String.format("%.1f", valueResult));
        double meter = valueResult % 1000;
        return km;

    }

    private String getDirectionsUrl(LatLng latLng, LatLng latLng1) {
        String str_origin = "origin=" + latLng.latitude + "," + latLng.longitude;
        // Destination of route
        String str_dest = "destination=" + latLng1.latitude + "," + latLng1.longitude;
        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
         final String key = "AIzaSyBRqZwPgkYnfeeh_P0SrJJIqHGRNo0l";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + key ;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters ;
        return url;
    }


    @Override
    public void onLocationChanged(Location location)
    {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (location != null) {
            /*Getting Current Location  longitude and latitude*/
            double longitude3 = location.getLongitude();
            double latitude3 = location.getLatitude();
            LatLng latLng = new LatLng(latitude3, longitude3);
            currentLocation_latlng = latLng;
            /*moving the map to location through moveMap() method*/
            pickupPoint_latlng = new LatLng(pickup_latitude, pickup_longitude);
            moveMap();
            distance = CalculateByDistance(currentLocation_latlng, pickupPoint_latlng);
           // DistanceBetween_CurrentLocation_to_PickupPoint.setText(distance + " km" + duration);

            //Place current location marker
            //  LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            Bitmap b = null;
            b = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.common_full_open_on_phone), 100, 60, false);
            final BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(b);
            if (mCurrLocationMarker != null) {
                mCurrLocationMarker.remove();
            }
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(icon);
            mCurrLocationMarker = mMap.addMarker(markerOptions);
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {

    }

    @Override
    public void onProviderEnabled(String provider)
    {

    }

    @Override
    public void onProviderDisabled(String provider)
    {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        {
            if (requestCode == 1 && resultCode == RESULT_OK)
            {
                String name;
                Double latitude, longitude;
                name = data.getStringExtra("place");
                Toast.makeText(getActivity(), "nameee" + name, Toast.LENGTH_SHORT).show();
                latitude = data.getDoubleExtra("lat", 0);
                Toast.makeText(getActivity(), "latitude" + latitude, Toast.LENGTH_SHORT).show();
                longitude = data.getDoubleExtra("lng", 0);
                Toast.makeText(getActivity(), "longitude" + longitude, Toast.LENGTH_SHORT).show();

                if (data.getStringExtra("case").equals("1")) {
                    Log.d("ABC", "name: " + name);
                    //destn_address.setText(name);
                    destination_latlng = new LatLng(latitude, longitude);
                    Geocoder geocoder = new Geocoder(getActivity());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        final String str = addressList.get(0).getAddressLine(0);
                        if (mMap != null && googleApiClient != null)
                            Log.d("ABC", "adding Marker on destinations." + destination_latlng);
                       getActivity().runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run() {
                                //  drawMarker(latLng);
                                if (isMapReady) {
                                    mMap.clear();
                                    mMap.addMarker(new MarkerOptions()
                                            .position(destination_latlng) //setting position
                                            .title("Destination")
                                            .draggable(true)).showInfoWindow();
                                    mMap.moveCamera(newLatLng(destination_latlng));
                                    //Animating the camera
                                    mMap.animateCamera(zoomTo(15));
                                    // AddPolyLineToDestination();
                                    AddPolyline();
                                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                        return;
                                    }
                                    mMap.setMyLocationEnabled(true);
                                    mMap.animateCamera(zoomTo(12));
                                    // time_distance_linear.setVisibility(View.VISIBLE);

                                }
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } else {
                    Log.d("ABC", "name: " + name);

                }
            }
        }
    }




    public void onSearch(View view)
    {
        String location_source = autocompleteView.getText().toString();
        List<Address> addressList = null;
        if (location_source!=null || !location_source.equals(""))
        {
            Geocoder geocoder = new Geocoder(getActivity());
            try
            {
                addressList = geocoder.getFromLocationName(location_source,1);
            } catch (IOException e)
            {
                e.printStackTrace();
            }

             address_source = addressList.get(0);
             latLng_source = new LatLng(address_source.getLatitude(),address_source.getLongitude());
             mMap.addMarker(new MarkerOptions().position(latLng_source).title(address_source.getAddressLine(0)).draggable(false).visible(true));
             mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng_source));

             hideSoftKeyboard(getActivity());


        }
    }

    public void onSearch1(View view)
    {
        String location_source = autocomplete_text.getText().toString();
        List<Address> addressList = null;
        if (location_source!=null || !location_source.equals(""))
        {
            Geocoder geocoder = new Geocoder(getActivity());
            try {
                addressList= geocoder.getFromLocationName(location_source,1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(addressList!=null)
            {
                address_destination = addressList.get(0);
                latLng_destination = new LatLng(address_destination.getLatitude(),address_destination.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng_destination).title(address_destination.getAddressLine(0)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).draggable(false).visible(true));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng_destination));

                abc();
                hideSoftKeyboard(getActivity());
            }







//            Polyline line = mMap.addPolyline(new PolylineOptions()
//                    .add(new LatLng(address_source.getLatitude(),address_source.getLongitude()), new LatLng(address_destination.getLatitude(),address_destination.getLongitude()))
//                    .width(5)
//                    .color(Color.RED).geodesic(true));





        }
    }


}


