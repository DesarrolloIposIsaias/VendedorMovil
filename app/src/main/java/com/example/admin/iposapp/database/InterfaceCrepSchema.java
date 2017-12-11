package com.example.admin.iposapp.database;

/**
 * Created by Desarrollo IPOS on 06/12/2017.
 */

public interface InterfaceCrepSchema {
    String tableName = "CREP";
    String columnId = "id";
    String columnCobranza = "cobranza";
    String columnVendedor = "vendedor";
    String columnSale = "venta";
    String columnCompany = "empresa";
    String columnClient = "cliente";
    String columnName = "nombre";
    String columnFactura = "factura";
    String columnState = "estatus";
    String columnObs = "obs";
    String columnFechaFactura = "fechaFactura";
    String columnFechaPago =  "fechaPago";
    String columnDias = "dias";
    String columnTotal = "total";
    String columnACuenta = "aCuenta";
    String columnSaldo = "saldo";
    String columnIntCob = "intCob";
    String columnIntereses = "intereses";
    String columnImporteNeto = "importeNeto";
    String columnPago = "pago";
    String columnEfectivo = "efectivo";
    String columnDiferencia = "diferencia";
    String columnImpCheque = "impCheque";
    String columnBank = "banco";
    String columnNumCheq = "numCheq";
    String columnInteres = "interes";
    String columnCapital = "capital";
    String columnOlla = "olla";
    String columnBloqueado = "bloqueado";
    String columnFecha = "fecha";
    String columnLlevar = "llevar";
    String columnIdFecha = "idFecha";
    String columnIdHora =  "idHora";

    String createQuery = "CREATE TABLE IF NOT EXISTS "
            + tableName
            + " ("
            + columnId + " TEXT NOT NULL,"
            + columnCobranza + " TEXT,"
            + columnVendedor + " TEXT,"
            + columnSale + " TEXT,"
            + columnCompany + " TEXT,"
            + columnClient + " TEXT,"
            + columnName + " TEXT,"
            + columnFactura + " TEXT,"
            + columnState + " TEXT,"
            + columnObs + " TEXT,"
            + columnFechaFactura + " TEXT,"
            + columnFechaPago +  " TEXT,"
            + columnDias + " DECIMAL,"
            + columnTotal + " DECIMAL,"
            + columnACuenta + " DECIMAL,"
            + columnSaldo + " DECIMAL,"
            + columnIntCob + " DECIMAL,"
            + columnIntereses + " DECIMAL,"
            + columnImporteNeto + " DECIMAL,"
            + columnPago + " DECIMAL,"
            + columnEfectivo + " DECIMAL,"
            + columnDiferencia + " DECIMAL,"
            + columnImpCheque + " DECIMAL,"
            + columnBank + " TEXT,"
            + columnNumCheq + " DECIMAL,"
            + columnInteres + " DECIMAL,"
            + columnCapital + " DECIMAL,"
            + columnOlla + " TEXT,"
            + columnBloqueado + " TEXT,"
            + columnFecha + " TEXT,"
            + columnLlevar + " TEXT,"
            + columnIdFecha + " TEXT,"
            + columnIdHora +  " TEXT,"
            + ")";

    String [] crepColumns = new String[] {columnId,
            columnCobranza,
            columnVendedor,
            columnSale,
            columnCompany,
            columnClient,
            columnName,
            columnFactura,
            columnState,
            columnObs,
            columnFechaFactura,
            columnFechaPago,
            columnDias,
            columnTotal,
            columnACuenta,
            columnSaldo,
            columnIntCob,
            columnIntereses,
            columnImporteNeto,
            columnPago,
            columnEfectivo,
            columnDiferencia,
            columnImpCheque,
            columnBank,
            columnNumCheq,
            columnInteres,
            columnCapital,
            columnOlla,
            columnBloqueado,
            columnFecha,
            columnLlevar,
            columnIdFecha,
            columnIdHora };
}
