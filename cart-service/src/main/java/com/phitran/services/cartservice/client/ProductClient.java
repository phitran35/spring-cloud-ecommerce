package com.phitran.services.cartservice.client;

import com.phitran.services.cartservice.domain.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "product-service", fallback = ProductClient.ProductClientFallback.class)
public interface ProductClient {

    @GetMapping(value = "/products/{id}")
    Product getProductById(@PathVariable(value = "id") Long productId);

    static class ProductClientFallback implements ProductClient {
        @Override
        public Product getProductById(Long productId) {
            return null;
        }
    }
}
