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
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.angadi.tripmanagementa.Activities.HomeActivity;
import com.angadi.tripmanagementa.Adapters.PlacesAutoCompleteAdapter;
import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.Custum.CustomDateTimePicker;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

import static android.content.Context.LOCATION_SERVICE;
import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;
import static com.google.android.gms.maps.CameraUpdateFactory.newLatLng;
import static com.google.android.gms.maps.CameraUpdateFactory.zoomTo;

public class LocationSearchFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener
{
    Address address = null;
    AutoCompleteTextView autocomplete,autocomplete_destintion,autocomplete_destintion_first,autocomplete_destintion_second,autocomplete_destintion_third;
    LatLng  latLng=null;
    private GoogleMap mMap;
    CustomDateTimePicker custom;
    private GoogleApiClient googleApiClient;
    public static String full_address="";
    private static final int LOCATION_REQUEST_CODE = 101;
    private LatLng delhi = null;
    // private LatLng chandigarh = new LatLng(30.7333, 76.7794);
    private LatLng chandigarh = null;

    RelativeLayout.LayoutParams layoutParams1,layoutParams2;
    private static final String TAG = LocationSearchFragment.class.getSimpleName();

    int windowwidth,windowheight;

    ImageView draggable_image;



    public static  Address address_source = null, address_destination = null,address_current = null;
    public static LatLng  latLng_source=null,latLng_destination=null,latlngcurrent = null;



    int mYear,mMonth,mDay;


    @BindView(R.id.CalenderTV) TextView CalenderTV;
    @BindView(R.id.TextViewClock) TextView TextViewClock;
    @BindView(R.id.LinLayoutCurrentLocation) LinearLayout LinLayoutCurrentLocation;
    @BindView(R.id.LinLayoutBack) LinearLayout LinLayoutBack;
    @BindView(R.id.LinLayoutPlusIcon) LinearLayout LinLayoutPlusIcon;
    @BindView(R.id.r12)RelativeLayout r12;
    @BindView(R.id.r11)RelativeLayout r11;
    @BindView(R.id.r13)RelativeLayout r13;
    @BindView(R.id.r14)RelativeLayout r14;
    @BindView(R.id.Imageview_secondRel) ImageView Imageview_secondRel;
    @BindView(R.id.LinLayoutcrossIcon) LinearLayout LinLayoutcrossIcon;
    @BindView(R.id.Imageview_secondRelcross) ImageView Imageview_secondRelcross;
    @BindView(R.id.Linlayout_r12) LinearLayout Linlayout_r12;
    @BindView(R.id.Linlayout_r12_cross) LinearLayout Linlayout_r12_cross;
    @BindView(R.id.Linlayout_r13_cross) LinearLayout Linlayout_r13_cross;
    @BindView(R.id.Linlayout_r13) LinearLayout Linlayout_r13;
    @BindView(R.id.Linlayout_r14) LinearLayout Linlayout_r14;
    @BindView(R.id.multipleAutoLayouts) LinearLayout multipleAutoLayouts;
    @BindView(R.id.LinLayoutChoosevehicletype) LinearLayout LinLayoutChoosevehicletype;
    @BindView(R.id.ButtonPlanTrip) Button ButtonPlanTrip;
    @BindView(R.id.LinlayoutPlanetZoom) LinearLayout LinlayoutPlanetZoom;
    @BindView(R.id.LinlayoutChat) LinearLayout LinlayoutChat;
    @BindView(R.id.NewPlanClickedLinlayout) TextView NewPlanClickedLinlayout;
    @BindView(R.id.NewPlanUnClickedLinlayout) TextView NewPlanUnClickedLinlayout;
    @BindView(R.id.PlanHistoryUnClickedLinLayout) TextView PlanHistoryUnClickedLinLayout;
    @BindView(R.id.PlanHistoryClickedLinLayout) TextView PlanHistoryClickedLinLayout;
    @BindView(R.id.LinlayoutDone) LinearLayout LinlayoutDone;
    @BindView(R.id.LinlayoutHelp) LinearLayout LinlayoutHelp;
    @BindView(R.id.LinlayoutBreakpoints) LinearLayout LinlayoutBreakpoints;
    @BindView(R.id.LinlayoutFood) LinearLayout LinlayoutFood;
    @BindView(R.id.BikeLinLayout) LinearLayout BikeLinLayout;
    @BindView(R.id.ImageviewBike) ImageView ImageviewBike;
    @BindView(R.id.TextviewBike) TextView TextviewBike;
    @BindView(R.id.Textviewcar) TextView Textviewcar;
    @BindView(R.id.Imageviewcar) ImageView Imageviewcar;
    @BindView(R.id.LinLayoutcar) LinearLayout LinLayoutcar;
    @BindView(R.id.LinLayoutBus) LinearLayout LinLayoutBus;
    @BindView(R.id.ImageviewBus) ImageView ImageviewBus;
    @BindView(R.id.TextviewBus) TextView TextviewBus;
    @BindView(R.id.LinlayoutTrain) LinearLayout LinlayoutTrain;
    @BindView(R.id.ImageViewTrain) ImageView ImageViewTrain;
    @BindView(R.id.TextViewTrain) TextView TextViewTrain;
    @BindView(R.id.ImageviewFood) ImageView ImageviewFood;
    @BindView(R.id.TextviewFood) TextView TextviewFood;
    @BindView(R.id.LinLayoutRestaurants) LinearLayout LinLayoutRestaurants;
    @BindView(R.id.ImageviewResturants) ImageView ImageviewResturants;
    @BindView(R.id.TextViewResturants) TextView TextViewResturants;
    @BindView(R.id.LinlayoutHotel) LinearLayout LinlayoutHotel;
    @BindView(R.id.ImageviewHotels) ImageView ImageviewHotels;
    @BindView(R.id.TextviewHotels) TextView TextviewHotels;
    @BindView(R.id.LinlayoutNearBy) LinearLayout LinlayoutNearBy;
    @BindView(R.id.ImageViewNearby) ImageView ImageViewNearby;
    @BindView(R.id.TextViewNearby) TextView TextViewNearby;
    @BindView(R.id.LinLayoutSkip) LinearLayout LinLayoutSkip;

