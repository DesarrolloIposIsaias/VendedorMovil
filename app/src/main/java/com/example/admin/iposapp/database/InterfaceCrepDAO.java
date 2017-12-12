package com.example.admin.iposapp.database;

import com.example.admin.iposapp.model.Crep;

import java.util.List;

/**
 * Created by Desarrollo IPOS on 08/12/2017.
 */

public interface InterfaceCrepDAO
{
    Crep fetchCrepById(int crepId);
    List<Crep> fetchAllCreps();
    boolean addCrep(Crep crep);
    boolean addCreps(List<Crep> creps);
    boolean deleteAllCreps();
    List<Crep> searchByFirstLetter(char search);
    boolean isEmpty();
    int getNumberOfCreps();
    boolean updateCrep(Crep crep);
}
