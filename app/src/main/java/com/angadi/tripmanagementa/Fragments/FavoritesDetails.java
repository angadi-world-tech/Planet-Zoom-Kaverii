package com.angadi.tripmanagementa.Fragments;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

import static com.angadi.tripmanagementa.Activities.HomeActivity.BLACK;
import static com.angadi.tripmanagementa.Activities.HomeActivity.HEIGHT;
import static com.angadi.tripmanagementa.Activities.HomeActivity.WHITE;
import static com.angadi.tripmanagementa.Activities.HomeActivity.WIDTH;

public class FavoritesDetails extends Fragment
{
    @BindView(R.id.LinLayoutBack) LinearLayout LinLayoutBack;
    @BindView(R.id.ShareToChat) LinearLayout ShareToChat;
    @BindView(R.id.image) ImageView myImageCapturedPhoto;
    @BindView(R.id.RelalyoutImageViewCapturedPicture) RelativeLayout RelalyoutImageViewCapturedPicture;
    @BindView(R.id.RellayoutQR) RelativeLayout RellayoutQR;
    @BindView(R.id.tv_productname) TextView tv_productname;
    @BindView(R.id.ProductNameTV) TextView ProductNameTV;
    @BindView(R.id.TextViewdescription) TextView TextViewdescription;
    @BindView(R.id.TextViewWebsite) TextView TextViewWebsite;
    @BindView(R.id.Nameoffavedproduct) TextView Nameoffavedproduct;
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
    @BindView(R.id.myImage) ImageView myImage;
    @BindView(R.id.blurredImage) ImageView blurredImage;

    Bitmap bitmap;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.favirites_details,container ,false);
        ButterKnife.bind(this, view);
        Fabric.with(getActivity(), new Crashlytics());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.black));
        }

        final Typeface montserrat_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MONTSERRAT-BOLD.OTF");

        Typeface montserrat_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Montserrat-Regular.ttf");


        tv_productname.setTypeface(montserrat_regular);
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


        Nameoffavedproduct.setTypeface(montserrat_bold);
        String ProductName = getArguments().getString("ProductName");
        String HashedId = getArguments().getString("HashedId");
        Log.d("HASHEDDD",HashedId);
        String Location = getArguments().getString("Location");
        String Desrcption = getArguments().getString("Desrcption");
        String Mobile = getArguments().getString("Mobile");
        String landlineFromList = getArguments().getString("landline");
        String stdcode = getArguments().getString("stdcode");



        String  EmailFromList = getArguments().getString("Email");
        String  WebsiteFromList = getArguments().getString("Website");
        String PriceFromlist = getArguments().getString("Price");
        String photoFromList = getArguments().getString("Photo");
        String qualityFromList = getArguments().getString("quality");
        String quantityFromList = getArguments().getString("quantity");


        Nameoffavedproduct.setText(ProductName);


        try
        {
            bitmap = encodeAsBitmap(HashedId);
            myImage.setImageBitmap(bitmap);
        } catch (WriterException e)
        {
            e.printStackTrace();
        }

        if (WebsiteFromList != "")
        {
            LinlayoutWebsite.setVisibility(View.VISIBLE);
            TextviewWebsite.setText(WebsiteFromList);
        }

        if (Desrcption != "")
        {
            LinlayoutDescripion.setVisibility(View.VISIBLE);
            TextviewDescription.setText(Desrcption);

        }
        if (Mobile != "")
        {
            mobileLinlayout.setVisibility(View.VISIBLE);
            TextviewPhoneNumber.setText(Mobile);
        }

        if (Location != "")
        {
            TextViewLocation.setText(Location);
        }

        if (quantityFromList!="")
        {
            TextviewQuantity.setText(quantityFromList);
        }


        if (qualityFromList!="")
        {
            TextviewQuality.setText(qualityFromList);
        }



        if (EmailFromList!="")
        {
            LinLayoutEmail.setVisibility(View.VISIBLE);
            TextviewEmail.setText(EmailFromList);
        }


        if (landlineFromList!="")
        {
            LinLayoutTelephone.setVisibility(View.VISIBLE);
            TextviewTelephone.setText(stdcode+"-"+landlineFromList);

        }

        if (photoFromList != "")
        {
            dispalyimage.setVisibility(View.GONE);
            Picasso.with(getActivity()).load(photoFromList).into(blurredImage);
        }



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
        getActivity().onBackPressed();
    }
}
