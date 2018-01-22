package com.example.admin.iposapp.controler;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.utility.CurrentData;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientMenuFragment extends Fragment
{
    private Button toAdd, toShow;


    public ClientMenuFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        CurrentData.setSubtotal(0);
        CurrentData.setTotal(0);
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        View view = inflater.inflate(R.layout.fragment_client_menu, container, false);

        toAdd = (Button) view.findViewById(R.id.addClientBtn);
        toShow = (Button) view.findViewById(R.id.showAllClientsBtn);

        toShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AllClientsFragment newFragment = new AllClientsFragment();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.fragmentContainer, newFragment,"contact_information");
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        toAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AddClientGeneralInfoFragment addClientsFragment = new AddClientGeneralInfoFragment();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.fragmentContainer, addClientsFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;

    }

}
