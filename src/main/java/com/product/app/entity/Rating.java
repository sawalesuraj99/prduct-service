package com.product.app.entity;

import lombok.Data;

@Data
public class Rating {

	private Long ratingId;
    private Long productId;
    private Long userId;
    private int value;
}
