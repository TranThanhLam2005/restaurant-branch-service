package com.example.branch.service;

import com.example.branch.entity.Branch;
import com.example.branch.entity.BranchTable;
import com.example.branch.dto.BranchTableRequest;
import com.example.branch.dto.BranchTableResponse;
import com.example.branch.repository.BranchRepository;
import com.example.branch.repository.BranchTableRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BranchTableService {
    
    private final BranchTableRepository branchTableRepository;
    private final BranchRepository branchRepository;

    @Transactional(readOnly = true)
    public List<BranchTableResponse> getAllBranchTables() {
        return branchTableRepository.findAll().stream()
                .map(BranchTableResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BranchTableResponse getBranchTableById(Long id) {
        BranchTable table = branchTableRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Branch table not found with id: " + id));
        return BranchTableResponse.fromEntity(table);
    }

    @Transactional(readOnly = true)
    public List<BranchTableResponse> getTablesByBranchId(Long branchId) {
        return branchTableRepository.findByBranchId(branchId).stream()
                .map(BranchTableResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public BranchTableResponse createBranchTable(BranchTableRequest request) {
        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + request.getBranchId()));
        
        BranchTable table = new BranchTable();
        table.setBranch(branch);
        table.setTableNumber(request.getTableNumber());
        table.setSeatingCapacity(request.getSeatingCapacity());
        table.setIsAvailable(request.getIsAvailable() != null ? request.getIsAvailable() : true);
        
        BranchTable savedTable = branchTableRepository.save(table);
        return BranchTableResponse.fromEntity(savedTable);
    }

    public BranchTableResponse updateBranchTableStatus(Long id, Boolean isAvailable) {
        BranchTable table = branchTableRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Branch table not found with id: " + id));
        
        table.setIsAvailable(isAvailable);
        BranchTable savedTable = branchTableRepository.save(table);
        return BranchTableResponse.fromEntity(savedTable);
    }
    
    public void deleteBranchTable(Long id) {
        BranchTable table = branchTableRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Branch table not found with id: " + id));
        branchTableRepository.delete(table);
    }
}