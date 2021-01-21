package com.phitran.services.cartservice.repository;

import java.util.Collection;

public interface CartRepository {
    void addItemToCart(String key, Object item);
    Collection<Object> getCart(String key, Class type);
    void deleteItemFromCart(String key, Object item);
    void deleteCart(String key);
}
