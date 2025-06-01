package ufp.esof.project.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import lombok.Setter;

import java.util.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({"id", "name", "degrees"})
@Table(name = "colleges")
public class College {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Degree> degrees = new HashSet<>();

    public College(String name) {
        this.name = name;
    }

    public void addDegree(Degree degree) {
        degrees.add(degree);
        degree.setCollege(this);
    }

    public void removeDegree(Degree degree) {
        degrees.remove(degree);
        degree.setCollege(null);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        College college = (College) o;
        return Objects.equals(id, college.id) && Objects.equals(name, college.name) && Objects.equals(degrees, college.degrees);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(degrees);
        return result;
    }
}
