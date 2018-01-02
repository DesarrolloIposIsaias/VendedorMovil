package com.example.admin.iposapp.controler;


import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.Crep;
import com.example.admin.iposapp.utility.AutoCompleteContentProvider;
import com.example.admin.iposapp.utility.CurrentData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewMultipleCrepPaymentFragment extends Fragment{

    private Button goApplyPaymentBtn;
    public static ListView crepsListView;
    private ArrayList<Crep> creps;
    private ListViewMultipleCrepAdapter listViewCrepAdapter;
    public static TextView aCuentaTxtVw;
    public static TextView aCuentaAbonadaTxtVw;
    private EditText auxEditText;


    public ListViewMultipleCrepPaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_view_multiple_crep_payment, container, false);

        try{
            creps = new ArrayList<>();

            Database db = new Database(this.getContext());
            db.open();
            creps = (ArrayList<Crep>) Database.crepDAO.fetchCrepsByClientList(getClientCode(CurrentData.getActualMultiplePaymentHeader().getClient()));
            db.close();

            aCuentaTxtVw = (TextView) view.findViewById(R.id.quantity);
            aCuentaAbonadaTxtVw = (TextView) view.findViewById(R.id.abonadoQty);
            crepsListView = (ListView)view.findViewById(R.id.lvCreps);
            listViewCrepAdapter = new ListViewMultipleCrepAdapter(getContext(), creps);
            goApplyPaymentBtn = (Button)view.findViewById(R.id.applyBtn);

            crepsListView.setAdapter(listViewCrepAdapter);

            aCuentaTxtVw.setText(CurrentData.getActualMultiplePaymentHeader().getAmount().toString());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        goApplyPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }


    private String getClientCode(String clientAux)
    {
        return clientAux.substring(clientAux.indexOf("<") + 1, clientAux.indexOf(">"));
    }

}
