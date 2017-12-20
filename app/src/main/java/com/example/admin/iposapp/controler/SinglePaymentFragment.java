package com.example.admin.iposapp.controler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.database.Database;

import info.hoang8f.widget.FButton;

public class SinglePaymentFragment extends DialogFragment {

    // TODO: Rename and change types of parameters
    private FButton apply, cancel;
    private EditText client, monto, reference, notes;
    private Spinner deposit, payForm, bank;
    private Database database;
    private Dialog dialog;

    public SinglePaymentFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        LayoutInflater inflater;
        View view;

        inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_single_payment, null);

        try
        {
            database = new Database(getContext());

            apply = (FButton) view.findViewById(R.id.btnApply);
            cancel = (FButton) view.findViewById(R.id.btnCancel);
            client = (EditText) view.findViewById(R.id.client);
            monto = (EditText) view.findViewById(R.id.monto);
            reference = (EditText) view.findViewById(R.id.reference);
            notes = (EditText) view.findViewById(R.id.notes);
            deposit = (Spinner) view.findViewById(R.id.deposit);
            payForm = (Spinner) view.findViewById(R.id.payForm);
            bank = (Spinner) view.findViewById(R.id.bank);

            apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "click", Toast.LENGTH_LONG).show();
                    getDialog().dismiss();
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDialog().dismiss();
                }
            });
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), "Problema al crear vista", Toast.LENGTH_LONG).show();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        dialog = builder.create();
        return dialog;
    }

    @Override
    public Dialog getDialog(){
        return dialog;
    }
}
