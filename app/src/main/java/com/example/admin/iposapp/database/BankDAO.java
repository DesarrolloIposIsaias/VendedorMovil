package com.example.admin.iposapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.admin.iposapp.utility.CurrentData;
import com.example.admin.iposapp.model.Bank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Desarrollo IPOS on 08/12/2017.
 */

public class BankDAO extends DbContentProvider
        implements InterfaceBankSchema, InterfaceBankDAO{

    private Cursor cursor;
    private ContentValues initialValues;

    public BankDAO(SQLiteDatabase db)
    {

        super(db);
    }

    public Bank fetchBankById(int id)
    {
        final String selectionArgs[] = {String.valueOf(id)};
        final String selection = id + "?";
        Bank bank = new Bank();
        cursor = super.query(tableName, bankColumns, selection, selectionArgs, columnId);

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                bank = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return bank;
    }

    private ArrayList<String> getNames(ArrayList<Bank> banks){

        ArrayList<String> names = new ArrayList<>();

        for (Bank bank : banks)
        {
            names.add(bank.getNombre());
        }

        return names;
    }

    public ArrayList<String> fetchBanksNames()
    {
        ArrayList<Bank> bankList = new ArrayList<Bank>();
        cursor = super.query(tableName, bankColumns, null, null, columnId);

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                Bank bank = cursorToEntity(cursor);
                bankList.add(bank);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return getNames(bankList);
    }

    public List<Bank> fetchAllBanks()
    {
        List<Bank> bankList = new ArrayList<Bank>();
        cursor = super.query(tableName, bankColumns, null, null, columnId);

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                Bank bank = cursorToEntity(cursor);
                bankList.add(bank);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return bankList;
    }

    public boolean addBank(Bank bank)
    {
        setContentValue(bank);
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

    public boolean addBanks(List<Bank> banks)
    {
        for (int i = 0; i < banks.size(); i++)
        {
            setContentValue(banks.get(i));
            try
            {
                if(super.insert(tableName, getContentValue()) > 0)
                {
                    Log.w("Bank: ", "Added");
                    Log.w("Bank: ", banks.get(i).getNombre());
                }
                else Log.w("Bank: ", "Problem adding bank");
            }
            catch (SQLiteConstraintException ex)
            {
                Log.w("Database", ex.getMessage());
                return false;
            }
        }
        return true;
    }

    public boolean deleteAllBanks() {
        return false;
    }

    protected Bank cursorToEntity(Cursor cursor)
    {
        Bank bank = new Bank();

        int idIndex;
        int claveIndex;
        int nameIndex;


        if(cursor != null)
        {
            if(cursor.getColumnIndex(columnId) != -1)
            {
                idIndex = cursor.getColumnIndexOrThrow(columnId);
                bank.setId(cursor.getString(idIndex));
            }
            if(cursor.getColumnIndex(columnClave) != -1)
            {
                claveIndex = cursor.getColumnIndexOrThrow(columnClave);
                bank.setClave(cursor.getString(claveIndex));
            }
            if(cursor.getColumnIndex(columnName) != -1)
            {
                nameIndex = cursor.getColumnIndexOrThrow(columnName);
                bank.setNombre(cursor.getString(nameIndex));
            }


        }
        return bank;
    }

    private void setContentValue(Bank bank)
    {
        initialValues = new ContentValues();
        initialValues.put(columnId, bank.getId());
        initialValues.put(columnClave, bank.getClave());
        initialValues.put(columnName, bank.getNombre());

    }

    private ContentValues getContentValue()
    {
        return initialValues;
    }

    public List<Bank> searchByFirstLetter(char search)
    {
        List<Bank> bankList = new ArrayList<Bank>();
        cursor = database.rawQuery("SELECT id, nombre, clave FROM BANCO WHERE clave LIKE '" +
                search +"%' or nombre like '"+ search + "%'", null);

        Log.w("FETCH BANK", "SELECT id, nombre, clave FROM BANCO WHERE clave LIKE '" +
                search +"%' or nombre like '"+ search + "'");

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                Bank bank = cursorToEntity(cursor);
                bankList.add(bank);
                cursor.moveToNext();
            }
            cursor.close();
        }
        else
        {
            Log.w("Bank", "NOT FOUND");
        }

        return bankList;

    }

    @Override
    public boolean isEmpty()
    {
        cursor = super.rawQuery("SELECT " + columnId +
                " FROM " + tableName +
                " WHERE " + columnId +
                " LIKE '%1'", null);

        return cursor.getCount() < 1;
    }

    @Override
    public int getNumberOfBanks()
    {
        cursor = super.rawQuery(
                "SELECT " + columnId +
                        " FROM " + tableName, null);

        return cursor.getCount();
    }

    @Override
    public boolean updateBank(Bank client)
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

    public void updateBanks(Context ctx)
    {
        database.execSQL("delete from BANCO where clave != -1");

        File file = new File(CurrentData.getInternalStoragePath() + "/bancos.sql");

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

    public Bank fetchBankById(String bankId) {
        Bank bank = new Bank();
        //cursor = super.query(tableName, productColumns, selection, selectionArgs, columnId);
        cursor = super.rawQuery("SELECT * FROM BANCO WHERE clave = '" + bankId+ "'", null);

        Log.w("QUERY: ", "SELECT * FROM BANCO WHERE clave = '" + bankId + "'");

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                bank = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return bank;
    }
}
