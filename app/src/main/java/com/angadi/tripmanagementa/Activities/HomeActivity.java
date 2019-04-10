package com.angadi.tripmanagementa.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.angadi.tripmanagementa.Custum.CameraSource;
import com.angadi.tripmanagementa.Custum.CameraSourcePreview;
import com.angadi.tripmanagementa.Custum.GraphicOverlay;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaScannerConnection;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.Extras.Keys;
import com.angadi.tripmanagementa.Extras.VolleyMultipartRequest;
import com.angadi.tripmanagementa.Fragments.AddressSearchfragment;
import com.angadi.tripmanagementa.Fragments.CameraQRFragment;
import com.angadi.tripmanagementa.Fragments.ChatDetailsFragment;
import com.angadi.tripmanagementa.Fragments.ChatListFragment;
import com.angadi.tripmanagementa.Fragments.CreateEcardFragmnet;
import com.angadi.tripmanagementa.Fragments.DashboardFragment;
import com.angadi.tripmanagementa.Fragments.EventsFragment;
import com.angadi.tripmanagementa.Fragments.FavoritesFragment;
import com.angadi.tripmanagementa.Fragments.HistoryFragment;
import com.angadi.tripmanagementa.Fragments.LocationSearchFragment;
import com.angadi.tripmanagementa.Fragments.NotificationFragment;
import com.angadi.tripmanagementa.Custum.BarcodeGraphic;
import com.angadi.tripmanagementa.Custum.BarcodeGraphicTracker;
import com.angadi.tripmanagementa.Custum.BarcodeTrackerFactory;
import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.fabric.sdk.android.Fabric;
import io.intercom.android.sdk.Intercom;
import io.intercom.android.sdk.UserAttributes;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.angadi.tripmanagementa.Activities.MapsActivity.REQUEST_CHECK_SETTINGS;
import static me.dm7.barcodescanner.core.CameraUtils.getCameraInstance;

public class HomeActivity extends AppCompatActivity implements BarcodeGraphicTracker.BarcodeDetectorListener
{
    DrawerLayout welcomeactivity;
    CircleImageView ImageViewLinLayout;
    public final static int WHITE = 0xFFFFFFFF;
    public final static int BLACK = 0xFF000000;
    public final static int WIDTH = 400;
    public final static int HEIGHT = 400;

    ImageView imageView,imageview_arrow , imageViewQR;
    LocationManager service;
    public static boolean enabled;
    Camera camera1;
    private static final String IMAGE_DIRECTORY ="/Trip Management";
    private int GALLERY = 1, CAMERA = 2;

    String PostProductURL = TripManagement.BASE_URL+"user";
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    public static final String NOTIFICATION_REPLY = "NotificationReply";
    public static final String CHANNNEL_ID = "SimplifiedCodingChannel";
    public static final String CHANNEL_NAME = "SimplifiedCodingChannel";
    public static final String CHANNEL_DESC = "This is a channel for Simplified Coding Notifications";

    public static final String KEY_INTENT_MORE = "keyintentmore";
    public static final String KEY_INTENT_HELP = "keyintenthelp";

    public static final int REQUEST_CODE_MORE = 100;
    public static final int REQUEST_CODE_HELP = 101;
    public static final int NOTIFICATION_ID = 200;

    //For flash light
    private boolean hasFlash;

    //For flash light

    //For zoom
    private static final String TAG = "Barcode-reader";

    // intent request code to handle updating play services if needed.
    private static final int RC_HANDLE_GMS = 10001;

    // permission request codes need to be < 256
    private static final int RC_HANDLE_CAMERA_PERM = 2;

    // constants used to pass extra data in the intent
    public static final String AutoFocus = "AutoFocus";
    public static final String UseFlash = "UseFlash";
    public static final String BarcodeObject = "Barcode";

    private CameraSource mCameraSource;
    private CameraSourcePreview mPreview;
    private GraphicOverlay<BarcodeGraphic> mGraphicOverlay;

    // helper objects for detecting taps and pinches.
    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;

    //For zoom

    private final int REQUEST_LOCATION_PERMISSION = 1;
    private LinearLayout LinlayoutFlash;
    private boolean isFlashLightOn = false;



    RelativeLayout r2;
    AutoCompleteTextView autocompleteView,autocomplete;
    LinearLayout trip,LinlayoutMsg,LinLayoutPopUp,LinlayoutQRScanner,LinLayoutTask,LinlayoutAutocomplete,Linlayoutfirst,trip_orange;

