package com.example.btcsedge.service;


import com.example.btcsedge.domain.enums.AuditEventType;
import com.example.btcsedge.domain.model.AuditLog;
import com.example.btcsedge.domain.model.User;
import com.example.btcsedge.repo.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    @Transactional
    public void loginSuccess(String username, String ip, String userAgent) {
        save(AuditEventType.LOGIN_SUCCESS, null, username, ip, userAgent, true, null, "/auth/login", "POST");
        log.info("LOGIN_SUCCESS user={} ip={}", username, ip);
    }

    @Transactional
    public void loginFailed(String username, String ip, String userAgent) {
        save(AuditEventType.LOGIN_FAILED, null, username, ip, userAgent, false, "Bad credentials", "/auth/login", "POST");
        log.warn("LOGIN_FAILED user={} ip={}", username, ip);
    }

    /**
     * AuthService.refresh(...) ichidan chaqiriladi
     */
    @Transactional
    public void refresh(String username, String ip, String userAgent) {
        save(AuditEventType.TOKEN_REFRESH, null, username, ip, userAgent, true, null, "/auth/refresh", "POST");
        log.info("TOKEN_REFRESH user={} ip={}", username, ip);
    }

    @Transactional
    public void logout(String username, String ip, String userAgent) {
        save(AuditEventType.LOGOUT, null, username, ip, userAgent, true, null, "/auth/logout", "POST");
        log.info("LOGOUT user={} ip={}", username, ip);
    }

    /**
     * Agar User obyekti qo'lingda bo'lsa (userId ham yozilsin desang)
     */
    @Transactional
    public void loginSuccess(User user, String ip, String userAgent) {
        save(AuditEventType.LOGIN_SUCCESS, user.getId(), user.getUsername(), ip, userAgent, true, null, "/auth/login", "POST");
        log.info("LOGIN_SUCCESS userId={} user={} ip={}", user.getId(), user.getUsername(), ip);
    }

    @Transactional
    public void refresh(User user, String ip, String userAgent) {
        save(AuditEventType.TOKEN_REFRESH, user.getId(), user.getUsername(), ip, userAgent, true, null, "/auth/refresh", "POST");
        log.info("TOKEN_REFRESH userId={} user={} ip={}", user.getId(), user.getUsername(), ip);
    }

    @Transactional
    public void logout(User user, String ip, String userAgent) {
        save(AuditEventType.LOGOUT, user.getId(), user.getUsername(), ip, userAgent, true, null, "/auth/logout", "POST");
        log.info("LOGOUT userId={} user={} ip={}", user.getId(), user.getUsername(), ip);
    }

    private void save(AuditEventType type,
                      Long userId,
                      String username,
                      String ip,
                      String userAgent,
                      boolean success,
                      String message,
                      String path,
                      String method) {

        // username null bo'lib qolmasin
        String safeUsername = (username == null || username.isBlank()) ? "unknown" : username;

        AuditLog row = AuditLog.builder()
                .eventType(type)
                .userId(userId)
                .username(safeUsername)
                .ip(ip)
                .userAgent(userAgent)
                .success(success)
                .message(message)
                .path(path)
                .method(method)
                .createdAt(Instant.now())
                .build();

        auditLogRepository.save(row);
    }
}


