package com.angadi.tripmanagementa.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.angadi.tripmanagementa.Activities.HomeActivity;
import com.angadi.tripmanagementa.Activities.LoginActivity;
import com.angadi.tripmanagementa.Activities.Qrcoderesult;
import com.angadi.tripmanagementa.Adapters.PlacesAutoCompleteAdapter;
import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.Model.DirectionsJSONParser;
import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
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
import com.squareup.picasso.Picasso;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.content.Context.LOCATION_SERVICE;
import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;
import static com.angadi.tripmanagementa.Activities.HomeActivity.BarcodeObject;
import static com.google.android.gms.maps.CameraUpdateFactory.newLatLng;
import static com.google.android.gms.maps.CameraUpdateFactory.zoomTo;

public class ProductsLocationFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener {
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    public static String full_address = "";
    public static Address address_source = null, address_destination = null, address_current = null;
    public static LatLng latLng_source = null, latLng_destination = null, latlngcurrent = null;
    private static final int LOCATION_REQUEST_CODE = 101;
    private LatLng delhi = null;
    private LatLng chandigarh = null;
    double currentLatitude;
    double currentLongitude;
    String description = "";
    AutoCompleteTextView autocomplete, autocomplete1;
    private static final String TAG = AddressSearchfragment.class.getSimpleName();
    List<HashMap<String, String>> path;
    PolylineOptions lineOptions;

    String Productlocation,ProductPhoto, ProductLatitude, productLongitude;


