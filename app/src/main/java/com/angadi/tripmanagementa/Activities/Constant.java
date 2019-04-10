package com.angadi.tripmanagementa.Activities;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;

import com.angadi.tripmanagementa.Activities.database.model.Products;
import com.angadi.tripmanagementa.Activities.database.model.SubProduct;
import com.angadi.tripmanagementa.Activities.database.model.mSubCategory;

import java.net.URISyntaxException;

public class Constant {
    public static String productID = "";
    public static String productImage = "";
    public static String productName = "";
    public static String productPrice = "";
    public static String productTimeDate = "";
    public static String recyclerviewPosition = "";
    public static mSubCategory mSubCategorysEd;

    public static String mPicURL = "";
    public static String mAttachmentURLI = "";
    public static String mAttachmentURLII = "";
    public static String mAttachmentURLIII = "";
    public static String mAttachmentURLIV = "";
    public static String mAttachmentURLV = "";
    public static String mOffersURL = "";
    public static String mAttachmentFileURL = "";

    public static String getDashboardProductPosition = "";
    public static String getDashboardProductCount = "";
    public static String productDetailQRFragmentStatus = "";

    //ProductInfo
    public static String pi_Name, pi_PlusCode, pi_Location, pi_Photo, pi_ISD_Code, pi_Phone, pi_STD_Code, pi_Landline, pi_Email,
                         pi_Website, pi_Description, pi_Price, pi_Validation_Start, pi_Validation_End, pi_Quality, pi_Offer,
                         pi_Attachment, pi_Image, pi_Created_At, pi_GST, pi_HashedID, pi_HashedID_URL, pi_Favorite, pi_DateTime;
    public static double pi_Latitude, pi_Longitude;
    public static int pi_ID, pi_ProductArrayCount, pi_ProductArrayCountNow, pi_ProductID, pi_UserID, pi_Type, pi_Quantity;

    //SubProductInfo
    public static int spi_ID, spi_ProductArrayCount, spi_ProductArrayCountNow, spi_SubProductArrayCount, getSpi_SubProductArrayCountNow,
                      spi_SpID;
    public static String spi_Name, spi_Price, spi_Photo, spi_TimeDate;

    public  static ArrayList<Products> mProductsList = new ArrayList<Products>();
    public static ArrayList<SubProduct> mSubProductList = new ArrayList<SubProduct>();

    String QueryDemo = "SELECT tbl_sub_cat_pid.id,product_id, " +
            "tbl_sub_cat_pn.product_name, " +
            "tbl_sub_cat_pq.quantity, " +
            "tbl_sub_cat_pp.price, " +
            "tbl_sub_cat_pt.total, " +
            "tbl_sub_cat_pt.date_time " +
            "FROM tbl_sub_cat_pt " +
            "JOIN tbl_sub_cat_pid ON tbl_sub_cat_pid.id = tbl_sub_cat_pt.id " +
            "JOIN tbl_sub_cat_pn ON tbl_sub_cat_pn.id = tbl_sub_cat_pt.id " +
            "JOIN tbl_sub_cat_pp ON tbl_sub_cat_pp.id = tbl_sub_cat_pt.id " +
            "JOIN tbl_sub_cat_pq ON tbl_sub_cat_pq.id = tbl_sub_cat_pt.id;";

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(date);
        } catch (ParseException e) {
            Log.d("Error",""+e.getMessage());
        }

        return "";
    }
}
