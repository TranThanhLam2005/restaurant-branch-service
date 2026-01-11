package com.example.branch.service;

import com.example.branch.dto.StateRequest;
import com.example.branch.dto.StateResponse;
import com.example.branch.entity.State;
import com.example.branch.repository.StateRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StateService {
    
    private final StateRepository stateRepository;
    
    @Transactional(readOnly = true)
    public List<StateResponse> getAllStates() {
        return stateRepository.findAll().stream()
                .map(StateResponse::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public StateResponse getStateById(Long id) {
        State state = stateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("State not found with id: " + id));
        return StateResponse.fromEntity(state);
    }
    
    @Transactional(readOnly = true)
    public StateResponse getStateByName(String name) {
        State state = stateRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("State not found with name: " + name));
        return StateResponse.fromEntity(state);
    }
    
    public StateResponse createState(StateRequest request) {
        State state = new State();
        state.setName(request.getName());
        State savedState = stateRepository.save(state);
        return StateResponse.fromEntity(savedState);
    }
    
    public void deleteState(Long id) {
        if (!stateRepository.existsById(id)) {
            throw new EntityNotFoundException("State not found with id: " + id);
        }
        stateRepository.deleteById(id);
    }
}
