package com.example.admin.iposapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.admin.iposapp.utility.CurrentData;
import com.example.admin.iposapp.model.Client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 21/06/2016.
 */
public class ClientDAO extends DbContentProvider
        implements InterfaceClientSchema, InterfaceClientDAO {

    private Cursor cursor;
    private ContentValues initialValues;

    public ClientDAO(SQLiteDatabase db)
    {

        super(db);
    }

    public Client fetchClientById(int id)
    {
        final String selectionArgs[] = {String.valueOf(id)};
        final String selection = id + "?";
        Client client = new Client();
        cursor = super.query(tableName, clientColumns, selection, selectionArgs, columnId);

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                client = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return client;
    }

    public ArrayList<Client> fetchClients(){
        ArrayList<Client> clientList = new ArrayList<>();

        cursor = super.query(
                tableName,
                clientColumns,
                null,
                null,
                columnId
        );

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                Client client = cursorToEntity(cursor);
                clientList.add(client);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return clientList;
    }

    public List<Client> fetchAllClients()
    {
        List<Client> clientList = new ArrayList<Client>();
        cursor = super.query(tableName, clientColumns, null, null, columnId);

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                Client client = cursorToEntity(cursor);
                clientList.add(client);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return clientList;
    }

    public boolean addClient(Client client)
    {
        setContentValue(client);
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

    public boolean addClients(List<Client> clients)
    {
        for (int i = 0; i < clients.size(); i++)
        {
            setContentValue(clients.get(i));
            try
            {
                if(super.insert(tableName, getContentValue()) > 0)
                {
                    Log.w("Client: ", "Added");
                    Log.w("Client: ", clients.get(i).getNombre());
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

    public boolean deleteAllUsers() {
        return false;
    }

    protected Client cursorToEntity(Cursor cursor)
    {
        Client client = new Client();

        int idIndex;
        int nameIndex;
        int namesIndex;
        int lastNameIndex;
        int addressIndex;
        int phone1Index;
        int phone2Index;
        int rfcIndex;
        int balanceIndex;
        int cityIndex;
        int sellerIdIndex;
        int postalCodeIndex;
        int priceListIdIndex;
        int creditLimitIndex;
        int daysIndex;
        int streetsIndex;
        int contact1Index;
        int contact2Index;
        int email1Index;
        int email2Index;
        int serieIndex;
        int countryIndex;
        int stateIndex;
        int neighborhoodIndex;
        int intNumberIndex;
        int extNumberIndex;
        int iepsIndex;
        int adressServiceIndex;
        int deadlineIndex;
        int priceIndex;
        int paydadayIndex;
        int reviewIndex;
        int cardIndex;
        int creditIndex;
        int checkIndex;
        int transferIndex;
        int blockedIndex;

        if(cursor != null)
        {
            if(cursor.getColumnIndex(columnId) != -1)
            {
                idIndex = cursor.getColumnIndexOrThrow(columnId);
                client.setClave(cursor.getString(idIndex));
            }
            if(cursor.getColumnIndex(columnName) != -1)
            {
                nameIndex = cursor.getColumnIndexOrThrow(columnName);
                client.setNombre(cursor.getString(nameIndex));
            }
            if(cursor.getColumnIndex(columnNames) != -1)
            {
                namesIndex = cursor.getColumnIndexOrThrow(columnNames);
                client.setNombres(cursor.getString(namesIndex));
            }

            if(cursor.getColumnIndex(columnLastName) != -1)
            {
                lastNameIndex = cursor.getColumnIndexOrThrow(columnLastName);
                client.setApellidos(cursor.getString(lastNameIndex));
            }
            if(cursor.getColumnIndex(columnAddress) != -1)
            {
                addressIndex = cursor.getColumnIndexOrThrow(columnAddress);
                client.setDomicilio(cursor.getString(addressIndex));
            }
            if(cursor.getColumnIndex(columnPhone1) != -1)
            {
                phone1Index = cursor.getColumnIndexOrThrow(columnPhone1);
                client.setTelefono1(cursor.getString(phone1Index));
            }
            if(cursor.getColumnIndex(columnPhone2) != -1)
            {
                phone2Index = cursor.getColumnIndexOrThrow(columnPhone2);
                client.setTelefono2(cursor.getString(phone2Index));
            }
            if(cursor.getColumnIndex(columnRfc) != -1)
            {
                rfcIndex = cursor.getColumnIndexOrThrow(columnRfc);
                client.setRfc(cursor.getString(rfcIndex));
            }
            if(cursor.getColumnIndex(columnBalance) != -1)
            {
                balanceIndex = cursor.getColumnIndexOrThrow(columnBalance);
                client.setSaldo(cursor.getFloat(balanceIndex));
            }
            if(cursor.getColumnIndex(columnCity) != -1)
            {
                cityIndex = cursor.getColumnIndexOrThrow(columnCity);
                client.setCiudad(cursor.getString(cityIndex));
            }
            if(cursor.getColumnIndex(columnSellerId) != -1)
            {
                sellerIdIndex = cursor.getColumnIndexOrThrow(columnSellerId);
                client.setVendedorId(cursor.getString(sellerIdIndex));
            }
            if(cursor.getColumnIndex(columnPostalCode) != -1)
            {
                postalCodeIndex = cursor.getColumnIndexOrThrow(columnPostalCode);
                client.setCodigoPostal(cursor.getString(postalCodeIndex));
            }
            if(cursor.getColumnIndex(columnPriceListId) != -1)
            {
                priceListIdIndex = cursor.getColumnIndexOrThrow(columnPriceListId);
                client.setListaPrecioId(cursor.getString(priceListIdIndex));
            }
            if(cursor.getColumnIndex(columnCreditLimit) != -1)
            {
                creditLimitIndex = cursor.getColumnIndexOrThrow(columnCreditLimit);
                client.setLimiteCredito(cursor.getFloat(creditLimitIndex));
            }
            if(cursor.getColumnIndex(columnDays) != -1)
            {
                daysIndex = cursor.getColumnIndexOrThrow(columnDays);
                client.setDias(cursor.getInt(daysIndex));
            }
            if(cursor.getColumnIndex(columnStreets) != -1)
            {
                streetsIndex = cursor.getColumnIndexOrThrow(columnStreets);
                client.setCalles(cursor.getString(streetsIndex));
            }
            if(cursor.getColumnIndex(columnContact1) != -1)
            {
                contact1Index = cursor.getColumnIndexOrThrow(columnContact1);
                client.setContacto1(cursor.getString(contact1Index));
            }
            if(cursor.getColumnIndex(columnContact2) != -1)
            {
                contact2Index = cursor.getColumnIndexOrThrow(columnContact2);
                client.setContacto2(cursor.getString(contact2Index));
            }
            if(cursor.getColumnIndex(columnEmail1) != -1)
            {
                email1Index = cursor.getColumnIndexOrThrow(columnEmail1);
                client.setEmail1(cursor.getString(email1Index));
            }
            if(cursor.getColumnIndex(columnEmail2) != -1)
            {
                email2Index = cursor.getColumnIndexOrThrow(columnEmail2);
                client.setEmail2(cursor.getString(email2Index));
            }
            if(cursor.getColumnIndex(columnSerie) != -1)
            {
                serieIndex = cursor.getColumnIndexOrThrow(columnSerie);
                client.setSerie(cursor.getString(serieIndex));
            }
            if(cursor.getColumnIndex(columnCountry) != -1)
            {
                countryIndex = cursor.getColumnIndexOrThrow(columnCountry);
                client.setPais(cursor.getString(countryIndex));
            }
            if(cursor.getColumnIndex(columnState) != -1)
            {
                stateIndex = cursor.getColumnIndexOrThrow(columnState);
                client.setEstado(cursor.getString(stateIndex));
            }
            if(cursor.getColumnIndex(columnNeighborhood) != -1)
            {
                neighborhoodIndex = cursor.getColumnIndexOrThrow(columnNeighborhood);
                client.setColonia(cursor.getString(neighborhoodIndex));
            }
            if(cursor.getColumnIndex(columnIntNumber) != -1)
            {
                intNumberIndex = cursor.getColumnIndexOrThrow(columnIntNumber);
                client.setNumeroInterior(cursor.getString(intNumberIndex));
            }
            if(cursor.getColumnIndex(columnExtNumber) != -1)
            {
                extNumberIndex = cursor.getColumnIndexOrThrow(columnExtNumber);
                client.setNumeroExterior(cursor.getString(extNumberIndex));
            }
            if(cursor.getColumnIndex(columnIeps) != -1)
            {
                iepsIndex = cursor.getColumnIndexOrThrow(columnIeps);
                client.setCuentaIeps(cursor.getString(iepsIndex));
            }
            if(cursor.getColumnIndex(columnAddresService) != -1)
            {
                adressServiceIndex = cursor.getColumnIndexOrThrow(columnAddresService);
                client.setServicioDomicilio(cursor.getString(adressServiceIndex));
            }
            if(cursor.getColumnIndex(columnDeadline) != -1)
            {
                deadlineIndex = cursor.getColumnIndexOrThrow(columnDeadline);
                client.setPlazo(cursor.getString(deadlineIndex));
            }
            if(cursor.getColumnIndex(columnPrice) != -1)
            {
                priceIndex = cursor.getColumnIndexOrThrow(columnPrice);
                client.setPrecio(cursor.getString(priceIndex));
            }
            if(cursor.getColumnIndex(columnPayday) != -1)
            {
                paydadayIndex = cursor.getColumnIndexOrThrow(columnPayday);
                client.setDiaPago(cursor.getString(paydadayIndex));
            }
            if(cursor.getColumnIndex(columnReview) != -1)
            {
                reviewIndex = cursor.getColumnIndexOrThrow(columnReview);
                client.setRevision(cursor.getString(reviewIndex));
            }
            if(cursor.getColumnIndex(columnCard) != -1)
            {
                cardIndex = cursor.getColumnIndexOrThrow(columnCard);
                client.setTarjeta(cursor.getString(cardIndex));
            }
            if(cursor.getColumnIndex(columnCredit) != -1)
            {
                creditIndex = cursor.getColumnIndexOrThrow(columnCredit);
                client.setCredito(cursor.getString(creditIndex));
            }
            if(cursor.getColumnIndex(columnCheck) != -1)
            {
                checkIndex = cursor.getColumnIndexOrThrow(columnCheck);
                client.setCheque(cursor.getString(checkIndex));
            }
            if(cursor.getColumnIndex(columnTransfer) != -1)
            {
                transferIndex = cursor.getColumnIndexOrThrow(columnTransfer);
                client.setTransferencia(cursor.getString(transferIndex));
            }
            if(cursor.getColumnIndex(columnBlocked) != -1)
            {
                blockedIndex = cursor.getColumnIndexOrThrow(columnBlocked);
                client.setBloqueado(cursor.getString(blockedIndex));
            }

        }
        return client;
    }

    private void setContentValue(Client client)
    {
        initialValues = new ContentValues();
        initialValues.put(columnId, client.getClave());
        initialValues.put(columnName, client.getNombre());
        initialValues.put(columnNames, client.getNombres());
        initialValues.put(columnLastName, client.getApellidos());
        initialValues.put(columnAddress, client.getDomicilio());
        initialValues.put(columnPhone1, client.getTelefono1());
        initialValues.put(columnPhone2, client.getTelefono2());
        initialValues.put(columnRfc, client.getRfc());
        initialValues.put(columnBalance, client.getSaldo());
        initialValues.put(columnCity, client.getCiudad());
        initialValues.put(columnSellerId, client.getVendedorId());
        initialValues.put(columnPostalCode, client.getCodigoPostal());
        initialValues.put(columnPriceListId, client.getListaPrecioId());
        initialValues.put(columnCreditLimit, client.getLimiteCredito());
        initialValues.put(columnDays, client.getDias());
        initialValues.put(columnStreets, client.getCalles());
        initialValues.put(columnContact1, client.getContacto1());
        initialValues.put(columnContact2, client.getContacto2());
        initialValues.put(columnEmail1, client.getEmail1());
        initialValues.put(columnEmail2, client.getEmail2());
        initialValues.put(columnSerie, client.getSerie());
        initialValues.put(columnCountry, client.getPais());
        initialValues.put(columnState, client.getEstado());
        initialValues.put(columnNeighborhood, client.getColonia());
        initialValues.put(columnIntNumber, client.getNumeroInterior());
        initialValues.put(columnExtNumber, client.getNumeroExterior());
        initialValues.put(columnIeps, client.getCuentaIeps());
        initialValues.put(columnAddresService, client.getServicioDomicilio());
        initialValues.put(columnDeadline, client.getPlazo());
        initialValues.put(columnPrice, client.getPrecio());
        initialValues.put(columnPayday, client.getDiaPago());
        initialValues.put(columnReview, client.getRevision());
        initialValues.put(columnCard, client.getTarjeta());
        initialValues.put(columnCredit, client.getCredito());
        initialValues.put(columnCheck, client.getCheque());
        initialValues.put(columnTransfer, client.getTransferencia());
        initialValues.put(columnBlocked, client.getBloqueado());
    }

    private ContentValues getContentValue()
    {
        return initialValues;
    }

    public List<Client> searchByFirstLetter(char search)
    {
        List<Client> clientList = new ArrayList<Client>();
        cursor = database.rawQuery("SELECT nombre, clave FROM PERSONA WHERE clave LIKE '" +
                search +"%' or nombre like '"+ search + "%'", null);

        Log.w("FETCH CLIENT", "SELECT nombre, clave FROM PERSONA WHERE clave LIKE '" +
                search +"%' or nombre like '"+ search + "%'");

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                Client client = cursorToEntity(cursor);
                clientList.add(client);
                cursor.moveToNext();
            }
            cursor.close();
        }
        else
        {
            Log.w("CLIENT", "NOT FOUND");
        }

        return clientList;

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
    public int getNumberOfClients()
    {
        cursor = super.rawQuery(
                "SELECT " + columnId +
                " FROM " + tableName, null);

        return cursor.getCount();
    }

    @Override
    public boolean updateClient(Client client)
    {
        cursor = super.rawQuery(
                "UPDATE " + tableName +
                " SET " + columnContact1 + " = '" + client.getContacto1() + "' ,"
                        + columnContact2 + " = '" + client.getContacto2() + "' ,"
                        + columnPhone1 + " = '" + client.getTelefono1() + "' ,"
                        + columnPhone2 + " = '" + client.getTelefono2() + "' ,"
                        + columnEmail1 + " = '" + client.getEmail1() + "' ,"
                        + columnEmail2 + " = '" + client.getEmail2() + "'" +
                 " WHERE " + columnId + " = '" + client.getClave() + "'", null
        );

        return cursor.getCount() > 0;
    }

    public void updateClients(Context ctx)
    {
        database.execSQL("delete from PERSONA where clave != -1");

        File file = new File(CurrentData.getInternalStoragePath() + "/clientes.sql");

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

    public Client fetchClientById(String clientId) {
        Client client = new Client();
        //cursor = super.query(tableName, productColumns, selection, selectionArgs, columnId);
        cursor = super.rawQuery("SELECT * FROM PERSONA WHERE clave = '" + clientId+ "'", null);

        Log.w("QUERY: ", "SELECT * FROM Persona WHERE clave = '" + clientId + "'");

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                client = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return client;
    }

    public boolean deleteClient(String clientKey) {

        String selection = columnId +  " = ?";
        String[] selectionArgs = {clientKey};

        int result = super.delete(tableName, selection, selectionArgs);

        return result > 0;
    }

}
