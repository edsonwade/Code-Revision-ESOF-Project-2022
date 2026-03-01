import axios from 'axios';
import { useAuthStore } from '../store/authStore';
import { useTenantStore } from '../store/tenantStore';

const BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api/v1';

export const httpClient = axios.create({
  baseURL: BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

httpClient.interceptors.request.use(
  (config) => {
    const token = useAuthStore.getState().token;
    const organizationId = useTenantStore.getState().organizationId;

    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    if (organizationId) {
      config.headers['X-Organization-Id'] = organizationId.toString();
    }

    return config;
  },
  (error) => Promise.reject(error)
);

httpClient.interceptors.response.use(
  (response) => response.data,
  (error) => {
    const status = error.response?.status;

    if (status === 401) {
      useAuthStore.getState().logout();
      window.location.href = '/login';
    }

    if (status === 403) {
      console.error('Access forbidden (403): Redirecting to forbidden page');
      window.location.href = '/forbidden';
    }

    return Promise.reject(error);
  }
);
