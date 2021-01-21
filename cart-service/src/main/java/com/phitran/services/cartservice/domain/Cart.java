package com.phitran.services.cartservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Data
@RedisHash("cart")
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @Indexed
    private String customerUsername;
}
