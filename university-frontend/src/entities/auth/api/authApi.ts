import httpClient from '@shared/api/httpClient';
import { buildUrl } from '@shared/api/apiConfig';
import type { LoginRequest, RegisterRequest, AuthResponse } from '../model/auth.types';

export const loginApi = async (credentials: LoginRequest): Promise<AuthResponse> => {
  const { data } = await httpClient.post<AuthResponse>(buildUrl('/auth/login'), credentials);
  return data;
};

export const registerApi = async (details: RegisterRequest): Promise<AuthResponse> => {
  const { data } = await httpClient.post<AuthResponse>(buildUrl('/auth/register'), details);
  return data;
};

export const logoutApi = async (body: { userId: number; token: string; ipAddress: string; userAgent: string }): Promise<void> => {
  await httpClient.post(buildUrl('/auth/logout'), body);
};
