package com.example.admin.iposapp.controler;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.Client;
import com.example.admin.iposapp.utility.CurrentData;

import java.util.Locale;

import de.halfbit.fabuless.FabView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddClientExtraInfoFragment extends Fragment
{
    private EditText clientIeps;
    private CheckBox clientHasDelivery;
    private CheckBox clientHasIeps;
    private FabView floatingSaveButton;
    Database db;

    public AddClientExtraInfoFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(
                R.layout.fragment_add_client_extra_info,
                container,
                false
        );

        db = new Database(getContext());

        clientIeps = (EditText) view.findViewById(R.id.etIepsAccount);
        clientHasDelivery = (CheckBox) view.findViewById(R.id.checkBoxAddresDelivery);
        clientHasIeps = (CheckBox) view.findViewById(R.id.checkBoxIeps);
        floatingSaveButton = (FabView) view.findViewById(R.id.fab);

        floatingSaveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                saveInfo();
                db.open();
                Client client = CurrentData.getClient();
                if(Database.clientDao.isEmpty())
                {
                    client.setClave(client.getSerie() + "0001");
                    CurrentData.incrementLastClient();
                }
                else
                {
                    CurrentData.incrementLastClient();
                    client.setClave(
                            client.getSerie() +
                                    String.format(
                                            Locale.ENGLISH,
                                            "%04d",
                                            CurrentData.getLastClientInserted()));
                }

                if(Database.clientDao.addClient(CurrentData.getClient()))
                {
                    //db.open();
                    if(Database.settingsDAO.updateLasClient(
                            CurrentData.getLastClientInserted(),
                            CurrentData.getSettings().getAppUser()))
                    {
                        Log.w("UPDATE", "Last Client Updated");
                    }

                    //db.close();
                    Toast.makeText(
                            getContext(),
                            "Insercion correcta",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {

                    CurrentData.decrementLastClient();
                    Toast.makeText(
                            getContext(),
                            "Problema al insertar",
                            Toast.LENGTH_SHORT).show();
                }
                CurrentData.getSettings().setClientSerie(
                        String.valueOf(CurrentData.getLastClientInserted())
                );

                db.close();

                ClientMenuFragment newFragment = new ClientMenuFragment();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.fragmentContainer, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        return view;
    }

    public void saveInfo()
    {
        Client client = CurrentData.getClient();

        if(clientHasIeps.isChecked()) client.setCuentaIeps(clientIeps.getText().toString());
        else client.setCuentaIeps("NA");
        if(clientHasDelivery.isChecked())client.setServicioDomicilio("S");
        else client.setServicioDomicilio("N");
        client.setSerie(CurrentData.getSettings().getSellerSerie());

        CurrentData.setClient(client);

        Log.w("EXTRA INFO: ", "SAVED");
    }

}
