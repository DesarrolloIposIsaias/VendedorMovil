package com.example.admin.iposapp.controler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.ContactsContract;
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
import android.widget.Toast;

import com.example.admin.iposapp.R;
import com.example.admin.iposapp.database.Database;
import com.example.admin.iposapp.model.Crep;
import com.example.admin.iposapp.model.Payment;
import com.example.admin.iposapp.utility.CurrentData;

import java.util.ArrayList;
import java.util.List;

public class ListViewPaymentAdapter
        extends ArrayAdapter<Payment>
        implements View.OnClickListener  {

    private ArrayList<Payment> dataSet;
    private Fragment parent;
    private Database db;
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

        super(context, R.layout.payment_custom_row, data);
        dataSet = data;
        this.context = context;
        parent = caller;
        db = new Database(context);
    }

    @Override
    public void onClick(View view) {

        int position = (Integer) view.getTag();
        Object object = getItem(position);
        Payment payment = (Payment)object;

        switch (view.getId()){
            case R.id.cancel_payment:

                try{
                    if(payment == null)
                        throw new Exception("No se pudo recuperar el pago");

                    Crep crep = getSale(payment.getVenta());

                    db.open();
                    db.beginTransaction();

                    boolean updateSaleSuccess = updateSaleTotal(
                            crep.getSaldo() + Double.parseDouble(payment.getImporte()),
                            crep.getVenta()
                    );

                    if(updateSaleSuccess)
                        throw new Exception("Problema al actualizar la venta");

                    boolean deletePaymentSuccess = deletePayment(payment.getId());

                    if(!deletePaymentSuccess)
                        throw new Exception("Problema al eliminar el pago");

                    db.commitTransaction();

                    ArrayList<Payment> payments = getPayments();
                    dataSet.clear();
                    dataSet.addAll(payments);
                    notifyDataSetChanged();

                    Toast.makeText(
                            getContext(),
                            "Proceso exitoso",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                catch (Exception e){
                    Toast.makeText(
                            getContext(),
                            e.getMessage(),
                            Toast.LENGTH_SHORT
                    ).show();
                }
                finally {
                    db.closeTransaction();
                    db.close();
                }

                break;
            default:
                break;
        }
    }

    private boolean deletePaymentDetails(String payment){
        db.open();
        boolean result = Database.paymentDetailDAO.deletePaymentDetailsByPayment(payment);
        db.close();

        return result;
    }

    private ArrayList<Payment> getPayments(){
        db.open();
        ArrayList<Payment> payments = Database.paymentDAO.fetchPayments();
        db.close();

        return payments;
    }

    private Crep getSale(String saleId){
        db.open();
        Crep crep = Database.crepDAO.fetchCrepBySale(saleId);
        db.close();

        return crep;
    }

    private boolean deletePayment(String paymentId){
        db.open();
        boolean result = Database.paymentDAO.deletePayment(paymentId);
        db.close();

        return result;
    }

    private boolean updateSaleTotal(double newTotal, String crepId){
        db.open();
        boolean result = Database.crepDAO.updateSaleTotal(newTotal, crepId);
        db.close();

        return result;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Payment payment = getItem(position);

        ListViewPaymentAdapter.ViewHolder viewHolder;

        final View result;

        if(convertView == null){

            viewHolder = new ListViewPaymentAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.payment_custom_row, parent, false);
            viewHolder.txtId = (TextView) convertView.findViewById(R.id.payment_id);
            viewHolder.txtAmount = (TextView) convertView.findViewById(R.id.payment_amount);
            viewHolder.txtBalance = (TextView) convertView.findViewById(R.id.payment_balance);
            viewHolder.imgCancel = (ImageView) convertView.findViewById(R.id.cancel_payment);

            result = convertView;
            result.setTag(viewHolder);
        }
        else{
            viewHolder = (ListViewPaymentAdapter.ViewHolder) convertView.getTag();
        }

        assert payment != null;
        viewHolder.txtId.setText(payment.getVenta());
        viewHolder.txtAmount.setText(payment.getImporte());
        viewHolder.txtBalance.setText(payment.getFecha());
        viewHolder.imgCancel.setOnClickListener(this);
        viewHolder.imgCancel.setTag(position);

        return convertView;
    }

    public List<Payment> getData(){

        return dataSet;

    }
}
