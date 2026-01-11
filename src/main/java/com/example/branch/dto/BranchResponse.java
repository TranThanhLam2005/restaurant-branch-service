package com.example.branch.dto;

import com.example.branch.entity.Branch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchResponse {
    private Long id;
    private String name;
    private String address;
    private Long cityId;
    private String cityName;
    private String phoneNumber;
    private String openingTime;
    private String closingTime;

    public static BranchResponse fromEntity(Branch branch) {
        return BranchResponse.builder()
                .id(branch.getId())
                .name(branch.getName())
                .address(branch.getAddress())
                .cityId(branch.getCity() != null ? branch.getCity().getId() : null)
                .cityName(branch.getCity() != null ? branch.getCity().getName() : null)
                .phoneNumber(branch.getPhoneNumber())
                .openingTime(branch.getOpeningTime() != null ? branch.getOpeningTime().toString() : null)
                .closingTime(branch.getClosingTime() != null ? branch.getClosingTime().toString() : null)
                .build();
    }
}
