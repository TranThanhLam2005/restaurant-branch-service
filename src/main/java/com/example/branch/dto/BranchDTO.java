package com.example.branch.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchDTO {
    private Long id;
    private String name;
    private String address;
    private String state;
    private String city;
    private String phoneNumber;
    private String openingTime;
    private String closingTime;
}
