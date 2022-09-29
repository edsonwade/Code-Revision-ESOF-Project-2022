package ufp.esof.project.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@JsonPropertyOrder({"id", "name", "degrees"})
public class College {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long Id;

    private String name;

    @OneToMany(mappedBy = "college", cascade = CascadeType.PERSIST)
    private Set<Degree> degrees = new HashSet<>();

    public College(String name) {
        this.setName(name);
    }
}
