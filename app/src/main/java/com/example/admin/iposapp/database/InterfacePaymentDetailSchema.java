package com.example.admin.iposapp.database;

/**
 * Created by sopor on 12/12/2017.
 */

public interface InterfacePaymentDetailSchema {
    String tableName = "DETALLE_PAGO";
    String columnId = "id";
    String columnPayment = "pago";
    String columnDate = "fecha";
    String columnSale = "venta";
    String columnCharge = "cargo";
    String columnPartialPayment = "abono";
    String columnBalance = "saldo";
    String columnInterest = "intereses";
    String columnNumber = "numero";

    String createQuery = "CREATE TABLE IF NOT EXISTS "
            + tableName
            + "("
            + columnId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + columnPayment + " TEXT,"
            + columnDate + " TEXT,"
            + columnSale + " TEXT,"
            + columnCharge + " TEXT,"
            + columnPartialPayment + " TEXT,"
            + columnBalance + " TEXT,"
            + columnInterest + " TEXT,"
            + columnNumber + " TEXT"
            + ")";

    String[] paymentDetailColumns = new String[]{
            columnId,
            columnPayment,
            columnDate,
            columnSale,
            columnCharge,
            columnPartialPayment,
            columnBalance,
            columnInterest,
            columnNumber
    };
}
