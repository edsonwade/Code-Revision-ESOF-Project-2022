package ufp.esof.project.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import ufp.esof.project.models.base.AuditableEntity;

@Entity
@NoArgsConstructor
@JsonPropertyOrder({"id", "name", "explainers", "degree"})
@Setter
@Getter
@Table(name = "courses")
@Where(clause = "deleted_at IS NULL")
public class Course extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "courses")
    private Set<Explainer> explainers = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "degree_id")
    private Degree degree;

    public Course(String name) {
        this.name = name;
    }

    public void addExplainer(Explainer explainer) {
        explainers.add(explainer);
        explainer.getCourses().add(this);
    }

    public void removeExplainer(Explainer explainer) {
        explainers.remove(explainer);
        explainer.getCourses().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;
        return Objects.equals(id, course.id) && Objects.equals(name, course.name) && Objects.equals(explainers, course.explainers) && Objects.equals(degree, course.degree);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(explainers);
        result = 31 * result + Objects.hashCode(degree);
        return result;
    }
}
