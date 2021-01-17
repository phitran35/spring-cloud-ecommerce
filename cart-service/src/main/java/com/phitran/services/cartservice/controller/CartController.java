package com.phitran.services.cartservice.controller;

import com.phitran.services.cartservice.client.ProductClient;
import com.phitran.services.cartservice.domain.Item;
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
    private final  ProductClient productClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);

    @Autowired
    public CartController(CartService cartService, ProductClient productClient) {
        this.cartService = cartService;
        this.productClient = productClient;
    }

    @GetMapping
    @ApiOperation(value = "This method is used to get cart by current user.")
    public List<Object> getCartById(@ApiIgnore Authentication authentication) {
        LOGGER.info("Carts find all");
        return cartService.getCart(authentication.getName());
    }

    @GetMapping("{id}/get-all-items")
    @ApiOperation(value = "This method is used to get cart items by cart id.")
    public List<Item> getAllItemsFromCart(@PathVariable String id) {
        LOGGER.info("Carts et-all-items-from-cart {}", id);
        return cartService.getAllItemsFromCart(id);
    }

    @PostMapping
    @ApiOperation(value = "This method is used to add cart items from current user's cart.")
    public List<Object> addItemToCart(
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") Integer quantity,
            @ApiIgnore Authentication authentication) {
        LOGGER.info("Carts add items to cart for user {}", authentication.getName());
        String cartKey = authentication.getName();
        List<Object> cart = cartService.getCart(cartKey);
        if (cart != null) {
            if (cart.isEmpty()){
                cartService.addItemToCart(cartKey, productId, quantity);
            } else {
                if (cartService.checkIfItemIsExist(cartKey, productId)){
                    cartService.changeItemQuantity(cartKey, productId, quantity);
                } else {
                    cartService.addItemToCart(cartKey, productId, quantity);
                }
            }
        }
        return cart;
    }

    @DeleteMapping()
    @ApiOperation(value = "This method is used to entry cartfrom current user's cart.")
    public void deleteCart(
            @ApiIgnore Authentication authentication) {
        LOGGER.info("Carts delete cart for user {}", authentication.getName());
        cartService.deleteCart(authentication.getName());
        return;
    }

    @DeleteMapping("/item")
    @ApiOperation(value = "This method is used to delete cart product item from current user's cart.")
    public void removeItemFromCart(
            @RequestParam("productId") Long productId,
            @ApiIgnore Authentication authentication) {
        LOGGER.info("Carts delete cart item for user {}", authentication.getName());
        String cartKey = authentication.getName();
        List<Object> cart = cartService.getCart(cartKey);
        if(cart != null) {
            cartService.deleteItemFromCart(cartKey, productId);
        }
        return;
    }
}
