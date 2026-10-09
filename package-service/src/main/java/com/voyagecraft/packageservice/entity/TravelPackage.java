package com.voyagecraft.packageservice.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "travel_packages")
public class TravelPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Package name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Description is required")
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price cannot be negative")
    @Column(nullable = false)
    private Double price;

    @NotNull(message = "Available seats are required")
    @Min(value = 0, message = "Available seats cannot be negative")
    @Column(nullable = false)
    private Integer availableSeats;

    @Valid
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "package_id")
    private List<Destination> destinations = new ArrayList<>();

    public TravelPackage() {
    }

    public TravelPackage(String name, String description, Double price, Integer availableSeats, List<Destination> destinations) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableSeats = availableSeats;
        if (destinations != null) {
            this.destinations = destinations;
        }
    }

    public TravelPackage(Long id, String name, String description, Double price, Integer availableSeats, List<Destination> destinations) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableSeats = availableSeats;
        if (destinations != null) {
            this.destinations = destinations;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public List<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<Destination> destinations) {
        this.destinations = destinations != null ? destinations : new ArrayList<>();
    }

    public void addDestination(Destination destination) {
        if (destination != null) {
            this.destinations.add(destination);
        }
    }

    public void removeDestination(Destination destination) {
        this.destinations.remove(destination);
    }
}