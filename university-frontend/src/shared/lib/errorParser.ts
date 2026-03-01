import type { AxiosError } from 'axios';
import type { ErrorResponse } from '@shared/types/api.types';

export function parseError(error: unknown): string {
  if (!error) return 'An unexpected error occurred.';

  const axiosError = error as AxiosError<ErrorResponse>;

  if (axiosError.response?.data) {
    const data = axiosError.response.data;
    if (data.message) return data.message;
    if (data.error) return data.error;
  }

  if (axiosError.message) {
    if (axiosError.message === 'Network Error') {
      return 'Network error. Please check your connection.';
    }
    return axiosError.message;
  }

  if (error instanceof Error) return error.message;

  return 'An unexpected error occurred.';
}

export function parseValidationErrors(
  error: unknown
): Record<string, string> | null {
  const axiosError = error as AxiosError<ErrorResponse>;
  const validationErrors = axiosError.response?.data?.validationErrors;
  if (validationErrors && Object.keys(validationErrors).length > 0) {
    return validationErrors;
  }
  return null;
}

export function isNotFound(error: unknown): boolean {
  const axiosError = error as AxiosError;
  return axiosError.response?.status === 404;
}
