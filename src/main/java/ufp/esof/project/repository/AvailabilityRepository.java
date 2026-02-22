package ufp.esof.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ufp.esof.project.models.Availability;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("all")
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByExplainerIdAndDayOfWeek(Long explainerId, DayOfWeek dayOfWeek);
    Optional<Availability> findByAvailabilityById(Long id);

}
