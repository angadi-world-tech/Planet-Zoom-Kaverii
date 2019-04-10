package com.angadi.tripmanagementa.Fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.angadi.tripmanagementa.Activities.HomeActivity;
import com.angadi.tripmanagementa.Activities.PicturePreviewActivity;
import com.angadi.tripmanagementa.Activities.Qrcoderesult;
import com.angadi.tripmanagementa.Activities.database.DatabaseHelper;
import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.Custum.CustomDateTimePicker;
import com.angadi.tripmanagementa.Custum.GPSTracker;
import com.angadi.tripmanagementa.Custum.LocationHelper;
import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraUtils;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.Size;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

import static android.app.Activity.RESULT_OK;

import static android.content.Context.LOCATION_SERVICE;



public class EventsFragment extends Fragment implements LocationHelper.OnLocationCompleteListener
{
    private LocationHelper locationHelper;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private Geocoder geocoder;
    private List<Address> addresses;
    String  mail = "", number = "", country = "",
            cityfull = "", gender = "", age = "", latitudenew = "",
            longitude_new = "", pincode = "", state = "",address_save="";

    private static WeakReference<byte[]> image;
    List<EditText> EdtProductName = new ArrayList<EditText>();
    List<EditText> EdtPrice = new ArrayList<EditText>();
    List<EditText> EdtQuantity = new ArrayList<EditText>();
    int subProdID  = 3;

    List<TextView> TxtID = new ArrayList<TextView>();
    List<TextView> TxtTotal = new ArrayList<TextView>();
    String DytableSt;
    private Uri filePath;
    File FrileTogetCapturedPhotoPath;
    String studentBatchListString;

    private DatabaseHelper db;

    TextView TVClose,TV_Invali;
    Dialog dialog_invalid_qr;
    LinearLayout LinLayoutClose;

    int sl_count = 3;
    int countForproduct = 1;
    String[] data1, strArr;

    String product_idupdate,photoupdate,LocationFromList,ProductNameFromList,PhoneFromList,landlineFromList,DesrciptionFromList,
            WebsiteFromList,EmailFromList,PriceFromlist,ValidityStartFromList,ValidityEndFromlist,qualityFromList,quantityFromList,Product_id;



    private int GALLERY = 1;

    LocationManager locationManager;
    Bitmap bitmapForUpload,bitmap_takenpic;
    Bitmap bmp;
    String jsonObject_global_code;
    CameraView camera;
    private boolean mCapturingPicture;
    private Size mCaptureNativeSize;
    private long mCaptureTime;
    public static final int PICK_IMAGE = 1;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    PicturePreviewActivity.FileDetail fileDetail = null;
    String PostProductURL = TripManagement.BASE_URL+"product";

    protected static final int REQUEST_CHECK_SETTINGS = 0x1;


    GPSTracker gps;
    double latitude ;
    double longitude;
    boolean GpsStatus;
    CustomDateTimePicker custom;
    File file ;

    String uploadPDFasStringToServer;

    Context context = getActivity();


    @BindView(R.id.LinLayoutBack) LinearLayout LinLayoutBack;
    @BindView(R.id.LinlayoutCurrentLocation) LinearLayout LinlayoutCurrentLocation;
    @BindView(R.id.generateQRCodeButton) Button generateQRCodeButton;
    @BindView(R.id.ImageViewEdit)
    ImageView ImageViewEdit;
    @BindView(R.id.ImageViewTick) ImageView ImageViewTick;
    @BindView(R.id.LinLayoutTransperant) LinearLayout LinLayoutTransperant;
    @BindView(R.id.image) ImageView imageView;
    @BindView(R.id.RellayoutImage) RelativeLayout RellayoutImage;
    @BindView(R.id.lacationEdittext) EditText lacationEdittext;
    @BindView(R.id.TextiewEdit) TextView TextiewEdit;
    @BindView(R.id.EditTextEdit) EditText EditTextEdit;
    @BindView(R.id.LinLayoutEdit) LinearLayout LinLayoutEdit;

