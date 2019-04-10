package com.angadi.tripmanagementa.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.angadi.tripmanagementa.Model.FavorotesList;
import com.angadi.tripmanagementa.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavoritesAdapter extends BaseAdapter
{
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<FavorotesList> favorotesLists;
    LinearLayout linLayoutFavoriteList,linlayoutMatchesNotFound;
    TextView  textViewNomatchesFound;
    EditText EDittextSearch;


    public FavoritesAdapter(Context context, ArrayList<FavorotesList> favorotesLists, LinearLayout linLayoutFavoriteList, LinearLayout linlayoutMatchesNotFound, TextView textViewNomatchesFound, EditText EDittextSearch)
    {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.favorotesLists = favorotesLists;
        this.linLayoutFavoriteList = linLayoutFavoriteList;
        this.linlayoutMatchesNotFound = linlayoutMatchesNotFound;
        this.textViewNomatchesFound = textViewNomatchesFound;
        this.EDittextSearch = EDittextSearch;
    }

    @Override
    public int getCount()
    {
        return favorotesLists.size();
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
    public View getView(int i, View view, ViewGroup viewGroup)
    {
         view = layoutInflater.inflate(R.layout.favorite_adapter_layout,null);

         ImageView imageview = (ImageView)view.findViewById(R.id.imageview);
         ImageView ImageviewFavHeart = (ImageView)view.findViewById(R.id.ImageviewFavHeart);
         TextView TextViewName = (TextView)view.findViewById(R.id.TextViewName);
         TextView TextViewDate = (TextView)view.findViewById(R.id.TextViewDate);
         TextView TextvewRatingcount = (TextView)view.findViewById(R.id.TextvewRatingcount);
         TextView TextviewAvarage = (TextView)view.findViewById(R.id.TextviewAvarage);

         TextViewName.setText(favorotesLists.get(i).getName());
         TextViewDate.setText(favorotesLists.get(i).getCreated_at());
         TextvewRatingcount.setText("("+favorotesLists.get(i).getCount()+")");
         TextviewAvarage.setText(favorotesLists.get(i).getAvearage());



        Typeface montserrat_regular = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Regular.ttf");

        TextViewName.setTypeface(montserrat_regular);
        TextViewDate.setTypeface(montserrat_regular);
        TextvewRatingcount.setTypeface(montserrat_regular);
        TextviewAvarage.setTypeface(montserrat_regular);

        return view;
    }

    public void updateList(ArrayList<FavorotesList> list) {
        linlayoutMatchesNotFound.setVisibility(View.GONE);
        linLayoutFavoriteList.setVisibility(View.VISIBLE);
        favorotesLists = list;
        notifyDataSetChanged();

    }

    public void ShowToast() {
        linlayoutMatchesNotFound.setVisibility(View.VISIBLE);
        linLayoutFavoriteList.setVisibility(View.GONE);
        textViewNomatchesFound.setText("No results found for" + " " + "'" + EDittextSearch.getText().toString() + "'");
        textViewNomatchesFound.setSingleLine(true);
        textViewNomatchesFound.setEllipsize(TextUtils.TruncateAt.END);
        int n = 1; // the exact number of lines you want to display
        textViewNomatchesFound.setLines(n);

    }


}
