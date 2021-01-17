package com.phitran.services.cartservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalCart {
    List<Item> cart;

    public PersonalCart(Cart shoppingCart) {
        this.cart = shoppingCart.getPersonalShoppingCart().getCart();
    }
}
