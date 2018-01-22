package com.example.admin.iposapp.controler;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.Payment;
import com.example.admin.iposapp.utility.CurrentData;

import java.util.ArrayList;

import info.hoang8f.widget.FButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentsFragment extends Fragment {

    private Database db;
    private ArrayList<Payment> payments;
    private ListView paymentsListView;
    private ListViewPaymentAdapter listViewPaymentAdapter;
    private FButton filterButton;
    private EditText filterEditText;

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

            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

            db.open();
            payments = Database.paymentDAO.fetchPayments();
            db.close();

            filterEditText = (EditText)view.findViewById(R.id.edit_text_filter_payments);

            filterButton = (FButton)view.findViewById(R.id.button_filter_payments);
            filterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String filter = filterEditText.getText().toString();

                    db.open();
                    if(filter.equals("")) {

                        payments = Database.paymentDAO.fetchPayments();
                    }
                    else {

                        ArrayList<Payment> clientBillFilterRecords =
                                Database.paymentDAO.fetchPaymentsByClientAndBill(filter);

                        payments = Database.paymentDAO.fetchMatchingPayments(filter);

                        payments.addAll(clientBillFilterRecords);
                    }

                    db.close();

                    updateListViewDataSet();
                }
            });

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

            paymentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    CurrentData.setSelectedPayment(
                            listViewPaymentAdapter.getData().get(i-1).getId()
                    );

                    PartialPaymentFragment partialPaymentFragment = new PartialPaymentFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    partialPaymentFragment.show(
                            fragmentManager,
                            "client_changed_dialog"
                    );
                }
            });
        }
        catch (Exception e){

            e.printStackTrace();
        }

        return view;
    }

    private void updateListViewDataSet() {

        listViewPaymentAdapter.getData().clear();
        listViewPaymentAdapter.getData().addAll(payments);
        listViewPaymentAdapter.notifyDataSetChanged();

    }

}
