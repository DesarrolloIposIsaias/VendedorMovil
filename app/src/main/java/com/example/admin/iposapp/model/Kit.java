package com.example.admin.iposapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by usuario on 08/12/2017.
 */

public class Kit implements java.io.Serializable{

    @SerializedName("Id")
    @Expose
    private String id;

    @SerializedName("Producto")
    @Expose
    private String producto;

    @SerializedName("Parte")
    @Expose
    private String parte;

    @SerializedName("Cantidad")
    @Expose
    private double cantidad;

    @SerializedName("Costo")
    @Expose
    private double costo;

    @SerializedName("IdFecha")
    @Expose
    private String idFecha;

    @SerializedName("IdHora")
    @Expose
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
