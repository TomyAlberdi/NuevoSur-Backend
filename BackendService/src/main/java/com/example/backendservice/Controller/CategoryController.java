package com.example.backendservice.Controller;

import com.example.backendservice.Service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@AllArgsConstructor
@RestController
@RestControllerAdvice
@Validated
@RequestMapping("/category")
public class CategoryController {
    
    private final CategoryService categoryService;
    
}
