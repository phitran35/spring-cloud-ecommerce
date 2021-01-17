package com.phitran.services.productservice.controller;

import com.phitran.services.productservice.entity.Product;
import com.phitran.services.productservice.entity.ProductPriceHistory;
import com.phitran.services.productservice.model.ProductRequest;
import com.phitran.services.productservice.service.ProductService;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;


@RestController
@Api( tags = "Products")
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ApiOperation(value = "This method is used to get the products.")
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                    value = "Results page you want to retrieve (0..N)", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page.", defaultValue = "5"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported."),
            // query DSL
            @ApiImplicitParam(name = "name", dataType = "string", paramType = "query", value = "Product name"),
            @ApiImplicitParam(name = "brand", dataType = "string", paramType = "query", value = "Product brand"),
            @ApiImplicitParam(name = "color", dataType = "string", paramType = "query", value = "Product color"),
            @ApiImplicitParam(name = "price", dataType = "number", paramType = "query", value = "Product price")
    })
    public List<Product> getAllProducts(@QuerydslPredicate(root = Product.class) Predicate predicate,
                                        @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC)
                                        @ApiIgnore(
                                                "Ignored because swagger ui shows the wrong params, " +
                                                        "instead they are explained in the implicit params"
                                        ) Pageable pageable,
                                        @ApiIgnore @RequestParam(required=false) MultiValueMap<String, List<String>> allRequestParams,
                                        @ApiIgnore  Authentication authentication) {
        LOGGER.info("Products find all");
        return productService.findAll(predicate, pageable);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "This method is used to get the product detail.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "string", paramType = "path")
    })
    public Product getProductDetail(@PathVariable String id, @ApiIgnore @ApiParam(hidden = true) Authentication authentication) {
        LOGGER.info("Organization find: id={}", id);
        return productService.findProductById(id);
    }

    @ApiOperation(value = "This method is used to update the product.")
    @PutMapping("/{id}")
    public Product updateProduct(@Valid @RequestBody ProductRequest productRequest, @PathVariable String id) {
        LOGGER.info("Product update: {}", productRequest);
        Product updatedProduct = productRequest.toEntity();
        updatedProduct.setId(Long.parseLong(id));
        return productService.update(updatedProduct);
    }

    @ApiOperation(value = "This method is used to add the product.")
    @PostMapping
    public Product addProduct(@Valid @RequestBody ProductRequest productRequest) {
        LOGGER.info("Product add: {}", productRequest);
        Product newProduct = productRequest.toEntity();
        return productService.add(newProduct);
    }

    @GetMapping("/{id}/price-history")
    @ApiOperation(value = "This method is used to get all product price history.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "string", paramType = "path")
    })
    public List<ProductPriceHistory> getProductPriceHistoryLog(@PathVariable String id) {
        LOGGER.info("Product price history find: id={}", id);
        return productService.findAllByProductId(id);
    }

}
