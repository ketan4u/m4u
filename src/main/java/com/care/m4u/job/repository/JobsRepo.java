package com.care.m4u.job.repository;

import com.care.m4u.job.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobsRepo extends JpaRepository<Job, Long> {

    Optional<Job> findByCustomerName(String customerName);
}
