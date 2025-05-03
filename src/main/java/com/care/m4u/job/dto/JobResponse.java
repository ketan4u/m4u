package com.care.m4u.job.dto;

import lombok.Data;
import java.util.Date;

@Data
public class JobResponse {
    private Long id;
    private Long workerId;
    private Long secondaryWorkerId;
    private String jobType;
    private String customerName;
    private String billingDuration;
    private Date startDate;
    private Date endDate;
    private Date createdAt;
    private String status;
} 