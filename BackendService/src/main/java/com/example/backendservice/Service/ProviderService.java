package com.example.backendservice.Service;

import com.example.backendservice.DTO.ProviderDTO;
import com.example.backendservice.Entity.Provider;
import com.example.backendservice.Repository.ProductRepository;
import com.example.backendservice.Repository.ProviderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProviderService {
    
    private final ProviderRepository providerRepository;
    private final ProductRepository productRepository;
    
    public List<Provider> list() {
        List<Provider> list = providerRepository.findAll();
        for (Provider provider : list) {
            provider.setProducts(productRepository.getProductAmountByProvider(provider.getId()));
        }
        return list;
    }
    
    public Optional<Provider> findByName(String name) {
        Optional<Provider> provider = providerRepository.findByName(name);
        provider.ifPresent(value -> value.setProducts(productRepository.getProductAmountByProvider(value.getId())));
        return provider;
    }
    
    public Optional<Provider> findById(Long id) {
        Optional<Provider> provider = providerRepository.findById(id);
        provider.ifPresent(value -> value.setProducts(productRepository.getProductAmountByProvider(id)));
        return provider;
    }
    
    public Provider add(ProviderDTO providerDTO) {
        Provider provider = new Provider();
        provider.setName(providerDTO.getName());
        return providerRepository.save(provider);
    }
    
    public void deleteById(Long id) {
        providerRepository.deleteById(id);
    }
    
}
