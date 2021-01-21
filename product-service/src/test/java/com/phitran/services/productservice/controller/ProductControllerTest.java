package com.phitran.services.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.phitran.services.productservice.entity.Product;
import com.phitran.services.productservice.service.ProductService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {
    public static final String PRODUCT_PATH = "/products";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    public void whenGetAll_ThenReturn200() throws Exception {
        mockMvc.perform(get(PRODUCT_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(productService).findAll(any(), any());
    }

    @Test
    public void whenGetProductDetail_ThenReturnCorrectProduct() throws Exception {
        Product product = mockIPhone();
        when(productService.findProductById("1")).thenReturn(product);
        mockMvc.perform(get(PRODUCT_PATH + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Is.is(product.getName())))
                .andExpect(jsonPath("$.brand", Is.is(product.getBrand())))
                .andExpect(jsonPath("$.color", Is.is(product.getColor())))
                .andExpect(jsonPath("$.price").value(product.getPrice()));
        verify(productService).findProductById("1");
    }

    @Test
    public void whenUpdateProduct_ThenReturnUpdatedProduct() throws Exception {
        Product product = mockIPhone();
        ObjectWriter writer = objectMapper.writer().withDefaultPrettyPrinter();
        String json = writer.writeValueAsString(product);
        mockMvc.perform(put(PRODUCT_PATH + "/1")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
        verify(productService).update(product);
    }

    @Test
    public void whenAddProduct_ThenReturnNewProduct() throws Exception {
        Product product = mockIPhone();
        product.setId(null);
        ObjectWriter writer = objectMapper.writer().withDefaultPrettyPrinter();
        String json = writer.writeValueAsString(product);
        mockMvc.perform(post(PRODUCT_PATH)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
        verify(productService).add(product);
    }

    @Test
    public void getAllProducts_whenNoUsernameHeader_ThenReturn401() throws Exception {
        mockMvc.perform(get(PRODUCT_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        verifyNoInteractions(productService);
    }

    @Test
    public void getProductDetail_whenNoUsernameHeader_ThenReturn401() throws Exception {
        mockMvc.perform(get(PRODUCT_PATH + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
        verifyNoInteractions(productService);
    }

    private Product mockIPhone() {
        return Product.builder().brand("Apple").name("iphone 12").price(BigDecimal.TEN).id(1L).build();
    }
}