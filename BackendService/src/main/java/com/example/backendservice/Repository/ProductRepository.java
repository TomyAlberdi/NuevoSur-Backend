package com.example.backendservice.Repository;

import com.example.backendservice.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    @Query("SELECT p.id FROM Product p WHERE p.categoryId = ?1")
    Optional<List<Long>> getIdByCategory(Long id);
    
    @Query("SELECT p.id FROM Product p WHERE p.providerId = ?1")
    Optional<List<Long>> getIdByProvider(Long id);
    
    @Modifying
    @Transactional
    @Query("UPDATE Product SET " +
            "name = ?1, " +
            "description = ?2, " +
            "categoryId = ?3, " +
            "providerId = ?4, " +
            "discount_percentage = ?5, " +
            "discount_new_price = ?6, " +
            "measures = ?7, " +
            "m2PerBox = ?8, " +
            "priceUnit = ?9, " +
            "salesUnit = ?10, " +
            "price = ?11, " +
            "quality = ?12 " +
            "WHERE id = ?13")
    void updateByCode(String name, String description, Long categoryId, Long providerId, Integer discount_percentage,
                      Double discount_new_price, String measures, Double m2PerBox, String priceUnit,
                      String salesUnit, Double price, String quality, Long id);
    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM product_images WHERE product_id = ?1", nativeQuery = true)
    void deleteImagesById(Long code);
    
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO product_images (product_id, images) VALUES (?2, ?1)", nativeQuery = true)
    void insertImageById(String image, Long id);
    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM product_tags WHERE product_id = ?1", nativeQuery = true)
    void deleteTagsById(Long code);
    
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO product_tags (product_id, tags) VALUES (?2, ?1)", nativeQuery = true)
    void insertTagById(String tag, Long id);
    
}
