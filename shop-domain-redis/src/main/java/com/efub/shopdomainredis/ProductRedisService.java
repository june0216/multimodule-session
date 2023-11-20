package com.efub.shopdomainredis;


import com.efub.shopdomain.product.Product;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductRedisService {
    private final ProductCacheRepository productCacheRepository;

    public void cacheProductDetails(Product product) {
        ProductCache cache = new ProductCache();
        cache.setId(product.getId());
        cache.setName(product.getName());
        cache.setPrice(product.getPrice());
        productCacheRepository.save(cache);
    }
    public Product getProductFromCache(Long id) {
        return productCacheRepository.findById(id)
                .map(cache -> new Product(cache.getName(), cache.getPrice()))
                .orElse(null);
    }

    // Redis 캐시 업데이트
    public void updateProductInCache(Product product) {
        ProductCache cache = new ProductCache();
        cache.setId(product.getId());
        cache.setName(product.getName());
        cache.setPrice(product.getPrice());
        productCacheRepository.save(cache);
    }
}

