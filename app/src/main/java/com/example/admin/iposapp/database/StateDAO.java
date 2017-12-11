package com.example.admin.iposapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.admin.iposapp.model.State;
import com.example.admin.iposapp.utility.CurrentData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by usuario on 11/12/2017.
 */

public class StateDAO extends DbContentProvider implements InterfaceStateSchema, InterfaceStateDAO {
    private Cursor cursor;
    private ContentValues initialValues;

    public StateDAO(SQLiteDatabase db)
    {

        super(db);
    }

    public State fetchStateById(int id)
    {
        final String selectionArgs[] = {String.valueOf(id)};
        final String selection = id + "?";
        State state = new State();
        cursor = super.query(tableName, stateColumns, selection, selectionArgs, columnId);

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                state = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return state;
    }

    public List<State> fetchAllStates()
    {
        List<State> stateList = new ArrayList<State>();
        cursor = super.query(tableName, stateColumns, null, null, columnId);

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                State state = cursorToEntity(cursor);
                stateList.add(state);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return stateList;
    }

    public boolean addState(State state)
    {
        setContentValue(state);
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

    public boolean addStates(List<State> states)
    {
        for (int i = 0; i < states.size(); i++)
        {
            setContentValue(states.get(i));
            try
            {
                if(super.insert(tableName, getContentValue()) > 0)
                {
                    Log.w("State: ", "Added");
                    Log.w("State: ", states.get(i).getNombre());
                }
                else Log.w("State: ", "Problem adding state");
            }
            catch (SQLiteConstraintException ex)
            {
                Log.w("Database", ex.getMessage());
                return false;
            }
        }
        return true;
    }

    public boolean deleteAllStates() {
        return false;
    }

    protected State cursorToEntity(Cursor cursor)
    {
        State state = new State();

        int id;
        int clave;
        int nombre;
        int fecha;
        int hora;

        if(cursor != null)
        {
            if(cursor.getColumnIndex(columnId) != -1)
            {
                id = cursor.getColumnIndexOrThrow(columnId);
                state.setId(cursor.getString(id));
            }
            if(cursor.getColumnIndex(columnClave) != -1)
            {
                clave = cursor.getColumnIndexOrThrow(columnClave);
                state.setClave(cursor.getString(clave));
            }
            if(cursor.getColumnIndex(columnNombre) != -1)
            {
                nombre = cursor.getColumnIndexOrThrow(columnNombre);
                state.setNombre(cursor.getString(nombre));
            }

            if(cursor.getColumnIndex(columnFecha) != -1)
            {
                fecha = cursor.getColumnIndexOrThrow(columnFecha);
                state.setFecha(cursor.getString(fecha));
            }
            if(cursor.getColumnIndex(columnHora) != -1)
            {
                hora = cursor.getColumnIndexOrThrow(columnHora);
                state.setHora(cursor.getString(hora));
            }

        }
        return state;
    }

    private void setContentValue(State state)
    {
        initialValues = new ContentValues();
        initialValues.put(columnId, state.getId());
        initialValues.put(columnClave, state.getClave());
        initialValues.put(columnNombre, state.getNombre());
        initialValues.put(columnFecha, state.getFecha());
        initialValues.put(columnHora, state.getHora());
    }

    private ContentValues getContentValue()
    {
        return initialValues;
    }

    public List<State> searchByFirstLetter(char search)
    {
        List<State> stateList = new ArrayList<State>();
        cursor = database.rawQuery("SELECT clave FROM STATE WHERE clave LIKE '" +
                search +"%' or nombre like '"+ search + "%'", null);

        Log.w("FETCH STATE", "SELECT producto FROM IT WHERE clave LIKE '" +
                search +"%' or nombre like '"+  search + "%'");

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                State state = cursorToEntity(cursor);
                stateList.add(state);
                cursor.moveToNext();
            }
            cursor.close();
        }
        else
        {
            Log.w("STATE", "NOT FOUND");
        }

        return stateList;
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
    public int getNumberOfStates()
    {
        cursor = super.rawQuery(
                "SELECT " + columnId +
                        " FROM " + tableName, null);

        return cursor.getCount();
    }

    @Override
    public boolean updateState(State state)
    {
        cursor = super.rawQuery(
                "UPDATE " + tableName +
                        " SET " + columnNombre + " = '" + state.getNombre() + "' ,"
                        + columnClave + " = '" + state.getClave() + "' ,"
                        + columnFecha + " = '" + state.getFecha() + "' ,"
                        + columnHora + " = '" + state.getHora() + "' ," +
                        " WHERE " + columnId + " = '" + state.getId() + "'", null
        );

        return cursor.getCount() > 0;
    }

    public void updateStates(Context ctx)
    {
        database.execSQL("delete from State where id != -1");

        File file = new File(CurrentData.getInternalStoragePath() + "/states.sql");

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

    public State fetchStateById(String stateId) {
        State state = new State();
        //cursor = super.query(tableName, productColumns, selection, selectionArgs, columnId);
        cursor = super.rawQuery("SELECT * FROM STATE WHERE id = '" + stateId + "'", null);

        Log.w("QUERY: ", "SELECT * FROM STATE WHERE clave = '" + stateId + "'");

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                state = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return state;
    }
}
