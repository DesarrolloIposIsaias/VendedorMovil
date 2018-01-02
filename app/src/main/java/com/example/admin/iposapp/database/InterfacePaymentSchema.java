package com.example.admin.iposapp.database;

/**
 * Created by sopor on 12/12/2017.
 */

public interface InterfacePaymentSchema {
    String tableName = "PAGO";
    String columnId = "id";
    String columnPayment = "pago";
    String columnDate = "fecha";
    String columnType = "tipo";
    String columnBank = "banco";
    String columnIdNum = "id_num";
    String columnImporte = "importe";
    String columnSaldo = "saldo";
    String columnIntereses = "intereses";
    String columnVoucher = "folio_deposito";

    String createQuery = "CREATE TABLE IF NOT EXISTS "
            + tableName
            + "("
            + columnId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + columnPayment + " TEXT NOT NULL, "
            + columnDate + " TEXT, "
            + columnType + " TEXT, "
            + columnBank + " TEXT, "
            + columnIdNum + " TEXT, "
            + columnImporte + " TEXT, "
            + columnSaldo + " TEXT, "
            + columnIntereses + " TEXT, "
            + columnVoucher + " TEXT) ";

    String[] paymentColumns = new String[]{
            columnId,
            columnPayment,
            columnDate,
            columnType,
            columnBank,
            columnIdNum,
            columnImporte,
            columnSaldo,
            columnIntereses,
            columnVoucher
    };
}
