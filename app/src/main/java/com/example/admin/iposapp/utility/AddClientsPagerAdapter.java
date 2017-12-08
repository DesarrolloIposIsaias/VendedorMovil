package com.example.admin.iposapp.utility;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;

import com.example.admin.iposapp.controler.AddClientExtraInfoFragment;
import com.example.admin.iposapp.controler.AddClientGeneralInfoFragment;
import com.example.admin.iposapp.controler.AddClientPaymentInfoFragment;

/**
 * Created by admin on 07/09/2016.
 */
public class AddClientsPagerAdapter extends FragmentStatePagerAdapter
{
    private int numOfTabs;

    public AddClientsPagerAdapter(FragmentManager fm, int numOfTabs)
    {
        super(fm);
        this.numOfTabs = numOfTabs;

    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                AddClientGeneralInfoFragment generalInfoFragment =
                        new AddClientGeneralInfoFragment();
                return generalInfoFragment;
            case 1:
                AddClientPaymentInfoFragment paymentInfoFragment =
                        new AddClientPaymentInfoFragment();
                return paymentInfoFragment;
            case 2:
                AddClientExtraInfoFragment extraInfoFragment =
                        new AddClientExtraInfoFragment();
                return extraInfoFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount()
    {
        return numOfTabs;
    }

}
