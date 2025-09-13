import { User } from './User';
import { Product } from './Product';

export interface OrderItem {
  id: number;
  product: Product;
  quantity: number;
  price: number;
  totalPrice: number;
}

export interface Order {
  id: number;
  user: User;
  items: OrderItem[];
  totalAmount: number;
  status: 'PENDING' | 'CONFIRMED' | 'SHIPPING' | 'DELIVERED' | 'CANCELLED';
  shippingAddress: string;
  paymentMethod: 'CASH' | 'CARD' | 'TRANSFER';
  paymentStatus: 'PENDING' | 'PAID' | 'FAILED';
  notes?: string;
  createdAt: Date;
  updatedAt: Date;
}

export interface CreateOrderRequest {
  items: {
    productId: number;
    quantity: number;
    price: number;
  }[];
  shippingAddress: string;
  paymentMethod: 'CASH' | 'CARD' | 'TRANSFER';
  notes?: string;
}

export interface UpdateOrderRequest {
  id: number;
  status?: 'PENDING' | 'CONFIRMED' | 'SHIPPING' | 'DELIVERED' | 'CANCELLED';
  paymentStatus?: 'PENDING' | 'PAID' | 'FAILED';
  notes?: string;
}
