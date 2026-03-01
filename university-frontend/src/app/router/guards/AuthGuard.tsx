import { Navigate, Outlet } from 'react-router-dom';
import { useAuthStore } from '@shared/store/authStore';
import { ROUTES } from '../routes';

export function AuthGuard() {
  const skipAuth = import.meta.env['VITE_SKIP_AUTH'] === 'true';
  const isAuthenticated = useAuthStore((s) => s.isAuthenticated);

  if (skipAuth || isAuthenticated) {
    return <Outlet />;
  }

  return <Navigate to={ROUTES.LOGIN} replace />;
}
