package com.example.admin.iposapp.controler;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        }
        catch (Exception e){

        }

        return view;
    }

}
