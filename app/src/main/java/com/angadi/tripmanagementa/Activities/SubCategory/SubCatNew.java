package com.angadi.tripmanagementa.Activities.SubCategory;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.angadi.tripmanagementa.Activities.PicturePreviewActivity;
import com.angadi.tripmanagementa.Activities.SubCategory.Helper.ImagePickerActivity;
import com.angadi.tripmanagementa.Activities.SubCategory.Helper.MyTextView;
import com.angadi.tripmanagementa.Activities.database.DatabaseHelper;
import com.angadi.tripmanagementa.R;
import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.IOException;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SubCatNew extends AppCompatActivity {

    LinearLayout tv_heading, ll_title_placeholder, ll_add_image, ll_product_name, ll_product_price, ll_next_finish, ll_gal_cam;
    MyTextView txt_title_placeholder, txt_heading, btn_next_finish;
    ImageView sub_cat_back, add_image_cam_gal;
    Button btn_camera, btn_gallery;
    EditText product_name_custedt, product_price_custedt;
    public static String ImageURL = "", Product_Name, Product_Price, Title_Content, Heading_Content, Nxt_Fnsh_Status = "AddImage";
    private static final String TAG = SubCatNew.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categorys);

        tv_heading = (LinearLayout)findViewById(R.id.tv_heading);
        ll_title_placeholder = (LinearLayout)findViewById(R.id.ll_title_placeholder);
        ll_add_image = (LinearLayout)findViewById(R.id.ll_add_image);
        ll_product_name = (LinearLayout)findViewById(R.id.ll_product_name);
        ll_product_price =(LinearLayout)findViewById(R.id.ll_product_price);
        ll_next_finish = (LinearLayout)findViewById(R.id.ll_next_finish);

        txt_heading = (MyTextView)findViewById(R.id.txt_heading);
        txt_title_placeholder = (MyTextView)findViewById(R.id.txt_title_placeholder);
        btn_next_finish = (MyTextView)findViewById(R.id.btn_next_finish);

        sub_cat_back = (ImageView)findViewById(R.id.sub_cat_back);
        add_image_cam_gal = (ImageView)findViewById(R.id.add_image_cam_gal);

        product_name_custedt = (EditText)findViewById(R.id.product_name_custedt);
        product_price_custedt = (EditText)findViewById(R.id.product_price_custedt);

        db = new DatabaseHelper(this);

        validAct();

        loadProfileDefault();

        onBackSubCat();
        // Clearing older images from cache directory
        // don't call this line if you want to choose multiple images in the same activity
        // call this once the bitmap(s) usage is over
        //ImagePickerActivity.clearCache(this);
    }

    private void loadProfile(String url) {
        Log.d(TAG, "Image cache path: " + url);
        ImageURL = url;
        Glide.with(this).load(url)
                .into(add_image_cam_gal);

        add_image_cam_gal.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
    }

    private void loadProfileDefault() {
        Glide.with(this).load(R.drawable.addimage)
                .into(add_image_cam_gal);
        add_image_cam_gal.setColorFilter(ContextCompat.getColor(this, R.color.profile_default_tint));
    }

    public void imageClick() {
        add_image_cam_gal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(SubCatNew.this)
                        .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    showImagePickerOptions();
                                }

                                if (report.isAnyPermissionPermanentlyDenied()) {
                                    showSettingsDialog();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(SubCatNew.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(SubCatNew.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    // loading profile image from local cache
                    loadProfile(uri.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SubCatNew.this);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                SubCatNew.this.openSettings();
            }
        });
        builder.setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


    public void onBackSubCat() {

        sub_cat_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Nxt_Fnsh_Status.equals("AddImage")) {
                    SubCatNew.this.finish();
                    startActivity(new Intent(SubCatNew.this, PicturePreviewActivity.class));
                } else if (Nxt_Fnsh_Status.equals("ProductName")){
                    txt_title_placeholder.setText("Add Your Product Image");
                    ll_product_name.setVisibility(View.GONE);
                    ll_add_image.setVisibility(View.VISIBLE);

                    btn_next_finish.setText("Next");

                    Nxt_Fnsh_Status = "AddImage";

                    btn_next_finish.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            validAct();
                        }
                    });
                } else if (Nxt_Fnsh_Status.equals("ProductPrice")){
                    txt_title_placeholder.setText("Add Your Product Name");
                    ll_product_price.setVisibility(View.GONE);
                    ll_product_name.setVisibility(View.VISIBLE);

                    btn_next_finish.setText("Next");

                    Nxt_Fnsh_Status = "ProductName";

                    btn_next_finish.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            validAct();
                        }
                    });
                } else {

                }
            }
        });
    }

    public void validAct() {
        if (Nxt_Fnsh_Status.equals("AddImage")) {
            txt_title_placeholder.setText("Add Your Product Image");
            ll_add_image.setVisibility(View.VISIBLE);

            btn_next_finish.setText("Next");

            imageClick();

            btn_next_finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ImageURL.equals("")) {
                        Log.d("Message ","Image Not Loaded !");
                    } else {
                        Nxt_Fnsh_Status = "ProductName";
                        validAct();
                    }
                }
            });

        } else if (Nxt_Fnsh_Status.equals("ProductName")) {
            ll_add_image.setVisibility(View.GONE);
            ll_product_name.setVisibility(View.VISIBLE);
            txt_title_placeholder.setText("Add Your Product Name");

            btn_next_finish.setText("Next");

            btn_next_finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (product_name_custedt.getText().toString().equals("")) {
                        product_name_custedt.setError("Please Enter Product Name");
                    } else {
                        Nxt_Fnsh_Status = "ProductPrice";
                        Product_Name = product_name_custedt.getText().toString();
                        validAct();
                    }
                }
            });

        } else if (Nxt_Fnsh_Status.equals("ProductPrice")) {
            ll_product_name.setVisibility(View.GONE);
            ll_product_price.setVisibility(View.VISIBLE);
            txt_title_placeholder.setText("Add Your Product Price");

            btn_next_finish.setText("Finish");

            btn_next_finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (product_price_custedt.getText().toString().equals("")) {
                        product_price_custedt.setError("Please Enter Product Price");
                    } else {
                        Product_Price = product_price_custedt.getText().toString();

                        if (db.getSubCategoryCount() == 50) {
                            Log.d("Message : ", "Limit Exceeds !");
                        } else {

                            new SweetAlertDialog(SubCatNew.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Note :")
                                    .setContentText("Want To Add More Sub Product ?")
                                    .setCancelText("No")
                                    .setConfirmText("Yes")
                                    .showCancelButton(true)
                                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog)
                                        {
                                            Log.d("SweetAlertDialog","1");

                                            db.insertSubCategory(ImageURL, Product_Name, Product_Price);

                                            ImageURL = "";
                                            Product_Name = "";
                                            Product_Price = "";
                                            product_name_custedt.setText("");
                                            product_price_custedt.setText("");

                                            loadProfileDefault();

                                            Nxt_Fnsh_Status = "AddImage";
                                            txt_title_placeholder.setText("Add Your Product Image");
                                            ll_product_price.setVisibility(View.GONE);
                                            ll_add_image.setVisibility(View.VISIBLE);

                                            btn_next_finish.setText("Next");
                                            imageClick();

                                            btn_next_finish.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Nxt_Fnsh_Status = "ProductName";
                                                    validAct();
                                                }
                                            });

                                            SubCatNew.this.finish();
                                            startActivity(new Intent(SubCatNew.this, PicturePreviewActivity.class));

                                            sDialog.cancel();
                                        }
                                    })
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog)
                                        {
                                            Log.d("SweetAlertDialog","2");

                                            db.insertSubCategory(ImageURL, Product_Name, Product_Price);

                                            ImageURL = "";
                                            Product_Name = "";
                                            Product_Price = "";
                                            product_name_custedt.setText("");
                                            product_price_custedt.setText("");

                                            loadProfileDefault();

                                            txt_title_placeholder.setText("Add Your Product Image");
                                            ll_product_price.setVisibility(View.GONE);
                                            ll_add_image.setVisibility(View.VISIBLE);

                                            btn_next_finish.setText("Next");
                                            imageClick();

                                            btn_next_finish.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Nxt_Fnsh_Status = "ProductName";
                                                    validAct();
                                                }
                                            });

                                            sDialog.cancel();
                                        }
                                    })
                                    .show();
                            }
                        }
                    }
                });

        } else {
            Log.d("Message","Next / Finish Status Error !");
        }
    }

}
