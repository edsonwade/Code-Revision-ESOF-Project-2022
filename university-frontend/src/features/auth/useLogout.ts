import { useMutation } from '@tanstack/react-query';
import { logoutApi } from '@entities/auth/api/authApi';
import { useAuthStore } from '@shared/store/authStore';
import { useNavigate } from 'react-router-dom';
import { ROUTES } from '@app/router/routes';

export function useLogout() {
  const clearAuth = useAuthStore((s) => s.clearAuth);
  const user = useAuthStore((s) => s.user);
  const token = useAuthStore((s) => s.refreshToken);
  const navigate = useNavigate();

  return useMutation({
    mutationFn: async () => {
      if (user && token) {
        await logoutApi({
          userId: user.id,
          token,
          ipAddress: '127.0.0.1', // Real apps would capture this or leave it backend-reliant
          userAgent: navigator.userAgent,
        });
      }
    },
    onSettled: () => {
      clearAuth();
      navigate(ROUTES.LOGIN, { replace: true });
    },
  });
}
