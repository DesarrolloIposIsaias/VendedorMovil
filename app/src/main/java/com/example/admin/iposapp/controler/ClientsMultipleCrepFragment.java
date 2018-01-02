package com.example.admin.iposapp.controler;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.Bank;
import com.example.admin.iposapp.model.Client;
import com.example.admin.iposapp.model.Crep;
import com.example.admin.iposapp.model.MultiplePaymentHeader;
import com.example.admin.iposapp.utility.AutoCompleteContentProvider;
import com.example.admin.iposapp.utility.CurrentData;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientsMultipleCrepFragment extends Fragment {

    private Button goNextBtn;
    private Button depositDate;
    private TextView dateTxtVw;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView montoTxtVw;
    private Spinner bankSpinner;
    private Spinner payFormSpinner;
    private AutoCompleteTextView filterAutoComTxtVw;
    private TextView referenciasTxtVw;
    private TextView notasTxtVw;
    private ArrayList<Client> clients;
    private List<Bank> banks;
    private ArrayList<String> banksName;
    public MultiplePaymentHeader paymentHeader;


    static final int DIALOG_ID = 0;

    public ClientsMultipleCrepFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(
                R.layout.fragment_clients_multiple_crep,
                container,
                false);

        Database db = new Database(this.getContext());
        db.open();
        clients = (ArrayList<Client>) Database.clientDao.fetchAllClients();
        banks = Database.bankDAO.fetchAllBanks();
        db.close();

        goNextBtn = (Button) view.findViewById(R.id.nextStep);
        depositDate = (Button) view.findViewById(R.id.datePicker);
        montoTxtVw = (TextView) view.findViewById(R.id.monto);
        bankSpinner = (Spinner) view.findViewById(R.id.bank);
        payFormSpinner = (Spinner) view.findViewById(R.id.payForm);
        dateTxtVw = (TextView) view.findViewById(R.id.date);
        referenciasTxtVw = (TextView) view.findViewById(R.id.reference);
        notasTxtVw = (TextView) view.findViewById(R.id.notes);

        final ArrayAdapter<CharSequence> paymentFormsAdapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.paymentForms,
                R.layout.white_black_spinner_textview);

        fillBanks();
        ArrayAdapter<String> bankAdapter =
                new ArrayAdapter<String>(getContext(), R.layout.white_black_spinner_textview, banksName);

        paymentFormsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bankAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        payFormSpinner.setAdapter(paymentFormsAdapter);
        bankSpinner.setAdapter(bankAdapter);

        depositDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        R.style.Theme_AppCompat_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(
                        getTag(),
                        "onDataSet: dd/mm/yyyy: " + day + " / " + month + " / " + year
                );

                String date = day + "/" + month + "/" + year;
                dateTxtVw.setText(date);
            }
        };


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

        goNextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(validateFields())
                {
                    paymentHeader = new MultiplePaymentHeader();
                    paymentHeader.setClient(filterAutoComTxtVw.getText().toString());
                    paymentHeader.setDate(dateTxtVw.getText().toString());
                    paymentHeader.setAmount(Float.parseFloat(montoTxtVw.getText().toString()));
                    paymentHeader.setPaymentType(payFormSpinner.getSelectedItem().toString());
                    if(paymentHeader.getPaymentType().trim() != "EFECTIVO")
                    {
                        paymentHeader.setBank(bankSpinner.getSelectedItem().toString());
                        if(referenciasTxtVw.length() > 0)
                        {
                            paymentHeader.setReference(referenciasTxtVw.getText().toString());
                        }
                        if(notasTxtVw.length() > 0)
                        {
                            paymentHeader.setNotes(notasTxtVw.getText().toString());
                        }
                    }
                    CurrentData.setActualMultiplePaymentHeader(paymentHeader);
                    ListViewMultipleCrepPaymentFragment cmcFragment =
                            new ListViewMultipleCrepPaymentFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer,
                            cmcFragment,
                            cmcFragment.getTag()).commit();
                }
            }
        });


        payFormSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i > 0)
                {
                    bankSpinner.setVisibility(View.VISIBLE);
                    bankSpinner.setEnabled(true);
                    referenciasTxtVw.setVisibility(View.VISIBLE);
                    referenciasTxtVw.setEnabled(true);
                    notasTxtVw.setVisibility(View.VISIBLE);
                    notasTxtVw.setEnabled(true);
                }
                else
                {
                    bankSpinner.setVisibility(View.GONE);
                    bankSpinner.setEnabled(false);
                    referenciasTxtVw.setVisibility(View.GONE);
                    referenciasTxtVw.setEnabled(false);
                    notasTxtVw.setVisibility(View.GONE);
                    notasTxtVw.setEnabled(false);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        return view;
    }

    private boolean validateFields()
    {
        if(montoTxtVw.length() <= 0 || Float.parseFloat(montoTxtVw.getText().toString()) <= 0 )
        {
            Toast.makeText(
                    getContext(),
                    "El monto es un campo requerido!",
                    Toast.LENGTH_LONG).show();
            montoTxtVw.requestFocus();
            return false;
        }
        else if(dateTxtVw.length() <= 0)
        {
            Toast.makeText(
                    getContext(),
                    "La fecha es un campo requerido!",
                    Toast.LENGTH_LONG).show();
            dateTxtVw.requestFocus();
            return false;
        }
        return true;
    }

    private void fillBanks()
    {
        banksName = new ArrayList<String>();
        for (Bank item : banks)
        {
            banksName.add(item.getNombre());
        }
    }

}
