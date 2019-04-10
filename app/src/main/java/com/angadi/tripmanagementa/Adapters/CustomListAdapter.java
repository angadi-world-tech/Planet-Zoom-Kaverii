package com.angadi.tripmanagementa.Adapters;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.Extras.Keys;
import com.angadi.tripmanagementa.Extras.VolleyMultipartRequest;
import com.angadi.tripmanagementa.Fragments.ChatDetailsFragment;
import com.angadi.tripmanagementa.Fragments.ChatListFragment;
import com.angadi.tripmanagementa.Model.ChatListPojo;
import com.angadi.tripmanagementa.Model.Product;
import com.angadi.tripmanagementa.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.angadi.tripmanagementa.Activities.HomeActivity.BLACK;
import static com.angadi.tripmanagementa.Activities.HomeActivity.HEIGHT;
import static com.angadi.tripmanagementa.Activities.HomeActivity.WHITE;
import static com.angadi.tripmanagementa.Activities.HomeActivity.WIDTH;

public class CustomListAdapter  extends BaseAdapter
{
        Context context;
        ProgressDialog progressDialog;
        LinearLayout LinlayoutSearch,LinlayoutCancel,linlayoutSearch,LinLayoutSenderSeen;
        EditText EDittextSearch;
        ImageView ImageviewCancel;
        String hold,imagefromURL;
       int selectedcontact;
        ArrayList<ChatListPojo> pojo_listArrayList;
        LinearLayout LinLayoutOnline;
       LinearLayout  LinlayoutSend;
       LayoutInflater layoutInflater;
       String id,created_at,updated_at,recipient,unique_id,name,avatar,Msg_conversation,created_conversation,Type_conversation,delevered_at,read_at;





    public CustomListAdapter(Context context, ArrayList<ChatListPojo> pojo_listArrayList,
                             String id, String created_at, String updated_at,
                             String recipient, String unique_id, String name, String avatar, String Msg_conversation,
                             String deliverd_at,
                             String read_at, String created_conversation,
                             String type_conversation, String hold, String imagefromURL)
        {

            this.context = context;
            this.pojo_listArrayList = pojo_listArrayList;
            this.layoutInflater = LayoutInflater.from(context);
            this.id = id;
            this.created_at = created_at;
            this.updated_at = updated_at;
            this.recipient = recipient;
            this.unique_id = unique_id;
            this.name = name;
            this.avatar = avatar;
            this.Msg_conversation = Msg_conversation;
            this.delevered_at = deliverd_at;
            this.read_at = read_at;
            this.created_conversation = created_conversation;
            this.Type_conversation = type_conversation;
            this.imagefromURL = imagefromURL;

            this.hold = hold;

//            this.ImageviewCancel = ImageviewCancel;
//            this.LinlayoutCancel = LinlayoutCancel;
//            this.linlayoutSearch = linlayoutSearch;
//            this. EDittextSearch = EDittextSearch;
        }

