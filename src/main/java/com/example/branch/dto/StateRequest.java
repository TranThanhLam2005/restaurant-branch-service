package com.example.branch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StateRequest {
    @NotBlank(message = "State name is required")
    @Size(max = 255, message = "State name must be less than 255 characters")
    private String name;
}
