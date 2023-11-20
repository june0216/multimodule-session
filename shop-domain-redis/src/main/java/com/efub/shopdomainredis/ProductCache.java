package com.efub.shopdomainredis;

import java.math.BigDecimal;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.annotation.Id;

@RedisHash("productCache")
public class ProductCache {
    @Id
    private Long id;
    private String name;
    private BigDecimal price;

    // 표준 getters 및 setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

