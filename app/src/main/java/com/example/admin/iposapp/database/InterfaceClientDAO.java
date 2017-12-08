package com.example.admin.iposapp.database;

import com.example.admin.iposapp.model.Client;

import java.util.List;

/**
 * Created by admin on 21/06/2016.
 */
public interface InterfaceClientDAO
{
    Client fetchClientById(int clientId);
    List<Client> fetchAllClients();
    boolean addClient(Client client);
    boolean addClients(List<Client> clients);
    boolean deleteAllUsers();
    List<Client> searchByFirstLetter(char search);
    boolean isEmpty();
    int getNumberOfClients();
    boolean updateClient(Client client);
}
