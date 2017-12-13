package com.example.admin.iposapp.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Desarrollo IPOS on 06/12/2017.
 */

public class Bank implements java.io.Serializable {

    @SerializedName("Clave")
    @Expose
    private String clave;

    @SerializedName("Id")
    @Expose
    private String id;

    @SerializedName("Nombre")
    @Expose
    private String nombre;

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
}
