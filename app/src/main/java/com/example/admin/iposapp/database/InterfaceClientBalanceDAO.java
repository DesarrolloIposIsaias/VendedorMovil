package com.example.admin.iposapp.database;

import android.database.Cursor;

import com.example.admin.iposapp.model.ClientBalance;

import java.util.List;
/**
 * Created by Desarrollo IPOS on 03/01/2018.
 */

public interface InterfaceClientBalanceDAO {
    ClientBalance fetchClientBalanceByClient(String client);
    ClientBalance fetchClientBalanceById(int id);
    boolean addClientBalance(ClientBalance clientBalance);
    boolean deleteClientBalancebyId(String id);

}
