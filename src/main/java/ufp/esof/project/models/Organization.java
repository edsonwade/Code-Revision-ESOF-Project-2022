package ufp.esof.project.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ufp.esof.project.models.base.AuditableEntity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * Organization entity representing a tenant in the multi-tenant SaaS application.
 * Each organization can have multiple colleges, users, and subscription plans.
 *
 * This is a core entity for multi-tenancy support.
 */
@Entity
@Table(name = "organizations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id", "name", "email", "description", "active"})
@SuppressWarnings("all")
public class Organization extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Organization name is required")
    private String name;

    @Column(nullable = false, unique = true)
    @Email(message = "Email should be valid")
    private String email;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Tenant ID (slug) is required")
    private String tenantId;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<College> colleges = new HashSet<>();

    /**
     * Add a college to this organization.
     *
     * @param college the college to add
     */
    public void addCollege(College college) {
        if (college != null) {
            colleges.add(college);
            college.setOrganization(this);
        }
    }

    /**
     * Remove a college from this organization.
     *
     * @param college the college to remove
     */
    public void removeCollege(College college) {
        if (college != null) {
            colleges.remove(college);
            college.setOrganization(null);
        }
    }

    /**
     * Activate this organization.
     */
    public void activate() {
        this.active = true;
    }

    /**
     * Deactivate this organization (soft disable without deletion).
     */
    public void deactivate() {
        this.active = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(tenantId, that.tenantId) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tenantId, name);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", active=" + active +
                '}';
    }
}

