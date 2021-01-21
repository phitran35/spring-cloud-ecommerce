package com.phitran.services.cartservice.ultilities;

import com.phitran.services.cartservice.domain.Product;

import java.math.BigDecimal;

public class CartUtilities {
    public static BigDecimal getSubTotalForItem(Product product, int quantity){
        return (product.getPrice()).multiply(BigDecimal.valueOf(quantity));
    }
}
