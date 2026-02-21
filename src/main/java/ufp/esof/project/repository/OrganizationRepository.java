package ufp.esof.project.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ufp.esof.project.models.Organization;
import ufp.esof.project.repository.base.BaseRepository;

import java.util.Optional;

/**
 * Repository for Organization entities.
 * Manages multi-tenant organization data with soft-delete support.
 */
@Repository
public interface OrganizationRepository extends BaseRepository<Organization, Long> {

    /**
     * Find an organization by tenant ID (slug).
     *
     * @param tenantId the tenant ID
     * @return Optional containing the organization if found
     */
    Optional<Organization> findByTenantId(String tenantId);

    /**
     * Find an active organization by tenant ID.
     *
     * @param tenantId the tenant ID
     * @return Optional containing the organization if found and active
     */
    @Query("SELECT o FROM Organization o WHERE o.tenantId = :tenantId AND o.active = true AND o.deletedAt IS NULL")
    Optional<Organization> findActiveByTenantId(@Param("tenantId") String tenantId);

    /**
     * Find an organization by email.
     *
     * @param email the organization email
     * @return Optional containing the organization if found
     */
    Optional<Organization> findByEmail(String email);

    /**
     * Check if tenant ID already exists.
     *
     * @param tenantId the tenant ID to check
     * @return true if exists and not deleted, false otherwise
     */
    @Query("SELECT COUNT(o) > 0 FROM Organization o WHERE o.tenantId = :tenantId AND o.deletedAt IS NULL")
    boolean existsByTenantId(@Param("tenantId") String tenantId);
}