    @BindView(R.id.LinLayoutHistoryOrange) LinearLayout LinLayoutHistoryOrange;
    @BindView(R.id.LinlayoutBlueHistory) LinearLayout LinlayoutBlueHistory;
    @BindView(R.id.LinLayoufavBlue) LinearLayout LinLayoufavBlue;
    @BindView(R.id.LinlayputfavOrange) LinearLayout LinlayputfavOrange;
    @BindView(R.id.LinlayoutLiveLocOrange) LinearLayout LinlayoutLiveLocOrange;
    @BindView(R.id.LinlayputLiveLocBlue) LinearLayout LinlayputLiveLocBlue;
    @BindView(R.id.LinLayoutQRCamera) LinearLayout LinLayoutQRCamera;
    @BindView(R.id.LinLayoutQRCameraSecond) LinearLayout LinLayoutQRCameraSecond;
    @BindView(R.id.LinLayoutHelp) LinearLayout LinLayoutHelp;
    @BindView(R.id.LinLayoutChat) LinearLayout LinLayoutChat;
    @BindView(R.id.LinlayoutGalleryBottom) LinearLayout LinlayoutGalleryBottom;
    @BindView(R.id.ImageviewFlash) ImageView ImageviewFlash;
    @BindView(R.id.LinlayoutBlueNotification) LinearLayout LinlayoutBlueNotification;
    @BindView(R.id.LinLayoutNotificationOrange) LinearLayout LinLayoutNotificationOrange;
    @BindView(R.id.LinlayoutLocation) LinearLayout LinlayoutLocation;
    @BindView(R.id.LinlayoutBlueRealEstate) LinearLayout LinlayoutBlueRealEstate;
    @BindView(R.id.LinlayoutBlueEvents) LinearLayout LinlayoutBlueEvents;
    @BindView(R.id.LinlayoutCreateQRCode) LinearLayout LinlayoutCreateQRCode;
    @BindView(R.id.LinlayoutGalleryGallerBottom) LinearLayout LinlayoutGalleryGallerBottom;
    @BindView(R.id.LinLayoutLogout) LinearLayout LinLayoutLogout;
    @BindView(R.id.UsernameTextview) TextView UsernameTextview;
    @BindView(R.id.UserUniqueIdTextview) TextView UserUniqueIdTextview;
    @BindView(R.id.UsernameHomeTextview) TextView UsernameHomeTextview;
    @BindView(R.id.UseIDeHomeTextview) TextView UseIDeHomeTextview;
    @BindView(R.id.ProfileImageview) CircleImageView ProfileImageview;
    @BindView(R.id.LinLayoutEdit) LinearLayout LinLayoutEdit;
    @BindView(R.id.UsernameEdittext) EditText UsernameEdittext;
    @BindView(R.id.LinLayoutTick)LinearLayout LinLayoutTick;
    @BindView(R.id.LinLayoutDashboard) LinearLayout LinLayoutDashboard;
    @BindView(R.id.TextViewdashboardIndicationText) TextView TextViewdashboardIndicationText;
    @BindView(R.id.TextviewLogout) TextView TextviewLogout;
    @BindView(R.id.QrCodeIndicationtextview) TextView QrCodeIndicationtextview;
    @BindView(R.id.TaskIndicationtextview) TextView TaskIndicationtextview;
    @BindView(R.id.LivelocationIndicationTextview) TextView LivelocationIndicationTextview;
    @BindView(R.id.FavIndicationTextview) TextView FavIndicationTextview;
    @BindView(R.id.HistoryIndicationTextview) TextView HistoryIndicationTextview;
    @BindView(R.id.NotificationIndicationTextview) TextView NotificationIndicationTextview;
    @BindView(R.id.RealEstateIndicationTextview) TextView RealEstateIndicationTextview;
    @BindView(R.id.EventsIndicationTextview) TextView EventsIndicationTextview;
    @BindView(R.id.HelpIndicationTextview) TextView HelpIndicationTextview;
    @BindView(R.id.UnreadIndicationTextview) TextView UnreadIndicationTextview;
    @BindView(R.id.QRIndication) TextView QRIndication;
    @BindView(R.id.TaskIndication) TextView TaskIndication;
    @BindView(R.id.TripmagmtIndication) TextView TripmagmtIndication;
    @BindView(R.id.TextViewhelpIndicationText) TextView TextViewhelpIndicationText;
    @BindView(R.id.TextViewEcardIndicationText) TextView TextViewEcardIndicationText;
    @BindView(R.id.TvCreateecard) TextView TvCreateecard;
    @BindView(R.id.TvSaharedToYou) TextView TvSaharedToYou;
    @BindView(R.id.Tvownlist) TextView Tvownlist;
    @BindView(R.id.TvSharedByYou) TextView TvSharedByYou;
    @BindView(R.id.LinLayoutHelp_support) LinearLayout LinLayoutHelp_support;
    @BindView(R.id.LinLayoutEVisitingcard) LinearLayout LinLayoutEVisitingcard;
    @BindView(R.id.LinlayoutEcardDropdown) LinearLayout LinlayoutEcardDropdown;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);

        ButterKnife.bind(this);

        Fabric.with(this, new Crashlytics());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));
        }

        TripManagement.saveValueuToPreferences(this,"ClickedOnList","");
        TripManagement.saveValueuToPreferences(this,"ClickedOnUpdateDetails","");

        ImageViewLinLayout = (CircleImageView) findViewById(R.id.ImageViewLinLayout);
        r2 = (RelativeLayout)findViewById(R.id.r2);

        LinLayoutTask = (LinearLayout)findViewById(R.id.LinLayoutTask);
        Linlayoutfirst = (LinearLayout)findViewById(R.id.Linlayoutfirst);
        autocompleteView = (AutoCompleteTextView)findViewById(R.id.autocomplete);
        autocomplete = (AutoCompleteTextView)findViewById(R.id.autocomplete);


        imageview_arrow = (ImageView)findViewById(R.id.imageview_arrow);
        welcomeactivity = (DrawerLayout)findViewById(R.id.welcomeactivity);
        imageView=(ImageView)findViewById(R.id.imageview);

        LinLayoutPopUp = (LinearLayout)findViewById(R.id.LinLayoutPopUp);
        LinlayoutQRScanner = (LinearLayout)findViewById(R.id.LinlayoutQRScanner);
        LinlayoutMsg = (LinearLayout)findViewById(R.id.LinlayoutMsg);


        LinlayoutFlash = (LinearLayout)findViewById(R.id.LinlayoutFlash);

        LinlayoutFlash.setTag("FlashOff");
        LinlayoutGalleryBottom.setTag("GalleryDisabled");


        trip = (LinearLayout)findViewById(R.id.trip);
        trip_orange = (LinearLayout)findViewById(R.id.trip_orange);

        //For zoom

        mPreview = (CameraSourcePreview) findViewById(R.id.preview);
        mGraphicOverlay = (GraphicOverlay<BarcodeGraphic>) findViewById(R.id.graphicOverlay);



        Log.e("FireBaseID",FirebaseInstanceId.getInstance().getToken());

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Log.e("Token-->",token);

                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
