import { useMutation } from '@tanstack/react-query';
import httpClient from '@shared/api/httpClient';
import { buildUrl } from '@shared/api/apiConfig';
import { useAuthStore, type AuthUser } from '@shared/store/authStore';
import { useTenantStore } from '@shared/store/tenantStore';

interface LoginRequest { email: string; password: string; }
interface LoginResponse { token: string; user: AuthUser; }

export function useLogin() {
  const setAuth = useAuthStore((s) => s.setAuth);
  const setOrgId = useTenantStore((s) => s.setOrganizationId);
  return useMutation({
    mutationFn: async (credentials: LoginRequest): Promise<LoginResponse> => {
      const { data } = await httpClient.post<LoginResponse>(buildUrl('/auth/login'), credentials);
      return data;
    },
    onSuccess: ({ token, user }) => { setAuth(token, user); setOrgId(user.organizationId); },
  });
}
