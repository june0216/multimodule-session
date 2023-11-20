package com.efub.shopapi.dto;

import com.efub.shopdomain.product.Product;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDto {
    private String name;
    private BigDecimal price;

    public ProductDto(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public Product toEntity(){
        return Product.builder()
                .name(name)
                .price(price)
                .build();
    }


}
