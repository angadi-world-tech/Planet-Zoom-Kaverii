package com.angadi.tripmanagementa.Activities;

import android.app.AlertDialog;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;



import io.fabric.sdk.android.Fabric;

import static com.angadi.tripmanagementa.Activities.HomeActivity.BarcodeObject;

public class MapsActivity extends AppCompatActivity  {

    public static Button button_next;

    String QRRESULT;
  //  protected static final String TAG = "MapActivity";
    private static final String TAG = "BarcodeMain";

    protected static final int REQUEST_CHECK_SETTINGS = 0x1;


    private static final int RC_BARCODE_CAPTURE = 10001;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        Fabric.with(this, new Crashlytics());

        button_next = (Button) findViewById(R.id.button);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));
        }




       // String s1 = QRRESULT.replace("lat/lng: (","");
       // String s2 = s1.replace(")","");


          QRRESULT = getIntent().getStringExtra(BarcodeObject);


        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            String message = bundle.getString("myData");

            Toast.makeText(this, "Mes"+message, Toast.LENGTH_SHORT).show();
            button_next.setVisibility(View.VISIBLE);
            button_next.setText(message);
        }



//
//        try
//        {
////            String[] latlong =  s2.split(",");
////            latitude_qr = Double.parseDouble(latlong[0]);
////            longitude_qr = Double.parseDouble(latlong[1]);
////
////            location_qr = new LatLng(latitude_qr, longitude_qr);
//
//        }
//        catch (NumberFormatException e)
//        {
//            AlertDialog.Builder dialog = new AlertDialog.Builder(MapsActivity.this);
//            dialog.setCancelable(false);
//            dialog.setTitle("Alert!");
//            dialog.setMessage("This Qr code does not cantain Location" );
//            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
//            {
//                @Override
//                public void onClick(DialogInterface dialog, int id)
//                {
//                    startActivity(new Intent(MapsActivity.this,HomeActivity.class));
//                }
//            });
//
//            final AlertDialog alert = dialog.create();
//            alert.show();
//        }


       // Zomato();


//        button_next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Autocomplete fr = new Autocomplete();
//                FragmentManager fm = getFragmentManager();
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.addToBackStack(null);
//                ft.add(R.id.frame_content, fr);
//                ft.commit();
//            }
//        });
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Toast.makeText(this, "k1", Toast.LENGTH_SHORT).show();
        if (requestCode == RC_BARCODE_CAPTURE)
        {
            Toast.makeText(this, "k2", Toast.LENGTH_SHORT).show();
            if (resultCode == CommonStatusCodes.SUCCESS)
            {
                if (data != null)
                {
                    Barcode barcode = data.getParcelableExtra(BarcodeObject);
//                    statusMessage.setText("Barcode Success");
//                    barcodeValue.setText(barcode.displayValue);
                      Toast.makeText(this, "success"+barcode.displayValue, Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Scan Result");
                    builder.setMessage(barcode.displayValue);
                    AlertDialog alert1 = builder.create();
                    alert1.show();
                } else {
                    Toast.makeText(this, "Barcode Failed!", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                /*statusMessage.setText(String.format("Barcode Error",
                        CommonStatusCodes.getStatusCodeString(resultCode)));*/
                Toast.makeText(this, "Barcode error!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "exited", Toast.LENGTH_LONG).show();

            super.onActivityResult(requestCode, resultCode, data);
        }
    }



}