package ufp.esof.project.services;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufp.esof.project.dto.*;
import ufp.esof.project.models.*;

import java.util.*;
import ufp.esof.project.repository.CourseRepo;
import ufp.esof.project.repository.ExplainerRepository;

@Service
@Transactional
public class ExplainerServiceImpl implements ExplainerService {


    private final ExplainerRepository explainerRepository;

    private final CourseRepo courseRepo;

    public ExplainerServiceImpl(ExplainerRepository explainerRepository, CourseRepo courseRepo) {
        this.explainerRepository = explainerRepository;
        this.courseRepo = courseRepo;
    }

    @Cacheable(value = "explainers", key = "#id")
    public Optional<Explainer> getById(long id) {
        return explainerRepository.findById(id);
    }

    public Set<Explainer> getFilteredExplainer(Object filterObject) {
        return null;

    }

    @Cacheable(value = "explainers", key = "#name")
    public Optional<Explainer> findExplainerByName(String name) {
        return this.explainerRepository.findByName(name);
    }

    @Cacheable(value = "explainers", key = "'all'")
    public Set<Explainer> findAllExplainers() {
        Set<Explainer> explainers = new HashSet<>();
        for (Explainer explainer : this.explainerRepository.findAll()) {
            explainers.add(explainer);
        }
        return Collections.unmodifiableSet(explainers);
    }

    @Override
    @CacheEvict(value = "explainers", allEntries = true)
    public Optional<Explainer> saveExplainer(ExplainerDto explainer) {
        return Optional.empty();
    }

    @Override
    @CacheEvict(value = "explainers", allEntries = true)
    public Optional<Explainer> editExplainer(Explainer currentExplainer, ExplainerDto explainer, Long id) {
        return Optional.empty();
    }

    @CacheEvict(value = "explainers", allEntries = true)
    public Optional<Explainer> saveExplainer(Explainer explainer) {
        Explainer newExplainer = new Explainer();
        Optional<Explainer> explainerOptional = this.findExplainerByName(explainer.getName());
        if (explainerOptional.isPresent())
            return Optional.empty();

        explainerOptional = validateExplainerCourses(explainer, explainer);
        if (explainerOptional.isPresent())
            newExplainer = explainerOptional.get();

        return Optional.of(this.explainerRepository.save(newExplainer));
    }

    @CacheEvict(value = "explainers", allEntries = true)
    public Optional<Explainer> editExplainer(Explainer currentExplainer, Explainer explainer, Long id) {
        Explainer newExplainer = new Explainer();
        Optional<Explainer> optionalExplainer = validateExplainerCourses(currentExplainer, explainer);
        if (optionalExplainer.isPresent())
            newExplainer = optionalExplainer.get();

        optionalExplainer = this.explainerRepository.findByName(explainer.getName());
        if (optionalExplainer.isPresent() && (!optionalExplainer.get().getId().equals(id)))
            return Optional.empty();

        newExplainer.setName(explainer.getName());

        return Optional.of(this.explainerRepository.save(newExplainer));
    }

    @CacheEvict(value = "explainers", allEntries = true)
    public boolean deleteById(Long id) {
        Optional<Explainer> optionalExplainer = this.getById(id);
        if (optionalExplainer.isPresent()) {
            this.explainerRepository.deleteById(id);
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