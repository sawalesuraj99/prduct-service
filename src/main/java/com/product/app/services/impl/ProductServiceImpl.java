package com.product.app.services.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.product.app.entity.Brand;
import com.product.app.entity.Product;
import com.product.app.entity.Rating;
import com.product.app.feignclient.BransService;
import com.product.app.feignclient.RatingService;
import com.product.app.repo.ProductRepository;
import com.product.app.services.ProductService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class ProductServiceImpl implements ProductService{
	
	private ProductRepository productRepository;
	
	private RatingService ratingService;
	
//	private RestTemplate restTemplate;
	
	private BransService bransService;

	public ProductServiceImpl(ProductRepository productRepository,
	    RatingService ratingService,BransService bransService) {
		
		super();
		this.productRepository = productRepository;
//		this.restTemplate = restTemplate;
		this.ratingService = ratingService;
		this.bransService = bransService;
	}

	@Override
	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(Product product, Long productId) {
		Product orElseThrow = productRepository.findById(productId).orElseThrow();
		orElseThrow.setDescription(product.getDescription());
		orElseThrow.setPrice(product.getPrice());
		productRepository.save(orElseThrow);
		return productRepository.save(orElseThrow);
	}

	@Override
	@CircuitBreaker(name = "ratingBreaker", fallbackMethod = "ratingFallBackExternalService")
	public Product getProduct(Long productId) {
		Product orElseThrow = productRepository.findById(productId).orElseThrow(()-> new RuntimeException("productId: "+productId+" Not Found"));
//		Rating[] forObject = restTemplate.getForObject("http://RATINGSERVICE/api/ratings/product-"+productId, Rating[].class);
//		orElseThrow.setRatings(Arrays.asList(forObject));
		List<Rating> forObject = ratingService.getRatingByProductId(productId).getBody();
		orElseThrow.setRatings(forObject);
//		Brand forObject1 = restTemplate.getForObject("http://PRODUCTBRAND/brands/product/"+productId, Brand.class);
//		orElseThrow.setBrand(forObject1);
		ResponseEntity<Brand> brandByProductId = bransService.getBrandByProductId(productId);
		Brand body = brandByProductId.getBody();
		orElseThrow.setBrand(body);
		return orElseThrow;
	}
	
	public Product ratingFallBackExternalService(Long productId,Exception ex) {
		Product product = new Product();
		product.setBrand(null);
		product.setDescription("this user is created dummy because some service is down");
		product.setName("dummy");
		product.setPrice(0.0);
		product.setRatings(null);
		return product;
	}
	
////	
//
//int retryCount=1;
//	
//	@Override
//	@RateLimiter(name = "ratingRateLimiter", fallbackMethod = "ratingFallBackExternalService")
//	public Product getProduct(Long productId) {
//		System.out.println("retry count: "+retryCount);
//		retryCount++;
//		Product orElseThrow = productRepository.findById(productId).orElseThrow(()-> new RuntimeException("productId: "+productId+" Not Found"));
////		Rating[] forObject = restTemplate.getForObject("http://RATINGSERVICE/api/ratings/product-"+productId, Rating[].class);
////		orElseThrow.setRatings(Arrays.asList(forObject));
//		List<Rating> forObject = ratingService.getRatingByProductId(productId).getBody();
//		orElseThrow.setRatings(forObject);
////		Brand forObject1 = restTemplate.getForObject("http://PRODUCTBRAND/brands/product/"+productId, Brand.class);
////		orElseThrow.setBrand(forObject1);
//		ResponseEntity<Brand> brandByProductId = bransService.getBrandByProductId(productId);
//		Brand body = brandByProductId.getBody();
//		orElseThrow.setBrand(body);
//		return orElseThrow;
//	}
//	
//	public Product ratingFallBackExternalService(Long productId,Exception ex) {
//		Product product = new Product();
//		product.setBrand(null);
//		product.setDescription("this user is created dummy because some service is down");
//		product.setName("dummy");
//		product.setPrice(0.0);
//		product.setRatings(null);
//		return product;
//	}

	@Override
	public List<Product> getAllProduct() {
		return productRepository.findAll();
	}

	@Override
	public String deleteProduct(Long productId) {
		Product orElseThrow = productRepository.findById(productId).orElseThrow();
		productRepository.deleteById(orElseThrow.getProductId());
		return "delete Successfull";
	}

	@Override
	public Product findProductByName(String name) {
		return productRepository.findByName(name).orElseThrow();
	}

	@Override
	public List<Product> getAllProductBySearchDescription(String description) {
		return productRepository.findAllByDescriptionContaining(description).orElseThrow();
	}

	@Override
	public List<Product> getAllProductByUserId(Long userId) {
		return productRepository.findByUserId(userId).orElseThrow();
	}
}
