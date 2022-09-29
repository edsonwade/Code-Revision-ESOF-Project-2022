package ufp.esof.project.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseTest {

    @Test
    void addExplainer() {
        Course course = new Course();
        assertEquals(course.getExplainers().size(), 0);
        course.addExplainer(new Explainer("Explainer"));
        assertEquals(course.getExplainers().size(), 1);
    }

    @Test
    void removeExplainer() {
        Course course = new Course();
        Explainer explainer = new Explainer("Test");
        course.addExplainer(explainer);

        assertEquals(course.getExplainers().size(), 1);

        course.removeExplainer(explainer);

        assertEquals(course.getExplainers().size(), 0);
    }
}
