import { AxiosError } from 'axios';
import { ErrorResponse } from '../types/api.types';

export const parseError = (error: unknown): string => {
  if (typeof error === 'string') return error;

  const axiosError = error as AxiosError<ErrorResponse>;
  
  if (axiosError.response?.data) {
    const data = axiosError.response.data;
    
    // Handle validation errors if present
    if (data.validationErrors) {
      const firstError = Object.values(data.validationErrors)[0];
      return firstError || data.message || 'Validation failed';
    }
    
    return data.message || data.error || 'An unexpected error occurred';
  }

  if (axiosError.message) {
    if (axiosError.message === 'Network Error') {
      return 'Network error. Please check your connection.';
    }
    return axiosError.message;
  }

  return 'An unexpected error occurred';
};
