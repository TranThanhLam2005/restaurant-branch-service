package com.example.branch.service;

import com.example.branch.dto.StateDTO;
import com.example.branch.entity.State;
import com.example.branch.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StateService {
    
    private final StateRepository stateRepository;
    
    public List<StateDTO> getAllStates() {
        return stateRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public Optional<StateDTO> getStateById(Long id) {
        return stateRepository.findById(id)
                .map(this::convertToDTO);
    }
    
    public Optional<StateDTO> getStateByName(String name) {
        return stateRepository.findByName(name)
                .map(this::convertToDTO);
    }
    
    @Transactional
    public StateDTO createState(StateDTO stateDTO) {
        State state = new State();
        state.setName(stateDTO.getName());
        State savedState = stateRepository.save(state);
        return convertToDTO(savedState);
    }
    
    
    @Transactional
    public boolean deleteState(Long id) {
        if (stateRepository.existsById(id)) {
            stateRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    private StateDTO convertToDTO(State state) {
        return new StateDTO(state.getId(), state.getName());
    }
}
