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
@JsonPropertyOrder({"id", "name", "courses", "college"})
public class Degree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long Id;

    private String name;

    @OneToMany(mappedBy = "degree", cascade = CascadeType.ALL)
    private Set<Course> courses = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @EqualsAndHashCode.Exclude
    @JsonBackReference
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
}
