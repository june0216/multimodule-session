package com.efub.shopapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.efub")
@EnableJpaAuditing
@EntityScan("com.efub.shop-domain")//@Entity , Spring Data Repository 관련 클래스들은 해당 패키지에 존재해도 인식을 할 수 없는 문제 해결
@EnableJpaRepositories("com.efub")
public class ShopApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopApiApplication.class, args);
    }

}
