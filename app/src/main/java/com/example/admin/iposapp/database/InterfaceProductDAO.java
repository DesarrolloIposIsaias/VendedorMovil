package com.example.admin.iposapp.database;

import com.example.admin.iposapp.model.Product;

import java.util.List;

/**
 * Created by admin on 21/06/2016.
 */
public interface InterfaceProductDAO {

    public Product fetchProductById(String productId);
    public List<Product> fetchAllProducts();
    public boolean addProduct(Product product);
    public boolean addProducts(List<Product> products);
    public boolean deleteAllProducts();
    public List<Product> searchByFirstLetter(char search);
}
