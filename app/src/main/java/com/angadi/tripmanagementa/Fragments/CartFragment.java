package com.angadi.tripmanagementa.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

public class CartFragment extends Fragment
{

    @BindView(R.id.TextviewRupeesGST) TextView TextviewRupeesGST;
    @BindView(R.id.TextviewRupeesTotal) TextView TextviewRupeesTotal;
    @BindView(R.id.RupeesCardText) TextView RupeesCardText;
    @BindView(R.id.RupeesCardText_second) TextView RupeesCardText_second;
    @BindView(R.id.LinLayoutBack) LinearLayout LinLayoutBack;
    @BindView(R.id.LinlayoutChat) LinearLayout LinlayoutChat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_cart, container, false);
        ButterKnife.bind(this, view);
        Fabric.with(getActivity(), new Crashlytics());

        TextviewRupeesGST.setText("\u20B9" + "13");
        TextviewRupeesTotal.setText("\u20B9" + "200");
        RupeesCardText.setText("\u20B9" + "80");
        RupeesCardText_second.setText("\u20B9" + "80");

        return view;
    }


    @OnClick(R.id.LinLayoutBack)
    public void setLinLayoutBack()
    {
        BreakPointsfragment breakPointsfragment = new BreakPointsfragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_content,breakPointsfragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.LinlayoutChat)
    public void setLinlayoutChat()
    {
        ChatListFragment chatListFragment = new ChatListFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_content,chatListFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }



}
