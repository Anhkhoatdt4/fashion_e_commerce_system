import { apiClient } from './client';
import { 
  Product, 
  CreateProductRequest, 
  UpdateProductRequest, 
  ProductFilter,
  ApiResponse, 
  PaginatedResponse 
} from '../types';

export const productApi = {
  // Lấy tất cả sản phẩm với filter
  getAll: (filter?: ProductFilter): Promise<PaginatedResponse<Product>> => {
    const params = new URLSearchParams();
    
    if (filter) {
      Object.entries(filter).forEach(([key, value]) => {
        if (value !== undefined && value !== null) {
          params.append(key, String(value));
        }
      });
    }
    
    const queryString = params.toString();
    return apiClient.get(`/products${queryString ? `?${queryString}` : ''}`);
  },

  // Lấy sản phẩm theo ID
  getById: (id: number): Promise<ApiResponse<Product>> => {
    return apiClient.get(`/products/${id}`);
  },

  // Lấy sản phẩm nổi bật
  getFeatured: (): Promise<ApiResponse<Product[]>> => {
    return apiClient.get('/products/featured');
  },

  // Lấy sản phẩm theo brand
  getByBrand: (brandId: number, page: number = 1, limit: number = 12): Promise<PaginatedResponse<Product>> => {
    return apiClient.get(`/products/brand/${brandId}?page=${page}&limit=${limit}`);
  },

  // Lấy sản phẩm theo category
  getByCategory: (category: string, page: number = 1, limit: number = 12): Promise<PaginatedResponse<Product>> => {
    return apiClient.get(`/products/category/${encodeURIComponent(category)}?page=${page}&limit=${limit}`);
  },

  // Tìm kiếm sản phẩm
  search: (query: string, page: number = 1, limit: number = 12): Promise<PaginatedResponse<Product>> => {
    return apiClient.get(`/products/search?q=${encodeURIComponent(query)}&page=${page}&limit=${limit}`);
  },

  // Tạo sản phẩm mới (Admin)
  create: (data: CreateProductRequest): Promise<ApiResponse<Product>> => {
    return apiClient.post('/products', data);
  },

  // Cập nhật sản phẩm (Admin)
  update: (data: UpdateProductRequest): Promise<ApiResponse<Product>> => {
    return apiClient.put(`/products/${data.id}`, data);
  },

  // Xóa sản phẩm (Admin)
  delete: (id: number): Promise<ApiResponse<void>> => {
    return apiClient.delete(`/products/${id}`);
  },

  // Lấy categories
  getCategories: (): Promise<ApiResponse<string[]>> => {
    return apiClient.get('/products/categories');
  },
};
