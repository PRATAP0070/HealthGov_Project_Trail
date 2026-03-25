package com.healthgov.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "audit_log",
    indexes = {
        @Index(name = "idx_auditlog_user", columnList = "user_id"),
        @Index(name = "idx_auditlog_timestamp", columnList = "timestamp")
    }
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
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

