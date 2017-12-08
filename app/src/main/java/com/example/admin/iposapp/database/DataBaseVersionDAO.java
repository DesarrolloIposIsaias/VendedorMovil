package com.example.admin.iposapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.iposapp.model.DataBaseVersion;

/**
 * Created by admin on 23/08/2016.
 */
public class DataBaseVersionDAO extends DbContentProvider implements InterfaceDataBaseVersionSchema
{
    private Cursor cursor;
    private ContentValues initialValues;

    public DataBaseVersionDAO(SQLiteDatabase db)
    {
        super(db);
    }

    protected DataBaseVersion cursorToEntity(Cursor cursor)
    {
        DataBaseVersion dataBaseVersion = new DataBaseVersion();
        int versionIndex;
        int dateIndex;

        if(cursor != null)
        {
            if (cursor.getColumnIndex(columnId) != -1)
            {
                versionIndex = cursor.getColumnIndexOrThrow(columnId);
                dataBaseVersion.setVersion(cursor.getInt(versionIndex));
            }

            if(cursor.getColumnIndex(columnDate) != -1)
            {
                dateIndex = cursor.getColumnIndexOrThrow(columnDate);
                dataBaseVersion.setDate(cursor.getString(dateIndex));
            }
        }

        return null;
    }

    public void upgradeVersion(String date)
    {
        try
        {
            super.rawQuery("INSERT INTO " + tableName + " VALUES(" + columnDate + ")", null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public int getCurrentVersion()
    {
        DataBaseVersion version = new DataBaseVersion();

        cursor = super.query(tableName, dbVersionColumns, null, null, columnId);

        if(cursor != null)
        {
            cursor.moveToLast();
            version = cursorToEntity(cursor);
            cursor.close();
        }

        return version.getVersion();
    }
}