    @BindView(R.id.ImageviewAppIcon) ImageView ImageviewAppIcon;
    @BindView(R.id.ShopnameEdittext) EditText ShopnameEdittext;
    @BindView(R.id.Email_new_Edittext) EditText Email_new_Edittext;
    @BindView(R.id.websiteEdittext) EditText websiteEdittext;
    @BindView(R.id.mobileEdittext) EditText mobileEdittext;
    @BindView(R.id.descriptionEdittext) EditText descriptionEdittext;
    @BindView(R.id.ButtonChooseFile) Button ButtonChooseFile;
    @BindView(R.id.cancelAnAttachment) ImageView cancelAnAttachment;
    @BindView(R.id.landlineEdittext) EditText landlineEdittext;
    @BindView(R.id.RellayoutCapture) RelativeLayout RellayoutCapture;
    @BindView(R.id.RelLayoutCameraLayout) RelativeLayout RelLayoutCameraLayout;
    @BindView(R.id.Linlayoutenlarge) LinearLayout Linlayoutenlarge;
    @BindView(R.id.RelLayoutFields) RelativeLayout RelLayoutFields;
    @BindView(R.id.ImageviewEnlarge) ImageView ImageviewEnlarge;
    @BindView(R.id.RelLayoutDelete) RelativeLayout RelLayoutDelete;
    @BindView(R.id.LinLayoutDelete) LinearLayout LinLayoutDelete;
    @BindView(R.id.LinlayoutFrontCam) LinearLayout LinlayoutFrontCam;

    @BindView(R.id.LinlayoutPhoto1) LinearLayout LinlayoutPhoto1;
    @BindView(R.id.LinlayoutPhoto2) LinearLayout LinlayoutPhoto2;
    @BindView(R.id.LinlayoutPhoto3) LinearLayout LinlayoutPhoto3;
    @BindView(R.id.LinlayoutPhoto4) LinearLayout LinlayoutPhoto4;
    @BindView(R.id.LinlayoutPhoto5) LinearLayout LinlayoutPhoto5;
    @BindView(R.id.PlusImageviewFirst) ImageView PlusImageviewFirst;
    @BindView(R.id.PlusImageviewSecond) ImageView PlusImageviewSecond;
    @BindView(R.id.PlusImageviewThird) ImageView PlusImageviewThird;
    @BindView(R.id.PlusImageviewFourth) ImageView PlusImageviewFourth;
    @BindView(R.id.PlusImageviewFifth) ImageView PlusImageviewFifth;
    @BindView(R.id.pdfText1) TextView pdfText1;
    @BindView(R.id.add_logo) TextView add_logo;

    @BindView(R.id.LinLayoutUpdateProductdetails) LinearLayout LinLayoutUpdateProductdetails;
    @BindView(R.id.LinLayoutGenaratingQRAndImage) LinearLayout LinLayoutGenaratingQRAndImage;
    @BindView(R.id.UpdateDetailsButton) Button UpdateDetailsButton;
    @BindView(R.id.nameTV) TextView nameTV;
    @BindView(R.id.shopnameTV) TextView shopnameTV;
    @BindView(R.id.mobileTV) TextView mobileTV;
    @BindView(R.id.attachmentTV) TextView attachmentTV;
    @BindView(R.id.landlineTV) TextView landlineTV;
    @BindView(R.id.emailTV) TextView emailTV;
    @BindView(R.id.webSiteTV) TextView webSiteTV;
    @BindView(R.id.descriptionTV) TextView descriptionTV;

    @BindView(R.id.HeaderProductNameTV) TextView HeaderProductNameTV;
    @BindView(R.id.GalleryTV) TextView GalleryTV;

    @BindView(R.id.PhotoImage1) ImageView PhotoImage1;
    @BindView(R.id.PhotoImage2) ImageView PhotoImage2;
    @BindView(R.id.PhotoImage3) ImageView PhotoImage3;
    @BindView(R.id.PhotoImage4) ImageView PhotoImage4;
    @BindView(R.id.PhotoImage5) ImageView PhotoImage5;


    static TextView TextviewNofilechoosen;

    public static void setImage(@Nullable byte[] im)
    {
        image = im != null ? new WeakReference<>(im) : null;
    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.layout_events,container,false);
        ButterKnife.bind(this,view);
        Fabric.with(getActivity(), new Crashlytics());

        LinLayoutUpdateProductdetails.setVisibility(View.GONE);
        LinLayoutGenaratingQRAndImage.setVisibility(View.VISIBLE);




        Linlayoutenlarge.setTag("enlarge");


