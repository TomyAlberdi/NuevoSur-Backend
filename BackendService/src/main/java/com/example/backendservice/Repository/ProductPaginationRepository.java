package com.example.backendservice.Repository;

import com.example.backendservice.Entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductPaginationRepository extends PagingAndSortingRepository<Product, Long> {
    
    @Query("SELECT c FROM Product c WHERE c.id = ?1")
    Optional<Product> findById(Long id);

}
