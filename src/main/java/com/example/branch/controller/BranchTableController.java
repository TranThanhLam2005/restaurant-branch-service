package com.example.branch.controller;

import com.example.branch.service.BranchTableService;
import com.example.branch.dto.BranchTableRequest;
import com.example.branch.dto.BranchTableResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branch-tables")
@RequiredArgsConstructor
public class BranchTableController {
    
    private final BranchTableService branchTableService;

    @GetMapping
    public ResponseEntity<List<BranchTableResponse>> getAllBranchTables() {
        List<BranchTableResponse> branchTables = branchTableService.getAllBranchTables();
        return ResponseEntity.ok(branchTables);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BranchTableResponse> getBranchTableById(@PathVariable Long id) {
        BranchTableResponse table = branchTableService.getBranchTableById(id);
        return ResponseEntity.ok(table);
    }
    
    @PostMapping
    public ResponseEntity<BranchTableResponse> createBranchTable(@Valid @RequestBody BranchTableRequest request) {
        BranchTableResponse createdTable = branchTableService.createBranchTable(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTable);
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<BranchTableResponse> updateBranchTableStatus(
            @PathVariable Long id,
            @RequestParam Boolean isAvailable) {
        BranchTableResponse updatedTable = branchTableService.updateBranchTableStatus(id, isAvailable);
        return ResponseEntity.ok(updatedTable);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranchTable(@PathVariable Long id) {
        branchTableService.deleteBranchTable(id);
        return ResponseEntity.noContent().build();
    }
}
