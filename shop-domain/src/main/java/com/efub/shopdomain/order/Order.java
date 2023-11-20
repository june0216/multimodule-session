package com.efub.shopdomain.order;


import javax.persistence.*;
import java.util.List;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderItems;

    // 기본 생성자, getter 및 setter
    // ...

    // Order 엔티티와 관련된 비즈니스 로직을 추가할 수 있습니다.
    // ...
}

