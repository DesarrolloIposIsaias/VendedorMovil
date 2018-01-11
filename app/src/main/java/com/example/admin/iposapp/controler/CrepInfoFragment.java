package com.example.admin.iposapp.controler;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.utility.CurrentData;

import org.w3c.dom.Text;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class CrepInfoFragment extends DialogFragment {

    private TextView clientTextView;
    private TextView balanceTextView;
    private Dialog dialog;
    private TextView totalTextView;
    private TextView saleTextView;
    private TextView bankTextView;
    private TextView paymentDateTextView;
    private TextView clientNameTextView;

    public CrepInfoFragment() {
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
        view = inflater.inflate(R.layout.fragment_crep_info, null);
        context = view.getContext();

        try
        {
            viewCustom = inflater.inflate(R.layout.fragment_crep_info, null);

            clientTextView = (TextView) view.findViewById(
                    R.id.text_view_client
            );

            balanceTextView = (TextView) view.findViewById(
                    R.id.text_view_balance
            );

            totalTextView = (TextView) view.findViewById(
                    R.id.text_view_total
            );

            saleTextView = (TextView) view.findViewById(
                    R.id.text_view_sale
            );

            bankTextView = (TextView) view.findViewById(
                    R.id.text_view_bank
            );

            paymentDateTextView = (TextView) view.findViewById(
                    R.id.text_view_payment_date
            );

            clientNameTextView = (TextView) view.findViewById(
                    R.id.text_view_client_name
            );

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

    @Override
    public Dialog getDialog(){
        return dialog;
    }

    private void loadData(){
        if(CurrentData.getSelectedCrep() != null){
            String clientStr =
                    "Cliente: " + CurrentData.getSelectedCrep().getNombre();
            clientNameTextView.setText(clientStr);

            String clientKeyStr =
                    "Clave: " + CurrentData.getSelectedCrep().getCliente();
            clientTextView.setText(clientKeyStr);

            String paymentDateStr =
                    "Fecha Pago: " + CurrentData.getSelectedCrep()
                                                .getFechaPago()
                                                .split(" ")[0];
            paymentDateTextView.setText(paymentDateStr);

            String totalStr =
                    "Total: " + String.format(
                            Locale.getDefault(),
                            "%.2f",
                            CurrentData.getSelectedCrep().getTotal()
                    );
            totalTextView.setText(totalStr);

            String balanceStr =
                    "Saldo: " + String.format(
                            Locale.getDefault(),
                            "%.2f",
                            CurrentData.getSelectedCrep().getSaldo()
                    );
            balanceTextView.setText(balanceStr);

            String bankStr =
                    CurrentData.getSelectedCrep().getBanco() != null ?
                    "Banco: " + CurrentData.getSelectedCrep().getBanco() :
                    "Banco no especificado";
            bankTextView.setText(bankStr);

            String saleStr = "Venta: " + CurrentData.getSelectedCrep().getVenta();
            saleTextView.setText(saleStr);
        }
    }

}
