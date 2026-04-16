package com.example.catalog.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ProductResponse {
    private UUID productId;
    private String sku;
    private String name;
    private String category;
    private BigDecimal price;
    private Boolean isActive;
    private LocalDateTime createdAt;
    
    // Default constructor
    public ProductResponse() {}
    
    // All-args constructor
    public ProductResponse(UUID productId, String sku, String name, String category, 
                           BigDecimal price, Boolean isActive, LocalDateTime createdAt) {
        this.productId = productId;
        this.sku = sku;
        this.name = name;
        this.category = category;
        this.price = price;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }
    
    // Builder pattern
    public static ProductResponseBuilder builder() {
        return new ProductResponseBuilder();
    }
    
    public static class ProductResponseBuilder {
        private UUID productId;
        private String sku;
        private String name;
        private String category;
        private BigDecimal price;
        private Boolean isActive;
        private LocalDateTime createdAt;
        
        public ProductResponseBuilder productId(UUID productId) {
            this.productId = productId;
            return this;
        }
        
        public ProductResponseBuilder sku(String sku) {
            this.sku = sku;
            return this;
        }
        
        public ProductResponseBuilder name(String name) {
            this.name = name;
            return this;
        }
        
        public ProductResponseBuilder category(String category) {
            this.category = category;
            return this;
        }
        
        public ProductResponseBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }
        
        public ProductResponseBuilder isActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }
        
        public ProductResponseBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        
        public ProductResponse build() {
            return new ProductResponse(productId, sku, name, category, price, isActive, createdAt);
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