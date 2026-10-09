package com.voyagecraft.packageservice.service;

import com.voyagecraft.packageservice.entity.TravelPackage;
import com.voyagecraft.packageservice.exception.PackageNotFoundException;
import com.voyagecraft.packageservice.repository.PackageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageService {

    private final PackageRepository packageRepository;

    public PackageService(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

   
    public TravelPackage createPackage(TravelPackage travelPackage) {
        return packageRepository.save(travelPackage);
    }

 
    public List<TravelPackage> getAllPackages() {
        return packageRepository.findAll();
    }

  
    public TravelPackage getPackageById(Long id) {
        return packageRepository.findById(id)
                .orElseThrow(() -> new PackageNotFoundException(id));
    }

    public TravelPackage updatePackage(Long id, TravelPackage updatedPackage) {
        TravelPackage existingPackage = getPackageById(id);

        existingPackage.setName(updatedPackage.getName());
        existingPackage.setDescription(updatedPackage.getDescription());
        existingPackage.setPrice(updatedPackage.getPrice());
        existingPackage.setAvailableSeats(updatedPackage.getAvailableSeats());

        
        existingPackage.getDestinations().clear();
        if (updatedPackage.getDestinations() != null) {
            existingPackage.getDestinations().addAll(updatedPackage.getDestinations());
        }

        return packageRepository.save(existingPackage);
    }

    
    public void deletePackage(Long id) {
        TravelPackage existingPackage = getPackageById(id);
        packageRepository.delete(existingPackage);
    }
}