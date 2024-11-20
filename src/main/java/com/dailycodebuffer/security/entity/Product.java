package com.dailycodebuffer.security.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Entity class representing a Product.
 * Maps to the product table in the database.
 * 
 * Author: Srini Battineni
 */
@Entity
public class Product {

    @Id
    private Integer productId; // Unique identifier for the product
    private String productName; // Name of the product
    private Double price; // Price of the product

    // No-argument constructor required by JPA
    public Product() {
        // Default constructor
    }

    // Parameterized constructor
    public Product(Integer productId, String productName, Double price) {
        super();
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
