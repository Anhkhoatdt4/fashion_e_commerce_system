export interface Brand {
  id: number;
  name: string;
  description?: string;
  logoUrl?: string;
  isActive: boolean;
  createdAt: Date;
  updatedAt: Date;
}

export interface CreateBrandRequest {
  name: string;
  description?: string;
  logoUrl?: string;
}

export interface UpdateBrandRequest {
  id: number;
  name?: string;
  description?: string;
  logoUrl?: string;
  isActive?: boolean;
}
