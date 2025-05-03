package com.care.m4u.job.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long workerId;

    @Column
    private Long secondaryWorkerId;

    @Column
    private String jobType;

    @Column
    private String customerName;

    @Column
    private String billingDuration;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @Column
    private Date createdAt;

    @Column
    private String status;



    public Job() {
    }

    public Long getId() {
        return id;
    }

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    public String getBillingDuration() {
        return billingDuration;
    }

    public void setBillingDuration(String billingDuration) {
        this.billingDuration = billingDuration;
    }

    public Long getSecondaryWorkerId() {
        return secondaryWorkerId;
    }

    public void setSecondaryWorkerId(Long secondaryWorkerId) {
        this.secondaryWorkerId = secondaryWorkerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
