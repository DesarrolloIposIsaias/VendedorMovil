package com.example.admin.iposapp.controler;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.model.Crep;
import com.example.admin.iposapp.model.Payment;
import com.example.admin.iposapp.utility.CurrentData;

import java.util.ArrayList;

/**
 * Created by soote1 on 30/12/17.
 */

public class ListViewPaymentAdapter
        extends ArrayAdapter<Payment>
        implements View.OnClickListener  {

    private ArrayList<Payment> dataSet;
    private Fragment parent;
    Context context;

    private static class ViewHolder{
        TextView txtId;
        TextView txtAmount;
        TextView txtBalance;
        ImageView imgCancel;
    }

    public ListViewPaymentAdapter(
            @NonNull Context context,
            ArrayList<Payment> data,
            Fragment caller) {

        super(context, R.layout.crep_custom_row);
        dataSet = data;
        this.context = context;
        parent = caller;
    }

    @Override
    public void onClick(View view) {

        int position = (Integer) view.getTag();
        Object object = getItem(position);
        Payment payment = (Payment)object;

        switch (view.getId()){
            case R.id.item_info:
                CrepInfoFragment crepInfoFragment = new CrepInfoFragment();
                crepInfoFragment.show(
                        ((FragmentActivity)context).getSupportFragmentManager(),
                        "client_changed_dialog");
                break;
            default:
                break;
        }

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Payment payment = getItem(position);

        ListViewPaymentAdapter.ViewHolder viewHolder;

        final View result;

        if(convertView == null){

            viewHolder = new ListViewPaymentAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.crep_custom_row, parent, false);
            viewHolder.txtId = (TextView) convertView.findViewById(R.id.crep_id);
            viewHolder.txtAmount = (TextView) convertView.findViewById(R.id.payment_amount);
            viewHolder.txtBalance = (TextView) convertView.findViewById(R.id.payment_balance);
            viewHolder.imgCancel = (ImageView) convertView.findViewById(R.id.cancel_payment);

            result = convertView;
            result.setTag(viewHolder);
        }
        else{
            viewHolder = (ListViewPaymentAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        assert payment != null;
        viewHolder.txtId.setText(payment.getVenta());
        viewHolder.txtAmount.setText(payment.getImporte());
        viewHolder.txtBalance.setText(payment.getSaldo());
        viewHolder.imgCancel.setOnClickListener(this);
        viewHolder.imgCancel.setTag(position);

        return convertView;
    }
}
