package com.example.backendservice.Controller;

import com.example.backendservice.DTO.ProductDTO;
import com.example.backendservice.Entity.Category;
import com.example.backendservice.Entity.Product;
import com.example.backendservice.Entity.Provider;
import com.example.backendservice.Service.CategoryService;
import com.example.backendservice.Service.ProductService;
import com.example.backendservice.Service.ProviderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RestControllerAdvice
@Validated
@RequestMapping("/product")
public class ProductController {
    
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProviderService providerService;
    
    @GetMapping("/list")
    public Page<ProductDTO> list(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size) {
            return productService.getPaginatedProducts(page, size);
    }
    
    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody Product product) {
    
        Optional<Category> category = categoryService.findById(product.getCategoryId());
        Optional<Provider> provider = providerService.findById(product.getProviderId());
        
        if (category.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category with ID " + product.getCategoryId() + " not found.");
        }
        if (provider.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Provider with ID " + product.getProviderId() + " not found.");
        }
        
        Product newProduct = productService.add(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product Created");
        
    }
    
}
