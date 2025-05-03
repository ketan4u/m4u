package com.care.m4u.job.service;

import com.care.m4u.job.dto.JobAssignmentRequest;
import com.care.m4u.job.dto.JobResponse;

public interface JobService {
    JobResponse assignJob(JobAssignmentRequest request);
} 