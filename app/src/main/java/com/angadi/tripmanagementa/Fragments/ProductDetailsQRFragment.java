package com.angadi.tripmanagementa.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.angadi.tripmanagementa.Activities.Constant;
import com.angadi.tripmanagementa.Activities.HomeActivity;
import com.angadi.tripmanagementa.Activities.PicturePreviewActivity;
import com.angadi.tripmanagementa.Activities.Qrcoderesult;
import com.angadi.tripmanagementa.Activities.SubCategory.Helper.SubCategoryViewAdapter;
import com.angadi.tripmanagementa.Activities.SubCategory.Helper.TextViewSFProDisplaySemibold;
import com.angadi.tripmanagementa.Activities.SubCategory.SubCatViewAll;
import com.angadi.tripmanagementa.Activities.database.DatabaseHelper;
import com.angadi.tripmanagementa.Activities.database.model.Products;
import com.angadi.tripmanagementa.Activities.database.model.SubProduct;
import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.Extras.Keys;
import com.angadi.tripmanagementa.Extras.VolleyMultipartRequest;
import com.angadi.tripmanagementa.R;
import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.maps.model.LatLng;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.otaliastudios.cameraview.CameraUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

import static android.content.ContentValues.TAG;
import static com.angadi.tripmanagementa.Activities.Constant.mSubProductList;
import static com.angadi.tripmanagementa.Activities.HomeActivity.BLACK;
import static com.angadi.tripmanagementa.Activities.HomeActivity.HEIGHT;
import static com.angadi.tripmanagementa.Activities.HomeActivity.WHITE;
import static com.angadi.tripmanagementa.Activities.HomeActivity.WIDTH;

public class ProductDetailsQRFragment extends Fragment
{
    ImageView myImage;
    public static DatabaseHelper db;
    Bitmap bitmap;
    private static WeakReference<byte[]> image;
    String Product_id, photoFromList,LocationFromList,ProductNameFromList,PhoneFromList,landlineFromList ,
            DesrciptionFromList ,EmailFromList,WebsiteFromList,PriceFromlist,ValidityStartFromList,
            ValidityEndFromlist,qualityFromList,quantityFromList,latitideFromList,longitudeFromList;

    String Phone;

    ProgressDialog progressDialog;

    LinearLayout horizontalscroll;
    TextViewSFProDisplaySemibold viewAll;
    RecyclerView horizontalRecyclerView;
    SubCategoryViewAdapter adapter;

    @BindView(R.id.LinLayoutBack) LinearLayout LinLayoutBack;
    @BindView(R.id.ShareToChat) LinearLayout ShareToChat;
    @BindView(R.id.image) ImageView myImageCapturedPhoto;
    @BindView(R.id.RelalyoutImageViewCapturedPicture) RelativeLayout RelalyoutImageViewCapturedPicture;
    @BindView(R.id.RellayoutQR) RelativeLayout RellayoutQR;
    @BindView(R.id.tv_productname) TextView tv_productname;
    @BindView(R.id.ProductNameTV) TextView ProductNameTV;
    @BindView(R.id.TextViewdescription) TextView TextViewdescription;
    @BindView(R.id.TextViewWebsite) TextView TextViewWebsite;
    @BindView(R.id.TextviewPhone) TextView TextviewPhone;
    @BindView(R.id.LinLayoutHelp) LinearLayout LinLayoutHelp;
    @BindView(R.id.mobileLinlayout)LinearLayout mobileLinlayout;
    @BindView(R.id.LinLayoutTelephone) LinearLayout LinLayoutTelephone;
    @BindView(R.id.LinLayoutEmail) LinearLayout LinLayoutEmail;
    @BindView(R.id.LinLayoutsaveToGallery) LinearLayout LinLayoutsaveToGallery;
    @BindView(R.id.TextviewDescription) TextView TextviewDescription;
    @BindView(R.id.LinlayoutDescripion) LinearLayout LinlayoutDescripion;
    @BindView(R.id.TextviewPhoneNumber) TextView TextviewPhoneNumber;
    @BindView(R.id.TextviewEmail) TextView TextviewEmail;
    @BindView(R.id.TextviewTelephone) TextView TextviewTelephone;
    @BindView(R.id.TextViewLocation) TextView TextViewLocation;
    @BindView(R.id.LinlayoutWebsite) LinearLayout LinlayoutWebsite;
    @BindView(R.id.TextviewWebsite) TextView TextviewWebsite;
    @BindView(R.id.createdLinlayout) LinearLayout createdLinlayout;
    @BindView(R.id.createdatNumber) TextView createdatNumber;
    @BindView(R.id.TextviewOffers) TextView TextviewOffers;
    @BindView(R.id.OffersLinlayout) LinearLayout OffersLinlayout;
    @BindView(R.id.TextviewQuantity) TextView TextviewQuantity;
    @BindView(R.id.TextviewPrice) TextView TextviewPrice;
    @BindView(R.id.TextviewQuality) TextView TextviewQuality;
    @BindView(R.id.QualityLinlayout) LinearLayout QualityLinlayout;
    @BindView(R.id.dispalyimage) ImageView dispalyimage;
    @BindView(R.id.ValidutyLinlayout) LinearLayout ValidutyLinlayout;
    @BindView(R.id.TextviewValidity) TextView TextviewValidity;
    @BindView(R.id.LinlayoutUpdate) LinearLayout LinlayoutUpdate;
    @BindView(R.id.UpdateDetails) TextView UpdateDetails;
    @BindView(R.id.InfoIndicationTextview) TextView InfoIndicationTextview;
    @BindView(R.id.LocationIndicationTextview) TextView LocationIndicationTextview;
    @BindView(R.id.ShareTextview) TextView ShareTextview;
    @BindView(R.id.SaveTextview) TextView SaveTextview;
    @BindView(R.id.payTextview) TextView payTextview;
    @BindView(R.id.getDiractionTextview) TextView getDiractionTextview;
    @BindView(R.id.AddToFavTextview) TextView AddToFavTextview;
    @BindView(R.id.ShareToChatFavTextview) TextView ShareToChatFavTextview;
    @BindView(R.id.TvHelpIndText) TextView TvHelpIndText;
    @BindView(R.id.extviewDescription) TextView extviewDescription;
    @BindView(R.id.MobileIndicationtextview) TextView MobileIndicationtextview;
    @BindView(R.id.TelephoneIndicationTextview) TextView TelephoneIndicationTextview;
    @BindView(R.id.EmailIndicationTextview) TextView EmailIndicationTextview;
    @BindView(R.id.TextviewWebsiteIndicationtextview) TextView TextviewWebsiteIndicationtextview;
    @BindView(R.id.LocationindicationTextview) TextView LocationindicationTextview;
    @BindView(R.id.CretaedAtTv) TextView CretaedAtTv;
    @BindView(R.id.TextviewOffersIndicationTV) TextView TextviewOffersIndicationTV;
    @BindView(R.id.TextviewValidityIndicationTV) TextView TextviewValidityIndicationTV;
    @BindView(R.id.TextviewPriceInicationTv) TextView TextviewPriceInicationTv;
    @BindView(R.id.TextviewQuantityIndicationTV) TextView TextviewQuantityIndicationTV;
    @BindView(R.id.TextviewQualityIndicationTV) TextView TextviewQualityIndicationTV;
    @BindView(R.id.LinlayoutDelete) LinearLayout LinlayoutDelete;

