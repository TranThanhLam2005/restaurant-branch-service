package com.example.branch.controller;

import com.example.branch.dto.StateRequest;
import com.example.branch.dto.StateResponse;
import com.example.branch.service.StateService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<StateResponse>> getAllStates() {
        List<StateResponse> states = stateService.getAllStates();
        return ResponseEntity.ok(states);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<StateResponse> getStateById(@PathVariable Long id) {
        StateResponse state = stateService.getStateById(id);
        return ResponseEntity.ok(state);
    }
    
    @GetMapping("/by-name/{name}")
    public ResponseEntity<StateResponse> getStateByName(@PathVariable String name) {
        StateResponse state = stateService.getStateByName(name);
        return ResponseEntity.ok(state);
    }
    
    @PostMapping
    public ResponseEntity<StateResponse> createState(@Valid @RequestBody StateRequest request) {
        StateResponse createdState = stateService.createState(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdState);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteState(@PathVariable Long id) {
        stateService.deleteState(id);
        return ResponseEntity.noContent().build();
    }
}
