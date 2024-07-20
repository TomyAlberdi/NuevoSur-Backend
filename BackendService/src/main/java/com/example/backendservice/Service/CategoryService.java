package com.example.backendservice.Service;

import com.example.backendservice.DTO.CategoryDTO;
import com.example.backendservice.Entity.Category;
import com.example.backendservice.Repository.CategoryRepository;
import com.example.backendservice.Repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    
    public List<Category> list() {
        List<Category> list = categoryRepository.findAll();
        for (Category category : list) {
            category.setProducts(productRepository.getProductAmountByCategory(category.getId()));
        }
        return list;
    }
    
    public Optional<Category> findByName(String name) {
        Optional<Category> category = categoryRepository.findByName(name);
        category.ifPresent(value -> value.setProducts(productRepository.getProductAmountByCategory(value.getId())));
        return category;
    }
    
    public Optional<Category> findById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        category.ifPresent(value -> value.setProducts(productRepository.getProductAmountByCategory(id)));
        return category;
    }
    
    public Category add(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        return categoryRepository.save(category);
    }
    
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
    
}
