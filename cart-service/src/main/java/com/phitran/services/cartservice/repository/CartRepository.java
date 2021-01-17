package com.phitran.services.cartservice.repository;

import com.phitran.services.cartservice.domain.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, String> {
}
