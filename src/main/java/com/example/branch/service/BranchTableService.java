package com.example.branch.service;

import com.example.branch.entity.BranchTable;
import com.example.branch.dto.BranchTableDTO;
import com.example.branch.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.branch.repository.BranchTableRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Internal service for handling REST API requests for branch tables
 * Uses external DTOs for API responses
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BranchTableService {
    
    private final BranchTableRepository branchTableRepository;
    private final BranchRepository branchRepository;

    public List<BranchTableDTO> getAllBranchTables() {
        return branchTableRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<BranchTableDTO> getBranchTableById(Long id) {
        return branchTableRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<BranchTableDTO> getTablesByBranchId(Long branchId) {
        return branchTableRepository.findByBranchId(branchId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<BranchTableDTO> createBranchTable(BranchTableDTO branchTableDTO) {
        BranchTable branchTable = convertToEntity(branchTableDTO);
        BranchTable savedTable = branchTableRepository.save(branchTable);
        return Optional.of(convertToDTO(savedTable));
    }

    @Transactional
    public Optional<BranchTableDTO> updateBranchTableStatus(Long id, Boolean isAvailable) {
        return branchTableRepository.findById(id)
                .map(existingBranchTable -> {
                    existingBranchTable.setIsAvailable(isAvailable);
                    BranchTable savedTable = branchTableRepository.save(existingBranchTable);
                    return convertToDTO(savedTable);
                });
    }
    
    public boolean deleteBranchTable(Long id) {
        return branchTableRepository.findById(id)
                .map(branchTable -> {
                    branchTableRepository.delete(branchTable);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Convert BranchTable entity to DTO
     */
    private BranchTableDTO convertToDTO(BranchTable table) {
        BranchTableDTO dto = new BranchTableDTO();
        dto.setId(table.getId());
        dto.setBranchId(table.getBranch() != null ? table.getBranch().getId() : null);
        dto.setTableNumber(table.getTableNumber());
        dto.setSeatingCapacity(table.getSeatingCapacity());
        dto.setIsAvailable(table.getIsAvailable());
        return dto;
    }

    /**
     * Convert DTO to BranchTable entity
     */
    private BranchTable convertToEntity(BranchTableDTO dto) {
        BranchTable table = new BranchTable();
        table.setId(dto.getId());
        table.setTableNumber(dto.getTableNumber());
        table.setSeatingCapacity(dto.getSeatingCapacity());
        table.setIsAvailable(dto.getIsAvailable());
        
        // Set branch reference if branchId is provided
        if (dto.getBranchId() != null) {
            table.setBranch(branchRepository.findById(dto.getBranchId())
                    .orElse(null));
        }
        
        return table;
    }
}