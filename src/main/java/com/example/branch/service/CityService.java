package com.example.branch.service;

import com.example.branch.dto.CityDTO;
import com.example.branch.entity.City;
import com.example.branch.entity.State;
import com.example.branch.repository.CityRepository;
import com.example.branch.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CityService {
    
    private final CityRepository cityRepository;
    private final StateRepository stateRepository;
    
    public List<CityDTO> getAllCities() {
        return cityRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<CityDTO> getCitiesByStateId(Long stateId) {
        return cityRepository.findByStateId(stateId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public Optional<CityDTO> getCityById(Long id) {
        return cityRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    @Transactional
    public CityDTO createCity(CityDTO cityDTO) {
        State state = stateRepository.findById(cityDTO.getStateId())
                .orElseThrow(() -> new RuntimeException("State not found with id: " + cityDTO.getStateId()));
        
        City city = new City();
        city.setName(cityDTO.getName());
        city.setState(state);
        
        City savedCity = cityRepository.save(city);
        return convertToDTO(savedCity);
    }
    
    @Transactional
    public Optional<CityDTO> updateCity(Long id, CityDTO cityDTO) {
        return cityRepository.findById(id)
                .map(city -> {
                    city.setName(cityDTO.getName());
                    if (cityDTO.getStateId() != null) {
                        State state = stateRepository.findById(cityDTO.getStateId())
                                .orElseThrow(() -> new RuntimeException("State not found with id: " + cityDTO.getStateId()));
                        city.setState(state);
                    }
                    City updatedCity = cityRepository.save(city);
                    return convertToDTO(updatedCity);
                });
    }
    
    @Transactional
    public boolean deleteCity(Long id) {
        if (cityRepository.existsById(id)) {
            cityRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    private CityDTO convertToDTO(City city) {
        return new CityDTO(
            city.getId(),
            city.getName(),
            city.getState() != null ? city.getState().getId() : null
        );
    }
}