    LinearLayout LinLayoutCalender,LinlayoutClock;

   public static double Latitude ;
   public static double Longitude ;
    LatLng currentLocation_latlng;
    SupportMapFragment mapFragment;
    String description = "";
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_location_search, container, false);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        ButterKnife.bind(this,view);
        Fabric.with(getActivity(), new Crashlytics());






        draggable_image = (ImageView)view.findViewById(R.id.draggable_image);
        windowwidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        windowheight = getActivity().getWindowManager().getDefaultDisplay().getHeight();

        draggable_image.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                layoutParams1 = (RelativeLayout.LayoutParams) draggable_image.getLayoutParams();
                switch(event.getActionMasked())
                {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int x_cord = (int) event.getRawX();
                        int y_cord = (int) event.getRawY();
                        if (x_cord > windowwidth) {
                            x_cord = windowwidth;
                        }
                        if (y_cord > windowheight) {
                            y_cord = windowheight;
                        }
                        layoutParams1.leftMargin = x_cord - 25;
                        layoutParams1.topMargin = y_cord - 75;
                        draggable_image.setLayoutParams(layoutParams1);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });


      TagInitialzation();


        autocomplete = (AutoCompleteTextView) view.findViewById(R.id.autocomplete);
        autocomplete_destintion = (AutoCompleteTextView)view.findViewById(R.id.autocomplete_destintion);
        autocomplete_destintion_first = (AutoCompleteTextView)view.findViewById(R.id.autocomplete_destintion_first);
        autocomplete_destintion_second = (AutoCompleteTextView)view.findViewById(R.id.autocomplete_destintion_second);
        autocomplete_destintion_third = (AutoCompleteTextView)view.findViewById(R.id.autocomplete_destintion_third);


        LinLayoutCalender = (LinearLayout) view.findViewById(R.id.LinLayoutCalender);
        LinlayoutClock = (LinearLayout)view.findViewById(R.id.LinlayoutClock);
        CalenderTV.setText(TripManagement.readValueFromPreferences(getActivity(),"DATE",""));
        TextViewClock.setText(TripManagement.readValueFromPreferences(getActivity(),"TIME",""));


        ImageViewTrain.setImageResource(R.mipmap.train_orange);
        ImageviewBus.setImageResource(R.mipmap.bus_orange);
        Imageviewcar.setImageResource(R.mipmap.car_orange);
        ImageviewBike.setImageResource(R.mipmap.bike_white);



        if(TripManagement.readValueFromPreferences(getActivity(),"BreakFastFragment","").equals("BreakFastFragment"))
        {

            multipleAutoLayouts.setVisibility(View.GONE);
            LinLayoutChoosevehicletype.setVisibility(View.VISIBLE);
        }
        else
        {
            multipleAutoLayouts.setVisibility(View.VISIBLE);
            LinLayoutChoosevehicletype.setVisibility(View.GONE);


        }



        if (!TripManagement.readValueFromPreferences(getActivity(),"DateTimeSet","").equals("DateTimeSet"))
     {
         setDateTime();
     }



        LinLayoutCalender.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

               // custom.showDialog();
                setDateTime();

            }
        });

        LinlayoutClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               // custom.showDialog();
                setDateTime();
            }
        });



        autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get data associated with the specified position
                // in the list (AdapterView)
                String description = (String) parent.getItemAtPosition(position);
                mMap.clear();
                SourceMovecamera(view);
              //  hideSoftKeyboard(getActivity());
                Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
            }
        });
        autocomplete.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.autocomplete_list_item));


        autocomplete_destintion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get data associated with the specified position
                // in the list (AdapterView)
                String description = (String) parent.getItemAtPosition(position);

               // onSearch2(view);

                DestinationMovecamera();

              //  DestinationMovecamera(view);
                Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
               // hideSoftKeyboard(getActivity());
            }
        });
        autocomplete_destintion.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.autocomplete_list_item));


        autocomplete_destintion_first.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get data associated with the specified position
                // in the list (AdapterView)
                String description = (String) parent.getItemAtPosition(position);
                mMap.clear();
                DestinationMovecamera_first();
              //  hideSoftKeyboard(getActivity());
                Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
            }
        });
        autocomplete_destintion_first.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.autocomplete_list_item));



        autocomplete_destintion_second.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get data associated with the specified position
                // in the list (AdapterView)
                String description = (String) parent.getItemAtPosition(position);
                mMap.clear();
                DestinationMovecamera_second();
              //  hideSoftKeyboard(getActivity());
                Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
            }
        });
        autocomplete_destintion_second.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.autocomplete_list_item));


        autocomplete_destintion_third.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get data associated with the specified position
                // in the list (AdapterView)
                String description = (String) parent.getItemAtPosition(position);
                mMap.clear();
                DestinationMovecamera_third();
              //  hideSoftKeyboard(getActivity());
                Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
            }
        });
        autocomplete_destintion_third.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.autocomplete_list_item));


       // hideSoftKeyboard(getActivity());
        return view;
    }



    private void DestinationMovecamera_second()
    {
        String location_source = autocomplete_destintion_second.getText().toString();
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
               // hideSoftKeyboard(getActivity());
            }


        }
    }

    private void DestinationMovecamera_third()
    {
        String location_source = autocomplete_destintion_third.getText().toString();
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
              //  hideSoftKeyboard(getActivity());
            }


        }
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
            mCurrLocationMarker = mMap.addMarker(markerOptions);
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

            // moveMap();
            LatLng loc1 = new LatLng(Latitude, Longitude);
            mMap.addMarker(new MarkerOptions().position(loc1).title("You are Here"));
           // mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Latitude, Longitude), 15));
            mMap.animateCamera(zoomTo(50), 2000, null);

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
            mMap.addMarker(new MarkerOptions()
                    .position(currentLocation_latlng)//setting position
                    .title(str + "(Current Location)")
                    .draggable(true)).showInfoWindow();
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


    public static void hideSoftKeyboard(Activity activity)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }


    @OnClick(R.id.LinLayoutCurrentLocation)
    public void setLinLayoutCurrentLocation()
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


    @OnClick(R.id.LinLayoutBack)
    public void setLinLayoutBack()
    {
        TripManagement.saveValueuToPreferences(getActivity(),"BreakFastFragment","");
        if (LinLayoutChoosevehicletype.getVisibility()==View.VISIBLE)
        {
            LinLayoutChoosevehicletype.setVisibility(View.GONE);
            multipleAutoLayouts.setVisibility(View.VISIBLE);
        }
        else
        {
            startActivity(new Intent(getActivity(),HomeActivity.class));

        }

    }


    @Override
    public void onResume()
    {
        super.onResume();

        if(TripManagement.readValueFromPreferences(getActivity(),"BreakFastFragment","").equals("BreakFastFragment"))
        {
            multipleAutoLayouts.setVisibility(View.GONE);
            LinLayoutChoosevehicletype.setVisibility(View.VISIBLE);
        }
        else
        {
            multipleAutoLayouts.setVisibility(View.VISIBLE);
            LinLayoutChoosevehicletype.setVisibility(View.GONE);
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

                       setLinLayoutBack();
                       // handle back button's click listener
                    return true;
                }
                return false;
            }
        });
    }


    public void SourceMovecamera(View view)
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

            mMap.addMarker(new MarkerOptions().position(latLng_source).title(address_source.getAddressLine(0)).draggable(false).visible(true));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng_source));



            // address_current = addressList.get(0);
            // latlngcurrent = new LatLng(address_current.getLatitude(),address_current.getLongitude());
            // mMap.addMarker(new MarkerOptions().position(latlngcurrent).title(address_current.getAddressLine(0)).draggable(false).visible(true));
            // mMap.animateCamera(CameraUpdateFactory.newLatLng(latlngcurrent));






        }
    }
    public void DestinationMovecamera()
    {
        String location_source = autocomplete_destintion.getText().toString();
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
               // hideSoftKeyboard(getActivity());
            }


            }
    }

    public void DestinationMovecamera_first()
    {

        String location_source = autocomplete_destintion_first.getText().toString();
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
               // hideSoftKeyboard(getActivity());
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
            mMap.animateCamera(zoomTo(7));

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
            mMap.animateCamera(zoomTo(7));

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
                    full_address=address.getAddressLine(0)+" "+address.getAddressLine(1)+" "+" "+address.getAdminArea();
                    autocomplete.setText(full_address);

                }
                else
                {
                    full_address=address.getAddressLine(0)+" "+address.getAddressLine(1)+" "+address.getLocality()+" "+address.getAdminArea();
                    autocomplete.setText(full_address);
                }

                }
        } catch (IOException e) {

            Toast.makeText(getActivity(), "Please Reboot/Restart your Device to get Current Address", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return null;
    }


    @OnClick(R.id.LinLayoutPlusIcon)
    public void setLinLayoutPlusIcon()
    {
        r12.setVisibility(View.VISIBLE);
        LinLayoutPlusIcon.setVisibility(View.GONE);
        LinLayoutcrossIcon.setVisibility(View.VISIBLE);
        //Imageview_secondRelcross.setVisibility(View.VISIBLE);
        //Imageview_secondRel.setImageResource(R.mipmap.cancel_icon);
    }

    @OnClick(R.id.Imageview_secondRelcross)
    public void setImageview_secondRelcross()
    {
        r11.setVisibility(View.GONE);
        LinLayoutPlusIcon.setVisibility(View.GONE);
        Imageview_secondRelcross.setVisibility(View.VISIBLE);
        //Imageview_secondRel.setImageResource(R.mipmap.cancel_icon);
    }


//    @OnClick(R.id.Imageview_secondRel)
//    public void setImageview_secondRel()
//    {
//        if (r11.getVisibility() == View.VISIBLE)
//        {
//            Imageview_secondRel.setImageResource(R.mipmap.cancel_icon);
//            r12.setVisibility(View.VISIBLE);
//        }
//        else
//        {
//            r11.setVisibility(View.GONE);
//        }

  //  }

@OnClick(R.id.Linlayout_r12)
    public void setLinlayout_r12()
{
     r13.setVisibility(View.VISIBLE);
    Linlayout_r12_cross.setVisibility(View.VISIBLE);
    Linlayout_r12.setVisibility(View.GONE);
}

    @OnClick(R.id.Linlayout_r12_cross)
    public void setLinlayout_r12_cross()
    {
        r12.setVisibility(View.GONE);
        r13.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.Linlayout_r13)
    public void setLinlayout_r13()
    {
       // r13.setVisibility(View.GONE);
        r14.setVisibility(View.VISIBLE);
        Linlayout_r13_cross.setVisibility(View.VISIBLE);
        Linlayout_r13.setVisibility(View.GONE);
    }

    @OnClick(R.id.Linlayout_r13_cross)
    public void setLinlayout_r13_cross()
    {
        r13.setVisibility(View.GONE);
        r14.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.Linlayout_r14)
    public void setLinlayout_r14()
    {
        Linlayout_r13_cross.setVisibility(View.VISIBLE);
        Linlayout_r13.setVisibility(View.GONE);
    }

    @OnClick(R.id.Linlayout_r14_cross)
    public void setLinlayout_r14_cross()
    {
        r14.setVisibility(View.GONE);

        }

    @OnClick(R.id.ButtonPlanTrip)
    public void setButtonPlanTrip()
    {
        LinLayoutChoosevehicletype.setVisibility(View.VISIBLE);
        multipleAutoLayouts.setVisibility(View.GONE);
    }

    @OnClick(R.id.LinlayoutPlanetZoom)
    public void setLinlayoutPlanetZoom()
    {
        startActivity(new Intent(getActivity(),HomeActivity.class));
    }

    @OnClick(R.id.LinlayoutChat)
    public void setLinlayoutChat()
    {
        ChatListFragment chatListFragment = new ChatListFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_content,chatListFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.NewPlanClickedLinlayout)
    public void setNewPlanClickedLinlayout()
    {
        NewPlanClickedLinlayout.setVisibility(View.GONE);
        NewPlanUnClickedLinlayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.NewPlanUnClickedLinlayout)
    public void setNewPlanUnClickedLinlayout()
    {
        NewPlanClickedLinlayout.setVisibility(View.VISIBLE);
        NewPlanUnClickedLinlayout.setVisibility(View.GONE);

        PlanHistoryUnClickedLinLayout.setVisibility(View.VISIBLE);
        PlanHistoryClickedLinLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.PlanHistoryUnClickedLinLayout)
    public void setPlanHistoryUnClickedLinLayout()
    {
        PlanHistoryUnClickedLinLayout.setVisibility(View.GONE);
        PlanHistoryClickedLinLayout.setVisibility(View.VISIBLE);

        NewPlanClickedLinlayout.setVisibility(View.GONE);
        NewPlanUnClickedLinlayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.PlanHistoryClickedLinLayout)
    public void setPlanHistoryClickedLinLayout()
    {
        PlanHistoryUnClickedLinLayout.setVisibility(View.VISIBLE);
        PlanHistoryClickedLinLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.LinlayoutDone)
    public void setLinlayoutDone()
    {
        Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.LinlayoutHelp)
    public void setLinlayoutHelp()
    {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("https://www.planetzoom.co.in/"));
        startActivity(intent);
        //https://www.planetzoom.co.in/

    }

    @OnClick(R.id.LinlayoutBreakpoints)
    public void setLinlayoutBreakpoints()
    {
        BreakPointsfragment breakPointsfragment = new BreakPointsfragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_content,breakPointsfragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

//    @OnClick(R.id.LinlayoutBreakImage)
//    public void setLinlayoutBreakImage()
//    {
//        BreakPointsfragment breakPointsfragment = new BreakPointsfragment();
//        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.frame_content,breakPointsfragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//
//    }

    public static LocationSearchFragment newInstance() {
        return new LocationSearchFragment();
    }


    public void setDateTime()
    {
        if (CalenderTV.getText().toString().isEmpty() || TextViewClock.getText().toString().isEmpty() )
        {

            TripManagement.saveValueuToPreferences(getActivity(),"DateTimeSet","DateTimeSet");
            custom = new CustomDateTimePicker(getActivity(),
                    new CustomDateTimePicker.ICustomDateTimeListener()
                    {

                        @Override
                        public void onSet(Dialog dialog, Calendar calendarSelected,
                                          Date dateSelected, int year, String monthFullName,
                                          String monthShortName, int monthNumber, int date,
                                          String weekDayFullName, String weekDayShortName,
                                          int hour24, int hour12, int min, int sec,
                                          String AM_PM) {
                            CalenderTV.setText("");

                            CalenderTV.setText(calendarSelected.get(Calendar.DAY_OF_MONTH)
                                    + "-" + (monthNumber + 1) + "-" + year);
                            TripManagement.saveValueuToPreferences(getActivity(),"DATE",calendarSelected.get(Calendar.DAY_OF_MONTH) + "-" + (monthNumber + 1) + "-" + year);

                            TextViewClock.setText(hour24 + ":" + min + ":" + sec);
                            TripManagement.saveValueuToPreferences(getActivity(),"TIME",hour24 + ":" + min + ":" + sec);





                        }

                        @Override
                        public void onCancel() {

                        }
                    });
            custom.set24HourFormat(true);
/**
 * Pass Directly current data and time to show when it pop up
 */
            custom.setDate(Calendar.getInstance());
            custom.showDialog();
        }
    }




    @OnClick(R.id.BikeLinLayout)
    public void setBikeLinLayout()
    {
        if (BikeLinLayout.getTag() == "BikeLinLayoutSelected")
        {
            BikeLinLayout.setBackgroundResource(R.drawable.gray_choose_vehicle_layout_border);
            ImageviewBike.setImageResource(R.mipmap.bike_oranage);
            TextviewBike.setTextColor(getActivity().getResources().getColor(R.color.orange));
            BikeLinLayout.setTag("BikeLinLayoutUnSelected");


        }
        else  if (BikeLinLayout.getTag() == "BikeLinLayoutUnSelected")
        {
            BikeLinLayout.setBackgroundResource(R.drawable.orange_filled_layout_border);
            ImageviewBike.setImageResource(R.mipmap.bike_white);
            TextviewBike.setTextColor(getActivity().getResources().getColor(R.color.white));
            BikeLinLayout.setTag("BikeLinLayoutSelected");

            //For Car - Start

            LinLayoutcar.setBackgroundResource(R.drawable.gray_choose_vehicle_layout_border);
            Imageviewcar.setImageResource(R.mipmap.car_orange); // need to change to orange bus_gray icon
            Textviewcar.setTextColor(getActivity().getResources().getColor(R.color.orange));
            LinLayoutcar.setTag("LinLayoutcarUnSelected");

            //For Car - End


            //For bus - start

            LinLayoutBus.setBackgroundResource(R.drawable.gray_choose_vehicle_layout_border);
            ImageviewBus.setImageResource(R.mipmap.bus_orange);
            TextviewBus.setTextColor(getActivity().getResources().getColor(R.color.orange));
            LinLayoutBus.setTag("LinLayoutBusUnSelected");

            //For bus - End


            //For Train - Start

            LinlayoutTrain.setBackgroundResource(R.drawable.gray_choose_vehicle_layout_border);
            ImageViewTrain.setImageResource(R.mipmap.train_orange);
            TextViewTrain.setTextColor(getActivity().getResources().getColor(R.color.orange));
            LinlayoutTrain.setTag("LinlayoutTrainUnselected");

            //For Train - End



        }

    }

    @OnClick(R.id.LinLayoutcar)
    public void setLinLayoutcar()
    {
        if (LinLayoutcar.getTag()=="LinLayoutcarUnSelected")
        {
            LinLayoutcar.setBackgroundResource(R.drawable.orange_filled_layout_border);
            Imageviewcar.setImageResource(R.mipmap.car_white);
            Textviewcar.setTextColor(getActivity().getResources().getColor(R.color.white));
            LinLayoutcar.setTag("LinLayoutcarSelected");

            //For bike - Start

            BikeLinLayout.setBackgroundResource(R.drawable.gray_choose_vehicle_layout_border);
            ImageviewBike.setImageResource(R.mipmap.bike_oranage);
            TextviewBike.setTextColor(getActivity().getResources().getColor(R.color.orange));
            BikeLinLayout.setTag("BikeLinLayoutUnSelected");

            //For bike - End

            //For bus - start

            LinLayoutBus.setBackgroundResource(R.drawable.gray_choose_vehicle_layout_border);
            ImageviewBus.setImageResource(R.mipmap.bus_orange);
            TextviewBus.setTextColor(getActivity().getResources().getColor(R.color.orange));
            LinLayoutBus.setTag("LinLayoutBusUnSelected");

            //For bus - End

            //For Train - Start

            LinlayoutTrain.setBackgroundResource(R.drawable.gray_choose_vehicle_layout_border);
            ImageViewTrain.setImageResource(R.mipmap.train_orange);
            TextViewTrain.setTextColor(getActivity().getResources().getColor(R.color.orange));
            LinlayoutTrain.setTag("LinlayoutTrainUnselected");

            //For Train - End


        }
        else  if (LinLayoutcar.getTag()=="LinLayoutcarSelected")
        {
            LinLayoutcar.setBackgroundResource(R.drawable.gray_choose_vehicle_layout_border);
            Imageviewcar.setImageResource(R.mipmap.car_orange);
            Textviewcar.setTextColor(getActivity().getResources().getColor(R.color.orange));
            LinLayoutcar.setTag("LinLayoutcarUnSelected");
        }

    }

    @OnClick(R.id.LinLayoutBus)
    public void setLinLayoutBus()
    {
        if (LinLayoutBus.getTag()=="LinLayoutBusUnSelected")
        {
            LinLayoutBus.setBackgroundResource(R.drawable.orange_filled_layout_border);
            ImageviewBus.setImageResource(R.mipmap.bus_white);
            TextviewBus.setTextColor(getActivity().getResources().getColor(R.color.white));
            LinLayoutBus.setTag("LinLayoutBusSelected");

            //For bike - Start

            BikeLinLayout.setBackgroundResource(R.drawable.gray_choose_vehicle_layout_border);
            TextviewBike.setTextColor(getActivity().getResources().getColor(R.color.orange));
            ImageviewBike.setImageResource(R.mipmap.bike_oranage); // need to change orange bike
            BikeLinLayout.setTag("BikeLinLayoutUnSelected");

            //For bike - End


            //For Train - Start

            LinlayoutTrain.setBackgroundResource(R.drawable.gray_choose_vehicle_layout_border);
            ImageViewTrain.setImageResource(R.mipmap.train_orange);
            TextViewTrain.setTextColor(getActivity().getResources().getColor(R.color.orange));
            LinlayoutTrain.setTag("LinlayoutTrainUnselected");

            //For Train - End


            //For Car - Start

            LinLayoutcar.setBackgroundResource(R.drawable.gray_choose_vehicle_layout_border);
            Imageviewcar.setImageResource(R.mipmap.car_orange); // need to change to orange bus_gray icon
            Textviewcar.setTextColor(getActivity().getResources().getColor(R.color.orange));
            LinLayoutcar.setTag("LinLayoutcarUnSelected");

            //For Car - End

        }
        else if(LinLayoutBus.getTag()=="LinLayoutBusSelected")
        {
            LinLayoutBus.setBackgroundResource(R.drawable.gray_choose_vehicle_layout_border);
            TextviewBus.setTextColor(getActivity().getResources().getColor(R.color.orange));
            ImageviewBus.setImageResource(R.mipmap.bus_orange);
            LinLayoutBus.setTag("LinLayoutBusUnSelected");
        }
    }

    @OnClick(R.id.LinlayoutTrain)
    public void seLinlayoutTrain()
    {
        if (LinlayoutTrain.getTag()=="LinlayoutTrainUnselected")
        {
            LinlayoutTrain.setBackgroundResource(R.drawable.orange_filled_layout_border);
            ImageViewTrain.setImageResource(R.mipmap.train_white);
            TextViewTrain.setTextColor(getActivity().getResources().getColor(R.color.white));
            LinlayoutTrain.setTag("LinlayoutTrainselected");


            //For bike - Start

            BikeLinLayout.setBackgroundResource(R.drawable.gray_choose_vehicle_layout_border);
            ImageviewBike.setImageResource(R.mipmap.bike_oranage);
            TextviewBike.setTextColor(getActivity().getResources().getColor(R.color.orange));
            BikeLinLayout.setTag("BikeLinLayoutUnSelected");

            //For bike - End



            //For Car - Start

            LinLayoutcar.setBackgroundResource(R.drawable.gray_choose_vehicle_layout_border);
            Imageviewcar.setImageResource(R.mipmap.car_orange); // need to change to orange bus_gray icon
            Textviewcar.setTextColor(getActivity().getResources().getColor(R.color.orange));
            LinLayoutcar.setTag("LinLayoutcarUnSelected");

            //For Car - End

            //For bus - start

            LinLayoutBus.setBackgroundResource(R.drawable.gray_choose_vehicle_layout_border);
            ImageviewBus.setImageResource(R.mipmap.bus_orange);
            TextviewBus.setTextColor(getActivity().getResources().getColor(R.color.orange));
            LinLayoutBus.setTag("LinLayoutBusUnSelected");

            //For bus - End




        }
        else  if (LinlayoutTrain.getTag()=="LinlayoutTrainselected")
        {
            LinlayoutTrain.setBackgroundResource(R.drawable.gray_choose_vehicle_layout_border);
            ImageViewTrain.setImageResource(R.mipmap.train_orange);
            TextViewTrain.setTextColor(getActivity().getResources().getColor(R.color.orange));
            LinlayoutTrain.setTag("LinlayoutTrainUnselected");
        }

    }

    @OnClick(R.id.LinlayoutFood)
    public void setLinlayoutFood()
    {

        if (LinlayoutFood.getTag()=="FoodSelected")
        {

            BreakPointsfragment breakPointsfragment = new BreakPointsfragment();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.frame_content,breakPointsfragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            
            LinlayoutFood.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
            ImageviewFood.setImageResource(R.mipmap.food_gray);
            TextviewFood.setTextColor(getActivity().getResources().getColor(R.color.dark_gray));
            LinlayoutFood.setTag("FoodUnSelected");
        }
        else if (LinlayoutFood.getTag()=="FoodUnSelected")
        {
            LinlayoutFood.setBackgroundResource(R.drawable.green_layout_border);
            ImageviewFood.setImageResource(R.mipmap.food_white_icon);
            TextviewFood.setTextColor(getActivity().getResources().getColor(R.color.white));
            LinlayoutFood.setTag("FoodSelected");


            //start of restuarants

            LinLayoutRestaurants.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
            ImageviewResturants.setImageResource(R.mipmap.restaurant_gray);
            TextViewResturants.setTextColor(getActivity().getResources().getColor(R.color.dark_gray));
            LinLayoutRestaurants.setTag("LinLayoutRestaurantsUnSelected");

            //end of resturants

            //start of nearby

            LinlayoutNearBy.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
            ImageViewNearby.setImageResource(R.mipmap.near_by_gray);
            TextViewNearby.setTextColor(getActivity().getResources().getColor(R.color.dark_gray));
            LinlayoutNearBy.setTag("LinlayoutNearByUnSelected");

            //end of nearby

            //start of hotels
            LinlayoutHotel.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
            ImageviewHotels.setImageResource(R.mipmap.hotels_gray);
            TextviewHotels.setTextColor(getActivity().getResources().getColor(R.color.dark_gray));
            LinlayoutHotel.setTag("LinlayoutHotelUnSelected");
            //end of hotels
        }

    }


    @OnClick(R.id.LinLayoutRestaurants)
    public void setLinLayoutRestaurants()
    {
        if (LinLayoutRestaurants.getTag()=="LinLayoutRestaurantsUnSelected")
        {
            LinLayoutRestaurants.setBackgroundResource(R.drawable.green_layout_border);
            ImageviewResturants.setImageResource(R.mipmap.restaurant_white_icon);
            TextViewResturants.setTextColor(getActivity().getResources().getColor(R.color.white));
            LinLayoutRestaurants.setTag("LinLayoutRestaurantsSelected");

            //Start of food

            LinlayoutFood.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
            ImageviewFood.setImageResource(R.mipmap.food_gray);
            TextviewFood.setTextColor(getActivity().getResources().getColor(R.color.dark_gray));
            LinlayoutFood.setTag("FoodUnSelected");

            //End of food


            //start of nearby

            LinlayoutNearBy.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
            ImageViewNearby.setImageResource(R.mipmap.near_by_gray);
            TextViewNearby.setTextColor(getActivity().getResources().getColor(R.color.dark_gray));
            LinlayoutNearBy.setTag("LinlayoutNearByUnSelected");

            //end of nearby

            //start of hotels
            LinlayoutHotel.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
            ImageviewHotels.setImageResource(R.mipmap.hotels_gray);
            TextviewHotels.setTextColor(getActivity().getResources().getColor(R.color.dark_gray));
            LinlayoutHotel.setTag("LinlayoutHotelUnSelected");
            //end of hotels


        }
        else if (LinLayoutRestaurants.getTag()=="LinLayoutRestaurantsSelected")
        {
            LinLayoutRestaurants.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
            ImageviewResturants.setImageResource(R.mipmap.restaurant_gray);
            TextViewResturants.setTextColor(getActivity().getResources().getColor(R.color.dark_gray));
            LinLayoutRestaurants.setTag("LinLayoutRestaurantsUnSelected");
        }
    }

    @OnClick(R.id.LinlayoutHotel)
    public void setLinlayoutHotel()
    {
        if(LinlayoutHotel.getTag()=="LinlayoutHotelUnSelected")
        {
            LinlayoutHotel.setBackgroundResource(R.drawable.green_layout_border);
            ImageviewHotels.setImageResource(R.mipmap.hotels_white_icon);
            TextviewHotels.setTextColor(getActivity().getResources().getColor(R.color.white));
            LinlayoutHotel.setTag("LinlayoutHotelSelected");


            //Start of food

            LinlayoutFood.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
            ImageviewFood.setImageResource(R.mipmap.food_gray);
            TextviewFood.setTextColor(getActivity().getResources().getColor(R.color.dark_gray));
            LinlayoutFood.setTag("FoodUnSelected");

            //End of food


            //start of nearby

            LinlayoutNearBy.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
            ImageViewNearby.setImageResource(R.mipmap.near_by_gray);
            TextViewNearby.setTextColor(getActivity().getResources().getColor(R.color.dark_gray));
            LinlayoutNearBy.setTag("LinlayoutNearByUnSelected");

            //end of nearby


            //start of restuarants

            LinLayoutRestaurants.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
            ImageviewResturants.setImageResource(R.mipmap.restaurant_gray);
            TextViewResturants.setTextColor(getActivity().getResources().getColor(R.color.dark_gray));
            LinLayoutRestaurants.setTag("LinLayoutRestaurantsUnSelected");

            //end of resturants
        }
        else if(LinlayoutHotel.getTag()=="LinlayoutHotelSelected")
        {
            LinlayoutHotel.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
            ImageviewHotels.setImageResource(R.mipmap.hotels_gray);
            TextviewHotels.setTextColor(getActivity().getResources().getColor(R.color.dark_gray));
            LinlayoutHotel.setTag("LinlayoutHotelUnSelected");
        }
    }


    @OnClick(R.id.LinlayoutNearBy)
    public void setLinlayoutNearBy()
    {
       if(LinlayoutNearBy.getTag()=="LinlayoutNearByUnSelected")
       {
           LinlayoutNearBy.setBackgroundResource(R.drawable.green_layout_border);
           ImageViewNearby.setImageResource(R.mipmap.nearby_white_icon);
           TextViewNearby.setTextColor(getActivity().getResources().getColor(R.color.white));
           LinlayoutNearBy.setTag("LinlayoutNearBySelected");


           //Start of food

           LinlayoutFood.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
           ImageviewFood.setImageResource(R.mipmap.food_gray);
           TextviewFood.setTextColor(getActivity().getResources().getColor(R.color.dark_gray));
           LinlayoutFood.setTag("FoodUnSelected");

           //End of food


           //start of hotels
           LinlayoutHotel.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
           ImageviewHotels.setImageResource(R.mipmap.hotels_gray);
           TextviewHotels.setTextColor(getActivity().getResources().getColor(R.color.dark_gray));
           LinlayoutHotel.setTag("LinlayoutHotelUnSelected");
           //end of hotels


           //start of restuarants

           LinLayoutRestaurants.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
           ImageviewResturants.setImageResource(R.mipmap.restaurant_gray);
           TextViewResturants.setTextColor(getActivity().getResources().getColor(R.color.dark_gray));
           LinLayoutRestaurants.setTag("LinLayoutRestaurantsUnSelected");

           //end of resturants




       }
       else  if( LinlayoutNearBy.getTag()=="LinlayoutNearBySelected")
       {
           LinlayoutNearBy.setBackgroundResource(R.drawable.gray_layout_border_breakpoints);
           ImageViewNearby.setImageResource(R.mipmap.near_by_gray);
           TextViewNearby.setTextColor(getActivity().getResources().getColor(R.color.dark_gray));
           LinlayoutNearBy.setTag("LinlayoutNearByUnSelected");
       }

    }


    private void TagInitialzation()
    {
        LinlayoutFood.setTag("FoodSelected");
        BikeLinLayout.setTag("BikeLinLayoutSelected");
        LinLayoutcar.setTag("LinLayoutcarUnSelected");
        LinLayoutBus.setTag("LinLayoutBusUnSelected");
        LinlayoutTrain.setTag("LinlayoutTrainUnselected");
        LinLayoutRestaurants.setTag("LinLayoutRestaurantsUnSelected");
        LinlayoutHotel.setTag("LinlayoutHotelUnSelected");
        LinlayoutNearBy.setTag("LinlayoutNearByUnSelected");

    }

   @OnClick(R.id.LinLayoutSkip)
    public void setLinLayoutSkip()
   {
       Toast.makeText(getActivity(), "Skip", Toast.LENGTH_SHORT).show();
   }








}
