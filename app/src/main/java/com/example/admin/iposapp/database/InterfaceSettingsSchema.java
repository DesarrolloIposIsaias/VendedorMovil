package com.example.admin.iposapp.database;

/**
 * Created by admin on 14/07/2016.
 */
public interface InterfaceSettingsSchema
{
    String tableName = "Configuracion";
    String columnId = "id";
    String columnServer = "servidor_webservice";
    String columnBranch = "sucursal";
    String columnCompany = "empresa";
    String columnAppUser = "usuario";
    String columnAppUserPass = "contrasena";
    String columnLastSale = "folioUltimaVenta";
    String columnBilling = "folioCobranza";
    String columnSellerSerie = "serieVendedor";
    String columnClientSerie = "ultimoCliente";

    String createQuery = "CREATE TABLE IF NOT EXISTS "
            + tableName
            + "("
            + columnId + " INTEGER, "
            + columnServer + " TEXT, "
            + columnBranch + " TEXT, "
            + columnCompany + " TEXT, "
            + columnAppUser + " TEXT, "
            + columnAppUserPass + " TEXT, "
            + columnLastSale + " TEXT, "
            + columnBilling + " TEXT, "
            + columnSellerSerie + " TEXT, "
            + columnClientSerie + " TEXT "
            + ")";

    String [] settingsColumns = new String[] {
            columnId,
            columnServer,
            columnBranch,
            columnCompany,
            columnAppUser,
            columnAppUserPass,
            columnLastSale,
            columnBilling,
            columnSellerSerie,
            columnClientSerie
    };



}
