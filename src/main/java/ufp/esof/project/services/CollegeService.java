package ufp.esof.project.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufp.esof.project.models.College;
import ufp.esof.project.models.Degree;
import ufp.esof.project.repository.CollegeRepo;
import ufp.esof.project.repository.DegreeRepo;

@Service
@Transactional
public class CollegeService {

    private final CollegeRepo collegeRepo;

    private final DegreeRepo degreeRepo;

    public CollegeService(CollegeRepo collegeRepo, DegreeRepo degreeRepo) {
        this.collegeRepo = collegeRepo;
        this.degreeRepo = degreeRepo;
    }

    @Cacheable(value = "colleges", key = "#id")
    public Optional<College> findById(Long id) {
        return this.collegeRepo.findById(id);
    }

    @Cacheable(value = "colleges", key = "#name")
    public Optional<College> findByName(String name) {
        return this.collegeRepo.findByName(name);
    }

    @Cacheable(value = "colleges", key = "'all'")
    public Iterable<College> getAllColleges() {
        return this.collegeRepo.findAll();
    }

    @CacheEvict(value = "colleges", allEntries = true)
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

    @CacheEvict(value = "colleges", allEntries = true)
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

    @CacheEvict(value = "colleges", allEntries = true)
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