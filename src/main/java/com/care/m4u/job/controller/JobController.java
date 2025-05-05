package com.care.m4u.job.controller;

import com.care.m4u.job.dto.JobAssignmentRequest;
import com.care.m4u.job.dto.JobDeletionRequest;
import com.care.m4u.job.dto.JobResponse;
import com.care.m4u.job.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
        logger.info("Received job assignment request for location: {}, {}", request.getLatitude(), request.getLongitude());
        
        JobResponse response = jobService.assignJob(request);
        
        logger.info("Successfully processed job assignment with ID: {}", response.getId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{jobId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    @Operation(summary = "Delete a job", description = "Deletes a job with a reason. Only customers and admins can delete jobs.")
    public ResponseEntity<Void> deleteJob(
            @PathVariable Long jobId,
            @RequestBody JobDeletionRequest request,
            Authentication authentication) {
        logger.info("Received job deletion request for ID: {}", jobId);
        
        jobService.deleteJob(jobId, request, authentication.getName());
        
        logger.info("Successfully processed job deletion for ID: {}", jobId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{jobId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get job by ID", description = "Retrieves a job by its ID")
    public ResponseEntity<JobResponse> getJobById(@PathVariable Long jobId) {
        logger.info("Received request to fetch job with ID: {}", jobId);
        
        JobResponse response = jobService.getJobById(jobId);
        
        logger.info("Successfully fetched job with ID: {}", jobId);
        return ResponseEntity.ok(response);
    }
}
