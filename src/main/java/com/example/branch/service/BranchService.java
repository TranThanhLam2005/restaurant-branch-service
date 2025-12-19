package com.example.branch.service;

import com.example.branch.entity.Branch;
import com.example.branch.dto.BranchDTO;
import com.example.branch.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchService {
    
    private final BranchRepository branchRepository;

    public List<BranchDTO> getAllBranches() {
        return branchRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<BranchDTO> getBranchById(Long id) {
        return branchRepository.findById(id)
                .map(this::convertToDTO);
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

    public boolean deleteBranch(Long id) {
        return branchRepository.findById(id)
                .map(branch -> {
                    branchRepository.delete(branch);
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
        return branch;
    }
}
