package com.example.admin.iposapp.controler;


import android.content.Context;
import android.content.EntityIterator;
import android.os.Bundle;
import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;
import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltipUtils;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.model.Client;
import com.example.admin.iposapp.utility.CurrentData;

import de.halfbit.fabuless.FabView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddClientGeneralInfoFragment extends BaseFragment
{
    private EditText clientNameEt;
    private EditText clientAdressEt;
    private EditText clientIntNumberEt;
    private EditText clientExtNumber;
    private EditText clientNeighborhoodEt;
    private EditText clientCityEt;
    private EditText clientStateEt;
    private EditText clientPcEt;
    private EditText clientRfcEt;
    private EditText clientEmail1Et;
    private EditText clientEmail2Et;
    private EditText clientPhone1Et;
    private EditText clientPhone2Et;
    private EditText clientContact1Et;
    private EditText clientContact2Et;
    private EditText clientStreetsEt;
    private FabView floatingSaveButton;

    public AddClientGeneralInfoFragment()
    {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(
                R.layout.fragment_add_client_general_info,
                container,
                false);

        floatingSaveButton = (FabView) rootView.findViewById(R.id.fab);
        clientNameEt = (EditText) rootView.findViewById(R.id.clientNameEt);
        clientAdressEt = (EditText) rootView.findViewById(R.id.clientAdressEt);
        clientIntNumberEt = (EditText) rootView.findViewById(R.id.clientIntNumber);
        clientExtNumber = (EditText) rootView.findViewById(R.id.clientExtNumber);
        clientNeighborhoodEt = (EditText) rootView.findViewById(R.id.clientColonyEt);
        clientCityEt = (EditText) rootView.findViewById(R.id.clientCityEt);
        clientStateEt = (EditText) rootView.findViewById(R.id.clientStateEt);
        clientPcEt = (EditText) rootView.findViewById(R.id.clientPostalCodeEt);
        clientRfcEt = (EditText) rootView.findViewById(R.id.clientRfcEt);
        clientEmail1Et = (EditText) rootView.findViewById(R.id.clientMail1Et);
        clientEmail2Et = (EditText) rootView.findViewById(R.id.clientMail2Et);
        clientContact1Et = (EditText) rootView.findViewById(R.id.clientContact1Et);
        clientContact2Et = (EditText) rootView.findViewById(R.id.clientContact2Et);
        clientPhone1Et = (EditText) rootView.findViewById(R.id.clientPhone1Et);
        clientPhone2Et = (EditText) rootView.findViewById(R.id.clientPhone2Et);
        clientStreetsEt = (EditText) rootView.findViewById(R.id.clientInBetweenStreets);

        floatingSaveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                saveInfo();

                AddClientPaymentInfoFragment newFragment = new AddClientPaymentInfoFragment();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.fragmentContainer, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });
        clientNameEt.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(hasFocus)
                {
                    final SimpleTooltip tooltip = new SimpleTooltip.Builder(getContext())

                            .anchorView(clientNameEt)
                            .text("RELLENE LOS CAMPOS PARA ALTA DE CLIENTE")
                            .textColor(android.R.color.white)
                            .gravity(Gravity.BOTTOM)
                            .dismissOnOutsideTouch(false)
                            .dismissOnInsideTouch(false)
                            .modal(true)
                            .animated(true)
                            .animationDuration(2000)
                            .animationPadding(SimpleTooltipUtils.pxFromDp(25))
                            .contentView(R.layout.tooltip_custom, R.id.tv_text)
                            .build();
                    tooltip.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v2) {
                            if (tooltip.isShowing())
                                tooltip.dismiss();
                            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                        }
                    });
                    tooltip.show();
                }
            }
        });
        clientNameEt.requestFocus();
        return rootView;
    }

    public void saveInfo()
    {
        Client client = new Client();

        client.setNombre(clientNameEt.getText().toString());
        client.setDomicilio(clientAdressEt.getText().toString());
        client.setNumeroExterior(clientExtNumber.getText().toString());
        client.setNumeroInterior(clientIntNumberEt.getText().toString());
        client.setColonia(clientNeighborhoodEt.getText().toString());
        client.setCiudad(clientCityEt.getText().toString());
        client.setEstado(clientStateEt.getText().toString());
        client.setCodigoPostal(clientPcEt.getText().toString());
        client.setRfc(clientRfcEt.getText().toString());
        client.setEmail1(clientEmail1Et.getText().toString());
        client.setEmail2(clientEmail2Et.getText().toString());
        client.setContacto1(clientContact1Et.getText().toString());
        client.setContacto2(clientContact2Et.getText().toString());
        client.setTelefono1(clientPhone1Et.getText().toString());
        client.setTelefono2(clientPhone2Et.getText().toString());
        client.setCalles(clientStreetsEt.getText().toString());

        CurrentData.setClient(client);
        Log.w("GENERAL INFO: ", "SAVED");
    }

}