        camera = view.findViewById(R.id.camera);
        camera.setLifecycleOwner(this);
        camera.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] picture)
            {
                onPicture(picture);
            }
        });

        GPSStatus();
        TextviewNofilechoosen = (TextView)view.findViewById(R.id.TextviewNofilechoosen);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.black));
        }

        Typeface montserrat_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Montserrat-Regular.ttf");
        Typeface montserrat_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MONTSERRAT-BOLD.OTF");




        TextiewEdit.setTypeface(montserrat_regular);
        HeaderProductNameTV.setTypeface(montserrat_bold);
        EditTextEdit.setTypeface(montserrat_regular);
        add_logo.setTypeface(montserrat_regular);
        nameTV.setTypeface(montserrat_regular);
        lacationEdittext.setTypeface(montserrat_regular);
        shopnameTV.setTypeface(montserrat_regular);
        ShopnameEdittext.setTypeface(montserrat_regular);
        mobileTV.setTypeface(montserrat_regular);
        mobileEdittext.setTypeface(montserrat_regular);
        landlineTV.setTypeface(montserrat_regular);
        emailTV.setTypeface(montserrat_regular);
        webSiteTV.setTypeface(montserrat_regular);
        descriptionTV.setTypeface(montserrat_regular);

        landlineEdittext.setTypeface(montserrat_regular);
        Email_new_Edittext.setTypeface(montserrat_regular);
        websiteEdittext.setTypeface(montserrat_regular);
        descriptionEdittext.setTypeface(montserrat_regular);
        GalleryTV.setTypeface(montserrat_regular);


        attachmentTV.setTypeface(montserrat_regular);
        ButtonChooseFile.setTypeface(montserrat_regular);
        TextviewNofilechoosen.setTypeface(montserrat_regular);








        pdfText1.setTypeface(montserrat_regular);


        generateQRCodeButton.setTypeface(montserrat_regular);

        UpdateDetailsButton.setTypeface(montserrat_regular);



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


        gps = new GPSTracker(getActivity());
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();


        return view;
    }




    public static void hideSoftKeyboard(Activity activity)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }



    private void onPicture(byte[] jpeg)
    {

        final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (250 * scale + 0.5f);


        RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, pixels);
        rel_btn.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        RellayoutImage.setLayoutParams(rel_btn);
        RelLayoutFields.setVisibility(View.VISIBLE);


        mCapturingPicture = false;
        long callbackTime = System.currentTimeMillis();


        // This can happen if picture was taken with a gesture.
        if (mCaptureTime == 0) mCaptureTime = callbackTime - 300;
        if (mCaptureNativeSize == null) mCaptureNativeSize = camera.getPictureSize();

        camera.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        RelLayoutCameraLayout.setVisibility(View.GONE);
        RelLayoutDelete.setVisibility(View.VISIBLE);



        this.setImage(jpeg);

        getBitmapAndUpload();

        mCaptureTime = 0;
        mCaptureNativeSize = null;
    }




    @OnClick(R.id.LinLayoutBack)
    public void setLinLayoutBack()
    {
        startActivity(new Intent(getActivity(),Qrcoderesult.class));

    }

    @OnClick(R.id.Linlayoutenlarge)
    public void setLinlayoutenlarge()
    {

        if (Linlayoutenlarge.getTag() == "compress")
        {
            Linlayoutenlarge.setTag("enlarge");
            ImageviewEnlarge.setImageResource(R.mipmap.enlarge_icon);
            final float scale = getResources().getDisplayMetrics().density;
            int pixels = (int) (250 * scale + 0.5f);


            RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, pixels);
            rel_btn.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            RellayoutImage.setLayoutParams(rel_btn);
            RelLayoutFields.setVisibility(View.VISIBLE);

        }
        else if (Linlayoutenlarge.getTag() == "enlarge")
        {
            ImageviewEnlarge.setImageResource(R.mipmap.compress_icon);


            final float scale = getResources().getDisplayMetrics().density;
            int pixels = (int) (500 * scale + 0.5f);


            RelativeLayout.LayoutParams rel_btn = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, pixels);
            rel_btn.addRule(RelativeLayout.CENTER_IN_PARENT);
            RellayoutImage.setLayoutParams(rel_btn);
            RelLayoutFields.setVisibility(View.GONE);

            Linlayoutenlarge.setTag("compress");

        }

    }


    @OnClick(R.id.LinlayoutCurrentLocation)
    public void setLinlayoutCurrentLocation()
    {
        if (GpsStatus == true)
        {

            // setaddress(latitude,longitude);

        }
        else
        {

            displayLocationSettingsRequest(getActivity());
        }

    }

    @OnClick(R.id.ImageViewTick)
    public void setImageViewTick()
    {
        TextiewEdit.setVisibility(View.VISIBLE);
        EditTextEdit.setVisibility(View.GONE);
        ImageViewEdit.setVisibility(View.VISIBLE);
        ImageViewTick.setVisibility(View.GONE);
        //  hideSoftKeyboard(this);

        TripManagement.saveValueuToPreferences(getActivity(),"PhotoName",EditTextEdit.getText().toString());
        TextiewEdit.setText(TripManagement.readValueFromPreferences(getActivity(),"PhotoName", ""));
        ShopnameEdittext.setText(TripManagement.readValueFromPreferences(getActivity(),"PhotoName", ""));
    }

    @OnClick(R.id.LinLayoutEdit)
    public void setLinLayoutEdit()
    {
        TextiewEdit.setVisibility(View.GONE);
        EditTextEdit.setVisibility(View.VISIBLE);
        EditTextEdit.requestFocus();
        EditTextEdit.setSelection(EditTextEdit.getText().length());
        ImageViewEdit.setVisibility(View.GONE);
        ImageViewTick.setVisibility(View.VISIBLE);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        InputMethodManager inputMethodManager =
                (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(
                ImageViewEdit.getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);


    }

    @OnClick(R.id.RellayoutImage) public void setRellayoutImage()
    {
        if (LinLayoutTransperant.getVisibility()==View.VISIBLE)
        {
            LinLayoutTransperant.setVisibility(View.GONE);
        }
        else
        {
            LinLayoutTransperant.setVisibility(View.VISIBLE);
        }
    }


    public String setaddress(double latitude, double longitude)
    {

        Geocoder geocoder;
        List<Address> addressList;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {
            addressList = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addressList != null && addressList.size() > 0)
            {
                Address address = addressList.get(0);
                LocationSearchFragment.address_current = addressList.get(0);
                LocationSearchFragment.latlngcurrent = new LatLng( LocationSearchFragment.address_current.getLatitude(), LocationSearchFragment.address_current.getLongitude());
                if (address.getAddressLine(1) == null)
                {
                    LocationSearchFragment.full_address=address.getAddressLine(0);
                    lacationEdittext.setText( LocationSearchFragment.full_address);

                }
                else if (address.getAdminArea()== null)
                {
                    LocationSearchFragment.full_address=address.getAddressLine(0)+" "+address.getAddressLine(1);

                    lacationEdittext.setText( LocationSearchFragment.full_address);

                }
                else if (address.getLocality()== null)
                {
                    LocationSearchFragment.full_address=address.getAddressLine(0)+" "+address.getAddressLine(1)+" "+" "+address.getAdminArea();
                    lacationEdittext.setText( LocationSearchFragment.full_address);

                }
                else
                {
                    LocationSearchFragment.full_address=address.getAddressLine(0)+" "+address.getAddressLine(1)+" "+address.getLocality()+" "+address.getAdminArea();
                    lacationEdittext.setText( LocationSearchFragment.full_address);
                }

            }



        } catch (IOException e) {

            Toast.makeText(getActivity(), "Please Reboot/Restart your Device to get Current Address", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onStart()
    {

        super.onStart();
        GPSStatus();
        gps = new GPSTracker(getActivity());
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();
        // setaddress(latitude,longitude);

    }

    @Override
    public void onResume()
    {
        super.onResume();
        GPSStatus();
        gps = new GPSTracker(getActivity());
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();
        // setaddress(latitude,longitude);

    }


    public void GPSStatus(){
        locationManager = (LocationManager)getActivity().getSystemService(LOCATION_SERVICE);
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public  void displayLocationSettingsRequest(final Context context) {
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
                        // Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }


    @OnClick(R.id.TextiewEdit)
    public void setTextiewEdit()
    {

    }

    @OnClick(R.id.EditTextEdit)
    public void srtEditTextEdit()
    {

    }


    @OnClick(R.id.ImageviewAppIcon)
    public void setImageviewAppIcon()
    {
//        CameraQRFragment cameraQRFragment = new CameraQRFragment();
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.FramePicturePreview,cameraQRFragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();



    }

    @OnClick(R.id.RellayoutCapture)
    public void setRellayoutCapture()
    {
        camera.capturePicture();
    }





    @OnClick(R.id.ButtonChooseFile)
    public void setButtonChooseFile()
    {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);



        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (requestCode == GALLERY)
        {
            if (data != null)
            {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    if(bitmap!=null)
                    {
                        if (TripManagement.readValueFromPreferences(getActivity(),"OffersImageview","").equals("OffersImageview"))
                        {
                            TripManagement.saveValueuToPreferences(getActivity(),"OffersImageview","");

                        }
                        else  if (TripManagement.readValueFromPreferences(getActivity(),"ClickedOnPhoto1","").equals("ClickedOnPhoto1"))
                        {

                            TripManagement.saveValueuToPreferences(getActivity(),"ClickedOnPhoto1","");
                            PlusImageviewFirst.setVisibility(View.GONE);
                            PhotoImage1.setVisibility(View.VISIBLE);
                            PhotoImage1.setImageBitmap(bitmap);
                        }

                        else  if (TripManagement.readValueFromPreferences(getActivity(),"ClickedOnPhoto2","").equals("ClickedOnPhoto2"))
                        {

                            TripManagement.saveValueuToPreferences(getActivity(),"ClickedOnPhoto2","");
                            PlusImageviewSecond.setVisibility(View.GONE);
                            PhotoImage2.setVisibility(View.VISIBLE);
                            PhotoImage2.setImageBitmap(bitmap);
                        }

                        else  if (TripManagement.readValueFromPreferences(getActivity(),"ClickedOnPhoto3","").equals("ClickedOnPhoto3"))
                        {

                            TripManagement.saveValueuToPreferences(getActivity(),"ClickedOnPhoto3","");
                            PlusImageviewThird.setVisibility(View.GONE);
                            PhotoImage3.setVisibility(View.VISIBLE);
                            PhotoImage3.setImageBitmap(bitmap);
                        }
                        else  if (TripManagement.readValueFromPreferences(getActivity(),"ClickedOnPhoto4","").equals("ClickedOnPhoto4"))
                        {

                            TripManagement.saveValueuToPreferences(getActivity(),"ClickedOnPhoto4","");
                            PlusImageviewFourth.setVisibility(View.GONE);
                            PhotoImage4.setVisibility(View.VISIBLE);
                            PhotoImage4.setImageBitmap(bitmap);
                        }
                        else  if (TripManagement.readValueFromPreferences(getActivity(),"ClickedOnPhoto5","").equals("ClickedOnPhoto5"))
                        {

                            TripManagement.saveValueuToPreferences(getActivity(),"ClickedOnPhoto5","");
                            PlusImageviewFifth.setVisibility(View.GONE);
                            PhotoImage5.setVisibility(View.VISIBLE);
                            PhotoImage5.setImageBitmap(bitmap);
                        }

                        else if (TripManagement.readValueFromPreferences(getActivity(),"ClickedOnPhoto4","").equals("ClickedOnPhoto4"))
                        {

                            TripManagement.saveValueuToPreferences(getActivity(),"ClickedOnPhoto4","");
                            PlusImageviewFourth.setVisibility(View.GONE);
                            PhotoImage4.setVisibility(View.VISIBLE);
                            PhotoImage4.setImageBitmap(bitmap);
                        }

                        else
                        {
                            Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();
                            camera.setVisibility(View.GONE);
                            imageView.setVisibility(View.VISIBLE);
                            RelLayoutCameraLayout.setVisibility(View.GONE);
                            RelLayoutDelete.setVisibility(View.VISIBLE);
                            imageView.setImageBitmap(bitmap);

                            TripManagement.saveValueuToPreferences(getActivity(),"ForLogo",BitMapToString(bitmap));
                        }



                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        }


        if (requestCode == PICK_IMAGE&& resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();
            ButtonChooseFile.setText("1 file choosen");

            if (data!=null)
            {

                Uri uri = data.getData();
                if (uri != null)
                {
                    getFileDetailFromUri(getActivity(), uri);
                }
            }


            //TODO: action
        }







        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                //getting bitmap object from uri
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);

                //displaying selected image to imageview
                // imageView.setImageBitmap(bitmap);

                //calling the method uploadBitmap to upload image

                //   uploadProduct(bitmap);

            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public PicturePreviewActivity.FileDetail getFileDetailFromUri(final Context context, final Uri uri)
    {

        if (uri != null)
        {
            fileDetail = new PicturePreviewActivity.FileDetail();
            // File Scheme.
            if (ContentResolver.SCHEME_FILE.equals(uri.getScheme()))
            {
                file = new File(uri.getPath());
                TripManagement.saveValueuToPreferences(context,"path",file.getPath());
                Log.d("fcgfcfg",file.getPath());
                Log.d("fcgfcfggg",file.getAbsolutePath());
                Log.d("fcgfcfggggg", String.valueOf(file.getAbsoluteFile()));

                fileDetail.fileName = file.getName();
                fileDetail.fileSize = file.length();
                TextviewNofilechoosen.setText(fileDetail.fileName);
                cancelAnAttachment.setVisibility(View.VISIBLE);

            }
            // Content Scheme.
            else if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
                Cursor returnCursor =
                        context.getContentResolver().query(uri, null, null, null, null);
                if (returnCursor != null && returnCursor.moveToFirst())
                {

                    int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                    fileDetail.fileName = returnCursor.getString(nameIndex);
                    fileDetail.fileSize = returnCursor.getLong(sizeIndex);
                    Log.d("hbhv", uri.getPath());
                    returnCursor.close();


                    TextviewNofilechoosen.setText(fileDetail.fileName);
                    cancelAnAttachment.setVisibility(View.VISIBLE);
                }
            }
        }
        return fileDetail;
    }



    /**
     * File Detail.
     * <p>
     * 1. Model used to hold file details.
     */
    public static class FileDetail
    {

        // fileSize.
        public String fileName;

        // fileSize in bytes.
        public long fileSize;

        /**
         * Constructor.
         */
        public FileDetail()
        {

        }
    }

    @OnClick(R.id.cancelAnAttachment)
    public void setcancelAnAttachment()
    {
        TextviewNofilechoosen.setText("No files choosen");
        cancelAnAttachment.setVisibility(View.GONE);
        ButtonChooseFile.setText("Choose a file");
    }


    private void sendAndRequestResponse()
    {


        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(getActivity());
        String url = "https://plus.codes/api?address="+latitude+","+longitude+"&email=kaveri.angadi@gmail.com";
        Log.d("URL",url);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //  Toast.makeText(PicturePreviewActivity.this,"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
                JSONObject jsonObject = null;

                try
                {
                    jsonObject = new JSONObject(response);

                    JSONObject jsonObject_plus_code = jsonObject.getJSONObject("plus_code");
                    jsonObject_global_code = jsonObject_plus_code.getString("global_code");
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    Log.d("Exeption", String.valueOf(e));
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
               // Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] arr=baos.toByteArray();
        String result = Base64.encodeToString(arr, Base64.DEFAULT);
        return result;
    }

    // String to Bitmap:
    public Bitmap StringToBitMap(String image){
        try{
            byte [] encodeByte=Base64.decode(image,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    private void getBitmapAndUpload()
    {
        byte[] b = image == null ? null : image.get();
        if (b == null)
        {
            Log.d("returned","returned");
            Log.d("b-->valiue", String.valueOf(b));
            return;
        }

        CameraUtils.decodeBitmap(b, 1000, 1000, new CameraUtils.BitmapCallback() {
            @Override
            public void onBitmapReady(Bitmap bitmap)
            {
                imageView.setImageBitmap(bitmap);
                TripManagement.saveValueuToPreferences(getActivity(),"Bitmapsave",BitMapToString(bitmap));
                bitmapForUpload = bitmap;

                Uri tempUri = getImageUri(getActivity(), bitmap);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                FrileTogetCapturedPhotoPath = new File(getRealPathFromURI(tempUri));

            }
        });

    }


    @OnClick(R.id.RelLayoutDelete)
    public void setRelLayoutDelete()
    {
        imageView.setVisibility(View.GONE);
        RelLayoutCameraLayout.setVisibility(View.VISIBLE);

        camera.setVisibility(View.VISIBLE);
        RelLayoutDelete.setVisibility(View.GONE);
    }
    @OnClick(R.id.LinlayoutFrontCam)
    public void setLinlayoutFrontCam()
    {
        camera.toggleFacing();
    }

    @OnClick(R.id.LinlayoutPhoto1)
    public void setLinlayoutPhoto1()
    {
        TripManagement.saveValueuToPreferences(getActivity(),"ClickedOnPhoto1","ClickedOnPhoto1");
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }


    @OnClick(R.id.LinlayoutPhoto2)
    public void setLinlayoutPhoto2()
    {
        TripManagement.saveValueuToPreferences(getActivity(),"ClickedOnPhoto2","ClickedOnPhoto2");
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }


    @OnClick(R.id.LinlayoutPhoto3)
    public void setLinlayoutPhoto3()
    {
        TripManagement.saveValueuToPreferences(getActivity(),"ClickedOnPhoto3","ClickedOnPhoto3");
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }


    @OnClick(R.id.LinlayoutPhoto4)
    public void setLinlayoutPhoto4()
    {
        TripManagement.saveValueuToPreferences(getActivity(),"ClickedOnPhoto4","ClickedOnPhoto4");
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }


    @OnClick(R.id.LinlayoutPhoto5)
    public void setLinlayoutPhoto5()
    {
        TripManagement.saveValueuToPreferences(getActivity(),"ClickedOnPhoto5","ClickedOnPhoto5");
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }



    @OnClick(R.id.pdfText1)
    public void setpdfText1()
    {
        //open the pdf


        File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() +"/"+ fileDetail.fileName);
        Log.d("gvhgc",fileDetail.fileName);
        Log.d("gvhgccc",file.toString());
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file),"application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent intent = Intent.createChooser(target, "Open File");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Instruct the user to install a PDF reader here, or something
           // Toast.makeText(context, "catch"+e, Toast.LENGTH_SHORT).show();
        }


    }

    @OnClick(R.id.add_logo)
    public void setadd_logo()
    {

        choosePhotoFromGallary();


    }

    public void  choosePhotoFromGallary()
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    public void needed()
    {


//        File file = new File(fileDetail.fileName);
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        byte [] data = new byte[(int)file.length()];
//        try {
//            fis.read(data);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        data = bos.toByteArray();
//
//
//        byte[] bitmapdata = new byte[0]; // let this be your byte array
//        Bitmap bitmap = BitmapFactory.decodeByteArray(data , 0, data .length);

        File file = new File(fileDetail.fileName);
        Log.d("naehgvhgv--->",fileDetail.fileName);

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //System.out.println(file.exists() + "!!");
        //InputStream in = resource.openStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); //no doubt here is 0
                //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex)
        {
            Toast.makeText(getActivity(), "Catch", Toast.LENGTH_SHORT).show();

        }
        byte[] bytes = bos.toByteArray();

        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes , 0, bytes .length);


        uploadPDFasStringToServer = Base64.encodeToString(getBytesFromBitmap(bitmap), Base64.NO_WRAP);




        //to convert pdf to byte array
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getActivity().getContentResolver() != null) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }



    @Override
    public void getLocationUpdate(Location location) {
        try {
//            Log.d(TAG, "getLatitude : " + location.getLatitude());
//            Log.d(TAG, "getLatitude : " + location.getLongitude());
            latitudenew = "" +location.getLatitude();
            longitude_new = "" +location.getLongitude();

            List<Address> addressList = geocoder.getFromLocation(
                    location.getLatitude(), location.getLongitude(), 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append("\n");
                    address_save=address.getAddressLine(i);
                    lacationEdittext.setText(address_save);
                }
                address_save = addressList.get(0).getAddressLine(0);


                lacationEdittext.setText(address_save);
            }

//                +"\n"+
//                        addressList.get(0).getAddressLine(1).toString()+"\n"+
//                        addressList.get(0).getAddressLine(2).toString()+"\n"+
//                        addressList.get(0).getAddressLine(3).toString()

        } catch (IOException e) {

        }
    }

    @Override
    public void onError(ConnectionResult connectionResult, Status status, String error) {
        if (connectionResult != null) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(getActivity(),
                            LocationHelper.CONNECTION_FAILURE_RESOLUTION_REQUEST);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        } else if (status != null) {
            // Location is not available, but we can ask permission from users
            try {

                status.startResolutionForResult(getActivity(), LocationHelper.REQUEST_CHECK_SETTINGS);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }
    }




}
