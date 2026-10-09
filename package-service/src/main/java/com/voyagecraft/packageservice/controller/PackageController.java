package com.voyagecraft.packageservice.controller;

import com.voyagecraft.packageservice.entity.TravelPackage;
import com.voyagecraft.packageservice.service.PackageService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/packages")
@CrossOrigin(origins = "*")
public class PackageController {

    private final PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @PostMapping
    public ResponseEntity<TravelPackage> createPackage(
            @Valid @RequestBody TravelPackage travelPackage) {

        TravelPackage savedPackage =
                packageService.createPackage(
                        travelPackage
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedPackage);
    }

    @GetMapping
    public ResponseEntity<List<TravelPackage>>
    getAllPackages() {

        return ResponseEntity.ok(
                packageService.getAllPackages()
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<TravelPackage>
    getPackageById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                packageService.getPackageById(id)
        );
    }

    // ==============================
    // UPDATE
    // ==============================

    @PutMapping("/{id}")
    public ResponseEntity<TravelPackage>
    updatePackage(
            @PathVariable Long id,
            @Valid @RequestBody TravelPackage travelPackage) {

        return ResponseEntity.ok(
                packageService.updatePackage(
                        id,
                        travelPackage
                )
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    deletePackage(
            @PathVariable Long id) {

        packageService.deletePackage(id);

        return ResponseEntity.ok(
                "Package deleted successfully"
        );
    }
}