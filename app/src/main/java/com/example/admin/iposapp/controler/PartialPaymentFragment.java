package com.example.admin.iposapp.controler;


import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.PaymentDetail;
import com.example.admin.iposapp.utility.CurrentData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PartialPaymentFragment extends DialogFragment {

    private ListView partialPaymentsListView;
    private ListViewPartialPaymentAdapter listViewPartialPaymentAdapter;
    private Dialog dialog;
    private Database db;
    private ArrayList<PaymentDetail> paymentDetails;

    public PartialPaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(
                R.layout.fragment_partial_payment,
                null
        );

        try{

            db = new Database(getActivity());

            partialPaymentsListView = (ListView) view.findViewById(R.id.list_view_partial_payments);

            final ViewGroup headerView = (ViewGroup) getActivity().getLayoutInflater().inflate(
                    R.layout.partial_payment_custom_row_header,
                    partialPaymentsListView,
                    false
            );

            partialPaymentsListView.addHeaderView(
                    headerView,
                    null,
                    false
            );

            db.open();
            paymentDetails = Database.paymentDetailDAO.fetchPaymentDetailsByPayment(CurrentData.getSelectedPayment());
            db.close();

            listViewPartialPaymentAdapter = new ListViewPartialPaymentAdapter(
                    getActivity(),
                    paymentDetails
            );

            partialPaymentsListView.setAdapter(listViewPartialPaymentAdapter);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(view);

            dialog = builder.create();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return dialog;
    }

}
