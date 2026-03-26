package com.healthgov.model;

import com.healthgov.enums.UserRole;
import com.healthgov.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotNull(message = "User role is required")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Phone number is mandatory")
    private String phone;

    @Column(nullable = false)
	@JsonIgnore
	private String password;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private UserStatus status;  // ACTIVE, INACTIVE, SUSPENDED

    // DO NOT TOUCH – used by other modules
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuditLog> auditLogs = new ArrayList<>();

    // DO NOT TOUCH – used by other modules
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();
}

