package com.phitran.services.orderservice.client;

import com.google.common.collect.Lists;
import com.phitran.services.orderservice.domain.Item;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "cart-service", fallback = CartClient.CartClientFallback.class)
public interface CartClient {

    @GetMapping(value = "carts/get-all-items")
    List<Item> getAllItemsFromCartFromCurrentUser();

    @DeleteMapping("/carts")
    void deleteCartByCurrentUser();

    class CartClientFallback implements CartClient {
        @Override
        public List<Item> getAllItemsFromCartFromCurrentUser() {
            return Lists.newArrayList();
        }
        public void deleteCartByCurrentUser() {
            return;
        }
    }
}
