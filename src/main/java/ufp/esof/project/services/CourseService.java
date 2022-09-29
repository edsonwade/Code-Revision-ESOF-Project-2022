package ufp.esof.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufp.esof.project.models.Course;
import ufp.esof.project.models.Degree;
import ufp.esof.project.models.Explainer;
import ufp.esof.project.repositories.CourseRepo;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CourseService {

    private CourseRepo courseRepo;
    private DegreeService degreeService;
    private ExplainerService explainerService;

    @Autowired
    public CourseService(CourseRepo courseRepo, DegreeService degreeService, ExplainerService explainerService) {
        this.courseRepo = courseRepo;
        this.degreeService = degreeService;
        this.explainerService = explainerService;
    }

    public Iterable<Course> findAllCourses() {
        return this.courseRepo.findAll();
    }

    public Optional<Course> findById(Long id) {
        return this.courseRepo.findById(id);
    }

    public boolean deleteById(Long id) {
        Optional<Course> optionalCourse = this.findById(id);
        if (optionalCourse.isPresent()) {
            this.courseRepo.deleteById(id);
            return true;
        }
        return false;
    }

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
