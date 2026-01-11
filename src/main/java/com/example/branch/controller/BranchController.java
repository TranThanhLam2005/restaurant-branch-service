package com.example.branch.controller;

import com.example.branch.dto.BranchRequest;
import com.example.branch.dto.BranchResponse;
import com.example.branch.dto.BranchTableResponse;
import com.example.branch.service.BranchService;
import com.example.branch.service.BranchTableService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
@Slf4j
public class BranchController {
    
    private final BranchService branchService;
    private final BranchTableService branchTableService;

    @GetMapping
    public ResponseEntity<List<BranchResponse>> getAllBranches() {
        List<BranchResponse> branches = branchService.getAllBranches();
        return ResponseEntity.ok(branches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchResponse> getBranchById(@PathVariable Long id) {
        BranchResponse branch = branchService.getBranchById(id);
        return ResponseEntity.ok(branch);
    }

    @GetMapping("/{id}/tables")
    public ResponseEntity<List<BranchTableResponse>> getTablesByBranchId(@PathVariable Long id) {
        List<BranchTableResponse> tables = branchTableService.getTablesByBranchId(id);
        return ResponseEntity.ok(tables);
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<List<BranchResponse>> getBranchesByCityId(@PathVariable Long cityId) {
        List<BranchResponse> branches = branchService.getBranchesByCityId(cityId);
        return ResponseEntity.ok(branches);
    }

    @PostMapping
    public ResponseEntity<BranchResponse> createBranch(@Valid @RequestBody BranchRequest request) {
        BranchResponse createdBranch = branchService.createBranch(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBranch);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchResponse> updateBranch(
            @PathVariable Long id,
            @Valid @RequestBody BranchRequest request) {
        BranchResponse updatedBranch = branchService.updateBranch(id, request);
        return ResponseEntity.ok(updatedBranch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        branchService.deleteBranch(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/health")
    public String health() {
        return "Branch OKeee";
    }
}



