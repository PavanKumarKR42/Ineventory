package com.example.Inventory.service;

import com.example.Inventory.dto.CreateOrderRequest;
import com.example.Inventory.model.Order;

public interface OrderService {

    Order createOrder(CreateOrderRequest request, String customerEmail);

}