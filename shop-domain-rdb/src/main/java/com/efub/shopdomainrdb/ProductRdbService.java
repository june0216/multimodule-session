package com.efub.shopdomainrdb;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.efub.shopdomain.product.Product;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductRdbService {

    private final ProductRepository productRepository;

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Product findById(Long id){
        return productRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}

