package com.example.admin.iposapp.database;

import com.example.admin.iposapp.model.Settings;

/**
 * Created by admin on 14/07/2016.
 */
public interface InterfaceSettingsDAO
{
    public Settings fetchSettingsBySeller (String seller, String pass);
    public Settings fetchSettingsById (String id);
    public Settings fetchSettingsByUser (String user);
    public Settings fetchSettingsByPassword (String pass);
    public boolean addSettings(Settings settings);
    public boolean deleteSettings(Settings settings);
    public int exist(String id, String pass);
    public boolean update(Settings settings);
    boolean updateLasClient(int lastClient, String user);

}
