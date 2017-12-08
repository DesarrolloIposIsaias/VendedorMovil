package com.example.admin.iposapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.admin.iposapp.model.Sale;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 24/06/2016.
 */
public class SaleDAO extends DbContentProvider implements InterfaceSaleSchema, InterfaceSaleDAO {

    private Cursor cursor;
    private ContentValues initialValues;

    public SaleDAO(SQLiteDatabase db)
    {
        super(db);
    }

    private void setContentValue(Sale sale)
    {
        initialValues = new ContentValues();
        initialValues.put(columnId, sale.getId());
        initialValues.put(columnCliente, sale.getClient());
        initialValues.put(columnTotal, sale.getTotal());
        initialValues.put(columnDescripcion1, sale.getDescription1());
        initialValues.put(columnDescripcion2, sale.getDescription2());
        initialValues.put(columnMetodoDePago, sale.getPaymentMethod());
        initialValues.put(columnEstatus1, sale.getStatus1());
        initialValues.put(columnEstatus2, sale.getStatus2());
        initialValues.put(columnSend, sale.getSend());
    }

    protected Sale cursorToEntity(Cursor cursor)
    {
        Sale sale= new Sale();

        int idIndex;
        int clientIndex;
        int totalIndex;
        int description1Index;
        int description2Index;
        int paymentMethodIndex;
        int status1Index;
        int status2Index;
        int sendIndex;

        if(cursor != null)
        {
            if(cursor.getColumnIndex(columnId) != -1)
            {
                idIndex = cursor.getColumnIndexOrThrow(columnId);
                sale.setId(cursor.getString(idIndex));
            }
            if(cursor.getColumnIndex(columnCliente) != -1)
            {
                clientIndex = cursor.getColumnIndexOrThrow(columnCliente);
                sale.setClient(cursor.getString(clientIndex));
            }
            if(cursor.getColumnIndex(columnTotal) != -1)
            {
                totalIndex = cursor.getColumnIndexOrThrow(columnTotal);
                sale.setTotal(cursor.getFloat(totalIndex));
            }

            if(cursor.getColumnIndex(columnDescripcion1) != -1)
            {
                description1Index = cursor.getColumnIndexOrThrow(columnDescripcion1);
                sale.setDescription1(cursor.getString(description1Index));
            }
            if(cursor.getColumnIndex(columnDescripcion2) != -1)
            {
                description2Index = cursor.getColumnIndexOrThrow(columnDescripcion2);
                sale.setDescription2(cursor.getString(description2Index));
            }
            if(cursor.getColumnIndex(columnMetodoDePago) != -1)
            {
                paymentMethodIndex = cursor.getColumnIndexOrThrow(columnMetodoDePago);
                sale.setPaymentMethod(cursor.getString(paymentMethodIndex));
            }
            if(cursor.getColumnIndex(columnEstatus1) != -1)
            {
                status1Index = cursor.getColumnIndexOrThrow(columnEstatus1);
                sale.setStatus1(cursor.getString(status1Index));
            }
            if(cursor.getColumnIndex(columnEstatus2) != -1)
            {
                status2Index = cursor.getColumnIndexOrThrow(columnEstatus2);
                sale.setStatus2(cursor.getString(status2Index));
            }
            if(cursor.getColumnIndex(columnSend) != -1)
            {
                sendIndex = cursor.getColumnIndexOrThrow(columnSend);
                sale.setSend(cursor.getString(sendIndex));
            }
        }

        return  sale;
    }

    public Sale fetchSaleById(String id)
    {
        cursor = super.rawQuery("SELECT * FROM VENTA WHERE folio = " + id, null);

        Sale sale = new Sale();

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                sale = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return sale;
    }

    public List<Sale> fetchAllSales()
    {
        List<Sale> salesList = new ArrayList<Sale>();
        cursor = super.query(tableName, saleColumns, null, null, columnId);

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                Sale sale = cursorToEntity(cursor);
                salesList.add(sale);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return salesList;
    }

    public void deleteSale(String idSale)
    {
        try
        {
            database.execSQL("delete from "+ tableName + " where " + columnId + " = " +idSale);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    public boolean addSale(Sale sale)
    {
        setContentValue(sale);
        try
        {

            return super.insert(tableName, getContentValue()) > 0;
        }
        catch(Exception ex)
        {
            Log.w("Database", ex.getMessage());
            return false;
        }
    }

    public boolean addSales(List<Sale> sales)
    {
        return false;
    }

    public boolean deleteAllSales()
    {
        return false;
    }

    private ContentValues getContentValue()
    {
        return initialValues;
    }

    public Sale fetchLastInserted()
    {
        cursor = super.query(tableName, saleColumns, null, null, columnId);

        Sale sale = new Sale();

        if(cursor != null)
        {
            cursor.moveToLast();
            sale = cursorToEntity(cursor);
            cursor.close();
        }

        return sale;
    }

    public boolean updateSale(String id, Sale sale)
    {
        setContentValue(sale);

        long i = super.update(tableName, initialValues, columnId + "=?", new String[]{id});

        return i > 0;
    }

    public boolean updateClient(String clientId, String saleId)
    {
        Cursor s = super.rawQuery(
                "UPDATE VENTA " +
                        "SET " + columnCliente + " = " + "'" + clientId + "'" +
                        " WHERE " + columnId + " = " + "'" + saleId + "'", null);

        return s.getCount() > 0;
    }

    public Sale fecthClientId(String id)
    {
        Sale sale = new Sale();
        cursor = rawQuery("SELECT * FROM " + tableName + " WHERE " +
                columnId + " = "  + id, null);
        Log.d("bd", "SELECT * FROM " + tableName + " WHERE " + columnId + " = " + id);

        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            sale = cursorToEntity(cursor);
            cursor.close();
            return sale;
        }
        else return sale;
    }

}
