package com.example.admin.iposapp.database;

/**
 * Created by admin on 14/07/2016.
 */
public interface InterfaceSettingsSchema
{
    String tableName = "Configuracion";
    String columnId = "id";
    String columnServer = "servidorFtp";
    String columnUser = "usuarioFtp";
    String columnPassword = "contrasenaFtp";
    String columnSeller = "vendedor";
    String columnSoapSellerId = "vendedorSoap";
    String columnFolder = "carpetaFtp";
    String columnFolderPass = "contrasenaCarpetaFtp";
    String columnAppUser = "usuario";
    String columnAppUserPass = "contrasena";
    String columnLastSale = "folioUltimaVenta";
    String columnBilling = "folioCobranza";
    String columnSellerSerie = "serieVendedor";
    String columnClientSerie = "ultimoCliente";
    String columnSoapServer = "servidorSoap";

    String createQuery = "CREATE TABLE IF NOT EXISTS "
            + tableName
            + "("
            + columnId + " INTEGER, "
            + columnServer + " TEXT, "
            + columnUser + " TEXT, "
            + columnPassword + " TEXT, "
            + columnSeller + " TEXT, "
            + columnSoapSellerId + " TEXT, "
            + columnFolder + " TEXT, "
            + columnFolderPass + " TEXT, "
            + columnAppUser + " TEXT, "
            + columnAppUserPass + " TEXT, "
            + columnLastSale + " TEXT, "
            + columnBilling + " TEXT, "
            + columnSellerSerie + " TEXT, "
            + columnClientSerie + " TEXT, "
            + columnSoapServer + " TEXT"
            + ")";

    String [] settingsColumns = new String[] {columnId, columnServer, columnUser, columnPassword,
            columnSeller, columnSoapSellerId, columnFolder, columnFolderPass, columnAppUser,
            columnAppUserPass, columnLastSale, columnBilling, columnSellerSerie, columnClientSerie,
            columnSoapServer};



}
