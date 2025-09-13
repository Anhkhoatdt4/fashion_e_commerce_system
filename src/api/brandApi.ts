import { apiClient } from './client';
import { Brand, CreateBrandRequest, UpdateBrandRequest, ApiResponse, PaginatedResponse } from '../types';

export const brandApi = {
  // Lấy tất cả brands
  getAll: (): Promise<ApiResponse<Brand[]>> => {
    return apiClient.get('/brands');
  },

  // Lấy brands với phân trang
  getPaginated: (page: number = 1, limit: number = 10): Promise<PaginatedResponse<Brand>> => {
    return apiClient.get(`/brands?page=${page}&limit=${limit}`);
  },

  // Lấy brand theo ID
  getById: (id: number): Promise<ApiResponse<Brand>> => {
    return apiClient.get(`/brands/${id}`);
  },

  // Tạo brand mới
  create: (data: CreateBrandRequest): Promise<ApiResponse<Brand>> => {
    return apiClient.post('/brands', data);
  },

  // Cập nhật brand
  update: (data: UpdateBrandRequest): Promise<ApiResponse<Brand>> => {
    return apiClient.put(`/brands/${data.id}`, data);
  },

  // Xóa brand
  delete: (id: number): Promise<ApiResponse<void>> => {
    return apiClient.delete(`/brands/${id}`);
  },

  // Lấy brands đang active
  getActive: (): Promise<ApiResponse<Brand[]>> => {
    return apiClient.get('/brands/active');
  },
};
