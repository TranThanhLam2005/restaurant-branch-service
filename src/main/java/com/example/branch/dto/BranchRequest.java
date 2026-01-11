package com.example.branch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchRequest {
    @NotBlank(message = "Branch name is required")
    @Size(max = 255, message = "Branch name must be less than 255 characters")
    private String name;

    @NotBlank(message = "Address is required")
    @Size(max = 500, message = "Address must be less than 500 characters")
    private String address;

    @NotNull(message = "City ID is required")
    private Long cityId;

    @Pattern(regexp = "^\\+?[0-9\\s\\-()]+$", message = "Invalid phone number format")
    @Size(max = 20, message = "Phone number must be less than 20 characters")
    private String phoneNumber;

    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Opening time must be in HH:mm format")
    private String openingTime;

    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Closing time must be in HH:mm format")
    private String closingTime;
}
