package com.example.admin.iposapp.controler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.model.Crep;

import java.util.ArrayList;

/**
 * Created by Desarrollo IPOS on 14/12/2017.
 */

public class ListViewMultipleCrepAdapter  extends ArrayAdapter<Crep> implements View.OnClickListener{

    private ArrayList<Crep> dataSet;
    Context context;

    private static class ViewHolder{
        TextView txtSale;
        TextView txtTotal;
        TextView txtSaldoMovil;
        ImageView imgInfo;
        EditText actualPayment;
    }

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
                Toast.makeText(
                        context,
                        "Id pressed: " + crep.getId(),
                        Toast.LENGTH_LONG).show();
                break;

            case R.id.crep_abono:
                Toast.makeText(
                        context,
                        "Client pressed: " + crep.getCliente(),
                        Toast.LENGTH_LONG).show();
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

        return convertView;
    }
}
