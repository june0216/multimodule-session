package com.efub.shopdomain.order;

import com.efub.shopdomain.product.Product;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int quantity;

    public OrderItem(Product product, BigDecimal price, int quantity) {
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }

    // OrderItem 엔티티와 관련된 비즈니스 로직을 추가할 수 있습니다.
    // ...
}

