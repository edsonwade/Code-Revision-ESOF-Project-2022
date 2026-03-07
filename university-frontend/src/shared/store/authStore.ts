import { create } from 'zustand';
import { persist } from 'zustand/middleware';
import { jwtDecode } from 'jwt-decode';
import type { AuthResponse } from '@entities/auth/model/auth.types';

// Synchronized with: ufp.esof.project.models.enums.Role
export const USER_ROLE = {
  SUPER_ADMIN: 'SUPER_ADMIN',
  ADMIN: 'ADMIN',
  EXPLAINER: 'EXPLAINER',
  STUDENT: 'STUDENT',
  USER: 'USER',
} as const;

export type UserRole = typeof USER_ROLE[keyof typeof USER_ROLE];

export interface AuthUser {
  id: number;
  email: string;
  role: UserRole;
  organizationId: number;
}

interface JwtPayload {
  userId: number;
  email: string;
  role: UserRole;
  sub: string;
  iat: number;
  exp: number;
}

interface AuthState {
  accessToken: string | null;
  refreshToken: string | null;
  user: AuthUser | null;
  isAuthenticated: boolean;
  setAuth: (response: AuthResponse) => void;
  clearAuth: () => void;
}

export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      accessToken: null,
      refreshToken: null,
      user: null,
      isAuthenticated: false,
      setAuth: (response) => {
        try {
          const decoded = jwtDecode<JwtPayload>(response.accessToken);
          const user: AuthUser = {
            id: response.userId,
            email: response.email,
            role: decoded.role || USER_ROLE.USER,
            organizationId: 1, // Defaulting to 1 as per current backend architecture
          };
          set({
            accessToken: response.accessToken,
            refreshToken: response.refreshToken,
            user,
            isAuthenticated: true,
          });
        } catch (e) {
          console.error('Failed to decode JWT during login', e);
        }
      },
      clearAuth: () =>
        set({ accessToken: null, refreshToken: null, user: null, isAuthenticated: false }),
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
