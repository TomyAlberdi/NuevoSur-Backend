package com.example.backendservice.Repository;

import com.example.backendservice.Entity.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPaginationRepository extends PagingAndSortingRepository<Product, Long> {
    
}