//                        Log.d(TAG, msg);
//
                    }
                });


        Intent notifyIntent = getIntent();
        String extras = getIntent().getStringExtra("KEY");
        String chatid = getIntent().getStringExtra("ChatId");
        String Name = getIntent().getStringExtra("Name");
        String UniqueID = getIntent().getStringExtra("UniqueID");
        String Avatar = getIntent().getStringExtra("Avatar");


        if (extras != null&&extras.equals("YOUR VAL"))
        {
            ChatDetailsFragment chatDetailsFragment = new ChatDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("ChatId", chatid);
            bundle.putString("Name", Name);
            bundle.putString("UniqueID", UniqueID);
            bundle.putString("Avatar", Avatar);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.frame_content, chatDetailsFragment);
            chatDetailsFragment.setArguments(bundle);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }

        // read parameters from the intent used to launch the activity.
        boolean autoFocus = getIntent().getBooleanExtra(AutoFocus, false);
        boolean useFlash = getIntent().getBooleanExtra(UseFlash, false);

        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        BarcodeGraphicTracker.mBarcodeDetectorListener = this;

        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {

            createCameraSource(true, false);
        } else {
            requestCameraPermission();
        }

        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG,"Permission is granted");
            //File write logic here
           // return true;
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        UsernameTextview.setVisibility(View.VISIBLE);
        UsernameEdittext.setVisibility(View.GONE);

        Typeface montserrat_bold = Typeface.createFromAsset(getAssets(), "fonts/MONTSERRAT-BOLD.OTF");
        Typeface montserrat_regular = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");


        UsernameTextview.setTypeface(montserrat_regular);
        UsernameEdittext.setTypeface(montserrat_regular);
        UserUniqueIdTextview.setTypeface(montserrat_regular);
        TextViewdashboardIndicationText.setTypeface(montserrat_regular);
        TextViewhelpIndicationText.setTypeface(montserrat_regular);
        TextviewLogout.setTypeface(montserrat_regular);
        UsernameHomeTextview.setTypeface(montserrat_regular);
        UseIDeHomeTextview.setTypeface(montserrat_regular);
        QrCodeIndicationtextview.setTypeface(montserrat_regular);
        autocomplete.setTypeface(montserrat_regular);
        TaskIndicationtextview.setTypeface(montserrat_regular);
        LivelocationIndicationTextview.setTypeface(montserrat_regular);
        FavIndicationTextview.setTypeface(montserrat_regular);
        HistoryIndicationTextview.setTypeface(montserrat_regular);
        NotificationIndicationTextview.setTypeface(montserrat_regular);
        RealEstateIndicationTextview.setTypeface(montserrat_regular);
        EventsIndicationTextview.setTypeface(montserrat_regular);
        HelpIndicationTextview.setTypeface(montserrat_regular);
        UnreadIndicationTextview.setTypeface(montserrat_regular);
        QRIndication.setTypeface(montserrat_regular);
        TaskIndication.setTypeface(montserrat_regular);
        TripmagmtIndication.setTypeface(montserrat_regular);
        TextViewEcardIndicationText.setTypeface(montserrat_regular);
        TvSaharedToYou.setTypeface(montserrat_regular);
        Tvownlist.setTypeface(montserrat_regular);
        TvSharedByYou.setTypeface(montserrat_regular);
        TvCreateecard.setTypeface(montserrat_regular);

        gestureDetector = new GestureDetector(this, new CaptureGestureListener());
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        LinLayoutEdit.setVisibility(View.VISIBLE);
        LinLayoutTick.setVisibility(View.GONE);

        ViewProfile();
        //For zoom

        LinlayoutMsg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ChatListFragment chatListFragment = new ChatListFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.frame_content,chatListFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        autocomplete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AddressSearchfragment addressSearchfragment = new AddressSearchfragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.frame_content,addressSearchfragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        TripManagement.saveValueuToPreferences(HomeActivity.this,"ClickedOnShare","");


        //For flash light
        hasFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!hasFlash) {
            // device doesn't support flash
            // Show alert message and close the application
            AlertDialog alert = new AlertDialog.Builder(HomeActivity.this)
                    .create();
            alert.setTitle("Error");
            alert.setMessage("Sorry, your device doesn't support flash light!");
            alert.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // closing the application
                    finish();
                }
            });
            alert.show();
            return;
        }
        //For flash light

