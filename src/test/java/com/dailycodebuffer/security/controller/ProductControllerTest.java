package com.dailycodebuffer.security.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.dailycodebuffer.security.entity.Product;
import com.dailycodebuffer.security.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
//@ExtendWith(MockitoExtension.class)

//@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ProductController productController;  

    
    @Test
    //@WithMockUser(username = "testuser", roles = "USER") 
    void testAddProduct() throws Exception {
    	
    	Product product = new Product();
        
        product.setProductId(1);
        product.setProductName("product Name");
        product.setPrice(100.0);
        // Arrange: Mock the behavior of the productService
        when(productService.addProduct(Mockito.any(Product.class))).thenReturn(product);

        Product product2 = new Product();
        
        product2.setProductId(1);
        product2.setProductName("product Name");
        product2.setPrice(100.0);
        
        String content = (new ObjectMapper()).writeValueAsString(product2);
        MockHttpServletRequestBuilder requestBuilder =MockMvcRequestBuilders.post("/product/add")
        		.contentType(MediaType.APPLICATION_JSON).content(content);
        
        MockMvcBuilders.standaloneSetup(productController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
		.andExpect(
				MockMvcResultMatchers.content().string("{\"productId\":1,\"productName\":\"product Name\",\"price\":100.0}"));
    }
    
    @Test
     void testGetProducts() throws Exception {
//        // Arrange
//        List<Product> products = Arrays.asList(
//                new Product(1, "Product1", 50.0),
//                new Product(2, "Product2", 100.0)
//        );
        when(productService.getAllProducts()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder =MockMvcRequestBuilders.get("/");
        
        MockMvcBuilders.standaloneSetup(productController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
		.andExpect(
				MockMvcResultMatchers.content().string("[]"));
    }
//        // Assert
//        List<Product> response = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), Product[].class));
//        assertEquals(2, response.size());
//        assertEquals("Product1", response.get(0).getProductName());
//        verify(productService, times(1)).getAllProducts();
//    }
    
       /* // Act: Perform POST request
        mockMvc.perform(post("/product/add")
                .contentType("application/json")
                .content("{\"productId\":1,\"name\":\"Test Product\",\"price\":100.0}"))
                .andExpect(status().isOk()) // Assert: Status 200 OK
                .andExpect(jsonPath("$.productId").value(1)) // Check if productId is returned as 1
                .andExpect(jsonPath("$.setProductName").value("Test Product")) // Check if name is returned correctly
                .andExpect(jsonPath("$.price").value(100.0)); // Check if price is returned correctly

        // Verify that the productService.addProduct method was called once
        verify(productService, times(1)).addProduct(any(Product.class));
    }*/
}


/*import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.dailycodebuffer.security.entity.Product;
import com.dailycodebuffer.security.exception.ProductNotFoundException;
import com.dailycodebuffer.security.service.JwtService;
import com.dailycodebuffer.security.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @MockBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Autowired
    private ObjectMapper objectMapper; // Converts objects to/from JSON


//    @Test
//    public void testAddProduct() throws Exception {
//        // Mock a CSRF token
//        CsrfToken csrfToken = new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", "dummy-csrf-token");
//
//        mockMvc.perform(post("/product/add")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"productId\":null,\"productName\":\"TestProduct\",\"price\":100}")
//                .with(SecurityMockMvcRequestPostProcessors.csrf()))
//                .andExpect(status().isOk()); // Adjust expected status
//    }
    @Test
    public void testGetProducts() throws Exception {
        // Arrange
        List<Product> products = Arrays.asList(
                new Product(1, "Product1", 50.0),
                new Product(2, "Product2", 100.0)
        );
        when(productService.getAllProducts()).thenReturn(products);

        // Act
        MvcResult result = mockMvc.perform(get("/product")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        List<Product> response = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), Product[].class));
        assertEquals(2, response.size());
        assertEquals("Product1", response.get(0).getProductName());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void testGetProductByIdSuccess() throws Exception {
        // Arrange
        Product product = new Product(1, "Test Product", 100.0);
        when(productService.getProductById(1)).thenReturn(product);

        // Act
        MvcResult result = mockMvc.perform(get("/product/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        Product response = objectMapper.readValue(result.getResponse().getContentAsString(), Product.class);
        assertEquals(1, response.getProductId());
        assertEquals("Test Product", response.getProductName());
        verify(productService, times(1)).getProductById(1);
    }

    @Test
    public void testGetProductByIdNotFound() throws Exception {
        // Arrange
        when(productService.getProductById(1)).thenThrow(new ProductNotFoundException("Product not found"));

        // Act
        MvcResult result = mockMvc.perform(get("/product/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        // Assert
        assertEquals("Product not found", result.getResponse().getContentAsString());
        verify(productService, times(1)).getProductById(1);
    }

    @Test
    public void testUpdateProduct() throws Exception {
        // Arrange
        Product updatedProduct = new Product(1, "Updated Product", 150.0);
        when(productService.updateProduct(eq(1), any(Product.class))).thenReturn(updatedProduct);

        // Act
        MvcResult result = mockMvc.perform(put("/product/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        Product response = objectMapper.readValue(result.getResponse().getContentAsString(), Product.class);
        assertEquals("Updated Product", response.getProductName());
        assertEquals(150.0, response.getPrice());
        verify(productService, times(1)).updateProduct(eq(1), any(Product.class));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        // Act
        MvcResult result = mockMvc.perform(delete("/product/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        assertEquals("Product deleted successfully.", result.getResponse().getContentAsString());
        verify(productService, times(1)).deleteProduct(1);
    }
}*/
