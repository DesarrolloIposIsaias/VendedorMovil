package com.example.admin.iposapp.controler;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.model.Client;
import com.hanks.htextview.HTextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabTaxInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabTaxInformationFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private HTextView deadlineTView,limitTView,priceTView,paydayTView,
                        reviewTView,IEPSTView,serieTView;
    private EditText deadLineET, limitET, priceET, paydayET,
                     reviewET, IEPSET, serieET;
    private boolean deadlineIsDrawed, limitIsDrawed, priceIsDrawed, paydayIsDrawed,
                    reviewIsDrawed, IEPSIsDrawed, serieIsDrawed;
    private CheckBox cBoxCard,cBoxCredit, cBoxCheck, cBoxTransfer,cBoxBlocked,
                    cBoxIEPS, cBoxHomeService;
    private ScrollView sV;
    public TabTaxInformationFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabTaxInformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TabTaxInformationFragment newInstance(String param1, String param2)
    {
        TabTaxInformationFragment fragment = new TabTaxInformationFragment();
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
        View view = inflater.inflate(R.layout.fragment_tab_tax_information, container, false);

        //scroll view binding
        sV = (ScrollView) view.findViewById(R.id.scrollView);
        //check box binding
        cBoxCard = (CheckBox) view.findViewById(R.id.checkBoxCard);
        cBoxCredit = (CheckBox) view.findViewById(R.id.checkBoxCredit);
        cBoxCheck = (CheckBox) view.findViewById(R.id.checkBoxCheck);
        cBoxBlocked = (CheckBox) view.findViewById(R.id.checkBoxBlocked);
        cBoxTransfer = (CheckBox) view.findViewById(R.id.checkBoxTransfer);
        cBoxIEPS = (CheckBox) view.findViewById(R.id.checkBoxIeps);
        cBoxHomeService =   (CheckBox) view.findViewById(R.id.checkBoxAddresDelivery);
        //HTEXTVIEW BINDING
        deadlineTView = (HTextView) view.findViewById(R.id.tvDeadline);
        limitTView = (HTextView) view.findViewById(R.id.tvLimit);
        priceTView = (HTextView) view.findViewById(R.id.tvPrice);
        paydayTView = (HTextView) view.findViewById(R.id.tvPayday);
        reviewTView = (HTextView) view.findViewById(R.id.tvReview);
        IEPSTView = (HTextView) view.findViewById(R.id.tvIEPSAccount);
        serieTView = (HTextView) view.findViewById(R.id.tvSerie);

        //Booleans fuckin initialization
        deadlineIsDrawed = false;
        limitIsDrawed = false;
        priceIsDrawed = false;
        paydayIsDrawed = false;
        reviewIsDrawed = false;
        IEPSIsDrawed = false;
        serieIsDrawed = false;

        // EDIT TEXT BINDING
        deadLineET = (EditText) view.findViewById(R.id.deadlineEt);
        limitET = (EditText) view.findViewById(R.id.limitEt);
        priceET = (EditText) view.findViewById(R.id.priceEt);
        paydayET = (EditText) view.findViewById(R.id.paydayEt);
        reviewET = (EditText) view.findViewById(R.id.ReviewEt);
        IEPSET = (EditText) view.findViewById(R.id.IEPSEt);
        serieET = (EditText) view.findViewById(R.id.serieEt);

        sV.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                Rect scrollBounds = new Rect();
                //dead line animation
                if (deadlineTView.getLocalVisibleRect(scrollBounds)) {
                    if(!deadlineIsDrawed){
                        deadlineTView.animateText("PLAZO");
                        deadlineIsDrawed = true;
                        // Any portion of the imageView, even a single pixel, is within the visible window
                    }

                }
                else
                {
                    deadlineIsDrawed = false;
                }
                // limit animation
                if ( limitTView.getLocalVisibleRect(scrollBounds)) {
                    if(!limitIsDrawed){
                        limitTView.animateText("LIMITE");
                        limitIsDrawed = true;
                        // Any portion of the imageView, even a single pixel, is within the visible window
                    }

                }
                else
                {
                    limitIsDrawed = false;
                }
                //price animation
                if (priceTView.getLocalVisibleRect(scrollBounds)) {
                    if(!priceIsDrawed){
                        priceTView.animateText("PRICE");
                        priceIsDrawed = true;
                        // Any portion of the imageView, even a single pixel, is within the visible window
                    }

                }
                else
                {
                    priceIsDrawed = false;
                }
                //payday animation
                if (paydayTView.getLocalVisibleRect(scrollBounds)) {
                    if(!paydayIsDrawed){
                        paydayTView.animateText("DIA DE PAGO");
                        paydayIsDrawed = true;
                        // Any portion of the imageView, even a single pixel, is within the visible window
                    }

                }
                else
                {
                    paydayIsDrawed = false;
                }
                //review animation
                if ( reviewTView.getLocalVisibleRect(scrollBounds)) {
                    if(!reviewIsDrawed){
                        reviewTView.animateText("REVISION");
                        reviewIsDrawed = true;
                        // Any portion of the imageView, even a single pixel, is within the visible window
                    }

                }
                else
                {
                    reviewIsDrawed = false;
                }
                //IEPS animation
                if (IEPSTView.getLocalVisibleRect(scrollBounds)) {
                    if(!IEPSIsDrawed){
                        IEPSTView.animateText("CUENTA IEPS");
                        IEPSIsDrawed = true;
                        // Any portion of the imageView, even a single pixel, is within the visible window
                    }

                }
                else
                {
                    IEPSIsDrawed = false;
                }
                //serie animation
                if (serieTView.getLocalVisibleRect(scrollBounds)) {
                    if(!serieIsDrawed){
                        serieTView.animateText("SERIE");
                        serieIsDrawed = true;
                        // Any portion of the imageView, even a single pixel, is within the visible window
                    }

                }
                else
                {
                    serieIsDrawed = false;
                }

            }
        });


        return view;
    }

    public void refreshData(Client client)
    {
        if (client.getDiaPago() != null)
        {
            deadLineET.setText(client.getDiaPago());
        }
        else{
            deadLineET.setText("");
        }

        if (String.valueOf(client.getLimiteCredito()) != null)
        {
            limitET.setText(String.valueOf(client.getLimiteCredito()));
        }
        else{
            limitET.setText("");
        }

        if (client.getPrecio()!=null)
        {
            priceET.setText(client.getPrecio());
        }
        else{
            priceET.setText("");
        }

        if (client.getRevision()!=null)
        {
            reviewET.setText(client.getRevision());
        }
        else{
            reviewET.setText("");
        }

        if(client.getCuentaIeps() != null)
        {
            IEPSET.setText(client.getCuentaIeps());
        }
        else{
            IEPSET.setText("");
        }

        if(client.getSerie() != null)
        {
            serieET.setText(client.getSerie());
        }
        else{
            serieET.setText("");
        }
        if(client.getTarjeta()!=null)
        {
            if(client.getTarjeta().equals("S"))
            {
                cBoxCard.setChecked(true);
            }
            else
            {
                cBoxCard.setChecked(false);
            }
        }
        else
        {
            cBoxCard.setChecked(false);
        }

        if(client.getCredito() != null)
        {
            if(client.getCredito().equals("S"))
            {
                cBoxCredit.setChecked(true);
            }
            else
            {
                cBoxCredit.setChecked(false);
            }
        }
        else{
            cBoxCredit.setChecked(false);
        }

        if(client.getCheque() != null)
        {
            if(client.getCheque().equals("S"))
            {
                cBoxCheck.setChecked(true);
            }
            else
            {
                cBoxCheck.setChecked(false);
            }
        }
        else{
            cBoxCheck.setChecked(false);
        }

        if(client.getTransferencia() != null)
        {
            if(client.getTransferencia().equals("S"))
            {
                cBoxTransfer.setChecked(true);
            }
            else
            {
                cBoxTransfer.setChecked(false);
            }
        }
        else{
            cBoxTransfer.setChecked(false);
        }

        if(client.getBloqueado() != null)
        {
            if(client.getBloqueado().equals("S"))
            {
                cBoxBlocked.setChecked(true);
            }
            else
            {
                cBoxBlocked.setChecked(false);
            }
        }
        else{
            cBoxBlocked.setChecked(false);
        }

        if(client.getCuentaIeps() != null)
        {
            if(client.getCuentaIeps().equals("S"))
            {
                cBoxIEPS.setChecked(true);
            }
            else
            {
                cBoxIEPS.setChecked(false);
            }
        }
        else{
            cBoxIEPS.setChecked(false);
        }

        if(client.getServicioDomicilio() != null)
        {
            if(client.getServicioDomicilio().equals("S"))
            {
                cBoxHomeService.setChecked(true);
            }
            else
            {
                cBoxHomeService.setChecked(false);
            }
        }
        else{
            cBoxHomeService.setChecked(false);
        }

    }
}
