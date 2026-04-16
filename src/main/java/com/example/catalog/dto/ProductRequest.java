package com.example.catalog.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ProductRequest {
    
    @NotBlank(message = "SKU is required")
    @Size(min = 3, max = 50, message = "SKU must be between 3 and 50 characters")
    private String sku;
    
    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 200, message = "Product name must be between 2 and 200 characters")
    private String name;
    
    private String category;
    
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @DecimalMax(value = "999999.99", message = "Price cannot exceed 999999.99")
    private BigDecimal price;
    
    private Boolean isActive = true;
    
    // Default constructor
    public ProductRequest() {}
    
    // All-args constructor
    public ProductRequest(String sku, String name, String category, BigDecimal price, Boolean isActive) {
        this.sku = sku;
        this.name = name;
        this.category = category;
        this.price = price;
        this.isActive = isActive;
    }
    
    // Builder pattern
    public static ProductRequestBuilder builder() {
        return new ProductRequestBuilder();
    }
    
    public static class ProductRequestBuilder {
        private String sku;
        private String name;
        private String category;
        private BigDecimal price;
        private Boolean isActive;
        
        public ProductRequestBuilder sku(String sku) {
            this.sku = sku;
            return this;
        }
        
        public ProductRequestBuilder name(String name) {
            this.name = name;
            return this;
        }
        
        public ProductRequestBuilder category(String category) {
            this.category = category;
            return this;
        }
        
        public ProductRequestBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }
        
        public ProductRequestBuilder isActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }
        
        public ProductRequest build() {
            return new ProductRequest(sku, name, category, price, isActive);
        }
    }
    
    // Getters and Setters
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
}