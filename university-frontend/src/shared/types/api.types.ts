// Matches backend GlobalExceptionHandler ErrorResponse DTO exactly
export interface ErrorResponse {
  timestamp: string; // "yyyy-MM-dd HH:mm:ss"
  status: number;
  error: string;
  message: string;
  path: string;
  validationErrors?: Record<string, string>;
  details?: string;
}

// Future: when backend adds Pageable support
export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
  first: boolean;
  last: boolean;
}
