export interface RegisterRequest {
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  ipAddress: string;
  userAgent: string;
}

export interface LoginRequest {
  email: string;
  password: string;
  ipAddress: string;
  userAgent: string;
}

export interface AuthResponse {
  accessToken: string;
  refreshToken: string;
  userId: number;
  email: string;
}
