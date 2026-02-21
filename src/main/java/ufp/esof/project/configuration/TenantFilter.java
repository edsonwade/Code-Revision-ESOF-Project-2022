package ufp.esof.project.configuration;

import org.springframework.stereotype.Component;

import ufp.esof.project.security.TenantContext;

@Component
public class TenantFilter {

    private final TenantContext tenantContext;

    public TenantFilter(TenantContext tenantContext) {
        this.tenantContext = tenantContext;
    }

    public Long getCurrentOrganizationId() {
        return tenantContext.getOrganizationId();
    }

    public boolean isMultiTenantEnabled() {
        return true;
    }
}
