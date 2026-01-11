package com.example.branch.dto;

import com.example.branch.entity.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StateResponse {
    private Long id;
    private String name;

    public static StateResponse fromEntity(State state) {
        return StateResponse.builder()
                .id(state.getId())
                .name(state.getName())
                .build();
    }
}
