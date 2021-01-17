package com.phitran.services.productservice.service;

import com.phitran.services.productservice.controller.ProductController;
import com.phitran.services.productservice.entity.Product;
import com.phitran.services.productservice.entity.ProductPriceHistory;
import com.phitran.services.productservice.repository.ProductPriceHistoryRepository;
import com.phitran.services.productservice.repository.ProductRepository;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductPriceHistoryRepository priceRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    public ProductService(ProductRepository productRepository, ProductPriceHistoryRepository priceRepository) {
        this.productRepository = productRepository;
        this.priceRepository = priceRepository;
    }

    /**
     * find products with filter and pagination
     * @param predicate
     * @param pageable
     * @return
     */
    public List<Product> findAll(Predicate predicate, Pageable pageable) {
        return productRepository.findAll(predicate, pageable).getContent();
    }

    /**
     * find product by Id
     * @param id
     * @return
     */
    public Product findProductById(String id) {
        return productRepository.findById(Long.parseLong(id)).orElseThrow();
    }

    /**
     * add new product
     * @param product
     * @return
     */
    public Product add(Product product) {
        return productRepository.save(product);
    }

    /**
     * update product information
     * @param newProduct
     * @return Product
     */
    public Product update(Product newProduct) {
        trackPriceChange(newProduct);
        return productRepository.save(newProduct);
    }

    /**
     * find all price activities by product id
     * @param id
     * @return
     */
    public List<ProductPriceHistory> findAllByProductId (String id) {
        return priceRepository.findAllByProductId(Long.parseLong(id));
    }

    /**
     * tracking price change for auditing
     * @param newProduct
     */
    private void trackPriceChange(Product newProduct) {
        Product oldProduct = productRepository.findById(newProduct.getId()).orElseThrow();
        if (newProduct.getPrice().compareTo(oldProduct.getPrice()) != 0) {
            LOGGER.info("tracking price change for product id {}", oldProduct.getId());
            ProductPriceHistory productPriceHistory = new ProductPriceHistory(oldProduct, newProduct.getPrice());
            priceRepository.save(productPriceHistory);
        }
    }

}
