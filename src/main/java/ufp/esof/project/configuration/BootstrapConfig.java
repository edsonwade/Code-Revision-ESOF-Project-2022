/*
package ufp.esof.project.configuration;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ufp.esof.project.models.*;
import ufp.esof.project.repositories.*;


import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static ufp.esof.project.models.Language.*;

@Component
@Transactional
public class BootstrapConfig implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(BootstrapConfig.class);

    private final ExplainerRepository explainerRepo;
    private final CourseRepo courseRepo;
    private final AppointmentRepository appointmentRepo;
    private final StudentRepository studentRepo;
    private final CollegeRepo collegeRepo;
    private final DegreeRepo degreeRepo;
    private final AvailabilityRepository availabilityRepo;

    @Autowired
    public BootstrapConfig(ExplainerRepository explainerRepo, CourseRepo courseRepo, AvailabilityRepository availabilityRepo, DegreeRepo degreeRepo, CollegeRepo collegeRepo, AppointmentRepository appointmentRepo, StudentRepository studentRepo) {
        this.explainerRepo = explainerRepo;
        this.courseRepo = courseRepo;
        this.studentRepo = studentRepo;
        this.appointmentRepo = appointmentRepo;
        this.availabilityRepo = availabilityRepo;
        this.collegeRepo = collegeRepo;
        this.degreeRepo = degreeRepo;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.debug("Startup");

        Degree degree = new Degree("Engenharia Informática");

        this.degreeRepo.save(degree);

        ArrayList<College> colleges = new ArrayList<>();
        colleges.add(new College("Faculdade de Ciências da UFP"));
        colleges.add(new College("Faculdade de Letras da UFP"));
        colleges.add(new College("Faculdade de Arquitectura da UFP"));

        this.collegeRepo.saveAll(colleges);

        ArrayList<Course> courses = new ArrayList<>();
        courses.add(new Course("Engenharia de Software"));
        courses.add(new Course("Bases de dados"));

        this.courseRepo.saveAll(courses);

        Explainer explainer1 = new Explainer("Alexandro", PORTUGUESE);
        Explainer explainer2 = new Explainer("Feliz", ITALIAN);
        Explainer explainer3 = new Explainer("Borges Gouveia", SPANISH);
        Explainer explainer4 = new Explainer("André", ENGLISH);


        Set<Explainer> explainers = new HashSet<>();
        explainers.add(explainer1);
        explainers.add(explainer2);
        explainers.add(explainer3);
        explainers.add(explainer4);

        explainerRepo.saveAll(explainers);

        LocalTime start1 = LocalTime.of(10, 0);
        LocalTime end1 = LocalTime.of(11, 30);
        LocalTime start2 = LocalTime.of(9, 30);
        LocalTime end2 = LocalTime.of(10, 0);

        DayOfWeek dayOfWeek0 = DayOfWeek.MONDAY;
        DayOfWeek dayOfWeek1 = DayOfWeek.FRIDAY;

        LocalDateTime start = LocalDateTime.of(2020, Month.DECEMBER, 2, 15, 30);
        LocalDateTime end = LocalDateTime.of(2020, Month.DECEMBER, 2, 16, 0);
        LocalDateTime starts = LocalDateTime.of(2020, Month.JANUARY, 16, 9, 0);
        LocalDateTime ends = LocalDateTime.of(2020, Month.JANUARY, 16, 9, 30);

        Set<Availability> availabilities1 = new HashSet<>();
        Availability availability1 = new Availability(dayOfWeek0, start1, end1);
        availability1.setExplainer(explainer1);
        availabilities1.add(availability1);

        Availability availability2 = new Availability(dayOfWeek1, start2, end2);
        availability2.setExplainer(explainer2);
        availabilities1.add(availability2);

        explainer1.setAvailabilities(availabilities1);

        this.availabilityRepo.saveAll(availabilities1);

        Set<Appointment> appointments1 = new HashSet<>();
        Appointment appointment1 = new Appointment(start, end);
        appointment1.setExplainer(explainer1);
        appointments1.add(appointment1);

        Set<Appointment> appointments2 = new HashSet<>();
        Appointment appointment2 = new Appointment(starts, ends);
        appointment2.setExplainer(explainer2);
        appointments2.add(appointment2);

        this.appointmentRepo.saveAll(appointments1);
        this.appointmentRepo.saveAll(appointments2);

        Set<Student> students = new HashSet<>();
        students.add(new Student("João"));
        students.add(new Student("Vanilson"));
        students.add(new Student("Filipe"));
        students.add(new Student("rui"));
        students.add(new Student("mario"));
        students.add(new Student("matilde"));

        this.studentRepo.saveAll(students);
    }
}
*/