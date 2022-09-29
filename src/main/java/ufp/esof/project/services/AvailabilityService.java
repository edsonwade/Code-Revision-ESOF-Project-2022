package ufp.esof.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufp.esof.project.models.Availability;
import ufp.esof.project.models.Explainer;
import ufp.esof.project.repositories.AvailabilityRepo;
import ufp.esof.project.repositories.ExplainerRepo;

import java.util.Optional;

@Service
public class AvailabilityService {

    private AvailabilityRepo availabilityRepo;

    private ExplainerRepo explainerRepo;

    @Autowired
    public AvailabilityService(AvailabilityRepo availabilityRepo, ExplainerRepo explainerRepo) {
        this.availabilityRepo = availabilityRepo;
        this.explainerRepo = explainerRepo;
    }

    public Iterable<Availability> findAll() {
        return this.availabilityRepo.findAll();
    }

    public Optional<Availability> findById(Long id) {
        return this.availabilityRepo.findById(id);
    }

    public boolean deleteById(Long id) {
        Optional<Availability> optionalAvailability = this.findById(id);
        if (optionalAvailability.isPresent()) {
            this.availabilityRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Availability> createAvailability(Availability availability) {
        Availability newAvailability = new Availability();

        Optional<Explainer> optionalExplainer = this.explainerRepo.findByName(availability.getExplainer().getName());
        if (optionalExplainer.isEmpty())
            return Optional.empty();

        newAvailability.setExplainer(optionalExplainer.get());

        //TODO: add validations
        newAvailability.setDayOfWeek(availability.getDayOfWeek());
        newAvailability.setStart(availability.getStart());
        newAvailability.setEnd(availability.getEnd());
        newAvailability.setDayOfWeek(availability.getDayOfWeek());

        return Optional.of(this.availabilityRepo.save(newAvailability));
    }

    public Optional<Availability> editAvailability(Availability currentAvailability, Availability availability, Long id) {
        Availability newAvailability;

        Optional<Availability> optionalAvailability = this.availabilityRepo.findById(id);
        if (optionalAvailability.isEmpty())
            return Optional.empty();

        newAvailability = optionalAvailability.get();

        Optional<Explainer> optionalExplainer = this.explainerRepo.findByName(availability.getExplainer().getName());
        if (optionalExplainer.isEmpty())
            return Optional.empty();

        newAvailability.setExplainer(optionalExplainer.get());

        //TODO: add validations
        newAvailability.setDayOfWeek(availability.getDayOfWeek());
        newAvailability.setStart(availability.getStart());
        newAvailability.setEnd(availability.getEnd());
        newAvailability.setDayOfWeek(availability.getDayOfWeek());

        return Optional.of(this.availabilityRepo.save(newAvailability));
    }

}
