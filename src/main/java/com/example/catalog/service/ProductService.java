package com.example.catalog.service;

import com.example.catalog.dto.ProductRequest;
import com.example.catalog.dto.ProductResponse;
import com.example.catalog.entity.Product;
import com.example.catalog.exception.ProductNotFoundException;
import com.example.catalog.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProductService {
    
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    
    private final ProductRepository productRepository;
    
    // Constructor injection (replaces @RequiredArgsConstructor)
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        log.debug("Fetching all products with pagination: page={}, size={}", 
                  pageable.getPageNumber(), pageable.getPageSize());
        
        return productRepository.findAll(pageable)
                .map(this::toResponse);
    }
    
    @Transactional(readOnly = true)
    public Page<ProductResponse> searchProducts(String search, String category, 
                                                  Boolean isActive, Pageable pageable) {
        log.debug("Searching products - search: {}, category: {}, active: {}", 
                  search, category, isActive);
        
        return productRepository.searchProducts(search, category, isActive, pageable)
                .map(this::toResponse);
    }
    
    @Transactional(readOnly = true)
    public ProductResponse getProductById(UUID id) {
        log.debug("Fetching product by id: {}", id);
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        
        return toResponse(product);
    }
    
    @Transactional(readOnly = true)
    public ProductResponse getProductBySku(String sku) {
        log.debug("Fetching product by sku: {}", sku);
        
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with sku: " + sku));
        
        return toResponse(product);
    }
    
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        log.info("Creating new product with SKU: {}", request.getSku());
        
        // Check if SKU already exists
        if (productRepository.findBySku(request.getSku()).isPresent()) {
            throw new IllegalArgumentException("Product with SKU " + request.getSku() + " already exists");
        }
        
        Product product = Product.builder()
                .sku(request.getSku())
                .name(request.getName())
                .category(request.getCategory())
                .price(request.getPrice())
                .isActive(request.getIsActive())
                .build();
        
        Product savedProduct = productRepository.save(product);
        log.info("Product created successfully with id: {}", savedProduct.getProductId());
        
        return toResponse(savedProduct);
    }
    
    @Transactional
    public ProductResponse updateProduct(UUID id, ProductRequest request) {
        log.info("Updating product with id: {}", id);
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        
        // Update fields
        product.setSku(request.getSku());
        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        product.setIsActive(request.getIsActive());
        
        Product updatedProduct = productRepository.save(product);
        log.info("Product updated successfully with id: {}", id);
        
        return toResponse(updatedProduct);
    }
    
    @Transactional
    public void deleteProduct(UUID id) {
        log.info("Deleting product with id: {}", id);
        
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        
        productRepository.deleteById(id);
        log.info("Product deleted successfully with id: {}", id);
    }
    
    private ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .sku(product.getSku())
                .name(product.getName())
                .category(product.getCategory())
                .price(product.getPrice())
                .isActive(product.getIsActive())
                .createdAt(product.getCreatedAt())
                .build();
    }
}