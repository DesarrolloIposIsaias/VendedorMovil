package com.example.admin.iposapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 24/06/2016.
 */
public class Sale {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Client")
    @Expose
    private String client;
    @SerializedName("Total")
    @Expose
    private float total;
    @SerializedName("Description1")
    @Expose
    private String description1;
    @SerializedName("Description2")
    @Expose
    private String description2;
    @SerializedName("PaymentMethod")
    @Expose
    private String paymentMethod;
    @SerializedName("Status1")
    @Expose
    private String status1;
    @SerializedName("Status2")
    @Expose
    private String status2;
    @SerializedName("Send")
    @Expose
    private String send;
    @SerializedName("Details")
    @Expose
    private List<SaleDetail> details;

    public Sale()
    {
        setSend("N");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public String getStatus2() {
        return status2;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    public String getSend()
    {
        return send;
    }

    public void setSend(String send)
    {
        this.send = send;
    }

    public List<SaleDetail> getDetails() {
        return details;
    }

    public void setDetails(List<SaleDetail> details) {
        this.details = details;
    }
}
