package com.example.catalog.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "products")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private UUID productId;
    
    @Column(unique = true, nullable = false, length = 50)
    private String sku;
    
    @Column(nullable = false, length = 200)
    private String name;
    
    @Column(length = 100)
    private String category;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    // Default constructor
    public Product() {}
    
    // All-args constructor
    public Product(UUID productId, String sku, String name, String category, 
                   BigDecimal price, Boolean isActive, LocalDateTime createdAt) {
        this.productId = productId;
        this.sku = sku;
        this.name = name;
        this.category = category;
        this.price = price;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }
    
    // Builder pattern manually implemented
    public static ProductBuilder builder() {
        return new ProductBuilder();
    }
    
    public static class ProductBuilder {
        private UUID productId;
        private String sku;
        private String name;
        private String category;
        private BigDecimal price;
        private Boolean isActive;
        private LocalDateTime createdAt;
        
        public ProductBuilder productId(UUID productId) {
            this.productId = productId;
            return this;
        }
        
        public ProductBuilder sku(String sku) {
            this.sku = sku;
            return this;
        }
        
        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }
        
        public ProductBuilder category(String category) {
            this.category = category;
            return this;
        }
        
        public ProductBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }
        
        public ProductBuilder isActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }
        
        public ProductBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        
        public Product build() {
            return new Product(productId, sku, name, category, price, isActive, createdAt);
        }
    }
    
    // Getters and Setters
    public UUID getProductId() {
        return productId;
    }
    
    public void setProductId(UUID productId) {
        this.productId = productId;
    }
    
    public String getSku() {
        return sku;
    }
    
    public void setSku(String sku) {
        this.sku = sku;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}