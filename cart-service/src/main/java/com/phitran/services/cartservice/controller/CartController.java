package com.phitran.services.cartservice.controller;

import com.phitran.services.cartservice.domain.PersonalCart;
import com.phitran.services.cartservice.domain.Cart;
import com.phitran.services.cartservice.service.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@Api( tags = "Carts")
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/all")
    @ApiOperation(value = "This method is used to get all carts in the system.")
    public List<Cart> getAll() {
        LOGGER.info("Carts find all");
        return cartService.findAll();
    }

    @GetMapping
    @ApiOperation(value = "This method is used to get customer's cart by current user.")
    public PersonalCart getShoppingCartOfCustomer(@ApiIgnore Authentication authentication) {
        LOGGER.info("Cart find all by customer");
        return cartService.findByCustomer(authentication.getName());
    }

    @PutMapping
    @ApiOperation(value = "This method is used to update customer's cart by current user.")
    public Cart updateCartByCustomer(@RequestBody PersonalCart personalShoppingCart,
                                     @ApiIgnore Authentication authentication) {
        LOGGER.info("Cart update by customer");
        return cartService.updateShoppingCart(authentication.getName(), personalShoppingCart);
    }

    @DeleteMapping
    @ApiOperation(value = "This method is used to delete customer's cart by current user.")
    public void deleteCartByCustomer(@ApiIgnore Authentication authentication) {
        LOGGER.info("Cart delete by customer");
        cartService.deleteShoppingCart(authentication.getName());
    }
}
