package com.angadi.tripmanagementa.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.angadi.tripmanagementa.Activities.Qrcoderesult;
import com.angadi.tripmanagementa.Adapters.FavRecyleradaptetr;
import com.angadi.tripmanagementa.Adapters.FavoritesAdapter;
import com.angadi.tripmanagementa.Application.TripManagement;
import com.angadi.tripmanagementa.Extras.Keys;
import com.angadi.tripmanagementa.Extras.VolleyMultipartRequest;
import com.angadi.tripmanagementa.Model.FavorotesList;
import com.angadi.tripmanagementa.Model.Groups;
import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

public class FavoritesFragment extends Fragment
{

    String getFavaritesListURL = TripManagement.BASE_URL+"favourite/products?page=1";
    String GroupFavorites = TripManagement.BASE_URL+"favourite/product/groups";

    ArrayList<FavorotesList> favorotesListArrayList = new ArrayList<>();
    FavorotesList favorotesList;
    String id,fpg_id,created_at,product,type,name,plus_code,location,photo,isd_code, phone,std_code,landline,email,website,description,price,validity_start,validity_end, quality,  quantity,  offer,  attachment,  image,  hashed_id;

    String count,average, value;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    FavoritesAdapter favoritesAdapter;

    private List<Groups> groupList = new ArrayList<>();
    Groups PojoGroup;
    private FavRecyleradaptetr favRecyleradaptetr;


