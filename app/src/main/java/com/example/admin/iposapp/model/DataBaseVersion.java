package com.example.admin.iposapp.model;

import com.example.admin.iposapp.database.Database;

/**
 * Created by admin on 23/08/2016.
 */
public class DataBaseVersion
{
    private int version;
    private String date;

    public DataBaseVersion()
    {

    }

    public DataBaseVersion(int v, String d)
    {
        version = v;
        date = d;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public int getVersion()
    {
        return version;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }


}
