package com.example.admin.iposapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 24/06/2016.
 */
public class SaleDetail {

    @SerializedName("Id")
    @Expose
    String id;
    @SerializedName("Client")
    @Expose
    String client;
    @SerializedName("ProductId")
    @Expose
    String productId;
    @SerializedName("Description")
    @Expose
    String description;
    @SerializedName("Price")
    @Expose
    float price;
    @SerializedName("Discount")
    @Expose
    float discount;
    @SerializedName("Amount")
    @Expose
    float amount;
    @SerializedName("SaleId")
    @Expose
    String saleId;

    public String getSaleId()
    {
        return saleId;
    }

    public void setSaleId(String saleId)
    {
        this.saleId = saleId;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
