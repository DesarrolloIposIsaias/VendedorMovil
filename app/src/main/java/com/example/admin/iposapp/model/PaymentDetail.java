package com.example.admin.iposapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sopor on 12/12/2017.
 */

public class PaymentDetail {

    @SerializedName("Pago")
    @Expose
    private String pago;
    @SerializedName("Fecha")
    @Expose
    private String fecha;
    @SerializedName("Venta")
    @Expose
    private String venta;
    @SerializedName("Cargo")
    @Expose
    private String cargo;
    @SerializedName("Abono")
    @Expose
    private String abono;
    @SerializedName("Saldo")
    @Expose
    private String saldo;
    @SerializedName("Intereses")
    @Expose
    private String intereses;
    @SerializedName("Numero")
    @Expose
    private String numero;
    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Anticipo")
    @Expose
    private String anticipo;

    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getVenta() {
        return venta;
    }

    public void setVenta(String venta) {
        this.venta = venta;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getAbono() {
        return abono;
    }

    public void setAbono(String abono) {
        this.abono = abono;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getIntereses() {
        return intereses;
    }

    public void setIntereses(String intereses) {
        this.intereses = intereses;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnticipo() {
        return anticipo;
    }

    public void setAnticipo(String anticipo) {
        this.anticipo = anticipo;
    }
}
