package com.angadi.tripmanagementa.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.Fragments.RegisterloginFragment;
import com.angadi.tripmanagementa.PreferenceManager;
import com.angadi.tripmanagementa.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class WelcomeActivity extends AppCompatActivity
{

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    public static Button btnSkip, btnNext;
    private PreferenceManager prefManager;
    private final int REQUEST_LOCATION_PERMISSION = 1;
    private final int REQUEST_WRITE_EXETERNAL_PERMISSION = 2;
    private final int REQUEST_RAED_EXETERNAL_PERMISSION = 3;
    private final int REQUEST_CAMERA_PERMISSION = 100;

    Typeface montserrat_bold,montserrat_regular;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);





        // Checking for first time launch - before calling setContentView()
        prefManager = new PreferenceManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            try
            {
                if (TripManagement.readValueFromPreferences(WelcomeActivity.this,"LoginSuccess","").equals("LoginSuccess"))
                {


                    launchHomeactivity();

                }
                else
                {


                    launchHomeScreen();
                    btnSkip.setVisibility(View.GONE);
                    btnNext.setVisibility(View.GONE);
                }



            }catch (Exception e)
            {
                e.printStackTrace();
                Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                Log.e("welcome_exeption",e.toString());
            }
        }



        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);

         montserrat_regular = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.ttf");
        montserrat_bold = Typeface.createFromAsset(getAssets(), "fonts/MONTSERRAT-BOLD.OTF");

        btnSkip.setTypeface(montserrat_regular);
        btnNext.setTypeface(montserrat_regular);




        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.slide_screen1,
                R.layout.slide_screen2,
                R.layout.slide_screen3};







        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));
        }

      //  requestLocationPermission();
       // requestCameraPermission();
      //  requestreadPermission();
       // requestwritePermission();
        checkLocationandAddToMapCurrent();


        isReadStoragePermissionGranted();
        isWriteStoragePermissionGranted();




        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
                btnSkip.setVisibility(View.GONE);
                btnNext.setVisibility(View.GONE);

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else
                    {
                        launchHomeScreen();
                        btnNext.setVisibility(View.GONE);

                }
            }
        });


        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }


    @Override
    public void onBackPressed()
    {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    public void requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if(EasyPermissions.hasPermissions(this, perms)) {
        }
        else {
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
        }
    }


    @AfterPermissionGranted(REQUEST_CAMERA_PERMISSION)
    public void requestCameraPermission() {
        String[] perms = {Manifest.permission.CAMERA};
        if(EasyPermissions.hasPermissions(this, perms)) {

        }
        else {
            EasyPermissions.requestPermissions(this, "Please grant the Camera permission", REQUEST_CAMERA_PERMISSION, perms);
        }
    }

    @AfterPermissionGranted(REQUEST_WRITE_EXETERNAL_PERMISSION)
    public void requestwritePermission() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(EasyPermissions.hasPermissions(this, perms)) {

        }
        else {
            EasyPermissions.requestPermissions(this, "Please grant the storage permission", REQUEST_WRITE_EXETERNAL_PERMISSION, perms);
        }
    }

    @AfterPermissionGranted(REQUEST_RAED_EXETERNAL_PERMISSION)
    public void requestreadPermission() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE};
        if(EasyPermissions.hasPermissions(this, perms)) {

        }
        else {
            EasyPermissions.requestPermissions(this, "Please grant the storage permission", REQUEST_RAED_EXETERNAL_PERMISSION, perms);
        }
    }



    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen()
    {

        prefManager.setFirstTimeLaunch(false);
        WelcomeActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.content, new RegisterloginFragment()).commit();


    }

    private void launchHomeactivity()
    {

        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(WelcomeActivity.this,Qrcoderesult.class));
       // WelcomeActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.content, new RegisterloginFragment()).commit();


    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            TextView TVtripmgmt = (TextView)findViewById(R.id.TVtripmgmt);
            TextView perplanned = (TextView)findViewById(R.id.perplanned);
            TextView offering = (TextView)findViewById(R.id.offering);

            TVtripmgmt.setTypeface(montserrat_bold);
            perplanned.setTypeface(montserrat_regular);
            offering.setTypeface(montserrat_regular);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }


    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions,
                                           int[] grantResults) {
        switch (permsRequestCode) {


            case REQUEST_CAMERA_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // you have permission go ahead ask next permission and get separate callback
                    RequestPermission(WelcomeActivity.this, Manifest.permission.CAMERA, REQUEST_CAMERA_PERMISSION);
                } else {
                    // you do not have permission show toast.
                    /*check your logic ask again*/
                    //RequestPermission(MainActivity.this, Manifest.permission.CAMERA, REQUEST_RUNTIME_PERMISSION_2);
                }
                return;
            }
            case REQUEST_LOCATION_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    RequestPermission(WelcomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_LOCATION_PERMISSION);

                    // you have 2nd permission go ahead
                } else {
                    // you do not have 2nd permission show toast.
                }
                return;
            }
        }
    }


    public void RequestPermission(Activity thisActivity, String Permission, int Code) {
        if (ContextCompat.checkSelfPermission(thisActivity,
                Permission)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
                    Permission)) {
            } else {
                ActivityCompat.requestPermissions(thisActivity,
                        new String[]{Permission},
                        Code);
            }
        }
    }

    public boolean CheckPermission(Context context, String Permission) {
        if (ContextCompat.checkSelfPermission(context,
                Permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }



    public  boolean isWriteStoragePermissionGranted()
    {

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {

                return true;
            } else {


                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation

            return true;
        }
    }

    public void checkLocationandAddToMapCurrent()
    {
        if (ActivityCompat.checkSelfPermission(WelcomeActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(WelcomeActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Requesting the Location permission
            ActivityCompat.requestPermissions(WelcomeActivity.this, new String[] {
                    android.Manifest.permission.ACCESS_FINE_LOCATION
            }, REQUEST_LOCATION_PERMISSION);
            return;
        }
        //Fetching the last known location using the Fus



    }
    public  boolean isReadStoragePermissionGranted()
    {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {

                return true;
            } else {


                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation

            return true;
        }
    }


}
