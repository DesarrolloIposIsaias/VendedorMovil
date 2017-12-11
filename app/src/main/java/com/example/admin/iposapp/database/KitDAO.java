package com.example.admin.iposapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.admin.iposapp.utility.CurrentData;
import com.example.admin.iposapp.model.Kit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by usuario on 08/12/2017.
 */

public class KitDAO extends DbContentProvider implements InterfaceKitSchema, InterfaceKitDAO{

    private Cursor cursor;
    private ContentValues initialValues;

    public KitDAO(SQLiteDatabase db)
    {

        super(db);
    }

    public Kit fetchKitById(int id)
    {
        final String selectionArgs[] = {String.valueOf(id)};
        final String selection = id + "?";
        Kit kit = new Kit();
        cursor = super.query(tableName, kitColumns, selection, selectionArgs, columnId);

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                kit = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return kit;
    }

    public List<Kit> fetchAllKits()
    {
        List<Kit> kitList = new ArrayList<Kit>();
        cursor = super.query(tableName, kitColumns, null, null, columnId);

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                Kit kit = cursorToEntity(cursor);
                kitList.add(kit);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return kitList;
    }

    public boolean addKit(Kit kit)
    {
        setContentValue(kit);
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

    public boolean addKits(List<Kit> kits)
    {
        for (int i = 0; i < kits.size(); i++)
        {
            setContentValue(kits.get(i));
            try
            {
                if(super.insert(tableName, getContentValue()) > 0)
                {
                    Log.w("Kit: ", "Added");
                    Log.w("Kit: ", kits.get(i).getProducto());
                }
                else Log.w("Kit: ", "Problem adding kit");
            }
            catch (SQLiteConstraintException ex)
            {
                Log.w("Database", ex.getMessage());
                return false;
            }
        }
        return true;
    }

    public boolean deleteAllKits() {
        return false;
    }

    protected Kit cursorToEntity(Cursor cursor)
    {
        Kit kit = new Kit();

        int id;
        int producto;
        int parte;
        int cantidad;
        int costo;
        int idFecha;
        int idHora;

        if(cursor != null)
        {
            if(cursor.getColumnIndex(columnId) != -1)
            {
                id = cursor.getColumnIndexOrThrow(columnId);
                kit.setId(cursor.getString(id));
            }
            if(cursor.getColumnIndex(columnProducto) != -1)
            {
                producto = cursor.getColumnIndexOrThrow(columnProducto);
                kit.setProducto(cursor.getString(producto));
            }
            if(cursor.getColumnIndex(columnParte) != -1)
            {
                parte = cursor.getColumnIndexOrThrow(columnParte);
                kit.setParte(cursor.getString(parte));
            }

            if(cursor.getColumnIndex(columnCantidad) != -1)
            {
                cantidad = cursor.getColumnIndexOrThrow(columnCantidad);
                kit.setCantidad(cursor.getDouble(cantidad));
            }
            if(cursor.getColumnIndex(columnCosto) != -1)
            {
                costo = cursor.getColumnIndexOrThrow(columnCosto);
                kit.setCosto(cursor.getDouble(costo));
            }
            if(cursor.getColumnIndex(columnIdFecha) != -1)
            {
                idFecha = cursor.getColumnIndexOrThrow(columnIdFecha);
                kit.setIdFecha(cursor.getString(idFecha));
            }
            if(cursor.getColumnIndex(columndIdHora) != -1)
            {
                idHora = cursor.getColumnIndexOrThrow(columndIdHora);
                kit.setIdHora(cursor.getString(idHora));
            }

        }
        return kit;
    }

    private void setContentValue(Kit kit)
    {
        initialValues = new ContentValues();
        initialValues.put(columnId, kit.getId());
        initialValues.put(columnProducto, kit.getProducto());
        initialValues.put(columnParte, kit.getParte());
        initialValues.put(columnCantidad, kit.getCantidad());
        initialValues.put(columnCosto, kit.getCosto());
        initialValues.put(columnIdFecha, kit.getIdFecha());
        initialValues.put(columndIdHora, kit.getIdHora());
    }

    private ContentValues getContentValue()
    {
        return initialValues;
    }

    public List<Kit> searchByFirstLetter(char search)
    {
        List<Kit> kitList = new ArrayList<Kit>();
        cursor = database.rawQuery("SELECT producto FROM KIT WHERE producto LIKE '" +
                search +"%'", null);

        Log.w("FETCH KIT", "SELECT producto FROM IT WHERE producto LIKE '" +
                search +"%'");

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                Kit kit = cursorToEntity(cursor);
                kitList.add(kit);
                cursor.moveToNext();
            }
            cursor.close();
        }
        else
        {
            Log.w("KIT", "NOT FOUND");
        }

        return kitList;
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
    public int getNumberOfKits()
    {
        cursor = super.rawQuery(
                "SELECT " + columnId +
                        " FROM " + tableName, null);

        return cursor.getCount();
    }

    @Override
    public boolean updateKit(Kit kit)
    {
        cursor = super.rawQuery(
                "UPDATE " + tableName +
                        " SET " + columnProducto + " = '" + kit.getProducto() + "' ,"
                        + columnParte + " = '" + kit.getParte() + "' ,"
                        + columnCantidad + " = '" + kit.getCantidad() + "' ,"
                        + columnCosto + " = '" + kit.getCosto() + "' ,"
                        + columnIdFecha + " = '" + kit.getIdFecha() + "' ,"
                        + columndIdHora + " = '" + kit.getIdHora() + "'" +
                        " WHERE " + columnId + " = '" + kit.getId() + "'", null
        );

        return cursor.getCount() > 0;
    }

    public void updateKits(Context ctx)
    {
        database.execSQL("delete from KIT where id != -1");

        File file = new File(CurrentData.getInternalStoragePath() + "/kits.sql");

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

    public Kit fetchKitById(String kitId) {
        Kit kit = new Kit();
        //cursor = super.query(tableName, productColumns, selection, selectionArgs, columnId);
        cursor = super.rawQuery("SELECT * FROM KIT WHERE id = '" + kitId+ "'", null);

        Log.w("QUERY: ", "SELECT * FROM KIt WHERE clave = '" + kitId + "'");

        if(cursor != null)
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                kit = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return kit;
    }
}
