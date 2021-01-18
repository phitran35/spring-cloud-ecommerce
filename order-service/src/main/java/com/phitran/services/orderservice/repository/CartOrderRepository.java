package com.phitran.services.orderservice.repository;

import com.phitran.services.orderservice.domain.Order;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CartOrderRepository extends CrudRepository<Order, String> {
    List<Order> findByUsername(String username);
}
