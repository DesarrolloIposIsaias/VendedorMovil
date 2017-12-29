package com.example.admin.iposapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.admin.iposapp.utility.CurrentData;
import com.example.admin.iposapp.model.Crep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * Created by Desarrollo IPOS on 11/12/2017.
 */

public class CrepDAO extends DbContentProvider
        implements InterfaceCrepSchema, InterfaceCrepDAO{

    private Cursor cursor;
    private ContentValues initialValues;

    public CrepDAO(SQLiteDatabase db)
    {

        super(db);
    }

    public Crep fetchCrepById(int id)
    {
        final String selectionArgs[] = {String.valueOf(id)};
        final String selection = id + "?";
        Crep crep = new Crep();
        cursor = super.query(tableName, crepColumns, selection, selectionArgs, columnId);

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                crep = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return crep;
    }

    public boolean updateCrepBalance(float saldo, float partialPayment, String id){

        String newBalance = String.valueOf(saldo-partialPayment);

        try{

            initialValues = new ContentValues();
            initialValues.put(columnSaldo, newBalance);

            String selection = columnSale + " = ?";
            String[] selectionArgs = new String[]{id};

            return super.update(tableName, initialValues, selection, selectionArgs) > 0;
        }
        catch (Exception e){

            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Crep> fetchCrepsByClient(String client){
        ArrayList<Crep> crepList = new ArrayList<>();

        String selectionStr = columnClient + " = ? and " + columnSaldo + " > 0";
        String[] selectionArgs = new String[]{client};
        cursor = super.query(tableName, crepColumns, selectionStr, selectionArgs, columnId);

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                Crep crep = cursorToEntity(cursor);
                crepList.add(crep);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return crepList;
    }

    public ArrayList<Crep> fetchMatchingCreps(String filter, String clientId){

        try{
            ArrayList<Crep> crepList = new ArrayList<Crep>();

            String query = " SELECT * FROM " + tableName +
                           " WHERE (" + columnVendedor + " LIKE '%"+filter+"%' OR " +
                                       columnSale + " LIKE '%"+filter+"%' OR " +
                                       columnId + " LIKE '%"+filter+"%') AND " +
                                       columnClient + " = '" + clientId + "'";

            cursor = super.rawQuery(query, null);

            if(cursor != null)
            {
                cursor.moveToFirst();
                while(!cursor.isAfterLast())
                {
                    Crep crep = cursorToEntity(cursor);
                    crepList.add(crep);
                    cursor.moveToNext();
                }
                cursor.close();
            }

            return crepList;
        }
        catch (Exception e){
            return null;
        }
    }

    public List<Crep> fetchAllCreps()
    {
        List<Crep> crepList = new ArrayList<Crep>();
        cursor = super.query(tableName, crepColumns, null, null, columnId);

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                Crep crep = cursorToEntity(cursor);
                crepList.add(crep);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return crepList;
    }

    public List<Crep> fetchCrepsByClientList(String cliente)
    {
        final String selectionArgs[] = {String.valueOf(cliente)};
        final String selection =  "cliente = '" + cliente + "' ";
        List<Crep> crepList = new ArrayList<Crep>();
        cursor = super.query(tableName, crepColumns, selection, null, columnId);

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                Crep crep = cursorToEntity(cursor);
                crepList.add(crep);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return crepList;
    }

    public boolean addCrep(Crep crep)
    {
        setContentValue(crep);
        try
        {
            return super.insert(tableName, getContentValue()) > 0;
        }
        catch (SQLiteConstraintException ex)
        {
            Log.w("Database", ex.getMessage());
            return false;
        }
    }

    public boolean addCreps(List<Crep> creps)
    {
        for (int i = 0; i < creps.size(); i++)
        {
            setContentValue(creps.get(i));
            try
            {
                if(super.insert(tableName, getContentValue()) > 0)
                {
                    Log.w("Crep: ", "Added");
                    Log.w("Crep: ", creps.get(i).getNombre());
                }
                else Log.w("Crep: ", "Problem adding crep");
            }
            catch (SQLiteConstraintException ex)
            {
                Log.w("Database", ex.getMessage());
                return false;
            }
        }
        return true;
    }

    public boolean deleteAllCreps() {
        return false;
    }

    protected Crep cursorToEntity(Cursor cursor)
    {
        Crep crep = new Crep();

        int idIndex;
        int cobranzaIndex;
        int vendedorIndex;
        int saleIndex;
        int companyIndex;
        int clientIndex;
        int nameIndex;
        int facturaIndex;
        int stateIndex;
        int obsIndex;
        int fechaFacturaIndex;
        int fechaPagoIndex;
        int diasIndex;
        int totalIndex;
        int aCuentaIndex;
        int saldoIndex;
        int intCobIndex;
        int interesesIndex;
        int importeNetoIndex;
        int pagoIndex;
        int efectivoIndex;
        int diferenciaIndex;
        int impChequeIndex;
        int bankIndex;
        int numCheqIndex;
        int interesIndex;
        int capitalIndex;
        int ollaIndex;
        int bloqueadoIndex;
        int fechaIndex;
        int llevarIndex;
        int idFechaIndex;
        int idHoraIndex;


        if(cursor != null)
        {
            if(cursor.getColumnIndex(columnId) != -1)
            {
                idIndex = cursor.getColumnIndexOrThrow(columnId);
                crep.setId(cursor.getString(idIndex));
            }
            if(cursor.getColumnIndex(columnCobranza) != -1)
            {
                cobranzaIndex = cursor.getColumnIndexOrThrow(columnCobranza);
                crep.setCobranza(cursor.getString(cobranzaIndex));
            }
            if(cursor.getColumnIndex(columnVendedor) != -1)
            {
                vendedorIndex = cursor.getColumnIndexOrThrow(columnVendedor);
                crep.setVendedor(cursor.getString(vendedorIndex));
            }
            if(cursor.getColumnIndex(columnSale) != -1)
            {
                saleIndex = cursor.getColumnIndexOrThrow(columnSale);
                crep.setVenta(cursor.getString(saleIndex));
            }
            if(cursor.getColumnIndex(columnCompany) != -1)
            {
                companyIndex = cursor.getColumnIndexOrThrow(columnCompany);
                crep.setEmpresa(cursor.getString(companyIndex));
            }
            if(cursor.getColumnIndex(columnClient) != -1)
            {
                clientIndex = cursor.getColumnIndexOrThrow(columnClient);
                crep.setCliente(cursor.getString(clientIndex));
            }
            if(cursor.getColumnIndex(columnName) != -1)
            {
                nameIndex = cursor.getColumnIndexOrThrow(columnName);
                crep.setNombre(cursor.getString(nameIndex));
            }
            if(cursor.getColumnIndex(columnFactura) != -1)
            {
                facturaIndex = cursor.getColumnIndexOrThrow(columnFactura);
                crep.setFactura(cursor.getString(facturaIndex));
            }
            if(cursor.getColumnIndex(columnState) != -1)
            {
                stateIndex = cursor.getColumnIndexOrThrow(columnState);
                crep.setEstatus(cursor.getString(stateIndex));
            }
            if(cursor.getColumnIndex(columnObs) != -1)
            {
                obsIndex = cursor.getColumnIndexOrThrow(columnObs);
                crep.setObs(cursor.getString(obsIndex));
            }
            if(cursor.getColumnIndex(columnFechaFactura) != -1)
            {
                fechaFacturaIndex = cursor.getColumnIndexOrThrow(columnFechaFactura);
                crep.setFechaFactura(cursor.getString(fechaFacturaIndex));
            }
            if(cursor.getColumnIndex(columnFechaPago) != -1)
            {
                fechaPagoIndex = cursor.getColumnIndexOrThrow(columnFechaPago);
                crep.setFechaPago(cursor.getString(fechaPagoIndex));
            }
            if(cursor.getColumnIndex(columnDias) != -1)
            {
                diasIndex = cursor.getColumnIndexOrThrow(columnDias);
                crep.setDias(cursor.getInt(diasIndex));
            }
            if(cursor.getColumnIndex(columnTotal) != -1)
            {
                totalIndex = cursor.getColumnIndexOrThrow(columnTotal);
                crep.setTotal(cursor.getFloat(totalIndex));
            }
            if(cursor.getColumnIndex(columnACuenta) != -1)
            {
                aCuentaIndex = cursor.getColumnIndexOrThrow(columnACuenta);
                crep.setaCuenta(cursor.getFloat(aCuentaIndex));
            }
            if(cursor.getColumnIndex(columnSaldo) != -1)
            {
                saldoIndex = cursor.getColumnIndexOrThrow(columnSaldo);
                crep.setSaldo(cursor.getFloat(saldoIndex));
            }
            if(cursor.getColumnIndex(columnIntCob) != -1)
            {
                intCobIndex = cursor.getColumnIndexOrThrow(columnIntCob);
                crep.setIntCob(cursor.getFloat(intCobIndex));
            }
            if(cursor.getColumnIndex(columnIntereses) != -1)
            {
                interesesIndex = cursor.getColumnIndexOrThrow(columnIntereses);
                crep.setIntereses(cursor.getFloat(interesesIndex));
            }
            if(cursor.getColumnIndex(columnImporteNeto) != -1)
            {
                importeNetoIndex = cursor.getColumnIndexOrThrow(columnImporteNeto);
                crep.setImporteNeto(cursor.getFloat(importeNetoIndex));
            }
            if(cursor.getColumnIndex(columnPago) != -1)
            {
                pagoIndex = cursor.getColumnIndexOrThrow(columnPago);
                crep.setPago(cursor.getFloat(pagoIndex));
            }
            if(cursor.getColumnIndex(columnEfectivo) != -1)
            {
                efectivoIndex = cursor.getColumnIndexOrThrow(columnEfectivo);
                crep.setEfectivo(cursor.getFloat(efectivoIndex));
            }
            if(cursor.getColumnIndex(columnDiferencia) != -1)
            {
                diferenciaIndex = cursor.getColumnIndexOrThrow(columnDiferencia);
                crep.setDiferencia(cursor.getFloat(diferenciaIndex));
            }
            if(cursor.getColumnIndex(columnImpCheque) != -1)
            {
                impChequeIndex = cursor.getColumnIndexOrThrow(columnImpCheque);
                crep.setImpCheque(cursor.getFloat(impChequeIndex));
            }
            if(cursor.getColumnIndex(columnBank) != -1)
            {
                bankIndex = cursor.getColumnIndexOrThrow(columnBank);
                crep.setBanco(cursor.getString(bankIndex));
            }
            if(cursor.getColumnIndex(columnNumCheq) != -1)
            {
                numCheqIndex = cursor.getColumnIndexOrThrow(columnNumCheq);
                crep.setNumCheq(cursor.getFloat(numCheqIndex));
            }
            if(cursor.getColumnIndex(columnInteres) != -1)
            {
                interesIndex = cursor.getColumnIndexOrThrow(columnInteres);
                crep.setInteres(cursor.getFloat(interesIndex));
            }
            if(cursor.getColumnIndex(columnCapital) != -1)
            {
                capitalIndex = cursor.getColumnIndexOrThrow(columnCapital);
                crep.setCapital(cursor.getFloat(capitalIndex));
            }
            if(cursor.getColumnIndex(columnOlla) != -1)
            {
                ollaIndex = cursor.getColumnIndexOrThrow(columnOlla);
                crep.setOlla(cursor.getString(ollaIndex));
            }
            if(cursor.getColumnIndex(columnBloqueado) != -1)
            {
                bloqueadoIndex = cursor.getColumnIndexOrThrow(columnBloqueado);
                crep.setBloqueado(cursor.getString(bloqueadoIndex));
            }
            if(cursor.getColumnIndex(columnFecha) != -1)
            {
                fechaIndex = cursor.getColumnIndexOrThrow(columnFecha);
                crep.setFecha(cursor.getString(fechaIndex));
            }
            if(cursor.getColumnIndex(columnLlevar) != -1)
            {
                llevarIndex = cursor.getColumnIndexOrThrow(columnLlevar);
                crep.setLlevar(cursor.getString(llevarIndex));
            }
            if(cursor.getColumnIndex(columnIdFecha) != -1)
            {
                idFechaIndex = cursor.getColumnIndexOrThrow(columnIdFecha);
                crep.setIdFecha(cursor.getString(idFechaIndex));
            }
            if(cursor.getColumnIndex(columnIdHora) != -1)
            {
                idHoraIndex = cursor.getColumnIndexOrThrow(columnIdHora);
                crep.setIdHora(cursor.getString(idHoraIndex));
            }
        }
        return crep;
    }


    private void setContentValue(Crep crep)
    {
        initialValues = new ContentValues();
        initialValues.put(columnId , crep.getId());
        initialValues.put(columnCobranza , crep.getCobranza());
        initialValues.put(columnVendedor , crep.getVendedor());
        initialValues.put(columnSale , crep.getVenta());
        initialValues.put(columnCompany , crep.getEmpresa());
        initialValues.put(columnClient , crep.getCliente());
        initialValues.put(columnName , crep.getNombre());
        initialValues.put(columnFactura , crep.getFactura());
        initialValues.put(columnState , crep.getEstatus());
        initialValues.put(columnObs , crep.getObs());
        initialValues.put(columnFechaFactura , crep.getFechaFactura());
        initialValues.put(columnFechaPago ,  crep.getFechaPago());
        initialValues.put(columnDias , crep.getDias());
        initialValues.put(columnTotal , crep.getTotal());
        initialValues.put(columnACuenta , crep.getaCuenta());
        initialValues.put(columnSaldo , crep.getSaldo());
        initialValues.put(columnIntCob , crep.getIntCob());
        initialValues.put(columnIntereses , crep.getIntereses());
        initialValues.put(columnImporteNeto , crep.getImporteNeto());
        initialValues.put(columnPago , crep.getPago());
        initialValues.put(columnEfectivo , crep.getEfectivo());
        initialValues.put(columnDiferencia , crep.getDiferencia());
        initialValues.put(columnImpCheque , crep.getImpCheque());
        initialValues.put(columnBank , crep.getBanco());
        initialValues.put(columnNumCheq , crep.getNumCheq());
        initialValues.put(columnInteres , crep.getInteres());
        initialValues.put(columnCapital , crep.getCapital());
        initialValues.put(columnOlla , crep.getOlla());
        initialValues.put(columnBloqueado , crep.getBloqueado());
        initialValues.put(columnFecha , crep.getFecha());
        initialValues.put(columnLlevar , crep.getLlevar());
        initialValues.put(columnIdFecha , crep.getIdFecha());
        initialValues.put(columnIdHora ,  crep.getIdHora());

    }

    private ContentValues getContentValue()
    {
        return initialValues;
    }

    @Override
    public int getNumberOfCreps()
    {
        cursor = super.rawQuery(
                "SELECT " + columnId +
                        " FROM " + tableName, null);

        return cursor.getCount();
    }

    @Override
    public boolean updateCrep(Crep crep)
    {
        /*cursor = super.rawQuery(
                "UPDATE " + tableName +
                        " SET " + columnContact1 + " = '" + client.getContacto1() + "' ,"
                        + columnContact2 + " = '" + client.getContacto2() + "' ,"
                        + columnPhone1 + " = '" + client.getTelefono1() + "' ,"
                        + columnPhone2 + " = '" + client.getTelefono2() + "' ,"
                        + columnEmail1 + " = '" + client.getEmail1() + "' ,"
                        + columnEmail2 + " = '" + client.getEmail2() + "'" +
                        " WHERE " + columnId + " = '" + client.getClave() + "'", null
        );

        return cursor.getCount() > 0;*/
        return true;
    }

    public void updateCreps(Context ctx)
    {
        database.execSQL("delete from CREP where id != -1");

        File file = new File(CurrentData.getInternalStoragePath() + "/creps.sql");

        Log.d("UPDATE", "path: " + file.getAbsolutePath());

        InputStream is = null;
        try
        {
            is = new FileInputStream(file);
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }

        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        String line;
        try{
            while((line = r.readLine()) != null)
            {
                String header = line;
                line = r.readLine();
                String values = line;
                String query = header + values;
                database.execSQL(query);

            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }


    }
    public Crep fetchCrepById(String crepId) {
        Crep crep = new Crep();
        //cursor = super.query(tableName, productColumns, selection, selectionArgs, columnId);
        cursor = super.rawQuery("SELECT * FROM CREP WHERE ID = '" + crepId+ "'", null);

        Log.w("QUERY: ", "SELECT * FROM CREP WHERE clave = '" + crepId + "'");

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                crep = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return crep;
    }

}
