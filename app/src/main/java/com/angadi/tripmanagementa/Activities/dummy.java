package com.angadi.tripmanagementa.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.Custum.FilePath;
import com.angadi.tripmanagementa.Extras.Keys;
import com.angadi.tripmanagementa.R;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.util.UUID;

import static com.angadi.tripmanagementa.Activities.HomeActivity.BarcodeObject;

public class dummy extends AppCompatActivity {

    //Declaring views


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dummy);

        //Requesting storage permission


        }



    /*
     * This is the method responsible for pdf upload
     * We need the full pdf path and the name for the pdf in this method
     * */




}
