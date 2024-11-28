package com.dailycodebuffer.security.controller;

import com.dailycodebuffer.security.entity.Product;
import com.dailycodebuffer.security.exception.ProductNotFoundException;
import com.dailycodebuffer.security.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProduct() {
        // Arrange
        Product product = new Product(1, "Test Product", 100.0);
        when(productService.addProduct(product)).thenReturn(product);

        // Act
        Product result = productController.addProduct(product);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getProductId()).isEqualTo(1);
        verify(productService, times(1)).addProduct(product);
    }

    @Test
    void testGetProducts() {
        // Arrange
        List<Product> products = Arrays.asList(
                new Product(1, "Product 1", 100.0),
                new Product(2, "Product 2", 200.0)
        );
        when(productService.getAllProducts()).thenReturn(products);

        // Act
        List<Product> result = productController.getProducts();

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getProductName()).isEqualTo("Product 1");
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testGetProductById_Success() {
        // Arrange
        Product product = new Product(1, "Test Product", 100.0);
        when(productService.getProductById(1)).thenReturn(product);

        // Act
        ResponseEntity<?> response = productController.getProductById(1);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(product);
        verify(productService, times(1)).getProductById(1);
    }

    @Test
    void testGetProductById_NotFound() {
        // Arrange
        when(productService.getProductById(1)).thenThrow(new ProductNotFoundException("Product not found"));

        // Act
        ResponseEntity<?> response = productController.getProductById(1);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        assertThat(response.getBody()).isEqualTo("Product not found");
        verify(productService, times(1)).getProductById(1);
    }

    @Test
    void testUpdateProduct_Success() {
        // Arrange
        Product updatedProduct = new Product(1, "Updated Product", 150.0);
        when(productService.updateProduct(eq(1), any(Product.class))).thenReturn(updatedProduct);

        // Act
        ResponseEntity<?> response = productController.updateProduct(1, updatedProduct);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(updatedProduct);
        verify(productService, times(1)).updateProduct(eq(1), any(Product.class));
    }

    @Test
    void testUpdateProduct_NotFound() {
        // Arrange
        when(productService.updateProduct(eq(1), any(Product.class))).thenThrow(new ProductNotFoundException("Product not found"));

        // Act
        ResponseEntity<?> response = productController.updateProduct(1, new Product());

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        assertThat(response.getBody()).isEqualTo("Product not found");
        verify(productService, times(1)).updateProduct(eq(1), any(Product.class));
    }

    @Test
    void testDeleteProduct_Success() {
        // Act
        ResponseEntity<?> response = productController.deleteProduct(1);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Product deleted successfully.");
        verify(productService, times(1)).deleteProduct(1);
    }

    @Test
    void testDeleteProduct_NotFound() {
        // Arrange
        doThrow(new ProductNotFoundException("Product not found")).when(productService).deleteProduct(1);

        // Act
        ResponseEntity<?> response = productController.deleteProduct(1);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(404);
        assertThat(response.getBody()).isEqualTo("Product not found");
        verify(productService, times(1)).deleteProduct(1);
    }
}
