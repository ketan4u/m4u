package com.care.m4u.worker.model;

import jakarta.persistence.*;


@Entity
@Table(name = "workers")
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String email;
    
    @Column(nullable = false)
    private String service;

    @Column(nullable = false)
    private float experience;

    @Column
    private Long phoneNo;

    @Column
    private String[] previousWorks;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    public Worker() {
    }

    public Worker(String name, String service, float experience, Long phoneNo, Double latitude, Double longitude) {
        this.name = name;
        this.service = service;
        this.experience = experience;
        this.phoneNo = phoneNo;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Worker(String name, String service) {
        this.name = name;
        this.service = service;
    }

    public Long getId() {
        return id;
    }

    //not needed because it is auto generated using JPA.
//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public float getExperience() {
        return experience;
    }

    public void setExperience(float experience) {
        this.experience = experience;
    }

    public Long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(Long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String[] getPreviousWorks() {
        return previousWorks;
    }

    public void setPreviousWorks(String[] previousWorks) {
        this.previousWorks = previousWorks;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
