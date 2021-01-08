package com.phitran.services.productservice.repository;

import com.phitran.services.productservice.entity.ProductPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductPriceHistoryRepository extends JpaRepository<ProductPriceHistory, Long> {
    List<ProductPriceHistory> findAllByProductId(Long productId);
}
