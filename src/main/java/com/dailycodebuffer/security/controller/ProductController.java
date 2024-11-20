package com.dailycodebuffer.security.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dailycodebuffer.security.entity.Product;
import com.dailycodebuffer.security.exception.ProductNotFoundException;
import com.dailycodebuffer.security.service.ProductService;

/**
 * Controller class for managing product-related operations.
 * Provides endpoints to add and retrieve products.
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    /**
     * Adds a new product to the system.
     * 
     * @param product The product details to be added.
     * @return The added product object.
     */
    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product) {
        logger.info("Request received to add a new product: {}", product);
        Product addedProduct = productService.addProduct(product);
        logger.info("Product added successfully with ID: {}", addedProduct.getProductId());
        return addedProduct;
    }

    /**
     * Retrieves all products from the system.
     * 
     * @return A list of all products.
     */
    @GetMapping
    public List<Product> getProducts() {
        logger.info("Request received to fetch all products.");
        List<Product> products = productService.getAllProducts();
        logger.info("Total products fetched: {}", products.size());
        return products;
    }
    
    
    /**
     * Retrieves a product by its ID.
     * 
     * @param id The ID of the product to retrieve.
     * @return ResponseEntity containing the product if found or an appropriate error message.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id) {
        logger.info("Received request to fetch product with ID: {}", id);
        try {
            Product product = productService.getProductById(id);
            logger.info("Product successfully retrieved: {}", product);
            return ResponseEntity.ok(product);
        } catch (ProductNotFoundException ex) {
            logger.warn("ProductNotFoundException: {}", ex.getMessage());
            return ResponseEntity.status(404).body(ex.getMessage());
        } catch (Exception ex) {
            logger.error("Unexpected error occurred: {}", ex.getMessage());
            return ResponseEntity.status(500).body("An unexpected error occurred. Please try again later.");
        }
    }
    
    /**
     * Updates an existing product.
     * 
     * @param id The ID of the product to update.
     * @param updatedProduct The product object with updated details.
     * @return ResponseEntity containing the updated product or an appropriate error message.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody Product updatedProduct) {
        logger.info("Received request to update product with ID: {}", id);
        try {
            Product product = productService.updateProduct(id, updatedProduct);
            logger.info("Product successfully updated: {}", product);
            return ResponseEntity.ok(product);
        } catch (ProductNotFoundException ex) {
            logger.warn("ProductNotFoundException: {}", ex.getMessage());
            return ResponseEntity.status(404).body(ex.getMessage());
        } catch (Exception ex) {
            logger.error("Unexpected error occurred while updating product: {}", ex.getMessage());
            return ResponseEntity.status(500).body("An unexpected error occurred. Please try again later.");
        }
    }

    /**
     * Deletes a product by its ID.
     * 
     * @param id The ID of the product to delete.
     * @return ResponseEntity containing a success message or an appropriate error message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        logger.info("Received request to delete product with ID: {}", id);
        try {
            productService.deleteProduct(id);
            logger.info("Product successfully deleted with ID: {}", id);
            return ResponseEntity.ok("Product deleted successfully.");
        } catch (ProductNotFoundException ex) {
            logger.warn("ProductNotFoundException: {}", ex.getMessage());
            return ResponseEntity.status(404).body(ex.getMessage());
        } catch (Exception ex) {
            logger.error("Unexpected error occurred while deleting product: {}", ex.getMessage());
            return ResponseEntity.status(500).body("An unexpected error occurred. Please try again later.");
        }
    }

    
    
}
