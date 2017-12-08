package com.example.admin.iposapp.database;

/**
 * Created by admin on 24/06/2016.
 */
public interface InterfaceSaleSchema {

    String tableName = "VENTA";
    String columnId = "folio";
    String columnCliente = "cliente";
    String columnTotal = "total";
    String columnDescripcion1 = "descripcion1";
    String columnDescripcion2 = "descripcion2";
    String columnMetodoDePago = "metodo_pago";
    String columnEstatus1 = "estatus1";
    String columnEstatus2 = "estatus2";
    String columnSend = "estado";

    String createQuery = "CREATE TABLE IF NOT EXISTS "
            + tableName
            + "("
            + columnId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + columnCliente + " TEXT NOT NULL, "
            + columnTotal + " DECIMAL, "
            + columnDescripcion1 + " TEXT, "
            + columnDescripcion2 + " TEXT, "
            + columnMetodoDePago + " TEXT, "
            + columnEstatus1 + " TEXT, "
            + columnEstatus2 + " TEXT, "
            + columnSend + " TEXT"
            + ")";

    String [] saleColumns = new String[] {columnId, columnCliente, columnTotal, columnDescripcion1,
    columnDescripcion2, columnMetodoDePago, columnEstatus1, columnEstatus2, columnSend};
}
