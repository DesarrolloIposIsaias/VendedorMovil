package com.example.admin.iposapp.model;

/**
 * Created by usuario on 08/12/2017.
 */

public class Kit {
    private String id;
    private String producto;
    private String parte;
    private double cantidad;
    private double costo;
    private String idFecha;
    private String idHora;

    public String getParte() {
        return parte;
    }

    public void setParte(String parte) {
        this.parte = parte;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdFecha() {
        return idFecha;
    }

    public void setIdFecha(String idFecha) {
        this.idFecha = idFecha;
    }

    public String getIdHora() {
        return idHora;
    }

    public void setIdHora(String idHora) {
        this.idHora = idHora;
    }
}
