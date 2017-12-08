package com.example.admin.iposapp.controler;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.iposapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTabHostPager extends Fragment
{

    private FragmentTabHost fragmentTabHost;

    public FragmentTabHostPager()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(
                R.layout.fragment_fragment_tab_host_pager,
                container,
                false);

        return rootView;
    }

}
