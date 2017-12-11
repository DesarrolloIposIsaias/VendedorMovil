package com.example.admin.iposapp.database;

import com.example.admin.iposapp.model.Kit;

import com.example.admin.iposapp.model.Client;

import java.util.List;

/**
 * Created by usuario on 08/12/2017.
 */

public interface InterfaceKitDAO {
    Kit fetchKitById(int kitId);
    List<Kit> fetchAllKits();
    boolean addKit(Kit kit);
    boolean addKits(List<Kit> kits);
    boolean deleteAllKits();
    List<Kit> searchByFirstLetter(char search);
    boolean isEmpty();
    int getNumberOfKits();
    boolean updateKit(Kit client);
}
