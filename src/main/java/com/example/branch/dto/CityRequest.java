package com.example.branch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityRequest {
    @NotBlank(message = "City name is required")
    @Size(max = 255, message = "City name must be less than 255 characters")
    private String name;

    @NotNull(message = "State ID is required")
    private Long stateId;
}
