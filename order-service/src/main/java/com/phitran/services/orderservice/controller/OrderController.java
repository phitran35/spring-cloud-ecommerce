package com.phitran.services.orderservice.controller;

import com.phitran.services.orderservice.client.CartClient;
import com.phitran.services.orderservice.domain.Item;
import com.phitran.services.orderservice.domain.Order;
import com.phitran.services.orderservice.service.OrderService;
import com.phitran.services.orderservice.utilities.OrderUtilities;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDate;
import java.util.List;

@RestController
@Api( tags = "Order")
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final CartClient cartClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    public OrderController(OrderService orderService, CartClient cartClient) {
        this.orderService = orderService;
        this.cartClient = cartClient;
    }

    @PostMapping
    @ApiOperation(value = "This method is used to save order from current user.")
    public ResponseEntity<Order> saveOrder(@RequestParam(name = "cartId") String cartId,
            @ApiIgnore Authentication authentication) {
        LOGGER.info("Order save order by user {}", authentication.getName());
        List<Item> cart = cartClient.getAllItemsFromCart(cartId);
        Order order = this.createOrder(cart, authentication.getName());
        try {
            orderService.saveOrder(order);
            cartClient.deleteCartByCurrentUser();
            return new ResponseEntity<Order>(
                    order,
                    HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.info("Order error when checkout order ", e.getMessage());
            return new ResponseEntity<Order>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by-username/{username}")
    @ApiOperation(value = "This method is used to get order detail by username.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", dataType = "string", paramType = "path",
                    value = "Customer's username, ex: test1@mail.com", defaultValue = "test1@mail.com"),
    })
    public List<Order> getAllByUsername(@PathVariable @ApiIgnore String username) {
        LOGGER.info("CustomerActivity find by username = {}", username);
        return orderService.findByUsername(username);
    }

    private Order createOrder(List<Item> cart, String username) {
        Order order = new Order();
        order.setItems(cart);
        order.setUsername(username);
        order.setTotal(OrderUtilities.countTotalPrice(cart));
        order.setOrderedDate(LocalDate.now());
        order.setStatus("CHECKOUT_COMPLETED");
        return order;
    }

}
