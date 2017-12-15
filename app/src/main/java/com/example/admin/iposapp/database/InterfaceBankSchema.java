package com.example.admin.iposapp.database;

/**
 * Created by Desarrollo IPOS on 06/12/2017.
 */

public interface InterfaceBankSchema {

    String tableName = "BANCO";
    String columnId = "id";
    String columnClave = "clave";
    String columnName = "nombre";

    String createQuery = "CREATE TABLE IF NOT EXISTS "
            + tableName
            + " ("
            + columnId + " TEXT,"
            + columnClave + " TEXT NOT NULL,"
            + columnName + " TEXT"
            + ")";

    String [] bankColumns = new String[] {columnId, columnClave, columnName};
}
