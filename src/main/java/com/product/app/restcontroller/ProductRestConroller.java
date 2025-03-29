package com.product.app.restcontroller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.app.entity.Product;
import com.product.app.services.ProductService;

@RestController
@RequestMapping("/product/api/v1")
public class ProductRestConroller {

	private ProductService productService;

	public ProductRestConroller(ProductService productService) {
		super();
		this.productService = productService;
	}

	@PostMapping("/save")
	public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
		Product saveProduct = productService.saveProduct(product);
		return new ResponseEntity<>(saveProduct, HttpStatus.CREATED);
	}

	@PutMapping("/update/productid-{productId}")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product,@PathVariable("productId") Long productId){
		Product updateProduct = productService.updateProduct(product, productId);
		return new ResponseEntity<>(updateProduct,HttpStatus.OK);
	}
	
	@GetMapping("/getproductbyid-{productId}")
	public ResponseEntity<Product> findProductById(@PathVariable("productId") Long productId) {
		Product product = productService.getProduct(productId);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}
	
	@GetMapping("/getallproduct")
	public ResponseEntity<List<Product>> findallProduct() {
		List<Product> allProduct = productService.getAllProduct();
		return new ResponseEntity<>(allProduct, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteproductbyid-{productId}")
	public ResponseEntity<String> deleteProductById(@PathVariable("productId") Long productId) {
		String deleteProduct = productService.deleteProduct(productId);
		return new ResponseEntity<>(deleteProduct, HttpStatus.OK);
	}
		
	@GetMapping("/getproductbyid/{name}")
	public ResponseEntity<Product> findProductByName(@PathVariable("name") String name) {
		Product product = productService.findProductByName(name);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}
	
	@GetMapping("/getallproductbyserch/{search}")
	public ResponseEntity<List<Product>> findallProductBySearch(@PathVariable("search") String search) {
		List<Product> allProduct = productService.getAllProductBySearchDescription(search);
		return new ResponseEntity<>(allProduct, HttpStatus.OK);
	}
	
	@GetMapping("/getallproductbyuserid/{userId}")
	public ResponseEntity<List<Product>> findProductByUserId(@PathVariable("userId") Long userId) {
		List<Product> allProductByUserId = productService.getAllProductByUserId(userId);
		return new ResponseEntity<>(allProductByUserId, HttpStatus.OK);
	}
}
