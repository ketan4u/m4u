package com.care.m4u.job.service;

import com.care.m4u.job.dto.JobAssignmentRequest;
import com.care.m4u.job.dto.JobDeletionRequest;
import com.care.m4u.job.dto.JobResponse;
import com.care.m4u.job.model.Job;
import com.care.m4u.job.repository.JobsRepo;
import com.care.m4u.worker.model.Worker;
import com.care.m4u.worker.repository.WorkerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    @Autowired
    private JobsRepo jobsRepo;

    @Autowired
    private WorkerRepo workerRepo;

    @Override
    @Transactional
    @CachePut(value = "jobs", key = "#result.id")
    public JobResponse assignJob(JobAssignmentRequest request) {
        logger.info("Starting job assignment for location: {}, {}", request.getLatitude(), request.getLongitude());

        // Find nearest workers with matching skills
        List<Worker> nearestWorkers = workerRepo.findNearestWorkersByService(
            request.getJobType(),
            request.getLatitude(),
            request.getLongitude()
        );

        if (nearestWorkers.size() < 2) {
            throw new RuntimeException("Not enough workers available with matching skills in the area");
        }

        // Create and save job
        Job job = new Job();
        job.setWorkerId(nearestWorkers.get(0).getId()); // Primary worker
        job.setSecondaryWorkerId(nearestWorkers.get(1).getId()); // Secondary worker
        job.setJobType(request.getJobType());
        job.setCustomerName(request.getCustomerName());
        job.setBillingDuration(request.getBillingDuration());
        job.setStartDate(request.getStartDate());
        job.setEndDate(request.getEndDate());
        job.setCreatedAt(new Date());
        job.setStatus("CREATED");

        Job savedJob = jobsRepo.save(job);
        logger.info("Job successfully assigned with ID: {}", savedJob.getId());
        logger.info("Primary worker ID: {}, Secondary worker ID: {}", 
            nearestWorkers.get(0).getId(), nearestWorkers.get(1).getId());

        return mapToResponse(savedJob);
    }

    @Override
    @Transactional
    @CacheEvict(value = "jobs", key = "#jobId")
    public void deleteJob(Long jobId, JobDeletionRequest request, String deletedBy) {
        logger.info("Starting job deletion for ID: {}", jobId);

        Job job = jobsRepo.findById(jobId)
            .orElseThrow(() -> new RuntimeException("Job not found"));

        // Check if the job is already completed
        if ("COMPLETED".equals(job.getStatus())) {
            throw new RuntimeException("Cannot delete a completed job");
        }

        // Set deletion details
        job.setDeletionReason(request.getReason());
        job.setDeletedBy(deletedBy);
        job.setStatus("DELETED");

        jobsRepo.save(job);
        logger.info("Job successfully deleted with ID: {}", jobId);
    }

    @Cacheable(value = "jobs", key = "#jobId", unless = "#result == null")
    public JobResponse getJobById(Long jobId) {
        logger.info("Fetching job with ID: {}", jobId);
        Job job = jobsRepo.findById(jobId)
            .orElseThrow(() -> new RuntimeException("Job not found"));
        return mapToResponse(job);
    }

    private JobResponse mapToResponse(Job job) {
        JobResponse response = new JobResponse();
        response.setId(job.getId());
        response.setWorkerId(job.getWorkerId());
        response.setSecondaryWorkerId(job.getSecondaryWorkerId());
        response.setJobType(job.getJobType());
        response.setCustomerName(job.getCustomerName());
        response.setBillingDuration(job.getBillingDuration());
        response.setStartDate(job.getStartDate());
        response.setEndDate(job.getEndDate());
        response.setCreatedAt(job.getCreatedAt());
        response.setStatus(job.getStatus());
        return response;
    }
} 