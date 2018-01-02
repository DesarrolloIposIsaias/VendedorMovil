package com.example.admin.iposapp.controler;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.listeners.SinglePaymentDialogCloseListener;
import com.example.admin.iposapp.model.Payment;
import com.example.admin.iposapp.model.PaymentDetail;
import com.example.admin.iposapp.utility.CurrentData;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import info.hoang8f.widget.FButton;

public class SinglePaymentFragment extends DialogFragment {

    // TODO: Rename and change types of parameters
    private FButton apply, cancel, dateTimePicker;
    private EditText monto, reference, notes;
    private Spinner depositSpinner, paymentMethodSpinner, banksSpinner;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TextView dateTextView, client;
    private Database database;
    private Dialog dialog;

    private SinglePaymentDialogCloseListener callback;

    public SinglePaymentFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstance){
        LayoutInflater inflater;
        View view;

        inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_single_payment, null);

        try
        {
            database = new Database(getContext());

            apply = (FButton) view.findViewById(R.id.btnApply);
            cancel = (FButton) view.findViewById(R.id.btnCancel);
            dateTimePicker = (FButton) view.findViewById(R.id.button_date_picker);
            dateTextView = (TextView) view.findViewById(R.id.text_view_date);
            client = (TextView) view.findViewById(R.id.client);
            monto = (EditText) view.findViewById(R.id.monto);
            reference = (EditText) view.findViewById(R.id.reference);
            notes = (EditText) view.findViewById(R.id.notes);
            depositSpinner = (Spinner) view.findViewById(R.id.deposit);
            paymentMethodSpinner = (Spinner) view.findViewById(R.id.payForm);
            banksSpinner = (Spinner) view.findViewById(R.id.bank);

            apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        Payment payment = createPayment();

                        if (payment == null)
                            throw new Exception("Problema al obtener datos de pago");

                        database.open();
                        database.beginTransaction();

                        boolean success = Database.paymentDAO.addPayment(payment);

                        if(!success)
                            throw new Exception("Problema al agregar pago");

                        payment = Database.paymentDAO.getLastInserted();

                        if(payment == null)
                            throw new Exception("Problema al obtener ultimo registro insertado");

                        PaymentDetail paymentDetail = createPaymentDetail(payment.getId());

                        if(paymentDetail == null)
                            throw new Exception("Problema al craer el detalle del pago");

                        success = Database.paymentDetailDAO.addPaymentDetail(paymentDetail);

                        if(!success)
                            throw new Exception("Problema al agregar detalle de pago");

                        success = Database.crepDAO.updateCrepBalance(
                                CurrentData.getSelectedCrep().getSaldo(),
                                Float.valueOf(monto.getText().toString()),
                                CurrentData.getSelectedCrep().getVenta()
                        );

                        if(!success)
                            throw new Exception("Problema al actualizar saldo de venta");

                        database.commitTransaction();

                        Toast.makeText(
                                getContext(),
                                "Proceso exitoso",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                    catch (Exception e){
                        Toast.makeText(
                                getContext(),
                                e.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                    finally {
                        database.closeTransaction();
                        database.close();
                        getDialog().dismiss();
                    }
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDialog().dismiss();
                }
            });

            dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month = month + 1;
                    Log.d(
                            getTag(),
                            "onDataSet: dd/mm/yyyy: " + day + " / " + month + " / " + year
                    );

                    String date = day + "/" + month + "/" + year;
                    dateTextView.setText(date);
                }
            };

            dateTimePicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(
                            getContext(),
                            R.style.Theme_AppCompat_Light_Dialog_MinWidth,
                            dateSetListener,
                            year,
                            month,
                            day
                    );

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                    dialog.show();
                }
            });

            loadData();
        }
        catch (Exception e) {
            Toast.makeText(getContext(), "Problema al crear vista", Toast.LENGTH_LONG).show();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        dialog = builder.create();

        return dialog;
    }

    @Override
    public Dialog getDialog(){
        return dialog;
    }

    private void loadData(){
        database.open();
        ArrayList<String> banks = Database.bankDAO.fetchBanksNames();
        database.close();

        //load spinners data
        loadBanks(banks);
        loadPaymentMethods();

        //load client to textview
        client.setText(CurrentData.getSelectedCrep().getNombre());
    }

    private void loadBanks(ArrayList<String> banks){
        ArrayAdapter<String> banksAdapter =
                new ArrayAdapter<String>(getContext(), R.layout.white_black_spinner_textview, banks);

        banksAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        banksSpinner.setAdapter(banksAdapter);
    }

    private Payment createPayment(){

        try{
            Payment payment = new Payment();
            payment.setFolioDeposito(reference.getText().toString());
            payment.setIntereses(String.valueOf(CurrentData.getSelectedCrep().getIntereses()));
            payment.setSaldo(String.valueOf(CurrentData.getSelectedCrep().getSaldo()));
            payment.setFecha(dateTextView.getText().toString());
            payment.setBanco(banksSpinner.getSelectedItem().toString());
            payment.setVenta(CurrentData.getSelectedCrep().getVenta());
            payment.setTipo(paymentMethodSpinner.getSelectedItem().toString());
            payment.setImporte(monto.getText().toString());

            return payment;
        }
        catch (Exception e){

            return null;
        }
    }

    private PaymentDetail createPaymentDetail(String pago){

        try{
            PaymentDetail paymentDetail = new PaymentDetail();

            paymentDetail.setIntereses(String.valueOf(CurrentData.getSelectedCrep().getIntereses()));
            paymentDetail.setSaldo(String.valueOf(CurrentData.getSelectedCrep().getSaldo()));
            paymentDetail.setFecha(dateTextView.getText().toString());
            paymentDetail.setVenta(CurrentData.getSelectedCrep().getVenta());
            paymentDetail.setFecha(dateTextView.getText().toString());
            paymentDetail.setPago(pago);
            paymentDetail.setAbono(monto.getText().toString());

            return paymentDetail;
        }
        catch (Exception e){

            return null;
        }
    }

    private void loadPaymentMethods(){
        ArrayAdapter<CharSequence> paymentFormsAdapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.paymentForms,
                R.layout.white_black_spinner_textview
        );

        paymentFormsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentMethodSpinner.setAdapter(paymentFormsAdapter);
    }

    public void onDismiss(DialogInterface dialog){
        callback = (SinglePaymentDialogCloseListener)getTargetFragment();
        callback.handleDialogClose("APPLY");
    }
}
