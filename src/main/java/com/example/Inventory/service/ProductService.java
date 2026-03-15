package com.example.Inventory.service;

import com.example.Inventory.dto.AddStockRequest;
import com.example.Inventory.model.Product;

public interface ProductService {

    Product addStock(AddStockRequest request, String employeeEmail);

}