import { create } from 'zustand';
import { persist } from 'zustand/middleware';

interface TenantState {
  organizationId: number | null;
  setOrganizationId: (id: number) => void;
  clearOrganizationId: () => void;
}

export const useTenantStore = create<TenantState>()(
  persist(
    (set) => ({
      organizationId: Number(import.meta.env.VITE_DEFAULT_ORG_ID) || 1,
      setOrganizationId: (id) => set({ organizationId: id }),
      clearOrganizationId: () => set({ organizationId: null }),
    }),
    {
      name: 'tenant-storage',
    }
  )
);
