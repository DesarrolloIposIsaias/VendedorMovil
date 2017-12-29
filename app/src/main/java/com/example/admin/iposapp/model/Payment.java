package com.example.admin.iposapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sopor on 12/12/2017.
 */

public class Payment implements java.io.Serializable{

    @SerializedName("Venta")
    @Expose
    private String venta;
    @SerializedName("Fecha")
    @Expose
    private String fecha;
    @SerializedName("Tipo")
    @Expose
    private String tipo;
    @SerializedName("Banco")
    @Expose
    private String banco;
    @SerializedName("IdNum")
    @Expose
    private String idNum;
    @SerializedName("Importe")
    @Expose
    private String importe;
    @SerializedName("Saldo")
    @Expose
    private String saldo;
    @SerializedName("Intereses")
    @Expose
    private String intereses;
    @SerializedName("FolioDeposito")
    @Expose
    private String folioDeposito;
    @SerializedName("Id")
    @Expose
    private String id;

    public String getVenta() {
        return venta;
    }

    public void setVenta(String venta) {
        this.venta = venta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
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

    public String getFolioDeposito() {
        return folioDeposito;
    }

    public void setFolioDeposito(String folioDeposito) {
        this.folioDeposito = folioDeposito;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
