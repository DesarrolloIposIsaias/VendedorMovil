package com.example.admin.iposapp.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class Client implements java.io.Serializable{

    @SerializedName("Clave")
    @Expose
    private String clave;

    @SerializedName("Nombre")
    @Expose
    private String nombre;

    @SerializedName("Nombres")
    @Expose
    private String nombres;

    @SerializedName("Apellidos")
    @Expose
    private String apellidos;

    @SerializedName("Domicilio")
    @Expose
    private String domicilio;

    @SerializedName("Telefono1")
    @Expose
    private String telefono1;

    @SerializedName("Telefono2")
    @Expose
    private String telefono2;

    @SerializedName("Rfc")
    @Expose
    private String rfc;

    @SerializedName("Saldo")
    @Expose
    private float saldo;

    @SerializedName("Ciudad")
    @Expose
    private String ciudad;

    @SerializedName("VendedorId")
    @Expose
    private String vendedorId;

    @SerializedName("CodigoPostal")
    @Expose
    private String codigoPostal;

    @SerializedName("ListaPrecioId")
    @Expose
    private String listaPrecioId;

    @SerializedName("LimiteCredito")
    @Expose
    private float limiteCredito;

    @SerializedName("Dias")
    @Expose
    private int dias;

    @SerializedName("Calles")
    @Expose
    private String calles;

    @SerializedName("Contacto1")
    @Expose
    private String contacto1;

    @SerializedName("Contacto2")
    @Expose
    private String contacto2;

    @SerializedName("Email1")
    @Expose
    private String email1;

    @SerializedName("Email2")
    @Expose
    private String email2;

    @SerializedName("Serie")
    @Expose
    private String serie;

    @SerializedName("Pais")
    @Expose
    private String pais;

    @SerializedName("Estado")
    @Expose
    private String estado;

    @SerializedName("Colonia")
    @Expose
    private String colonia;

    @SerializedName("NumeroInterior")
    @Expose
    private String numeroInterior;

    @SerializedName("NumeroExterior")
    @Expose
    private String numeroExterior;

    @SerializedName("CuentaIeps")
    @Expose
    private String cuentaIeps;

    @SerializedName("ServicioDomicilio")
    @Expose
    private String servicioDomicilio;

    @SerializedName("Plazo")
    @Expose
    private String plazo;

    @SerializedName("Precio")
    @Expose
    private String precio;

    @SerializedName("DiaPago")
    @Expose
    private String diaPago;

    @SerializedName("Revision")
    @Expose
    private String revision;

    @SerializedName("Tarjeta")
    @Expose
    private String tarjeta;

    @SerializedName("Credito")
    @Expose
    private String credito;

    @SerializedName("Cheque")
    @Expose
    private String cheque;

    @SerializedName("Transferencia")
    @Expose
    private String transferencia;

    @SerializedName("Bloqueado")
    @Expose
    private String bloqueado;

    public String getCuentaIeps()
    {
        return cuentaIeps;
    }

    public void setCuentaIeps(String cuentaIeps)
    {
        this.cuentaIeps = cuentaIeps;
    }

    public String getServicioDomicilio()
    {
        return servicioDomicilio;
    }

    public void setServicioDomicilio(String servicioDomicilio)
    {
        this.servicioDomicilio = servicioDomicilio;
    }

    public String getPlazo()
    {
        return plazo;
    }

    public void setPlazo(String plazo)
    {
        this.plazo = plazo;
    }

    public String getPrecio()
    {
        return precio;
    }

    public void setPrecio(String precio)
    {
        this.precio = precio;
    }

    public String getDiaPago()
    {
        return diaPago;
    }

    public void setDiaPago(String diaPago)
    {
        this.diaPago = diaPago;
    }

    public String getRevision()
    {
        return revision;
    }

    public void setRevision(String revision)
    {
        this.revision = revision;
    }

    public String getTarjeta()
    {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta)
    {
        this.tarjeta = tarjeta;
    }

    public String getCredito()
    {
        return credito;
    }

    public void setCredito(String credito)
    {
        this.credito = credito;
    }

    public String getCheque()
    {
        return cheque;
    }

    public void setCheque(String cheque)
    {
        this.cheque = cheque;
    }

    public String getTransferencia()
    {
        return transferencia;
    }

    public void setTransferencia(String transferencia)
    {
        this.transferencia = transferencia;
    }

    public String getBloqueado()
    {
        return bloqueado;
    }

    public void setBloqueado(String bloqueado)
    {
        this.bloqueado = bloqueado;
    }

    public String getTelefono2()
    {
        return telefono2;
    }

    public void setTelefono2(String telefono2)
    {
        this.telefono2 = telefono2;
    }

    public String getCalles()
    {
        return calles;
    }

    public void setCalles(String calles)
    {
        this.calles = calles;
    }

    public String getContacto1()
    {
        return contacto1;
    }

    public void setContacto1(String contacto1)
    {
        this.contacto1 = contacto1;
    }

    public String getContacto2()
    {
        return contacto2;
    }

    public void setContacto2(String contacto2)
    {
        this.contacto2 = contacto2;
    }

    public String getEmail1()
    {
        return email1;
    }

    public void setEmail1(String email1)
    {
        this.email1 = email1;
    }

    public String getEmail2()
    {
        return email2;
    }

    public void setEmail2(String email2)
    {
        this.email2 = email2;
    }

    public String getSerie()
    {
        return serie;
    }

    public void setSerie(String serie)
    {
        this.serie = serie;
    }

    public String getPais()
    {
        return pais;
    }

    public void setPais(String pais)
    {
        this.pais = pais;
    }

    public String getEstado()
    {
        return estado;
    }

    public void setEstado(String estado)
    {
        this.estado = estado;
    }

    public String getColonia()
    {
        return colonia;
    }

    public void setColonia(String colonia)
    {
        this.colonia = colonia;
    }

    public String getNumeroInterior()
    {
        return numeroInterior;
    }

    public void setNumeroInterior(String numeroInterior)
    {
        this.numeroInterior = numeroInterior;
    }

    public String getNumeroExterior()
    {
        return numeroExterior;
    }

    public void setNumeroExterior(String numeroExterior)
    {
        this.numeroExterior = numeroExterior;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(String vendedorId) {
        this.vendedorId = vendedorId;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getListaPrecioId() {
        return listaPrecioId;
    }

    public void setListaPrecioId(String listaPrecioId) {
        this.listaPrecioId = listaPrecioId;
    }

    public float getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(float limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }
}
