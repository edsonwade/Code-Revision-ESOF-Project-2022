package ufp.esof.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufp.esof.project.models.College;
import ufp.esof.project.models.Course;
import ufp.esof.project.models.Degree;
import ufp.esof.project.repository.CourseRepo;
import ufp.esof.project.repository.DegreeRepo;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class DegreeService {

    private final DegreeRepo degreeRepo;
    private final CourseRepo courseRepo;
    private final CollegeService collegeService;


    public DegreeService(DegreeRepo degreeRepo, CollegeService collegeService, CourseRepo courseRepo) {
        this.degreeRepo = degreeRepo;
        this.collegeService = collegeService;
        this.courseRepo = courseRepo;
    }

    public Optional<Degree> findById(Long id) {
        return this.degreeRepo.findById(id);
    }

    public Optional<Degree> findByName(String name) {
        return this.degreeRepo.findByName(name);
    }

    public Iterable<Degree> findAllDegrees() {
        return this.degreeRepo.findAll();
    }

    public boolean deleteById(Long id) {
        Optional<Degree> optionalDegree = this.findById(id);
        if (optionalDegree.isPresent()) {
            this.degreeRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Degree> createDegree(Degree degree) {
        Degree newDegree = new Degree();
        Optional<Degree> degreeOptional = this.findByName(degree.getName());
        if (degreeOptional.isPresent())
            return Optional.empty();

        Optional<Degree> optionalDegree = validateDegreeCourses(degree, degree);
        if (optionalDegree.isPresent())
            newDegree = optionalDegree.get();

        College college = degree.getCollege();
        Optional<College> optionalCollege = collegeService.findByName(college.getName());
        if (optionalCollege.isPresent()) {
            newDegree.setCollege(optionalCollege.get());
            return Optional.of(this.degreeRepo.save(newDegree));
        }
        return Optional.empty();
    }

    public Optional<Degree> editDegree(Degree currentDegree, Degree degree, Long id) {
        Degree newDegree = new Degree();
        Optional<Degree> optionalDegree = validateDegreeCourses(currentDegree, degree);
        if (optionalDegree.isPresent())
            newDegree = optionalDegree.get();

        optionalDegree = this.degreeRepo.findByName(degree.getName());
        if (optionalDegree.isPresent() && (!optionalDegree.get().getId().equals(id)))
            return Optional.empty();

        newDegree.setName(degree.getName());

        Optional<College> optionalCollege = this.collegeService.findByName(degree.getCollege().getName());
        if (optionalCollege.isEmpty())
            return Optional.empty();

        newDegree.setCollege(optionalCollege.get());

        return Optional.of(this.degreeRepo.save(newDegree));
    }

    public Optional<Degree> validateDegreeCourses(Degree currentDegree, Degree degree) {
        Set<Course> newCourses = new HashSet<>();
        for (Course course : degree.getCourses()) {
            Optional<Course> optionalCourse = this.courseRepo.findByName(course.getName());
            if (optionalCourse.isEmpty()) {
                return Optional.empty();
            }
            Course foundCourse = optionalCourse.get();
            foundCourse.setDegree(currentDegree);
            newCourses.add(foundCourse);
        }
        currentDegree.setCourses(newCourses);
        return Optional.of(currentDegree);
    }
}
