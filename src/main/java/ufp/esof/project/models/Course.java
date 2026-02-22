package ufp.esof.project.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@JsonPropertyOrder({"id", "name", "explainers", "degree"})
@Setter
@Getter
@Table(name = "courses")
@SuppressWarnings("all")
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
