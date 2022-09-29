package ufp.esof.project.services;

import org.springframework.stereotype.Component;
import ufp.esof.project.filters.FilterObject;
import ufp.esof.project.filters.explainerFilters.ExplainerFilterService;
import ufp.esof.project.models.Course;
import ufp.esof.project.models.Explainer;
import ufp.esof.project.repositories.CourseRepo;
import ufp.esof.project.repositories.ExplainerRepo;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class ExplainerServiceImpl implements ExplainerService {

    private ExplainerFilterService explainerFilterService;

    private ExplainerRepo explainerRepo;

    private CourseRepo courseRepo;

    public ExplainerServiceImpl(ExplainerFilterService explainerFilterService, ExplainerRepo explainerRepo, CourseRepo courseRepo) {
        this.explainerRepo = explainerRepo;
        this.courseRepo = courseRepo;
        this.explainerFilterService = explainerFilterService;
    }

    public Optional<Explainer> getById(long id) {
        return explainerRepo.findById(id);
    }

    public Set<Explainer> getFilteredExplainer(FilterObject filterObject) {
        return explainerFilterService.filterExplainer(findAllExplainers(), filterObject);

        // courseFilterService.filterCourses(getAllCourses(), filterObject);
    }

    public Optional<Explainer> findExplainerByName(String name) {
        return this.explainerRepo.findByName(name);
    }

    public Set<Explainer> findAllExplainers() {
        Set<Explainer> explainers = new HashSet<>();
        for (Explainer explainer : this.explainerRepo.findAll()) {
            explainers.add(explainer);
        }
        return Collections.unmodifiableSet(explainers);
    }

    public Optional<Explainer> saveExplainer(Explainer explainer) {
        Explainer newExplainer = new Explainer();
        Optional<Explainer> explainerOptional = this.findExplainerByName(explainer.getName());
        if (explainerOptional.isPresent())
            return Optional.empty();

        explainerOptional = validateExplainerCourses(explainer, explainer);
        if (explainerOptional.isPresent())
            newExplainer = explainerOptional.get();

        return Optional.of(this.explainerRepo.save(newExplainer));
    }

    public Optional<Explainer> editExplainer(Explainer currentExplainer, Explainer explainer, Long id) {
        Explainer newExplainer = new Explainer();
        Optional<Explainer> optionalExplainer = validateExplainerCourses(currentExplainer, explainer);
        if (optionalExplainer.isPresent())
            newExplainer = optionalExplainer.get();

        optionalExplainer = this.explainerRepo.findByName(explainer.getName());
        if (optionalExplainer.isPresent() && (!optionalExplainer.get().getId().equals(id)))
            return Optional.empty();

        newExplainer.setName(explainer.getName());

        return Optional.of(this.explainerRepo.save(newExplainer));
    }

    public boolean deleteById(Long id) {
        Optional<Explainer> optionalExplainer = this.getById(id);
        if (optionalExplainer.isPresent()) {
            this.explainerRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Explainer> validateExplainerCourses(Explainer currentExplainer, Explainer explainer) {
        Set<Course> newCourses = new HashSet<>();
        for (Course course : explainer.getCourses()) {
            Optional<Course> optionalCourse = this.courseRepo.findByName(course.getName());
            if (optionalCourse.isEmpty())
                return Optional.empty();
            Course foundCourse = optionalCourse.get();
            foundCourse.addExplainer(currentExplainer);
            newCourses.add(foundCourse);
        }
        currentExplainer.setCourses(newCourses);
        return Optional.of(currentExplainer);
    }
}