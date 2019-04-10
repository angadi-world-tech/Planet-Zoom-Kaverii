package com.angadi.tripmanagementa.Model;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.angadi.tripmanagementa.Fragments.BreakFastFragment;
import com.angadi.tripmanagementa.Fragments.DinnerFragment;
import com.angadi.tripmanagementa.Fragments.FoodStyleFragment;
import com.angadi.tripmanagementa.Fragments.LunchFragment;

public class MyPagerAdapter extends FragmentPagerAdapter
{
    private static int NUM_ITEMS = 4;

    public MyPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0 : return BreakFastFragment.newInstance(0,"Break Fast");

            case 1: return LunchFragment.newInstance(1,"Lunch");

            case 2 : return DinnerFragment.newInstance(2,"Dinner");

            case  3 : return FoodStyleFragment.newInstance(3,"Food Style");

            default: return null;
        }

    }

    @Override
    public int getCount()
    {
        return NUM_ITEMS;
    }
    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0 : return "Break Fast";

            case 1 : return "Lunch";

            case 2 : return "Dinner";

            case 3 : return "FoodStyle";

        }
        return null;
    }



}
