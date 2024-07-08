package com.example.backendservice.Service;

import com.example.backendservice.DTO.ProductDTO;
import com.example.backendservice.Entity.Category;
import com.example.backendservice.Entity.Product;
import com.example.backendservice.Entity.Provider;
import com.example.backendservice.Repository.CategoryRepository;
import com.example.backendservice.Repository.ProductPaginationRepository;
import com.example.backendservice.Repository.ProductRepository;
import com.example.backendservice.Repository.ProviderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    
    private final ProductPaginationRepository productPaginationRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProviderRepository providerRepository;
    
    private ProductDTO convertDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setDiscountPercentage(product.getDiscount_percentage());
        productDTO.setDiscountNewPrice(product.getDiscount_new_price());
        productDTO.setCode(product.getCode());
        productDTO.setMeasures(product.getMeasures());
        productDTO.setM2PerBox(product.getM2PerBox());
        productDTO.setPriceUnit(product.getPriceUnit());
        productDTO.setSalesUnit(product.getSalesUnit());
        productDTO.setPrice(product.getPrice());
        productDTO.setQuality(product.getQuality());
        
        Optional<Category> category = categoryRepository.findById(product.getCategoryId());
        if (category.isPresent()) {
            productDTO.setCategory(category.get().getName());
        } else {
            productDTO.setCategory(null);
        }
        
        Optional<Provider> provider = providerRepository.findById(product.getProviderId());
        if (provider.isPresent()) {
            productDTO.setProvider(provider.get().getName());
        } else {
            productDTO.setProvider(null);
        }
        
        Optional<List<String>> images = this.getProductImages(product.getId());
        if (images.isPresent()) {
            productDTO.setImages(images.get());
        } else {
            productDTO.setImages(null);
        }
    
        Optional<List<String>> tags = this.getProductTags(product.getId());
        if (tags.isPresent()) {
            productDTO.setTags(tags.get());
        } else {
            productDTO.setTags(null);
        }
        
        return productDTO;
        
    }
    
    public Page<ProductDTO> getPaginatedProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productPaginationRepository.findAll(pageable);
        
        List<ProductDTO> productDTOS = productPage.getContent().stream().map(this::convertDTO).collect(Collectors.toList());
        
        return new PageImpl<>(productDTOS, pageable, productPage.getTotalElements());
        
    }
    
    public Product add(Product product) {
        return productRepository.save(product);
    }
    
    public Optional<List<String>> getProductTags(Long productId) {
        return productPaginationRepository.findById(productId).map(Product::getTags);
    }
    
    public Optional<List<String>> getProductImages(Long productId) {
        return productPaginationRepository.findById(productId).map(Product::getImages);
    }
    
}
