package com.product.app.services;

import java.util.List;

import com.product.app.entity.Product;

public interface ProductService {

	Product saveProduct(Product product);
	Product updateProduct(Product product, Long productId);
	Product getProduct(Long productId);
	List<Product> getAllProduct();
	String deleteProduct(Long productId);
	Product findProductByName(String name);
	List<Product> getAllProductBySearchDescription(String description);
	List<Product> getAllProductByUserId(Long userId);
}
