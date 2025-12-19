package com.example.branch.controller;

import com.example.branch.dto.BranchDTO;
import com.example.branch.dto.BranchTableDTO;
import com.example.branch.service.BranchService;
import com.example.branch.service.BranchTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {
    
    private final BranchService branchService;
    private final BranchTableService branchTableService;

    @GetMapping
    public ResponseEntity<List<BranchDTO>> getAllBranches() {
        List<BranchDTO> branches = branchService.getAllBranches();
        return ResponseEntity.ok(branches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDTO> getBranchById(@PathVariable Long id) {
        return branchService.getBranchById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/tables")
    public ResponseEntity<List<BranchTableDTO>> getTablesByBranchId(@PathVariable Long id) {
        List<BranchTableDTO> tables = branchTableService.getTablesByBranchId(id);
        return ResponseEntity.ok(tables);
    }

    @PostMapping
    public ResponseEntity<BranchDTO> createBranch(@RequestBody BranchDTO branchDTO) {
        BranchDTO createdBranch = branchService.createBranch(branchDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBranch);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchDTO> updateBranch(@PathVariable Long id, @RequestBody BranchDTO branchDTO) {
        return branchService.updateBranch(id, branchDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        boolean deleted = branchService.deleteBranch(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/health")
    public String health() {
        return "Branch OKeee";
    }
}



