package com.example.backendservice.Controller;

import com.example.backendservice.DTO.CategoryDTO;
import com.example.backendservice.Entity.Category;
import com.example.backendservice.Service.CategoryService;
import com.example.backendservice.Service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RestControllerAdvice
@Validated
@RequestMapping("/category")
public class CategoryController {
    
    private final CategoryService categoryService;
    private final ProductService productService;
    
    public ResponseEntity<?> notFound(Long code) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category with ID " + code + " not found.");
    }
    
    @GetMapping("/list")
    public List<Category> list() {
        return categoryService.list();
    }
    
    @GetMapping("/get/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name) {
        Optional<Category> category = categoryService.findByName(name);
        return category.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category " + name + " not found.")
                : ResponseEntity.ok(category);
    }
    
    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody CategoryDTO categoryDTO) {
        String newName = categoryDTO.getName();
        Optional<Category> repeatedCategory = categoryService.findByName(newName);
        if (repeatedCategory.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category " + newName + " already exists.");
        }
        Category category = categoryService.add(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Category created");
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<Category> search = categoryService.findById(id);
        if (search.isPresent()) {
            Optional<List<Long>> listProducts = productService.getIdByCategory(id);
            if (listProducts.isPresent() && listProducts.get().size() > 0) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Unable to delete Category. Products with Category ID " + id + " found ( IDs: " + listProducts.get() + " )");
            }
            categoryService.deleteById(id);
            return ResponseEntity.ok("Category with ID " + id + " deleted.");
        }
        return notFound(id);
    }
    
}
