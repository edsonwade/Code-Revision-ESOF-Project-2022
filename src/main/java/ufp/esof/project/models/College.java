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
@Getter
@Setter
@JsonPropertyOrder({"id", "name", "degrees"})
@Table(name = "colleges")
@SuppressWarnings("unused")
public class College extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "organization_id", nullable = false)
    private Long organizationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", insertable = false, updatable = false)
    private Organization organization;

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
