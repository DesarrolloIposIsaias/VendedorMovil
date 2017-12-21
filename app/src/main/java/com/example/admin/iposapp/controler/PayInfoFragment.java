package com.example.admin.iposapp.controler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.iposapp.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */

public class PayInfoFragment extends DialogFragment {

    public PayInfoFragment() {
        // Required empty public constructor
    }

    private Dialog dialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LayoutInflater inflater;
        View view, viewCustom;
        Context context;

        inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_pay_info, null);
        context = view.getContext();

        try
        {
            viewCustom = inflater.inflate(R.layout.fragment_pay_info, null);

            TextView clientTextView = (TextView) viewCustom.findViewById(
                    R.id.text_view_client
            );

            TextView paymentMethodTextView = (TextView) viewCustom.findViewById(
                    R.id.text_view_payment_method
            );

            TextView totalTextView = (TextView) viewCustom.findViewById(
                    R.id.text_view_total
            );

            TextView referenceTextView = (TextView) viewCustom.findViewById(
                    R.id.text_view_reference
            );

            TextView notesTextView = (TextView) viewCustom.findViewById(
                    R.id.text_view_notes
            );
        }
        catch (Exception e)
        {

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
