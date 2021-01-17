package com.phitran.services.cartservice.service;

import com.phitran.services.cartservice.domain.PersonalCart;
import com.phitran.services.cartservice.domain.Cart;
import com.phitran.services.cartservice.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository repository;

    @Autowired
    public CartService(CartRepository repository) {
        this.repository = repository;
    }

    public List<Cart> findAll() {
        ArrayList<Cart> result = new ArrayList<>();
        Iterable<Cart> shoppingCarts = repository.findAll();
        shoppingCarts.forEach(result::add);
        return result;
    }

    public PersonalCart findByCustomer(String customer) {
        Cart shoppingCart = repository.findById(customer).orElseThrow();
        return new PersonalCart(shoppingCart);
    }

    public Cart updateShoppingCart(String customer, PersonalCart personalShoppingCart) {
        Optional<Cart> shoppingCart = repository.findById(customer);
        Cart customerShoppingCart = shoppingCart.orElse(new Cart(customer));
        customerShoppingCart.setPersonalShoppingCart(personalShoppingCart);
        return repository.save(customerShoppingCart);
    }

    public void deleteShoppingCart(String customer) {
        repository.deleteById(customer);
    }
}
