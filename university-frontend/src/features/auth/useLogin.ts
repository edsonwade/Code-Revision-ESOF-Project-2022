import { useMutation } from '@tanstack/react-query';
import { loginApi } from '@entities/auth/api/authApi';
import type { LoginRequest, AuthResponse } from '@entities/auth/model/auth.types';
import { useAuthStore } from '@shared/store/authStore';

export function useLogin() {
  const setAuth = useAuthStore((s) => s.setAuth);
  
  return useMutation({
    mutationFn: (credentials: LoginRequest) => loginApi(credentials),
    onSuccess: (data: AuthResponse) => {
      setAuth(data);
    },
  });
}
