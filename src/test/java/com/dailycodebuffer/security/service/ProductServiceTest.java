package com.dailycodebuffer.security.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.dailycodebuffer.security.entity.Product;
import com.dailycodebuffer.security.exception.ProductNotFoundException;
import com.dailycodebuffer.security.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProduct_Success() {
        Product product = new Product(1, "Product1", 100.0);
        when(productRepository.save(product)).thenReturn(product);

        Product savedProduct = productService.addProduct(product);

        assertNotNull(savedProduct);
        assertEquals(1, savedProduct.getProductId());
        assertEquals("Product1", savedProduct.getProductName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testAddProduct_Exception() {
        Product product = new Product(1, "Product1", 100.0);
        when(productRepository.save(product)).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class, () -> productService.addProduct(product));

        assertEquals("Failed to add product. Please try again later.", exception.getMessage());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testGetAllProducts_Success() {
        List<Product> products = Arrays.asList(
                new Product(1, "Product1", 100.0),
                new Product(2, "Product2", 200.0)
        );
        when(productRepository.findAll()).thenReturn(products);

        List<Product> retrievedProducts = productService.getAllProducts();

        assertEquals(2, retrievedProducts.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById_Success() {
        Product product = new Product(1, "Product1", 100.0);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Product retrievedProduct = productService.getProductById(1);

        assertNotNull(retrievedProduct);
        assertEquals("Product1", retrievedProduct.getProductName());
        verify(productRepository, times(1)).findById(1);
    }

   
    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> productService.getProductById(1));

        assertEquals("Failed to fetch product. Please try again later.", exception.getMessage());
        verify(productRepository, times(1)).findById(1);
    }


    @Test
    void testUpdateProduct_Success() {
        Product existingProduct = new Product(1, "OldProduct", 50.0);
        Product updatedProduct = new Product(1, "NewProduct", 100.0);

        when(productRepository.findById(1)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(updatedProduct);

        Product result = productService.updateProduct(1, updatedProduct);

        assertNotNull(result);
        assertEquals("NewProduct", result.getProductName());
        assertEquals(100.0, result.getPrice());
        verify(productRepository, times(1)).findById(1);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    void testUpdateProduct_NotFound() {
        Product updatedProduct = new Product(1, "NewProduct", 100.0);
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> productService.updateProduct(1, updatedProduct));

        assertEquals("Failed to update product. Please try again later.", exception.getMessage());
        verify(productRepository, times(1)).findById(1);
        verify(productRepository, never()).save(any(Product.class));
    }


    @Test
    void testDeleteProduct_Success() {
        Product product = new Product(1, "Product1", 100.0);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);

        assertDoesNotThrow(() -> productService.deleteProduct(1));

        verify(productRepository, times(1)).findById(1);
        verify(productRepository, times(1)).delete(product);
    }


    @Test
    void testDeleteProduct_NotFound() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> productService.deleteProduct(1));

        assertEquals("Failed to delete product. Please try again later.", exception.getMessage());
        verify(productRepository, times(1)).findById(1);
    }

}
