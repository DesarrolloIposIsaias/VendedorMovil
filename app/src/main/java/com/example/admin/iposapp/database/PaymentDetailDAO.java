package com.example.admin.iposapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.admin.iposapp.model.Payment;
import com.example.admin.iposapp.model.PaymentDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sopor on 12/12/2017.
 */

public class PaymentDetailDAO
        extends DbContentProvider
        implements InterfacePaymentDetailSchema, InterfacePaymentDetailDAO{

    private Cursor cursor;
    private ContentValues initialValues;

    public PaymentDetailDAO(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public PaymentDetail fetchPaymentDetailById(int id) {
        final String selectionArgs[] = {String.valueOf(id)};
        final String selection = id + "?";
        PaymentDetail paymentDetail = new PaymentDetail();

        cursor = super.query(tableName, paymentDetailColumns, selection, selectionArgs, columnId);

        if(cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                paymentDetail = cursorToEntity(cursor);
                cursor.moveToNext();
            }

            cursor.close();
        } else return null;

        return paymentDetail;
    }

    public boolean deletePaymentDetailsByPayment(String payment){
        final String selectionArgs[] = {payment};
        final String selection = columnPayment + " = ?";

        return super.delete(tableName, selection, selectionArgs) > 0;
    }

    public ArrayList<PaymentDetail> fetchPaymentDetailsByPayment(String payment){

        final String selectionArgs[] = {payment};
        final String selection = columnPayment + " = ?";
        ArrayList<PaymentDetail> paymentDetails = new ArrayList<>();

        cursor = super.query(tableName, paymentDetailColumns, selection, selectionArgs, columnId);

        if(cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                PaymentDetail paymentDetail1 = cursorToEntity(cursor);
                paymentDetails.add(paymentDetail1);
                cursor.moveToNext();
            }

            cursor.close();
        }

        return paymentDetails;
    }

    @Override
    public List<PaymentDetail> fetchAllPaymentDetails() {
        List<PaymentDetail> paymentDetailList = new ArrayList<>();
        cursor = super.query(
                tableName,
                paymentDetailColumns,
                null,
                null,
                columnId);

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                PaymentDetail paymentDetail = cursorToEntity(cursor);
                paymentDetailList.add(paymentDetail);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return paymentDetailList;
    }

    @Override
    public boolean addPaymentDetail(PaymentDetail paymentDetail) {


        cursor = super.query(
                tableName,
                null,
                null,
                null,
                null,
                null
        );

        String[] columnNames = cursor.getColumnNames();

        setContentValue(paymentDetail);

        try{
            String query =
                    "INSERT INTO " + tableName + "(" +
                            columnPayment + "," +
                            columnDate + "," +
                            columnSale + "," +
                            columnCharge + "," +
                            columnPartialPayment + "," +
                            columnBalance + "," +
                            columnInterest + "," +
                            columnNumber + ") " +

                            "VALUES (" +
                            "'" + paymentDetail.getPago() + "'," +
                            "'" + paymentDetail.getFecha() + "'," +
                            "'" + paymentDetail.getVenta() + "'," +
                            "'" + paymentDetail.getCargo() + "'," +
                            "'" + paymentDetail.getAbono() + "'," +
                            "'" + paymentDetail.getSaldo() + "'," +
                            "'" + paymentDetail.getIntereses() + "'," +
                            "'" + paymentDetail.getNumero() + "')";

            database.execSQL(query);

            return true;
        }
        catch (Exception ex){

            Log.w("Database:", ex.getMessage());
            return false;
        }
    }

    private void setContentValue(PaymentDetail paymentDetail){

        initialValues = new ContentValues();
        initialValues.put(columnId, paymentDetail.getId());
        initialValues.put(columnPayment, paymentDetail.getPago());
        initialValues.put(columnDate, paymentDetail.getFecha());
        initialValues.put(columnSale, paymentDetail.getVenta());
        initialValues.put(columnCharge, paymentDetail.getCargo());
        initialValues.put(columnPartialPayment, paymentDetail.getAbono());
        initialValues.put(columnBalance, paymentDetail.getSaldo());
        initialValues.put(columnInterest, paymentDetail.getIntereses());
        initialValues.put(columnNumber, paymentDetail.getNumero());
    }

    @Override
    public boolean addPaymentDetails(List<PaymentDetail> paymentDetails) {
        for (int i = 0; i < paymentDetails.size(); i++)
        {
            setContentValue(paymentDetails.get(i));
            try
            {
                if(super.insert(tableName, initialValues) > 0)
                {
                    Log.w("Client: ", "Added");
                    Log.w("Client: ", paymentDetails.get(i).getId());
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
    public boolean deletePaymentDetails() {
        return false;
    }

    @Override
    public boolean updatePaymentDetail() {
        return false;
    }

    @Override
    protected PaymentDetail cursorToEntity(Cursor cursor) {

        PaymentDetail paymentDetail = new PaymentDetail();

        if(cursor != null){
            if(cursor.getColumnIndex(columnId) != -1){
                int idIndex = cursor.getColumnIndex(columnId);
                paymentDetail.setId(cursor.getString(idIndex));
            }

            if(cursor.getColumnIndex(columnPayment) != -1){
                int paymentIndex = cursor.getColumnIndex(columnPayment);
                paymentDetail.setPago(cursor.getString(paymentIndex));
            }

            if(cursor.getColumnIndex(columnDate) != -1){
                int dateIndex = cursor.getColumnIndex(columnDate);
                paymentDetail.setFecha(cursor.getString(dateIndex));
            }

            if(cursor.getColumnIndex(columnSale) != -1){
                int saleIndex = cursor.getColumnIndex(columnSale);
                paymentDetail.setVenta(cursor.getString(saleIndex));
            }

            if(cursor.getColumnIndex(columnCharge) != -1){
                int chargeIndex = cursor.getColumnIndex(columnCharge);
                paymentDetail.setCargo(cursor.getString(chargeIndex));
            }

            if(cursor.getColumnIndex(columnPartialPayment) != -1){
                int partialPaymentIndex = cursor.getColumnIndex(columnPartialPayment);
                paymentDetail.setAbono(cursor.getString(partialPaymentIndex));
            }

            if(cursor.getColumnIndex(columnBalance) != -1){
                int balanceIndex = cursor.getColumnIndex(columnBalance);
                paymentDetail.setSaldo(cursor.getString(balanceIndex));
            }

            if(cursor.getColumnIndex(columnInterest) != -1){
                int interestIndex = cursor.getColumnIndex(columnInterest);
                paymentDetail.setIntereses(cursor.getString(interestIndex));
            }

            if(cursor.getColumnIndex(columnNumber) != -1){
                int numberIndex = cursor.getColumnIndex(columnNumber);
                paymentDetail.setNumero(cursor.getString(numberIndex));
            }

        }else return null;

        return paymentDetail;
    }
}
