package com.example.admin.iposapp.controler;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.Client;
import com.example.admin.iposapp.model.Crep;
import com.example.admin.iposapp.utility.AutoCompleteContentProvider;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientsMultipleCrepFragment extends Fragment {

    private Button goNextBtn;
    private DatePicker depositDate;
    private TextView montoTxtVw;
    private Spinner bankSpinner;
    private Spinner payFormSpinner;
    private AutoCompleteTextView filterAutoComTxtVw;
    private ArrayList<Client> clients;

    public ClientsMultipleCrepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clients_multiple_crep, container, false);

        Database db = new Database(this.getContext());
        db.open();
        clients = (ArrayList<Client>) Database.clientDao.fetchAllClients();
        db.close();

        goNextBtn = (Button) view.findViewById(R.id.nextStep);
        depositDate = (DatePicker) view.findViewById(R.id.deposit);
        montoTxtVw = (TextView) view.findViewById(R.id.monto);
        bankSpinner = (Spinner) view.findViewById(R.id.bank);
        payFormSpinner = (Spinner) view.findViewById(R.id.payForm);

        filterAutoComTxtVw = (AutoCompleteTextView)view.findViewById(R.id.clientAutoCompleteTextView);
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
                    String[] dataSetClients = AutoCompleteContentProvider.getClients(clients);
                    ArrayAdapter<String> autoCompleteAdapter = new ArrayAdapter<>(
                            getActivity(),
                            R.layout.custom_item,
                            R.id.autoCompleteItem,
                            dataSetClients
                    );

                    filterAutoComTxtVw.setAdapter(autoCompleteAdapter);
                }
            }
        });


        return view;
    }

}
