import { create } from 'zustand';
import { persist } from 'zustand/middleware';

// Synchronized with: ufp.esof.project.models.enums.Role
export const USER_ROLE = {
  SUPER_ADMIN: 'SUPER_ADMIN',
  ADMIN: 'ADMIN',
  EXPLAINER: 'EXPLAINER',
  STUDENT: 'STUDENT',
} as const;

export type UserRole = typeof USER_ROLE[keyof typeof USER_ROLE];

export interface AuthUser {
  id: number;
  name: string;
  email: string;
  role: UserRole;
  organizationId: number;
}

interface AuthState {
  token: string | null;
  user: AuthUser | null;
  isAuthenticated: boolean;
  setAuth: (token: string, user: AuthUser) => void;
  clearAuth: () => void;
}

export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      token: null,
      user: null,
      isAuthenticated: false,
      setAuth: (token, user) =>
        set({ token, user, isAuthenticated: true }),
      clearAuth: () =>
        set({ token: null, user: null, isAuthenticated: false }),
    }),
    {
      name: 'uni-auth',
      storage: {
        getItem: (name) => {
          const v = sessionStorage.getItem(name);
          return v ? JSON.parse(v) : null;
        },
        setItem: (name, value) =>
          sessionStorage.setItem(name, JSON.stringify(value)),
        removeItem: (name) => sessionStorage.removeItem(name),
      },
    }
  )
);
