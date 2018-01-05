package com.example.admin.iposapp.database;

/**
 * Created by Desarrollo IPOS on 03/01/2018.
 */

public interface InterfaceClientBalanceSchema {
    String tableName = "CLIENTBALANCE";
    String columnId = "id";
    String columnClient = "client";
    String columnBalance = "balance";

    String createQuery = "CREATE TABLE IF NOT EXISTS "
            + tableName
            + " ("
            + columnId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + columnClient + " TEXT NOT NULL, "
            + columnBalance + " TEXT NOT NULL "
            + ")";

    String [] clientBalanceColumns = new String[] {columnId, columnClient, columnBalance};
}
