package com.phitran.services.orderservice.utilities;


import com.phitran.services.orderservice.domain.Item;

import java.math.BigDecimal;
import java.util.List;

public class OrderUtilities {
    public static BigDecimal countTotalPrice(List<Item> cart){
        BigDecimal total = BigDecimal.ZERO;
        for(int i = 0; i < cart.size(); i++){
            total = total.add(cart.get(i).getSubTotal());
        }
        return total;
    }
}
