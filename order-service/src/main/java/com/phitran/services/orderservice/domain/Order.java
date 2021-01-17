package com.phitran.services.orderservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Document
@AllArgsConstructor
public class Order {
    @Id
    @Indexed
    Long id;
    @Indexed
    String username;
    LocalDate orderedDate;
    String status;
    BigDecimal total;
    List<Item> items;
}
