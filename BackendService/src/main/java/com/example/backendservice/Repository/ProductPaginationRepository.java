package com.example.backendservice.Repository;

import com.example.backendservice.DTO.ProductCardDTO;
import com.example.backendservice.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPaginationRepository extends PagingAndSortingRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    
    @Query("SELECT new com.example.backendservice.DTO.ProductCardDTO(p.id, p.name, p.price, p.salesUnit, p.discount_percentage, p.discount_new_price, '') FROM Product p")
    Page<ProductCardDTO> getProductCards(Pageable pageable);
    
    @Query("SELECT p FROM Product p " +
            "LEFT JOIN Category c ON p.categoryId = c.id " +
            "LEFT JOIN Provider pr ON p.providerId = pr.id " +
            "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(pr.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR :keyword MEMBER OF p.tags")
    List<Product> searchProductsByKeyword(@Param("keyword") String keyword);
    
}
