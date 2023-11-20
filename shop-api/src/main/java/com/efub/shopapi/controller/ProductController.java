package com.efub.shopapi.controller;

import static com.efub.shopcommon.constants.Constants.API_BASE_URL;

import com.efub.shopapi.dto.ProductDto;
import com.efub.shopapi.service.*;
import com.efub.shopdomain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_BASE_URL+"/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{productId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ProductDto getProductName(@PathVariable Long productId){
        Product product = productService.getProduct(productId);
        return new ProductDto(product.getName(), product.getPrice());
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ProductDto createProduct(@RequestBody ProductDto productDto){
        Product product = productService.addNewProduct(productDto);
        return new ProductDto(product.getName(), product.getPrice());
    }

}
