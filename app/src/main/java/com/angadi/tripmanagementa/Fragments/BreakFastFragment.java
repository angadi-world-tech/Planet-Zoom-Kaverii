package com.angadi.tripmanagementa.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angadi.tripmanagementa.R;
import com.crashlytics.android.Crashlytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

public class BreakFastFragment extends Fragment {
    private String title;
    private int page;
    int count = 0 ,count_description = 0;
    Point point;


    @BindView(R.id.RelLayoutAddItem)
    RelativeLayout RelLayoutAddItem;
    @BindView(R.id.RelLayoutRemoveItem)
    RelativeLayout RelLayoutRemoveItem;
    @BindView(R.id.TextViewCount)
    TextView TextViewCount;
    @BindView(R.id.RelLayoutCount)
    RelativeLayout RelLayoutCount;
    @BindView(R.id.TextViewFabCount)
    TextView TextViewFabCount;
    @BindView(R.id.FabCount)
    FloatingActionButton FabCount;
    @BindView(R.id.FabOuter)
    FloatingActionButton FabOuter;
    @BindView(R.id.LinLayoutForDescription) LinearLayout LinLayoutForDescription;


    // newInstance constructor for creating fragment with arguments
    public static BreakFastFragment newInstance(int page, String title) {
        BreakFastFragment fragmentFirst = new BreakFastFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_breakfast, container, false);
        ButterKnife.bind(this, view);
        Fabric.with(getActivity(), new Crashlytics());
        FabOuter.bringToFront();

        return view;
    }

    @OnClick(R.id.RelLayoutAddItem)
    public void setRelLayoutAddItem() {
        count = count + 1;
        if (count > 0) {
            FabCount.setVisibility(View.VISIBLE);
            TextViewCount.setText(Integer.toString(count));
            TextViewFabCount.setText(Integer.toString(count));
        }
        Log.d("count123", String.valueOf(count));

    }

    @OnClick(R.id.RelLayoutRemoveItem)
    public void setRelLayoutRemoveItem() {
        count = count - 1;
        if (count > 0) {
            FabCount.setVisibility(View.VISIBLE);
            TextViewCount.setText(Integer.toString(count));
            TextViewFabCount.setText(Integer.toString(count));
        } else {
            FabCount.setVisibility(View.GONE);

        }
        Log.d("count1234", String.valueOf(count));

    }


    @OnClick(R.id.RelLayoutCount)
    public void setRelLayoutCount()
    {

    }

    @OnClick(R.id.FabOuter)
    public void setFabOuter()
    {
        CartFragment cartFragment = new CartFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_content,cartFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @OnClick(R.id.LinLayoutForDescription)
    public void setLinLayoutForDescription()
    {
        DialogFoodDescription();
    }


    public void DialogFoodDescription()
    {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_food_description, null);
        final AlertDialog dialog = dialogBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView Rupees_food_description = (TextView) dialogView.findViewById(R.id.Rupees_food_description);
        LinearLayout LinLayoutPlusIcon = (LinearLayout)dialogView.findViewById(R.id.LinLayoutPlusIcon);
        final TextView TextviewCount= (TextView)dialogView.findViewById(R.id.TextviewCount);
        Rupees_food_description.setText("\u20B9" + "80");


        LinLayoutPlusIcon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                count_description = count_description + 1;
                TextviewCount.setText(Integer.toString(count_description));

            }
        });
        // Button got_it = (Button) dialogView.findViewById(R.id.got_it);

//        got_it.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });

        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();


    }








}
