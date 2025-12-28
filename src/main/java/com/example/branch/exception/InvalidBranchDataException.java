package com.example.branch.exception;

public class InvalidBranchDataException extends RuntimeException {
    public InvalidBranchDataException(String message) {
        super(message);
    }
}
