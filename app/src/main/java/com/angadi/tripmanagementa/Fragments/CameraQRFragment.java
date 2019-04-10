package com.angadi.tripmanagementa.Fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.angadi.tripmanagementa.Activities.HomeActivity;

import com.angadi.tripmanagementa.Activities.PicturePreviewActivity;
import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.Size;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

public class CameraQRFragment extends Fragment
{
    CameraView camera;
    private boolean mCapturingPicture;
    private Size mCaptureNativeSize;
    private long mCaptureTime;

    @BindView(R.id.capturePhoto) LinearLayout capturePhoto;
    @BindView(R.id.LinlayoutBack) LinearLayout LinlayoutBack;
    @BindView(R.id.LinlayoutFrontCam) LinearLayout LinlayoutFrontCam;
    @BindView(R.id.LinlayoutGallery) LinearLayout LinlayoutGallery;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.layout_qr_camera,container ,false);

        ButterKnife.bind(this,view);
        Fabric.with(getActivity(), new Crashlytics());


        camera = view.findViewById(R.id.camera);
        camera.setLifecycleOwner(this);
        camera.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] picture)
            {
                onPicture(picture);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getActivity(). getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.black));
        }

       // TripManagement.saveValueuToPreferences(getActivity(),"Bitmapsave","");


        return view;


    }
    @Override
    public void onResume()
    {
        super.onResume();

        try { camera.start(); } catch (Exception ex) { camera.stop(); }

//        if (camera!=null)
//        {
//            camera.start();
//        }

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
                    // handle back button's click listener
                    startActivity(new Intent(getActivity(),HomeActivity.class));
                    return true;
                }
                return false;
            }
        });

       // TripManagement.saveValueuToPreferences(getActivity(),"Bitmapsave","");
    }

    @Override
    public void onPause()
    {

        super.onPause();
//        if (camera!=null)
//        {
//            camera.stop();
//        }

        try { camera.stop(); } catch (Exception ex) { camera.stop(); }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (camera!=null)
        {
            camera.destroy();
        }

    }

    private void onPicture(byte[] jpeg)
    {
        mCapturingPicture = false;
        long callbackTime = System.currentTimeMillis();


        // This can happen if picture was taken with a gesture.
        if (mCaptureTime == 0) mCaptureTime = callbackTime - 300;
        if (mCaptureNativeSize == null) mCaptureNativeSize = camera.getPictureSize();

      //  PicturePreviewActivity.setImage(jpeg);
        ProductDetailsQRFragment.setImage(jpeg);
        Intent intent = new Intent(getActivity(), PicturePreviewActivity.class);
//        intent.putExtra("delay", callbackTime - mCaptureTime);
//        intent.putExtra("nativeWidth", mCaptureNativeSize.getWidth());
//        intent.putExtra("nativeHeight", mCaptureNativeSize.getHeight());
        startActivity(intent);

        mCaptureTime = 0;
        mCaptureNativeSize = null;
    }

    @OnClick(R.id.capturePhoto)
    public void setcapturePhoto()
    {
        camera.capturePicture();
    }


    @OnClick(R.id.LinlayoutBack)
    public void setLinlayoutBack()
    {
        startActivity(new Intent(getActivity(), HomeActivity.class));

    }


    @OnClick(R.id.LinlayoutFrontCam)
    public void setLinlayoutFrontCam()
    {
       // camera.stop();
        camera.toggleFacing();


       // camera.start();


    }

    @OnClick(R.id.LinlayoutGallery)
    public void setLinlayoutGallery()
    {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"),1234);
    }

}
