package com.care.m4u.job.controller;

import com.care.m4u.job.dto.JobAssignmentRequest;
import com.care.m4u.job.dto.JobResponse;
import com.care.m4u.job.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
@Tag(name = "Jobs", description = "Jobs management endpoints")
public class JobController {

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private JobService jobService;

    @PostMapping("/assign")
    @PreAuthorize("hasRole('CUSTOMER')")
    @Operation(summary = "Assign a job to workers", description = "Assigns a job to a primary worker and finds a secondary worker with matching skills")
    public ResponseEntity<JobResponse> assignJob(@RequestBody JobAssignmentRequest request) {
        logger.info("Received job assignment request for worker ID: {}", request.getWorkerId());
        
        JobResponse response = jobService.assignJob(request);
        
        logger.info("Successfully processed job assignment with ID: {}", response.getId());
        return ResponseEntity.ok(response);
    }
}
