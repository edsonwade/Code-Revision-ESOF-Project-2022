package ufp.esof.project.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ufp.esof.project.models.base.AuditableEntity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@NoArgsConstructor
@JsonPropertyOrder({"id", "name", "courses", "college"})
@Getter
@Setter
@Table(name = "degrees")
@SuppressWarnings("all")
public class Degree extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "degree", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Course> courses = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id")
    private College college;


    public Degree(String name) {
        this.setName(name);
    }


    public Degree(Long id, String name, Set<Course> courses, College college) {
        this.setId(id);
        this.setName(name);
        this.setCourses(courses);
        this.setCollege(college);
    }


    public void addCourse(Course course) {
        courses.add(course);
        course.setDegree(this);
    }


    public void removeCourse(Course course) {
        courses.remove(course);
        course.setDegree(null);
    }

    public void setCollege(College college) {
        this.college = college;
        if (college != null) {
            college.getDegrees().add(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Degree degree = (Degree) o;
        return Objects.equals(id, degree.id) && Objects.equals(name, degree.name) && Objects.equals(courses, degree.courses) && Objects.equals(college, degree.college);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(courses);
        result = 31 * result + Objects.hashCode(college);
        return result;
    }
}
