package com.example.admin.iposapp.controler;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.utility.ShowClientsPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientInfoFragment extends DialogFragment {

    private Dialog dialog;

    public ClientInfoFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        LayoutInflater inflater;
        View view = null, viewCustom;
        Context context;
        
        try {

            inflater = getActivity().getLayoutInflater();
            view = inflater.inflate(R.layout.fragment_client_info, null);
            context = view.getContext();

            viewCustom = inflater.inflate(R.layout.fragment_client_info, null);

        }
        catch (Exception e) {
            Toast.makeText(
                    getContext(),
                    "Problema al crear la vista",
                    Toast.LENGTH_SHORT
            ).show();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        dialog = builder.create();
        return dialog;
    }

}
