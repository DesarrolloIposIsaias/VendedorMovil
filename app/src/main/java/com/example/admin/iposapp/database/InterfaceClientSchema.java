package com.example.admin.iposapp.database;

/**
 * Created by admin on 21/06/2016.
 */
public interface InterfaceClientSchema {
    String tableName = "PERSONA";
    String columnId = "clave";
    String columnName = "nombre";
    String columnNames = "nombres";
    String columnLastName = "apellidos";
    String columnAddress = "domicilio";
    String columnPhone1 = "telefono1";
    String columnPhone2 = "telefono2";
    String columnRfc = "rfc";
    String columnBalance = "saldo";
    String columnCity = "ciudad";
    String columnSellerId = "vendedor_id";
    String columnPostalCode = "codigo_postal";
    String columnPriceListId = "lista_precioid";
    String columnCreditLimit = "limite_credito";
    String columnDays = "dias";
    String columnStreets = "calles";
    String columnContact1 = "contacto1";
    String columnContact2 = "contacto2";
    String columnEmail1 = "correo1";
    String columnEmail2 = "correo2";
    String columnSerie = "serie";
    String columnCountry = "pais";
    String columnState = "estado";
    String columnNeighborhood = "colonia";
    String columnIntNumber = "numero_interior";
    String columnExtNumber = "numero_exterior";
    String columnIeps = "cuenta_ieps";
    String columnAddresService = "servicio_domicilio";
    String columnDeadline = "plazo";
    String columnPrice = "precio";
    String columnPayday = "dia_pago";
    String columnReview = "revision";
    String columnCard = "tarjeta";
    String columnCredit = "credito";
    String columnCheck = "cheque";
    String columnTransfer = "transferencia";
    String columnBlocked = "bloqueado";

    String createQuery = "CREATE TABLE IF NOT EXISTS "
            + tableName
            + " ("
            + columnId + " TEXT NOT NULL,"
            + columnName + " TEXT,"
            + columnNames + " TEXT,"
            + columnLastName + " TEXT,"
            + columnAddress + " TEXT,"
            + columnPhone1 + " TEXT,"
            + columnPhone2 + " TEXT,"
            + columnRfc + " TEXT,"
            + columnBalance + " DECIMAL,"
            + columnCity + " TEXT,"
            + columnSellerId + "  INTEGER,"
            + columnPostalCode + " INTEGER,"
            + columnPriceListId + " INTEGER,"
            + columnCreditLimit + " DECIMAL,"
            + columnDays + " INTEGER, "
            + columnStreets + " TEXT, "
            + columnContact1 + " TEXT, "
            + columnContact2 + " TEXT, "
            + columnEmail1 + " TEXT, "
            + columnEmail2 + " TEXT, "
            + columnSerie + " TEXT, "
            + columnCountry + " TEXT, "
            + columnState + " TEXT, "
            + columnNeighborhood + " TEXT, "
            + columnIntNumber + " TEXT, "
            + columnExtNumber + " TEXT, "
            + columnIeps  + " TEXT, "
            + columnAddresService + " TEXT, "
            + columnDeadline + " TEXT, "
            + columnPrice + " TEXT, "
            + columnPayday + " TEXT, "
            + columnReview + " TEXT, "
            + columnCard + " TEXT, "
            + columnCredit + " TEXT, "
            + columnCheck + " TEXT, "
            + columnTransfer + " TEXT, "
            + columnBlocked + " TEXT"
            + ")";

    String [] clientColumns = new String[] {columnId, columnName, columnNames, columnLastName,
                                            columnAddress, columnPhone1, columnPhone2, columnRfc,
                                            columnBalance, columnCity, columnSellerId,
                                            columnPostalCode, columnPriceListId, columnCreditLimit,
                                            columnDays, columnStreets, columnContact1,
                                            columnContact2, columnEmail1, columnEmail2, columnSerie,
                                            columnCountry, columnState, columnNeighborhood,
                                            columnIntNumber, columnExtNumber, columnIeps,
                                            columnAddresService, columnDeadline, columnPrice,
                                            columnPayday, columnReview, columnCard, columnCredit,
                                            columnCheck, columnTransfer, columnBlocked};

}
