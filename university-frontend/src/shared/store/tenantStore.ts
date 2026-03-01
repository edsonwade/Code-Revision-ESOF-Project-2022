import { create } from 'zustand';

interface TenantState {
  organizationId: number | null;
  setOrganizationId: (id: number) => void;
  clearOrganization: () => void;
}

export const useTenantStore = create<TenantState>()((set) => ({
  organizationId: Number(import.meta.env['VITE_DEFAULT_ORG_ID'] ?? '1'),
  setOrganizationId: (id) => set({ organizationId: id }),
  clearOrganization: () => set({ organizationId: null }),
}));
