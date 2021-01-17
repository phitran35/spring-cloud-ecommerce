package com.phitran.services.orderservice.service;

import com.phitran.services.orderservice.domain.Order;
import com.phitran.services.orderservice.repository.CartOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartOrderRepository cartOrderRepository;

    @Override
    public Order saveOrder(Order order) {
        return cartOrderRepository.save(order);
    }

    /**
     * find all by customer username
     * @param username
     * @return
     */
    @Override
    public List<Order> findByUsername(String username) {
        return cartOrderRepository.findByUsername(username);
    }
}
