// Export all types
export * from './Brand';
export * from './Product';
export * from './User';
export * from './Cart';
export * from './Order';

// Common API response types
export interface ApiResponse<T> {
  success: boolean;
  data: T;
  message?: string;
}

export interface PaginatedResponse<T> {
  success: boolean;
  data: T[];
  pagination: {
    page: number;
    limit: number;
    total: number;
    totalPages: number;
  };
  message?: string;
}

export interface ApiError {
  success: false;
  message: string;
  errors?: string[];
}
