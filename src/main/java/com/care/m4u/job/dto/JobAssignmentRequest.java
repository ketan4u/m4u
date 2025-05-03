package com.care.m4u.job.dto;

import lombok.Data;
import java.util.Date;

@Data
public class JobAssignmentRequest {
    private Long workerId;
    private String jobType;
    private String customerName;
    private String billingDuration;
    private Date startDate;
    private Date endDate;
    private Double latitude;
    private Double longitude;
} 