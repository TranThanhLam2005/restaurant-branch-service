package com.example.branch.service;

import com.example.branch.entity.Branch;
import com.example.branch.entity.City;
import com.example.branch.dto.BranchDTO;
import com.example.branch.repository.BranchRepository;


import jakarta.transaction.Transactional;
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
    private final CityService cityService;


    public List<BranchDTO> getAllBranches() {
        List<Branch> branches = branchRepository.findAll();
        List<BranchDTO> result = branches.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return result;
    }

    public Optional<BranchDTO> getBranchById(Long id) {
        Optional<Branch> branch = branchRepository.findById(id);
        return branch.map(this::convertToDTO);
    }

    public BranchDTO createBranch(BranchDTO branchDTO) {
        Branch branch = convertToEntity(branchDTO);
        Branch savedBranch = branchRepository.save(branch);
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
                    Branch savedBranch = branchRepository.save(existingBranch);
                    return convertToDTO(savedBranch);
                });
    }

    @Transactional
    public boolean deleteBranch(Long id) {
        if (branchRepository.existsById(id)) {
            branchRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private BranchDTO convertToDTO(Branch branch) {
        BranchDTO dto = new BranchDTO();
        dto.setId(branch.getId());
        dto.setName(branch.getName());
        dto.setAddress(branch.getAddress());
        dto.setCityId(branch.getCity() != null ? branch.getCity().getId() : null);
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
        
        // Set City by cityId
        if (dto.getCityId() != null) {
            City city = cityService.getCityById(dto.getCityId())
                    .map(cityDTO -> {
                        City c = new City();
                        c.setId(cityDTO.getId());
                        c.setName(cityDTO.getName());
                        return c;
                    })
                    .orElseThrow(() -> new RuntimeException("City not found with id: " + dto.getCityId()));
            branch.setCity(city);
        }
        return branch;
    }
}
