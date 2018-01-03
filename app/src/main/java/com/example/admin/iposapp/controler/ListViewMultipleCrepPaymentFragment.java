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
import com.example.admin.iposapp.model.Payment;
import com.example.admin.iposapp.model.PaymentDetail;
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
    private Database db;
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

            db = new Database(this.getContext());
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
                if(Float.parseFloat(aCuentaTxtVw.getText().toString()) >= Float.parseFloat(aCuentaAbonadaTxtVw.getText().toString())) {
                    try {
                        Payment payment;


                        db.open();
                        db.beginTransaction();

                        boolean success;
                        payment = Database.paymentDAO.getLastInserted();

                        if (payment == null)
                            throw new Exception("Problema al obtener ultimo registro insertado");

                        PaymentDetail paymentDetail;

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

    private String getClientCode(String clientAux)
    {
        return clientAux.substring(clientAux.indexOf("<") + 1, clientAux.indexOf(">"));
    }

}
