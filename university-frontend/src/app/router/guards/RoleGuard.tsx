import type { ReactNode } from 'react';
import { useAuthStore, type UserRole } from '@shared/store/authStore';

interface RoleGuardProps {
  allowedRoles: UserRole[];
  children: ReactNode;
  fallback?: ReactNode;
}

export function RoleGuard({ allowedRoles, children, fallback = null }: RoleGuardProps) {
  const skipAuth = import.meta.env['VITE_SKIP_AUTH'] === 'true';
  const user = useAuthStore((s) => s.user);

  if (skipAuth) return <>{children}</>;
  if (!user) return <>{fallback}</>;
  if (!allowedRoles.includes(user.role)) return <>{fallback}</>;

  return <>{children}</>;
}
