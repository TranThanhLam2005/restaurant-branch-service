package com.example.branch.repository;

import com.example.branch.entity.Branch;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    List<Branch> findByCityId(Long cityId);
}
