package com.phitran.services.cartservice.repository;

import java.util.Collection;

public interface CartRepository {
    public void addItemToCart(String key, Object item);
    public Collection<Object> getCart(String key, Class type);
    public void deleteItemFromCart(String key, Object item);
    public void deleteCart(String key);
}
