package com.example.admin.iposapp.controler;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.model.Crep;
import com.example.admin.iposapp.model.CrepToPay;
import com.example.admin.iposapp.utility.CurrentData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Desarrollo IPOS on 14/12/2017.
 */

public class ListViewMultipleCrepAdapter  extends ArrayAdapter<Crep> implements View.OnClickListener, View.OnFocusChangeListener{

    private ArrayList<Crep> dataSet;
    Context context;
    private final int minDelta = 300;           // threshold in ms
    private long focusTime = 0;                 // time of last touch
    private View focusTarget = null;

    private static class ViewHolder{
        TextView txtSale;
        TextView txtTotal;
        TextView txtSaldoMovil;
        ImageView imgInfo;
        EditText actualPayment;
    }
    private EditText auxEditText;
   // private TextView aCuentaTxtVw;
    private float auxAcount;

    public ListViewMultipleCrepAdapter(@NonNull Context ctx, ArrayList<Crep> data) {
        super(ctx, R.layout.multiple_crep_custom_row, data);
        dataSet = data;
        context = ctx;
    }

    @Override
    public void onClick(View view) {

        int position = (Integer) view.getTag();
        Object object = getItem(position);
        Crep crep = (Crep)object;

        assert crep != null;
        switch (view.getId()){
            case R.id.item_info:
                Bundle args = new Bundle();
                args.putString("Factura", crep.getFactura());
                args.putString("Estatus", crep.getEstatus());
                args.putString("Cobranza", crep.getCobranza());
                args.putString("Venta", crep.getVenta());
                args.putString("Total", Float.toString(crep.getTotal()));
                args.putString("SaldoMovil", Float.toString(crep.getSaldo()));
                args.putString("PagoActual", Float.toString(crep.getPago()));
                args.putString("Dias", Float.toString(crep.getDias()));
                args.putString("ACuenta", Float.toString(crep.getaCuenta()));

                CurrentData.setItemMultipleCrep(args);
                PayInfoFragment payInfoFragment = new PayInfoFragment();
                payInfoFragment.show(
                        ((FragmentActivity)context).getSupportFragmentManager(),
                        "client_changed_dialog");
                break;

            case R.id.crep_abono:
                Toast.makeText(
                        context,
                        "Client pressed: " + crep.getCliente(),
                        Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
    /* When focus is lost check that the text field
    * has valid values.
    *
    *
    */


        int position = (Integer) view.getTag();
        Object object = getItem(position);
        Crep crep = (Crep)object;
        CrepToPay crepToPay = new CrepToPay();

        assert crep != null;
        switch (view.getId()){
            case R.id.crep_abono:
                if (!hasFocus) {
                    recalculateACount();
                }
                break;
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Crep crep = getItem(position);

        ListViewMultipleCrepAdapter.ViewHolder viewHolder;

        final View result;

        if(convertView == null){
            viewHolder = new ListViewMultipleCrepAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());


            convertView = inflater.inflate(R.layout.multiple_crep_custom_row, parent, false);
            viewHolder.txtSale = (TextView) convertView.findViewById(R.id.crep_sale);
            viewHolder.txtTotal = (TextView) convertView.findViewById(R.id.crep_total);
            viewHolder.txtSaldoMovil = (TextView) convertView.findViewById(R.id.crep_saldo_movil);
            viewHolder.imgInfo = (ImageView) convertView.findViewById(R.id.item_info);
            viewHolder.actualPayment = (EditText) convertView.findViewById(R.id.crep_abono);

            result = convertView;
            result.setTag(viewHolder);
        }
        else{
            viewHolder = (ListViewMultipleCrepAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        assert crep != null;
        viewHolder.txtSale.setText(crep.getVenta());
        viewHolder.txtTotal.setText(Float.toString(crep.getTotal()));
        viewHolder.txtSaldoMovil.setText(Float.toString(crep.getSaldo()));
        viewHolder.imgInfo.setOnClickListener(this);
        viewHolder.imgInfo.setTag(position);
        viewHolder.actualPayment.setOnClickListener(this);
        viewHolder.actualPayment.setTag(position);
        viewHolder.actualPayment.setOnFocusChangeListener(this);

        return convertView;
    }

    private void recalculateACount(){
        Float totalAuxCount = Float.valueOf(0);
        Float totalPayment = Float.parseFloat(ListViewMultipleCrepPaymentFragment.aCuentaTxtVw.getText().toString());
        Float restanteAuxCount = totalPayment;
        for(int i=0; i<this.getCount(); i++)
        {
            View v = this.getView(i, ListViewMultipleCrepPaymentFragment.crepsListView.getChildAt(i),ListViewMultipleCrepPaymentFragment.crepsListView );
            auxEditText = (EditText) v.findViewById(R.id.crep_abono);
            if(auxEditText != null && auxEditText.getText().length() > 0)
            {
                Object object = getItem(i);
                Crep crep = (Crep)object;
                if(Float.parseFloat(auxEditText.getText().toString()) > crep.getSaldo())
                {
                    auxEditText.setText(String.valueOf(crep.getSaldo()));
                }
                totalAuxCount += Float.parseFloat(auxEditText.getText().toString());
                restanteAuxCount -= Float.parseFloat(auxEditText.getText().toString());
            }
        }

        ListViewMultipleCrepPaymentFragment.aCuentaAbonadaTxtVw.setText(totalAuxCount.toString());
        ListViewMultipleCrepPaymentFragment.aCuentaRestanteTxtVw.setText(restanteAuxCount.toString());
        if(totalPayment < totalAuxCount)
        {
            ListViewMultipleCrepPaymentFragment.aCuentaAbonadaTxtVw.setTextColor(Color.RED);
        }
        else
        {
            ListViewMultipleCrepPaymentFragment.aCuentaAbonadaTxtVw.setTextColor(Color.WHITE);
        }
    }

    public List<Crep> getData(){
        return dataSet;
    }
}
