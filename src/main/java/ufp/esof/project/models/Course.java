package ufp.esof.project.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Explainer> explainers = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Degree degree;

    public Course(String name) {
        this.setName(name);
    }

    public void addExplainer(Explainer explainer) {
        this.explainers.add(explainer);
    }

    public void removeExplainer(Explainer explainer) {
        this.explainers.remove(explainer);
    }
}
