package com.example.branch.dto;

import com.example.branch.entity.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityResponse {
    private Long id;
    private String name;
    private Long stateId;
    private String stateName;

    public static CityResponse fromEntity(City city) {
        return CityResponse.builder()
                .id(city.getId())
                .name(city.getName())
                .stateId(city.getState() != null ? city.getState().getId() : null)
                .stateName(city.getState() != null ? city.getState().getName() : null)
                .build();
    }
}
