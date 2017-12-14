package com.example.admin.iposapp.database;

/**
 * Created by usuario on 08/12/2017.
 */

public interface InterfaceKitSchema {
    String tableName = "KIT";
    String columnProducto = "producto";
    String columnParte = "parte";
    String columnCantidad = "cantidad";
    String columnCosto = "costo";
    String columnId = "id";
    String columnIdFecha = "idFecha";
    String columndIdHora = "idHora";

    String createQuery = "CREATE TABLE IF NOT EXISTS "
            + tableName
            + " ("
            + columnId + " TEXT,"
            + columnProducto + " TEXT NOT NULL,"
            + columnParte + " TEXT,"
            + columnCantidad + " TEXT,"
            + columnCosto + " TEXT,"
            + columnIdFecha + " TEXT,"
            + columndIdHora + " TEXT"
            + ")";

    String [] kitColumns = new String[] {columnId, columnProducto, columnParte, columnCantidad,
            columnCosto, columnIdFecha, columndIdHora};
}
