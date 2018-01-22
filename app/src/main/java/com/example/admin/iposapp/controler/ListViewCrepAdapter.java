package com.example.admin.iposapp.controler;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.model.Crep;
import com.example.admin.iposapp.utility.CurrentData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sopor on 13/12/2017.
 */

public class ListViewCrepAdapter
        extends ArrayAdapter<Crep>
        implements View.OnClickListener {

    private ArrayList<Crep> dataSet;
    private Fragment parent;
    Context context;

    private static class ViewHolder{
        TextView txtId;
        TextView txtClient;
        TextView txtClientName;
        TextView txtQuantityCrep;
        ImageView imgInfo;
        ImageView imgPayment;
    }

    public ListViewCrepAdapter(@NonNull Context ctx, ArrayList<Crep> data, Fragment caller) {
        super(ctx, R.layout.crep_custom_row, data);
        dataSet = data;
        context = ctx;
        parent = caller;
    }

    @Override
    public void onClick(View view) {

        int position = (Integer) view.getTag();
        Object object = getItem(position);
        Crep crep = (Crep)object;

        CurrentData.setSelectedCrep(crep);

        switch (view.getId()){
            case R.id.item_info:
                CrepInfoFragment crepInfoFragment = new CrepInfoFragment();
                crepInfoFragment.show(
                        ((FragmentActivity)context).getSupportFragmentManager(),
                        "client_changed_dialog");
                break;

            case R.id.go_pay_crep:
                SinglePaymentFragment fragment = new SinglePaymentFragment();
                fragment.setTargetFragment(parent, 1);
                fragment.show(
                        ((FragmentActivity)context).getSupportFragmentManager(),
                        "client_changed_dialog");
                break;
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Crep crep = getItem(position);

        ViewHolder viewHolder;

        final View result;

        if(convertView == null){

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.crep_custom_row, parent, false);
            viewHolder.txtId = (TextView) convertView.findViewById(R.id.crep_id);
            viewHolder.txtClient = (TextView) convertView.findViewById(R.id.crep_client);
            viewHolder.txtClientName = (TextView) convertView.findViewById(R.id.crep_client_name);
            viewHolder.txtQuantityCrep = (TextView) convertView.findViewById(R.id.crep_quantity);
            viewHolder.imgInfo = (ImageView) convertView.findViewById(R.id.item_info);
            viewHolder.imgPayment = (ImageView) convertView.findViewById(R.id.go_pay_crep);

            result = convertView;
            result.setTag(viewHolder);

            if(position % 2 == 1){
                result.setBackgroundColor(Color.LTGRAY);
            }
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        assert crep != null;
        viewHolder.txtId.setText("Venta: " + crep.getVenta());
        viewHolder.txtClient.setText("Clave cliente: " + crep.getCliente());
        viewHolder.txtClientName.setText("Nombre: " + crep.getNombre());
        viewHolder.txtQuantityCrep.setText("Cantidad a pagar: " + crep.getSaldo());
        viewHolder.imgInfo.setOnClickListener(this);
        viewHolder.imgInfo.setTag(position);
        viewHolder.imgPayment.setOnClickListener(this);
        viewHolder.imgPayment.setTag(position);

        return convertView;
    }

    public List<Crep> getData(){
        return dataSet;
    }
}
