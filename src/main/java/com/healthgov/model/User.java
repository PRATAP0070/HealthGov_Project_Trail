package com.healthgov.model;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class User {
	@Id
	//@Min(value = 2500001)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(nullable = false, length = 100)
	private String name;

    @Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private Role role;

    
	@Column(nullable = false, unique = true, length = 120)
	private String email;

	@Column(nullable = false, unique = true, length = 15)
	private String phone;

	@Column(nullable = false, length = 20)
	private String status;
	
	//@Min(value = 8)
	@Column(nullable = false)
	@JsonIgnore
	private String password;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<AuditLog> auditLogs;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Notification> notifications;
=======
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
>>>>>>> origin/res-infra
}
