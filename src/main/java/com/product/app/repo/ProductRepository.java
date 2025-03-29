package com.product.app.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.product.app.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	Optional<Product> findByName(String name);

	@Query(value = "SELECT p FROM Product p WHERE p.description LIKE %:keyword%")
	Optional<List<Product>> findAllByDescriptionContaining(@Param("keyword") String keyword);

	Optional<List<Product>>  findByUserId(Long userId);

}
