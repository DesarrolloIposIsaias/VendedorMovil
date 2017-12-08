package com.example.admin.iposapp.database;


import com.example.admin.iposapp.model.SaleDetail;

import java.util.List;

/**
 * Created by admin on 24/06/2016.
 */
public interface InterfaceSaleDetailDAO {
    public SaleDetail fetchSaleDetailById(int saleId);
    public List<SaleDetail> fetchAllSaleDetails();
    public List<SaleDetail> fetchSaleDetailsBySale(String id);
    public boolean addSaleDetail(SaleDetail sale);
    public boolean addSaleDetails(List<SaleDetail> products);
    public boolean deleteAllSaleDetails();
}