    @BindView(R.id.LinLayoutBack) LinearLayout LinLayoutBack;
    @BindView(R.id.LinlayoutSearch) LinearLayout LinlayoutSearch;
    @BindView(R.id.ClickedPrivateLinlayout) LinearLayout ClickedPrivateLinlayout;
    @BindView(R.id.UnclickedPrivateLinlayout) LinearLayout UnclickedPrivateLinlayout;
    @BindView(R.id.ClickedFamilyLinlayout) LinearLayout ClickedFamilyLinlayout;
    @BindView(R.id.UnClickedLinlayoutFamily) LinearLayout UnClickedLinlayoutFamily;
    @BindView(R.id.ClickedOfficialLinlayout) LinearLayout ClickedOfficialLinlayout;
    @BindView(R.id.UnClickedOfficialLinLayout) LinearLayout UnClickedOfficialLinLayout;
    @BindView(R.id.UnClickedPubliclayout) LinearLayout UnClickedPubliclayout;
    @BindView(R.id.ClickedPublicLayout) LinearLayout ClickedPublicLayout;
    @BindView(R.id.EDittextSearch) EditText EDittextSearch;
    @BindView(R.id.LinlayoutCancel) LinearLayout LinlayoutCancel;
    @BindView(R.id.ImageviewCancel) ImageView ImageviewCancel;
    @BindView(R.id.listview) ListView listview;
    @BindView(R.id.recyclerviewGropusfavorites) RecyclerView recyclerviewGropusfavorites;
    @BindView(R.id.LinlayoutEmptyContainer) LinearLayout LinlayoutEmptyContainer;
    @BindView(R.id.LinLayoutFavoriteList) LinearLayout LinLayoutFavoriteList;
    @BindView(R.id.LinlayoutMatchesNotFound) LinearLayout LinlayoutMatchesNotFound;
    @BindView(R.id.TextviewFavoritesHeader) TextView TextviewFavoritesHeader;
    @BindView(R.id.TextViewNomatchesFound) TextView TextViewNomatchesFound;
    @BindView(R.id.TextviewNofavorites) TextView TextviewNofavorites;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.layout_favorites,container ,false);
        ButterKnife.bind(this, view);
        Fabric.with(getActivity(), new Crashlytics());

        Typeface montserrat_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MONTSERRAT-BOLD.OTF");
        Typeface montserrat_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Montserrat-Regular.ttf");


        TextviewFavoritesHeader.setTypeface(montserrat_bold);
        EDittextSearch.setTypeface(montserrat_regular);
        TextviewNofavorites.setTypeface(montserrat_regular);


        favRecyleradaptetr = new FavRecyleradaptetr(groupList,getActivity(), id, fpg_id);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,
                false);
        recyclerviewGropusfavorites.setLayoutManager(mLayoutManager);
        recyclerviewGropusfavorites.setItemAnimator(new DefaultItemAnimator());
        recyclerviewGropusfavorites.setAdapter(favRecyleradaptetr);

        EDittextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int count)
            {
                if (count==0)
                {
                  LinLayoutFavoriteList.setVisibility(View.VISIBLE);
                  LinlayoutMatchesNotFound.setVisibility(View.VISIBLE);
                }
                else if (count>0)
                {

                    filter(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                  filter(editable.toString());

            }




        });


        GetFavoritesList();
        GetGroups();
        return view;
    }


    @OnClick(R.id.LinLayoutBack)
    public void setLinLayoutBack()
    {

        startActivity(new Intent(getActivity(), Qrcoderesult.class));
    }

    @OnClick(R.id.LinlayoutSearch)
    public void setLinlayoutSearch() {
        filter(EDittextSearch.getText().toString());
    }

    @OnClick(R.id.LinlayoutCancel)
    public void setLinlayoutCancel()
    {
        LinlayoutCancel.setVisibility(View.GONE);
        LinlayoutSearch.setVisibility(View.VISIBLE);
        EDittextSearch.setText("");
    }

    private void GetFavoritesList()
    {
        //getting the tag from the edittext
        //final String tags = editTextTags.getText().toString().trim();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
       // progressDialog.show();

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, getFavaritesListURL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        progressDialog.dismiss();


                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            if (jsonObject.getInt(Keys.success) == 1)
                            {
                              //  Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                JSONObject jsonObjectData = jsonObject.getJSONObject(Keys.data);
                                Log.e("FavoritesList",jsonObjectData.toString());
                                JSONArray jsonArrayfavourites =  jsonObjectData.getJSONArray(Keys.favourites);

                                if (jsonArrayfavourites != null && jsonArrayfavourites.length() > 0)
                                {
                                    LinlayoutEmptyContainer.setVisibility(View.GONE);
                                    LinLayoutFavoriteList.setVisibility(View.VISIBLE);


                                    for (int i=0;i<jsonArrayfavourites.length();i++)
                                    {
                                        JSONObject jsonObject11 = jsonArrayfavourites.getJSONObject(i);
                                        id = jsonObject11.getString(Keys.id);
                                        fpg_id = jsonObject11.getString(Keys.fpg_id);
                                        created_at = jsonObject11.getString(Keys.created_at);
                                        JSONObject jsonObjectProduct = jsonObject11.getJSONObject(Keys.product);
                                        name = jsonObjectProduct.getString(Keys.name);
                                        description = jsonObjectProduct.getString(Keys.description);
                                        location = jsonObjectProduct.getString(Keys.location);
                                        photo = jsonObjectProduct.getString(Keys.photo);
                                        email = jsonObjectProduct.isNull(Keys.email) ? "" : jsonObjectProduct.getString(Keys.email);
                                        phone = jsonObjectProduct.isNull(Keys.phone) ? "" : jsonObjectProduct.getString(Keys.phone);
                                        price = jsonObjectProduct.isNull(Keys.price) ? "" : jsonObjectProduct.getString(Keys.price);
                                        website = jsonObjectProduct.isNull(Keys.website) ? "" : jsonObjectProduct.getString(Keys.website);
                                        quality = jsonObjectProduct.isNull(Keys.quality) ? "" : jsonObjectProduct.getString(Keys.quality);
                                        quantity = jsonObjectProduct.isNull(Keys.quantity) ? "" : jsonObjectProduct.getString(Keys.quantity);
                                       // validitystart = jsonObject11.isNull(Keys.validity_start) ? "" : jsonObject11.getString(Keys.validity_start);
                                        location = jsonObjectProduct.isNull(Keys.location) ? "" : jsonObjectProduct.getString(Keys.location);
                                        photo = jsonObjectProduct.isNull(Keys.photo) ? "" : jsonObjectProduct.getString(Keys.photo);
                                        offer = jsonObjectProduct.isNull(Keys.offer) ? "" : jsonObjectProduct.getString(Keys.offer);
                                       // validitysend = jsonObject11.isNull(Keys.validity_end) ? "" : jsonObject11.getString(Keys.validity_end);
                                       // stdcode = jsonObject11.isNull(Keys.std_code) ? "" : jsonObject11.getString(Keys.std_code);
                                        landline = jsonObjectProduct.isNull(Keys.landline) ? "" : jsonObjectProduct.getString(Keys.landline);
                                        hashed_id = jsonObjectProduct.isNull(Keys.hashed_id) ? "" : jsonObjectProduct.getString(Keys.hashed_id);

                                         JSONObject jsonObject_rating = jsonObjectProduct.getJSONObject(Keys.rating);
                                         String count = jsonObject_rating.isNull(Keys.count) ? "" :jsonObject_rating.getString(Keys.count);
                                         String average = jsonObject_rating.isNull(Keys.average) ? "" :jsonObject_rating.getString(Keys.average);
                                         String value = jsonObject_rating.isNull(Keys.value) ? "" :jsonObject_rating.getString(Keys.value);

                                        favorotesList = new FavorotesList(id,fpg_id,created_at,product,type,name,plus_code,location,photo,isd_code, phone,std_code,landline,email,website,description,price,validity_start,validity_end, quality,  quantity,  offer,  attachment,  image,  hashed_id,count,average,value);
                                        favorotesListArrayList.add(favorotesList);
                                        favoritesAdapter = new FavoritesAdapter(getActivity(),favorotesListArrayList,LinLayoutFavoriteList,LinlayoutMatchesNotFound,TextViewNomatchesFound,EDittextSearch);
                                        listview.setAdapter(favoritesAdapter);

                                        TripManagement.saveValueuToPreferences(getActivity(),"FPG",fpg_id);

                                        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                                            {
                                                FavoritesDetails favoritesDetails = new FavoritesDetails();
                                                Bundle bundle = new Bundle();
                                                bundle.putString("ProductName",favorotesListArrayList.get(i).getName());
                                                bundle.putString("HashedId",favorotesListArrayList.get(i).getHashed_id());
                                                bundle.putString("Location",favorotesListArrayList.get(i).getLocation());
                                                bundle.putString("Desrcption",favorotesListArrayList.get(i).getDescription());
                                                bundle.putString("Mobile",favorotesListArrayList.get(i).getPhone());


                                                bundle.putString("Price", favorotesListArrayList.get(i).getPrice());
                                                bundle.putString("Website", favorotesListArrayList.get(i).getWebsite());
                                                bundle.putString("Email", favorotesListArrayList.get(i).getEmail());
                                                bundle.putString("Photo", favorotesListArrayList.get(i).getPhoto());
                                                bundle.putString("stdcode", favorotesListArrayList.get(i).getStd_code());
                                                bundle.putString("landline", favorotesListArrayList.get(i).getLandline());
                                                bundle.putString("quality", favorotesListArrayList.get(i).getQuality());
                                                bundle.putString("quantity", favorotesListArrayList.get(i).getQuantity());

                                                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                                fragmentTransaction.add(R.id.frame_content,favoritesDetails);
                                                fragmentTransaction.addToBackStack(null);
                                                favoritesDetails.setArguments(bundle);
                                                fragmentTransaction.commit();

                                            }
                                        });
                                    }
                                }
                                else
                                {
                                    LinlayoutEmptyContainer.setVisibility(View.VISIBLE);
                                    LinLayoutFavoriteList.setVisibility(View.GONE);
                                }


                            }
                            else if (jsonObject.getInt(Keys.success) == 0)
                            {
                                JSONObject jsonObject_errors = jsonObject.getJSONObject(Keys.errors);
                                Toast.makeText(getActivity(), ""+jsonObject_errors, Toast.LENGTH_SHORT).show();
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


    private void GetGroups()
    {
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, GroupFavorites,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            if (jsonObject.getInt(Keys.success) == 1)
                            {
                               // Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                JSONObject jsonObjectData = jsonObject.getJSONObject(Keys.data);
                                JSONArray jsonArrayGroups = jsonObjectData.getJSONArray(Keys.groups);

                                for (int i=0;i<jsonArrayGroups.length();i++)
                                {
                                    JSONObject jsonObject11 = jsonArrayGroups.getJSONObject(i);
                                    String id = jsonObject11.getString(Keys.id);
                                    String name = jsonObject11.getString(Keys.name);

                                    PojoGroup = new Groups(id,name);
                                    groupList.add(PojoGroup);


                                    favRecyleradaptetr = new FavRecyleradaptetr(groupList,getActivity(),id,fpg_id);
                                    recyclerviewGropusfavorites.setAdapter(favRecyleradaptetr);



                                }
                            }
                            else if (jsonObject.getInt(Keys.success) == 0)
                            {
                                JSONObject jsonObject_errors = jsonObject.getJSONObject(Keys.errors);

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

    void filter(String text)
    {

        //  SearchUserMessege();

        ArrayList<FavorotesList> temp = new ArrayList();
        for(FavorotesList d: favorotesListArrayList)
        {
            if(d.getName().toLowerCase().contains(text.toLowerCase()))
            {
                temp.add(d);
            }



        }
        //update recyclerview
        if (!temp.isEmpty())
        {
            favoritesAdapter.updateList(temp);
        }
        else if (temp.isEmpty() && !EDittextSearch.getText().toString().isEmpty())
        {
            favoritesAdapter.ShowToast();
        }

    }



}
