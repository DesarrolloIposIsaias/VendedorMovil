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
 * Use the {@link FtpMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FtpMenu extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button toFtpSettings;
    private Button toSync;


    public FtpMenu()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FtpMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static FtpMenu newInstance(String param1, String param2)
    {
        FtpMenu fragment = new FtpMenu();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_ftp_menu, container, false);
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        CurrentData.setSubtotal(0);
        CurrentData.setTotal(0);
        toFtpSettings = (Button) view.findViewById(R.id.uploadBtn);
        toSync = (Button) view.findViewById(R.id.toSync);

         toFtpSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FtpSettingFragment newFragment = new FtpSettingFragment();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.fragmentContainer, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        toSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SyncFragment newFragment = new SyncFragment();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.fragmentContainer, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        return view;
    }

}
