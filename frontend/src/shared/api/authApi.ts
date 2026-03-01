import { httpClient } from './httpClient';

export interface AuthResponse {
  accessToken: string;
  refreshToken: string;
  userId: number;
  email: string;
}

export interface RegisterRequest {
  email: string;
  password?: string;
  firstName: string;
  lastName: string;
  ipAddress: string;
  userAgent: string;
}

export interface LoginRequest {
  email: string;
  password?: string;
  ipAddress: string;
  userAgent: string;
}

export const authApi = {
  login: (data: LoginRequest): Promise<AuthResponse> => 
    httpClient.post('/auth/login', data),
  
  register: (data: RegisterRequest): Promise<AuthResponse> => 
    httpClient.post('/auth/register', data),
  
  refreshToken: (refreshToken: string): Promise<AuthResponse> => 
    httpClient.post('/auth/refresh', { refreshToken }),
  
  logout: (userId: number, token: string, ipAddress: string, userAgent: string) => 
    httpClient.post('/auth/logout', { userId, token, ipAddress, userAgent }),
  
  setup2fa: (userId: number, email: string) => 
    httpClient.post('/auth/2fa/setup', { userId, email }),
  
  verify2fa: (userId: number, code: string) => 
    httpClient.post('/auth/2fa/verify', { userId, code }),
  
  disable2fa: (userId: number, code: string) => 
    httpClient.post('/auth/2fa/disable', { userId, code }),
};
