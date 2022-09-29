package ufp.esof.project.models;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DegreeTest {

    @Test
    void degreeName() {
        Degree degree = new Degree("Curso");
        assertEquals(degree.getName(), "Curso");
    }

    @Test
    void degree() {
        Degree degree = new Degree();
        assertNotEquals(degree.getId(), 0);
    }

    @Test
    void degreeArgs() {
        Set<Course> courses = new HashSet<>();
        courses.add(new Course("Disciplina 1"));
        courses.add(new Course("Disciplina 2"));
        courses.add(new Course("Disciplina 3"));

        College college = new College("UFP");

        Degree degree = new Degree(10L, "Curso 1", courses, college);

        assertEquals(degree.getId(), 10L);
        assertEquals(degree.getName(), "Curso 1");
        assertFalse(degree.getCourses().isEmpty());
        assertEquals(degree.getCourses().size(), 3);
        assertEquals(degree.getCollege().getName(), "UFP");
    }
}
