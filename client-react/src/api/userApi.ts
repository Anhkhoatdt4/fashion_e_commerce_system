import { apiClient } from './client';
import { User, UpdateProfileRequest, ApiResponse } from '../types';

export const userApi = {
  // Lấy thông tin profile hiện tại
  getProfile: (): Promise<ApiResponse<User>> => {
    return apiClient.get('/users/profile');
  },

  // Cập nhật profile
  updateProfile: (data: UpdateProfileRequest): Promise<ApiResponse<User>> => {
    return apiClient.put('/users/profile', data);
  },

  // Upload avatar
  uploadAvatar: (file: File): Promise<ApiResponse<{ avatarUrl: string }>> => {
    const formData = new FormData();
    formData.append('avatar', file);
    
    return fetch(`${process.env.REACT_APP_API_URL || 'http://localhost:8080/api'}/users/avatar`, {
      method: 'POST',
      headers: {
        Authorization: `Bearer ${localStorage.getItem('authToken')}`,
      },
      body: formData,
    }).then(res => res.json());
  },

  // Lấy lịch sử đơn hàng
  getOrderHistory: (page: number = 1, limit: number = 10): Promise<ApiResponse<any>> => {
    return apiClient.get(`/users/orders?page=${page}&limit=${limit}`);
  },

  // Xóa tài khoản
  deleteAccount: (): Promise<ApiResponse<void>> => {
    return apiClient.delete('/users/profile');
  },
};
