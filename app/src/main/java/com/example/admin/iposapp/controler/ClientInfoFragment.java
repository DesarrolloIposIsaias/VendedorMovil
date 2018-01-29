package com.example.admin.iposapp.controler;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.database.ClientDAO;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.Client;
import com.example.admin.iposapp.utility.CurrentData;
import com.example.admin.iposapp.utility.ShowClientsPagerAdapter;

import org.w3c.dom.Text;

import java.util.Locale;

/**
     * A simple {@link Fragment} subclass.
     */
    public class ClientInfoFragment extends DialogFragment {

        private Dialog dialog;
        private TextView clientName;
        private TextView address;
        private TextView colony;
        private TextView city;
        private TextView state;
        private TextView country;
        private TextView postalCode;
        private TextView RFC;
        private TextView firstMail;
        private TextView secondMail;
        private TextView firstPhone;
        private TextView secondPhone;
        private TextView firstContact;
        private TextView secondContact;

        public ClientInfoFragment() {
            // Required empty public constructor
        }


        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Inflate the layout for this fragment

            LayoutInflater inflater;
            View view, viewCustom;
            Context context;

            inflater = getActivity().getLayoutInflater();
            view = inflater.inflate(R.layout.fragment_client_info, null);
            context = view.getContext();

            try {
                viewCustom = inflater.inflate(R.layout.fragment_client_info, null);

                clientName = (TextView) view.findViewById(R.id.tvClientName);
                address = (TextView) view.findViewById(R.id.tvClientAdress);
                colony = (TextView) view.findViewById(R.id.tvClientColony);
                city = (TextView) view.findViewById(R.id.tvCity);
                state = (TextView) view.findViewById(R.id.tvState);
                country = (TextView) view.findViewById(R.id.tvCountry);
                postalCode = (TextView) view.findViewById(R.id.tvCodePostal);
                RFC = (TextView) view.findViewById(R.id.tvRFC);
                firstMail = (TextView) view.findViewById(R.id.tvFirstMail);
                secondMail = (TextView) view.findViewById(R.id.tvSecondMail);
                firstContact = (TextView) view.findViewById(R.id.tvFirstContact);
                secondContact = (TextView) view.findViewById(R.id.tvSecondContact);
                firstPhone = (TextView) view.findViewById(R.id.tvFirstPhone);
                secondPhone = (TextView) view.findViewById(R.id.tvSecondPhone);

                loadData();
            }
            catch (Exception e) {
                Toast.makeText(
                        getContext(),
                        "Problema al crear la vista",
                        Toast.LENGTH_SHORT
                ).show();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(view);

            dialog = builder.create();
            return dialog;
        }

    private void loadData(){
        if(CurrentData.getSelectedClient() != null){
            String clientNameStr =
                    "Cliente: " + CurrentData.getSelectedClient().getNombre();
            clientName.setText(clientNameStr);

            String addressStr =
                    "Direccion: " + CurrentData.getSelectedClient().getDomicilio();
            address.setText(addressStr);

            String colonyStr =
                    "Colonia: " + CurrentData.getSelectedClient().getColonia();
            colony.setText(colonyStr);

            String cityStr =
                    "Ciudad: " + CurrentData.getSelectedClient().getCiudad();
            city.setText(cityStr);

            String stateStr =
                    "Estado: " + CurrentData.getSelectedClient().getEstado();
            state.setText(stateStr);

            String countryStr =
                    "Pais: " + CurrentData.getSelectedClient().getPais();
            country.setText(countryStr);

            String postalCodeStr =
                    "Codigo postal: " + CurrentData.getSelectedClient().getCodigoPostal();
            postalCode.setText(postalCodeStr);

            String RFCStr =
                    "RFC: " + CurrentData.getSelectedClient().getRfc();
            RFC.setText(RFCStr);

            if(CurrentData.getSelectedClient().getEmail1() == null){
                String firstMailStr =
                        "Email 1: No existe";
                firstMail.setText(firstMailStr);
            }else{
                String firstMailStr =
                        "Email 1: " + CurrentData.getSelectedClient().getEmail1();
                firstMail.setText(firstMailStr);
            }


            if(CurrentData.getSelectedClient().getEmail2() == null) {
                String secondMailStr =
                        "Email 2: No existe" ;
                secondMail.setText(secondMailStr);
            }else{
                String secondMailStr =
                        "Email 2: " + CurrentData.getSelectedClient().getEmail2();
                secondMail.setText(secondMailStr);
            }


            if(CurrentData.getSelectedClient().getContacto1() == null) {
                String firstContactStr =
                        "Contacto 1: No existe";
                firstContact.setText(firstContactStr);
            }else{
                String firstContactStr =
                        "Contacto 1: " + CurrentData.getSelectedClient().getContacto1();
                firstContact.setText(firstContactStr);
            }

            if(CurrentData.getSelectedClient().getContacto2() == null) {
                String secondContactStr =
                        "Contacto 2: No existe";
                secondContact.setText(secondContactStr);
            }else{
                String secondContactStr =
                        "Contacto 2: " + CurrentData.getSelectedClient().getContacto2();
                secondContact.setText(secondContactStr);
            }

            if(CurrentData.getSelectedClient().getTelefono1() == null) {
                String firstPhoneStr =
                        "Telefono 1: No existe";
                firstPhone.setText(firstPhoneStr);
            }else{
                String firstPhoneStr =
                        "Telefono 1: " + CurrentData.getSelectedClient().getTelefono1();
                firstPhone.setText(firstPhoneStr);
            }


            if(CurrentData.getSelectedClient().getTelefono2() == null) {
                String secondPhoneStr =
                        "Telefono 2: No existe";
                secondPhone.setText(secondPhoneStr);
            }else{
                String secondPhoneStr =
                        "Telefono 2: " + CurrentData.getSelectedClient().getTelefono2();
                secondPhone.setText(secondPhoneStr);
            }

        }
    }
}

