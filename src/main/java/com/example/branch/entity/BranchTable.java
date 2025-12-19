package com.example.branch.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "branch_tables")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "branch")
@EqualsAndHashCode(exclude = "branch")
public class BranchTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    @JsonIgnore
    private Branch branch;
    
    private Integer tableNumber;
    private Integer seatingCapacity;
    private Boolean isAvailable;
}
