package com.phitran.services.orderservice.service;

import com.phitran.services.orderservice.domain.Order;

import java.util.List;

public interface OrderService {
    Order saveOrder(Order order);
    List<Order> findByUsername(String username);
}