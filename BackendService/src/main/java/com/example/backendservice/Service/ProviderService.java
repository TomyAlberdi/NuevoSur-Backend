package com.example.backendservice.Service;

import com.example.backendservice.DTO.ProviderDTO;
import com.example.backendservice.Entity.Provider;
import com.example.backendservice.Repository.ProviderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProviderService {
    
    private final ProviderRepository providerRepository;
    
    public List<Provider> list() {
        return providerRepository.findAll();
    }
    
    public Optional<Provider> findByName(String name) {
        return providerRepository.findByName(name);
    }
    
    public Optional<Provider> findById(Long id) {
        return providerRepository.findById(id);
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
