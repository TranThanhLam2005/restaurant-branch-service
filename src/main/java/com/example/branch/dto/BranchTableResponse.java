package com.example.branch.dto;

import com.example.branch.entity.BranchTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchTableResponse {
    private Long id;
    private Long branchId;
    private String branchName;
    private Integer tableNumber;
    private Integer seatingCapacity;
    private Boolean isAvailable;

    public static BranchTableResponse fromEntity(BranchTable table) {
        return BranchTableResponse.builder()
                .id(table.getId())
                .branchId(table.getBranch() != null ? table.getBranch().getId() : null)
                .branchName(table.getBranch() != null ? table.getBranch().getName() : null)
                .tableNumber(table.getTableNumber())
                .seatingCapacity(table.getSeatingCapacity())
                .isAvailable(table.getIsAvailable())
                .build();
    }
}
