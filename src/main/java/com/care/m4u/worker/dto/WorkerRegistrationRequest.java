package com.care.m4u.worker.dto;

import lombok.Data;

@Data
public class WorkerRegistrationRequest {
    private String name;
    private String username;
    private String email;
    private String password;
    private Long phoneNo;
    private String service;
    private float experience;
    private String[] previousWorks;
} 