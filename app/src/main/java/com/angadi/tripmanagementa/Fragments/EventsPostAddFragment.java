package com.angadi.tripmanagementa.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.angadi.tripmanagementa.Activities.HomeActivity;
import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.Custum.GPSTracker;
import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

import static android.content.Context.LOCATION_SERVICE;
import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

import static com.angadi.tripmanagementa.Fragments.Autocomplete.TAG;
import static com.google.android.gms.maps.CameraUpdateFactory.newLatLng;
import static com.google.android.gms.maps.CameraUpdateFactory.zoomTo;

public class EventsPostAddFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener
{

    @BindView(R.id.LinLayoutBack) LinearLayout LinLayoutBack;
    @BindView(R.id.LinlayoutPlanetZoom) LinearLayout LinlayoutPlanetZoom;
    private static final int LOCATION_REQUEST_CODE = 101;
    @BindView(R.id.LinLayoutChat) LinearLayout LinLayoutChat;
    @BindView(R.id.posttAddButton) Button posttAddButton;
    @BindView(R.id.image) ImageView image;
    String Decription = "";
    private GoogleApiClient googleApiClient;
    LatLng currentLocation_latlng;
    private GoogleMap mMap;
    double distance;

    SupportMapFragment mapFragment;

    private Marker mCurrLocationMarker;
    LatLng pickupPoint_latlng;

    private double pickup_latitude;
    private double pickup_longitude;

  Address  address_destination = null;
   LatLng latLng_destination =null;

   GPSTracker gpsTracker ;


    double currentLatitude ;
    double currentLongitude ;

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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.events_postadd,container,false);


         mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        ButterKnife.bind(this,view);
        Fabric.with(getActivity(), new Crashlytics());

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        if (TripManagement.readValueFromPreferences(getActivity(),"PostAddClick","").equals("PostAddClick"))
        {
            image.setVisibility(View.VISIBLE);
            mapFragment.getView().setVisibility(View.GONE);
        }
        else
        {
            image.setVisibility(View.GONE);
            mapFragment.getView().setVisibility(View.VISIBLE);


            Bundle bundle=getArguments();
             Decription = bundle.getString("msg");
             Log.d("descri---->",Decription);

            onSearch1();
        }


        return view;
    }
    @OnClick(R.id.LinLayoutBack)
    public void setLinLayoutBack()
    {
        EventsFragment eventsFragment = new EventsFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.FramePicturePreview,eventsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @OnClick(R.id.LinLayoutChat)
    public void setLinlayoutChat()
    {
        ChatListFragment chatListFragment = new ChatListFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.FramePicturePreview,chatListFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.LinlayoutPlanetZoom)
    public void setLLinlayoutPlanetZoom()
    {
        startActivity(new Intent(getActivity(),HomeActivity.class));
    }

    @OnClick(R.id.posttAddButton)
    public void setposttAddButton()
    {
        Toast.makeText(getActivity(), "Your add has been Posted", Toast.LENGTH_SHORT).show();
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


            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

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
    public void onConnected(@Nullable Bundle bundle)
    {
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
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            //Requesting the Location permission
            ActivityCompat.requestPermissions(getActivity(), new String[]
                    {
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



            moveMap();
            LatLng loc1 = new LatLng(currentLatitude, currentLongitude);
            mMap.addMarker(new MarkerOptions().position(loc1).title("You are Here"));
            //  mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatitude, currentLongitude), 13));
            mMap.animateCamera(zoomTo(40), 1000, null);

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
        mMap.animateCamera(zoomTo(13));


    }

    private double CalculateByDistance(LatLng latLng, LatLng latLng1)
    {
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
                    EventsFragment eventsFragment = new EventsFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.frame_content,eventsFragment);
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();

                   // getFragmentManager().popBackStackImmediate();


                    // handle back button's click listener
                    return true;
                }
                return false;
            }
        });
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                    checkLocationandAddToMap();
                } else
                    Toast.makeText(getActivity(), "Location Permission Denied", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void onStop() {
        super.onStop();

        googleApiClient.disconnect();
    }

    @Override
    public void onStart()
    {
        googleApiClient.connect();
        super.onStart();
    }


    public void onSearch1()
    {
        String location_source = Decription;
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
              //  mMap.addMarker(new MarkerOptions().position(latLng_destination).title(address_destination.getAddressLine(0)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).draggable(false).visible(true));
              //  mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng_destination));


            }







//            Polyline line = mMap.addPolyline(new PolylineOptions()
//                    .add(new LatLng(address_source.getLatitude(),address_source.getLongitude()), new LatLng(address_destination.getLatitude(),address_destination.getLongitude()))
//                    .width(5)
//                    .color(Color.RED).geodesic(true));





        }
    }



}
