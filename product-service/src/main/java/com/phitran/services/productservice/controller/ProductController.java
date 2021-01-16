package com.phitran.services.productservice.controller;

import com.phitran.services.productservice.entity.Product;
import com.phitran.services.productservice.model.ProductRequest;
import com.phitran.services.productservice.service.ProductService;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.security.core.Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping(ProductController.PRODUCT_PATH)
public class ProductController {
    public static final String PRODUCT_PATH = "/products";
    private final ProductService productService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts(@QuerydslPredicate(root = Product.class) Predicate predicate,
                                        Pageable pageable, @RequestParam(required=false) MultiValueMap<String, List<String>> allRequestParams,
                                        @RequestHeader(name="Username", required=false) String customer, Principal principal) {
        LOGGER.info("Organization find all");
        return productService.findAll(predicate, pageable);
    }

    @GetMapping("/{id}")
    public Product getProductDetail(@PathVariable String id, @RequestHeader(name="Username", required=false) String customer, Authentication authentication) {
        LOGGER.info("Organization find: principal={}", authentication);
        LOGGER.info("Organization find: id={}", id);
        return productService.findProductById(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@Valid @RequestBody ProductRequest productRequest, @PathVariable String id) {
        LOGGER.info("Product update: {}", productRequest);
        Product updatedProduct = productRequest.toEntity();
        updatedProduct.setId(Long.parseLong(id));
        return productService.update(updatedProduct);
    }

    @PostMapping
    public Product addProduct(@Valid @RequestBody ProductRequest productRequest) {
        LOGGER.info("Product add: {}", productRequest);
        Product newProduct = productRequest.toEntity();
        return productService.add(newProduct);
    }

}