    @BindView(R.id.blurredImage) ImageView blurredImage;
    @BindView(R.id.LinLayoutShare) LinearLayout LinLayoutShare;
    @BindView(R.id.LinlayoutLocation) LinearLayout LinlayoutLocation;

    int radiusArr[];




    public static void setImage(@Nullable byte[] im)
    {
        image = im != null ? new WeakReference<>(im) : null;
    }


    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.layout_product_qr_details, container,false);

        ButterKnife.bind(this,view);
        Fabric.with(getActivity(), new Crashlytics());

        myImage = (ImageView)view. findViewById(R.id.myImage);
        db = new DatabaseHelper(getActivity());

        horizontalRecyclerView = (RecyclerView)view.findViewById(R.id.horizontalRecyclerView);
        viewAll = (TextViewSFProDisplaySemibold)view.findViewById(R.id.viewAll);
        horizontalscroll = (LinearLayout) view.findViewById(R.id.horizontalscroll);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.black));
        }

        Typeface montserrat_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Montserrat-Regular.ttf");
        Typeface montserrat_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MONTSERRAT-BOLD.OTF");


        tv_productname.setTypeface(montserrat_bold);
        UpdateDetails.setTypeface(montserrat_regular);
        InfoIndicationTextview.setTypeface(montserrat_regular);
        LocationIndicationTextview.setTypeface(montserrat_regular);
        ShareTextview.setTypeface(montserrat_regular);
        SaveTextview.setTypeface(montserrat_regular);
        payTextview.setTypeface(montserrat_regular);
        getDiractionTextview.setTypeface(montserrat_regular);
        AddToFavTextview.setTypeface(montserrat_regular);
        ShareToChatFavTextview.setTypeface(montserrat_regular);
        TextviewDescription.setTypeface(montserrat_regular);
        TextViewWebsite.setTypeface(montserrat_regular);
        TextviewPhone.setTypeface(montserrat_regular);
        TextViewdescription.setTypeface(montserrat_regular);
        TextviewPhoneNumber.setTypeface(montserrat_regular);
        ProductNameTV.setTypeface(montserrat_regular);
        TvHelpIndText.setTypeface(montserrat_regular);
        extviewDescription.setTypeface(montserrat_regular);
        MobileIndicationtextview.setTypeface(montserrat_regular);
        TelephoneIndicationTextview.setTypeface(montserrat_regular);
        EmailIndicationTextview.setTypeface(montserrat_regular);
        TextviewWebsiteIndicationtextview.setTypeface(montserrat_regular);
        LocationindicationTextview.setTypeface(montserrat_regular);
        CretaedAtTv.setTypeface(montserrat_regular);
        TextviewOffersIndicationTV.setTypeface(montserrat_regular);
        TextviewValidityIndicationTV.setTypeface(montserrat_regular);
        TextviewPriceInicationTv.setTypeface(montserrat_regular);
        TextviewQuantityIndicationTV.setTypeface(montserrat_regular);
        TextviewQualityIndicationTV.setTypeface(montserrat_regular);
        TextviewQuality.setTypeface(montserrat_regular);
        TextviewQuantity.setTypeface(montserrat_regular);
        TextviewPrice.setTypeface(montserrat_regular);
        TextviewValidity.setTypeface(montserrat_regular);
        TextviewOffers.setTypeface(montserrat_regular);
        createdatNumber.setTypeface(montserrat_regular);
        TextViewLocation.setTypeface(montserrat_regular);
        TextviewEmail.setTypeface(montserrat_regular);
        TextviewTelephone.setTypeface(montserrat_regular);
        TextviewWebsite.setTypeface(montserrat_regular);



        TripManagement.saveValueuToPreferences(getActivity(),"ClickedOnShare","");
        if (TripManagement.readValueFromPreferences(getActivity(),"ClickedOnGeneateImageButton","").equals("ClickedOnGeneateImageButton"))
        {
            RellayoutQR.setVisibility(View.GONE);
            RelalyoutImageViewCapturedPicture.setVisibility(View.VISIBLE);
        }
        else
        {
            RellayoutQR.setVisibility(View.VISIBLE);
            RelalyoutImageViewCapturedPicture.setVisibility(View.GONE);
        }

        try
        {
            bitmap = encodeAsBitmap(TripManagement.readValueFromPreferences(getActivity(),"hashed_id",""));
            myImage.setImageBitmap(bitmap);
            } catch (WriterException e)
        {
            e.printStackTrace();
        }

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        final long delay = getActivity().getIntent().getLongExtra("delay", 0);
        final int nativeWidth = getActivity().getIntent().getIntExtra("nativeWidth", 0);
        final int nativeHeight = getActivity().getIntent().getIntExtra("nativeHeight", 0);
        byte[] b = image == null ? null : image.get();
        if (b == null)
        {

        }

        if (b !=null)
        {
            CameraUtils.decodeBitmap(b, 1000, 1000, new CameraUtils.BitmapCallback() {
                @Override
                public void onBitmapReady(Bitmap bitmap)
                {
                    myImageCapturedPhoto.setImageBitmap(bitmap);

                }
            });
        }

        List<SubProduct> mSubProduct = db.getSubProduct_SB_PACN(Integer.parseInt(Constant.getDashboardProductPosition));
        mSubProductList.clear();
        for (SubProduct mSubProducts : mSubProduct) {
            SubProduct msbSubProducts = new SubProduct();
            try {
                msbSubProducts.setId(mSubProducts.getId());
                msbSubProducts.setProduct_array_count(mSubProducts.getProduct_array_count());
                msbSubProducts.setProduct_array_count_now(mSubProducts.getProduct_array_count_now());
                msbSubProducts.setSub_product_array_count(mSubProducts.getSub_product_array_count());
                msbSubProducts.setSub_product_array_count_now(mSubProducts.getSub_product_array_count_now());
                msbSubProducts.setSp_id(mSubProducts.getSp_id());
                msbSubProducts.setName(mSubProducts.getName());
                msbSubProducts.setPrice(mSubProducts.getPrice());
                msbSubProducts.setPhoto(mSubProducts.getPhoto());
                msbSubProducts.setTime_date(mSubProducts.getTime_date());

                Log.d("checkidd : "," Sub Products : "+msbSubProducts.getId());
                Log.d("checkidd : "," Sub Products : "+msbSubProducts.getProduct_array_count());
                Log.d("checkidd : "," Sub Products : "+msbSubProducts.getProduct_array_count_now());
                Log.d("checkidd : "," Sub Products : "+msbSubProducts.getSub_product_array_count());
                Log.d("checkidd : "," Sub Products : "+msbSubProducts.getSub_product_array_count_now());

                mSubProductList.add(msbSubProducts);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("checkidd ","Exception : "+e.getMessage());
            }
        }

        List<Products> mProduct = db.getProducts_SB_PACN(Integer.parseInt(Constant.getDashboardProductPosition));
        Constant.mProductsList.clear();
        for (Products mProducts : mProduct) {
            Products msbProducts = new Products();
            try {
                msbProducts.setId(mProducts.getId());
                msbProducts.setProduct_array_count(mProducts.getProduct_array_count());
                msbProducts.setProduct_array_count_now(mProducts.getProduct_array_count_now());
                msbProducts.setP_id(mProducts.getP_id());
                msbProducts.setUserId(mProducts.getUserId());
                msbProducts.setType(mProducts.getType());
                msbProducts.setName(mProducts.getName());
                msbProducts.setPlusCode(mProducts.getPlusCode());
                msbProducts.setLatitude(mProducts.getLatitude());
                msbProducts.setLongitude(mProducts.getLongitude());
                msbProducts.setLocation(mProducts.getLocation());
                msbProducts.setPhoto(mProducts.getPhoto());
                msbProducts.setIsdCode(mProducts.getIsdCode());
                msbProducts.setPhone(mProducts.getPhone());
                msbProducts.setStdCode(mProducts.getStdCode());
                msbProducts.setLandline(mProducts.getLandline());
                msbProducts.setEmail(mProducts.getEmail());
                msbProducts.setWebsite(mProducts.getWebsite());
                msbProducts.setDescription(mProducts.getDescription());
                msbProducts.setPrice(mProducts.getPrice());
                msbProducts.setValidityStart(mProducts.getValidityStart());
                msbProducts.setValidityEnd(mProducts.getValidityEnd());
                msbProducts.setQuality(mProducts.getQuality());
                msbProducts.setQuantity(mProducts.getQuantity());
                msbProducts.setOffer(mProducts.getOffer());
                msbProducts.setAttachment(mProducts.getAttachment());
                msbProducts.setImage(mProducts.getImage());
                msbProducts.setCreatedAt(mProducts.getCreatedAt());
                msbProducts.setGst(mProducts.getGst());
                msbProducts.setHashedId(mProducts.getHashedId());
                msbProducts.setHashedIdUrl(mProducts.getHashedIdUrl());
                msbProducts.setFavourite(mProducts.getFavourite());

                Log.d("checkidd : "," Products : "+mProducts.getId());
                Log.d("checkidd : "," Products : "+mProducts.getProduct_array_count());
                Log.d("checkidd : "," Products : "+mProducts.getProduct_array_count_now());
                Log.d("checkidd : "," Products : "+mProducts.getP_id());
                Log.d("checkidd : "," Products : "+mProducts.getUserId());

                Constant.mProductsList.add(msbProducts);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("checkidd ","Exception : "+e.getMessage());
            }
        }

        for (int ang = 0; ang < Constant.mProductsList.size(); ang++) {
            Constant.pi_ID = Constant.mProductsList.get(ang).getId();
            Constant.pi_ProductArrayCount = Constant.mProductsList.get(ang).getProduct_array_count();
            Constant.pi_ProductArrayCountNow = Constant.mProductsList.get(ang).getProduct_array_count_now();
            Constant.pi_ProductID = Constant.mProductsList.get(ang).getP_id();
            Constant.pi_UserID = Constant.mProductsList.get(ang).getUserId();
            Constant.pi_Type = Constant.mProductsList.get(ang).getType();
            Constant.pi_Name = Constant.mProductsList.get(ang).getName();
            Constant.pi_PlusCode = Constant.mProductsList.get(ang).getPlusCode();
            Constant.pi_Latitude = Constant.mProductsList.get(ang).getLatitude();
            Constant.pi_Longitude = Constant.mProductsList.get(ang).getLongitude();
            Constant.pi_Location = Constant.mProductsList.get(ang).getLocation();
            Constant.pi_Photo = Constant.mProductsList.get(ang).getPhoto();
            Constant.pi_ISD_Code = Constant.mProductsList.get(ang).getIsdCode();
            Constant.pi_Phone = Constant.mProductsList.get(ang).getPhone();
            Constant.pi_STD_Code = Constant.mProductsList.get(ang).getStdCode();
            Constant.pi_Landline = Constant.mProductsList.get(ang).getLandline();
            Constant.pi_Email = Constant.mProductsList.get(ang).getEmail();
            Constant.pi_Website = Constant.mProductsList.get(ang).getWebsite();
            Constant.pi_Description = Constant.mProductsList.get(ang).getDescription();
            Constant.pi_Price = Constant.mProductsList.get(ang).getPrice();
            Constant.pi_Validation_Start = Constant.mProductsList.get(ang).getValidityStart();
            Constant.pi_Validation_End = Constant.mProductsList.get(ang).getValidityEnd();
            Constant.pi_Quality = Constant.mProductsList.get(ang).getQuality();
            Constant.pi_Quantity = Constant.mProductsList.get(ang).getQuantity();
            Constant.pi_Offer = Constant.mProductsList.get(ang).getOffer();
            Constant.pi_Attachment = Constant.mProductsList.get(ang).getAttachment();
            Constant.pi_Image = Constant.mProductsList.get(ang).getImage();
            Constant.pi_Created_At = Constant.mProductsList.get(ang).getCreatedAt();
            Constant.pi_GST = Constant.mProductsList.get(ang).getGst();
            Constant.pi_HashedID = Constant.mProductsList.get(ang).getHashedId();
            Constant.pi_HashedID_URL = Constant.mProductsList.get(ang).getHashedIdUrl();
            Constant.pi_Favorite = Constant.mProductsList.get(ang).getFavourite();
            Constant.pi_DateTime = Constant.mProductsList.get(ang).getTime_date();
        }

        if (Constant.productDetailQRFragmentStatus.equals("") || Constant.productDetailQRFragmentStatus.isEmpty()) {
            Log.d("prodDetailsQrFrag : ", " Status Empty !");
        } else if (Constant.productDetailQRFragmentStatus.equals("Edit")) {
            LinlayoutDescripion.setVisibility(View.GONE);
            LinlayoutDelete.setVisibility(View.VISIBLE);

            try {
                bitmap = encodeAsBitmap(Constant.pi_HashedID);
                myImage.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }

            if (Constant.pi_Quantity != 0) {
                TextviewQuantity.setText(Constant.pi_Quantity);
            }

            if (!Objects.equals(Constant.pi_Quality, "")) {
                TextviewQuality.setText(Constant.pi_Quality);
            }

            if (!Objects.equals(Constant.pi_Created_At, "")) {
                createdLinlayout.setVisibility(View.VISIBLE);
                createdatNumber.setText(Constant.pi_Created_At);
            }

            if (!Objects.equals(Constant.pi_Email, "")) {
                LinLayoutEmail.setVisibility(View.VISIBLE);
                TextviewEmail.setText(Constant.pi_Email);
            }

            if (!Objects.equals(Constant.pi_Landline, "") && !Objects.equals(Constant.pi_STD_Code, "")) {
                LinLayoutTelephone.setVisibility(View.VISIBLE);
                TextviewTelephone.setText(Constant.pi_STD_Code + "-" + Constant.pi_Landline);
            } else if (!Objects.equals(Constant.pi_Landline, "") || !Objects.equals(Constant.pi_STD_Code, "")) {
                LinLayoutTelephone.setVisibility(View.VISIBLE);
                TextviewTelephone.setText(Constant.pi_STD_Code + "" + Constant.pi_Landline);
            }

            if (!Objects.equals(Constant.pi_Validation_Start, "") && !Objects.equals(Constant.pi_Validation_End, "")) {
                ValidutyLinlayout.setVisibility(View.VISIBLE);
                TextviewValidity.setText("From " + Constant.pi_Validation_Start + " To " + Constant.pi_Validation_End);
            }

//            if (!Objects.equals(Constant.pi_Photo, "")) {
//                dispalyimage.setVisibility(View.VISIBLE);
//                Picasso.with(getActivity()).load(Constant.pi_Photo).into(dispalyimage);
//            }

            if (!Objects.equals(Constant.pi_Location, "")) {
                TextViewLocation.setText(Constant.pi_Location);
            }

            LinlayoutLocation.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    String uri = String.format(Locale.ENGLISH, "geo:", Constant.pi_Latitude, Constant.pi_Longitude);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    getActivity().startActivity(intent);
                }
            });

            if (!Objects.equals(Constant.pi_Photo, ""))
            {
                dispalyimage.setVisibility(View.GONE);
                Picasso.with(getActivity()).load(Constant.pi_Photo).into(blurredImage);
            }

            LinLayoutEmail.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+Constant.pi_Email)));
                }
            });

            if (!Objects.equals(Constant.pi_Phone, "")) {
                mobileLinlayout.setVisibility(View.VISIBLE);
                TextviewPhoneNumber.setText(Constant.pi_Phone);
            }

            if (!Objects.equals(Constant.pi_Description, "")) {
                TextviewDescription.setText(Constant.pi_Description);
                LinlayoutDescripion.setVisibility(View.VISIBLE);
            }

            if (!Objects.equals(Constant.pi_Website, "")) {
                LinlayoutWebsite.setVisibility(View.VISIBLE);
                TextviewWebsite.setText(Constant.pi_Website);
            }

            if (!Objects.equals(Constant.pi_Name, "")) {
                tv_productname.setText(Constant.pi_Name);
                ProductNameTV.setText(Constant.pi_Name);
            }

            viewAll.setText("View All ("+mSubProductList.size()+")");

            horizontalRecyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            horizontalRecyclerView.setLayoutManager(layoutManager);

            adapter = new SubCategoryViewAdapter(mSubProductList, getActivity());
            horizontalRecyclerView.setAdapter(adapter);

            viewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), SubCatViewAll.class));
                }
            });


        } else if (Constant.productDetailQRFragmentStatus.equals("Result")) {
            Toast.makeText(getActivity(), "FromPicture", Toast.LENGTH_SHORT).show();

            LinlayoutUpdate.setVisibility(View.GONE);
            LinlayoutDelete.setVisibility(View.GONE);

//            if (!Objects.equals(Constant.pi_Photo, "")) {
//                dispalyimage.setVisibility(View.VISIBLE);
//                Picasso.with(getActivity()).load(Constant.pi_Photo).into(dispalyimage);
//            }

            LinlayoutLocation.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    String uri = String.format(Locale.ENGLISH, "geo:", Constant.pi_Latitude, Constant.pi_Longitude);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    getActivity().startActivity(intent);
                }
            });

            if (!Objects.equals(Constant.pi_Photo, ""))
            {
                dispalyimage.setVisibility(View.GONE);
                Picasso.with(getActivity()).load(Constant.pi_Photo).into(blurredImage);
            }

            LinLayoutEmail.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+Constant.pi_Email)));
                }
            });

            if (!Objects.equals(Constant.pi_Offer, "")) {
                OffersLinlayout.setVisibility(View.VISIBLE);
                TextviewOffers.setText(Constant.pi_Offer);
            }

            if (!Objects.equals(Constant.pi_Created_At, "")) {
                createdatNumber.setText(Constant.pi_Created_At);
            }

            if (Objects.equals(Constant.pi_Website, "")) {

            } else {
                LinlayoutWebsite.setVisibility(View.VISIBLE);
                TextviewWebsite.setText(Constant.pi_Website);
            }

            if (!Objects.equals(Constant.pi_Location, "")) {
                TextViewLocation.setText(Constant.pi_Location);
            }

            if (Objects.equals(Constant.pi_Landline, "")) {

            } else {
                LinLayoutTelephone.setVisibility(View.VISIBLE);
                TextviewTelephone.setText(Constant.pi_Landline);
            }

            if (!Objects.equals(Constant.pi_Email, "")) {
                LinLayoutEmail.setVisibility(View.VISIBLE);
                TextviewEmail.setText(Constant.pi_Email);
            }

            if (!Objects.equals(Constant.pi_Phone, "")) {
                mobileLinlayout.setVisibility(View.VISIBLE);
                TextviewPhoneNumber.setText(Constant.pi_Phone);
            }

            if (!Objects.equals(Constant.pi_Description, "")) {
                TextviewDescription.setText(Constant.pi_Description);
                LinlayoutDescripion.setVisibility(View.VISIBLE);
            }

            tv_productname.setText(Constant.pi_Name);
            ProductNameTV.setText(Constant.pi_Name);

            viewAll.setText("View All ("+mSubProductList.size()+")");

            horizontalRecyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            horizontalRecyclerView.setLayoutManager(layoutManager);

            adapter = new SubCategoryViewAdapter(mSubProductList, getActivity());
            horizontalRecyclerView.setAdapter(adapter);

            viewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), SubCatViewAll.class));
                }
            });

        } else {
            Log.d("prodDetailsQrFrag : ", " Status Error !");
        }


