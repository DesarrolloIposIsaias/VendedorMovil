package com.example.admin.iposapp.model;

import com.google.android.gms.common.server.converter.StringToIntConverter;

/**
 * Created by admin on 14/07/2016.
 */
public class Settings
{
    private int id;
    private String server;
    private String user;
    private String password;
    private String seller;
    private String soapSellerId;
    private String folder;
    private String folderPass;
    private String appUser;
    private String appUserPass;
    private String lastSale;
    private String billing;
    private String sellerSerie;
    private String clientSerie;
    private String soapServer;



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

    public String getFolderPass()
    {
        return folderPass;
    }

    public void setFolderPass(String folderPass)
    {
        this.folderPass = folderPass;
    }

    public String getFolder()
    {
        return folder;
    }

    public void setFolder(String folder)
    {
        this.folder = folder;
    }

    public String getSoapSellerId()
    {
        return soapSellerId;
    }

    public void setSoapSellerId(String soapSellerId)
    {
        this.soapSellerId = soapSellerId;
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

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getSeller()
    {
        return seller;
    }

    public void setSeller(String seller)
    {
        this.seller = seller;
    }

    public String getSoapServer()
    {
        return soapServer;
    }

    public void setSoapServer(String soapServer)
    {
        this.soapServer = soapServer;
    }
}
