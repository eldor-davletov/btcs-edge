package com.example.btcsedge.domain.model;



import com.example.btcsedge.domain.enums.AuditEventType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "audit_logs", indexes = {
        @Index(name = "ix_audit_created_at", columnList = "created_at"),
        @Index(name = "ix_audit_username", columnList = "username"),
        @Index(name = "ix_audit_user_id", columnList = "user_id"),
        @Index(name = "ix_audit_event_type", columnList = "event_type")
})
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false, length = 40)
    private AuditEventType eventType;

    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, length = 120)
    private String username;

    @Column(length = 64)
    private String ip;

    @Column(name = "user_agent", length = 255)
    private String userAgent;

    /**
     * login_failed uchun false, qolganlari true bo'lishi mumkin
     */
    @Column(nullable = false)
    private boolean success;

    /**
     * qo'shimcha izoh/xato sababi, qisqa qilib
     */
    @Column(length = 500)
    private String message;

    /**
     * qaysi endpointdan kelganini saqlash ixtiyoriy
     */
    @Column(length = 120)
    private String path;

    @Column(length = 10)
    private String method;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
}


