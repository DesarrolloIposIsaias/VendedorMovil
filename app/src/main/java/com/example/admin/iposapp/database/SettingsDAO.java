package com.example.admin.iposapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.admin.iposapp.model.Settings;

public class SettingsDAO extends DbContentProvider
        implements InterfaceSettingsSchema, InterfaceSettingsDAO
{
    private Cursor cursor;
    private ContentValues initialValues;

    public SettingsDAO(SQLiteDatabase db)
    {
        super(db);
    }

    protected Settings cursorToEntity(Cursor cursor)
    {
        Settings settings= new Settings();

        int idIndex;
        int serverIndex;
        int branchIndex;
        int companyIndex;
        int appUserIndex;
        int appUserPassIndex;
        int lastSaleIndex;
        int billingIndex;
        int sellerSerieIndex;
        int clientSerieIdnex;

        if (cursor != null)
        {
            if (cursor.getColumnIndex(columnId) != -1)
            {
                idIndex = cursor.getColumnIndexOrThrow(columnId);
                settings.setId(cursor.getInt(idIndex));
            }

            if(cursor.getColumnIndex(columnServer) != -1)
            {
                serverIndex = cursor.getColumnIndexOrThrow(columnServer);
                settings.setServer(cursor.getString(serverIndex));
            }

            if(cursor.getColumnIndex(columnBranch) != -1)
            {
                branchIndex = cursor.getColumnIndexOrThrow(columnBranch);
                settings.setBranch(cursor.getString(branchIndex));
            }

            if(cursor.getColumnIndex(columnCompany) != -1)
            {
                companyIndex = cursor.getColumnIndexOrThrow(columnCompany);
                settings.setCompany(cursor.getString(companyIndex));
            }

            if(cursor.getColumnIndex(columnAppUser) != -1)
            {
                appUserIndex = cursor.getColumnIndexOrThrow(columnAppUser);
                settings.setAppUser(cursor.getString(appUserIndex));
            }

            if(cursor.getColumnIndex(columnAppUserPass) != -1)
            {
                appUserPassIndex = cursor.getColumnIndexOrThrow(columnAppUserPass);
                settings.setAppUserPass(cursor.getString(appUserPassIndex));
            }
            if(cursor.getColumnIndex(columnLastSale) != -1)
            {
                lastSaleIndex = cursor.getColumnIndexOrThrow(columnLastSale);
                settings.setLastSale(cursor.getString(lastSaleIndex));
            }
            if(cursor.getColumnIndex(columnBilling) != -1)
            {
                billingIndex = cursor.getColumnIndexOrThrow(columnBilling);
                settings.setBilling(cursor.getColumnName(billingIndex));
            }
            if(cursor.getColumnIndex(columnSellerSerie) != -1)
            {
                sellerSerieIndex = cursor.getColumnIndexOrThrow(columnSellerSerie);
                settings.setSellerSerie(cursor.getString(sellerSerieIndex));
            }
            if(cursor.getColumnIndex(columnClientSerie) != -1)
            {
                clientSerieIdnex = cursor.getColumnIndexOrThrow(columnClientSerie);
                settings.setClientSerie(cursor.getString(clientSerieIdnex));
            }
        }

        return settings;
    }

    private void setContentValue(Settings settings)
    {
        initialValues = new ContentValues();

        initialValues.put(columnId, settings.getId());
        initialValues.put(columnServer, settings.getServer());
        initialValues.put(columnBranch, settings.getBranch());
        initialValues.put(columnCompany, settings.getCompany());
        initialValues.put(columnAppUser, settings.getAppUser());
        initialValues.put(columnAppUserPass, settings.getAppUserPass());
        initialValues.put(columnLastSale, settings.getLastSale());
        initialValues.put(columnBilling, settings.getBilling());
        initialValues.put(columnSellerSerie, settings.getSellerSerie());
        initialValues.put(columnClientSerie, settings.getClientSerie());
    }


    @Override
    public Settings fetchSettingsBySeller(String seller, String pass)
    {
        cursor = super.rawQuery("SELECT * FROM " + tableName +
                " WHERE " + columnAppUser + " = '" + seller +
                "' AND " + columnAppUserPass + " = '" + pass + "'", null);

        if(cursor != null)
        {
            cursor.moveToFirst();
            return cursorToEntity(cursor);
        }

        Log.w("Settings: ", "Problema al recuperar configuracion");

        return null;
    }

    @Override
    public Settings fetchSettingsById(String id)
    {
        return null;
    }

    @Override
    public Settings fetchSettingsByUser(String user)
    {
        return null;
    }

    @Override
    public Settings fetchSettingsByPassword(String pass)
    {
        return null;
    }

    public boolean addSettings(Settings settings)
    {
        setContentValue(settings);

        return super.insert(tableName, getContentValue()) > 0;

    }

    private ContentValues getContentValue()
    {
        return initialValues;
    }

    @Override
    public boolean deleteSettings(Settings settings)
    {
        return false;
    }

    @Override
    public int exist(String id, String pass)
    {
        Settings settings;

        cursor = super.rawQuery(
                "SELECT " + columnId +
                " FROM " + tableName +
                " WHERE " + columnAppUser + " = '" + id + "'" +
                " AND " + columnAppUserPass + " = '" + pass + "'", null);

        if(cursor != null && cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            settings = cursorToEntity(cursor);
            return settings.getId();
        }

        return -1;
    }

    @Override
    public boolean update(Settings settings)
    {
        setContentValue(settings);

        long i = super.update(tableName, initialValues, columnId + "=?",
                new String[]{String.valueOf(settings.getId())});

        return i > 0;
    }

    @Override
    public boolean updateLasClient(int lastClient, String user)
    {
        cursor = super.rawQuery(
                "UPDATE " + tableName
                + " SET " + columnClientSerie + " = '" + lastClient
                + "' WHERE " + columnAppUser + " = '" + user + "'",
                null
        );

        Log.w("UPDATE LAST CLIENT:  ", "UPDATE " + tableName
                                    + " SET " + columnClientSerie + " = '" + lastClient
                                    + "' WHERE " + columnAppUser + " = '" + user + "'");

        return cursor.getCount() > 0;
    }
}
