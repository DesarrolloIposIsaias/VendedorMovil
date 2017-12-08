package com.example.admin.iposapp.database;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

import com.example.admin.iposapp.model.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 31/08/2016.
 */
public class ClientProvider extends ContentProvider
{
    List<Client> clients;
    ArrayList<String> clientsArray;
    private Database database;



    @Override
    public boolean onCreate()
    {

        return false;
    }

    @Nullable
    @Override
    public Cursor query(
            Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder)
    {
        clientsArray = new ArrayList<>();
        database = new Database(getContext());
        database.open();
        clients = Database.clientDao.fetchAllClients();
        for(int i = 0; i < clients.size(); i++)
        {
            clientsArray.add(clients.get(i).getNombre() +"<"+ clients.get(i).getClave()+">");

        }
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{
                BaseColumns._ID,
                SearchManager.SUGGEST_COLUMN_TEXT_1,
                SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID
        });



        if(clients != null)
        {
            String query = uri.getLastPathSegment().toString();
            int limit = Integer.parseInt(uri.getQueryParameter(SearchManager.SUGGEST_PARAMETER_LIMIT));

            int length = clientsArray.size();

            for(int i = 0; i < length && matrixCursor.getCount() < limit; i++) {
                String clientsString = clientsArray.get(i);

                if(clientsString.contains(query)){
                    matrixCursor.addRow(new Object[]{
                          i,clientsString,i
                    });

                    }
            }
        }
        database.close();
        return matrixCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri)
    {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        return 0;
    }
}
