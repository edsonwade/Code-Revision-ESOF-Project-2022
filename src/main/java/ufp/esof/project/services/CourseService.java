package ufp.esof.project.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufp.esof.project.models.Course;
import ufp.esof.project.models.Degree;
import ufp.esof.project.models.Explainer;
import ufp.esof.project.repository.CourseRepo;

@Service
@Transactional
public class CourseService {

    private final CourseRepo courseRepo;
    private final DegreeService degreeService;
    private final ExplainerService explainerService;

    public CourseService(CourseRepo courseRepo, DegreeService degreeService, ExplainerService explainerService) {
        this.courseRepo = courseRepo;
        this.degreeService = degreeService;
        this.explainerService = explainerService;
    }

    @Cacheable(value = "courses", key = "'all'")
    public Iterable<Course> findAllCourses() {
        return this.courseRepo.findAll();
    }

    @Cacheable(value = "courses", key = "#id")
    public Optional<Course> findById(Long id) {
        return this.courseRepo.findById(id);
    }

    @CacheEvict(value = "courses", allEntries = true)
    public boolean deleteById(Long id) {
        Optional<Course> optionalCourse = this.findById(id);
        if (optionalCourse.isPresent()) {
            this.courseRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @CacheEvict(value = "courses", allEntries = true)
    public Optional<Course> createCourse(Course course) {
        Course newCourse = new Course();
        Optional<Course> optionalCourse = this.validateExplainers(course, course);
        if (optionalCourse.isPresent())
            newCourse = optionalCourse.get();

        optionalCourse = this.courseRepo.findByName(course.getName());
        if (optionalCourse.isPresent())
            return Optional.empty();

        Degree degree = course.getDegree();
        Optional<Degree> optionalDegree = this.degreeService.findByName(degree.getName());
        if (optionalDegree.isPresent()) {
            newCourse.setDegree(optionalDegree.get());
            return Optional.of(this.courseRepo.save(newCourse));
        }
        return Optional.empty();
    }

    @CacheEvict(value = "courses", allEntries = true)
    public Optional<Course> editCourse(Course currentCourse, Course course, Long id) {
        Course newCourse = new Course();
        Optional<Course> optionalCourse = this.validateExplainers(currentCourse, course);
        if (optionalCourse.isPresent())
            newCourse = optionalCourse.get();

        optionalCourse = this.courseRepo.findByName(course.getName());
        if (optionalCourse.isPresent() && (!optionalCourse.get().getId().equals(id)))
            return Optional.empty();

        newCourse.setName(course.getName());

        Optional<Degree> optionalDegree = this.degreeService.findByName(course.getDegree().getName());
        if (optionalDegree.isEmpty())
            return Optional.empty();

        newCourse.setDegree(optionalDegree.get());

        return Optional.of(this.courseRepo.save(newCourse));
    }

    public Optional<Course> validateExplainers(Course currentCourse, Course course) {
        Set<Explainer> newExplainers = new HashSet<>();
        for (Explainer explainer : course.getExplainers()) {
            Optional<Explainer> optionalExplainer = this.explainerService.findExplainerByName(explainer.getName());
            if (optionalExplainer.isEmpty())
                return Optional.empty();
            Explainer foundExplainer = optionalExplainer.get();
            foundExplainer.addCourse(currentCourse);
            newExplainers.add(foundExplainer);
        }

        currentCourse.setExplainers(newExplainers);
        return Optional.of(currentCourse);
    }
}
