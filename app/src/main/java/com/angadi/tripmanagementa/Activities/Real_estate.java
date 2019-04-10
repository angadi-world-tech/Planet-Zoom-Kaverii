package com.angadi.tripmanagementa.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.angadi.tripmanagementa.Adapters.Realestate_category_Adapter;
import com.angadi.tripmanagementa.Fragments.Paid_AD_Fragment;
import com.angadi.tripmanagementa.Fragments.Uploadadd_fragment;
import com.angadi.tripmanagementa.Model.Realestate_category;
import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Real_estate extends AppCompatActivity {
    public static LinearLayout main, help, test,tooldetails;
    private static final int CAPTURE_IMAGE_REQUEST_CODE = 1000;
    private static final int CAPTURE_VIDEO_REQUEST_CODE = 1001;
    static Uri capturedImageUri = null;
    LinearLayout LinLayoutBack,Lin_paid_AD;
    EditText edt_textsearch;
    String[] check;
    ArrayList<Realestate_category> arraylist = new ArrayList<Realestate_category>();
    int[] flag;
    Realestate_category_Adapter adapter;
    String[] category_name;
    GridView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_estate);

        Fabric.with(this, new Crashlytics());


        Button add = findViewById(R.id.upload);
        Button paid_AD = findViewById(R.id.paid_AD);
        help = findViewById(R.id.help);

        main = findViewById(R.id.layoutmain);
        tooldetails=findViewById(R.id.tooldetails);
        LinLayoutBack = findViewById(R.id.LinLayoutBack);
        Lin_paid_AD = findViewById(R.id.Lin_paid_AD);
        edt_textsearch = findViewById(R.id.edt_textsearch);
        flag = new int[] { R.mipmap.flat_icon, R.mipmap.plot_icon,
                R.mipmap.home_icon, R.mipmap.resort_icon,
                R.mipmap.flat_icon, R.mipmap.bungalow_icon, R.mipmap.property_icon,
                R.mipmap.villas_icon, R.mipmap.corporate_icon};
        // test=findViewById(R.id.test);
        category_name = new String[] {"Appartment","Plots","Homes","Resorts","Flats","Bungalow","Property","Villas","Corporate Offices"};

        // check = edt_textsearch.getText().toString();


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                help.setVisibility(VISIBLE);
                main.setVisibility(GONE);
                tooldetails.setVisibility(VISIBLE);
                Uploadadd_fragment uploadadd_fragment = new Uploadadd_fragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up);

                ft.replace(R.id.content_frame, uploadadd_fragment);
                ft.commit();

            }
        });
        LinLayoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Backpresses();
            }
        });

        paid_AD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                help.setVisibility(View.INVISIBLE);
                main.setVisibility(GONE);
                tooldetails.setVisibility(View.INVISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame2, new Paid_AD_Fragment()).commit();

            }
        });

//        for (int i=0;i<Realestate_Name.length;i++)
//        {
//            if((check.equalsIgnoreCase(Realestate_Name[i]))) {
//                Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_LONG).show();
//            }
//
//        }

        // Locate the ListView in listview_main.xml
        list = (GridView) findViewById(R.id.listview_category);

        for (int i = 0; i < flag.length; i++)
        {
            Realestate_category wp = new Realestate_category(flag[i], category_name[i]);
            // Binds all strings into an array
            arraylist.add(wp);
        }

        // Pass results to ListViewAdapter Class


        adapter = new Realestate_category_Adapter(this, arraylist);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        // Locate the EditText in listview_main.xml

        // Capture Text in EditText
        edt_textsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = edt_textsearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });
    }


    private void Backpresses() {
        Intent intent= new Intent(Real_estate.this,HomeActivity.class);
        startActivity(intent);
//       overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up);

    }


}
