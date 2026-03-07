import { useMutation } from '@tanstack/react-query';
import { registerApi } from '@entities/auth/api/authApi';
import type { RegisterRequest, AuthResponse } from '@entities/auth/model/auth.types';
import { useAuthStore } from '@shared/store/authStore';

export function useRegister() {
  return useMutation({
    mutationFn: (details: RegisterRequest) => registerApi(details),
  });
}
