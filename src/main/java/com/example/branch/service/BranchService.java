package com.example.branch.service;

import com.example.branch.entity.Branch;
import com.example.branch.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BranchService {
    
    private final BranchRepository branchRepository;

    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    public Optional<Branch> getBranchById(Long id) {
        return branchRepository.findById(id);
    }

    public Branch createBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    public Optional<Branch> updateBranch(Long id, Branch branchDetails) {
        return branchRepository.findById(id)
                .map(existingBranch -> {
                    existingBranch.setName(branchDetails.getName());
                    existingBranch.setAddress(branchDetails.getAddress());
                    existingBranch.setPhoneNumber(branchDetails.getPhoneNumber());
                    existingBranch.setOpeningTime(branchDetails.getOpeningTime());
                    existingBranch.setClosingTime(branchDetails.getClosingTime());
                    return branchRepository.save(existingBranch);
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
}
