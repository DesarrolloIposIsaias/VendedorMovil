package com.example.admin.iposapp.controler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.Payment;
import com.example.admin.iposapp.model.PaymentDetail;

import java.util.ArrayList;

/**
 * Created by sopor on 02/01/2018.
 */

public class ListViewPartialPaymentAdapter
        extends ArrayAdapter<PaymentDetail>
        implements View.OnClickListener {

    private ArrayList<PaymentDetail> dataSet;
    Context context;

    private static class ViewHolder{
        TextView txtPayment;
        TextView txtAmount;
        TextView txtSale;
        TextView txtDate;
    }

    public ListViewPartialPaymentAdapter(@NonNull Context context,
                                         ArrayList<PaymentDetail> data) {

        super(context, R.layout.partial_payment_custom_row, data);

        dataSet = data;
        this.context = context;
    }

    @Override
    public void onClick(View view) {

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent){

        PaymentDetail payment = getItem(position);

        ListViewPartialPaymentAdapter.ViewHolder viewHolder;

        final View result;

        if(convertView == null){

            viewHolder = new ListViewPartialPaymentAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(
                    R.layout.partial_payment_custom_row,
                    parent,
                    false
            );

            viewHolder.txtPayment = (TextView) convertView.findViewById(R.id.text_view_payment);
            viewHolder.txtAmount = (TextView) convertView.findViewById(R.id.text_view_amount);
            viewHolder.txtSale = (TextView) convertView.findViewById(R.id.text_view_sale);
            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.text_view_date);

            result = convertView;
            result.setTag(viewHolder);
        }
        else{
            viewHolder = (ListViewPartialPaymentAdapter.ViewHolder) convertView.getTag();
        }

        assert payment != null;
        viewHolder.txtPayment.setText(payment.getPago());
        viewHolder.txtAmount.setText(payment.getAbono());
        viewHolder.txtSale.setText(payment.getVenta());
        viewHolder.txtDate.setText(payment.getFecha());

        return convertView;
    }
}
