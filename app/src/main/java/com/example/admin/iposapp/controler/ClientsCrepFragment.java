package com.example.admin.iposapp.controler;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.model.Crep;
import com.example.admin.iposapp.utility.AutoCompleteContentProvider;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientsCrepFragment extends Fragment {

    private Button goCrepBtn;
    private TextView totalBalanceTxtVw, totalPartialPaymentsTxtVw;
    private AutoCompleteTextView filterAutoComTxtVw;
    private ListView crepsListView;
    private ArrayList<Crep> creps;
    private ListViewCrepAdapter listViewCrepAdapter;

    public ClientsCrepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_clients_crep, container, false);

        try{
            creps = new ArrayList<>();
            creps.add(new Crep("1234", "4321"));
            creps.add(new Crep("4567", "7654"));

            crepsListView = (ListView)view.findViewById(R.id.lvCreps);
            listViewCrepAdapter = new ListViewCrepAdapter(getContext(), creps);
            crepsListView.setAdapter(listViewCrepAdapter);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        filterAutoComTxtVw = (AutoCompleteTextView)view.findViewById(R.id.crepAutoCompleteTextView);
        filterAutoComTxtVw.setThreshold(1);
        filterAutoComTxtVw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(filterAutoComTxtVw.getText().length() == 1){
                    String[] dataSetCreps = AutoCompleteContentProvider.getCreps(creps);
                    ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(
                            getActivity(),
                            R.layout.custom_item,
                            R.id.autoCompleteItem,
                            dataSetCreps
                    );

                    filterAutoComTxtVw.setAdapter(autoCompleteAdapter);
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
