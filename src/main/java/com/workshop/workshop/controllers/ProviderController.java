
package com.workshop.workshop.controllers;

import com.workshop.workshop.entity.Provider;
import com.workshop.workshop.repositories.ProviderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:4200")

@RequestMapping("/api/providers")
public class ProviderController {

    @Autowired
    private ProviderRepo providerRepo;

    // Get all providers
    @GetMapping("/list")
    public List<Provider> getAllProviders() {
        return providerRepo.findAll();
    }

    // Get provider by ID
    @GetMapping("/{id}")
    public Optional<Provider> getProviderById(@PathVariable String id) {
        return providerRepo.findById(id);
    }

    // Create a new provider
    @PostMapping("/add")
    public Provider createProvider(@RequestBody Provider provider) {
        return providerRepo.save(provider);
    }

    // Update an existing provider
    @PutMapping("/{id}")
    public Provider updateProvider(@PathVariable String id, @RequestBody Provider providerDetails) {
        return providerRepo.findById(id)
                .map(provider -> {
                    provider.setName(providerDetails.getName());
                    provider.setAddress(providerDetails.getAddress());
                    provider.setEmail(providerDetails.getEmail());
                    return providerRepo.save(provider);
                }).orElse(null);
    }

    // Delete a provider
    @DeleteMapping("/{id}")
    public void deleteProvider(@PathVariable String id) {
        providerRepo.deleteById(id);
    }
}
