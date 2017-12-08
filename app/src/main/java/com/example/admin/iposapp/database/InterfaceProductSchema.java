package com.example.admin.iposapp.database;

/**
 * Created by admin on 21/06/2016.
 */
public interface InterfaceProductSchema {
    String tableName = "PRODUCTO";
    String columnId = "clave";
    String columnDescripcion1 = "descripcion1";
    String columnDescripcion2 = "descripcion2";
    String columnDescripcion3 = "descripcion3";
    String columnPrecio1 = "precio1";
    String columnCBarras = "cbarras";
    String columnCEmpaque = "cempaque";
    String columnEan = "ean";
    String columnUnidad = "unidad";
    String columnPzaCaja= "pzacaja";
    String columnU_empaque = "u_empaque";
    String columnExistencia = "existencia";

    String createQuery = "CREATE TABLE IF NOT EXISTS "
            + tableName
            + " ("
            + columnId + " TEXT NOT NULL,"
            + columnDescripcion1 + " TEXT,"
            + columnDescripcion2 + " TEXT,"
            + columnDescripcion3 + " TEXT,"
            + columnPrecio1 + " DECIMAL,"
            + columnCBarras + " TEXT,"
            + columnCEmpaque + " TEXT,"
            + columnEan + " TEXT,"
            + columnUnidad + " TEXT,"
            + columnPzaCaja + " DECIMAL,"
            + columnU_empaque + " DECIMAL,"
            + columnExistencia + " DECIMAL"
            + ")";

    String [] productColumns = new String[] {columnId, columnDescripcion1, columnDescripcion2, columnDescripcion3,
            columnPrecio1, columnCBarras, columnCEmpaque, columnEan, columnUnidad,
            columnPzaCaja, columnU_empaque, columnExistencia};

}
