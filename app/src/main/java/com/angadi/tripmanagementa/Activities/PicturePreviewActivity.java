package com.angadi.tripmanagementa.Activities;

import android.Manifest;
import android.app.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;

import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.angadi.tripmanagementa.Activities.Constant;
import com.angadi.tripmanagementa.Activities.Qrcoderesult;
import com.angadi.tripmanagementa.Activities.SubCategory.Helper.RecyclerTouchListener;
import com.angadi.tripmanagementa.Activities.SubCategory.Helper.SubCategoryListAdapter;
import com.angadi.tripmanagementa.Activities.SubCategory.Helper.TextViewSFProDisplaySemibold;
import com.angadi.tripmanagementa.Activities.SubCategory.SubCatAll;
import com.angadi.tripmanagementa.Activities.SubCategory.SubCatNew;
import com.angadi.tripmanagementa.Activities.database.DatabaseHelper;
import com.angadi.tripmanagementa.Activities.database.model.mImageUpload;
import com.angadi.tripmanagementa.Activities.database.model.mSubCategory;
import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.Custum.CustomDateTimePicker;
import com.angadi.tripmanagementa.Custum.FilePath;
import com.angadi.tripmanagementa.Custum.GPSTracker;
import com.angadi.tripmanagementa.Custum.LocationHelper;
import com.angadi.tripmanagementa.Extras.Keys;
import com.angadi.tripmanagementa.Extras.VolleyMultipartRequest;
import com.angadi.tripmanagementa.Fragments.ChatListFragment;
import com.angadi.tripmanagementa.Fragments.LocationSearchFragment;
import com.angadi.tripmanagementa.Fragments.ProductDetailsQRFragment;
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
import com.google.gson.Gson;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraUtils;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.Size;
import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

import static android.content.ContentValues.TAG;
import static com.angadi.tripmanagementa.Activities.MapsActivity.REQUEST_CHECK_SETTINGS;


public class PicturePreviewActivity extends AppCompatActivity implements LocationHelper.OnLocationCompleteListener
{

    List<File> imgFile = new ArrayList<>();
    List<Uri> uriArrayList = new ArrayList<>();


    private LocationHelper locationHelper;

    private Geocoder geocoder;

    String   latitudenew = "", longitude_new = "",address_save="";

    // SubCategory New - Start //
    public static DatabaseHelper db;
    private RecyclerView horizontalRecyclerView;
    public static SubCategoryListAdapter adapter;
    public static ArrayList<mSubCategory> mSubCategoryList = new ArrayList<mSubCategory>();
    public static ArrayList<mImageUpload> mImageUploadList = new ArrayList<mImageUpload>();
    String DbCount = "";
    public static TextViewSFProDisplaySemibold viewAll;
    public static LinearLayout horizontalscroll;
    // SubCategory New - End //

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
    FileDetail fileDetail = null;
    String PostProductURL = TripManagement.BASE_URL+"product";

    List<List<String>> subproducts;

    ProgressDialog progressDialog;

    int countQuantity = 0;
    GPSTracker gps;
    double latitude ;
    double longitude;
    boolean GpsStatus;
    CustomDateTimePicker custom;
    File file ;

    String uploadPDFasStringToServer;

    @BindView(R.id.LinLayoutBack) LinearLayout LinLayoutBack;
    @BindView(R.id.LinlayoutCurrentLocation) LinearLayout LinlayoutCurrentLocation;
    @BindView(R.id.generateQRCodeButton) Button generateQRCodeButton;
    @BindView(R.id.ImageViewEdit) ImageView ImageViewEdit;
    @BindView(R.id.ImageViewTick) ImageView ImageViewTick;
    @BindView(R.id.LinLayoutTransperant) LinearLayout LinLayoutTransperant;
    @BindView(R.id.image) ImageView imageView;
    @BindView(R.id.RellayoutImage) RelativeLayout RellayoutImage;
    @BindView(R.id.lacationEdittext) EditText lacationEdittext;
    @BindView(R.id.TextiewEdit) TextView TextiewEdit;
    @BindView(R.id.EditTextEdit) EditText EditTextEdit;
    @BindView(R.id.LinLayoutEdit) LinearLayout LinLayoutEdit;
    @BindView(R.id.generateImageCodeButton) Button generateImageCodeButton;
    @BindView(R.id.ImageviewAppIcon) ImageView ImageviewAppIcon;
    @BindView(R.id.ShopnameEdittext) EditText ShopnameEdittext;
    @BindView(R.id.Email_new_Edittext) EditText Email_new_Edittext;
    @BindView(R.id.websiteEdittext) EditText websiteEdittext;
   // @BindView(R.id.EdProductName3) EditText EdProductName3;
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
    @BindView(R.id.Textviewdate) TextView Textviewdate;
    @BindView(R.id.Textviewtime) TextView Textviewtime;
    @BindView(R.id.LinlayoutSetQuality) LinearLayout LinlayoutSetQuality;
    @BindView(R.id.TextViewSetQuality) TextView TextViewSetQuality;
    @BindView(R.id.EditTextQuantityCount)TextView EditTextQuantityCount;
    @BindView(R.id.LinlayoutdecreaseQuanitity) LinearLayout LinlayoutdecreaseQuanitity;
    @BindView(R.id.LinlayoutincreaseQuanitity) LinearLayout LinlayoutincreaseQuanitity;
    @BindView(R.id.ValidityLinlayoutFrom) LinearLayout ValidityLinlayoutFrom;
    //@BindView(R.id.TextviewValidity) TextView TextviewValidity;
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
    @BindView(R.id.offersEdittext) EditText offersEdittext;
    @BindView(R.id.add_logo) TextView add_logo;
    @BindView(R.id.priceIDEdittext) EditText priceIDEdittext;
    @BindView(R.id.TextViewvalidityFrom) TextView TextViewvalidityFrom;
    @BindView(R.id.TextViewvalidityTo) TextView TextViewvalidityTo;
    @BindView(R.id.ValidityLinlayoutTo) LinearLayout ValidityLinlayoutTo;
    @BindView(R.id.LinlayoutdateTime) LinearLayout LinlayoutdateTime;
    @BindView(R.id.LinLayoutUpdateProductdetails) LinearLayout LinLayoutUpdateProductdetails;
    @BindView(R.id.LinLayoutGenaratingQRAndImage) LinearLayout LinLayoutGenaratingQRAndImage;
    @BindView(R.id.UpdateDetailsButton) Button UpdateDetailsButton;

    @BindView(R.id.nameTV) TextView nameTV;
    @BindView(R.id.shopnameTV) TextView shopnameTV;
    @BindView(R.id.mobileTV) TextView mobileTV;
    @BindView(R.id.offersTV) TextView offersTV;
    @BindView(R.id.txtqyality) TextView txtqyality;
    @BindView(R.id.datetime) TextView datetime;
    @BindView(R.id.attachmentTV) TextView attachmentTV;
    @BindView(R.id.landlineTV) TextView landlineTV;
    @BindView(R.id.emailTV) TextView emailTV;
    @BindView(R.id.webSiteTV) TextView webSiteTV;
    @BindView(R.id.descriptionTV) TextView descriptionTV;
    @BindView(R.id.priceTV) TextView priceTV;
    @BindView(R.id.HeaderProductNameTV) TextView HeaderProductNameTV;

