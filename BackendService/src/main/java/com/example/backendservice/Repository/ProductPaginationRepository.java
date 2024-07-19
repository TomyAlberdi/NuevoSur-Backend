package com.example.backendservice.Repository;

import com.example.backendservice.DTO.ProductCardDTO;
import com.example.backendservice.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPaginationRepository extends PagingAndSortingRepository<Product, Long> {
    
    @Query("SELECT new com.example.backendservice.DTO.ProductCardDTO(p.id, p.name, p.price, p.salesUnit, p.discount_percentage, p.discount_new_price, '') FROM Product p")
    Page<ProductCardDTO> getProductCards(Pageable pageable);
    
}
