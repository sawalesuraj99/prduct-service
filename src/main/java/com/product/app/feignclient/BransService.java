package com.product.app.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.product.app.entity.Brand;


@FeignClient(name = "PRODUCTBRAND" ,path = "/brands")
public interface BransService {

	@GetMapping("/product/{productId}")
    public ResponseEntity<Brand> getBrandByProductId(@PathVariable("productId") Long productId);
}
