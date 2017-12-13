package com.example.admin.iposapp.model;
import java.util.Date;

/**
 * Created by Desarrollo IPOS on 06/12/2017.
 */

public class Crep {

    private String cobranza;
    private String vendedor;
    private String venta;
    private String empresa;
    private String cliente;
    private String nombre;
    private String factura;
    private String estatus;
    private String obs;
    private String fechaFactura;
    private String fechaPago;
    private float dias;
    private float total;
    private float aCuenta;
    private float saldo;
    private float intCob;
    private float intereses;
    private float importeNeto;
    private float pago;
    private float efectivo;
    private float diferencia;
    private float impCheque;
    private String banco;
    private float numCheq;
    private float interes;
    private float capital;
    private String olla;
    private String bloqueado;
    private String fecha;
    private String llevar;
    private String id;
    private String idFecha;
    private String idHora;

    public Crep(String id, String clientId){

        this.id = id;
        cliente = clientId;
    }

    public  Crep(){

    }

    public String getCobranza() {
        return cobranza;
    }

    public void setCobranza(String cobranza) {
        this.cobranza = cobranza;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getVenta() {
        return venta;
    }

    public void setVenta(String venta) {
        this.venta = venta;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public float getDias() {
        return dias;
    }

    public void setDias(float dias) {
        this.dias = dias;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getaCuenta() {
        return aCuenta;
    }

    public void setaCuenta(float aCuenta) {
        this.aCuenta = aCuenta;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public float getIntCob() {
        return intCob;
    }

    public void setIntCob(float intCob) {
        this.intCob = intCob;
    }

    public float getIntereses() {
        return intereses;
    }

    public void setIntereses(float intereses) {
        this.intereses = intereses;
    }

    public float getImporteNeto() {
        return importeNeto;
    }

    public void setImporteNeto(float importeNeto) {
        this.importeNeto = importeNeto;
    }

    public float getPago() {
        return pago;
    }

    public void setPago(float pago) {
        this.pago = pago;
    }

    public float getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(float efectivo) {
        this.efectivo = efectivo;
    }

    public float getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(float diferencia) {
        this.diferencia = diferencia;
    }

    public float getImpCheque() {
        return impCheque;
    }

    public void setImpCheque(float impCheque) {
        this.impCheque = impCheque;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public float getNumCheq() {
        return numCheq;
    }

    public void setNumCheq(float numCheq) {
        this.numCheq = numCheq;
    }

    public float getInteres() {
        return interes;
    }

    public void setInteres(float interes) {
        this.interes = interes;
    }

    public float getCapital() {
        return capital;
    }

    public void setCapital(float capital) {
        this.capital = capital;
    }

    public String getOlla() {
        return olla;
    }

    public void setOlla(String olla) {
        this.olla = olla;
    }

    public String getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(String bloqueado) {
        this.bloqueado = bloqueado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLlevar() {
        return llevar;
    }

    public void setLlevar(String llevar) {
        this.llevar = llevar;
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
