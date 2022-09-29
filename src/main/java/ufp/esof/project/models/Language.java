package ufp.esof.project.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@ToString
@Entity
@Data
public class Language {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;


    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ToString.Exclude
    @OneToOne
    private Explainer explainer;

    public Language(String name) {
        this.name = name;
    }
}
