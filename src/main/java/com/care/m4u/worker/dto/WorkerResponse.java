package com.care.m4u.worker.dto;

import lombok.Data;

@Data
public class WorkerResponse {
    private Long id;
    private String name;
    private String username;
    private String email;
    private Long phoneNo;
    private String service;
    private float experience;
    private String[] previousWorks;
}
