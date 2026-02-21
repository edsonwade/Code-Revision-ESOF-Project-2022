package ufp.esof.project.models.enums;

/**
 * Enumeration representing user roles in the system.
 *
 * Roles:
 * - ADMIN: System administrator with full access
 * - STUDENT: Student who books appointments with explainers
 * - EXPLAINER: Instructor/tutor who provides explanations
 * - SUPER_ADMIN: Multi-tenant super administrator
 */
public enum Role {
    SUPER_ADMIN("Super Administrator", "system_superadmin"),
    ADMIN("Administrator", "admin"),
    EXPLAINER("Explainer/Instructor", "explainer"),
    STUDENT("Student", "student");

    private final String displayName;
    private final String authority;

    Role(String displayName, String authority) {
        this.displayName = displayName;
        this.authority = authority;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAuthority() {
        return "ROLE_" + authority.toUpperCase();
    }

    /**
     * Check if this role has administrative privileges.
     *
     * @return true if role is ADMIN or SUPER_ADMIN, false otherwise
     */
    public boolean isAdmin() {
        return this == ADMIN || this == SUPER_ADMIN;
    }

    /**
     * Check if this role is a super administrator.
     *
     * @return true if role is SUPER_ADMIN, false otherwise
     */
    public boolean isSuperAdmin() {
        return this == SUPER_ADMIN;
    }

    /**
     * Check if this role can create/edit other users.
     *
     * @return true if role has user management permissions
     */
    public boolean canManageUsers() {
        return this == SUPER_ADMIN || this == ADMIN;
    }

    /**
     * Check if this role can view all appointments across organization.
     *
     * @return true if role has cross-user appointment visibility
     */
    public boolean canViewAllAppointments() {
        return this == SUPER_ADMIN || this == ADMIN;
    }
}

