package com.example.admin.iposapp.controler;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.Payment;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentsFragment extends Fragment {

    private Database db;
    private ArrayList<Payment> payments;
    private ListView paymentsListView;
    private ListViewPaymentAdapter listViewPaymentAdapter;

    public PaymentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payments, container, false);

        db = new Database(getContext());

        try{

            db.open();
            payments = Database.paymentDAO.fetchPayments();
            db.close();

            paymentsListView = (ListView)view.findViewById(R.id.list_view_payments);
            listViewPaymentAdapter = new ListViewPaymentAdapter(
                    getContext(),
                    payments,
                    this
            );

            final ViewGroup headerView = (ViewGroup) getActivity().getLayoutInflater().inflate(
                    R.layout.payment_custom_row_header,
                    paymentsListView,
                    false
            );
            paymentsListView.addHeaderView(
                    headerView,
                    null,
                    false
            );
            paymentsListView.setAdapter(listViewPaymentAdapter);
        }
        catch (Exception e){

            e.printStackTrace();
        }

        return view;
    }

}
