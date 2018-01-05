package com.example.admin.iposapp.controler;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.listeners.SinglePaymentDialogCloseListener;
import com.example.admin.iposapp.model.Crep;
import com.example.admin.iposapp.utility.AutoCompleteContentProvider;
import com.example.admin.iposapp.utility.CurrentData;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.widget.FButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientsCrepFragment extends Fragment implements SinglePaymentDialogCloseListener {

    private FButton goCrepBtn;
    private TextView totalBalanceTxtVw, totalPartialPaymentsTxtVw;
    private EditText filterEditText;
    private ListView crepsListView;
    private ArrayList<Crep> creps;
    private ListViewCrepAdapter listViewCrepAdapter;
    private Database db;

    public ClientsCrepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_clients_crep, container, false);
        db = new Database(getContext());

        try{

            db.open();
            creps = Database.crepDAO.fetchCrepsByClient(CurrentData.getClientId());
            db.close();

            crepsListView = (ListView)view.findViewById(R.id.lvCreps);
            listViewCrepAdapter = new ListViewCrepAdapter(getContext(), creps, this);
            crepsListView.setAdapter(listViewCrepAdapter);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        filterEditText = (EditText) view.findViewById(R.id.edit_text_filter);
        goCrepBtn = (FButton)view.findViewById(R.id.button_filter);
        goCrepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filter = filterEditText.getText().toString();

                db.open();
                if(filter.equals("")) {

                    creps = Database.crepDAO.fetchCrepsByClient(CurrentData.getClientId());

                }
                else {

                    creps = Database.crepDAO.fetchMatchingCreps(filter, CurrentData.getClientId());
                }

                db.close();

                updateListViewDataSet();
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    private void updateListViewDataSet(){
        listViewCrepAdapter.getData().clear();
        listViewCrepAdapter.getData().addAll(creps);
        listViewCrepAdapter.notifyDataSetChanged();
    }

    @Override
    public void handleDialogClose(String sender) {
        if(sender.equals("APPLY")){
            db.open();
            creps = Database.crepDAO.fetchCrepsByClient(CurrentData.getClientId());
            db.close();

            updateListViewDataSet();
        }
    }
}
