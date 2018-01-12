package com.example.admin.iposapp.controler;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
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
import com.example.admin.iposapp.model.PaymentDetail;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewPaymentAdapter
        extends ArrayAdapter<Payment>
        implements View.OnClickListener{

    private ArrayList<Payment> dataSet;
    private Fragment parent;
    private Database db;
    Context context;

    private static class ViewHolder{

        TextView txtId;
        TextView txtAmount;
        TextView txtBalance;
        TextView txtClientName;
        TextView txtPaymentMethod;
        TextView txtFactura;
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

                    db.open();
                    db.beginTransaction();

                    ArrayList<PaymentDetail> details = getPaymentDetails(payment.getId());

                    for(int i = 0; i < details.size(); i++){
                        PaymentDetail detail = details.get(i);
                        Crep sale = getSale(detail.getVenta());

                        double newTotal =
                                sale.getSaldo() + Double.parseDouble(detail.getAbono());

                        boolean updateSaleSuccess = updateSaleTotal(
                                newTotal,
                                sale.getVenta()
                        );

                        if(!updateSaleSuccess)
                            throw new Exception("Problema al actualizar la venta");
                    }

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
                PartialPaymentFragment partialPaymentFragment = new PartialPaymentFragment();
                partialPaymentFragment.setTargetFragment(parent, 1);
                partialPaymentFragment.show(((FragmentActivity)context).getSupportFragmentManager(),
                        "client_changed_dialog");
                break;
        }
    }

    private ArrayList<PaymentDetail> getPaymentDetails(String payment){

        return Database.paymentDetailDAO.fetchPaymentDetailsByPayment(payment);
    }

    private boolean deletePaymentDetails(String payment){

        return Database.paymentDetailDAO.deletePaymentDetailsByPayment(payment);
    }

    private ArrayList<Payment> getPayments(){

        return Database.paymentDAO.fetchPayments();
    }

    private Crep getSale(String saleId){

        return Database.crepDAO.fetchCrepBySale(saleId);
    }

    private boolean deletePayment(String paymentId){

        return Database.paymentDAO.deletePayment(paymentId);
    }

    private boolean updateSaleTotal(double newTotal, String crepId){

        return Database.crepDAO.updateSaleTotal(newTotal, crepId);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        Payment payment = getItem(position);

        ListViewPaymentAdapter.ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ListViewPaymentAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(
                    R.layout.payment_custom_row,
                    parent,
                    false
            );

            viewHolder.txtId = (TextView) convertView.findViewById(R.id.payment_id);
            viewHolder.txtAmount = (TextView) convertView.findViewById(R.id.payment_amount);
            viewHolder.txtBalance = (TextView) convertView.findViewById(R.id.payment_balance);
            viewHolder.imgCancel = (ImageView) convertView.findViewById(R.id.cancel_payment);
            viewHolder.txtClientName = (TextView) convertView.findViewById(R.id.payment_client_name);
            viewHolder.txtPaymentMethod = (TextView) convertView.findViewById(R.id.payment_method);
            viewHolder.txtFactura = (TextView) convertView.findViewById(R.id.payment_factura);

            result = convertView;
            result.setTag(viewHolder);

            if (position % 2 == 1) {
                result.setBackgroundColor(Color.LTGRAY);
            }
        } else {
            viewHolder = (ListViewPaymentAdapter.ViewHolder) convertView.getTag();
        }

        assert payment != null;

        String venta = payment.getVenta().equals("0") ? "MULTI" : payment.getVenta();
        viewHolder.txtId.setText(venta);
        viewHolder.txtAmount.setText(
                String.format(
                        Locale.getDefault(),
                        "%.2f",
                        Double.valueOf(payment.getImporte())
                )
        );

        db.open();
        Crep saleInfo = Database.crepDAO.fetchCrepBySale(payment.getVenta());
        db.close();

        String factura = saleInfo.getFactura() == null ? "No especificado" : saleInfo.getFactura();
        /*String factura = saleInfo.getFactura();
        if(TextUtils.isEmpty(factura)){
            factura = "No especificado";
        }*/
        String metodoPago = payment.getTipo() == null ? "No especificado" : payment.getTipo();
        /*String metodoPago = payment.getTipo();
        if(TextUtils.isEmpty(metodoPago)){
            metodoPago = "No especificado";
        }*/
        String nombre = saleInfo.getNombre() == null ? "No especificado" : saleInfo.getNombre();
        /*String nombre = saleInfo.getNombre();
        if(TextUtils.isEmpty(nombre)){
            nombre = "No especificado";
        }*/

        viewHolder.txtBalance.setText(payment.getFecha());
        viewHolder.txtClientName.setText("Cliente: " + nombre);
        viewHolder.txtPaymentMethod.setText("Metodo de pago: " + metodoPago);
        viewHolder.txtFactura.setText("Factura: " + factura);
        viewHolder.imgCancel.setOnClickListener(this);
        viewHolder.imgCancel.setTag(position);

        return convertView;
    }

    public List<Payment> getData(){

        return dataSet;

    }
}
