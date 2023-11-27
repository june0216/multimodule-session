package com.efub.shopdomainredis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface ProductCacheRepository extends CrudRepository<ProductCache, Long> {
    // 필요한 경우 추가적인 쿼리 메서드 정의
}