//        if(TripManagement.readValueFromPreferences(getActivity(),"ClickedOnList","").equals("ClickedOnList"))
//      {
//          LinlayoutDescripion.setVisibility(View.GONE);
//          LinlayoutDelete.setVisibility(View.VISIBLE);
//
//           TripManagement.saveValueuToPreferences(getActivity(),"ProductName123k","");
//           ProductNameFromList = getArguments().getString("Name");
//           DesrciptionFromList = getArguments().getString("Desrciption");
//           EmailFromList = getArguments().getString("Email");
//           WebsiteFromList = getArguments().getString("Website");
//           PriceFromlist = getArguments().getString("Price");
//           PhoneFromList = getArguments().getString("Phone");
//           LocationFromList = getArguments().getString("Location");
//           photoFromList = getArguments().getString("Photo");
//           ValidityStartFromList = getArguments().getString("ValidityStart");
//           ValidityEndFromlist = getArguments().getString("ValidityEnd");
//           qualityFromList = getArguments().getString("quality");
//           quantityFromList = getArguments().getString("quantity");
//           latitideFromList = getArguments().getString("latitude");
//           longitudeFromList = getArguments().getString("longitude");
//
//          String stdcode = getArguments().getString("stdcode");
//
//          String createdAt = getArguments().getString("createdAt");
//           landlineFromList = getArguments().getString("landline");
//           Product_id = getArguments().getString("id");
//
//           try
//          {
//              bitmap = encodeAsBitmap(TripManagement.readValueFromPreferences(getActivity(),"hashed_id",""));
//              myImage.setImageBitmap(bitmap);
//          } catch (WriterException e)
//          {
//              e.printStackTrace();
//          }
//
//         if (quantityFromList!="")
//         {
//             TextviewQuantity.setText(quantityFromList);
//         }
//
//
//          if (qualityFromList!="")
//          {
//              TextviewQuality.setText(qualityFromList);
//          }
//
//          if (createdAt!="")
//          {
//              createdLinlayout.setVisibility(View.VISIBLE);
//              createdatNumber.setText(createdAt);
//          }
//
//          if (EmailFromList!="")
//          {
//              LinLayoutEmail.setVisibility(View.VISIBLE);
//              TextviewEmail.setText(EmailFromList);
//          }
//

