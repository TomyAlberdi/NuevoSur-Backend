package com.example.backendservice.Service;

import com.example.backendservice.DTO.ProductCardDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    
    public ResponseEntity<?> searchKeys(Long categoryID, Long providerID) {
    
        Optional<Category> category = categoryRepository.findById(categoryID);
        Optional<Provider> provider = providerRepository.findById(providerID);
    
        if (category.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category with ID " + categoryID + " not found.");
        }
        if (provider.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Provider with ID " + providerID + " not found.");
        }
        
        return ResponseEntity.status(HttpStatus.FOUND).body("");
        
    }
    
    public Page<ProductCardDTO> getPaginatedProductsCard(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductCardDTO> productDTOPage = productPaginationRepository.getProductCards(pageable);
        
        List<ProductCardDTO> productCardDTOList = productDTOPage.getContent().stream().toList();
        
        for (ProductCardDTO productCardDTO : productCardDTOList) {
            Optional<List<String>> images = this.getProductImages(productCardDTO.getId());
            images.ifPresent(strings -> productCardDTO.setImage(strings.get(0)));
        }
        return new PageImpl<>(productCardDTOList, pageable, productDTOPage.getTotalElements());
    }
    
    public Product add(Product product) {
        return productRepository.save(product);
    }
    
    public Optional<ProductDTO> getById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(this::convertDTO);
    }
    
    public Optional<Product> getCompleteById(Long id) {
        return productRepository.findById(id);
    }
    
    public Optional<List<Long>> getIdByCategory(Long id) {
        return productRepository.getIdByCategory(id);
    }
    
    public Optional<List<Long>> getIdByProvider(Long id) {
        return productRepository.getIdByProvider(id);
    }
    
    public void deleteByCode(Long id) {
        productRepository.deleteById(id);
    }
    
    public void updateProduct(Product product) {
        // Deletes images with updated product ID
        productRepository.deleteImagesById(product.getId());
        // Deletes tags with updated product ID
        productRepository.deleteTagsById(product.getId());
        // Update the rest of the product fields
        productRepository.updateByCode(product.getName(), product.getDescription(), product.getCategoryId(), product.getProviderId(), product.getDiscount_percentage(), product.getDiscount_new_price(), product.getMeasures(), product.getM2PerBox(), product.getPriceUnit(), product.getSalesUnit(), product.getPrice(), product.getQuality(), product.getId());
        // Insert all new tags
        for (String tag : product.getTags()) {
            productRepository.insertTagById(tag, product.getId());
        }
        // Insert all new images
        for (String image : product.getImages()) {
            productRepository.insertImageById(image, product.getId());
        }
    }
    
    public Optional<List<String>> getProductTags(Long productId) {
        return productRepository.findById(productId).map(Product::getTags);
    }
    
    public Optional<List<String>> getProductImages(Long productId) {
        return productRepository.findById(productId).map(Product::getImages);
    }
    
}
