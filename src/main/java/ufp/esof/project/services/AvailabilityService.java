package ufp.esof.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufp.esof.project.dto.AvailabilityDto;
import ufp.esof.project.models.Availability;
import ufp.esof.project.models.Explainer;
import ufp.esof.project.repositories.AvailabilityRepository;
import ufp.esof.project.repositories.ExplainerRepository;

import java.util.Optional;

@Service
public class AvailabilityService {

    private AvailabilityRepository availabilityRepository;

    private ExplainerRepository explainerRepository;

    @Autowired
    public AvailabilityService(AvailabilityRepository availabilityRepository, ExplainerRepository explainerRepository) {
        this.availabilityRepository = availabilityRepository;
        this.explainerRepository = explainerRepository;
    }

    public Iterable<Availability> findAll() {
        return this.availabilityRepository.findAll();
    }

    public Optional<Availability> findById(Long id) {
        return this.availabilityRepository.findById(id);
    }

    public boolean deleteById(Long id) {
        Optional<Availability> optionalAvailability = this.findById(id);
        if (optionalAvailability.isPresent()) {
            this.availabilityRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Availability> createAvailability(AvailabilityDto availability) {
        Availability newAvailability = new Availability();

        Optional<Explainer> optionalExplainer = this.explainerRepository.findByName(availability.getExplainer().getName());
        if (optionalExplainer.isEmpty())
            return Optional.empty();

        newAvailability.setExplainer(optionalExplainer.get());

        //TODO: add validations
        newAvailability.setDayOfWeek(availability.getDayOfWeek());
        newAvailability.setStart(availability.getStart());
        newAvailability.setEnd(availability.getEnd());
        newAvailability.setDayOfWeek(availability.getDayOfWeek());

        return Optional.of(this.availabilityRepository.save(newAvailability));
    }

    public Optional<Availability> editAvailability(Availability availability, Long id) {
        Availability newAvailability;

        Optional<Availability> optionalAvailability = this.availabilityRepository.findById(id);
        if (optionalAvailability.isEmpty())
            return Optional.empty();

        newAvailability = optionalAvailability.get();

        Optional<Explainer> optionalExplainer = this.explainerRepository.findByName(availability.getExplainer().getName());
        if (optionalExplainer.isEmpty())
            return Optional.empty();

        newAvailability.setExplainer(optionalExplainer.get());

        //TODO: add validations
        newAvailability.setDayOfWeek(availability.getDayOfWeek());
        newAvailability.setStart(availability.getStart());
        newAvailability.setEnd(availability.getEnd());
        newAvailability.setDayOfWeek(availability.getDayOfWeek());

        return Optional.of(this.availabilityRepository.save(newAvailability));
    }

}
