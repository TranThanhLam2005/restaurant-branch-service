package com.example.branch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchTableDTO {
    private Long id;
    private Long branchId;
    private Integer tableNumber;
    private Integer seatingCapacity;
    private Boolean isAvailable;
}
