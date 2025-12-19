package com.example.branch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.branch.entity.BranchTable;

import java.util.List;

@Repository
public interface BranchTableRepository extends JpaRepository<BranchTable, Long> {
    List<BranchTable> findByBranchId(Long branchId);
    List<BranchTable> findByBranchIdAndIsAvailable(Long branchId, Boolean isAvailable);
}
