package com.example.backendservice.Repository;

import com.example.backendservice.Entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {
    
    @Query("SELECT c FROM Provider c WHERE c.name = ?1")
    Optional<Provider> findByName(String name);
    
    @Query("SELECT c FROM Provider c WHERE c.id = ?1")
    Optional<Provider> findById(Long id);
    
}
