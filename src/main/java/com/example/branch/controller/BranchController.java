package com.example.branch.controller;

import com.example.branch.entity.Branch;
import com.example.branch.service.BranchService;
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

    @GetMapping
    public ResponseEntity<List<Branch>> getAllBranches() {
        List<Branch> branches = branchService.getAllBranches();
        return ResponseEntity.ok(branches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Branch> getBranchById(@PathVariable Long id) {
        return branchService.getBranchById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Branch> createBranch(@RequestBody Branch branch) {
        Branch createdBranch = branchService.createBranch(branch);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBranch);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Branch> updateBranch(@PathVariable Long id, @RequestBody Branch branch) {
        return branchService.updateBranch(id, branch)
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



