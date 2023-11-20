package com.efub.shopapi.service;

import com.efub.shopapi.dto.ProductDto;
import com.efub.shopdomain.product.Product;
import com.efub.shopdomainrdb.ProductRdbService;
import com.efub.shopdomainredis.ProductRedisService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRdbService productRdbService;
    private final ProductRedisService productRedisService;

    public Product addNewProduct(ProductDto reqDto) {
        Product product =  reqDto.toEntity();
        // RDBMS에 상품 정보를 저장
        productRdbService.saveProduct(product);

        // Redis에 상품 정보를 캐싱
        productRedisService.cacheProductDetails(product);
        return product;
    }
    // Redis에서 상품 조회
    public Product getProduct(Long id) {
        // 먼저 Redis 캐시에서 상품 조회
        Product cache = productRedisService.getProductFromCache(id);

        // Redis에 상품이 있는 경우 바로 반환
        if (cache != null) {
            return cache;
        }

        // Redis에 없는 경우, RDBMS에서 조회
        Product product = productRdbService.findById(id);

        // RDBMS에서 찾은 상품을 Redis에 캐싱
        if (product != null) {
            productRedisService.cacheProductDetails(product);
        }

        return product;
    }

}
