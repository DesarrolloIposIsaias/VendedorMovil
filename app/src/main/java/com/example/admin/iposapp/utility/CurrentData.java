package com.example.admin.iposapp.utility;

import android.os.Bundle;

import com.example.admin.iposapp.model.Client;
import com.example.admin.iposapp.model.Crep;
import com.example.admin.iposapp.model.MultiplePaymentHeader;
import com.example.admin.iposapp.model.Product;
import com.example.admin.iposapp.model.SaleDetail;
import com.example.admin.iposapp.model.Settings;

import java.util.List;

/**
 * Created by admin on 29/06/2016.
 */
public class CurrentData
{
    private static String clientId;
    private static boolean clientModificationEnabled;
    private static String clientName;
    private static String productId;
    private static String saleId;
    private static String saleDetailId;
    private static String internalStoragePath;
    private static String user;
    private static String sended;
    private static float price;
    private static float total;
    private static float subtotal;
    private static boolean mutable;
    private static boolean callFromAllSales;
    private static boolean isSync;
    private static Settings settings;
    private static List<Product> productList;
    private static List<SaleDetail> detailProductList;
    private static boolean onlyForView;
    private static Client client;
    private static int lastClientInserted;
    private static String company;
    private static String branch;
    private static String remoteDbName;
    private static Crep selectedCrep;
    private static MultiplePaymentHeader actualMultiplePaymentHeader;
    private static Bundle itemMultipleCrep;
    private static String selectedPayment;

    public static Crep getSelectedCrep() {
        return selectedCrep;
    }

    public static void setSelectedCrep(Crep selectedCrep) {
        CurrentData.selectedCrep = selectedCrep;
    }

    public static int getLastClientInserted()
    {
        return lastClientInserted;
    }

    public static void setLastClientInserted(int lastClientInserted)
    {
        CurrentData.lastClientInserted = lastClientInserted;
    }

    public static void incrementLastClient()
    {
        CurrentData.lastClientInserted++;
    }

    public static void decrementLastClient()
    {
        CurrentData.lastClientInserted--;
    }

    public CurrentData()
    {
        total = 0;
        lastClientInserted = 0;
        mutable = false;
        sended = "N";
        callFromAllSales = false;
        onlyForView = false;
        isSync = false;
        clientModificationEnabled = false;
        settings = new Settings();
    }

    public static boolean isClientModificationEnabled(){ return clientModificationEnabled;}

    public static void setClientModificationEnabled(boolean value){clientModificationEnabled = value;}

    public static Client getClient()
    {
        return client;
    }

    public static void setClient(Client client)
    {
        CurrentData.client = client;
    }

    public static boolean isSync()
    {
        return isSync;
    }

    public static void setIsSync(boolean isSync)
    {
        CurrentData.isSync = isSync;
    }

    public static boolean isOnlyForView()
    {
        return onlyForView;
    }

    public static void setOnlyForView(boolean onlyForView)
    {
        CurrentData.onlyForView = onlyForView;
    }

    public static String getSended()
    {
        return sended;
    }

    public static void setSended(String sended)
    {
        CurrentData.sended = sended;
    }

    public static boolean isCallFromAllSales()
    {
        return callFromAllSales;
    }

    public static void setCallFromAllSales(boolean callFromAllSales)
    {
        CurrentData.callFromAllSales = callFromAllSales;
    }

    public static List<SaleDetail> getDetailProductList()
    {
        return detailProductList;
    }

    public static void setDetailProductList(List<SaleDetail> detailProductList)
    {
        CurrentData.detailProductList = detailProductList;
    }

    public static String getInternalStoragePath()
    {
        return internalStoragePath;
    }

    public static void setInternalStoragePath(String internalStoragePath)
    {
        CurrentData.internalStoragePath = internalStoragePath;
    }

    public static boolean isMutable()
    {
        return mutable;
    }

    public static void setMutable(boolean mutable)
    {
        CurrentData.mutable = mutable;
    }

    public static float getSubtotal()
    {
        return subtotal;
    }

    public static void setSubtotal(float subtotal)
    {
        CurrentData.subtotal = subtotal;
    }

    public static float getTotal()
    {
        return total;
    }

    public static void setTotal(float total)
    {
        CurrentData.total = total;
    }

    public static float getPrice()
    {
        return price;
    }

    public static void setPrice(float price)
    {
        CurrentData.price = price;
    }

    public static String getClientName()
    {
        return clientName;
    }

    public static void setClientName(String clientName)
    {
        CurrentData.clientName = clientName;
    }

    public static String getClientId()
    {
        return clientId;
    }

    public static void setClientId(String clientId)
    {
        CurrentData.clientId = clientId;
    }

    public static String getProductId()
    {
        return productId;
    }

    public static void setProductId(String productId)
    {
        CurrentData.productId = productId;
    }

    public static String getSaleId()
    {
        return saleId;
    }

    public static void setSaleId(String saleId)
    {
        CurrentData.saleId = saleId;
    }

    public static String getSaleDetailId()
    {
        return saleDetailId;
    }

    public static void setSaleDetailId(String saleDetailId)
    {
        CurrentData.saleDetailId = saleDetailId;
    }

    public static Settings getSettings()
    {
        return settings;
    }

    public static void setSettings(Settings settings)
    {
        CurrentData.settings = settings;
    }

    public static String getUser()
    {
        return user;
    }

    public static void setUser(String user)
    {
        CurrentData.user = user;
    }

    public static List<Product> getProductList()
    {
        return productList;
    }

    public static void setProductList(List<Product> productList)
    {
        CurrentData.productList = productList;
    }

    public static String getCompany() {
        return company;
    }

    public static void setCompany(String company) {
        CurrentData.company = company;
    }

    public static String getBranch() {
        return branch;
    }

    public static void setBranch(String branch) {
        CurrentData.branch = branch;
    }

    public static String getRemoteDbName() {
        return remoteDbName;
    }

    public static void setRemoteDbName(String remoteDbName) {
        CurrentData.remoteDbName = remoteDbName;
    }

    public static Bundle getItemMultipleCrep() {
        return itemMultipleCrep;
    }

    public static void setItemMultipleCrep(Bundle itemMultipleCrep) {
        CurrentData.itemMultipleCrep = itemMultipleCrep;
    }

    public static MultiplePaymentHeader getActualMultiplePaymentHeader() {
        return actualMultiplePaymentHeader;
    }

    public static void setActualMultiplePaymentHeader(MultiplePaymentHeader actualMultiplePaymentHeader) {
        CurrentData.actualMultiplePaymentHeader = actualMultiplePaymentHeader;
    }

    public static String getSelectedPayment() {
        return selectedPayment;
    }

    public static void setSelectedPayment(String selectedPayment) {
        CurrentData.selectedPayment = selectedPayment;
    }
}
