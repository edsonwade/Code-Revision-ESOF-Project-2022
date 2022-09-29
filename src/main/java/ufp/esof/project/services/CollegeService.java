package ufp.esof.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ufp.esof.project.models.College;
import ufp.esof.project.models.Degree;
import ufp.esof.project.repositories.CollegeRepo;
import ufp.esof.project.repositories.DegreeRepo;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CollegeService {

    private CollegeRepo collegeRepo;

    private DegreeRepo degreeRepo;

    @Autowired
    public CollegeService(CollegeRepo collegeRepo, DegreeRepo degreeRepo) {
        this.collegeRepo = collegeRepo;
        this.degreeRepo = degreeRepo;
    }

    public Optional<College> findById(Long id) {
        return this.collegeRepo.findById(id);
    }

    public Optional<College> findByName(String name) {
        return this.collegeRepo.findByName(name);
    }

    public Iterable<College> getAllColleges() {
        return this.collegeRepo.findAll();
    }

    public boolean deleteById(Long id) {
        Optional<College> optionalCollege = this.findById(id);
        if (optionalCollege.isPresent()) {
            if (optionalCollege.get().getDegrees().isEmpty())
                this.collegeRepo.deleteById(id);
            else
                return false;

            return true;
        }
        return false;
    }

    public Optional<College> createCollege(College college) {
        College newCollege = new College();

        Optional<College> optionalCollege = this.validateDegrees(college, college);
        if (optionalCollege.isPresent())
            newCollege = optionalCollege.get();

        optionalCollege = this.collegeRepo.findByName(college.getName());
        if (optionalCollege.isPresent())
            return Optional.empty();

        newCollege.setName(college.getName());

        return Optional.of(this.collegeRepo.save(newCollege));
    }

    public Optional<College> editCollege(College currentCollege, College college, Long id) {
        College newCollege = new College();
        Optional<College> optionalCollege = this.validateDegrees(currentCollege, college);
        if (optionalCollege.isPresent())
            newCollege = optionalCollege.get();

        optionalCollege = this.collegeRepo.findByName(college.getName());
        if (optionalCollege.isPresent() && (!optionalCollege.get().getId().equals(id)))
            return Optional.empty();

        newCollege.setName(college.getName());

        return Optional.of(this.collegeRepo.save(newCollege));
    }

    public Optional<College> validateDegrees(College currentCollege, College college) {
        Set<Degree> newDegrees = new HashSet<>();
        for (Degree degree : college.getDegrees()) {
            Optional<Degree> optionalDegree = this.degreeRepo.findByName(degree.getName());
            if (optionalDegree.isEmpty())
                return Optional.empty();
            Degree foundDegree = optionalDegree.get();
            foundDegree.setCollege(currentCollege);
            newDegrees.add(foundDegree);
        }

        currentCollege.setDegrees(newDegrees);
        return Optional.of(currentCollege);
    }
}