    @Override
    public int getCount() {
        return pojo_listArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {



                convertView = layoutInflater.inflate(R.layout.layout_listview_item, null);
                Typeface montserrat_regular = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Regular.ttf");

               TextView  TVname = (TextView) convertView.findViewById(R.id.TVname);
               TextView  TvMsg = (TextView) convertView.findViewById(R.id.TvMsg);
               TextView TVSend = (TextView) convertView.findViewById(R.id.TVSend);
               CircleImageView ImageviewList = (CircleImageView) convertView.findViewById(R.id.ImageviewList);

               LinLayoutSenderSeen = (LinearLayout)convertView.findViewById(R.id.LinLayoutSenderSeen);
               LinlayoutSend = (LinearLayout)convertView.findViewById(R.id.LinlayoutSend);


                TVname.setTypeface(montserrat_regular);
                TvMsg.setTypeface(montserrat_regular);

                TVname.setText(pojo_listArrayList.get(position).getName());

                if (pojo_listArrayList.get(position).getType_conversation() !=null)
                {

                    if (pojo_listArrayList.get(position).getType_conversation().matches("1"))
                    {
                        TvMsg.setText("Photo");
                    }
                    else
                    {
                        String fromServerUnicodeDecoded = StringEscapeUtils.unescapeJava(pojo_listArrayList.get(position).getMsg_conversation());
                        TvMsg.setText(fromServerUnicodeDecoded);
                    }


                    if (pojo_listArrayList.get(position).getRead_at() != "")
                    {
                        LinLayoutSenderSeen.setVisibility(View.VISIBLE);
                    }

                }

                if (pojo_listArrayList.get(position).getAvatar() != null)
                {
                    Picasso.with(context).load(pojo_listArrayList.get(position).getAvatar()).into(ImageviewList);

                }

                if (hold.equals("ShareScanResuldImages"))
                {

                    LinlayoutSend.setVisibility(pojo_listArrayList.get(position).isSelected()?View.VISIBLE:View.GONE);

                }


            LinlayoutSend.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    try
                    {
                        Bitmap bitmap = encodeAsBitmap(imagefromURL);
                        SendMassage(bitmap,pojo_listArrayList.get(position).getName(),pojo_listArrayList.get(position).getAvatar(),pojo_listArrayList.get(position).getUnique_id());
                        // myImage.setImageBitmap(bitmap);
                    } catch (WriterException e)
                    {
                        e.printStackTrace();
                        Log.e("Exeption_QR",e.toString());
                    }










                }
            });

                return convertView;


        }
    private void SendMassage(final Bitmap bitmap, final String nameForsubHeader,final String avatarForsubHeader,final String uniqueIdForsubHeader)
    {
        //getting the tag from the edittext
        //final String tags = editTextTags.getText().toString().trim();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String postchatURL = TripManagement.BASE_URL+"chat/"+id;
        Log.e("PreviewFile",postchatURL);

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, postchatURL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        //  pojo_listArrayList.clear();
                        progressDialog.dismiss();
                        Log.d("StringRequest--->", String.valueOf(response));
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            Log.d("jsonObjectFreciew--->", jsonObject.toString());
                            if (jsonObject.getInt(Keys.success) == 1)
                            {
                                Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                                LinlayoutSend.setVisibility(View.GONE);

                                JSONObject jsonObjectData = jsonObject.getJSONObject(Keys.data);
                                JSONObject jsonObjectcnsersation = jsonObjectData.getJSONObject(Keys.conversation);

                                if (TripManagement.readValueFromPreferences(context,"ShareParoductsDetials","").equals("ShareParoductsDetials"))
                                {
                                    Log.e("k1","k1");
                                    TripManagement.saveValueuToPreferences(context,"ShareParoductsDetials","");
                                    TripManagement.saveValueuToPreferences(context,"BackToScanFromShare","");
                                    TripManagement.saveValueuToPreferences(context,"BackafterUpdate","");

                                    if (TripManagement.readValueFromPreferences(context,"PicturePreviewActivity","").equals("PicturePreviewActivity"))
                                    {
                                        Log.e("k2","k2");
                                       // TripManagement.saveValueuToPreferences(context,"PicturePreviewActivity","");

                                        ChatDetailsFragment chatDetailsFragment = new ChatDetailsFragment();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("ChatId",id);
                                        bundle.putString("ProfileFromList",avatarForsubHeader);
                                        bundle.putString("NameFromList",nameForsubHeader);
                                        bundle.putString("uniqueIDFromList",uniqueIdForsubHeader);
                                        FragmentTransaction fragmentTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.add(R.id.FramePicturePreview, chatDetailsFragment);
                                        fragmentTransaction.addToBackStack(null);
                                        chatDetailsFragment.setArguments(bundle);
                                        fragmentTransaction.commit();

                                    }
                                    else
                                    {
                                        Log.e("k3","k3");
                                        ChatDetailsFragment chatDetailsFragment = new ChatDetailsFragment();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("ChatId",id);
                                        bundle.putString("ProfileFromList",avatarForsubHeader);
                                        bundle.putString("NameFromList",nameForsubHeader);
                                        bundle.putString("uniqueIDFromList",uniqueIdForsubHeader);
                                        FragmentTransaction fragmentTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.add(R.id.frame_content, chatDetailsFragment);
                                        fragmentTransaction.addToBackStack(null);
                                        chatDetailsFragment.setArguments(bundle);
                                        fragmentTransaction.commit();
                                    }


                                }
                                else
                                {
                                    Log.e("k4","k4");
                                    ChatDetailsFragment chatDetailsFragment = new ChatDetailsFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("ChatId",id);
                                    bundle.putString("ProfileFromList",avatarForsubHeader);
                                    bundle.putString("NameFromList",nameForsubHeader);
                                    bundle.putString("uniqueIDFromList",uniqueIdForsubHeader);
                                    FragmentTransaction fragmentTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.add(R.id.QRActivity_FameID, chatDetailsFragment);
                                    fragmentTransaction.addToBackStack(null);
                                    chatDetailsFragment.setArguments(bundle);
                                    fragmentTransaction.commit();

                                    TripManagement.saveValueuToPreferences(context,"BackToScanFromShare","BackToScanFromShare");
                                }


                            }
                            else if (jsonObject.getInt(Keys.success) == 0)
                            {
                                JSONObject jsonObject_errors = jsonObject.getJSONObject(Keys.errors);
                                Log.d("ggg",jsonObject_errors.toString());
                                Toast.makeText(context, ""+jsonObject_errors, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e)
                        {

                            e.printStackTrace();
                            Toast.makeText(context, "exeption"+e.toString(), Toast.LENGTH_SHORT).show();
                            Log.d("Exeption_file_preview",e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                        Log.e("Efforfile",error.toString());
                        Toast.makeText(context, ""+error, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        InputMethodManager inputMethodManager = (InputMethodManager)  context.getSystemService(INPUT_METHOD_SERVICE);


                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                // params.put(Keys.message, String.valueOf(bitmap));
                params.put(Keys.type,"1");

                return params;
            }

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Keys.Authorization,"Bearer "+TripManagement.readValueFromPreferences(context,"accesstoken",""));
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
                params.put(Keys.message, new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        volleyMultipartRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 20000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 20000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        //adding the request to volley
        Volley.newRequestQueue(context).add(volleyMultipartRequest);
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    Bitmap encodeAsBitmap(String list) throws WriterException
    {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(String.valueOf(list), BarcodeFormat.QR_CODE, WIDTH, HEIGHT, null);

        } catch (IllegalArgumentException iae)
        {
            // Unsupported format
            return null;
        }

        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++)
        {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
        return bitmap;
    }


}
