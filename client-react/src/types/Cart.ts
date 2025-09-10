import { Product } from './Product';

export interface CartItem {
  id: number;
  product: Product;
  quantity: number;
  price: number;
  createdAt: Date;
}

export interface Cart {
  id: number;
  userId: number;
  items: CartItem[];
  totalAmount: number;
  totalItems: number;
  updatedAt: Date;
}

export interface AddToCartRequest {
  productId: number;
  quantity: number;
}

export interface UpdateCartItemRequest {
  cartItemId: number;
  quantity: number;
}
