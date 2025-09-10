import { Brand } from './Brand';

export interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  originalPrice?: number;
  imageUrl: string;
  images?: string[];
  stock: number;
  category: string;
  brand: Brand;
  isActive: boolean;
  isFeatured: boolean;
  createdAt: Date;
  updatedAt: Date;
}

export interface CreateProductRequest {
  name: string;
  description: string;
  price: number;
  originalPrice?: number;
  imageUrl: string;
  images?: string[];
  stock: number;
  category: string;
  brandId: number;
  isFeatured?: boolean;
}

export interface UpdateProductRequest {
  id: number;
  name?: string;
  description?: string;
  price?: number;
  originalPrice?: number;
  imageUrl?: string;
  images?: string[];
  stock?: number;
  category?: string;
  brandId?: number;
  isActive?: boolean;
  isFeatured?: boolean;
}

export interface ProductFilter {
  category?: string;
  brandId?: number;
  minPrice?: number;
  maxPrice?: number;
  search?: string;
  page?: number;
  limit?: number;
  sortBy?: 'price' | 'name' | 'createdAt';
  sortOrder?: 'asc' | 'desc';
}
