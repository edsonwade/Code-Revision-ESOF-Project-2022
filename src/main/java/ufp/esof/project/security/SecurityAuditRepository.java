package ufp.esof.project.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityAuditRepository extends JpaRepository<SecurityAuditLog, Long> {
}
