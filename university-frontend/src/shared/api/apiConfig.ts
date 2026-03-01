export const API_CONFIG = {
  baseURL: import.meta.env['VITE_API_BASE_URL'] ?? 'http://localhost:8080',
  prefix: '/api/v1',
  timeout: 15000,
} as const;

export const buildUrl = (path: string): string =>
  `${API_CONFIG.prefix}${path}`;
