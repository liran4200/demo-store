package com.example.service;

public class ReportNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    public ReportNotFoundException(String message) {
        super(message);
    }
}
