import { apiClient } from './client';
import { Cart, AddToCartRequest, UpdateCartItemRequest, ApiResponse } from '../types';

export const cartApi = {
  // Lấy giỏ hàng hiện tại
  getCart: (): Promise<ApiResponse<Cart>> => {
    return apiClient.get('/cart');
  },

  // Thêm sản phẩm vào giỏ hàng
  addToCart: (data: AddToCartRequest): Promise<ApiResponse<Cart>> => {
    return apiClient.post('/cart/items', data);
  },

  // Cập nhật số lượng sản phẩm trong giỏ hàng
  updateCartItem: (data: UpdateCartItemRequest): Promise<ApiResponse<Cart>> => {
    return apiClient.put(`/cart/items/${data.cartItemId}`, { quantity: data.quantity });
  },

  // Xóa sản phẩm khỏi giỏ hàng
  removeFromCart: (cartItemId: number): Promise<ApiResponse<Cart>> => {
    return apiClient.delete(`/cart/items/${cartItemId}`);
  },

  // Xóa toàn bộ giỏ hàng
  clearCart: (): Promise<ApiResponse<void>> => {
    return apiClient.delete('/cart');
  },

  // Áp dụng mã giảm giá
  applyCoupon: (couponCode: string): Promise<ApiResponse<Cart>> => {
    return apiClient.post('/cart/coupon', { couponCode });
  },

  // Xóa mã giảm giá
  removeCoupon: (): Promise<ApiResponse<Cart>> => {
    return apiClient.delete('/cart/coupon');
  },
};
