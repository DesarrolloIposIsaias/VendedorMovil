package com.example.admin.iposapp.controler;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.model.Client;
import com.example.admin.iposapp.utility.CurrentData;
import com.example.admin.iposapp.utility.DataProviderExpList;
import com.example.admin.iposapp.utility.PaymentInfoAdapter;
import com.hanks.htextview.HTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.halfbit.fabuless.FabView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddClientPaymentInfoFragment extends Fragment
{
    private EditText paymentLimitEt;
    private EditText paymentDeadlineEt;
    private Spinner paymentPriceSpinner;
    private Spinner paymentPaydaySpinner;
    private Spinner paymentReviewSpinner;
    private CheckBox paymentCardChkBox;
    private CheckBox paymentCreditChkBox;
    private CheckBox paymentCheckChkBox;
    private CheckBox paymentTransferChkBox;
    private CheckBox paymentBlockedChkBox;
    private HTextView tvPayday, tvReview, tvPrice;
    private FabView floatingSaveButton;
    private boolean paydayIsDrawed, reviewIsDrawed, priceIsDrawed;
    private ScrollView sV;

    public AddClientPaymentInfoFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_add_client_payment_info, container, false);

        //scrollview initialization
        sV = (ScrollView) view.findViewById(R.id.scrollView);
        //animate text view initialization
        tvPayday = (HTextView) view.findViewById(R.id.tvPayday);
        tvReview = (HTextView) view.findViewById(R.id.tvReview);
        tvPrice = (HTextView) view.findViewById(R.id.tvPrice);
        // boolean is drawed iniatilization
        paydayIsDrawed = false;
        reviewIsDrawed = false;
        priceIsDrawed = false;
        //floating action button initialization
        floatingSaveButton = (FabView) view.findViewById(R.id.fab);

        paymentLimitEt = (EditText) view.findViewById(R.id.paymentLimit);
        paymentDeadlineEt = (EditText) view.findViewById(R.id.paymentDeadline);
        paymentPriceSpinner = (Spinner) view.findViewById(R.id.spinner_price);
        paymentPaydaySpinner = (Spinner) view.findViewById(R.id.spinner_payday);
        paymentReviewSpinner = (Spinner) view.findViewById(R.id.spinner_reviewday);
        paymentCardChkBox = (CheckBox) view.findViewById(R.id.checkBoxCard);
        paymentCreditChkBox = (CheckBox) view.findViewById(R.id.checkBoxCredit);
        paymentCheckChkBox = (CheckBox) view.findViewById(R.id.checkBoxCheck);
        paymentTransferChkBox = (CheckBox) view.findViewById(R.id.checkBoxTransfer);
        paymentBlockedChkBox = (CheckBox) view.findViewById(R.id.checkBoxBlocked);


        ArrayAdapter<CharSequence> daysAdapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.days,
                R.layout.white_spinner_textview);

        ArrayAdapter<CharSequence> pricesAdapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.prices,
                R.layout.white_spinner_textview);

        daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pricesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        paymentPriceSpinner.setAdapter(pricesAdapter);
        paymentPaydaySpinner.setAdapter(daysAdapter);
        paymentReviewSpinner.setAdapter(daysAdapter);

        floatingSaveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                saveInfo();

                AddClientExtraInfoFragment newFragment = new AddClientExtraInfoFragment();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.fragmentContainer, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        sV.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                Rect scrollBounds = new Rect();
                //client name animation
                if (tvPayday.getLocalVisibleRect(scrollBounds)) {
                    if(paydayIsDrawed == false){
                        tvPayday.animateText("DIA DE PAGO");
                        paydayIsDrawed = true;
                        // Any portion of the imageView, even a single pixel, is within the visible window
                    }

                }
                else
                {
                    paydayIsDrawed = false;
                }

                if (tvPrice.getLocalVisibleRect(scrollBounds)) {
                    if(priceIsDrawed == false){
                        tvPrice.animateText("PRECIO");
                        priceIsDrawed = true;
                        // Any portion of the imageView, even a single pixel, is within the visible window
                    }

                }
                else
                {
                    priceIsDrawed = false;
                }

                if (tvReview.getLocalVisibleRect(scrollBounds)) {
                    if(reviewIsDrawed == false){
                        tvReview.animateText("REVISION");
                        reviewIsDrawed = true;
                        // Any portion of the imageView, even a single pixel, is within the visible window
                    }

                }
                else
                {
                    reviewIsDrawed = false;
                }
            }
        });

        return view;
    }

    public void saveInfo()
    {
        Client client = CurrentData.getClient();

        if(paymentLimitEt.getText().toString().equals("")) client.setLimiteCredito(0);
        else client.setLimiteCredito(Float.valueOf(paymentLimitEt.getText().toString()));
        client.setDiaPago(paymentPaydaySpinner.getSelectedItem().toString());
        client.setPlazo(paymentDeadlineEt.getText().toString());
        client.setPrecio(paymentPriceSpinner.getSelectedItem().toString());
        client.setRevision(paymentReviewSpinner.getSelectedItem().toString());

        if(paymentCardChkBox.isChecked()) client.setTarjeta("S");
        else client.setTarjeta("N");
        if(paymentCreditChkBox.isChecked()) client.setCredito("S");
        else client.setCredito("N");
        if(paymentBlockedChkBox.isChecked()) client.setBloqueado("S");
        else client.setBloqueado("N");
        if(paymentTransferChkBox.isChecked()) client.setTransferencia("S");
        else  client.setTransferencia("N");
        if(paymentCheckChkBox.isChecked()) client.setCheque("S");
        else client.setCheque("N");

        CurrentData.setClient(client);

        Log.w("PAYMENT INFO: ", "SAVED");
    }
}
