package com.angadi.tripmanagementa.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.angadi.tripmanagementa.Activities.ForgotUniqueIDActivity;
import com.angadi.tripmanagementa.Activities.HomeActivity;
import com.angadi.tripmanagementa.Activities.LoginActivity;
import com.angadi.tripmanagementa.Activities.Qrcoderesult;
import com.angadi.tripmanagementa.Adapters.CustomListAdapter;
import com.angadi.tripmanagementa.Adapters.ProductListAdapter;
import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.Extras.Keys;
import com.angadi.tripmanagementa.Extras.VolleyMultipartRequest;
import com.angadi.tripmanagementa.Model.ChatListPojo;
import com.angadi.tripmanagementa.Model.Product;
import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.fabric.sdk.android.Fabric;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class ChatListFragment extends Fragment {

    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String chatIdFromCreateAPI;

    TextView TVClose, TV_Invali;
    Dialog dialog_invalid_qr;
    LinearLayout LinLayoutClose;
    String hold;
    int selectedcontact = 0;


    CustomListAdapter adapter;

    private static final int UNBOUNDED = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);


    ArrayList<ChatListPojo> pojo_listArrayList = new ArrayList<>();
    ChatListPojo pojoList;
    String id, chat_id, id_recipient, status, created_at, updated_at, recipient, unique_id, name, avatar, Msg_conversation, createdAt_conversation, Type_conversation, delivered_at, read_at;

    ListView listview;


    @BindView(R.id.LinLayoutBack) LinearLayout LinLayoutBack;
    @BindView(R.id.LinLayoutFindUserResult) LinearLayout LinLayoutFindUserResult;
    @BindView(R.id.LinlayoutSearch)LinearLayout LinlayoutSearch;
    @BindView(R.id.ClickedPrivateLinlayout) LinearLayout ClickedPrivateLinlayout;
    @BindView(R.id.UnclickedPrivateLinlayout) LinearLayout UnclickedPrivateLinlayout;
    @BindView(R.id.ClickedFamilyLinlayout) LinearLayout ClickedFamilyLinlayout;
    @BindView(R.id.UnClickedLinlayoutFamily)
    LinearLayout UnClickedLinlayoutFamily;
    @BindView(R.id.ClickedOfficialLinlayout)
    LinearLayout ClickedOfficialLinlayout;
    @BindView(R.id.UnClickedOfficialLinLayout)
    LinearLayout UnClickedOfficialLinLayout;
    @BindView(R.id.UnClickedPubliclayout)
    LinearLayout UnClickedPubliclayout;
    @BindView(R.id.ClickedPublicLayout)
    LinearLayout ClickedPublicLayout;
    @BindView(R.id.EDittextSearch)
    EditText EDittextSearch;
    @BindView(R.id.LinlayoutCancel)
    LinearLayout LinlayoutCancel;
    @BindView(R.id.ImageviewCancel)
    ImageView ImageviewCancel;
    @BindView(R.id.NameLinLayout)
    TextView NameLinLayout;
    @BindView(R.id.id_name)
    TextView id_name;
    @BindView(R.id.clickedPrivateTV)
    TextView clickedPrivateTV;
    @BindView(R.id.unclickedPrivateTV)
    TextView unclickedPrivateTV;
    @BindView(R.id.Unclickedfamily)
    TextView Unclickedfamily;
    @BindView(R.id.clickedfamily)
    TextView clickedfamily;
    @BindView(R.id.Unclickedofficial)
    TextView Unclickedofficial;
    @BindView(R.id.clickedoffiacial)
    TextView clickedoffiacial;
    @BindView(R.id.unclickedpublic)
    TextView unclickedpublic;
    @BindView(R.id.clickedpublic)
    TextView clickedpublic;
    @BindView(R.id.TextviewEmptyContainer)
    TextView TextviewEmptyContainer;
    @BindView(R.id.TVnameForList)
    TextView TVnameForList;
    @BindView(R.id.TvUniqueIDlist)
    TextView TvUniqueIDlist;
    @BindView(R.id.LinlayoutEmptyContainer)
    LinearLayout LinlayoutEmptyContainer;
    @BindView(R.id.LinLayoutList)
    LinearLayout LinLayoutList;
    @BindView(R.id.LinLayoutFindUser)
    LinearLayout LinLayoutFindUser;
    @BindView(R.id.FindUseravatar)
    CircleImageView FindUseravatar;
    @BindView(R.id.ImageViewLinLayout)
    CircleImageView ImageViewLinLayout;
    @BindView(R.id.LinlayoutSearchlayout)
    LinearLayout LinlayoutSearchlayout;
    @BindView(R.id.LinlayoutSendlayout)
    LinearLayout LinlayoutSendlayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_chatlist, container, false);
        ButterKnife.bind(this, view);
        Fabric.with(getActivity(), new Crashlytics());
        listview = (ListView) view.findViewById(R.id.listview);

        Typeface montserrat_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Montserrat-Regular.ttf");


        NameLinLayout.setTypeface(montserrat_regular);
        id_name.setTypeface(montserrat_regular);
        clickedPrivateTV.setTypeface(montserrat_regular);
        unclickedPrivateTV.setTypeface(montserrat_regular);
        Unclickedfamily.setTypeface(montserrat_regular);
        clickedfamily.setTypeface(montserrat_regular);
        Unclickedofficial.setTypeface(montserrat_regular);
        clickedoffiacial.setTypeface(montserrat_regular);
        unclickedpublic.setTypeface(montserrat_regular);
        clickedpublic.setTypeface(montserrat_regular);
        EDittextSearch.setTypeface(montserrat_regular);
        TextviewEmptyContainer.setTypeface(montserrat_regular);

        Picasso.with(getActivity()).load(TripManagement.readValueFromPreferences(getActivity(), "PHOTOFORHEADER", "")).into(ImageViewLinLayout);


        id_name.setText(TripManagement.readValueFromPreferences(getActivity(), "USERUNIIQUE_IDFORHEADER", ""));
        NameLinLayout.setText(TripManagement.readValueFromPreferences(getActivity(), "USERNAMEFORHEADER", ""));


        dialog_invalid_qr = new Dialog(getActivity());
        dialog_invalid_qr.setContentView(R.layout.layout_alert_dialog);

        TVClose = (TextView) dialog_invalid_qr.findViewById(R.id.TVClose);
        TV_Invali = (TextView) dialog_invalid_qr.findViewById(R.id.TV_Invali);
        LinLayoutClose = (LinearLayout) dialog_invalid_qr.findViewById(R.id.LinLayoutClose);
        TVClose.setTypeface(montserrat_regular);
        TV_Invali.setTypeface(montserrat_regular);
        dialog_invalid_qr.setCanceledOnTouchOutside(false);
        LinLayoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_invalid_qr.dismiss();

            }
        });

        hold = "something";


        if (TripManagement.readValueFromPreferences(getActivity(), "ShareScanResuldImages", "").equals("ShareScanResuldImages"))
        {
            LinlayoutSearchlayout.setVisibility(View.GONE);
            LinlayoutSendlayout.setVisibility(View.VISIBLE);

            hold = TripManagement.readValueFromPreferences(getActivity(), "ShareScanResuldImages", "");

        }





        getChatListURL();


        return view;
    }

    @OnClick(R.id.LinLayoutBack)
    public void setLinLayoutBack() {

        startActivity(new Intent(getActivity(), Qrcoderesult.class));
    }

    @OnClick(R.id.LinlayoutSearch)
    public void setLinlayoutSearch() {
        if (!EDittextSearch.getText().toString().isEmpty()) {
            FindForUser();
        } else {

            TV_Invali.setText("Please enter the Unique ID");
            dialog_invalid_qr.show();
        }

    }

    @OnClick(R.id.ClickedPrivateLinlayout)
    public void setClickedPrivateLinlayout() {
        if (ClickedPrivateLinlayout.getVisibility() == View.VISIBLE) {
            ClickedPrivateLinlayout.setVisibility(View.GONE);
            UnclickedPrivateLinlayout.setVisibility(View.VISIBLE);
        } else {
            ClickedPrivateLinlayout.setVisibility(View.VISIBLE);
            UnclickedPrivateLinlayout.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.UnclickedPrivateLinlayout)
    public void setUnclickedPrivateLinlayout() {
        if (ClickedPrivateLinlayout.getVisibility() == View.VISIBLE) {

            ClickedPrivateLinlayout.setVisibility(View.GONE);
            UnclickedPrivateLinlayout.setVisibility(View.VISIBLE);
        } else {
            if (ClickedFamilyLinlayout.getVisibility() == View.VISIBLE || ClickedOfficialLinlayout.getVisibility() == View.VISIBLE || ClickedPublicLayout.getVisibility() == View.VISIBLE) {
                ClickedFamilyLinlayout.setVisibility(View.GONE);
                ClickedOfficialLinlayout.setVisibility(View.VISIBLE);

                UnClickedLinlayoutFamily.setVisibility(View.VISIBLE);
                UnClickedOfficialLinLayout.setVisibility(View.VISIBLE);

                ClickedPublicLayout.setVisibility(View.GONE);
                UnClickedPubliclayout.setVisibility(View.VISIBLE);
            }
            ClickedPrivateLinlayout.setVisibility(View.VISIBLE);
            UnclickedPrivateLinlayout.setVisibility(View.GONE);
        }
    }


    @OnClick(R.id.UnClickedLinlayoutFamily)
    public void setUnClickedLinlayoutFamily() {
        if (UnClickedLinlayoutFamily.getVisibility() == View.VISIBLE) {
            if (ClickedOfficialLinlayout.getVisibility() == View.VISIBLE) {
                ClickedOfficialLinlayout.setVisibility(View.GONE);
                UnClickedOfficialLinLayout.setVisibility(View.VISIBLE);
            }
            UnClickedLinlayoutFamily.setVisibility(View.GONE);
            ClickedFamilyLinlayout.setVisibility(View.VISIBLE);


        } else {
            UnClickedLinlayoutFamily.setVisibility(View.VISIBLE);
            ClickedFamilyLinlayout.setVisibility(View.GONE);


        }
    }


    @OnClick(R.id.ClickedFamilyLinlayout)
    public void setClickedFamilyLinlayout() {
        if (ClickedFamilyLinlayout.getVisibility() == View.VISIBLE)
        {
            ClickedFamilyLinlayout.setVisibility(View.GONE);
            UnClickedLinlayoutFamily.setVisibility(View.VISIBLE);
        } else
            {
            ClickedFamilyLinlayout.setVisibility(View.VISIBLE);
            UnClickedLinlayoutFamily.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.UnClickedLinlayoutFamily)
    public void setUnClickedLinlayoutFamily1() {
        if (ClickedPrivateLinlayout.getVisibility() == View.VISIBLE) {
            ClickedPrivateLinlayout.setVisibility(View.GONE);
            UnclickedPrivateLinlayout.setVisibility(View.VISIBLE);
        } else {
            if (ClickedPublicLayout.getVisibility() == View.VISIBLE) {
                ClickedPublicLayout.setVisibility(View.GONE);
                UnClickedPubliclayout.setVisibility(View.VISIBLE);
            }
        }


    }

    @OnClick(R.id.UnclickedPrivateLinlayout)
    public void setUnclickedPrivateLinlayout1() {
        if (ClickedFamilyLinlayout.getVisibility() == View.VISIBLE) {
            UnClickedLinlayoutFamily.setVisibility(View.VISIBLE);
            ClickedFamilyLinlayout.setVisibility(View.GONE);
        }

    }

    @OnClick(R.id.UnClickedOfficialLinLayout)
    public void serUnClickedOfficialLinLayout() {
        if (UnClickedOfficialLinLayout.getVisibility() == View.VISIBLE) {
            if (ClickedPrivateLinlayout.getVisibility() == View.VISIBLE || ClickedFamilyLinlayout.getVisibility() == View.VISIBLE || ClickedPublicLayout.getVisibility() == View.VISIBLE) {
                ClickedPrivateLinlayout.setVisibility(View.GONE);
                ClickedFamilyLinlayout.setVisibility(View.GONE);
                UnclickedPrivateLinlayout.setVisibility(View.VISIBLE);
                UnClickedLinlayoutFamily.setVisibility(View.VISIBLE);

                ClickedPublicLayout.setVisibility(View.GONE);
                UnClickedPubliclayout.setVisibility(View.VISIBLE);

            }
            UnClickedOfficialLinLayout.setVisibility(View.GONE);
            ClickedOfficialLinlayout.setVisibility(View.VISIBLE);
        } else {
            UnClickedOfficialLinLayout.setVisibility(View.VISIBLE);
            ClickedOfficialLinlayout.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.ClickedOfficialLinlayout)
    public void setClickedOfficialLinlayout() {
        if (ClickedOfficialLinlayout.getVisibility() == View.VISIBLE) {
            ClickedOfficialLinlayout.setVisibility(View.GONE);
            UnClickedOfficialLinLayout.setVisibility(View.VISIBLE);
        }
    }


    @OnClick(R.id.UnClickedPubliclayout)
    public void setUnClickedPubliclayout() {
        if (ClickedPublicLayout.getVisibility() == View.VISIBLE) {
            UnClickedPubliclayout.setVisibility(View.VISIBLE);
            ClickedPublicLayout.setVisibility(View.GONE);
        } else {
            if (ClickedPrivateLinlayout.getVisibility() == View.VISIBLE || ClickedFamilyLinlayout.getVisibility() == View.VISIBLE || ClickedOfficialLinlayout.getVisibility() == View.VISIBLE) {
                ClickedPrivateLinlayout.setVisibility(View.GONE);
                UnclickedPrivateLinlayout.setVisibility(View.VISIBLE);

                ClickedFamilyLinlayout.setVisibility(View.GONE);
                UnClickedLinlayoutFamily.setVisibility(View.VISIBLE);

                ClickedOfficialLinlayout.setVisibility(View.GONE);
                UnClickedOfficialLinLayout.setVisibility(View.VISIBLE);
            }
            UnClickedPubliclayout.setVisibility(View.GONE);
            ClickedPublicLayout.setVisibility(View.VISIBLE);

        }

    }

    @OnClick(R.id.ClickedPublicLayout)
    public void setClickedPublicLayout() {
        if (ClickedPublicLayout.getVisibility() == View.VISIBLE) {
            ClickedPublicLayout.setVisibility(View.GONE);
            UnClickedPubliclayout.setVisibility(View.VISIBLE);

        }

    }

    @OnClick(R.id.LinlayoutCancel)
    public void setLinlayoutCancel() {
        LinlayoutCancel.setVisibility(View.GONE);
        LinlayoutSearch.setVisibility(View.VISIBLE);
        EDittextSearch.setText("");
    }


    private void getChatListURL()
    {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String getchatListURL = TripManagement.BASE_URL +"chat";
        Log.e("GetChatListURL", getchatListURL);

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, getchatListURL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        progressDialog.dismiss();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            Log.e("GetChatListURL", jsonObject.toString());
                            if (jsonObject.getInt(Keys.success) == 1) {

                                JSONObject jsonObjectData = jsonObject.getJSONObject(Keys.data);
                                JSONArray jsonArray_chat = jsonObjectData.getJSONArray(Keys.chat);
                                Log.e("GetChatListURL", jsonObjectData.toString());

                                if (jsonArray_chat != null && jsonArray_chat.length() > 0) {

                                    LinLayoutList.setVisibility(View.VISIBLE);
                                    LinLayoutFindUser.setVisibility(View.GONE);
                                    LinlayoutEmptyContainer.setVisibility(View.GONE);

                                    for (int i = 0; i < jsonArray_chat.length(); i++) {
                                        JSONObject jsonObjectChat = jsonArray_chat.getJSONObject(i);
                                        id = jsonObjectChat.getString(Keys.id);
                                        status = jsonObjectChat.getString(Keys.status);
                                        created_at = jsonObjectChat.isNull(Keys.created_at) ? "" : jsonObjectChat.getString(Keys.created_at);
                                        updated_at = jsonObjectChat.isNull(Keys.updated_at) ? "" : jsonObjectChat.getString(Keys.updated_at);


                                        JSONObject jsonObjec_recipient = jsonObjectChat.getJSONObject(Keys.recipient);

                                        id_recipient = jsonObjec_recipient.getString(Keys.id);
                                        unique_id = jsonObjec_recipient.getString(Keys.unique_id);
                                        name = jsonObjec_recipient.getString(Keys.name);
                                        avatar = jsonObjec_recipient.getString(Keys.avatar);

                                        //start of conversation object

                                        JSONObject jsonconversation = null;
                                        try {

                                            jsonconversation = jsonObjectChat.getJSONObject(Keys.conversation);
                                            Msg_conversation = jsonconversation.getString(Keys.message);
                                            Log.d("HHHH", Msg_conversation);
                                            Log.e("HHHH-->", Msg_conversation);
                                            createdAt_conversation = jsonconversation.getString(Keys.created_at);
                                            Type_conversation = jsonconversation.getString(Keys.type);
                                            read_at = jsonconversation.isNull(Keys.read_at) ? "" : jsonconversation.getString(Keys.type);
                                        } catch (JSONException e) {

                                            e.printStackTrace();
                                        }

                                        //end of conversation object

                                        if (avatar != null) {
                                            Picasso.with(getActivity()).load(avatar).into(FindUseravatar);

                                        }
                                        pojoList = new ChatListPojo(id, id_recipient, created_at, updated_at, recipient, unique_id, name, avatar, Msg_conversation, delivered_at, read_at, createdAt_conversation, Type_conversation);
                                        pojo_listArrayList.add(pojoList);
                                    }

                                    Bundle bundle = getArguments();
                                    String image = "";
                                    if (bundle!=null)
                                    {
                                        image = getArguments().getString("BitmapImageShare") ;
                                        Log.e("Image-->",image);
                                    }
                                    else
                                    {
                                        Log.e("Image-null","Image-null");
                                    }



                                    adapter = new CustomListAdapter(getActivity(), pojo_listArrayList, id, created_at, updated_at, recipient, unique_id, name, avatar, Msg_conversation, delivered_at, read_at, createdAt_conversation, Type_conversation, hold,image);
                                    listview.setAdapter(adapter);

                                    if (!TripManagement.readValueFromPreferences(getActivity(), "ShareScanResuldImages", "").equals("ShareScanResuldImages")) {


                                        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                ChatDetailsFragment chatDetailsFragment = new ChatDetailsFragment();
                                                Bundle bundle = new Bundle();
                                                bundle.putString("ProfileFromList", pojo_listArrayList.get(i).getAvatar());
                                                bundle.putString("NameFromList", pojo_listArrayList.get(i).getName());
                                                bundle.putString("uniqueIDFromList", pojo_listArrayList.get(i).getUnique_id());
                                                bundle.putString("ChatId", pojo_listArrayList.get(i).getId());
                                                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                                fragmentTransaction.add(R.id.frame_content, chatDetailsFragment);
                                                chatDetailsFragment.setArguments(bundle);
                                                fragmentTransaction.addToBackStack(null);
                                                fragmentTransaction.commit();
                                            }

                                        });
                                    } else if (TripManagement.readValueFromPreferences(getActivity(), "ShareScanResuldImages", "").equals("ShareScanResuldImages")) {
                                        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                pojo_listArrayList.get(position).setSelected(!pojo_listArrayList.get(position).isSelected());
                                                adapter.notifyDataSetChanged();


                                            }
                                        });

                                    }

                                } else {
                                    LinLayoutList.setVisibility(View.GONE);
                                    LinlayoutEmptyContainer.setVisibility(View.VISIBLE);
                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                            Log.d("ChatListExeption", e.toString());
                            Log.e("ChatListExeption", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // params.put(Keys.name,UsernameTextview.getText().toString());

                return params;
            }

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Keys.Authorization, "Bearer " + TripManagement.readValueFromPreferences(getActivity(), "accesstoken", ""));
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */

        };

        //adding the request to volley
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }


    private void FindForUser() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String FindUserURL = TripManagement.BASE_URL + "chat/user/" + EDittextSearch.getText().toString();

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, FindUserURL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        progressDialog.dismiss();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            if (jsonObject.getInt(Keys.success) == 1) {
                                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                                if (getActivity().getCurrentFocus() != null) {
                                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                                }
                                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                Log.e("ResponceFind---->", jsonObject.toString());
                                final JSONObject jsonObjectData = jsonObject.getJSONObject(Keys.data);
                                Log.e("ResponceFind123---->", jsonObjectData.toString());

                                JSONObject jsonObject_User = jsonObjectData.getJSONObject(Keys.user);

                                id = jsonObject_User.isNull(Keys.id) ? "" : jsonObject_User.getString(Keys.id);
                                final String unique_idFromFindUser = jsonObject_User.isNull(Keys.unique_id) ? "" : jsonObject_User.getString(Keys.unique_id);
                                final String nameFromFindUser = jsonObject_User.isNull(Keys.name) ? "" : jsonObject_User.getString(Keys.name);
                                final String avatarFromFindUser = jsonObject_User.isNull(Keys.avatar) ? "" : jsonObject_User.getString(Keys.avatar);


                                LinLayoutFindUserResult.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (jsonObjectData.has(Keys.chat) && !jsonObjectData.isNull(Keys.chat)) {
                                            try {
                                                JSONObject jsonObject_chat = jsonObjectData.getJSONObject(Keys.chat);
                                                chat_id = jsonObject_chat.getString(Keys.id);

                                                ChatDetailsFragment chatDetailsFragment = new ChatDetailsFragment();
                                                Bundle bundle = new Bundle();
                                                bundle.putString("ChatId", chat_id);
                                                bundle.putString("ProfileFromList", avatarFromFindUser);
                                                bundle.putString("uniqueIDFromList", unique_idFromFindUser);
                                                bundle.putString("NameFromList", nameFromFindUser);

                                                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                                fragmentTransaction.add(R.id.frame_content, chatDetailsFragment);
                                                fragmentTransaction.addToBackStack(null);
                                                chatDetailsFragment.setArguments(bundle);
                                                fragmentTransaction.commit();

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                Log.e("Exeption1", e.toString());
                                            }
                                        } else {
                                            //call create API here

                                            CreateChatURL();


                                        }

                                    }
                                });


                                LinLayoutFindUser.setVisibility(View.VISIBLE);
                                LinlayoutEmptyContainer.setVisibility(View.GONE);
                                LinLayoutList.setVisibility(View.GONE);

                                TVnameForList.setText(nameFromFindUser);
                                TvUniqueIDlist.setText(unique_idFromFindUser);

                                Picasso.with(getActivity()).load(avatarFromFindUser).into(FindUseravatar);


                            } else if (jsonObject.getInt(Keys.success) == 0) {
                                TV_Invali.setText(jsonObject.getString(Keys.message));
                                dialog_invalid_qr.show();

                                // Toast.makeText(getActivity(), "" + jsonObject.getString(Keys.message), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                            Log.e("Exeption", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                        if (getActivity().getCurrentFocus() != null) {
                            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                        }
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                // params.put(Keys.name,UsernameTextview.getText().toString());

                return params;
            }

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Keys.Authorization, "Bearer " + TripManagement.readValueFromPreferences(getActivity(), "accesstoken", ""));
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */

        };

        //adding the request to volley
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }

    private void CreateChatURL() {
        //getting the tag from the edittext
        //final String tags = editTextTags.getText().toString().trim();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String CreateUserURL = TripManagement.BASE_URL + "chat";

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, CreateUserURL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        progressDialog.dismiss();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            if (jsonObject.getInt(Keys.success) == 1) {
                                JSONObject jsonObject_data = jsonObject.getJSONObject(Keys.data);

                                JSONObject jsonObject_chat = jsonObject_data.getJSONObject(Keys.chat);


                                chatIdFromCreateAPI = jsonObject_chat.getString(Keys.id);
                                String status = jsonObject_chat.getString(Keys.status);
                                String created_at = jsonObject_chat.getString(Keys.created_at);
                                String updated_at = jsonObject_chat.getString(Keys.updated_at);

                                JSONObject jsonObject_recipient = jsonObject_chat.getJSONObject(Keys.recipient);
                                String Recipient = jsonObject_recipient.getString(Keys.id);
                                String uniqueIdFromCreateAPI = jsonObject_recipient.getString(Keys.unique_id);
                                String nameFromCreateAPI = jsonObject_recipient.getString(Keys.name);
                                String avatarFromCreateAPI = jsonObject_recipient.getString(Keys.avatar);


                                ChatDetailsFragment chatDetailsFragment = new ChatDetailsFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("ChatId", chatIdFromCreateAPI);
                                bundle.putString("ProfileFromList", avatarFromCreateAPI);
                                bundle.putString("uniqueIDFromList", uniqueIdFromCreateAPI);
                                bundle.putString("NameFromList", nameFromCreateAPI);

                                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.add(R.id.frame_content, chatDetailsFragment);
                                fragmentTransaction.addToBackStack(null);
                                chatDetailsFragment.setArguments(bundle);
                                fragmentTransaction.commit();
                            } else if (jsonObject.getInt(Keys.success) == 0) {
                                JSONObject jsonObject_errors = jsonObject.getJSONObject(Keys.errors);
                                // JSONArray jsonArray_name = jsonObject_errors.getJSONArray(Keys.recipient_user_id);

                                TV_Invali.setText(jsonObject_errors.getJSONArray(Keys.recipient_user_id).get(0).toString());
                                dialog_invalid_qr.show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Exeption2", e.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Keys.recipient_user_id, id);

                return params;
            }

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Keys.Authorization, "Bearer " + TripManagement.readValueFromPreferences(getActivity(), "accesstoken", ""));
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */

        };

        //adding the request to volley
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (getView() == null) {
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
                    startActivity(new Intent(getActivity(), Qrcoderesult.class));
                    return true;
                }
                return false;
            }
        });
    }


}