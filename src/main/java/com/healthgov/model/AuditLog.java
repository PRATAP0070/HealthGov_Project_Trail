package com.healthgov.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditId;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "user_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_auditlog_user")
    )
    private Users user;

    @NotBlank
    private String action;   // PROJECT_CREATED, PROJECT_APPROVED...

    @NotBlank
    private String resource; // PROJECT / GRANT / APPLICATION

    @NotNull
    private LocalDateTime timestamp;
}

