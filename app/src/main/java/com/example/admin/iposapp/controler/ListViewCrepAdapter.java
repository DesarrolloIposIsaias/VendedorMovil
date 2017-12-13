package com.example.admin.iposapp.controler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.model.Crep;

import java.util.ArrayList;

/**
 * Created by sopor on 13/12/2017.
 */

public class ListViewCrepAdapter
        extends ArrayAdapter<Crep>
        implements View.OnClickListener {

    private ArrayList<Crep> dataSet;
    Context context;

    private static class ViewHolder{
        TextView txtId;
        TextView txtClient;
        ImageView imgInfo;
        ImageView imgPayment;
    }

    public ListViewCrepAdapter(@NonNull Context ctx, ArrayList<Crep> data) {
        super(ctx, R.layout.crep_custom_row, data);
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

            case R.id.go_pay_crep:
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

        ViewHolder viewHolder;

        final View result;

        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.crep_custom_row, parent, false);
            viewHolder.txtId = (TextView) convertView.findViewById(R.id.crep_id);
            viewHolder.txtClient = (TextView) convertView.findViewById(R.id.crep_client);
            viewHolder.imgInfo = (ImageView) convertView.findViewById(R.id.item_info);
            viewHolder.imgPayment = (ImageView) convertView.findViewById(R.id.go_pay_crep);

            result = convertView;
            result.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        assert crep != null;
        viewHolder.txtId.setText(crep.getId());
        viewHolder.txtClient.setText(crep.getCliente());
        viewHolder.imgInfo.setOnClickListener(this);
        viewHolder.imgInfo.setTag(position);
        viewHolder.imgPayment.setOnClickListener(this);
        viewHolder.imgPayment.setTag(position);

        return convertView;
    }
}
