package com.example.branch.service;

import com.example.branch.entity.Branch;
import com.example.branch.dto.BranchDTO;
import com.example.branch.repository.BranchRepository;
import com.example.branch.config.BranchRedisHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class BranchService {
    
    private final BranchRepository branchRepository;
    private final BranchRedisHelper redisHelper;
    
    private static final String BRANCH_CACHE_KEY = "branch:";
    private static final String ALL_BRANCHES_KEY = "branches:all";

    public List<BranchDTO> getAllBranches() {
        // Try to get all branches from Redis cache first
        try {
            Boolean hasCache = redisHelper.hasKey(ALL_BRANCHES_KEY);
            if (Boolean.TRUE.equals(hasCache)) {
                log.info("Fetching all branches from Redis cache");
                Map<Object, Object> cachedBranches = redisHelper.getAllBranchesFromHash(ALL_BRANCHES_KEY);
                if (cachedBranches != null && !cachedBranches.isEmpty()) {
                    return cachedBranches.values().stream()
                            .map(obj -> (Branch) obj)
                            .map(this::convertToDTO)
                            .collect(Collectors.toList());
                }
            }
        } catch (Exception e) {
            log.error("Error retrieving branches from cache, will fetch from database", e);
        }
        
        // If not in cache, fetch from database
        log.info("Branches not in cache, fetching from database");
        List<Branch> branches = branchRepository.findAll();
        
        // Cache all branches in a hash
        try {
            Map<String, Branch> branchMap = branches.stream()
                    .collect(Collectors.toMap(
                            branch -> String.valueOf(branch.getId()),
                            branch -> branch
                    ));
            redisHelper.storeAllBranches(ALL_BRANCHES_KEY, branchMap);
            log.info("Cached {} branches in Redis", branches.size());
            
            // Also cache each branch individually for getBranchById
            branches.forEach(branch -> {
                try {
                    redisHelper.createRedisString(BRANCH_CACHE_KEY + branch.getId(), branch);
                } catch (Exception e) {
                    log.error("Error caching individual branch: {}", branch.getId(), e);
                }
            });
        } catch (Exception e) {
            log.error("Error caching branches", e);
        }
        
        return branches.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<BranchDTO> getBranchById(Long id) {
        // Try to get from cache first
        try {
            Branch cachedBranch = redisHelper.getStrValueByKey(BRANCH_CACHE_KEY + id);
            if (cachedBranch != null) {
                log.info("Branch {} found in cache", id);
                return Optional.of(convertToDTO(cachedBranch));
            }
        } catch (Exception e) {
            log.error("Error retrieving branch from cache: {}", id, e);
        }
        
        // If not in cache, get from database and cache it
        log.info("Branch {} not in cache, fetching from database", id);
        Optional<Branch> branch = branchRepository.findById(id);
        branch.ifPresent(b -> {
            try {
                redisHelper.createRedisString(BRANCH_CACHE_KEY + id, b);
            } catch (Exception e) {
                log.error("Error caching branch: {}", id, e);
            }
        });
        
        return branch.map(this::convertToDTO);
    }

    public BranchDTO createBranch(BranchDTO branchDTO) {
        Branch branch = convertToEntity(branchDTO);
        Branch savedBranch = branchRepository.save(branch);
        
        // Cache the newly created branch
        try {
            redisHelper.createRedisString(BRANCH_CACHE_KEY + savedBranch.getId(), savedBranch);
            // Invalidate all branches cache since we added a new branch
            redisHelper.deleteKey(ALL_BRANCHES_KEY);
            log.info("Invalidated all branches cache after creating new branch");
        } catch (Exception e) {
            log.error("Error caching new branch: {}", savedBranch.getId(), e);
        }
        
        return convertToDTO(savedBranch);
    }

    public Optional<BranchDTO> updateBranch(Long id, BranchDTO branchDTO) {
        return branchRepository.findById(id)
                .map(existingBranch -> {
                    existingBranch.setName(branchDTO.getName());
                    existingBranch.setAddress(branchDTO.getAddress());
                    existingBranch.setState(branchDTO.getState());
                    existingBranch.setCity(branchDTO.getCity());
                    existingBranch.setPhoneNumber(branchDTO.getPhoneNumber());
                    existingBranch.setOpeningTime(branchDTO.getOpeningTime() != null ? java.time.LocalTime.parse(branchDTO.getOpeningTime()) : null);
                    existingBranch.setClosingTime(branchDTO.getClosingTime() != null ? java.time.LocalTime.parse(branchDTO.getClosingTime()) : null);
                    Branch savedBranch = branchRepository.save(existingBranch);
                    
                    // Update cache with new data
                    try {
                        redisHelper.createRedisString(BRANCH_CACHE_KEY + id, savedBranch);
                        // Invalidate all branches cache since we updated a branch
                        redisHelper.deleteKey(ALL_BRANCHES_KEY);
                        log.info("Invalidated all branches cache after updating branch: {}", id);
                    } catch (Exception e) {
                        log.error("Error updating cache for branch: {}", id, e);
                    }
                    
                    return convertToDTO(savedBranch);
                });
    }

    public boolean deleteBranch(Long id) {
        return branchRepository.findById(id)
                .map(branch -> {
                    branchRepository.delete(branch);
                    // Invalidate caches after deletion
                    try {
                        redisHelper.deleteKey(BRANCH_CACHE_KEY + id);
                        redisHelper.deleteKey(ALL_BRANCHES_KEY);
                        log.info("Invalidated caches after deleting branch: {}", id);
                    } catch (Exception e) {
                        log.error("Error invalidating cache for deleted branch: {}", id, e);
                    }
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
        dto.setState(branch.getState());
        dto.setCity(branch.getCity());
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
        branch.setState(dto.getState());
        branch.setCity(dto.getCity());
        branch.setPhoneNumber(dto.getPhoneNumber());
        branch.setOpeningTime(dto.getOpeningTime() != null ? java.time.LocalTime.parse(dto.getOpeningTime()) : null);
        branch.setClosingTime(dto.getClosingTime() != null ? java.time.LocalTime.parse(dto.getClosingTime()) : null);
        return branch;
    }
}
