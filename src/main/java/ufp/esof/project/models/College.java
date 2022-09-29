package ufp.esof.project.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class College {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;

    @OneToMany(mappedBy = "college", cascade = CascadeType.PERSIST)
    private Set<Degree> degrees = new HashSet<>();

    public College(String name) {
        this.setName(name);
    }
}
