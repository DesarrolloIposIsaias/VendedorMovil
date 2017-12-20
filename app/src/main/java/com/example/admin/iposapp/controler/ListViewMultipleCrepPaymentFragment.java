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
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.Client;
import com.example.admin.iposapp.model.Crep;
import com.example.admin.iposapp.utility.AutoCompleteContentProvider;
import com.example.admin.iposapp.utility.CurrentData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewMultipleCrepPaymentFragment extends Fragment {

    private Button goApplyPaymentBtn;
    private ListView crepsListView;
    private ArrayList<Crep> creps;
    private ListViewMultipleCrepAdapter listViewCrepAdapter;
    private TextView aCuentaTxtVw;


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
            /*creps.add(new Crep("1234", "4321"));
            creps.add(new Crep("4567", "7654"));*/
            Database db = new Database(this.getContext());
            db.open();
            creps = (ArrayList<Crep>) Database.crepDAO.fetchAllCreps();
            db.close();

            aCuentaTxtVw = (TextView) view.findViewById(R.id.quantity);

            crepsListView = (ListView)view.findViewById(R.id.lvCreps);
            listViewCrepAdapter = new ListViewMultipleCrepAdapter(getContext(), creps);
            crepsListView.setAdapter(listViewCrepAdapter);

            aCuentaTxtVw.setText(CurrentData.getActualMultiplePaymentHeader().getAmount().toString());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        /*filterAutoComTxtVw = (AutoCompleteTextView)view.findViewById(R.id.crepAutoCompleteTextView);
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
        });*/

        return view;
    }

}
