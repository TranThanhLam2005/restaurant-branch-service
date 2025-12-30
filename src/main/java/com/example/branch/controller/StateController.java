package com.example.branch.controller;

import com.example.branch.dto.StateDTO;
import com.example.branch.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/states")
@RequiredArgsConstructor
public class StateController {
    
    private final StateService stateService;
    
    @GetMapping
    public ResponseEntity<List<StateDTO>> getAllStates() {
        List<StateDTO> states = stateService.getAllStates();
        return ResponseEntity.ok(states);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<StateDTO> getStateById(@PathVariable Long id) {
        return stateService.getStateById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/by-name/{name}")
    public ResponseEntity<StateDTO> getStateByName(@PathVariable String name) {
        return stateService.getStateByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<StateDTO> createState(@RequestBody StateDTO stateDTO) {
        StateDTO createdState = stateService.createState(stateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdState);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteState(@PathVariable Long id) {
        boolean deleted = stateService.deleteState(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