    @BindView(R.id.Add_ur_offer_photo) TextView Add_ur_offer_photo;
    @BindView(R.id.OfferImageview) ImageView OfferImageview;
    @BindView(R.id.PhotoImage1) ImageView PhotoImage1;
    @BindView(R.id.PhotoImage2) ImageView PhotoImage2;
    @BindView(R.id.PhotoImage3) ImageView PhotoImage3;
    @BindView(R.id.PhotoImage4) ImageView PhotoImage4;
    @BindView(R.id.PhotoImage5) ImageView PhotoImage5;
    @BindView(R.id.LinlayoutAddItems) LinearLayout LinlayoutAddItems;
    @BindView(R.id.TVAddItems) TextView TVAddItems;
    @BindView(R.id.RelLayoutUpdatephoto) RelativeLayout RelLayoutUpdatephoto;


    // @BindView(R.id.TextviewNofilechoosen) TextView TextviewNofilechoosen;


    static TextView TextviewNofilechoosen;

    public static void setImage(@Nullable byte[] im)
    {
        image = im != null ? new WeakReference<>(im) : null;
    }



    @Override
    protected void onRestart()
    {
        super.onRestart();
        GPSStatus();
        gps = new GPSTracker(this);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();
       // setaddress(latitude,longitude);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_picture_preview);
        ButterKnife.bind(this);

        Fabric.with(this, new Crashlytics());


        LinLayoutUpdateProductdetails.setVisibility(View.GONE);
        LinLayoutGenaratingQRAndImage.setVisibility(View.VISIBLE);

        db = new DatabaseHelper(this);



      //  isStoragePermissionGranted();

        locationHelper = new LocationHelper(PicturePreviewActivity.this,this);
        geocoder = new Geocoder(this, Locale.getDefault());

// Sub Category - Start //
        horizontalRecyclerView = (RecyclerView) findViewById(R.id.horizontalRecyclerView);
        viewAll = (TextViewSFProDisplaySemibold) findViewById(R.id.viewAll);
        horizontalscroll = (LinearLayout) findViewById(R.id.horizontalscroll);

        DbCount = String.valueOf(db.getSubCategoryCount());
        Log.d("checkidd ", "DB_Count : " + DbCount);

        if (db.getSubCategoryCount() == 50) {
            Log.d("Message : ", "Limit Exceeds !");
            LinlayoutAddItems.setVisibility(View.GONE);
        } else {
            LinlayoutAddItems.setVisibility(View.VISIBLE);
        }

        if (DbCount.equals("0") || DbCount.equals("NULL") || DbCount.equals("")) {
            viewAll.setVisibility(View.GONE);
            horizontalscroll.setVisibility(View.GONE);
        } else {
            subProduct();
            viewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (DbCount.equals("0") || DbCount.equals("NULL") || DbCount.equals("")) {
                        Log.d("Message ", "Db Info Empty !");
                    } else {
                        Log.d("Message ", "New Act Coming Soon");
                        startActivity(new Intent(PicturePreviewActivity.this, SubCatAll.class));
                    }
                }
            });
        }

        LinlayoutAddItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.getSubCategoryCount() == 50) {
                    Log.d("Message : ", "Limit Exceeds !");
                } else {
                    startActivity(new Intent(PicturePreviewActivity.this, SubCatNew.class));
                }
            }
        });

        horizontalRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                horizontalRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Constant.recyclerviewPosition = String.valueOf(position);
                Constant.mSubCategorysEd = mSubCategoryList.get(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        // Sub Category - End //

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            LinLayoutUpdateProductdetails.setVisibility(View.VISIBLE);
            LinLayoutGenaratingQRAndImage.setVisibility(View.GONE);
            product_idupdate = getIntent().getStringExtra("Product_id");
            photoupdate = getIntent().getStringExtra("Photo");

            LocationFromList = getIntent().getStringExtra("LocationFromList");
            ProductNameFromList = getIntent().getStringExtra("ProductNameFromList");
            PhoneFromList = getIntent().getStringExtra("PhoneFromList");
            landlineFromList = getIntent().getStringExtra("landlineFromList");
            DesrciptionFromList = getIntent().getStringExtra("DesrciptionFromList");
            WebsiteFromList = getIntent().getStringExtra("WebsiteFromList");
            EmailFromList = getIntent().getStringExtra("EmailFromList");
            PriceFromlist = getIntent().getStringExtra("PriceFromlist");
            ValidityStartFromList = getIntent().getStringExtra("ValidityStartFromList");
            ValidityEndFromlist = getIntent().getStringExtra("ValidityEndFromlist");
            qualityFromList = getIntent().getStringExtra("qualityFromList");
            quantityFromList = getIntent().getStringExtra("quantityFromList");
            LinlayoutdateTime.setVisibility(View.GONE);
            //The key argument here must match that used in the other activity
        }

        Linlayoutenlarge.setTag("enlarge");
        EditTextQuantityCount.setText(Integer.toString(countQuantity));

        camera = findViewById(R.id.camera);
        camera.setLifecycleOwner(this);
        camera.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] picture)
            {
                onPicture(picture);
            }
        });

        GPSStatus();
        TripManagement.saveValueuToPreferences(this,"ClickedOnGeneateImageButton","");
        TextviewNofilechoosen = (TextView)findViewById(R.id.TextviewNofilechoosen);
        TripManagement.saveValueuToPreferences(this,"PicturePreviewActivity","PicturePreviewActivity");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));
        }

        Typeface montserrat_regular = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");
        Typeface montserrat_bold = Typeface.createFromAsset(getAssets(), "fonts/MONTSERRAT-BOLD.OTF");




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
        priceTV.setTypeface(montserrat_regular);
        landlineEdittext.setTypeface(montserrat_regular);
        Email_new_Edittext.setTypeface(montserrat_regular);
        websiteEdittext.setTypeface(montserrat_regular);
        descriptionEdittext.setTypeface(montserrat_regular);

        priceIDEdittext.setTypeface(montserrat_regular);
        attachmentTV.setTypeface(montserrat_regular);
        ButtonChooseFile.setTypeface(montserrat_regular);
        TextviewNofilechoosen.setTypeface(montserrat_regular);
        datetime.setTypeface(montserrat_regular);
        Textviewdate.setTypeface(montserrat_regular);
        Textviewtime.setTypeface(montserrat_regular);
        txtqyality.setTypeface(montserrat_regular);
        TextViewSetQuality.setTypeface(montserrat_regular);
        EditTextQuantityCount.setTypeface(montserrat_regular);
        TextViewvalidityFrom.setTypeface(montserrat_regular);
        TextViewvalidityTo.setTypeface(montserrat_regular);
        pdfText1.setTypeface(montserrat_regular);
        offersTV.setTypeface(montserrat_regular);
        offersEdittext.setTypeface(montserrat_regular);
        generateQRCodeButton.setTypeface(montserrat_regular);
        generateImageCodeButton.setTypeface(montserrat_regular);
        UpdateDetailsButton.setTypeface(montserrat_regular);
        Add_ur_offer_photo.setTypeface(montserrat_regular);


        dialog_invalid_qr = new Dialog(PicturePreviewActivity.this);
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


        gps = new GPSTracker(this);
          latitude = gps.getLatitude();
          longitude = gps.getLongitude();

          sendAndRequestResponse();

        if (Constant.productDetailQRFragmentStatus.equals("Update"))
        {
            camera.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            RelLayoutCameraLayout.setVisibility(View.GONE);
            RelLayoutUpdatephoto.setVisibility(View.VISIBLE);
            if (Constant.pi_Photo!=null)
            {
                try {
                Picasso.with(this).load(Constant.pi_Photo).into(imageView);
                }catch (Exception e){
                    e.printStackTrace();
                    Picasso.with(getApplicationContext())
                            .load(R.drawable.gallery_backgroung)
                            .into(imageView);
                }
            }

            ShopnameEdittext.setText(Constant.pi_Name);
            HeaderProductNameTV.setText(Constant.pi_Name);
            lacationEdittext.setText(Constant.pi_Location);
            mobileEdittext.setText(Constant.pi_Phone);
            landlineEdittext.setText(Constant.pi_Landline);
            descriptionEdittext.setText(Constant.pi_Description);
            websiteEdittext.setText(Constant.pi_Website);
            Email_new_Edittext.setText(Constant.pi_Email);
            priceIDEdittext.setText(Constant.pi_Price);
            TextViewvalidityFrom.setText(Constant.pi_Validation_Start);
            TextViewvalidityTo.setText(Constant.pi_Validation_End);
//            TextViewSetQuality.setText(Constant.pi_Quality);
//            EditTextQuantityCount.setText(Constant.pi_Quantity);


           }
        else
        {
            camera.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            RelLayoutCameraLayout.setVisibility(View.VISIBLE);


        }
        //To set date & Time

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
       // SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat df = new SimpleDateFormat("2019-02-23 05:08:56");
        String formattedDate = df.format(c);
        Textviewdate.setText(formattedDate);
        Textviewtime.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date()));

        Log.d("TimeStamp",DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date()));

        //To set date & Time


        subproducts = new ArrayList<List<String>>();

        List<String> row1 = new ArrayList<String>(1);
        row1.add("pencils pencil");
        row1.add("10");
        subproducts.add(row1);





        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "demokav");
        map.put("price", "2000");
        map.get("name");

        List<Map<String, String>> data = new ArrayList<>();
        data.add(0, map);
        data.get(0).get("name");

        studentBatchListString = new Gson().toJson(data);

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(studentBatchListString);
            for (int i = 0; i < jsonArray.length(); i++)
            {
                 strArr = new String[jsonArray.length()];
               // strArr[i] = jsonArray.getString(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }







        data1 = new String[data.size()];
      //  data1 = data.toArray(data1);


        TextiewEdit.setText(TripManagement.readValueFromPreferences(this,"PhotoName", ""));
        ShopnameEdittext.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                String name =  ShopnameEdittext.getText().toString();
                EditTextEdit.setText(name);
                TextiewEdit.setText(name);
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });


        //To upload an image to server

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }


        //To uplaod an image to an server



    }

    private static float getApproximateFileMegabytes(Bitmap bitmap)
    {
        return (bitmap.getRowBytes() * bitmap.getHeight()) / 1024 / 1024;
    }

    @OnClick(R.id.LinLayoutBack)
    public void setLinLayoutBack()
    {
        startActivity(new Intent(PicturePreviewActivity.this, Qrcoderesult.class));

    }

    public void imageUpload() {
        List<mImageUpload> mImageUploadLists = db.getAllImageUpload();
        mImageUploadList.clear();
        for (mImageUpload mImageUploads : mImageUploadLists) {
            mImageUpload mImageUploades = new mImageUpload();
            try {
                System.out.println(mImageUploads.getId());
                Log.d("checkidd", "" + mImageUploads.getId());
                mImageUploades.setId(mImageUploads.getId());
                mImageUploades.setImage_path(mImageUploads.getImage_path());
                mImageUploades.setImage_uri(mImageUploads.getImage_uri());
                mImageUploades.setImage_type(mImageUploads.getImage_type());
                mImageUploades.setTime_date(mImageUploads.getTime_date());
                Log.d("checkidd", "" + mImageUploads.getId());
                Log.d("checkidd", "" + mImageUploads.getImage_path());
                Log.d("checkidd", "" + mImageUploads.getImage_uri());
                Log.d("checkidd", "" + mImageUploads.getImage_type());
                Log.d("checkidd", "" + mImageUploads.getTime_date());
                mImageUploadList.add(mImageUploades);
                imgFile.add(new File(String.valueOf(mImageUploades)));
                Uri uri = Uri.parse(mImageUploads.getImage_uri());
                uriArrayList.add(uri);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("checkidd ", "Exception : " + e.getMessage());
            }
        }
    }

    public void subProduct() {
        viewAll.setText("View All (" + DbCount + ")");
        if (DbCount.equals("0") || DbCount.equals("NULL") || DbCount.equals("")) {
            horizontalRecyclerView.setVisibility(View.GONE);
        } else {
            horizontalRecyclerView.setVisibility(View.VISIBLE);
            horizontalRecyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PicturePreviewActivity.this, LinearLayoutManager.HORIZONTAL, false);
            horizontalRecyclerView.setLayoutManager(layoutManager);

            List<mSubCategory> mSubCategories = db.getAllSubCategory();
            mSubCategoryList.clear();
            for (mSubCategory mSubCategory : mSubCategories) {
                mSubCategory mSubCategorys = new mSubCategory();
                try {
                    System.out.println(mSubCategory.getId());
                    Log.d("checkidd", "" + mSubCategory.getId());
                    mSubCategorys.setId(mSubCategory.getId());
                    mSubCategorys.setProduct_image(mSubCategory.getProduct_image());
                    mSubCategorys.setProduct_name(mSubCategory.getProduct_name());
                    mSubCategorys.setProduct_price(mSubCategory.getProduct_price());
                    mSubCategorys.setProduct_time_date(mSubCategory.getProduct_time_date());
                    Log.d("checkidd", "" + mSubCategory.getId());
                    Log.d("checkidd", "" + mSubCategory.getProduct_image());
                    Log.d("checkidd", "" + mSubCategory.getProduct_name());
                    Log.d("checkidd", "" + mSubCategory.getProduct_price());
                    Log.d("checkidd", "" + mSubCategory.getProduct_time_date());
                    mSubCategoryList.add(mSubCategorys);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("checkidd ", "Exception : " + e.getMessage());
                }
            }

            JSONArray jsonArray = new JSONArray();
            for (int ang = 0; ang < mSubCategoryList.size(); ang++) {
                try {
                    JSONObject data1 = new JSONObject();
                    data1.put("ID", mSubCategoryList.get(ang).getId());
                    data1.put("Product_Image", mSubCategoryList.get(ang).getProduct_image());
                    data1.put("Product_Name", mSubCategoryList.get(ang).getProduct_name());
                    data1.put("Product_Price", mSubCategoryList.get(ang).getProduct_price());
                    data1.put("Product_Date_Time", mSubCategoryList.get(ang).getProduct_time_date());
                    jsonArray.put(data1);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            JSONObject DataObj = new JSONObject();
            try {
                DataObj.put("Data", jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String jsonStr = DataObj.toString();
            System.out.println("jsonString: " + jsonStr);
            Log.d("checkidd ", "ListSize : " + mSubCategoryList.size());

            adapter = new SubCategoryListAdapter(mSubCategoryList, getApplicationContext());
            horizontalRecyclerView.setAdapter(adapter);
        }
    }


    @OnClick(R.id.Linlayoutenlarge)
    public void setLinlayoutenlarge() {
        if (Linlayoutenlarge.getTag() == "compress") {
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

            displayLocationSettingsRequest(this);
        }

        }


        @OnClick(R.id.generateQRCodeButton)
       public void setgenerateQRCodeButton()
    {
        if (ShopnameEdittext.getText().toString().length()==0 )
        {

            TV_Invali.setText("Name can not be empty");
            dialog_invalid_qr.show();


        }
        else if (lacationEdittext.getText().toString().length()==0)
        {
            TV_Invali.setText("Location can not be empty");
            dialog_invalid_qr.show();

        }
        else
        {
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            if (getCurrentFocus() != null)
            {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

            }


            TripManagement.saveValueuToPreferences(this,"ClickedOnGeneateImageButton","");
            TripManagement.saveValueuToPreferences(this,"ProductName123k",ShopnameEdittext.getText().toString());

            if (bitmapForUpload!=null)
            {


                uploadPDFasStringToServer = Base64.encodeToString(getBytesFromBitmap(bitmapForUpload), Base64.NO_WRAP);

                uploadProduct(bitmapForUpload);


            }
            else
                {
                    uploadProduct(StringToBitMap(TripManagement.readValueFromPreferences(PicturePreviewActivity.this,"ForLogo","")));

            }
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

        TripManagement.saveValueuToPreferences(this,"PhotoName",EditTextEdit.getText().toString());
        TextiewEdit.setText(TripManagement.readValueFromPreferences(this,"PhotoName", ""));
        ShopnameEdittext.setText(TripManagement.readValueFromPreferences(this,"PhotoName", ""));
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

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        InputMethodManager inputMethodManager =
                (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
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
        geocoder = new Geocoder(this, Locale.getDefault());

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

            Toast.makeText(this, "Please Reboot/Restart your Device to get Current Address", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onStart()
    {

        super.onStart();
        GPSStatus();
        gps = new GPSTracker(this);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();
       // setaddress(latitude,longitude);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        GPSStatus();
        gps = new GPSTracker(this);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();
       // setaddress(latitude,longitude);

        }


    public void GPSStatus(){
        locationManager = (LocationManager)this.getSystemService(this.LOCATION_SERVICE);
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
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
                       // Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                       // Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(PicturePreviewActivity.this, REQUEST_CHECK_SETTINGS);
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


    @OnClick(R.id.generateImageCodeButton)
    public void setgenerateImageeButton()
    {
//        if (ShopnameEdittext.getText().toString().length()==0 || Email_new_Edittext.getText().toString().length()==0 &&  websiteEdittext.getText().toString().length()==0||
//        mobileEdittext.getText().toString().length()==0 ||
//                descriptionEdittext.getText().toString().length()==0 )
//        {
//            Toast.makeText(this, "Please fill All details", Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
            TripManagement.saveValueuToPreferences(this,"ProductName123k",ShopnameEdittext.getText().toString());
            TripManagement.saveValueuToPreferences(this,"ProductEmail",Email_new_Edittext.getText().toString());
            TripManagement.saveValueuToPreferences(this,"ProductWebsite",websiteEdittext.getText().toString());
            TripManagement.saveValueuToPreferences(this,"ProductContact",mobileEdittext.getText().toString());
            TripManagement.saveValueuToPreferences(this,"productDesription",descriptionEdittext.getText().toString());



//            TripManagement.saveValueuToPreferences(this,"ClickedOnGeneateImageButton","ClickedOnGeneateImageButton");
//            ProductDetailsQRFragment productDetailsQRFragment = new ProductDetailsQRFragment();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.add(R.id.FramePicturePreview,productDetailsQRFragment);
//            fragmentTransaction.addToBackStack(null);
//            fragmentTransaction.commit();
       // }



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

    public void hideSoftKeyboard(Activity activity)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null)
        {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }    }

    @Override
    public void onBackPressed()
    {
       // super.onBackPressed();
        Intent main_activity = new Intent(PicturePreviewActivity.this, Qrcoderesult.class);
        main_activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // main_activity.putExtra("EXIT", true);
        startActivity(main_activity);

        finish();

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
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    if (bitmap != null) {
                        if (TripManagement.readValueFromPreferences(PicturePreviewActivity.this, "OffersImageview", "").equals("OffersImageview")) {
                            TripManagement.saveValueuToPreferences(PicturePreviewActivity.this, "OffersImageview", "");
                            Add_ur_offer_photo.setVisibility(View.GONE);
                            OfferImageview.setVisibility(View.VISIBLE);
                            OfferImageview.setImageBitmap(bitmap);
                            try {
                                Constant.mOffersURL = Constant.getPath(PicturePreviewActivity.this, contentURI);
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        } else if (TripManagement.readValueFromPreferences(PicturePreviewActivity.this, "ClickedOnPhoto1", "").equals("ClickedOnPhoto1")) {
                            TripManagement.saveValueuToPreferences(PicturePreviewActivity.this, "ClickedOnPhoto1", "");
                            PlusImageviewFirst.setVisibility(View.GONE);
                            PhotoImage1.setVisibility(View.VISIBLE);
                            PhotoImage1.setImageBitmap(bitmap);
                            try {
                                if (Constant.mAttachmentURLI.equals("") || Constant.mAttachmentURLI.isEmpty() || Constant.mAttachmentURLI == null) {
                                    Constant.mAttachmentURLI = Constant.getPath(PicturePreviewActivity.this, contentURI);
                                    db.insertImageUpload(Constant.mAttachmentURLI, String.valueOf(contentURI),"1");
                                } else {
                                    Constant.mAttachmentURLI = Constant.getPath(PicturePreviewActivity.this, contentURI);
                                    db.updateImageUploadNw(Constant.mAttachmentURLI, String.valueOf(contentURI),"1");
                                }
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        } else if (TripManagement.readValueFromPreferences(PicturePreviewActivity.this, "ClickedOnPhoto2", "").equals("ClickedOnPhoto2")) {
                            TripManagement.saveValueuToPreferences(PicturePreviewActivity.this, "ClickedOnPhoto2", "");
                            PlusImageviewSecond.setVisibility(View.GONE);
                            PhotoImage2.setVisibility(View.VISIBLE);
                            PhotoImage2.setImageBitmap(bitmap);
                            try {
                                if (Constant.mAttachmentURLII.equals("") || Constant.mAttachmentURLII.isEmpty() || Constant.mAttachmentURLII == null) {
                                    Constant.mAttachmentURLII = Constant.getPath(PicturePreviewActivity.this, contentURI);
                                    db.insertImageUpload(Constant.mAttachmentURLII, String.valueOf(contentURI),"2");
                                } else {
                                    Constant.mAttachmentURLII = Constant.getPath(PicturePreviewActivity.this, contentURI);
                                    db.updateImageUploadNw(Constant.mAttachmentURLII, String.valueOf(contentURI),"2");
                                }
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        } else if (TripManagement.readValueFromPreferences(PicturePreviewActivity.this, "ClickedOnPhoto3", "").equals("ClickedOnPhoto3")) {
                            TripManagement.saveValueuToPreferences(PicturePreviewActivity.this, "ClickedOnPhoto3", "");
                            PlusImageviewThird.setVisibility(View.GONE);
                            PhotoImage3.setVisibility(View.VISIBLE);
                            PhotoImage3.setImageBitmap(bitmap);
                            try {
                                if (Constant.mAttachmentURLIII.equals("") || Constant.mAttachmentURLIII.isEmpty() || Constant.mAttachmentURLIII == null) {
                                    Constant.mAttachmentURLIII = Constant.getPath(PicturePreviewActivity.this, contentURI);
                                    db.insertImageUpload(Constant.mAttachmentURLIII, String.valueOf(contentURI),"3");
                                } else {
                                    Constant.mAttachmentURLIII = Constant.getPath(PicturePreviewActivity.this, contentURI);
                                    db.updateImageUploadNw(Constant.mAttachmentURLIII, String.valueOf(contentURI),"3");
                                }
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        } else if (TripManagement.readValueFromPreferences(PicturePreviewActivity.this, "ClickedOnPhoto4", "").equals("ClickedOnPhoto4")) {
                            TripManagement.saveValueuToPreferences(PicturePreviewActivity.this, "ClickedOnPhoto4", "");
                            PlusImageviewFourth.setVisibility(View.GONE);
                            PhotoImage4.setVisibility(View.VISIBLE);
                            PhotoImage4.setImageBitmap(bitmap);
                            try {
                                if (Constant.mAttachmentURLIV.equals("") || Constant.mAttachmentURLIV.isEmpty() || Constant.mAttachmentURLIV == null) {
                                    Constant.mAttachmentURLIV = Constant.getPath(PicturePreviewActivity.this, contentURI);
                                    db.insertImageUpload(Constant.mAttachmentURLIV, String.valueOf(contentURI),"4");
                                } else {
                                    Constant.mAttachmentURLIV = Constant.getPath(PicturePreviewActivity.this, contentURI);
                                    db.updateImageUploadNw(Constant.mAttachmentURLIV, String.valueOf(contentURI),"4");
                                }
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        } else if (TripManagement.readValueFromPreferences(PicturePreviewActivity.this, "ClickedOnPhoto5", "").equals("ClickedOnPhoto5")) {
                            TripManagement.saveValueuToPreferences(PicturePreviewActivity.this, "ClickedOnPhoto5", "");
                            PlusImageviewFifth.setVisibility(View.GONE);
                            PhotoImage5.setVisibility(View.VISIBLE);
                            PhotoImage5.setImageBitmap(bitmap);
                            try {
                                if (Constant.mAttachmentURLV.equals("") || Constant.mAttachmentURLV.isEmpty() || Constant.mAttachmentURLV == null) {
                                    Constant.mAttachmentURLV = Constant.getPath(PicturePreviewActivity.this, contentURI);
                                    db.insertImageUpload(Constant.mAttachmentURLV, String.valueOf(contentURI),"5");
                                } else {
                                    Constant.mAttachmentURLV = Constant.getPath(PicturePreviewActivity.this, contentURI);
                                    db.updateImageUploadNw(Constant.mAttachmentURLV, String.valueOf(contentURI),"5");
                                }
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show();
                            camera.setVisibility(View.GONE);
                            imageView.setVisibility(View.VISIBLE);
                            RelLayoutCameraLayout.setVisibility(View.GONE);
                            RelLayoutDelete.setVisibility(View.VISIBLE);
                            imageView.setImageBitmap(bitmap);

                            TripManagement.saveValueuToPreferences(PicturePreviewActivity.this, "ForLogo", BitMapToString(bitmap));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
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
                   //getFileDetailFromUri(this, uri);
                   try {
                       Constant.mAttachmentFileURL = Constant.getPath(PicturePreviewActivity.this, uri);
                   } catch (URISyntaxException e) {
                       e.printStackTrace();
                   }
               }
           }


            //TODO: action
        }







        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            //getting the image Uri
            Uri imageUri = data.getData();
            try {
                //getting bitmap object from uri
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

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

    public  FileDetail getFileDetailFromUri(final Context context, final Uri uri)
    {

        if (uri != null)
        {
            fileDetail = new FileDetail();
            // File Scheme.
            if (ContentResolver.SCHEME_FILE.equals(uri.getScheme()))
            {
                 file = new File(uri.getPath());
                 TripManagement.saveValueuToPreferences(this,"path",file.getPath());
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
        mRequestQueue = Volley.newRequestQueue(this);
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
                Log.i(TAG,"Error :" + error.toString());
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



    private void uploadProduct(final Bitmap bitmap)
    {

        //getting the tag from the edittext
        //final String tags = editTextTags.getText().toString().trim();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);


        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, PostProductURL,
                new Response.Listener<NetworkResponse>()
                {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        progressDialog.dismiss();

                        Log.e("ProductUploadURL",PostProductURL);

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            Log.e("ResponceProductUpload", String.valueOf(jsonObject));
                            if (jsonObject.getInt(Keys.success) == 1)
                            {
                                TripManagement.saveValueuToPreferences(PicturePreviewActivity.this,"ClickedOnList","");
                                JSONObject jsonObjectData = jsonObject.getJSONObject(Keys.data);


                                JSONObject jsonObjectProduct = jsonObjectData.getJSONObject(Keys.product);
                                JSONArray jsonArray_subproducts = jsonObjectProduct.getJSONArray(Keys.sub_products);
                                for (int i=0;i>jsonArray_subproducts.length();i++)
                                {
                                    JSONObject object = jsonArray_subproducts.getJSONObject(i);


                                }

                                TripManagement.saveValueuToPreferences(PicturePreviewActivity.this,"Product_Id",jsonObjectProduct.getString(Keys.id));
                                TripManagement.saveValueuToPreferences(PicturePreviewActivity.this,"hashed_id",jsonObjectProduct.getString(Keys.hashed_id_url));

                                Constant.productDetailQRFragmentStatus = "Result";

                                ProductDetailsQRFragment productDetailsQRFragment = new ProductDetailsQRFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("Description",jsonObjectProduct.isNull(Keys.description) ? "":jsonObjectProduct.getString(Keys.description));
                                bundle.putString("Phone",jsonObjectProduct.isNull(Keys.phone) ? "" :jsonObjectProduct.getString(Keys.phone));
                                bundle.putString("Email",jsonObjectProduct.isNull(Keys.email) ? "":jsonObjectProduct.getString(Keys.email));
                                bundle.putString("Telephone",jsonObjectProduct.isNull(Keys.landline) ? "":jsonObjectProduct.getString(Keys.landline));
                                bundle.putString("Location",jsonObjectProduct.getString(Keys.location));
                                bundle.putString("Website",jsonObjectProduct.isNull(Keys.website) ? "" :jsonObjectProduct.getString(Keys.website));
                                bundle.putString("CreatedAt",jsonObjectProduct.isNull(Keys.created_at) ? "" :jsonObjectProduct.getString(Keys.created_at));
                                bundle.putString("Offers",jsonObjectProduct.isNull(Keys.offer) ? "" :jsonObjectProduct.getString(Keys.offer));
                                bundle.putString("price",jsonObjectProduct.isNull(Keys.price) ? "" :jsonObjectProduct.getString(Keys.price));
                                bundle.putString("quality",jsonObjectProduct.isNull(Keys.quality) ? "" :jsonObjectProduct.getString(Keys.quality));
                                bundle.putString("quantity",jsonObjectProduct.isNull(Keys.quantity) ? "" :jsonObjectProduct.getString(Keys.quantity));
                                bundle.putString("Photo",jsonObjectProduct.isNull(Keys.photo) ? "" :jsonObjectProduct.getString(Keys.photo));

                                db.del_ImageUpload();
                                db.del_SubCategory();

                                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.add(R.id.FramePicturePreview,productDetailsQRFragment);
                                fragmentTransaction.addToBackStack(null);
                                productDetailsQRFragment.setArguments(bundle);
                                fragmentTransaction.commit();


                              //  UploadMultipleFilesWithData();
                            }
                            else if (jsonObject.getInt(Keys.success) == 0)
                            {
                                db.del_ImageUpload();
                                db.del_SubCategory();
                               // Toast.makeText(PicturePreviewActivity.this, ""+jsonObject.getJSONObject(Keys.errors), Toast.LENGTH_SHORT).show();
                                JSONObject jsonObject_errors = jsonObject.getJSONObject(Keys.errors);
                                if (jsonObject_errors.has(Keys.name))
                                {
                                    TV_Invali.setText(jsonObject_errors.getJSONArray(Keys.name).get(0).toString());
                                    dialog_invalid_qr.show();

                                }
                                if (jsonObject_errors.has(Keys.email))
                                {
                                    TV_Invali.setText(jsonObject_errors.getJSONArray(Keys.email).get(0).toString());
                                    dialog_invalid_qr.show();
                                }
                                else if (jsonObject_errors.has(Keys.phone))
                                {
                                    TV_Invali.setText(jsonObject_errors.getJSONArray(Keys.phone).get(0).toString());
                                    dialog_invalid_qr.show();

                                }
                                else if (jsonObject_errors.has(Keys.landline))
                                {
                                    TV_Invali.setText(jsonObject_errors.getJSONArray(Keys.landline).get(0).toString());
                                    dialog_invalid_qr.show();

                                }
                                else if (jsonObject_errors.has(Keys.price))
                                {
                                    TV_Invali.setText(jsonObject_errors.getJSONArray(Keys.price).get(0).toString());
                                    dialog_invalid_qr.show();

                                }
                                else if (jsonObject_errors.has(Keys.quality))
                                {
                                    TV_Invali.setText(jsonObject_errors.getJSONArray(Keys.quality).get(0).toString());
                                    dialog_invalid_qr.show();

                                }
                                else if (jsonObject_errors.has(Keys.website))
                                {
                                    TV_Invali.setText(jsonObject_errors.getJSONArray(Keys.website).get(0).toString());
                                    dialog_invalid_qr.show();

                                }
                                else if (jsonObject_errors.has(Keys.quantity))
                                {
                                    TV_Invali.setText(jsonObject_errors.getJSONArray(Keys.quantity).get(0).toString());
                                    dialog_invalid_qr.show();

                                }
                                else
                                {
                                    TV_Invali.setText(jsonObject_errors.toString());
                                    dialog_invalid_qr.show();
                                }

                            }
                        } catch (JSONException e)
                        {
                            progressDialog.dismiss();
                            e.printStackTrace();
                            Toast.makeText(PicturePreviewActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Exeption", error.toString());
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
                params.put(Keys.plus_code,jsonObject_global_code);
                params.put(Keys.latitude, String.valueOf(latitude));
                params.put(Keys.longitude, String.valueOf(longitude));
                params.put(Keys.name,ShopnameEdittext.getText().toString());
                params.put(Keys.type,"0");
                params.put(Keys.location,lacationEdittext.getText().toString());
                params.put(Keys.isd_code,"91");
                params.put(Keys.price,priceIDEdittext.getText().toString());
                params.put(Keys.phone,mobileEdittext.getText().toString());
                params.put(Keys.std_code,"040");
                params.put(Keys.landline,landlineEdittext.getText().toString());
                params.put(Keys.email,Email_new_Edittext.getText().toString());
                params.put(Keys.website,websiteEdittext.getText().toString());
                params.put(Keys.description,descriptionEdittext.getText().toString());
                params.put(Keys.validity_start,TextViewvalidityFrom.getText().toString());
                params.put(Keys.validity_end,TextViewvalidityTo.getText().toString());
                params.put(Keys.quality,TextViewSetQuality.getText().toString());
                params.put(Keys.quantity,EditTextQuantityCount.getText().toString());
                params.put(Keys.offer,offersEdittext.getText().toString());
               // params.put(Keys.gst,TripManagement.readValueFromPreferences(PicturePreviewActivity.this,"5Percent",""));
                params.put(Keys.gst,"5");

                for (int ang = 0; ang < mSubCategoryList.size(); ang++) {
                    params.put("sub_products["+(ang)+"][name]", mSubCategoryList.get(ang).getProduct_name());
                    params.put("sub_products["+(ang)+"][price]", mSubCategoryList.get(ang).getProduct_price());
                    //params.put("sub_products["+(ang)+"][image]", mSubCategoryList.get(ang).getProduct_image());
                }

//                params.put("Offer Product Pic URL", Constant.mOffersURL);
//                params.put("Main Product Pic URL", Constant.mPicURL);
//                params.put("Attachment PDF File URL", Constant.mAttachmentFileURL);






                return params;
            }

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Keys.Authorization,"Bearer "+TripManagement.readValueFromPreferences(PicturePreviewActivity.this,"accesstoken",""));
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData()
            {
                Map<String, DataPart> params = new HashMap<>();
                long imagename  = System.currentTimeMillis();
                params.put(Keys.photo, new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));

                imageUpload();

                for (int wor = 0; wor < uriArrayList.size(); wor++)
                {
                    //Bitmap bitmapImageUpload_multiple = BitmapFactory.decodeFile(imgFile.get(wor).getAbsolutePath());
                    try {
                        String imagename_m  = "multi_" + System.currentTimeMillis();
                        Bitmap bitmaps = getThumbnail(uriArrayList.get(wor));
                        params.put("image["+wor+"]",new DataPart(imagename_m + ".png",getFileDataFromDrawable(bitmaps)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
              ////  params.put("image[0]", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
              //  params.put("image[1]", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    public Bitmap getThumbnail(Uri uri) throws FileNotFoundException, IOException{
        InputStream input = PicturePreviewActivity.this.getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither=true;//optional
        onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();

        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1)) {
            return null;
        }

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > originalSize) ? (originalSize / originalSize) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither = true; //optional
        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//
        input = PicturePreviewActivity.this.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    private static int getPowerOfTwoForSampleRatio(double ratio){
        int k = Integer.highestOneBit((int)Math.floor(ratio));
        if(k==0) return 1;
        else return k;
    }
   // Bitmap to String:
    public String BitMapToString(Bitmap bitmap)
    {
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
                TripManagement.saveValueuToPreferences(PicturePreviewActivity.this,"Bitmapsave",BitMapToString(bitmap));
                bitmapForUpload = bitmap;

                Uri tempUri = getImageUri(getApplicationContext(), bitmap);

                try {
                    Constant.mPicURL = Constant.getPath(PicturePreviewActivity.this, tempUri);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

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

    @OnClick(R.id.LinlayoutincreaseQuanitity)
    public void setLinlayoutincreaseQuanitity()
    {
        countQuantity = countQuantity + 1;
        EditTextQuantityCount.setText(Integer.toString(countQuantity));
    }


    @OnClick(R.id.LinlayoutdecreaseQuanitity)
    public void setLinlayoutdecreaseQuanitity()
    {
        countQuantity = countQuantity - 1;
        EditTextQuantityCount.setText(Integer.toString(countQuantity));
    }

    @OnClick(R.id.LinlayoutPhoto1)
    public void setLinlayoutPhoto1()
    {
        TripManagement.saveValueuToPreferences(PicturePreviewActivity.this,"ClickedOnPhoto1","ClickedOnPhoto1");
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }


    @OnClick(R.id.LinlayoutPhoto2)
    public void setLinlayoutPhoto2()
    {
        TripManagement.saveValueuToPreferences(PicturePreviewActivity.this,"ClickedOnPhoto2","ClickedOnPhoto2");
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }


    @OnClick(R.id.LinlayoutPhoto3)
    public void setLinlayoutPhoto3()
    {
        TripManagement.saveValueuToPreferences(PicturePreviewActivity.this,"ClickedOnPhoto3","ClickedOnPhoto3");
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }


    @OnClick(R.id.LinlayoutPhoto4)
    public void setLinlayoutPhoto4()
    {
        TripManagement.saveValueuToPreferences(PicturePreviewActivity.this,"ClickedOnPhoto4","ClickedOnPhoto4");
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }


    @OnClick(R.id.LinlayoutPhoto5)
    public void setLinlayoutPhoto5()
    {
        TripManagement.saveValueuToPreferences(PicturePreviewActivity.this,"ClickedOnPhoto5","ClickedOnPhoto5");
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }



    @OnClick(R.id.pdfText1)
    public void setpdfText1()
    {
        //open the pdf

       Log.d("dfdfd",TripManagement.readValueFromPreferences(this,"path",""));

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
            Toast.makeText(this, "catch"+e, Toast.LENGTH_SHORT).show();
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
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

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
        } catch (IOException ex) {
            Toast.makeText(this, "Catch", Toast.LENGTH_SHORT).show();
        }

        byte[] bytes = bos.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes , 0, bytes .length);

        uploadPDFasStringToServer = Base64.encodeToString(getBytesFromBitmap(bitmap), Base64.NO_WRAP);
        //to convert pdf to byte array
    }



    public void UploadMultipleFilesWithData()
    {
         String pathAttachment = null,pathPhoto = null;
         MultipartUploadRequest QrRequest = null;
         if (filePath != null)
         {
             pathAttachment = FilePath.getPath(this, filePath);
         }

          pathPhoto = FrileTogetCapturedPhotoPath.getPath();

        try
        {
            //Creating a multi part request
         QrRequest =  new MultipartUploadRequest(this,PostProductURL);



         if (pathAttachment != null )
         {
             QrRequest.addFileToUpload(pathAttachment, Keys.attachment); //Adding file
         }
         QrRequest.addFileToUpload(pathPhoto,Keys.photo)
                    .addParameter(Keys.name, ShopnameEdittext.getText().toString()) //Adding text parameter to the request
                    .addParameter(Keys.plus_code, jsonObject_global_code)
                    .addParameter(Keys.latitude, String.valueOf(latitude))
                    .addParameter(Keys.longitude, String.valueOf(longitude))
                    .addParameter(Keys.type, "0")
                    .addParameter(Keys.location, lacationEdittext.getText().toString())
                    .addParameter(Keys.isd_code,"91")
                    .addParameter(Keys.phone,mobileEdittext.getText().toString())
                    .addParameter(Keys.std_code,"040")
                    .addParameter(Keys.landline,landlineEdittext.getText().toString())
                    .addParameter(Keys.email,Email_new_Edittext.getText().toString())
                    .addParameter(Keys.website,websiteEdittext.getText().toString())
                    .addParameter(Keys.description,descriptionEdittext.getText().toString())
                    .addParameter(Keys.price,priceIDEdittext.getText().toString())
                    .addParameter(Keys.validity_start,"2019-01-01")
                    .addParameter(Keys.validity_end,"2019-01-31")
                    .addParameter(Keys.quality,TextViewSetQuality.getText().toString())
                    .addParameter(Keys.quantity,EditTextQuantityCount.getText().toString())
                    .addParameter(Keys.offer,offersEdittext.getText().toString())
                    .addHeader(Keys.Authorization,"Bearer "+TripManagement.readValueFromPreferences(PicturePreviewActivity.this,"accesstoken",""))
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

            Log.d("WRREQUEST",QrRequest.toString());



            ProductDetailsQRFragment productDetailsQRFragment = new ProductDetailsQRFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.FramePicturePreview,productDetailsQRFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } catch (Exception exc)
        {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    //to get captured pictures's path

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    //to get captured pictures's path





    private void updateProduct()
    {

        //getting the tag from the edittext
        //final String tags = editTextTags.getText().toString().trim();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String PostUpdatededProdut = TripManagement.BASE_URL+"product/"+TripManagement.readValueFromPreferences(PicturePreviewActivity.this,"ProductIDforUpdate","");


        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, PostUpdatededProdut,
                new Response.Listener<NetworkResponse>()
                {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        progressDialog.dismiss();

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            Log.e("ResponceUpdate", String.valueOf(jsonObject));
                            if (jsonObject.getInt(Keys.success) == 1)
                            {

                                InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(INPUT_METHOD_SERVICE);
                                if (getCurrentFocus() != null)
                                {
                                    inputMethodManager.hideSoftInputFromWindow( getCurrentFocus().getWindowToken(), 0);
                                }


                                //TripManagement.saveValueuToPreferences(PicturePreviewActivity.this,"ClickedOnList","ClickedOnList");

                                Toast.makeText(PicturePreviewActivity.this, "Update success!", Toast.LENGTH_SHORT).show();
                                JSONObject jsonObjectData = jsonObject.getJSONObject(Keys.data);
                                JSONObject jsonObjectProduct = jsonObjectData.getJSONObject(Keys.product);
                                TripManagement.saveValueuToPreferences(PicturePreviewActivity.this,"Product_Id",jsonObjectProduct.getString(Keys.id));


                                Log.e("Photo",jsonObjectProduct.getString(Keys.photo));

                                Constant.productDetailQRFragmentStatus = "Result";
                                ProductDetailsQRFragment productDetailsQRFragment = new ProductDetailsQRFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("Desrciption",jsonObjectProduct.isNull(Keys.description) ? "":jsonObjectProduct.getString(Keys.description));
                                bundle.putString("Name",jsonObjectProduct.isNull(Keys.name) ? "":jsonObjectProduct.getString(Keys.name));
                                bundle.putString("Phone",jsonObjectProduct.isNull(Keys.phone) ? "" :jsonObjectProduct.getString(Keys.phone));
                                bundle.putString("Email",jsonObjectProduct.isNull(Keys.email) ? "":jsonObjectProduct.getString(Keys.email));
                                bundle.putString("landline",jsonObjectProduct.isNull(Keys.landline) ? "":jsonObjectProduct.getString(Keys.landline));
                                bundle.putString("Location",jsonObjectProduct.getString(Keys.location));
                                bundle.putString("Website",jsonObjectProduct.isNull(Keys.website) ? "" :jsonObjectProduct.getString(Keys.website));
                                bundle.putString("CreatedAt",jsonObjectProduct.isNull(Keys.created_at) ? "" :jsonObjectProduct.getString(Keys.created_at));
                                bundle.putString("Offers",jsonObjectProduct.isNull(Keys.offer) ? "" :jsonObjectProduct.getString(Keys.offer));
                                bundle.putString("price",jsonObjectProduct.isNull(Keys.price) ? "" :jsonObjectProduct.getString(Keys.price));
                                bundle.putString("quality",jsonObjectProduct.isNull(Keys.quality) ? "" :jsonObjectProduct.getString(Keys.quality));
                                bundle.putString("quantity",jsonObjectProduct.isNull(Keys.quantity) ? "" :jsonObjectProduct.getString(Keys.quantity));
                                bundle.putString("Photo",jsonObjectProduct.getString(Keys.photo));
                                bundle.putString("stdcode",jsonObjectProduct.isNull(Keys.std_code) ? "" :jsonObjectProduct.getString(Keys.std_code));
                                bundle.putString("ValidityStart",jsonObjectProduct.isNull(Keys.validity_start) ? "" :jsonObjectProduct.getString(Keys.validity_start));
                                bundle.putString("ValidityEnd",jsonObjectProduct.isNull(Keys.validity_end) ? "" :jsonObjectProduct.getString(Keys.validity_end));
                                bundle.putString("createdAt",jsonObjectProduct.isNull(Keys.created_at) ? "" :jsonObjectProduct.getString(Keys.created_at));
                                bundle.putString("quality",jsonObjectProduct.isNull(Keys.quality) ? "" :jsonObjectProduct.getString(Keys.quality));
                                bundle.putString("quantity",jsonObjectProduct.isNull(Keys.quantity) ? "" :jsonObjectProduct.getString(Keys.quantity));

                                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.add(R.id.FramePicturePreview,productDetailsQRFragment);
                                fragmentTransaction.addToBackStack(null);
                                productDetailsQRFragment.setArguments(bundle);
                                fragmentTransaction.commit();
                            }
                            else if (jsonObject.getInt(Keys.success) == 0)
                            {
                                JSONObject jsonObject_errors = jsonObject.getJSONObject(Keys.errors);
                                if (jsonObject_errors.has(Keys.name))
                                {
                                    TV_Invali.setText(jsonObject_errors.getJSONArray(Keys.name).get(0).toString());
                                    dialog_invalid_qr.show();
                                }
                                if (jsonObject_errors.has(Keys.email))
                                {
                                    TV_Invali.setText(jsonObject_errors.getJSONArray(Keys.email).get(0).toString());
                                    dialog_invalid_qr.show();
                                }
                                if (jsonObject_errors.has(Keys.phone))
                                {
                                    TV_Invali.setText(jsonObject_errors.getJSONArray(Keys.phone).get(0).toString());
                                    dialog_invalid_qr.show();
                                }

                                if (jsonObject_errors.has(Keys.landline))
                                {
                                    TV_Invali.setText(jsonObject_errors.getJSONArray(Keys.landline).get(0).toString());
                                    dialog_invalid_qr.show();
                                }
                                else if (jsonObject_errors.has(Keys.price))
                                {
                                    TV_Invali.setText(jsonObject_errors.getJSONArray(Keys.price).get(0).toString());
                                    dialog_invalid_qr.show();


                                }
                                else if (jsonObject_errors.has(Keys.website))
                                {
                                    TV_Invali.setText(jsonObject_errors.getJSONArray(Keys.website).get(0).toString());
                                    dialog_invalid_qr.show();


                                }
                                else if (jsonObject_errors.has(Keys.quality))
                                {
                                    TV_Invali.setText(jsonObject_errors.getJSONArray(Keys.quality).get(0).toString());
                                    dialog_invalid_qr.show();

                                }
                                else if (jsonObject_errors.has(Keys.quantity))
                                {
                                    TV_Invali.setText(jsonObject_errors.getJSONArray(Keys.quantity).get(0).toString());
                                    dialog_invalid_qr.show();

                                }

                            }
                        } catch (JSONException e)
                        {
                            progressDialog.dismiss();
                            e.printStackTrace();
                            Toast.makeText(PicturePreviewActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
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
                params.put(Keys.plus_code,jsonObject_global_code);
                params.put(Keys.latitude, String.valueOf(latitude));
                params.put(Keys.longitude, String.valueOf(longitude));
                params.put(Keys.name,ShopnameEdittext.getText().toString());
                params.put(Keys.type,"0");
                params.put(Keys.location,lacationEdittext.getText().toString());
                params.put(Keys.isd_code,"91");
                params.put(Keys.price,priceIDEdittext.getText().toString());
                params.put(Keys.phone,mobileEdittext.getText().toString());
                params.put(Keys.std_code,"040");
                params.put(Keys.landline,landlineEdittext.getText().toString());
                params.put(Keys.email,Email_new_Edittext.getText().toString());
                params.put(Keys.website,websiteEdittext.getText().toString());
                params.put(Keys.description,descriptionEdittext.getText().toString());
                params.put(Keys.validity_start,TextViewvalidityFrom.getText().toString());
                params.put(Keys.validity_end,TextViewvalidityTo.getText().toString());
                params.put(Keys.quality,TextViewSetQuality.getText().toString());
                params.put(Keys.quantity,EditTextQuantityCount.getText().toString());
                params.put(Keys.offer,offersEdittext.getText().toString());



                return params;
            }

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Keys.Authorization,"Bearer "+TripManagement.readValueFromPreferences(PicturePreviewActivity.this,"accesstoken",""));
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData()
            {
                Map<String, DataPart> params = new HashMap<>();
                long imagename  = System.currentTimeMillis();
                uploadPDFasStringToServer = Base64.encodeToString(getBytesFromBitmap(bitmapForUpload), Base64.NO_WRAP);

                params.put(Keys.photo, new DataPart(imagename + ".png", getFileDataFromDrawable(bitmapForUpload)));
                ////  params.put("image[0]", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                //  params.put("image[1]", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }


    @OnClick(R.id.UpdateDetailsButton)
    public void setUpdateDetailsButton()
    {
        TripManagement.saveValueuToPreferences(PicturePreviewActivity.this,"BackafterUpdate","BackafterUpdate");
        updateProduct();
    }

    @OnClick(R.id.Add_ur_offer_photo)
    public void setAdd_ur_offer_photo()
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
        TripManagement.saveValueuToPreferences(PicturePreviewActivity.this,"OffersImageview","OffersImageview");
    }

    @Override
    public void getLocationUpdate(Location location) {
        try {
            Log.d(TAG, "getLatitude : " + location.getLatitude());
            Log.d(TAG, "getLatitude : " + location.getLongitude());
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
               // Log.e(TAG, "getaddresses3 : " + address_save);

                lacationEdittext.setText(address_save);
            }



        } catch (IOException e) {
            Log.d(TAG, "getLocationUpdate exception : " + e);
        }
    }

    @Override
    public void onError(ConnectionResult connectionResult, Status status, String error) {
        if (connectionResult != null) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(PicturePreviewActivity.this,
                            LocationHelper.CONNECTION_FAILURE_RESOLUTION_REQUEST);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        } else if (status != null) {
            // Location is not available, but we can ask permission from users
            try {

                status.startResolutionForResult(PicturePreviewActivity.this, LocationHelper.REQUEST_CHECK_SETTINGS);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.RelLayoutUpdatephoto)
    public void setRelLayoutUpdatephoto()
    {
        RelLayoutUpdatephoto.setVisibility(View.GONE);
        RelLayoutCameraLayout.setVisibility(View.VISIBLE);

        imageView.setVisibility(View.GONE);
        camera.setVisibility(View.VISIBLE);
        RelLayoutDelete.setVisibility(View.GONE);
    }
}
