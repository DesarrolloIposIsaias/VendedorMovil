package com.example.admin.iposapp.database;

import com.example.admin.iposapp.model.Bank;

import java.util.List;

/**
 * Created by Desarrollo IPOS on 08/12/2017.
 */

public interface InterfaceBankDAO
{
    Bank fetchBankById(int bankId);
    List<Bank> fetchAllBanks();
    boolean addBank(Bank bank);
    boolean addBanks(List<Bank> banks);
    boolean deleteAllBanks();
    List<Bank> searchByFirstLetter(char search);
    boolean isEmpty();
    int getNumberOfBanks();
    boolean updateBank(Bank bank);
}
