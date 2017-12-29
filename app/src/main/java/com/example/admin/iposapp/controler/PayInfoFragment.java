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
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.utility.CurrentData;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */

public class PayInfoFragment extends DialogFragment {

    public PayInfoFragment() {
        // Required empty public constructor
    }

    Bundle mArgs = CurrentData.getItemMultipleCrep();
    public String factura = mArgs.getString("Factura");
    public String estatus = mArgs.getString("Estatus");
    public String cobranza = mArgs.getString("Cobranza");
    public String venta = mArgs.getString("Venta");
    public String total = mArgs.getString("Total");
    public String saldoMovil = mArgs.getString("SaldoMovil");
    public String pagoActual = mArgs.getString("PagoActual");
    public String anticipoActual = mArgs.getString("AnticipoActual");
    public String saldoDespues = mArgs.getString("SaldoDespues");
    public String abonosMovil = mArgs.getString("AbonosMovil");
    public String dias = mArgs.getString("Dias");
    public String aCuenta = mArgs.getString("ACuenta");

    private Dialog dialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LayoutInflater inflater;
        View view;
        Context context;

        inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.fragment_pay_info, null);
        context = view.getContext();

        try
        {

            TextView facturaTV = (TextView) view.findViewById(
                    R.id.txtFactura
            );

            facturaTV.setText(factura != null ? factura.toString() : "No asignado");

            TextView estatusTV = (TextView) view.findViewById(
                    R.id.txtEstatus
            );

            estatusTV.setText(estatus != null ? estatus : "No asignado");

            TextView cobranzaTV = (TextView) view.findViewById(
                    R.id.txtCobranza
            );

            cobranzaTV.setText(cobranza);

            TextView ventaTV = (TextView) view.findViewById(
                    R.id.txtVenta
            );

            ventaTV.setText(venta);

            TextView totalTV = (TextView) view.findViewById(
                    R.id.txtTotal
            );

            totalTV.setText(total);

            TextView saldoMovilTV = (TextView) view.findViewById(
                    R.id.txtSaldoMovil
            );

            saldoMovilTV.setText(saldoMovil);

            TextView pagoActualTV = (TextView) view.findViewById(
                    R.id.txtPagoActual
            );

            pagoActualTV.setText(pagoActual);

            TextView anticipoActualTV = (TextView) view.findViewById(
                    R.id.txtAnticipoActual
            );

            anticipoActualTV.setText(anticipoActual);

            TextView saldoDespuesTV = (TextView) view.findViewById(
                    R.id.txtSaldoDespues
            );

            saldoDespuesTV.setText(saldoDespues);

            TextView abonosMovilTV = (TextView) view.findViewById(
                    R.id.txtAbonosMovil
            );

            abonosMovilTV.setText(abonosMovil);

            TextView diasTV = (TextView) view.findViewById(
                    R.id.txtDias
            );

            diasTV.setText(dias);

            TextView aCuentaTV = (TextView) view.findViewById(
                    R.id.txtACuenta
            );

            aCuentaTV.setText(aCuenta);
        }
        catch (Exception e)
        {

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

}
