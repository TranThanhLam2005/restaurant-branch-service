package com.example.branch.service;

import com.example.branch.dto.CityRequest;
import com.example.branch.dto.CityResponse;
import com.example.branch.entity.City;
import com.example.branch.entity.State;
import com.example.branch.repository.CityRepository;
import com.example.branch.repository.StateRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CityService {
    
    private final CityRepository cityRepository;
    private final StateRepository stateRepository;
    
    @Transactional(readOnly = true)
    public List<CityResponse> getAllCities() {
        return cityRepository.findAll().stream()
                .map(CityResponse::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<CityResponse> getCitiesByStateId(Long stateId) {
        return cityRepository.findByStateId(stateId).stream()
                .map(CityResponse::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public CityResponse getCityById(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("City not found with id: " + id));
        return CityResponse.fromEntity(city);
    }
    
    public CityResponse createCity(CityRequest request) {
        State state = stateRepository.findById(request.getStateId())
                .orElseThrow(() -> new EntityNotFoundException("State not found with id: " + request.getStateId()));
        
        City city = new City();
        city.setName(request.getName());
        city.setState(state);
        
        City savedCity = cityRepository.save(city);
        return CityResponse.fromEntity(savedCity);
    }
    
    public void deleteCity(Long id) {
        if (!cityRepository.existsById(id)) {
            throw new EntityNotFoundException("City not found with id: " + id);
        }
        cityRepository.deleteById(id);
    }
}
