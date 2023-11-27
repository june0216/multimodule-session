package com.efub.shopdomainrdb;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.efub.shopdomain.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(Long id);
}
