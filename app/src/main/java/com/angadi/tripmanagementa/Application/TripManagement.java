package com.angadi.tripmanagementa.Application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.angadi.tripmanagementa.Extras.Constants;
import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;

import java.util.concurrent.TimeUnit;

import io.fabric.sdk.android.Fabric;

import io.intercom.android.sdk.Intercom;
import okhttp3.OkHttpClient;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by alexey.herashchenko on 02.07.2015.
 */
public class TripManagement extends Application {



    private static boolean orderAnythingMode = false;
    Context context;
    private static boolean userLocationAbsent, disableSwipeOnProfile;

    public static String BASE_URL = "https://www.planetzoom.co.in/api/v1/";

    public static int ERRCODE_401 = 401;
    public static int SUCCESSCODE_1 = 1;
    public static int ERRCODE_500 = 500;
   // public static String BASE_URL = "https://13.234.77.47/api/v1/";

   public static TripManagement mInstatnce;



    @Override
    public void onCreate() {
        super.onCreate();
        initializeRetrofit();
        Fabric.with(this, new Crashlytics());
        Intercom.initialize(this, "android_sdk-3226f3e0ca137467d6b324f8f9e5c3e3becf196f", "z8fq0tsh");

    }

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);

    }


    public static final Retrofit initializeRetrofit()
    {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(30,TimeUnit.SECONDS).addInterceptor(interceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }



    public static boolean isOrderAnythingMode() {
        return orderAnythingMode;
    }

    public static boolean isUserLocationAbsent() {
        return userLocationAbsent;
    }


    public static void saveValueuToPreferences(Context context, String preferenceName, String preferenceValue)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }



    public static String readValueFromPreferences(Context context, String preferenceName, String defaultValue)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sharedPreferences.getString(preferenceName, defaultValue);
    }



    public void setStatusbar(Activity activity)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window =activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(activity, R.color.black));
        }

    }



    //        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);



}
