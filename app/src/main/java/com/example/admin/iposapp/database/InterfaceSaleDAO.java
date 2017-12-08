package com.example.admin.iposapp.database;

import com.example.admin.iposapp.model.Sale;

import java.util.List;

/**
 * Created by admin on 24/06/2016.
 */
public interface InterfaceSaleDAO
{
    public Sale fetchSaleById(String saleId);
    public List<Sale> fetchAllSales();
    public Sale fetchLastInserted();
    public boolean addSale(Sale sale);
    public boolean addSales(List<Sale> products);
    public void deleteSale(String idSale);
    public boolean deleteAllSales();
    public boolean updateSale(String id, Sale sale);
}
