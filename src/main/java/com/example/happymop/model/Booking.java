package com.example.happymop.model;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookings")

public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceId;
    private String serviceName;
    private String serviceIds; // comma-separated service ids when multiple selected
    private int amount; // total amount in smallest currency unit (or rupees as int)
    private String location;
    private String date; // yyyy-mm-dd
    private String time; // HH:mm
    private String userName;
    private String userPhone;
    private String workerAssigned;
    private String status = "pending";
    private String paidWith;
    private Instant createdAt = Instant.now();

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getServiceId() { return serviceId; }
    public void setServiceId(String serviceId) { this.serviceId = serviceId; }
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    public String getServiceIds() { return serviceIds; }
    public void setServiceIds(String serviceIds) { this.serviceIds = serviceIds; }
    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getUserPhone() { return userPhone; }
    public void setUserPhone(String userPhone) { this.userPhone = userPhone; }
    public String getWorkerAssigned() { return workerAssigned; }
    public void setWorkerAssigned(String workerAssigned) { this.workerAssigned = workerAssigned; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPaidWith() { return paidWith; }
    public void setPaidWith(String paidWith) { this.paidWith = paidWith; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}

