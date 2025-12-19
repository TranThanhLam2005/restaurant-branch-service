package com.example.branch.controller;


import com.example.branch.service.BranchTableService;
import com.example.branch.dto.BranchTableDTO;
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
    public ResponseEntity<List<BranchTableDTO>> getAllBranchTables() {
        List<BranchTableDTO> branchTables = branchTableService.getAllBranchTables();
        return ResponseEntity.ok(branchTables);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BranchTableDTO> getBranchTableById(@PathVariable Long id)
    {
        return branchTableService.getBranchTableById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<BranchTableDTO> createBranchTable(@RequestBody BranchTableDTO branchTableDTO)
    {
        return branchTableService.createBranchTable(branchTableDTO)
                .map(createdBranchTable -> ResponseEntity.status(HttpStatus.CREATED).body(createdBranchTable))
                .orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<BranchTableDTO> updateBranchTableStatus(@PathVariable Long id, @RequestParam Boolean isAvailable)
    {
        return branchTableService.updateBranchTableStatus(id, isAvailable)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranchTable(@PathVariable Long id)
    {
        boolean deleted = branchTableService.deleteBranchTable(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
