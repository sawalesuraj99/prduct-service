package com.product.app.entity;


import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long productId;
	private String name;
	private Double price;
	private String description;
	private Long userId;
	
	@Transient
	private List<Rating> ratings;
	
	@Transient
	private Brand brand;
}
