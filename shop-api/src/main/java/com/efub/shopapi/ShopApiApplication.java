package com.efub.shopapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.efub") //com.efub 패키지 및 그 하위 패키지를 스캔
@ConfigurationPropertiesScan
@EntityScan(basePackages = {"com.efub.shopdomain", "com.efub.shopdomainredis"})
@EnableJpaRepositories(basePackages = {"com.efub.shopdomainredis", "com.efub.shopdomainrdb"})
public class ShopApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopApiApplication.class, args);
    }

}
