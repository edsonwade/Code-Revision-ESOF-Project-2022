package ufp.esof.project.repository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import ufp.esof.project.models.base.AuditableEntity;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
@SuppressWarnings("all")
public interface BaseRepository<T extends AuditableEntity, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    @Query("SELECT e FROM #{#entityName} e WHERE e.deletedAt IS NULL")
    List<T> findAllActive();

    @Query("SELECT e FROM #{#entityName} e")
    List<T> findAllIncludingDeleted();

    @Query("SELECT e FROM #{#entityName} e WHERE e.id = :id AND e.deletedAt IS NULL")
    Optional<T> findByIdActive(ID id);

    @Query("UPDATE #{#entityName} e SET e.deletedAt = CURRENT_TIMESTAMP WHERE e.id = :id")
    void softDeleteById(ID id);

    @Query("UPDATE #{#entityName} e SET e.deletedAt = NULL WHERE e.id = :id")
    void restoreById(ID id);

    @Query("SELECT CASE WHEN e.deletedAt IS NOT NULL THEN true ELSE false END FROM #{#entityName} e WHERE e.id = :id")
    boolean isDeleted(ID id);
}
