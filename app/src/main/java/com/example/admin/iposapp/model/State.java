package com.example.admin.iposapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by usuario on 11/12/2017.
 */

public class State implements java.io.Serializable{

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @SerializedName("Clave")
    @Expose
    private String clave;

    @SerializedName("Nombre")
    @Expose
    private String nombre;

    @SerializedName("Id")
    @Expose
    private String id;

    @SerializedName("Fecha")
    @Expose
    private String fecha;

    @SerializedName("Hora")
    @Expose
    private String hora;
}
