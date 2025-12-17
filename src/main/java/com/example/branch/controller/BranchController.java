package com.example.branch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class BranchController {
    @GetMapping
    public String health(){
        return "Branch OK";
    }
}
