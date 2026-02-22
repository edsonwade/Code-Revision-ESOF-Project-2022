package ufp.esof.project.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityAuditService {

    private final SecurityAuditRepository auditRepository;

    public void logAction(Long userId, String action, String entityType, Long entityId, String ipAddress, String userAgent, String details) {
        SecurityAuditLog auditLog = new SecurityAuditLog();
        auditLog.setUserId(userId);
        auditLog.setAction(action);
        auditLog.setEntityType(entityType);
        auditLog.setEntityId(entityId);
        auditLog.setIpAddress(ipAddress);
        auditLog.setUserAgent(userAgent);
        auditLog.setDetails(details);
        auditRepository.save(auditLog);
    }
}
