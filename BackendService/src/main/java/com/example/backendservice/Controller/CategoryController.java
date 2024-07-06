package com.example.backendservice.Controller;

import com.example.backendservice.DTO.CategoryDTO;
import com.example.backendservice.Entity.Category;
import com.example.backendservice.Service.CategoryService;
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
    
}
