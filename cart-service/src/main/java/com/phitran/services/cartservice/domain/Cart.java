package com.phitran.services.cartservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @Indexed
    private String customer;
    private PersonalCart personalShoppingCart;

    public Cart(String customer) {
        this.customer = customer;
    }
}
