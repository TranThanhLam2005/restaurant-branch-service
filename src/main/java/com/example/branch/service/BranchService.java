package com.example.branch.service;

import com.example.branch.entity.Branch;
import com.example.branch.entity.City;
import com.example.branch.dto.BranchRequest;
import com.example.branch.dto.BranchResponse;
import com.example.branch.repository.BranchRepository;
import com.example.branch.repository.CityRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BranchService {
    
    private final BranchRepository branchRepository;
    private final CityRepository cityRepository;

    @Transactional(readOnly = true)
    public List<BranchResponse> getAllBranches() {
        return branchRepository.findAll().stream()
                .map(BranchResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BranchResponse getBranchById(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + id));
        return BranchResponse.fromEntity(branch);
    }

    @Transactional(readOnly = true)
    public List<BranchResponse> getBranchesByCityId(Long cityId) {
        return branchRepository.findByCityId(cityId).stream()
                .map(BranchResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public BranchResponse createBranch(BranchRequest request) {
        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new EntityNotFoundException("City not found with id: " + request.getCityId()));

        Branch branch = Branch.builder()
                .name(request.getName())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .openingTime(request.getOpeningTime() != null ? LocalTime.parse(request.getOpeningTime()) : null)
                .closingTime(request.getClosingTime() != null ? LocalTime.parse(request.getClosingTime()) : null)
                .city(city)
                .build();

        Branch savedBranch = branchRepository.save(branch);
        return BranchResponse.fromEntity(savedBranch);
    }

    public BranchResponse updateBranch(Long id, BranchRequest request) {
        Branch existingBranch = branchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Branch not found with id: " + id));

        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new EntityNotFoundException("City not found with id: " + request.getCityId()));

        existingBranch.setName(request.getName());
        existingBranch.setAddress(request.getAddress());
        existingBranch.setPhoneNumber(request.getPhoneNumber());
        existingBranch.setOpeningTime(request.getOpeningTime() != null ? LocalTime.parse(request.getOpeningTime()) : null);
        existingBranch.setClosingTime(request.getClosingTime() != null ? LocalTime.parse(request.getClosingTime()) : null);
        existingBranch.setCity(city);

        Branch savedBranch = branchRepository.save(existingBranch);
        return BranchResponse.fromEntity(savedBranch);
    }

    public void deleteBranch(Long id) {
        if (!branchRepository.existsById(id)) {
            throw new EntityNotFoundException("Branch not found with id: " + id);
        }
        branchRepository.deleteById(id);
    }

}
