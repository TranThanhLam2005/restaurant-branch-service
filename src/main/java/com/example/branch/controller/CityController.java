package com.example.branch.controller;

import com.example.branch.dto.CityRequest;
import com.example.branch.dto.CityResponse;
import com.example.branch.service.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController {
    
    private final CityService cityService;
    
    @GetMapping
    public ResponseEntity<List<CityResponse>> getAllCities() {
        List<CityResponse> cities = cityService.getAllCities();
        return ResponseEntity.ok(cities);
    }
    
    @GetMapping("/by-state/{stateId}")
    public ResponseEntity<List<CityResponse>> getCitiesByStateId(@PathVariable Long stateId) {
        List<CityResponse> cities = cityService.getCitiesByStateId(stateId);
        return ResponseEntity.ok(cities);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CityResponse> getCityById(@PathVariable Long id) {
        CityResponse city = cityService.getCityById(id);
        return ResponseEntity.ok(city);
    }
    
    @PostMapping
    public ResponseEntity<CityResponse> createCity(@Valid @RequestBody CityRequest request) {
        CityResponse createdCity = cityService.createCity(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCity);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }
}
