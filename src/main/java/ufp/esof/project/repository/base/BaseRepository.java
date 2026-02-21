package ufp.esof.project.repository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import ufp.esof.project.models.base.AuditableEntity;

import java.util.List;
import java.util.Optional;

/**
 * Base repository interface providing common CRUD operations and soft-delete support
 * for all entities extending AuditableEntity.
 *
 * @param <T>  the entity type
 * @param <ID> the ID type
 */
@NoRepositoryBean
public interface BaseRepository<T extends AuditableEntity, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    /**
     * Find all non-deleted entities.
     *
     * @return list of all non-deleted entities
     */
    List<T> findAllActive();

    /**
     * Find all entities including soft-deleted ones.
     *
     * @return list of all entities
     */
    List<T> findAllIncludingDeleted();

    /**
     * Find a non-deleted entity by ID.
     *
     * @param id the entity ID
     * @return Optional containing the entity if found, empty otherwise
     */
    Optional<T> findByIdActive(ID id);

    /**
     * Soft delete an entity by ID.
     *
     * @param id the entity ID
     */
    void softDeleteById(ID id);

    /**
     * Restore a soft-deleted entity by ID.
     *
     * @param id the entity ID
     */
    void restoreById(ID id);

    /**
     * Check if an entity is deleted.
     *
     * @param id the entity ID
     * @return true if deleted, false otherwise
     */
    boolean isDeleted(ID id);
}

