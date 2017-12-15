package com.example.admin.iposapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 20/06/2016.
 */
public class Product implements java.io.Serializable{

    @SerializedName("Clave")
    @Expose
    private String clave;

    @SerializedName("Descripcion1")
    @Expose
    private String descripcion1;

    @SerializedName("Descripcion2")
    @Expose
    private String descripcion2;

    @SerializedName("Descripcion3")
    @Expose
    private String descripcion3;

    @SerializedName("Precio1")
    @Expose
    private float precio1;

    @SerializedName("CBarras")
    @Expose
    private String cBarras;

    @SerializedName("CEmpaque")
    @Expose
    private String cEmpaque;

    @SerializedName("Ean")
    @Expose
    private String ean;

    @SerializedName("Unidad")
    @Expose
    private String unidad;

    @SerializedName("PzaCaja")
    @Expose
    private float pzaCaja;

    @SerializedName("UEmpaque")
    @Expose
    private float uEmpaque;

    @SerializedName("Existencia")
    @Expose
    private float existencia;

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDescripcion1() {
        return descripcion1;
    }

    public void setDescripcion1(String descripcion1) {
        this.descripcion1 = descripcion1;
    }

    public String getDescripcion2() {
        return descripcion2;
    }

    public void setDescripcion2(String descripcion2) {
        this.descripcion2 = descripcion2;
    }

    public String getDescripcion3() {
        return descripcion3;
    }

    public void setDescripcion3(String descripcion3) {
        this.descripcion3 = descripcion3;
    }

    public float getPrecio1() {
        return precio1;
    }

    public void setPrecio1(float precio1) {
        this.precio1 = precio1;
    }

    public String getcBarras() {
        return cBarras;
    }

    public void setcBarras(String cBarras) {
        this.cBarras = cBarras;
    }

    public String getcEmpaque() {
        return cEmpaque;
    }

    public void setcEmpaque(String cEmpaque) {
        this.cEmpaque = cEmpaque;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public float getPzaCaja() {
        return pzaCaja;
    }

    public void setPzaCaja(float pzaCaja) {
        this.pzaCaja = pzaCaja;
    }

    public float getuEmpaque() {
        return uEmpaque;
    }

    public void setuEmpaque(float uEmpaque) {
        this.uEmpaque = uEmpaque;
    }

    public float getExistencia() {
        return existencia;
    }

    public void setExistencia(float existencia) {
        this.existencia = existencia;
    }
}
