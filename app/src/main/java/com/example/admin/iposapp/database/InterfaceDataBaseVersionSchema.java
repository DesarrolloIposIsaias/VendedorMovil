package com.example.admin.iposapp.database;

/**
 * Created by admin on 23/08/2016.
 */
public interface InterfaceDataBaseVersionSchema
{
    final String tableName = "Version";
    final String columnId = "numero_version";
    final String columnDate = "fecha";

    final String createQuery = "CREATE TABLE IF NOT EXISTS "
            + tableName
            + "("
            + columnId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + columnDate + " TEXT)";

    String [] dbVersionColumns = new String[] {columnId, columnDate};

}