//
//
//          if (landlineFromList!="" && stdcode !="")
//          {
//              LinLayoutTelephone.setVisibility(View.VISIBLE);
//              TextviewTelephone.setText(stdcode+"-"+landlineFromList);
//          }
//          else  if (landlineFromList!="" || stdcode !="")
//          {
//              LinLayoutTelephone.setVisibility(View.VISIBLE);
//              TextviewTelephone.setText(stdcode+"" +landlineFromList);
//          }
//

//
//
//
//
//          if (ValidityStartFromList !="" && ValidityEndFromlist != "")
//          {
//              ValidutyLinlayout.setVisibility(View.VISIBLE);
//              TextviewValidity.setText("From "+ValidityStartFromList+" To "+ValidityEndFromlist);
//          }
//
//
//

//
//
//          if (LocationFromList != null)
//          {
//              TextViewLocation.setText(LocationFromList);
//          }
//
//
//
//
//
//          if (PhoneFromList != "")
//          {
//              mobileLinlayout.setVisibility(View.VISIBLE);
//              TextviewPhoneNumber.setText(PhoneFromList);
//
//          }
//
//          if (DesrciptionFromList != "")
//          {
//              TextviewDescription.setText(DesrciptionFromList);
//              LinlayoutDescripion.setVisibility(View.VISIBLE);
//          }
//
////          if (PriceFromlist != "")
////          {
////              .setVisibility(View.VISIBLE);
////              TextviewPrice.setText(PriceFromlist);
////          }
//
//          if (WebsiteFromList != "")
//          {
//              LinlayoutWebsite.setVisibility(View.VISIBLE);
//              TextviewWebsite.setText(WebsiteFromList);
//          }
//
//          if (ProductNameFromList !="")
//          {
//              tv_productname.setText(ProductNameFromList);
//              ProductNameTV.setText(ProductNameFromList);
//          }
//
//      }
//      else if(!TripManagement.readValueFromPreferences(getActivity(),"ClickedOnList","").equals("ClickedOnList"))
//          {
//              tv_productname.setText(TripManagement.readValueFromPreferences(getActivity(), "ProductName123k", ""));
//              ProductNameTV.setText(TripManagement.readValueFromPreferences(getActivity(), "ProductName123k", ""));
//              LinlayoutUpdate.setVisibility(View.GONE);
//              LinlayoutDelete.setVisibility(View.GONE);
//              String description = getArguments().getString("Description");
//               Phone = getArguments().getString("Phone");
//              final String Email = getArguments().getString("Email");
//              String Telephone = getArguments().getString("Telephone");
//              String Location = getArguments().getString("Location");
//              String Website = getArguments().getString("Website");
//              String CreatedAt = getArguments().getString("CreatedAt");
//              String Offers = getArguments().getString("Offers");
//              String price = getArguments().getString("price");
//              String quality = getArguments().getString("quality");
//              String quantity = getArguments().getString("quantity");
//              String photo = getArguments().getString("Photo");
//              Log.e("PhotoFromCaptured",photo);
//
//
//
//                  if (photo!=null)
//                  {
//                      dispalyimage.setVisibility(View.GONE);
//                      Picasso.with(getActivity()).load(photo).into(blurredImage);
//                  }
//
//
//
//
//
//
//              if (Offers!="")
//              {
//                  OffersLinlayout.setVisibility(View.VISIBLE);
//                  TextviewOffers.setText(CreatedAt);
//              }
//
//              if (CreatedAt!="")
//              {
//                 // createdLinlayout.setVisibility(View.VISIBLE);
//                  createdatNumber.setText(CreatedAt);
//              }
//
//
//              if (Website == "")
//              {
//
//              }
//              else
//              {
//                  LinlayoutWebsite.setVisibility(View.VISIBLE);
//                  TextviewWebsite.setText(Website);
//              }
//
//
//              if (Location != null)
//              {
//                  TextViewLocation.setText(Location);
//              }
//
//              if (Telephone == "")
//              {
//
//              }
//              else
//              {
//                  LinLayoutTelephone.setVisibility(View.VISIBLE);
//                  TextviewTelephone.setText(Telephone);
//              }
//
//              if (Email != "")
//              {
//                  LinLayoutEmail.setVisibility(View.VISIBLE);
//                  TextviewEmail.setText(Email);
//              }
//              LinLayoutEmail.setOnClickListener(new View.OnClickListener()
//              {
//                  @Override
//                  public void onClick(View v)
//                  {
//                      startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+Email)));
//                  }
//              });
//
//
//
//              if (Phone != "")
//              {
//                  mobileLinlayout.setVisibility(View.VISIBLE);
//                  TextviewPhoneNumber.setText(Phone);
//              }
//
//              if (description != "")
//              {
//                  TextviewDescription.setText(description);
//                  LinlayoutDescripion.setVisibility(View.VISIBLE);
//              }
//
//          }

        return view;
    }

    Bitmap encodeAsBitmap(String list) throws WriterException
    {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(String.valueOf(list), BarcodeFormat.QR_CODE, WIDTH, HEIGHT, null);

        } catch (IllegalArgumentException iae)
        {
            // Unsupported format
            return null;
        }

        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++)
        {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
        return bitmap;
    }


    @OnClick(R.id.LinLayoutBack)
    public void setLinLayoutBack()
    {
        if (TripManagement.readValueFromPreferences(getActivity(),"ClickedOnList","").equals("ClickedOnList"))
        {
            Log.e("ppp1","ppp1");
            if (TripManagement.readValueFromPreferences(getActivity(),"BackafterUpdate","").equals("BackafterUpdate"))
            {
                TripManagement.saveValueuToPreferences(getActivity(),"BackafterUpdate","");
                DashboardFragment dashboardFragment = new DashboardFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.FramePicturePreview,dashboardFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
            else
            {
                Log.e("ppp2","pp2");
                DashboardFragment dashboardFragment = new DashboardFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.frame_content,dashboardFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }


        }
        else
            {
                Constant.productDetailQRFragmentStatus = "Update";
            startActivity(new Intent(getActivity(), PicturePreviewActivity.class));
                Log.e("ppp3","ppp3");
        }

    }

//    @OnClick(R.id.LinlayoutChat)
//    public void setLinlayoutChat()
//    {
//        ChatListFragment chatListFragment = new ChatListFragment();
//        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.FramePicturePreview,chatListFragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//    }

//    @OnClick(R.id.LinlayoutPlanetZoom)
//    public void setLLinlayoutPlanetZoom()
//    {
//        startActivity(new Intent(getActivity(),HomeActivity.class));
//    }

    @OnClick(R.id.ShareToChat)
    public void setShareToChat()
    {

        TripManagement.saveValueuToPreferences(getActivity(),"ClickedOnShare","ClickedOnShare");
        ChatListFragment chatListFragment = new ChatListFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.FramePicturePreview,chatListFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

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

    @OnClick(R.id.mobileLinlayout)
    public void setmobileLinlayout()
   {
      callPhoneNumber();
   }

   @OnClick(R.id.LinLayoutTelephone)
   public void setLinLayoutTelephone()
   {
       callPhoneNumber();
   }



   @OnClick(R.id.LinLayoutsaveToGallery)
   public void setLinLayoutsaveToGallery()
   {
       requestPermission();
   }

    public void callPhoneNumber()
    {
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

                if(TripManagement.readValueFromPreferences(getActivity(),"ClickedOnList","").equals("ClickedOnList"))
                {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + PhoneFromList));
                    startActivity(callIntent);


                }
                else
                {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + Phone));
                    startActivity(callIntent);

                }


            }
            else {
                if(TripManagement.readValueFromPreferences(getActivity(),"ClickedOnList","").equals("ClickedOnList"))
                {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + PhoneFromList));
                    startActivity(callIntent);


                }
                else
                {

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + Phone));
                    startActivity(callIntent);

                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {

        if(requestCode == 101)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                callPhoneNumber();
            }
            else
            {
                Log.d("TAG", "Permission not Granted");
            }
        }
        if (requestCode == 786 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {

            if (bitmap != null)
            {

                String Filepath = getImageUri(getActivity(),bitmap).getPath();
                if (Filepath!=null)
                {

                    addImageToGallery(Filepath,getActivity());

                    if (getImageUri(getActivity(),bitmap).getPath()!=null)
                    {

                        Intent mediaScannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        Uri fileContentUri = Uri.fromFile(new File(Filepath)); // With 'permFile' being the File object
                        mediaScannerIntent.setData(fileContentUri);
                        getActivity().sendBroadcast(mediaScannerIntent); // With 'this' being the context, e.g. the activity
                    }

                }

            }
        }
//        switch (requestCode)
//        {
//            case 2:
//                Log.d(TAG, "External storage2");
//                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
//                    Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
//
//
//
//                }else{
//
//                }
//                break;
//
//            case 3:
//                Log.d(TAG, "External storage1");
//                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
//                    Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
//                    //resume tasks needing this permission
//
//
//                }else{
//
//                }
//                break;
//        }


    }

    @Override
    public void onResume()
    {
        super.onResume();
        if(getView() == null){
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
                    return true;
                }
                return false;
            }
        });
    }





