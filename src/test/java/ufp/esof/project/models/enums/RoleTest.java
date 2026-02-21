package ufp.esof.project.models.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Role Enum Tests")
class RoleTest {

    @Test
    @DisplayName("Should have 4 role values defined")
    void testEnumValues() {
        assertThat(Role.values()).hasSize(4);
    }

    @Test
    @DisplayName("SUPER_ADMIN is admin")
    void testSuperAdminIsAdmin() {
        assertThat(Role.SUPER_ADMIN.isAdmin()).isTrue();
    }

    @Test
    @DisplayName("SUPER_ADMIN is super admin")
    void testSuperAdminIsSuperAdmin() {
        assertThat(Role.SUPER_ADMIN.isSuperAdmin()).isTrue();
    }

    @Test
    @DisplayName("ADMIN is admin")
    void testAdminIsAdmin() {
        assertThat(Role.ADMIN.isAdmin()).isTrue();
    }

    @Test
    @DisplayName("ADMIN is not super admin")
    void testAdminIsNotSuperAdmin() {
        assertThat(Role.ADMIN.isSuperAdmin()).isFalse();
    }

    @Test
    @DisplayName("EXPLAINER is not admin")
    void testExplainerIsNotAdmin() {
        assertThat(Role.EXPLAINER.isAdmin()).isFalse();
    }

    @Test
    @DisplayName("STUDENT is not admin")
    void testStudentIsNotAdmin() {
        assertThat(Role.STUDENT.isAdmin()).isFalse();
    }

    @Test
    @DisplayName("SUPER_ADMIN can manage users")
    void testSuperAdminCanManageUsers() {
        assertThat(Role.SUPER_ADMIN.canManageUsers()).isTrue();
    }

    @Test
    @DisplayName("ADMIN can manage users")
    void testAdminCanManageUsers() {
        assertThat(Role.ADMIN.canManageUsers()).isTrue();
    }

    @Test
    @DisplayName("EXPLAINER cannot manage users")
    void testExplainerCannotManageUsers() {
        assertThat(Role.EXPLAINER.canManageUsers()).isFalse();
    }

    @Test
    @DisplayName("STUDENT cannot manage users")
    void testStudentCannotManageUsers() {
        assertThat(Role.STUDENT.canManageUsers()).isFalse();
    }

    @Test
    @DisplayName("SUPER_ADMIN can view all appointments")
    void testSuperAdminCanViewAllAppointments() {
        assertThat(Role.SUPER_ADMIN.canViewAllAppointments()).isTrue();
    }

    @Test
    @DisplayName("ADMIN can view all appointments")
    void testAdminCanViewAllAppointments() {
        assertThat(Role.ADMIN.canViewAllAppointments()).isTrue();
    }

    @Test
    @DisplayName("EXPLAINER cannot view all appointments")
    void testExplainerCannotViewAllAppointments() {
        assertThat(Role.EXPLAINER.canViewAllAppointments()).isFalse();
    }

    @Test
    @DisplayName("STUDENT cannot view all appointments")
    void testStudentCannotViewAllAppointments() {
        assertThat(Role.STUDENT.canViewAllAppointments()).isFalse();
    }

    @Test
    @DisplayName("All roles have authority strings")
    void testAuthorityStrings() {
        assertThat(Role.SUPER_ADMIN.getAuthority()).isEqualTo("ROLE_SYSTEM_SUPERADMIN");
        assertThat(Role.ADMIN.getAuthority()).isEqualTo("ROLE_ADMIN");
        assertThat(Role.EXPLAINER.getAuthority()).isEqualTo("ROLE_EXPLAINER");
        assertThat(Role.STUDENT.getAuthority()).isEqualTo("ROLE_STUDENT");
    }

    @Test
    @DisplayName("All roles have display names")
    void testDisplayNames() {
        for (Role role : Role.values()) {
            assertThat(role.getDisplayName()).isNotBlank();
        }
    }
}

