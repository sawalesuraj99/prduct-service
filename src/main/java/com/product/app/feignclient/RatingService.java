package com.product.app.feignclient;

import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.product.app.entity.Rating;

@FeignClient(name = "RATINGSERVICE",path = "/api/ratings")
@RibbonClient(name = "RATINGSERVICE")
public interface RatingService {

	@GetMapping("/product-{productId}")
    public ResponseEntity<List<Rating>>  getRatingByProductId(@PathVariable("productId")Long productId);
}
