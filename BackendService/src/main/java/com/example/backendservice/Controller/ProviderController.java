package com.example.backendservice.Controller;

import com.example.backendservice.DTO.ProviderDTO;
import com.example.backendservice.Entity.Provider;
import com.example.backendservice.Service.ProductService;
import com.example.backendservice.Service.ProviderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RestControllerAdvice
@Validated
@RequestMapping("/provider")
public class ProviderController {
    
    private final ProviderService providerService;
    private final ProductService productService;
    
    public ResponseEntity<?> notFound(Long code) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Provider with ID " + code + " not found.");
    }
    
    @GetMapping("/list")
    public List<Provider> list() {
        return providerService.list();
    }
    
    @GetMapping("/get/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name) {
        Optional<Provider> provider = providerService.findByName(name);
        return provider.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Provider " + name + " not found.")
                : ResponseEntity.ok(provider);
    }
    
    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> add(@Valid @RequestBody ProviderDTO providerDTO) {
        String newName = providerDTO.getName();
        Optional<Provider> repeatedProvider = providerService.findByName(newName);
        if (repeatedProvider.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Provider " + newName + " already exists.");
        }
        Provider provider = providerService.add(providerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Provider created");
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        Optional<Provider> search = providerService.findById(id);
        if (search.isPresent()) {
            Optional<List<Long>> listProducts = productService.getIdByProvider(id);
            if (listProducts.isPresent() && listProducts.get().size() > 0) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Unable to delete Provider. Products with Provider ID " + id + " found ( IDs: " + listProducts.get() + " )");
            }
            providerService.deleteById(id);
            return ResponseEntity.ok("Provider with ID " + id + " deleted.");
        }
        return notFound(id);
    }
    
}
