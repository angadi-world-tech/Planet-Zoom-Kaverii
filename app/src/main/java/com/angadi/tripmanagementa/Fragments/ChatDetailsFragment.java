package com.angadi.tripmanagementa.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.angadi.tripmanagementa.Activities.HomeActivity;
import com.angadi.tripmanagementa.Activities.PicturePreviewActivity;
import com.angadi.tripmanagementa.Activities.Qrcoderesult;
import com.angadi.tripmanagementa.Activities.RegistrationActivity;
import com.angadi.tripmanagementa.Adapters.ChatDetailsAdapter;
import com.angadi.tripmanagementa.Adapters.CustomListAdapter;
import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.Extras.Keys;
import com.angadi.tripmanagementa.Extras.VolleyMultipartRequest;
import com.angadi.tripmanagementa.Model.ChatDetailsPojo;
import com.angadi.tripmanagementa.Model.ChatListPojo;
import com.angadi.tripmanagementa.Model.Product;
import com.angadi.tripmanagementa.R;
import com.angadi.tripmanagementa.emoji.Emojicon;
import com.angadi.tripmanagementa.emoji.EmojiconEditText;
import com.angadi.tripmanagementa.emoji.EmojiconGridView;
import com.angadi.tripmanagementa.emoji.EmojiconsPopup;
import com.crashlytics.android.Crashlytics;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.fabric.sdk.android.Fabric;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class ChatDetailsFragment extends Fragment
{
    String avatar, toServerUnicodeEncoded,fromServerUnicodeDecoded ;

    private int GALLERY = 1, CAMERA = 2;
    private static final String IMAGE_DIRECTORY = "/PlanetZoom";

    @BindView(R.id.LinLayoutback)LinearLayout LinLayoutback;
    @BindView(R.id.emoji_btn) ImageView emojiButton;
    @BindView(R.id.MsgEditText) EmojiconEditText MsgEditText;
    @BindView(R.id.LinLayoutSend) LinearLayout LinLayoutSend;
    @BindView(R.id.LinLayoutMike) LinearLayout LinLayoutMike;
    @BindView(R.id.LinLayoutCamera) LinearLayout LinLayoutCamera;
    @BindView(R.id.LinLayoutGallery) LinearLayout LinLayoutGallery;
    @BindView(R.id.ProfileImageview) CircleImageView ProfileImageview;
    @BindView(R.id.unique_id) TextView unique_id;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.help) TextView help;
    @BindView(R.id.id_name) TextView id_name;
    @BindView(R.id.NameLinLayout) TextView NameLinLayout;
    @BindView(R.id.TvNoChatsyet) TextView TvNoChatsyet;
    @BindView(R.id.listview) ListView listview;
    @BindView(R.id.LinlayoutEmptyContainer) LinearLayout LinlayoutEmptyContainer;
    @BindView(R.id.LinLayoutList) LinearLayout LinLayoutList;
    @BindView(R.id.ImageViewLinLayout) CircleImageView ImageViewLinLayout;


    ArrayList<ChatDetailsPojo> pojo_listArrayList = new ArrayList<>();
    ChatDetailsPojo pojoList;

    ChatDetailsAdapter adapter;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String id,chat_id,user_id,type,message,status,created_at,ChatId,delivered_at,read_at;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.layout_chat_details, container, false);
        ButterKnife.bind(this,view);
        Fabric.with(getActivity(), new Crashlytics());


        final View rootView = view.findViewById(R.id.root_view);

        Bundle bundle = getArguments();

        TripManagement.saveValueuToPreferences(getActivity(),"LoadScanResFromChatDetails","LoadScanResFromChatDetails");


        Typeface montserrat_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Montserrat-Regular.ttf");
         if (bundle!=null)
         {
             if(bundle.containsKey("ProfileFromList"))
             {
                 Log.e("ABC","ABC");
                 avatar = getArguments().getString("ProfileFromList");
                 TripManagement.saveValueuToPreferences(getActivity(),"avatarFilePreview",avatar);

                 String name = getArguments().getString("NameFromList");
                 String unique_id = getArguments().getString("uniqueIDFromList");

                 ChatId = getArguments().getString("ChatId");
                 Log.e("ChatId",ChatId);

                 this.name.setText(name);
                 Log.e("Name",name);
                 this.unique_id.setText(unique_id);

                 Picasso.with(getActivity()).load(avatar).into(ProfileImageview);
                 Picasso.with(getActivity()).load(TripManagement.readValueFromPreferences(getActivity(),"PHOTOFORHEADER","")).into(ImageViewLinLayout);

             }
             else
                 {
                     Log.e("DEF","DEF");
                 ChatId = getArguments().getString("ChatId");
                 avatar = getArguments().getString("Avatar");
                 String name1 = getArguments().getString("Name");
                 String unique_id1 = getArguments().getString("UniqueID");
                 name.setText(name1);
                 unique_id.setText(unique_id1);

                 Picasso.with(getActivity()).load(avatar).into(ProfileImageview);
                 Picasso.with(getActivity()).load(TripManagement.readValueFromPreferences(getActivity(),"PHOTOFORHEADER","")).into(ImageViewLinLayout);


             }



         }






        id_name.setText(TripManagement.readValueFromPreferences(getActivity(),"USERUNIIQUE_IDFORHEADER",""));
        NameLinLayout.setText(TripManagement.readValueFromPreferences(getActivity(),"USERNAMEFORHEADER",""));





        this.name.setTypeface(montserrat_regular);
        this.unique_id.setTypeface(montserrat_regular);
        MsgEditText.setTypeface(montserrat_regular);
        help.setTypeface(montserrat_regular);
        id_name.setTypeface(montserrat_regular);
        NameLinLayout.setTypeface(montserrat_regular);
        TvNoChatsyet.setTypeface(montserrat_regular);


        final EmojiconsPopup popup = new EmojiconsPopup(rootView, getActivity());
        popup.setSizeForSoftKeyboard();

        //If the emoji popup is dismissed, change emojiButton to smiley icon
        popup.setOnDismissListener(new PopupWindow.OnDismissListener()
        {

            @Override
            public void onDismiss() {
                changeEmojiKeyboardIcon(emojiButton, R.mipmap.smiley);
            }
        });

        popup.setOnSoftKeyboardOpenCloseListener(new EmojiconsPopup.OnSoftKeyboardOpenCloseListener() {

            @Override
            public void onKeyboardOpen(int keyBoardHeight)
            {

            }

            @Override
            public void onKeyboardClose()
            {
                if (popup.isShowing())
                    popup.dismiss();
            }
        });


        popup.setOnEmojiconClickedListener(new EmojiconGridView.OnEmojiconClickedListener() {

            @Override
            public void onEmojiconClicked(Emojicon emojicon) {
                if (MsgEditText == null || emojicon == null) {
                    return;
                }

                int start = MsgEditText.getSelectionStart();
                int end = MsgEditText.getSelectionEnd();
                if (start < 0) {
                    MsgEditText.append(emojicon.getEmoji());
                } else {
                    MsgEditText.getText().replace(Math.min(start, end),
                            Math.max(start, end), emojicon.getEmoji(), 0,
                            emojicon.getEmoji().length());
                }
            }
        });

        //On backspace clicked, emulate the KEYCODE_DEL key event
        popup.setOnEmojiconBackspaceClickedListener(new EmojiconsPopup.OnEmojiconBackspaceClickedListener() {

            @Override
            public void onEmojiconBackspaceClicked(View v) {
                KeyEvent event = new KeyEvent(
                        0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
                MsgEditText.dispatchKeyEvent(event);
            }
        });

        emojiButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
//                if (container.getVisibility()==View.VISIBLE)
//                {
//                    container.setVisibility(View.GONE);
//                }


                //If popup is not showing => emoji keyboard is not visible, we need to show it
                if (!popup.isShowing()) {

                    //If keyboard is visible, simply show the emoji popup
                    if (popup.isKeyBoardOpen()) {
                        popup.showAtBottom();
                        changeEmojiKeyboardIcon(emojiButton, R.drawable.ic_action_keyboard);
                    }

                    //else, open the text keyboard first and immediately after that show the emoji popup
                    else
                    {
                        MsgEditText.setFocusableInTouchMode(true);
                        MsgEditText.requestFocus();
                        popup.showAtBottomPending();
                        final InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                        inputMethodManager.showSoftInput(MsgEditText, InputMethodManager.SHOW_IMPLICIT);
                        changeEmojiKeyboardIcon(emojiButton, R.drawable.ic_action_keyboard);
                    }
                }

                //If popup is showing, simply dismiss it to show the undelying text keyboard
                else {
                    popup.dismiss();
                }
            }
        });


        getChatListURL();


        ReadReciepts();



        return view;
    }

    @OnClick(R.id.LinLayoutback)
    public void setLinLayoutback()
    {
        if (TripManagement.readValueFromPreferences(getActivity(),"QRCHAT","").equals("QRCHAT"))
        {   TripManagement.saveValueuToPreferences(getActivity(),"QRCHAT","");
            startActivity(new Intent(getActivity(),Qrcoderesult.class));
        }
        else if (TripManagement.readValueFromPreferences(getActivity(),"BackToScanFromShare","").equals("BackToScanFromShare"))
        {   TripManagement.saveValueuToPreferences(getActivity(),"BackToScanFromShare","");
            startActivity(new Intent(getActivity(),Qrcoderesult.class));
        }
        else
        {
            TripManagement.saveValueuToPreferences(getActivity(),"LoadScanResFromChatDetails","");
            ChatListFragment chatListFragment = new ChatListFragment();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            if (TripManagement.readValueFromPreferences(getActivity(),"PicturePreviewActivity","").equals("PicturePreviewActivity"))
            {
                TripManagement.saveValueuToPreferences(getActivity(),"PicturePreviewActivity","");
                fragmentTransaction.add(R.id.FramePicturePreview,chatListFragment);
            }
            else
            {
                fragmentTransaction.add(R.id.frame_content,chatListFragment);
            }

            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

    }

    private void changeEmojiKeyboardIcon(ImageView iconToBeChanged, int drawableResourceId)
    {
        iconToBeChanged.setImageResource(drawableResourceId);
    }

    @OnClick(R.id.LinLayoutGallery)
    public void setLinLayoutGallery()
    {
        choosePhotoFromGallary();
    }

    @OnClick(R.id.LinLayoutSend)
    public void setLinLayoutSend()
    {
        if (MsgEditText.getText().toString() != null && MsgEditText.length()>0)
        {

             String toServer = MsgEditText.getText().toString();
             toServerUnicodeEncoded = StringEscapeUtils.escapeJava(toServer);
             SendMassage();
        }

    }

    @OnClick(R.id.LinLayoutMike)
    public void setLinLayoutMike()
    {
        Toast.makeText(getActivity(), "Audio", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.LinLayoutCamera)
    public void setLinLayoutCamera()
    {
        Toast.makeText(getActivity(), "Camera", Toast.LENGTH_SHORT).show();
    }






    @Override
    public void onResume()
    {
        super.onResume();
        if(getView() == null)
        {
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
                    // startActivity(new Intent(getActivity(),PicturePreviewActivity.class));

                   getActivity().onBackPressed();

                    return true;
                }
                return false;
            }
        });
    }


    private void getChatListURL()
    {


        //getting the tag from the edittext
        //final String tags = editTextTags.getText().toString().trim();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String getchatListURL = TripManagement.BASE_URL+"chat/"+ChatId;
        Log.e("ChatDetailsURL",getchatListURL);

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, getchatListURL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        progressDialog.dismiss();
                        InputMethodManager inputMethodManager = (InputMethodManager)  getActivity().getSystemService(INPUT_METHOD_SERVICE);
                        if (getActivity().getCurrentFocus() != null)
                        {
                            inputMethodManager.hideSoftInputFromWindow( getActivity().getCurrentFocus().getWindowToken(), 0);
                        }                         JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            if (jsonObject.getInt(Keys.success) == 1)
                            {
                                Log.e("ChatDetailsURLResponce",jsonObject.toString());
                                JSONObject jsonObjectData = jsonObject.getJSONObject(Keys.data);
                                JSONArray jsonArray_conversation = jsonObjectData.getJSONArray(Keys.conversation);
                                if(jsonArray_conversation != null && jsonArray_conversation.length() > 0 )
                                {

                                    LinlayoutEmptyContainer.setVisibility(View.GONE);
                                    LinLayoutList.setVisibility(View.VISIBLE);

                                    for (int i=0;i<jsonArray_conversation.length();i++)
                                    {
                                        JSONObject jsonObject11 = jsonArray_conversation.getJSONObject(i);
                                         id = jsonObject11.getString(Keys.id);
                                         chat_id = jsonObject11.getString(Keys.chat_id);
                                         user_id = jsonObject11.getString(Keys.user_id);
                                         type = jsonObject11.getString(Keys.type);
                                         Log.e("kkkk",type);
                                         message = jsonObject11.getString(Keys.message);
                                        created_at = jsonObject11.isNull(Keys.created_at)? "":jsonObject11.getString(Keys.created_at);
                                        delivered_at = jsonObject11.isNull(Keys.delivered_at)? "":jsonObject11.getString(Keys.delivered_at);
                                        read_at = jsonObject11.isNull(Keys.read_at)? "":jsonObject11.getString(Keys.read_at);

                                        pojoList = new ChatDetailsPojo(id,chat_id,user_id,type,message,delivered_at,read_at,created_at);
                                        pojo_listArrayList.add(pojoList);
                                    }
                                    adapter = new ChatDetailsAdapter(getActivity(),pojo_listArrayList,id,chat_id,user_id,type,message,status,created_at);
                                    listview.setAdapter(adapter);


                                    if (pojo_listArrayList.size() > 8)
                                    {
                                        listview.setStackFromBottom(true);
                                    }

                                }
                                else
                                {
                                    LinlayoutEmptyContainer.setVisibility(View.VISIBLE);
                                    LinLayoutList.setVisibility(View.GONE);
                                }
                            }
                            else
                            {

                                JSONObject jsonObject_errors = jsonObject.getJSONObject(Keys.errors);
                                // JSONArray jsonArray_name = jsonObject_errors.getJSONArray(Keys.name);
                                Toast.makeText(getActivity(), ""+jsonObject_errors.getJSONArray(Keys.name).get(0), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        InputMethodManager inputMethodManager = (InputMethodManager)  getActivity().getSystemService(INPUT_METHOD_SERVICE);
                        if (getActivity().getCurrentFocus() != null)
                        {
                            inputMethodManager.hideSoftInputFromWindow( getActivity().getCurrentFocus().getWindowToken(), 0);
                        }                         Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                // params.put(Keys.name,UsernameTextview.getText().toString());

                return params;
            }

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Keys.Authorization,"Bearer "+TripManagement.readValueFromPreferences(getActivity(),"accesstoken",""));
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */

        };

        //adding the request to volley
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }


    private void SendMassage()
    {
        //getting the tag from the edittext
        //final String tags = editTextTags.getText().toString().trim();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String postchatURL = TripManagement.BASE_URL+"chat/"+ChatId;

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, postchatURL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        pojo_listArrayList.clear();
                        progressDialog.dismiss();
                        Log.d("StringRequest--->", String.valueOf(response));
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            Log.d("StringRequestdata--->", String.valueOf(response.data));
                            if (jsonObject.getInt(Keys.success) == 1)
                            {
                                InputMethodManager inputMethodManager = (InputMethodManager)  getActivity().getSystemService(INPUT_METHOD_SERVICE);
                                if (getActivity().getCurrentFocus() != null)
                                {
                                    inputMethodManager.hideSoftInputFromWindow( getActivity().getCurrentFocus().getWindowToken(), 0);
                                }

                                // JSONObject jsonObjectData = jsonObject.getJSONObject(Keys.data);
                                //JSONObject jsonObjectcnsersation = jsonObjectData.getJSONObject(Keys.conversation);


                                MsgEditText.setText("");

                                getChatListURL();



                            }
                            else if (jsonObject.getInt(Keys.success) == 0)
                            {
                                JSONObject jsonObject_errors = jsonObject.getJSONObject(Keys.errors);
                                Toast.makeText(getActivity(), ""+jsonObject_errors, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            Log.e("Exeption",e.toString());
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        InputMethodManager inputMethodManager = (InputMethodManager)  getActivity().getSystemService(INPUT_METHOD_SERVICE);
                        if (getActivity().getCurrentFocus() != null)
                        {
                            inputMethodManager.hideSoftInputFromWindow( getActivity().getCurrentFocus().getWindowToken(), 0);
                        }                         Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
               // params.put(Keys.message,MsgEditText.getText().toString());
                params.put(Keys.message,toServerUnicodeEncoded);
                params.put(Keys.type,"0");

                return params;
            }

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Keys.Authorization,"Bearer "+TripManagement.readValueFromPreferences(getActivity(),"accesstoken",""));
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData()
            {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                //  params.put(Keys.avatar, new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }

    public void  choosePhotoFromGallary()
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
       // super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY)
        {
            Bitmap bitmap = null;
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    if (contentURI != null)
                    {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);

                    }
                    if(bitmap!=null)
                    {

                        String path = saveImage(bitmap);
                        FilePreviewFragment filePreviewFragment = new FilePreviewFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("Image",path);
                        bundle.putString("ChatId",ChatId);
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.frame_content,filePreviewFragment);
                        fragmentTransaction.addToBackStack(null);
                        filePreviewFragment.setArguments(bundle);
                        fragmentTransaction.commit();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ProfileImageview.setImageBitmap(thumbnail);
            saveImage(thumbnail);

           // Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


    private void ReadReciepts()
    {


        String readRecieptURL = TripManagement.BASE_URL+"chat/"+ChatId+"/read_receipt";

        Log.e("ReadRecieptURL",readRecieptURL);

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, readRecieptURL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            if (jsonObject.getInt(Keys.success) == 1)
                            {
                                InputMethodManager inputMethodManager = (InputMethodManager)  getActivity().getSystemService(INPUT_METHOD_SERVICE);
                                if (getActivity().getCurrentFocus() != null)
                                {
                                    inputMethodManager.hideSoftInputFromWindow( getActivity().getCurrentFocus().getWindowToken(), 0);

                                }
                            }
                            else if (jsonObject.getInt(Keys.success) == 0)
                            {
                                Toast.makeText(getActivity(), ""+jsonObject.getString(Keys.message), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        InputMethodManager inputMethodManager = (InputMethodManager)  getActivity().getSystemService(INPUT_METHOD_SERVICE);
                        if (getActivity().getCurrentFocus() != null)
                        {
                            inputMethodManager.hideSoftInputFromWindow( getActivity().getCurrentFocus().getWindowToken(), 0);
                        }                         Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                // params.put(Keys.message,MsgEditText.getText().toString());
                // params.put(Keys.message,toServerUnicodeEncoded);
                // params.put(Keys.type,"0");

                return params;
            }

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Keys.Authorization,"Bearer "+TripManagement.readValueFromPreferences(getActivity(),"accesstoken",""));
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData()
            {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                //  params.put(Keys.avatar, new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }


}
