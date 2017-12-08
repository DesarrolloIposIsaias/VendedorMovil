package com.example.admin.iposapp.utility;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.admin.iposapp.controler.TabContactInformationFragment;
import com.example.admin.iposapp.controler.TabTaxInformationFragment;

/**
 * Created by admin on 05/09/2016.
 */
 public class  ShowClientsPagerAdapter extends FragmentStatePagerAdapter
{
    private int mNumOfTabs;


    public ShowClientsPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabContactInformationFragment tab1 = new TabContactInformationFragment();
                return tab1;
            case 1:
                TabTaxInformationFragment tab2 = new TabTaxInformationFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}