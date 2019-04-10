package com.angadi.tripmanagementa.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.angadi.tripmanagementa.Activities.Real_estate;
import com.angadi.tripmanagementa.Database.DbHelper;
import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import io.fabric.sdk.android.Fabric;

import static android.view.View.VISIBLE;


public class Uploadadd_fragment extends Fragment {

    Button upload;
    ImageButton image_back;
    public static ImageView camera, flag;
    LinearLayout LinLayoutBack;
    //code for Activity result
    TextView text_websit,text_location,text_contactnumber,text_description,text_price,text_category,text_time;
    private static final int CAPTURE_IMAGE_REQUEST_CODE = 1000;
    private static final int CAPTURE_VIDEO_REQUEST_CODE = 1001;
    private static final int IMAGE = 2000;
    private static final int VIDEO = 2001;
    static Uri capturedImageUri = null;
    DbHelper dbHelper;
    private String TAG = "SignupFragment";
    private CheckBox private_policy;
   public static String textwebsite,textlocation,textcontactnumber,textdescription,textprice,textcategory,texttime;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_uploadadd_fragment, container, false);

        Fabric.with(getActivity(), new Crashlytics());

        upload = v.findViewById(R.id.upload);
        dbHelper = new DbHelper(getActivity());

        camera = v.findViewById(R.id.camera);
       // image_back = v.findViewById(R.id.image_back);
        LinLayoutBack = v.findViewById(R.id.LinLayoutBack);
        text_websit=v.findViewById(R.id.txt_website);
        text_location=v.findViewById(R.id.txt_location);
        text_contactnumber=v.findViewById(R.id.txt_contactnumber);
        text_description=v.findViewById(R.id.txt_description);
        text_price=v.findViewById(R.id.txt_price);
        private_policy= v.findViewById(R.id.checkbox_privacy);
//        text_category=v.findViewById(R.id.txt_category);
//        text_time=v.findViewById(R.id.txt_time);
        flag = v.findViewById(R.id.flag);
        textwebsite=text_websit.getText().toString().trim();
        textlocation=text_location.getText().toString().trim();
        textcontactnumber=text_contactnumber.getText().toString().trim();
        textdescription=text_description.getText().toString().trim();
        textprice=text_price.getText().toString().toString().trim();
//        textcategory=text_category.getText().toString().trim();
//        texttime=text_time.getText().toString().trim();
      //  dbHelper.Adddetails(textwebsite, textlocation,textcontactnumber,textdescription,textprice,textcategory,texttime);



        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Real_estate.help.setVisibility(VISIBLE);
                Real_estate.main.setVisibility(View.GONE);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame1, new Search_fragment()).commit();
                getActivity().overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up);


                Toast.makeText(getActivity(), "You have successfully update your data", Toast.LENGTH_SHORT).show();


            }
        });


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIfCameraAvailable()) {
                    clickImage();
                } else {
                    Toast.makeText(getActivity(), "Camera Not Available", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

        private_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // custom dialog
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.custom_dialog);
                // Custom Android Allert Dialog Title
                dialog.setTitle("Custom Dialog Example");

              //  Button dialogButtonCancel = (Button) dialog.findViewById(R.id.customDialogCancel);
                Button dialogButtonOk = (Button) dialog.findViewById(R.id.customDialogOk);
                // Click cancel to dismiss android custom dialog box
//                dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });

                // Your android custom dialog ok action
                // Action for custom dialog ok button click
                dialogButtonOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        LinLayoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Backpresses();
            }
        });

        return v;
    }

    private void Backpresses() {
        Intent intent= new Intent(getActivity(),Real_estate.class);
        startActivity(intent);
//        getActivity().overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up);


    }


    //method to click image
    private void clickImage() {

        Calendar cal = Calendar.getInstance();
        File file = new File(Environment.getExternalStorageDirectory(), (cal.getTimeInMillis() + ".jpg"));
        Log.e("file", "" + file);


        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        capturedImageUri = Uri.fromFile(file);
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);
        startActivityForResult(i, CAPTURE_IMAGE_REQUEST_CODE);


    }



    //check if camera is available in device
    public boolean checkIfCameraAvailable() {
        if (getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            //camera is available
            return true;
        } else {
            //camera is not available
            return false;
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //if capturing image
        if (requestCode == CAPTURE_IMAGE_REQUEST_CODE) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), capturedImageUri);
                Log.e("bitmap", "" + bitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Log.d("yyyyy", String.valueOf(baos));
                try {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data1 = baos.toByteArray();
                    Log.d("yyyyyhhhh", String.valueOf(data1));
                     dbHelper.addToDb(data1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(getActivity(), "Image saved to DB successfully", Toast.LENGTH_SHORT).show();
                Uploadadd_fragment.flag.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}