//        // Old scanner
//        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner);
//        barcodeScannerView.setTorchListener(this);
//        capture = new CaptureManager(this, barcodeScannerView);
//        capture.initializeFromIntent(getIntent(), savedInstanceState);
//        capture.decode();
//        //old scaner


        service = (LocationManager) getSystemService(LOCATION_SERVICE);
         enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // Check if enabled and if not send user to the GPS settings
        if (!enabled)
        {
            displayLocationSettingsRequest(this);
        }

        TripManagement.saveValueuToPreferences(this,"PicturePreviewActivity","");

        requestLocationPermission();
        isWriteStoragePermissionGranted();

        trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                        trip_orange.setVisibility(View.VISIBLE);
                        LinLayoutPopUp.setVisibility(View.GONE);
                        trip.setVisibility(View.GONE);

                        LocationSearchFragment varify_email = new LocationSearchFragment();
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.addToBackStack(null);
                        ft.add(R.id.frame_content, varify_email);
                        ft.commit();

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                welcomeactivity.openDrawer(Gravity.LEFT); //Edit Gravity.START need API 14


            }
        });

        LinLayoutTask.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                LinLayoutPopUp.setVisibility(View.GONE);
                EventsFragment eventsFragment = new EventsFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.frame_content,eventsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        if(isReadStorageAllowed()){
            //If permission is already having then showing the toast
            //Existing the method with return
            return;
        }

        //If the app has not the permission then asking for the permission
        requestStoragePermission();
        isReadStoragePermissionGranted();
        isStoragePermissionGranted();

        LinlayoutFlash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    if (LinlayoutFlash.getTag()=="FlashOff")
                    {
                        camera1 = Camera.open();
                        Camera.Parameters parameters = camera1.getParameters();
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camera1.setParameters(parameters);
                        camera1.startPreview();

                        LinlayoutFlash.setBackgroundResource(R.drawable.black_circle);
                        ImageviewFlash.setImageResource(R.mipmap.flash_on);
                        LinlayoutFlash.setTag("FlashOn");
                    }
                    else if (LinlayoutFlash.getTag()=="FlashOn")
                    {
                        camera1 = Camera.open();
                        Camera.Parameters parameters = camera1.getParameters();
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        camera1.setParameters(parameters);
                        camera1.stopPreview();
                        camera1.release();
                        LinlayoutFlash.setBackgroundResource(R.drawable.black_circle);
                        ImageviewFlash.setImageResource(R.mipmap.flash_off);
                        LinlayoutFlash.setTag("FlashOff");
                    }

                }
            });
       // }





        final View activityRootView = findViewById(R.id.welcomeactivity);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                if (heightDiff > dpToPx(HomeActivity.this, 200)) { // if more than 200 dp, it's probably a keyboard...

                 hideSoftKeyboard(HomeActivity.this);
                }
            }
        });



        Log.d("BarcodeObject-->",BarcodeObject);


    }
    public void onStart() {
        super.onStart();

        requestLocationPermission();
    }

    public void onStop()
    {
        super.onStop();
    }


    @OnClick(R.id.LinlayoutBlueNotification)
    public void setLinlayoutBlueNotification()
    {
        NotificationFragment notificationFragment = new NotificationFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_content,notificationFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @OnClick(R.id.LinlayoutBlueHistory)
    public void setLinlayoutBlueHistory()
    {
        LinLayoutHistoryOrange.setVisibility(View.VISIBLE);
        LinlayoutBlueHistory.setVisibility(View.GONE);
        LinLayoutPopUp.setVisibility(View.GONE);

        HistoryFragment notificationFragment = new HistoryFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_content,notificationFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }

    @OnClick(R.id.LinLayoutHistoryOrange)
    public void setLinLayoutHistoryOrange()
    {
        LinLayoutHistoryOrange.setVisibility(View.GONE);
        LinlayoutBlueHistory.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.LinLayoufavBlue)
    public void setLinLayoufavBlue()
    {
        LinLayoufavBlue.setVisibility(View.GONE);
        LinlayputfavOrange.setVisibility(View.VISIBLE);
        LinLayoutPopUp.setVisibility(View.GONE);

        FavoritesFragment notificationFragment = new FavoritesFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_content,notificationFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.LinlayputfavOrange)
    public void setLinlayputfavOrange()
    {
        LinLayoufavBlue.setVisibility(View.VISIBLE);
        LinlayputfavOrange.setVisibility(View.GONE);
    }

    @OnClick(R.id.LinlayputLiveLocBlue)
    public void setLinlayputLiveLocBlue()
    {
        LinlayputLiveLocBlue.setVisibility(View.GONE);
        LinlayoutLiveLocOrange.setVisibility(View.VISIBLE);
        LinLayoutPopUp.setVisibility(View.GONE);

//        HistoryFragment liveLocationFragment = new HistoryFragment();
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.frame_content,liveLocationFragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();


        startActivity(new Intent(HomeActivity.this,Location_Tracker.class));
    }

    @OnClick(R.id.LinlayoutLiveLocOrange)
    public void setLinlayoutLiveLocOrange()
    {

        LinlayputLiveLocBlue.setVisibility(View.VISIBLE);
        LinlayoutLiveLocOrange.setVisibility(View.GONE);


    }

    @OnClick(R.id.LinLayoutQRCamera)
    public void setLinLayoutQRCamera()
    {

        CameraQRFragment cameraQRFragment = new CameraQRFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_content,cameraQRFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

        if (requestCode != RC_HANDLE_CAMERA_PERM)
        {
            Log.d(TAG, "Got unexpected permission result: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Camera permission granted - initialize the camera source");
            // we have permission, so create the camerasource
            boolean autoFocus = getIntent().getBooleanExtra(AutoFocus,false);
            boolean useFlash = getIntent().getBooleanExtra(UseFlash, false);
            createCameraSource(autoFocus, useFlash);
            return;
        }

        if(requestCode == 23)
        {

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast
                Toast.makeText(this,"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted
                Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }

        Log.e(TAG, "Permission not granted: results len = " + grantResults.length +
                "Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        };

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Multitracker sample")
//                .setMessage("No Camera Permission")
//                .setPositiveButton("Ok", listener)
//                .show();
    }

    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission()
    {

        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if(EasyPermissions.hasPermissions(this, perms)) {
            //Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
        else {
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
       // capture.onResume();
        startCameraSource();
        requestLocationPermission();
    }

    @Override
    protected void onPause() {
        super.onPause();
       // capture.onPause();

        if (mPreview != null) {
            mPreview.stop();
        }

        requestLocationPermission();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // capture.onDestroy();
        if (mPreview != null) {
            mPreview.release();
        }
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
      //  capture.onSaveInstanceState(outState);
    }

    @OnClick(R.id.LinLayoutQRCameraSecond)
    public void setLinLayoutQRCameraSecond()
    {
        CameraQRFragment cameraQRFragment = new CameraQRFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_content,cameraQRFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

//    public void switchFlashlight()
//    {
//        if (isFlashLightOn) {
//          //  barcodeScannerView.setTorchOff();
//            isFlashLightOn = false;
//        } else {
//          //  barcodeScannerView.setTorchOn();
//            isFlashLightOn = true;
//        }
//
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


    public  void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result

                            status.startResolutionForResult(HomeActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }


    @OnClick(R.id.LinLayoutHelp)
    public void setLinLayoutHelp()
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("https://www.planetzoom.co.in/"));
        startActivity(intent);
    }

    @OnClick(R.id.LinLayoutChat)
    public void setLinLayoutChat()
    {
        ChatListFragment chatListFragment = new ChatListFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_content,chatListFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @OnClick(R.id.LinlayoutGalleryBottom)
    public void setLinlayoutGalleryBottom()
    {
     if (LinlayoutGalleryBottom.getTag()=="GalleryDisabled")
     {
         LinlayoutGalleryBottom.setTag("GalleryEnabled");
         LinlayoutGalleryBottom.setBackgroundResource(R.drawable.black_circle);
     }
     else if (LinlayoutGalleryBottom.getTag()=="GalleryEnabled")
     {
         LinlayoutGalleryBottom.setBackgroundResource(R.drawable.black_circle);
         LinlayoutGalleryBottom.setTag("GalleryDisabled");

     }

        CameraQRFragment cameraQRFragment = new CameraQRFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_content,cameraQRFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }



    public static float dpToPx(Context context, float valueInDp)
    {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    @OnClick(R.id.LinlayoutLocation)
    public void setLinlayoutLocation()
    {
        HistoryFragment liveLocationFragment = new HistoryFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_content,liveLocationFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }



    @OnClick(R.id.LinlayoutBlueEvents)
    public void setLinlayoutBlueEvents()
    {
        EventsFragment eventsFragment = new EventsFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_content,eventsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @OnClick(R.id.LinlayoutBlueRealEstate)
    public void setLinlayoutBlueRealEstate()
    {
        startActivity(new Intent(this,Real_estate.class));
    }


    public static void hideSoftKeyboard(Activity activity)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onBackPressed()
    {
       super.onBackPressed();
    }



    private void requestCameraPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
            return;
        }

        final Activity thisActivity = this;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(thisActivity, permissions,
                        RC_HANDLE_CAMERA_PERM);
            }
        };


    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent e)
    {
        scaleGestureDetector.onTouchEvent(e);
        gestureDetector.onTouchEvent(e);
        return super.dispatchTouchEvent(e);
    }

    /**
     * Creates and starts the camera.  Note that this uses a higher resolution in comparison
     * to other detection examples to enable the barcode detector to detect small barcodes
     * at long distances.
     *
     * Suppressing InlinedApi since there is a check that the minimum version is met before using
     * the constant.
     */
    @SuppressLint("InlinedApi")
    private void createCameraSource(boolean autoFocus, boolean useFlash) {
        Context context = getApplicationContext();

        // A barcode detector is created to track barcodes.  An associated multi-processor instance
        // is set to receive the barcode detection results, track the barcodes, and maintain
        // graphics for each barcode on screen.  The factory is used by the multi-processor to
        // create a separate tracker instance for each barcode.
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(context).build();
        BarcodeTrackerFactory barcodeFactory = new BarcodeTrackerFactory(mGraphicOverlay);
        barcodeDetector.setProcessor(
                new MultiProcessor.Builder<>(barcodeFactory).build());

        if (!barcodeDetector.isOperational()) {
            // Note: The first time that an app using the barcode or face API is installed on a
            // device, GMS will download a native libraries to the device in order to do detection.
            // Usually this completes before the app is run for the first time.  But if that
            // download has not yet completed, then the above call will not detect any barcodes
            // and/or faces.
            //
            // isOperational() can be used to check if the required native libraries are currently
            // available.  The detectors will automatically become operational once the library
            // downloads complete on device.
            Log.w(TAG, "Detector dependencies are not yet available.");

            // Check for low storage.  If there is low storage, the native library will not be
            // downloaded, so detection will not become operational.
            IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = registerReceiver(null, lowstorageFilter) != null;

            if (hasLowStorage) {
                Toast.makeText(this, "Low Storage", Toast.LENGTH_LONG).show();
                Log.w(TAG, "Low Storage");
            }
        }

        // Creates and starts the camera.  Note that this uses a higher resolution in comparison
        // to other detection examples to enable the barcode detector to detect small barcodes
        // at long distances.
        CameraSource.Builder builder = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1600, 1024)
                .setRequestedFps(15.0f);

        // make sure that auto focus is an available option
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            builder = builder.setFocusMode(
                    autoFocus ? Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE : null);
        }



        mCameraSource = builder
                .setFlashMode(useFlash ? Camera.Parameters.FLASH_MODE_TORCH : null)
                .build();

    }
    private void startCameraSource() throws SecurityException {
        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (mCameraSource != null) {
            try {
                mPreview.start(mCameraSource, mGraphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

    /**
     * onTap returns the tapped barcode result to the calling Activity.
     *
     * @param rawX - the raw position of the tap
     * @param rawY - the raw position of the tap.
     * @return true if the activity is ending.
     */
    private boolean onTap(float rawX, float rawY) {
        // Find tap point in preview frame coordinates.
        int[] location = new int[2];
        mGraphicOverlay.getLocationOnScreen(location);
        float x = (rawX - location[0]) / mGraphicOverlay.getWidthScaleFactor();
        float y = (rawY - location[1]) / mGraphicOverlay.getHeightScaleFactor();

        // Find the barcode whose center is closest to the tapped point.
        Barcode best = null;
        float bestDistance = Float.MAX_VALUE;
        for (BarcodeGraphic graphic : mGraphicOverlay.getGraphics()) {
            Barcode barcode = graphic.getBarcode();
            if (barcode.getBoundingBox().contains((int) x, (int) y)) {
                // Exact hit, no need to keep looking.
                best = barcode;
                break;
            }
            float dx = x - barcode.getBoundingBox().centerX();
            float dy = y - barcode.getBoundingBox().centerY();
            float distance = (dx * dx) + (dy * dy);  // actually squared distance
            if (distance < bestDistance) {
                best = barcode;
                bestDistance = distance;
            }
        }

        if (best != null) {
            Intent data = new Intent();
            data.putExtra(BarcodeObject, best);
            setResult(CommonStatusCodes.SUCCESS, data);
            finish();
            return true;
        }
        return false;
    }

    private class CaptureGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return onTap(e.getRawX(), e.getRawY()) || super.onSingleTapConfirmed(e);
        }
    }

    private class ScaleListener implements ScaleGestureDetector.OnScaleGestureListener
    {

        /**
         * Responds to scaling events for a gesture in progress.
         * Reported by pointer motion.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         * @return Whether or not the detector should consider this event
         * as handled. If an event was not handled, the detector
         * will continue to accumulate movement until an event is
         * handled. This can be useful if an application, for example,
         * only wants to update scaling factors if the change is
         * greater than 0.01.
         */
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            return false;
        }

        /**
         * Responds to the beginning of a scaling gesture. Reported by
         * new pointers going down.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         * @return Whether or not the detector should continue recognizing
         * this gesture. For example, if a gesture is beginning
         * with a focal point outside of a region where it makes
         * sense, onScaleBegin() may return false to ignore the
         * rest of the gesture.
         */
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        /**
         * Responds to the end of a scale gesture. Reported by existing
         * pointers going up.
         * <p/>
         * Once a scale has ended, {@link ScaleGestureDetector#getFocusX()}
         * and {@link ScaleGestureDetector#getFocusY()} will return focal point
         * of the pointers remaining on the screen.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         */
        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            mCameraSource.doZoom(detector.getScaleFactor());
        }
    }

//    //For flash light
//
//
//
//    private void turnOnFlash() {
//        if (!isFlashOn) {
//            if (Flashcamera == null || params == null) {
//                return;
//            }
//            // play sound
//          //  playSound();
//
//            params = Flashcamera.getParameters();
//            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
//            Flashcamera.setParameters(params);
//            Flashcamera.startPreview();
//            isFlashOn = true;
//
//            // changing button/switch image
//           // toggleButtonImage();
//        }
//    }
//
//
//    private void turnOffFlash() {
//        if (isFlashOn) {
//            if (Flashcamera == null || params == null) {
//                return;
//            }
//            // play sound
//           // playSound();
//
//          //  params = Flashcamera.getParameters();
//            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
//           // Flashcamera.setParameters(params);
//            Flashcamera.stopPreview();
//            isFlashOn = false;
//
//            // changing button/switch image
//          //  toggleButtonImage();
//        }
//    }
//
//    //For flash light

    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)


            return true;

        //If permission is not granted returning false
        return false;
    }

    //Requesting permission
    private void requestStoragePermission()
    {


        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE))
        {

            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }



        //And finally ask for the permission
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},23);
    }

    //This method will be called when the user will tap on allow or deny


    @OnClick(R.id.LinlayoutCreateQRCode)
    public void setLinlayoutCreateQRCode()
    {
        startActivity(new Intent(HomeActivity.this,PicturePreviewActivity.class));
        TripManagement.saveValueuToPreferences(HomeActivity.this,"ForLogo","");

    }

    @OnClick(R.id.LinlayoutGalleryGallerBottom)
    public void setLinlayoutGalleryGallerBottom()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),1234);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case 2:
                Log.d(TAG, "External storage2");
                if(requestCode == PackageManager.PERMISSION_GRANTED)
                {
                   // Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                    //resume tasks needing this permission
                   // downloadPdfFile();
                }else{
                   // progress.dismiss();
                }
                break;

            case 3:
                Log.d(TAG, "External storage1");
                if(requestCode== PackageManager.PERMISSION_GRANTED){
                   // Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
                    //resume tasks needing this permission
                   // SharePdfFile();
                }else{
                   // progress.dismiss();
                }
                break;
        }


        if (requestCode == 1234)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                if (intent != null) {
                    try {
                        Bitmap bMap = MediaStore.Images.Media.getBitmap(getContentResolver(), intent.getData());

                        String contents = null;

                        int[] intArray = new int[bMap.getWidth()*bMap.getHeight()];
                        bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());
                        LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), intArray);
                        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                        Barcode data = new Barcode();



                        Frame frame = new Frame.Builder().setBitmap(bMap).build();
                        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this)
                                .build();
                        if(barcodeDetector.isOperational())
                        {
                            SparseArray<Barcode> sparseArray = barcodeDetector.detect(frame);
                            if(sparseArray != null && sparseArray.size() > 0){
                                for (int i = 0; i < sparseArray.size(); i++){
                                    Log.e("LOG_TAG", "Value: " + sparseArray.valueAt(i).rawValue + "----" + sparseArray.valueAt(i).displayValue);
                                    onObjectDetected(sparseArray.valueAt(i));

                                }
                            }else {
                                Log.e("LOG_TAG","SparseArray null or empty");

                            }

                        }
                        else{
                            Log.e("LOG_TAG", "Detector dependencies are not yet downloaded");
                        }
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)  {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
        }



        if (resultCode == this.RESULT_CANCELED) {
            return;
        }

        if (requestCode == GALLERY)
        {
            Bitmap bitmap = null;
            if (intent != null) {
                Uri contentURI = intent.getData();
                try {
                    if (contentURI != null)
                    {
                         bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);

                    }
                    if(bitmap!=null)
                    {

                        String path = saveImage(bitmap);
                        Toast.makeText(HomeActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                        ProfileImageview.setImageBitmap(bitmap);
                        UpdateProfile(bitmap);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(HomeActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) intent.getExtras().get("data");
            ProfileImageview.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            UpdateProfile(thumbnail);
            Toast.makeText(HomeActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.LinLayoutLogout)
    public void setLinLayoutLogout()
    {
        welcomeactivity.closeDrawers(); //Edit Gravity.START need API 14
        TripManagement.saveValueuToPreferences(this,"LogoutClicked","LogoutClicked");


        startActivity(new Intent(HomeActivity.this,Qrcoderesult.class));

        TripManagement.saveValueuToPreferences(this,"LoginSuccess","");
        TripManagement.saveValueuToPreferences(this,"Rate","");
        TripManagement.saveValueuToPreferences(this,"GENDER","");
        TripManagement.saveValueuToPreferences(this,"male","");
        TripManagement.saveValueuToPreferences(this,"female","");

        Intercom.client().setLauncherVisibility(Intercom.Visibility.GONE);

    }

    private void UpdateProfile(final Bitmap bitmap)
    {
        //getting the tag from the edittext
        //final String tags = editTextTags.getText().toString().trim();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, PostProductURL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        progressDialog.dismiss();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            Log.e("Update Profile Responce", String.valueOf(response.data));
                            if (jsonObject.getInt(Keys.success) == 1)
                            {
                                InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(INPUT_METHOD_SERVICE);
                                if (getCurrentFocus() != null)
                                {
                                    inputMethodManager.hideSoftInputFromWindow( getCurrentFocus().getWindowToken(), 0);
                                }
                                JSONObject jsonObjectData = jsonObject.getJSONObject(Keys.data);
                                JSONObject jsonObjectUser = jsonObjectData.getJSONObject(Keys.user);
                                Log.d("USERNAME",jsonObjectUser.getString(Keys.name));
                               if (jsonObjectUser.has(Keys.name))
                                {
                                    UsernameTextview.setText(jsonObjectUser.getString(Keys.name));
                                    UsernameEdittext.setText(jsonObjectUser.getString(Keys.name));
                                }


                                Picasso.with(HomeActivity.this).load(jsonObjectUser.getString(Keys.avatar)).placeholder(R.drawable.avatar_man).into(ProfileImageview);
                                }
                            else if (jsonObject.getInt(Keys.success) == 0)
                            {
                                JSONObject jsonObject_errors = jsonObject.getJSONObject(Keys.errors);
                                if (jsonObject_errors.has(Keys.name))
                                {
                                  //  JSONArray jsonArray_name = jsonObject_errors.getJSONArray(Keys.name);
                                    Toast.makeText(HomeActivity.this, ""+jsonObject_errors, Toast.LENGTH_SHORT).show();
                                }

                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(HomeActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
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
                params.put(Keys.name,UsernameEdittext.getText().toString());

                return params;
            }

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Keys.Authorization,"Bearer "+TripManagement.readValueFromPreferences(HomeActivity.this,"accesstoken",""));
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
                params.put(Keys.avatar, new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }


    private void UpdateProfilenames()
    {
        //getting the tag from the edittext
        //final String tags = editTextTags.getText().toString().trim();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, PostProductURL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        progressDialog.dismiss();
                        Log.e("StringRequest--->", String.valueOf(response));
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            Log.d("StringRequestdata--->", String.valueOf(jsonObject));
                            if (jsonObject.getInt(Keys.success) == 1)
                            {
                                InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(INPUT_METHOD_SERVICE);
                                if (getCurrentFocus() != null)
                                {
                                    inputMethodManager.hideSoftInputFromWindow( getCurrentFocus().getWindowToken(), 0);
                                }
                                JSONObject jsonObjectData = jsonObject.getJSONObject(Keys.data);
                                JSONObject jsonObjectUser = jsonObjectData.getJSONObject(Keys.user);
                                Log.d("USERNAME",jsonObjectUser.getString(Keys.name));
                                Picasso.with(HomeActivity.this).load(jsonObjectUser.getString(Keys.avatar)).into(ProfileImageview);
                                Picasso.with(HomeActivity.this).load(jsonObjectUser.getString(Keys.avatar)).into(ImageViewLinLayout);
                                UsernameTextview.setText(jsonObjectUser.getString(Keys.name));
                                UsernameEdittext.setText(jsonObjectUser.getString(Keys.name));
                                ViewProfile();
                                }
                            else if (jsonObject.getInt(Keys.success) == 0)
                            {
                                JSONObject jsonObject_errors = jsonObject.getJSONObject(Keys.errors);
                                JSONArray jsonArray_name = jsonObject_errors.getJSONArray(Keys.name);
                                Toast.makeText(HomeActivity.this, ""+jsonObject_errors.getJSONArray(Keys.name).get(0), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(HomeActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
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
                params.put(Keys.name,UsernameEdittext.getText().toString());

                return params;
            }

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Keys.Authorization,"Bearer "+TripManagement.readValueFromPreferences(HomeActivity.this,"accesstoken",""));
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

    public byte[] getFileDataFromDrawable(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void  choosePhotoFromGallary()
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }



    @OnClick(R.id.ProfileImageview)
    public void setProfileImageview()
    {
        showPictureDialog();
    }



    private void ViewProfile()
    {


        //getting the tag from the edittext
        //final String tags = editTextTags.getText().toString().trim();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, PostProductURL,
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
                                Log.e("HOMEUserId",UserID);
                                TripManagement.saveValueuToPreferences(HomeActivity.this,"USERID",UserID);
                                UsernameEdittext.setText(jsonObjectUser.getString(Keys.name));
                                UsernameTextview.setText(jsonObjectUser.getString(Keys.name));
                                UserUniqueIdTextview.setText(jsonObjectUser.getString(Keys.unique_id));

                             String avatarr = jsonObjectUser.isNull(Keys.avatar) ? "" :jsonObjectUser.getString(Keys.avatar);

                             Log.d("stringavatar",avatarr);
                             Log.d("stringarespvatar",jsonObjectUser.getString(Keys.avatar));

                                UsernameHomeTextview.setText(jsonObjectUser.getString(Keys.name));
                                UseIDeHomeTextview.setText(jsonObjectUser.getString(Keys.unique_id));



                                UserAttributes userAttributes = new UserAttributes.Builder()
                                        .withName(jsonObjectUser.getString(Keys.name))
                                        .withEmail(jsonObjectUser.getString(Keys.email))
                                        .build();
                                Intercom.client().updateUser(userAttributes);


                                TripManagement.saveValueuToPreferences(HomeActivity.this,"USERNAMEFORHEADER",jsonObjectUser.getString(Keys.name));
                                TripManagement.saveValueuToPreferences(HomeActivity.this,"USERUNIIQUE_IDFORHEADER",jsonObjectUser.getString(Keys.unique_id));


                                if (avatarr != "")
                                {
                                    Picasso.with(HomeActivity.this).load(jsonObjectUser.getString(Keys.avatar)).into(ProfileImageview);
                                    TripManagement.saveValueuToPreferences(HomeActivity.this,"PHOTOFORHEADER",jsonObjectUser.getString(Keys.avatar));
                                    Picasso.with(HomeActivity.this).load(jsonObjectUser.getString(Keys.avatar)).into(ImageViewLinLayout);

                                }

                                }
                            else if (jsonObject.getInt(Keys.success) == 0)
                            {
                                JSONObject jsonObject_errors = jsonObject.getJSONObject(Keys.errors);
                                JSONArray jsonArray_name = jsonObject_errors.getJSONArray(Keys.name);
                                Toast.makeText(HomeActivity.this, ""+jsonObject_errors.getJSONArray(Keys.name).get(0), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(HomeActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
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
                params.put(Keys.Authorization,"Bearer "+TripManagement.readValueFromPreferences(HomeActivity.this,"accesstoken",""));
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */

        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }


    @OnClick(R.id.LinLayoutEdit)
    public void setLinLayoutEdit()
    {
        UsernameTextview.setVisibility(View.GONE);
        UsernameEdittext.setVisibility(View.VISIBLE);

        LinLayoutEdit.setVisibility(View.GONE);
        LinLayoutTick.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.LinLayoutTick)
    public void setLinLayoutTick()
    {
        LinLayoutEdit.setVisibility(View.VISIBLE);
        LinLayoutTick.setVisibility(View.GONE);

        UsernameTextview.setVisibility(View.VISIBLE);
        UsernameEdittext.setVisibility(View.GONE);


        UpdateProfilenames();


    }


    public  boolean isStoragePermissionGranted()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    public  boolean isStoragePermissionGranted1()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                startActivity(new Intent(HomeActivity.this,PicturePreviewActivity.class));
                TripManagement.saveValueuToPreferences(HomeActivity.this,"ForLogo","");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    public  boolean isWriteStoragePermissionGranted()
    {


        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted2");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted2");
            return true;
        }
    }
    public  boolean isReadStoragePermissionGranted()
    {

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted1");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted1");
            return true;
        }
    }


    @OnClick(R.id.LinLayoutDashboard)
    public void setLinLayoutDashboard()
    {
        welcomeactivity.closeDrawers();
        DashboardFragment dashboardFragment = new DashboardFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_content,dashboardFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void onObjectDetected(Barcode data)
    {
        String regexStr = "^[0-9]*$";
        if(!data.displayValue.matches(regexStr))
        {
            Intent mIntent = new Intent();
            mIntent.putExtra(BarcodeObject, data);
            setResult(CommonStatusCodes.SUCCESS, mIntent);
            TripManagement.saveValueuToPreferences(this,"Scanned","Scanned");

            // For beap sound
            ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 50);
            toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200); // 200 is duration in ms
            //For beap sound

            finish();

        }

        }
    @OnClick(R.id.LinLayoutHelp_support)
    public void setLinLayoutHelp_support()
    {
        Intercom.client().setLauncherVisibility(Intercom.Visibility.VISIBLE);
    }

    @OnClick(R.id.LinLayoutEVisitingcard)
    public void setLinLayoutEVisitingcard()
    {
       if (LinlayoutEcardDropdown.getVisibility()==View.VISIBLE)
       {
           LinlayoutEcardDropdown.setVisibility(View.GONE);

       }
       else
       {
           LinlayoutEcardDropdown.setVisibility(View.VISIBLE);
       }

    }

    @OnClick(R.id.TvCreateecard)
    public void setTvCreateecard()
    {
        welcomeactivity.closeDrawers();
        CreateEcardFragmnet createEcardFragmnet = new CreateEcardFragmnet();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_content,createEcardFragmnet);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}