//Adding generated QR into Gallery
public  void addImageToGallery(final String filePath, final Context context)
{

    ContentValues values = new ContentValues();

    values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
    values.put(MediaStore.MediaColumns.DATA, filePath);

    context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

    Toast.makeText(getActivity(), "QR Code has been saved to Gallery", Toast.LENGTH_SHORT).show();

}

    //Adding generated QR into Gallery







    //To get Uri from Bitmap

    private Uri getImageUri(Context context, Bitmap inImage)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, null, null);
        return Uri.parse(path);
    }
    //To get uri from Bitmap


    private void requestPermission()
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {

            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 786);
        } else {

            if (bitmap != null)
            {

                String Filepath = getImageUri(getActivity(),bitmap).getPath();
                if (Filepath!=null)
                {
                    addImageToGallery(Filepath,getActivity());
                    if (getImageUri(getActivity(),bitmap).getPath()!=null)
                    {
                        Intent mediaScannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        Uri fileContentUri = Uri.fromFile(new File(Filepath)); // With 'permFile' being the File object
                        mediaScannerIntent.setData(fileContentUri);
                        getActivity().sendBroadcast(mediaScannerIntent); // With 'this' being the context, e.g. the activity
                    }

                }

            }
        }
    }

  @OnClick(R.id.LinlayoutUpdate)
  public void setLinlayoutUpdate()
  {
      Constant.productDetailQRFragmentStatus = "Update";
      TripManagement.saveValueuToPreferences(getActivity(),"ClickedOnUpdateDetails","ClickedOnUpdateDetails");
      Intent intent = new Intent(getActivity(),PicturePreviewActivity.class);
      intent.putExtra("Product_id", Product_id);
      intent.putExtra("Photo", photoFromList);
      intent.putExtra("LocationFromList", LocationFromList);
      intent.putExtra("ProductNameFromList", ProductNameFromList);
      intent.putExtra("PhoneFromList", PhoneFromList);
      intent.putExtra("landlineFromList", landlineFromList);
      intent.putExtra("DesrciptionFromList", DesrciptionFromList);
      intent.putExtra("WebsiteFromList", WebsiteFromList);
      intent.putExtra("EmailFromList", EmailFromList);
      intent.putExtra("PriceFromlist", PriceFromlist);
      intent.putExtra("ValidityStartFromList", ValidityStartFromList);
      intent.putExtra("ValidityEndFromlist", ValidityEndFromlist);
      intent.putExtra("qualityFromList", qualityFromList);
      intent.putExtra("quantityFromList", quantityFromList);

      startActivity(intent);

  }

    @OnClick(R.id.LinlayoutDelete)
    public void setLinlayoutDelete()
    {


        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Are your sure you want to delete?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Delete",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        Deleteproduct();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();

                    }
                });
        alertDialog.show();

    }


    private void Deleteproduct()
    {
        //getting the tag from the edittextd
        //final String tags = editTextTags.getText().toString().trim();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String DeleteproductURl = TripManagement.BASE_URL+"product/"+Product_id+"/delete";
        Log.e("DeleteproductURl",DeleteproductURl);

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, DeleteproductURl,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        progressDialog.dismiss();
                        Log.d("StringRequest--->", String.valueOf(response));
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            Log.d("StringRequestdata--->", String.valueOf(response.data));
                            if (jsonObject.getInt(Keys.success) == 1)
                            {
                                Toast.makeText(getActivity(), "Product Deleted successfully", Toast.LENGTH_SHORT).show();
                                getActivity().onBackPressed();
                            }
                            else if (jsonObject.getInt(Keys.success) == 0)
                            {
                                Toast.makeText(getActivity(), ""+jsonObject.getString(Keys.message), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();

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
              //  params.put(Keys.name,UsernameEdittext.getText().toString());

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
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }

    @OnClick(R.id.LinLayoutShare)
    public void setLinLayoutShare()
    {
        if (TripManagement.readValueFromPreferences(getActivity(),"PicturePreviewActivity","").equals("PicturePreviewActivity"))
        {
           // TripManagement.saveValueuToPreferences(getActivity(),"PicturePreviewActivity","");

            TripManagement.saveValueuToPreferences(getActivity(),"ShareParoductsDetials","ShareParoductsDetials");
            TripManagement.saveValueuToPreferences(getActivity(),"ShareScanResuldImages","ShareScanResuldImages");
            ChatListFragment chatListFragment = new ChatListFragment();
            Bundle bundle = new Bundle();
            bundle.putString("BitmapImageShare",TripManagement.readValueFromPreferences(getActivity(),"hashed_id",""));
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.FramePicturePreview,chatListFragment);
            fragmentTransaction.addToBackStack(null);
            chatListFragment.setArguments(bundle);
            fragmentTransaction.commit();
        }
        else
        {
            TripManagement.saveValueuToPreferences(getActivity(),"ShareParoductsDetials","ShareParoductsDetials");
            TripManagement.saveValueuToPreferences(getActivity(),"ShareScanResuldImages","ShareScanResuldImages");
            ChatListFragment chatListFragment = new ChatListFragment();
            Bundle bundle = new Bundle();
            bundle.putString("BitmapImageShare",TripManagement.readValueFromPreferences(getActivity(),"hashed_id",""));
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.frame_content,chatListFragment);
            fragmentTransaction.addToBackStack(null);
            chatListFragment.setArguments(bundle);
            fragmentTransaction.commit();
        }

    }



}
