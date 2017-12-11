package com.example.admin.iposapp.database;

/**
 * Created by usuario on 11/12/2017.
 */

public interface InterfaceStateSchema {
    String tableName = "STATE";
    String columnId = "id";
    String columnClave = "clave";
    String columnNombre = "nombre";
    String columnFecha = "fecha";
    String columnHora = "hora";

    String createQuery = "CREATE TABLE IF NOT EXISTS "
            + tableName
            + " ("
            + columnId + " TEXT NOT NULL,"
            + columnClave + " TEXT NOT NULL,"
            + columnNombre + " TEXT,"
            + columnFecha + " TEXT,"
            + columnHora + " TEXT,"
            + ")";

    String [] stateColumns = new String[] {columnId, columnClave, columnNombre, columnFecha,
            columnHora};
}
