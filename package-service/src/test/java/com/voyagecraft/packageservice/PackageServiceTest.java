package com.voyagecraft.packageservice;

import com.voyagecraft.packageservice.entity.Destination;
import com.voyagecraft.packageservice.entity.TravelPackage;
import com.voyagecraft.packageservice.exception.PackageNotFoundException;
import com.voyagecraft.packageservice.repository.PackageRepository;
import com.voyagecraft.packageservice.service.PackageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PackageServiceTest {

    @Mock
    private PackageRepository packageRepository;

    @InjectMocks
    private PackageService packageService;

    private TravelPackage samplePackage;

    @BeforeEach
    void setUp() {
        List<Destination> destinations = new ArrayList<>();
        destinations.add(new Destination(1L, "Panaji", "India"));
        destinations.add(new Destination(2L, "Calangute", "India"));

        samplePackage = new TravelPackage(
                1L,
                "Goa Beach Escape",
                "3 days and 2 nights beach vacation",
                15000.0,
                20,
                destinations
        );
    }

    @Test
    void testCreatePackage() {
        when(packageRepository.save(any(TravelPackage.class))).thenReturn(samplePackage);

        TravelPackage created = packageService.createPackage(samplePackage);

        assertNotNull(created);
        assertEquals("Goa Beach Escape", created.getName());
        assertEquals(15000.0, created.getPrice());
        assertEquals(2, created.getDestinations().size());
        verify(packageRepository, times(1)).save(samplePackage);
    }

    @Test
    void testGetAllPackages() {
        when(packageRepository.findAll()).thenReturn(Arrays.asList(samplePackage));

        List<TravelPackage> list = packageService.getAllPackages();

        assertNotNull(list);
        assertEquals(1, list.size());
        assertEquals("Goa Beach Escape", list.get(0).getName());
        verify(packageRepository, times(1)).findAll();
    }

    @Test
    void testGetPackageById_Success() {
        when(packageRepository.findById(1L)).thenReturn(Optional.of(samplePackage));

        TravelPackage found = packageService.getPackageById(1L);

        assertNotNull(found);
        assertEquals(1L, found.getId());
        assertEquals(2, found.getDestinations().size());
        assertEquals("Panaji", found.getDestinations().get(0).getCity());
        verify(packageRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPackageById_NotFound() {
        when(packageRepository.findById(99L)).thenReturn(Optional.empty());

        PackageNotFoundException exception = assertThrows(
                PackageNotFoundException.class,
                () -> packageService.getPackageById(99L)
        );

        assertEquals("Travel package not found with id: 99", exception.getMessage());
        verify(packageRepository, times(1)).findById(99L);
    }

    @Test
    void testUpdatePackage_Success() {
        List<Destination> updatedDestinations = new ArrayList<>();
        updatedDestinations.add(new Destination("Vagator", "India"));

        TravelPackage updatedDetails = new TravelPackage(
                "Goa Premium Escape",
                "4 days and 3 nights premium vacation",
                20000.0,
                15,
                updatedDestinations
        );

        when(packageRepository.findById(1L)).thenReturn(Optional.of(samplePackage));
        when(packageRepository.save(any(TravelPackage.class))).thenReturn(samplePackage);

        TravelPackage result = packageService.updatePackage(1L, updatedDetails);

        assertNotNull(result);
        assertEquals("Goa Premium Escape", result.getName());
        assertEquals(20000.0, result.getPrice());
        assertEquals(15, result.getAvailableSeats());
        verify(packageRepository, times(1)).save(samplePackage);
    }

    @Test
    void testDeletePackage_Success() {
        when(packageRepository.findById(1L)).thenReturn(Optional.of(samplePackage));
        doNothing().when(packageRepository).delete(samplePackage);

        assertDoesNotThrow(() -> packageService.deletePackage(1L));

        verify(packageRepository, times(1)).delete(samplePackage);
    }
}
