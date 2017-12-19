package com.example.admin.iposapp.model;

import com.google.android.gms.common.server.converter.StringToIntConverter;

/**
 * Created by admin on 14/07/2016.
 */
public class Settings
{
    private int id;
    private String server;
    private String appUser;
    private String appUserPass;
    private String lastSale;
    private String billing;
    private String sellerSerie;
    private String clientSerie;
    private String company;
    private String branch;



    public String getClientSerie()
    {
        return clientSerie;
    }

    public void setClientSerie(String clientSerie)
    {
        this.clientSerie = clientSerie;
    }

    public String getSellerSerie()
    {
        return sellerSerie;
    }

    public void setSellerSerie(String sellerSerie)
    {
        this.sellerSerie = sellerSerie;
    }

    public String getBilling()
    {
        return billing;
    }

    public void setBilling(String billing)
    {
        this.billing = billing;
    }

    public String getLastSale()
    {
        return lastSale;
    }

    public void setLastSale(String lastSale)
    {
        this.lastSale = lastSale;
    }

    public String getAppUserPass()
    {
        return appUserPass;
    }

    public void setAppUserPass(String appUserPass)
    {
        this.appUserPass = appUserPass;
    }

    public String getAppUser()
    {
        return appUser;
    }

    public void setAppUser(String appUser)
    {
        this.appUser = appUser;
    }

    public Settings()
    {
        sellerSerie = "";
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getServer()
    {
        return server;
    }

    public void setServer(String server)
    {
        this.server = server;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
