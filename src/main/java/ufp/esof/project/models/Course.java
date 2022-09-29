package ufp.esof.project.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@JsonPropertyOrder({"id", "name", "explainers", "degree"})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
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


}
