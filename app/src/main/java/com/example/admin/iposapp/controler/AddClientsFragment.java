package com.example.admin.iposapp.controler;


import android.os.Bundle;
import com.github.clans.fab.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.utility.AddClientsPagerAdapter;
import com.github.clans.fab.FloatingActionMenu;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddClientsFragment extends Fragment
{


    public AddClientsFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_add_clients, container, false);

        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.tab_layout_add_clients);
        tabLayout.addTab(tabLayout.newTab().setText("Contacto"));
        tabLayout.addTab(tabLayout.newTab().setText("Metodo de pago"));
        tabLayout.addTab(tabLayout.newTab().setText("Extra"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager_add_clients);
        final AddClientsPagerAdapter addClientsPagerAdapter =
                new AddClientsPagerAdapter(
                        getFragmentManager(),
                        tabLayout.getTabCount());

        viewPager.setAdapter(addClientsPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });

        return view;
    }

}
