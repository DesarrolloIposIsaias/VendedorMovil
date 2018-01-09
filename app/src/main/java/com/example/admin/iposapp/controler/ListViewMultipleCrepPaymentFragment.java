package com.example.admin.iposapp.controler;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import com.example.admin.iposapp.listeners.SinglePaymentDialogCloseListener;
import com.example.admin.iposapp.model.ClientBalance;
import com.example.admin.iposapp.model.Crep;
import com.example.admin.iposapp.model.MultiplePaymentHeader;
import com.example.admin.iposapp.model.Payment;
import com.example.admin.iposapp.model.PaymentDetail;
import com.example.admin.iposapp.utility.AutoCompleteContentProvider;
import com.example.admin.iposapp.utility.CurrentData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewMultipleCrepPaymentFragment extends Fragment implements SinglePaymentDialogCloseListener{

    private Button goApplyPaymentBtn;
    public static ListView crepsListView;
    private ArrayList<Crep> creps;
    private ListViewMultipleCrepAdapter listViewCrepAdapter;
    public static TextView aCuentaTxtVw;
    public static TextView aCuentaAbonadaTxtVw;
    public static TextView aCuentaRestanteTxtVw;
    private Database db;
    private EditText auxEditText;
    private PaymentDetail paymentDetailAnticipo;
    private Fragment parent;



    public ListViewMultipleCrepPaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_view_multiple_crep_payment, container, false);

        try{
            parent = this;

            creps = new ArrayList<>();

            db = new Database(this.getContext());
            db.open();
            creps = (ArrayList<Crep>) Database.crepDAO.fetchCrepsByClientList(getClientCode(CurrentData.getActualMultiplePaymentHeader().getClient()));
            db.close();

            aCuentaTxtVw = (TextView) view.findViewById(R.id.quantity);
            aCuentaAbonadaTxtVw = (TextView) view.findViewById(R.id.abonadoQty);
            aCuentaRestanteTxtVw = (TextView) view.findViewById(R.id.restantes);
            crepsListView = (ListView)view.findViewById(R.id.lvCreps);
            listViewCrepAdapter = new ListViewMultipleCrepAdapter(getContext(), creps);
            goApplyPaymentBtn = (Button)view.findViewById(R.id.applyBtn);

            crepsListView.setAdapter(listViewCrepAdapter);

            aCuentaTxtVw.setText(CurrentData.getActualMultiplePaymentHeader().getAmount().toString());
            aCuentaRestanteTxtVw.setText(CurrentData.getActualMultiplePaymentHeader().getAmount().toString());
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        goApplyPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Float.parseFloat(aCuentaTxtVw.getText().toString()) >= Float.parseFloat(aCuentaAbonadaTxtVw.getText().toString())
                        && aCuentaAbonadaTxtVw.getText().length() > 0) {

                    if(Float.parseFloat(aCuentaAbonadaTxtVw.getText().toString()) <= 0)
                    {
                        Toast.makeText(
                                getContext(),
                                "No ha realizado abonos!",
                                Toast.LENGTH_SHORT
                        ).show();
                        return;
                    }

                    try {
                        Payment payment;


                        db.open();
                        db.beginTransaction();

                        boolean success;
                        payment = Database.paymentDAO.getLastInserted();

                        if (payment == null)
                            throw new Exception("Problema al obtener ultimo registro insertado");

                        PaymentDetail paymentDetail = new PaymentDetail();

                        for (int i = 0; i < listViewCrepAdapter.getCount(); i++) {
                            paymentDetail = new PaymentDetail();
                            View v = listViewCrepAdapter.getView(i, ListViewMultipleCrepPaymentFragment.crepsListView.getChildAt(i), ListViewMultipleCrepPaymentFragment.crepsListView);
                            auxEditText = (EditText) v.findViewById(R.id.crep_abono);
                            if (auxEditText != null && auxEditText.getText().length() > 0) {
                                paymentDetail = createPaymentDetail(payment.getId(), auxEditText.getText().toString(), i);
                                if (paymentDetail == null) {
                                    throw new Exception("Problema al craer el detalle del pago");
                                }

                                success = Database.paymentDetailDAO.addPaymentDetail(paymentDetail);

                                if (!success) {
                                    throw new Exception("Problema al agregar detalle de pago");
                                }

                                paymentDetailAnticipo = paymentDetail;

                                success = Database.crepDAO.updateCrepBalance(
                                        creps.get(i).getSaldo(),
                                        Float.valueOf(auxEditText.getText().toString()),
                                        creps.get(i).getVenta()
                                );

                                if (!success) {
                                    throw new Exception("Problema al actualizar saldo de venta");
                                }
                            }

                        }

                        if(Float.parseFloat(aCuentaTxtVw.getText().toString()) > Float.parseFloat(aCuentaAbonadaTxtVw.getText().toString()))
                        {
                            paymentDetailAnticipo.setAnticipo("S");
                            String currentAnticipo = Float.toString(Float.parseFloat(aCuentaTxtVw.getText().toString()) - Float.parseFloat(aCuentaAbonadaTxtVw.getText().toString()));
                            paymentDetailAnticipo.setAbono(currentAnticipo);


                            CharSequence options[] = new CharSequence[] {"Agregar saldo a favor del cliente", "Realizar pagos de otro cliente"};

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setCancelable(false);
                            builder.setTitle("Hay un saldo a favor, que desea hacer:");
                            builder.setItems(options, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // the user clicked on options[which]
                                    if(which == 0)
                                    {
                                        db = new Database(getContext());
                                        db.open();
                                        boolean successBalance = db.paymentDetailDAO.addPaymentDetail(paymentDetailAnticipo);
                                        db.close();


                                        if (!successBalance) {
                                            //throw new Exception("Problema al agregar detalle de pago");
                                            Toast.makeText(
                                                    getContext(),
                                                    "Error en el proceso!",
                                                    Toast.LENGTH_SHORT
                                            ).show();
                                        }
                                        else
                                        {
                                            Toast.makeText(
                                                    getContext(),
                                                    "Procesado con exito!",
                                                    Toast.LENGTH_SHORT
                                            ).show();
                                        }

                                        getFragmentManager().popBackStack();

                                        /*Float saldoAFavor = Float.parseFloat(aCuentaTxtVw.getText().toString()) - Float.parseFloat(aCuentaAbonadaTxtVw.getText().toString());
                                        ClientBalance clientBalance = new ClientBalance();
                                        clientBalance.setClient(getClientCode(CurrentData.getActualMultiplePaymentHeader().getClient()));
                                        clientBalance.setBalance(saldoAFavor.toString());

                                        boolean successBalance = Database.clientBalanceDAO.addClientBalance(clientBalance);

                                        if (!successBalance) {
                                            //throw new Exception("Problema al agregar detalle de pago");
                                            Toast.makeText(
                                                    getContext(),
                                                    "Error en el proceso!",
                                                    Toast.LENGTH_SHORT
                                            ).show();
                                        }*/
                                    }
                                    else if(which == 1)
                                    {
                                        NextPaymentFragment nextPaymentFragment = new NextPaymentFragment();
                                        nextPaymentFragment.setTargetFragment(parent, 1);
                                        nextPaymentFragment.show(
                                                ((FragmentActivity)getContext()).getSupportFragmentManager(),
                                                "next_payment");
                                        //aqui josue

                                    }
                                }
                            });
                            builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //the user clicked on Cancel
                                }
                            });
                            builder.show();
                        }

                        db.commitTransaction();

                        Toast.makeText(
                                getContext(),
                                "Proceso exitoso",
                                Toast.LENGTH_SHORT
                        ).show();
                    } catch (Exception e) {
                        Toast.makeText(
                                getContext(),
                                e.getMessage(),
                                Toast.LENGTH_SHORT
                        ).show();
                    } finally {
                        db.closeTransaction();
                        db.close();
                        //getDialog().dismiss();
                    }
                }
                else
                {
                    Toast.makeText(
                            getContext(),
                            "La cantidad abonada es mayor a la cantidad del pago",
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });

        return view;
    }

    private PaymentDetail createPaymentDetail(String pago, String selectedAmount, int actualPosition){

        try{
            PaymentDetail paymentDetail = new PaymentDetail();

            paymentDetail.setIntereses(String.valueOf(creps.get(actualPosition).getIntereses()));
            paymentDetail.setSaldo(String.valueOf(creps.get(actualPosition).getSaldo()));
            paymentDetail.setFecha(CurrentData.getActualMultiplePaymentHeader().getDate());
            paymentDetail.setVenta(creps.get(actualPosition).getVenta());
            paymentDetail.setFecha(CurrentData.getActualMultiplePaymentHeader().getDate());
            paymentDetail.setPago(pago);
            paymentDetail.setAbono(selectedAmount);

            return paymentDetail;
        }
        catch (Exception e){

            return null;
        }
    }

   /* private PaymentDetail createPaymentAdvanceDetail(String pago, String selectedAmount, int actualPosition){

        try{
            PaymentDetail paymentDetail = new PaymentDetail();

            paymentDetail.setIntereses(String.valueOf(creps.get(actualPosition).getIntereses()));
            paymentDetail.setSaldo(String.valueOf(creps.get(actualPosition).getSaldo()));
            paymentDetail.setFecha(CurrentData.getActualMultiplePaymentHeader().getDate());
            paymentDetail.setVenta(creps.get(actualPosition).getVenta());
            paymentDetail.setFecha(CurrentData.getActualMultiplePaymentHeader().getDate());
            paymentDetail.setPago(pago);
            paymentDetail.setAbono(selectedAmount);

            return paymentDetail;
        }
        catch (Exception e){

            return null;
        }
    }*/

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

    private void clearLisview()
    {
        for (int i = 0; i < listViewCrepAdapter.getCount(); i++) {
            View v = listViewCrepAdapter.getView(i, ListViewMultipleCrepPaymentFragment.crepsListView.getChildAt(i), ListViewMultipleCrepPaymentFragment.crepsListView);
            auxEditText = (EditText) v.findViewById(R.id.crep_abono);
            if (auxEditText != null && auxEditText.getText().length() > 0) {
                auxEditText.setText("");
            }
        }
    }

    @Override
    public void handleDialogClose(String sender) {
        if(sender.equals("APPLY")){
            creps = new ArrayList<>();

            db = new Database(getContext());
            db.open();
            creps = (ArrayList<Crep>) db.crepDAO.fetchCrepsByClientList(getClientCode(CurrentData.getNextPayment()));
            db.close();


            listViewCrepAdapter.getData().clear();
            listViewCrepAdapter.getData().addAll(creps);
            listViewCrepAdapter.notifyDataSetChanged();

            clearLisview();

            CurrentData.getActualMultiplePaymentHeader().setAmount(Float.parseFloat(aCuentaRestanteTxtVw.getText().toString()));

            aCuentaTxtVw.setText(CurrentData.getActualMultiplePaymentHeader().getAmount().toString());
            aCuentaAbonadaTxtVw.setText("");
        }
    }
}
