package com.example.admin.iposapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.iposapp.model.Payment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sopor on 12/12/2017.
 */

public class PaymentDAO
        extends DbContentProvider
        implements InterfacePaymentDAO, InterfacePaymentSchema {

    private Cursor cursor;
    private ContentValues initialValues;

    public PaymentDAO(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public Payment fetchPaymentById(int id) {
        final String selectionArgs[] = {String.valueOf(id)};
        final String selection = id + "?";
        Payment payment = new Payment();

        cursor = super.query(tableName, paymentColumns, selection, selectionArgs, columnId);

        if(cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                payment = cursorToEntity(cursor);
                cursor.moveToNext();
            }

            cursor.close();
        } else return null;

        return payment;
    }

    @Override
    public List<Payment> fetchAllPayments() {
        List<Payment> paymentList = new ArrayList<Payment>();
        cursor = super.query(tableName, paymentColumns, null, null, columnId);

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                Payment payment = cursorToEntity(cursor);
                paymentList.add(payment);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return paymentList;
    }

    public boolean deletePayment(String id){
        try{
            String selection = columnId + " = ?";
            String[] selectionArgs = new String[]{id};

            return super.delete(tableName, selection, selectionArgs) > 0;

        }
        catch (Exception e){

            e.printStackTrace();
            return false;
        }
    }

    public Payment getLastInserted(){
        List<Payment> paymentList = new ArrayList<Payment>();
        cursor = super.query(tableName, paymentColumns, null, null, columnId);

        Payment payment = null;

        if(cursor != null)
        {
            cursor.moveToLast();
            payment = cursorToEntity(cursor);

            cursor.close();
        }

        return payment;
    }

    @Override
    public boolean addPayment(Payment payment) {
        setContentValue(payment);

        try{

            long res = super.insert(tableName, initialValues);

            return res > 0;
        }
        catch (Exception ex){

            Log.w("Database:", ex.getMessage());

            return false;
        }
    }

    @Override
    public boolean addPayments(List<Payment> payments) {
        for (int i = 0; i < payments.size(); i++)
        {
            setContentValue(payments.get(i));
            try
            {
                if(super.insert(tableName, initialValues) > 0)
                {
                    Log.w("Client: ", "Added");
                    Log.w("Client: ", payments.get(i).getId());
                }
                else Log.w("Client: ", "Problem adding client");
            }
            catch (SQLiteConstraintException ex)
            {
                Log.w("Database", ex.getMessage());
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean deletePayments() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean updatePayment() {
        return false;
    }

    @Override
    protected Payment cursorToEntity(Cursor cursor) {

        Payment payment = new Payment();

        if(cursor != null){

            if(cursor.getColumnIndex(columnId) != -1)
            {
                int idIndex = cursor.getColumnIndexOrThrow(columnId);
                payment.setId(cursor.getString(idIndex));
            }

            if(cursor.getColumnIndex(columnPayment) != -1)
            {
                int paymentIndex = cursor.getColumnIndexOrThrow(columnPayment);
                payment.setVenta(cursor.getString(paymentIndex));
            }

            if(cursor.getColumnIndex(columnDate) != -1)
            {
                int dateIndex = cursor.getColumnIndexOrThrow(columnDate);
                payment.setFecha(cursor.getString(dateIndex));
            }

            if(cursor.getColumnIndex(columnType) != -1)
            {
                int typeIndex = cursor.getColumnIndexOrThrow(columnType);
                payment.setTipo(cursor.getString(typeIndex));
            }

            if(cursor.getColumnIndex(columnBank) != -1)
            {
                int bankIndex = cursor.getColumnIndexOrThrow(columnBank);
                payment.setBanco(cursor.getString(bankIndex));
            }

            if(cursor.getColumnIndex(columnIdNum) != -1)
            {
                int idNumIndex = cursor.getColumnIndexOrThrow(columnIdNum);
                payment.setIdNum(cursor.getString(idNumIndex));
            }

            if(cursor.getColumnIndex(columnImporte) != -1)
            {
                int importeIndex = cursor.getColumnIndexOrThrow(columnImporte);
                payment.setImporte(cursor.getString(importeIndex));
            }

            if(cursor.getColumnIndex(columnSaldo) != -1)
            {
                int saldoIndex = cursor.getColumnIndexOrThrow(columnSaldo);
                payment.setSaldo(cursor.getString(saldoIndex));
            }

            if(cursor.getColumnIndex(columnIntereses) != -1)
            {
                int interesesIndex = cursor.getColumnIndexOrThrow(columnIntereses);
                payment.setIntereses(cursor.getString(interesesIndex));
            }

            if(cursor.getColumnIndex(columnVoucher) != -1)
            {
                int voucherIndex = cursor.getColumnIndexOrThrow(columnVoucher);
                payment.setFolioDeposito(cursor.getString(voucherIndex));
            }

        }else return null;

        return payment;
    }

    private void setContentValue(Payment payment){

        initialValues = new ContentValues();
        initialValues.put(columnId, payment.getId());
        initialValues.put(columnPayment, payment.getVenta());
        initialValues.put(columnDate, payment.getFecha());
        initialValues.put(columnType, payment.getTipo());
        initialValues.put(columnBank, payment.getBanco());
        initialValues.put(columnIdNum, payment.getIdNum());
        initialValues.put(columnImporte, payment.getImporte());
        initialValues.put(columnSaldo, payment.getSaldo());
        initialValues.put(columnIntereses, payment.getIntereses());
        initialValues.put(columnVoucher, payment.getFolioDeposito());
    }
}
