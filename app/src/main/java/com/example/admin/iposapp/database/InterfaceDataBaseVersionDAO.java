package com.example.admin.iposapp.database;

/**
 * Created by admin on 23/08/2016.
 */
public interface InterfaceDataBaseVersionDAO
{
    void upgradeVersion(String date);
    int getCurrentVersion();
}
