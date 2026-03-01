import axios, { type AxiosError, type InternalAxiosRequestConfig } from 'axios';
import { API_CONFIG } from './apiConfig';
import { useAuthStore } from '@shared/store/authStore';
import { useTenantStore } from '@shared/store/tenantStore';

const httpClient = axios.create({
  baseURL: API_CONFIG.baseURL,
  timeout: API_CONFIG.timeout,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor: attach auth + tenant headers
httpClient.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const token = useAuthStore.getState().token;
    const organizationId = useTenantStore.getState().organizationId
      ?? import.meta.env['VITE_DEFAULT_ORG_ID']
      ?? '1';

    if (token) {
      config.headers.set('Authorization', `Bearer ${token}`);
    }

    config.headers.set('X-Organization-Id', String(organizationId));
    return config;
  },
  (error) => Promise.reject(error)
);

// Response interceptor: handle auth errors
httpClient.interceptors.response.use(
  (response) => response,
  (error: AxiosError) => {
    if (error.response?.status === 401) {
      useAuthStore.getState().clearAuth();
      window.location.href = '/login';
    }
    if (error.response?.status === 403) {
      window.location.href = '/forbidden';
    }
    return Promise.reject(error);
  }
);

export default httpClient;
