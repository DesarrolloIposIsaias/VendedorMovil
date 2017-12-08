package com.example.admin.iposapp.database;

/**
 * Created by admin on 24/06/2016.
 */
public interface InterfaceSaleDetailSchema {
    String tableName = "DETALLE_VENTA";
    String columnId = "folio";
    String columnSaleId = "folio_venta";
    String columnClient = "cliente";
    String columnProductId = "id_producto";
    String columnDescription = "descripcion1";
    String columnPrice = "precio";
    String columnDiscount = "descuento";
    String columnAmount = "cantidad";

    String createQuery = "CREATE TABLE IF NOT EXISTS "
            + tableName
            + "("
            + columnId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + columnClient + " TEXT NOT NULL, "
            + columnProductId + " TEXT NOT NULL, "
            + columnPrice + " DECIMAL, "
            + columnDescription + " TEXT, "
            + columnAmount + " DECIMAL, "
            + columnDiscount + " DECIMAL, "
            + columnSaleId + " TEXT"
            + ")";

    String [] saleDetailColumns = new String[] {columnId, columnClient, columnProductId,
            columnDescription, columnPrice, columnAmount, columnDiscount, columnSaleId};
}