    public static final int PATTERN_DASH_LENGTH_PX = 20;
    public static final int PATTERN_GAP_LENGTH_PX = 20;
    public static final PatternItem DOT = new Dot();
    public static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    public static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    public static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);


    @BindView(R.id.LinLayoutCurrentlocation) LinearLayout LinLayoutCurrentlocation;
    @BindView(R.id.Linlayoutback) LinearLayout Linlayoutback;
    @BindView(R.id.RelLayooutGetdirection) RelativeLayout RelLayooutGetdirection;
    @BindView(R.id.RellayoutStartNavigation) RelativeLayout RellayoutStartNavigation;
    @BindView(R.id.HeaderTVGetDirections) TextView HeaderTVGetDirections;


    public static double Latitude ;
    public static double Longitude ;
    LatLng currentLocation_latlng;
    SupportMapFragment mapFragment;
    private Marker mCurrLocationMarker;




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map1);

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout_listview_item for this fragment
        final View view = inflater.inflate(R.layout.products_locationfragment, container, false);
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


        Productlocation = getArguments().getString("location");
        ProductPhoto = getArguments().getString("Photo");
        Log.e("productPhoto",ProductPhoto);

        HeaderTVGetDirections.setTypeface(montserrat_bold);
        autocomplete1.setTypeface(montserrat_regular);
        autocomplete.setTypeface(montserrat_regular);

        autocomplete1.setText(Productlocation);





        autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String description = (String) parent.getItemAtPosition(position);
                mMap.clear();
               SourceMovecamera();
                Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
            }
        });
        autocomplete.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.autocomplete_list_item));


        autocomplete1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String description = (String) parent.getItemAtPosition(position);
                DestinationMovecamera();
                Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
            }
        });
        autocomplete1.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.autocomplete_list_item));
        return view;
    }





    @OnClick(R.id.LinLayoutCurrentlocation)
    public void setLinLayoutCurrentlocation()
    {
        mMap.clear();
        checkLocationandAddToMapCurrent();
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
            Log.e("TAG", "GPS is on");
            Latitude = location.getLatitude();
            Longitude = location.getLongitude();

            setaddress(location.getLatitude(),location.getLongitude());
        }

    }


    public void SourceMovecamera()
    {
        String location_source = autocomplete.getText().toString();
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

           // mMap.addMarker(new MarkerOptions().position(latLng_source).title(address_source.getAddressLine(0)));
            mMap.setMyLocationEnabled(true);
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng_source));

            }
    }
    public void DestinationMovecamera()
    {
        String location_source = autocomplete1.getText().toString();

      //  String location_source = autocomplete1.getText().toString();
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
                mMap.addMarker(new MarkerOptions().position(latLng_destination).title(address_destination.getAddressLine(0)));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng_destination));
                mMap.setMyLocationEnabled(true);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Latitude, Longitude), 10));
                  mMap.animateCamera(zoomTo(10), 100, null);

                abc();
                // hideSoftKeyboard(getActivity());

                InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
                if (getActivity().getCurrentFocus() != null)
                {
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

                }
            }


        }
    }



    public void  abc()
    {
        if (address_source!=null && address_destination!=null)
        {

            String str_origin = "origin=" + address_source.getLatitude() + "," + address_source.getLongitude();
            String str_dest = "destination=" + address_destination.getLatitude() + "," + address_destination.getLongitude();
            String sensor = "sensor=false";
            String key = "AIzaSyANBgzAB3bcVhHUejLag31UNExauM921Kk";
            String parameters = str_origin + "&" + str_dest + "&" + "key="+key;
            String output = "json";
            String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

            Log.d("onMapClick", url.toString());
           FetchUrl FetchUrl = new FetchUrl();
            FetchUrl.execute(url);
            mMap.moveCamera(newLatLng(latLng_source));
            mMap.animateCamera(zoomTo(10));
           // mMap.animateCamera(zoomTo(30), 1200, null);

            Location delhi_location = new Location("Delhi");
            delhi_location.setLatitude(address_source.getLatitude());
            delhi_location.setLongitude(address_source.getLongitude());

            Location chandigarh_location = new Location("Chandigarh");
            chandigarh_location.setLatitude(address_destination.getLatitude());
            chandigarh_location.setLongitude(address_destination.getLongitude());

            double distance = (delhi_location.distanceTo(chandigarh_location)) * 0.000621371;

            // TVkm.setText(String.valueOf(distance*1.6+" Kilo Meters"));
            // LinLayoutKM.setVisibility(View.VISIBLE);
            delhi = new LatLng(address_source.getLatitude(),address_source.getLongitude());
            chandigarh = new LatLng(address_destination.getLatitude(),address_destination.getLongitude());
        }
        else if (address_current!=null)
        {

            String str_origin = "origin=" + address_current.getLatitude()+ "," + address_current.getLongitude();
            String str_dest = "destination=" + address_destination.getLatitude() + "," + address_destination.getLongitude();
            String sensor = "sensor=false";
            String key = "AIzaSyANBgzAB3bcVhHUejLag31UNExauM921Kk";
            String parameters = str_origin + "&" + str_dest + "&" + "key="+key;
            String output = "json";
            String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

            Log.d("onMapClick", url.toString());
            FetchUrl FetchUrl = new FetchUrl();
            FetchUrl.execute(url);
            mMap.moveCamera(newLatLng(latlngcurrent));
            mMap.animateCamera(zoomTo(10));
          //  mMap.animateCamera(zoomTo(30), 1200, null);

            Location delhi_location = new Location("Delhi");
            delhi_location.setLatitude(address_current.getLatitude());
            delhi_location.setLongitude(address_current.getLongitude());

            Location chandigarh_location = new Location("Chandigarh");
            chandigarh_location.setLatitude(address_destination.getLatitude());
            chandigarh_location.setLongitude(address_destination.getLongitude());

            double distance = (delhi_location.distanceTo(chandigarh_location)) * 0.000621371;

            // TVkm.setText(String.valueOf(distance*1.6+" Kilo Meters"));
            // LinLayoutKM.setVisibility(View.VISIBLE);
            delhi = new LatLng(address_current.getLatitude(),address_current.getLongitude());
            chandigarh = new LatLng(address_destination.getLatitude(),address_destination.getLongitude());
        }


    }

    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url)
        {
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
            for(int i=0;i<result.size();i++)
            {
                // points.clear();
                points = new ArrayList();
                lineOptions = new PolylineOptions();


                List<HashMap<String, String>> path = result.get(i);
                for(int j=0;j <path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    if(j==0)
                    {
                        distance = (String)point.get("distance");
                        continue;
                    }else if(j==1)
                    {
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
            //TVkm.setText("Distance:"+distance + ", Duration:"+duration);
            polyline = mMap.addPolyline(lineOptions);


        }
    }

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
               // mMap.addMarker(new MarkerOptions().position(latlngcurrent).title(address_current.getAddressLine(0)).draggable(false).visible(true));
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
                    full_address=address.getAddressLine(0)+" "+address.getAddressLine(1)+" "+" "+address.getAdminArea();
                    autocomplete.setText(full_address);


                }
                else
                {
                    full_address=address.getAddressLine(0)+" "+address.getAddressLine(1)+" "+address.getLocality()+" "+address.getAdminArea();
                    autocomplete.setText(full_address);

                }
                SourceMovecamera();
                }
        } catch (IOException e) {

            Toast.makeText(getActivity(), "Please Reboot/Restart your Device to get Current Address", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (latLng_source != null && latLng_destination != null)
        {
            Toast.makeText(getActivity(), " Not Empty", Toast.LENGTH_SHORT).show();
            if (latLng_source.equals(latLng_destination))
            {
                Toast.makeText(getActivity(), "Both are same", Toast.LENGTH_SHORT).show();
                Dialog image_popup = new Dialog(getActivity());
                image_popup.setContentView(R.layout.layout_arrived_destination);

                ImageView Imagepopup = (ImageView)image_popup.findViewById(R.id.Imagepopup);
                if (ProductPhoto != null)
                {
                    Toast.makeText(getActivity(), "photo not null", Toast.LENGTH_SHORT).show();
                    Picasso.with(getActivity()).load(ProductPhoto).into(Imagepopup);
                    image_popup.show();
                }
                else
                {
                    Toast.makeText(getActivity(), "photo null", Toast.LENGTH_SHORT).show();

                }

            }

            Log.e("latLng_source", String.valueOf(latLng_source));
            Log.e("latLng_destination", String.valueOf(latLng_destination));

        }
        else
        {
            Toast.makeText(getActivity(), "Empty", Toast.LENGTH_SHORT).show();
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

                   setLinlayoutback();
                    // handle back button's click listener
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onLocationChanged(Location location)
    {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (location != null)
        {
            /*Getting Current Location  longitude and latitude*/
            double longitude3 = location.getLongitude();
            double latitude3 = location.getLatitude();
            LatLng latLng = new LatLng(latitude3, longitude3);
            currentLocation_latlng = latLng;
            /*moving the map to location through moveMap() method*/
            // pickupPoint_latlng = new LatLng(pickup_latitude, pickup_longitude);
            moveMap();
            DestinationMovecamera();
            // distance = CalculateByDistance(currentLocation_latlng, pickupPoint_latlng);
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
           // mCurrLocationMarker = mMap.addMarker(markerOptions);
        }

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
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        googleApiClient.connect();

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
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Latitude, Longitude)));

        if (address_source!=null && address_destination!=null)
        {
            DestinationMovecamera();

        }

        mMap.getUiSettings().setZoomControlsEnabled(true);
        LocationManager service = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean enabled_network = service.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        // Check that Network Location Provider reports enabled



        if (!enabled)
        {
            if (!enabled_network)
            {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);

            }
            else
            {
                checkLocationandAddToMap();
            }

        }
        else
        {
            checkLocationandAddToMap();
        }


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
            Latitude = location.getLatitude();
            Longitude = location.getLongitude();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                double speed1 = location.getSpeedAccuracyMetersPerSecond();
                Log.d("Speed1", String.valueOf(speed1));
            }

            setaddress(location.getLatitude(),location.getLongitude());

            // moveMap();
            LatLng loc1 = new LatLng(Latitude, Longitude);
          //  mMap.addMarker(new MarkerOptions().position(loc1).title("You are Here"));
            // mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Latitude, Longitude), 15));
          //  mMap.animateCamera(zoomTo(50), 2000, null);

        }
    }


    private void moveMap()
    {

        //Creating a LatLng Object to store Coordinates
        currentLocation_latlng = new LatLng(Latitude, Longitude);
        Geocoder geocoder = new Geocoder(getActivity());
        try
        {
            List<Address> addressList = geocoder.getFromLocation(Latitude, Longitude, 1);
            String str = addressList.get(0).getSubLocality();
//            mMap.addMarker(new MarkerOptions()
//                    .position(currentLocation_latlng)//setting position
//                    .title(str + "(Current Location)")
//                    .draggable(true)).showInfoWindow();
        } catch (IOException e)
        {
            e.printStackTrace();
        }


        //Moving the camera
        mMap.moveCamera(newLatLng(currentLocation_latlng));
        //Animating the camera
        mMap.animateCamera(zoomTo(15));


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

    @Override
    public void onStart()
    {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        checkLocationandAddToMap();

        if (address_source!=null && address_destination!=null)
        {
            DestinationMovecamera();
        }
    }


    @OnClick(R.id.RelLayooutGetdirection)
    public void setRelLayooutGetdirection()
    {
        DestinationMovecamera();
    }


    @OnClick(R.id.Linlayoutback)
    public void setLinlayoutback()
    {
      startActivity(new Intent(getActivity(),Qrcoderesult.class));
    }

    @OnClick(R.id.RellayoutStartNavigation)
    public void setRellayoutStartNavigation()
    {
        final Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://maps.google.com/maps?" + "saddr="+ address_source.getLatitude() + "," + address_source.getLongitude() + "&daddr=" + address_destination.getLatitude() + "," + address_destination.getLongitude()));
        intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
        startActivity(intent);
    }


}