package com.dailycodebuffer.security.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dailycodebuffer.security.entity.Product;
import com.dailycodebuffer.security.repository.ProductRepository;
import com.dailycodebuffer.security.exception.ProductNotFoundException;

/**
 * Service class for handling product-related business logic.
 * 
 * Author: Srinivas Battineni
 */
@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    /**
     * Adds a new product to the repository.
     * 
     * @param product The product to be added.
     * @return The saved product.
     */
    public Product addProduct(Product product) {
        try {
            logger.info("Attempting to add product: {}", product);
            Product savedProduct = productRepository.save(product);
            logger.info("Product successfully added with ID: {}", savedProduct.getProductId());
            return savedProduct;
        } catch (Exception ex) {
            logger.error("Error while adding product: {}", ex.getMessage());
            throw new RuntimeException("Failed to add product. Please try again later.");
        }
    }

    /**
     * Retrieves all products from the repository.
     * 
     * @return A list of all available products.
     */
    public List<Product> getAllProducts() {
        try {
            logger.info("Fetching all products from the repository.");
            List<Product> products = productRepository.findAll();
            logger.info("Total number of products retrieved: {}", products.size());
            return products;
        } catch (Exception ex) {
            logger.error("Error while fetching products: {}", ex.getMessage());
            throw new RuntimeException("Failed to fetch products. Please try again later.");
        }
    }

    /**
     * Retrieves a product by its ID.
     * 
     * @param id The ID of the product to retrieve.
     * @return The retrieved product.
     * @throws ProductNotFoundException if the product is not found.
     */
    public Product getProductById(Integer id) {
        try {
            logger.info("Fetching product with ID: {}", id);
            Optional<Product> product = productRepository.findById(id);
            if (product.isPresent()) {
                logger.info("Product retrieved successfully: {}", product.get());
                return product.get();
            } else {
                logger.warn("Product not found with ID: {}", id);
                throw new ProductNotFoundException("Product not found with ID: " + id);
            }
        } catch (Exception ex) {
            logger.error("Error while fetching product with ID {}: {}", id, ex.getMessage());
            throw new RuntimeException("Failed to fetch product. Please try again later.");
        }
    }
    
    
    /**
     * Updates an existing product in the repository.
     * 
     * @param productId The ID of the product to update.
     * @param updatedProduct The product object with updated details.
     * @return The updated product.
     * @throws ProductNotFoundException if the product does not exist.
     */
    public Product updateProduct(Integer productId, Product updatedProduct) {
        try {
            logger.info("Attempting to update product with ID: {}", productId);
            Product existingProduct = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));
            
            // Update the fields of the existing product
            existingProduct.setProductName(updatedProduct.getProductName());
            existingProduct.setPrice(updatedProduct.getPrice());
            
            Product savedProduct = productRepository.save(existingProduct);
            logger.info("Product successfully updated: {}", savedProduct);
            return savedProduct;
        } catch (Exception ex) {
            logger.error("Error while updating product with ID {}: {}", productId, ex.getMessage());
            throw new RuntimeException("Failed to update product. Please try again later.");
        }
    }

    /**
     * Deletes a product by its ID.
     * 
     * @param productId The ID of the product to delete.
     * @throws ProductNotFoundException if the product does not exist.
     */
    public void deleteProduct(Integer productId) {
        try {
            logger.info("Attempting to delete product with ID: {}", productId);
            Product existingProduct = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));
            
            productRepository.delete(existingProduct);
            logger.info("Product successfully deleted with ID: {}", productId);
        } catch (Exception ex) {
            logger.error("Error while deleting product with ID {}: {}", productId, ex.getMessage());
            throw new RuntimeException("Failed to delete product. Please try again later.");
        }
    }

}
