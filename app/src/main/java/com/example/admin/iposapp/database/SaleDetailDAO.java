package com.example.admin.iposapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.admin.iposapp.model.Sale;
import com.example.admin.iposapp.model.SaleDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by admin on 24/06/2016.
 */
public class SaleDetailDAO extends DbContentProvider
        implements InterfaceSaleDetailDAO, InterfaceSaleDetailSchema {

    private Cursor cursor;
    private ContentValues initialValues;

    public SaleDetailDAO(SQLiteDatabase db)
    {
        super(db);
        //super.rawQuery("DROP TABLE IF EXISTS " + tableName, null);
    }

    protected SaleDetail cursorToEntity(Cursor cursor) {
        SaleDetail saleDetail = new SaleDetail();

        int idIndex;
        int clientIndex;
        int productIdIndex;
        int description1Index;
        int priceIndex;
        int discountIndex;
        int amountIndex;
        int saleIdIndex;

        if(cursor != null)
        {
            if(cursor.getColumnIndex(columnId) != -1)
            {
                idIndex = cursor.getColumnIndexOrThrow(columnId);
                String x = cursor.getString(idIndex);
                saleDetail.setId(x);
            }
            if(cursor.getColumnIndex(columnClient) != -1)
            {
                clientIndex = cursor.getColumnIndexOrThrow(columnClient);
                saleDetail.setClient(cursor.getString(clientIndex));
            }
            if(cursor.getColumnIndex(columnProductId) != -1)
            {
                productIdIndex = cursor.getColumnIndexOrThrow(columnProductId);
                saleDetail.setProductId(cursor.getString(productIdIndex));
            }
            if(cursor.getColumnIndex(columnDescription) != -1)
            {
                description1Index = cursor.getColumnIndexOrThrow(columnDescription);
                saleDetail.setDescription(cursor.getString(description1Index));
            }
            if(cursor.getColumnIndex(columnPrice) != -1)
            {
                priceIndex = cursor.getColumnIndexOrThrow(columnPrice);
                saleDetail.setPrice(cursor.getFloat(priceIndex));
            }
            if(cursor.getColumnIndex(columnDiscount) != -1)
            {
                discountIndex = cursor.getColumnIndexOrThrow(columnDiscount);
                saleDetail.setDiscount(cursor.getFloat(discountIndex));
            }
            if(cursor.getColumnIndex(columnAmount) != -1)
            {
                amountIndex = cursor.getColumnIndexOrThrow(columnAmount);
                saleDetail.setAmount(cursor.getFloat(amountIndex));
            }
            if(cursor.getColumnIndex(columnSaleId) != -1)
            {
                saleIdIndex = cursor.getColumnIndexOrThrow(columnSaleId);
                saleDetail.setSaleId(cursor.getString(saleIdIndex));
            }
        }

        return saleDetail;
    }

    public SaleDetail fetchSaleDetailById(int saleDetailId) {
        final String selectionArgs[] = {String.valueOf(saleDetailId)};
        final String selection = saleDetailId + "?";
        SaleDetail saleDetail = new SaleDetail();
        cursor = super.query(tableName, saleDetailColumns, selection, selectionArgs, columnId);

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                saleDetail = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return saleDetail;
    }

    public List<SaleDetail> fetchAllSaleDetails() {
        List<SaleDetail> saleDetailList = new ArrayList<SaleDetail>();
        cursor = super.query(tableName, saleDetailColumns, null, null, columnId);

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                SaleDetail saleDetail = cursorToEntity(cursor);
                saleDetailList.add(saleDetail);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return saleDetailList;
    }

    public List<SaleDetail> fetchSaleDetailsBySale(String id)
    {
        List<SaleDetail> saleDetailList = new ArrayList<SaleDetail>();
        cursor = super.rawQuery("SELECT * FROM " + tableName + " WHERE "  + columnSaleId
                + " = " + "'" + id + "'", null);

        Log.w("Database: ", "SELECT * FROM " + tableName +
                " WHERE "  + columnSaleId + " = " + "'" + id + "'");

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                SaleDetail saleDetail = cursorToEntity(cursor);
                saleDetailList.add(saleDetail);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return saleDetailList;
    }


    public boolean addSaleDetail(SaleDetail saleDetail) {
        setContentValue(saleDetail);
        try
        {
            return super.insert(tableName, getContentValue()) > 0;
        }
        catch (SQLiteConstraintException ex)
        {
            Log.w("DataBase", ex.getMessage());
            return false;
        }
    }

    public boolean addSaleDetails(List<SaleDetail> products) {
        return false;
    }

    public boolean deleteAllSaleDetails() {
        return false;
    }

    private ContentValues getContentValue()
    {
        return initialValues;
    }

    private void setContentValue(SaleDetail saleDetail)
    {
        initialValues = new ContentValues();
        initialValues.put(columnId, saleDetail.getId());
        initialValues.put(columnClient, saleDetail.getClient());
        initialValues.put(columnProductId, saleDetail.getProductId());
        initialValues.put(columnDescription, saleDetail.getDescription());
        initialValues.put(columnPrice, saleDetail.getPrice());
        initialValues.put(columnDiscount, saleDetail.getDiscount());
        initialValues.put(columnAmount, saleDetail.getAmount());
        initialValues.put(columnSaleId, saleDetail.getSaleId());
    }

    public void deleteSaleDetail(String idSale, String price, String description)
    {
            try
            {
                database.execSQL("delete from DETALLE_VENTA where folio_venta = '" +idSale+
                    "' and descripcion1 = '"+description+"' and precio = " + price);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
    }

    public void deleteSaleDetail(SaleDetail saleDetail)
    {
        database.execSQL("delete from DETALLE_VENTA where folio_venta = '" + saleDetail.getSaleId());
    }

    public SaleDetail exists(String id, String saleId, String price)
    {
        SaleDetail aux = new SaleDetail();

        cursor = super.rawQuery("SELECT * FROM " + tableName +" WHERE " + columnProductId +
                " = " + "'" + id + "' " + "AND " + columnSaleId + " = " + "'" + saleId + "' " +
                " AND " + columnPrice + " = " + price, null);

        Log.w("QUERY: ", "SELECT * FROM " + tableName +" WHERE " + columnProductId +
                " = " + "'" + id + "' " + "AND " + columnSaleId + " = " + "'" + saleId + "' " +
                " AND " + columnPrice + " = " + price);

        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            SaleDetail saleDetail = cursorToEntity(cursor);
            cursor.close();
            return saleDetail;
        }
        else
        {
            cursor.close();
            aux.setId("-1");

            return aux;
        }
    }

    public boolean update(SaleDetail saleDetail, String saleId)
    {
        setContentValue(saleDetail);

        try
        {
            cursor = database.rawQuery("UPDATE " + tableName +
                    " SET " + columnAmount + " = " + saleDetail.getAmount() +
                    " WHERE " + columnProductId + " = " + "'" + saleDetail.getProductId() + "'" +
                    " AND " + columnSaleId + " = " + "'" + saleId + "' " +
                    " AND " + columnPrice + " = " + saleDetail.getPrice(), null);

            Log.w("UPDATE: ", "UPDATE " + tableName +
                    " SET " + columnAmount + " = " + saleDetail.getAmount() +
                    " WHERE " + columnProductId + " = " + "'" + saleDetail.getProductId() + "'" +
                    " AND " + columnSaleId + " = " + "'" + saleId + "' " +
                    " AND " + columnPrice + " = " + saleDetail.getPrice());
        }
        catch (Exception ex)
        {
            Log.w("UPDATE: ", "UPDATE " + tableName +
                    " SET " + columnAmount + " = " + saleDetail.getAmount() +
                    " WHERE " + columnProductId + " = " + "'" + saleDetail.getProductId() + "'" +
                    " AND " + columnSaleId + " = " + "'" + saleId + "' " +
                    " AND " + columnPrice + " = " + saleDetail.getPrice());
        }

        if(cursor.getCount() > 0)
        {
            cursor.close();
            return true;
        }
        else
        {
            cursor.close();
            return false;
        }
    }

    public boolean updateClient(String clientId, String saleId)
    {
        Cursor result = super.rawQuery("UPDATE " + tableName +
                                       " SET " + columnClient + " = " + "'" + clientId + "'" +
                                       " WHERE " + columnSaleId + " = " + "'" + saleId + "'", null);

        return result.getCount() > 0;
    }

    public void deleteBySaleId(String id)
    {
        try
        {
            rawQuery("DELETE FROM " + tableName +
                    " WHERE " + columnSaleId + " = " + "'" + columnSaleId + "'", null);
        }
        catch (Exception ex)
        {
            Log.w("Problema: ", ex.getMessage());
        }
    }
}
