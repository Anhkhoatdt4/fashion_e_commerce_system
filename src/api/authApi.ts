import { apiClient } from './client';
import { LoginRequest, RegisterRequest, UpdateProfileRequest, AuthResponse, User, ApiResponse } from '../types';

export const authApi = {
  // Đăng nhập
  login: (data: LoginRequest): Promise<AuthResponse> => {
    return apiClient.post('/auth/login', data);
  },

  // Đăng ký
  register: (data: RegisterRequest): Promise<AuthResponse> => {
    return apiClient.post('/auth/register', data);
  },

  // Đăng xuất
  logout: (): Promise<ApiResponse<void>> => {
    return apiClient.post('/auth/logout');
  },

  // Refresh token
  refreshToken: (): Promise<AuthResponse> => {
    return apiClient.post('/auth/refresh');
  },

  // Kiểm tra token có hợp lệ không
  verifyToken: (): Promise<ApiResponse<User>> => {
    return apiClient.get('/auth/verify');
  },

  // Quên mật khẩu
  forgotPassword: (email: string): Promise<ApiResponse<void>> => {
    return apiClient.post('/auth/forgot-password', { email });
  },

  // Reset mật khẩu
  resetPassword: (token: string, newPassword: string): Promise<ApiResponse<void>> => {
    return apiClient.post('/auth/reset-password', { token, newPassword });
  },

  // Đổi mật khẩu
  changePassword: (currentPassword: string, newPassword: string): Promise<ApiResponse<void>> => {
    return apiClient.post('/auth/change-password', { currentPassword, newPassword });
  },
};
