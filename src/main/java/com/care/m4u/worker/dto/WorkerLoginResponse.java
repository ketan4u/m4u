package com.care.m4u.worker.dto;

import lombok.Data;

@Data
public class WorkerLoginResponse {
    private String token;
    private WorkerResponse worker;
    private String message;
} 