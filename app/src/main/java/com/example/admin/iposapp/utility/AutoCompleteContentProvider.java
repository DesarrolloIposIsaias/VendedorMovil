package com.example.admin.iposapp.utility;

import com.example.admin.iposapp.model.Client;
import com.example.admin.iposapp.model.Crep;

import java.util.List;

/**
 * Created by sopor on 13/12/2017.
 */

public class AutoCompleteContentProvider {

    public static String[] getClients(List<Client> clients){
        String[] strItems = new String[clients.size()];

        for(int i = 0; i < clients.size(); i++) {

            strItems[i] = clients.get(i).getNombre() + "<" + clients.get(i).getClave() + ">";
        }

        return strItems;
    }

    public static String[] getCreps(List<Crep> creps){
        String[] strItems = new String[creps.size()];

        for(int i = 0; i < creps.size(); i++) {

            strItems[i] = creps.get(i).getId() + "<" + creps.get(i).getCliente() + ">";
        }

        return strItems;
    }
}
