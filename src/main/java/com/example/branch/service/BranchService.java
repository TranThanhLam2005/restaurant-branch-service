package com.example.branch.service;

import com.example.branch.entity.Branch;
import com.example.branch.entity.City;
import com.example.branch.entity.State;
import com.example.branch.dto.BranchDTO;
import com.example.branch.repository.BranchRepository;
import com.example.branch.repository.CityRepository;
import com.example.branch.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BranchService {
    
    private final BranchRepository branchRepository;
    private final CityRepository cityRepository;
    private final StateRepository stateRepository;

    public List<BranchDTO> getAllBranches() {
        log.info("Fetching all branches from database");
        List<Branch> branches = branchRepository.findAll();
        log.info("Found {} branches in database", branches.size());
        List<BranchDTO> result = branches.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        log.info("Converted {} branches to DTOs", result.size());
        return result;
    }

    public Optional<BranchDTO> getBranchById(Long id) {
        log.info("Fetching branch {} from database", id);
        Optional<Branch> branch = branchRepository.findById(id);
        return branch.map(this::convertToDTO);
    }

    public BranchDTO createBranch(BranchDTO branchDTO) {
        Branch branch = convertToEntity(branchDTO);
        Branch savedBranch = branchRepository.save(branch);
        log.info("Created new branch with id: {}", savedBranch.getId());
        return convertToDTO(savedBranch);
    }

    public Optional<BranchDTO> updateBranch(Long id, BranchDTO branchDTO) {
        return branchRepository.findById(id)
                .map(existingBranch -> {
                    existingBranch.setName(branchDTO.getName());
                    existingBranch.setAddress(branchDTO.getAddress());
                    existingBranch.setPhoneNumber(branchDTO.getPhoneNumber());
                    existingBranch.setOpeningTime(branchDTO.getOpeningTime() != null ? java.time.LocalTime.parse(branchDTO.getOpeningTime()) : null);
                    existingBranch.setClosingTime(branchDTO.getClosingTime() != null ? java.time.LocalTime.parse(branchDTO.getClosingTime()) : null);
                    
                    // Update City if provided
                    if (branchDTO.getCity() != null && branchDTO.getState() != null) {
                        State state = stateRepository.findByName(branchDTO.getState())
                                .orElseThrow(() -> new IllegalArgumentException("State not found: " + branchDTO.getState()));
                        
                        City city = cityRepository.findByNameAndStateId(branchDTO.getCity(), state.getId())
                                .orElseThrow(() -> new IllegalArgumentException("City not found: " + branchDTO.getCity() + " in state " + branchDTO.getState()));
                        
                        existingBranch.setCity(city);
                    }
                    
                    Branch savedBranch = branchRepository.save(existingBranch);
                    log.info("Updated branch with id: {}", id);
                    return convertToDTO(savedBranch);
                });
    }

    public boolean deleteBranch(Long id) {
        return branchRepository.findById(id)
                .map(branch -> {
                    branchRepository.delete(branch);
                    log.info("Deleted branch with id: {}", id);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Convert Branch entity to DTO
     */
    private BranchDTO convertToDTO(Branch branch) {
        BranchDTO dto = new BranchDTO();
        dto.setId(branch.getId());
        dto.setName(branch.getName());
        dto.setAddress(branch.getAddress());
        dto.setCity(branch.getCity() != null ? branch.getCity().getName() : null);
        dto.setState(branch.getCity() != null && branch.getCity().getState() != null ? 
                     branch.getCity().getState().getName() : null);
        dto.setPhoneNumber(branch.getPhoneNumber());
        dto.setOpeningTime(branch.getOpeningTime() != null ? branch.getOpeningTime().toString() : null);
        dto.setClosingTime(branch.getClosingTime() != null ? branch.getClosingTime().toString() : null);
        return dto;
    }

    /**
     * Convert DTO to Branch entity
     */
    private Branch convertToEntity(BranchDTO dto) {
        Branch branch = new Branch();
        branch.setId(dto.getId());
        branch.setName(dto.getName());
        branch.setAddress(dto.getAddress());
        branch.setPhoneNumber(dto.getPhoneNumber());
        branch.setOpeningTime(dto.getOpeningTime() != null ? java.time.LocalTime.parse(dto.getOpeningTime()) : null);
        branch.setClosingTime(dto.getClosingTime() != null ? java.time.LocalTime.parse(dto.getClosingTime()) : null);
        
        // Find or get City by name and state
        if (dto.getCity() != null && dto.getState() != null) {
            State state = stateRepository.findByName(dto.getState())
                    .orElseThrow(() -> new IllegalArgumentException("State not found: " + dto.getState()));
            
            City city = cityRepository.findByNameAndStateId(dto.getCity(), state.getId())
                    .orElseThrow(() -> new IllegalArgumentException("City not found: " + dto.getCity() + " in state " + dto.getState()));
            
            branch.setCity(city);
        }
        
        return branch;
    }
}
