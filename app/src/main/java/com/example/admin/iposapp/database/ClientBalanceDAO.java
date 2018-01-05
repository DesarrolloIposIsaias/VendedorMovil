package com.example.admin.iposapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.admin.iposapp.model.Bank;
import com.example.admin.iposapp.utility.CurrentData;
import com.example.admin.iposapp.model.ClientBalance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Desarrollo IPOS on 03/01/2018.
 */

public class ClientBalanceDAO extends DbContentProvider
        implements InterfaceClientBalanceSchema, InterfaceClientBalanceDAO{

    private Cursor cursor;
    private ContentValues initialValues;

    public ClientBalanceDAO(SQLiteDatabase db)
    {

        super(db);
    }

    public ClientBalance fetchClientBalanceByClient(String client){
        ClientBalance clientBalance = new ClientBalance();

        String selectionStr = columnClient + " = ? ";
        String[] selectionArgs = new String[]{client};
        cursor = super.query(tableName, clientBalanceColumns, selectionStr, selectionArgs, columnId);

        if(cursor != null)
        {
            cursor.moveToFirst();
            if(!cursor.isAfterLast()) {
                clientBalance = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return clientBalance;
    }


    public ClientBalance fetchClientBalanceById(int id)
    {
        final String selectionArgs[] = {String.valueOf(id)};
        final String selection = id + "?";
        ClientBalance clientBalance = new ClientBalance();
        cursor = super.query(tableName, clientBalanceColumns, selection, selectionArgs, columnId);

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                clientBalance = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return clientBalance;
    }

    @Override
    public boolean addClientBalance(ClientBalance clientBalance) {


        cursor = super.query(
                tableName,
                null,
                null,
                null,
                null,
                null
        );

        String[] columnNames = cursor.getColumnNames();

        setContentValue(clientBalance);

        try{
            String query =
                    "INSERT INTO " + tableName + "(" +
                            columnClient + "," +
                            columnBalance + ") " +

                            "VALUES (" +
                            "'" + clientBalance.getClient() + "'," +
                            "'" + clientBalance.getBalance() + "')";

            database.execSQL(query);

            return true;
        }
        catch (Exception ex){

            Log.w("Database:", ex.getMessage());
            return false;
        }
    }

    private void setContentValue(ClientBalance clientBalance){

        initialValues = new ContentValues();
        initialValues.put(columnId, clientBalance.getId());
        initialValues.put(columnClient, clientBalance.getId());
        initialValues.put(columnBalance, clientBalance.getBalance());
    }

    @Override
    public boolean deleteClientBalancebyId(String id) {

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

    @Override
    protected ClientBalance cursorToEntity(Cursor cursor) {
        ClientBalance clientBalance = new ClientBalance();

        if(cursor != null){
            if(cursor.getColumnIndex(columnId) != -1){
                int idIndex = cursor.getColumnIndex(columnId);
                clientBalance.setId(cursor.getInt(idIndex));
            }

            if(cursor.getColumnIndex(columnClient) != -1){
                int clientIndex = cursor.getColumnIndex(columnClient);
                clientBalance.setClient(cursor.getString(clientIndex));
            }

            if(cursor.getColumnIndex(columnBalance) != -1){
                int balanceIndex = cursor.getColumnIndex(columnBalance);
                clientBalance.setBalance(cursor.getString(balanceIndex));
            }


        }else return null;

        return clientBalance;
    }
}
