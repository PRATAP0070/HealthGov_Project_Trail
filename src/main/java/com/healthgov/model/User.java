package com.healthgov.model;

import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
 
@Entity
@Table(name = "users")
public class User {
    @Id
    private Long userId;
    private String name;
    private String role;
    private String email;
    private String phone;
    private String status;
 
    @OneToMany(mappedBy = "user")
    private List<AuditLog> auditLogs;
 
    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;
}
