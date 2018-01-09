package com.example.admin.iposapp.controler;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.listeners.SinglePaymentDialogCloseListener;
import com.example.admin.iposapp.model.Bank;
import com.example.admin.iposapp.model.Client;
import com.example.admin.iposapp.model.MultiplePaymentHeader;
import com.example.admin.iposapp.utility.AutoCompleteContentProvider;
import com.example.admin.iposapp.utility.CurrentData;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NextPaymentFragment extends DialogFragment {

    private Button goApplyPaymentBtn;
    private AutoCompleteTextView filterAutoComTxtVw;
    private ArrayList<Client> clients;
    private Database db;

    private SinglePaymentDialogCloseListener callback;

    public NextPaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(
                R.layout.fragment_next_payment,
                container,
                false);

        db = new Database(this.getContext());
        db.open();
        clients = (ArrayList<Client>) Database.clientDao.fetchAllClients();
        db.close();
        goApplyPaymentBtn = (Button)view.findViewById(R.id.applyBtn);

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
                /*ClientBalance clientBalance = new ClientBalance();
                clientBalance = Database.clientBalanceDAO.fetchClientBalanceByClient(getClientCode(filterAutoComTxtVw.getText().toString()));

                if(clientBalance != null)
                {
                    if(Float.parseFloat(clientBalance.getBalance()) > 0)
                    {
                        new AlertDialog.Builder(getContext())
                                .setTitle("Existe un saldo a favor del cliente")
                                .setMessage("desea utilizarlo?")
                                .setCancelable(false)
                                .setPositiveButton("Si", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        try
                                        {

                                        }
                                        catch (Exception e)
                                        {
                                            e.printStackTrace();
                                        }

                                    }
                                })
                                .setNegativeButton("No", null).show();
                    }
                }*/
            }


        });

        goApplyPaymentBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!clientExist(filterAutoComTxtVw.getText().toString()))
                {
                    Toast.makeText(
                            getContext(),
                            "Cliente inexistente, utilice el autocompletado!",
                            Toast.LENGTH_LONG).show();
                    filterAutoComTxtVw.requestFocus();
                    return;
                }
                    getDialog().dismiss();
                }
            }));

        return view;

    }

    public void onDismiss(DialogInterface dialog){
        CurrentData.setNextPayment(filterAutoComTxtVw.getText().toString());
        callback = (SinglePaymentDialogCloseListener)getTargetFragment();
        callback.handleDialogClose("APPLY");
    }

    private boolean clientExist(String clientSearch)
    {
        for(int i = 0; i < clients.size(); i++)
        {
            if(clients.get(i).getClave().equals(getClientCode(clientSearch)))
            {
                return true;
            }
        }
        return false;
    }

    private String getClientCode(String clientAux)
    {
        if(clientAux.contains("<") && clientAux.contains(">"))
        {
            return clientAux.substring(clientAux.indexOf("<") + 1, clientAux.indexOf(">"));
        }
        else
        {
            return clientAux;
        }
    }

}
