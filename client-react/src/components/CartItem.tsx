import React from 'react';
import { CartItem as CartItemType } from '../types';

interface CartItemProps {
  item: CartItemType;
  onUpdateQuantity: (itemId: number, quantity: number) => void;
  onRemove: (itemId: number) => void;
}

const CartItem: React.FC<CartItemProps> = ({ item, onUpdateQuantity, onRemove }) => {
  const formatPrice = (price: number) => {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND',
    }).format(price);
  };

  const handleQuantityChange = (newQuantity: number) => {
    if (newQuantity < 1) return;
    if (newQuantity > item.product.stock) {
      alert(`Chỉ còn ${item.product.stock} sản phẩm trong kho`);
      return;
    }
    onUpdateQuantity(item.id, newQuantity);
  };

  return (
    <div className="flex items-center space-x-4 bg-white p-4 rounded-lg shadow-sm border">
      <div className="flex-shrink-0">
        <img
          src={item.product.imageUrl}
          alt={item.product.name}
          className="w-20 h-20 object-cover rounded-md"
        />
      </div>

      <div className="flex-1 min-w-0">
        <h3 className="text-lg font-medium text-gray-900 truncate">
          {item.product.name}
        </h3>
        <p className="text-sm text-gray-500">{item.product.brand.name}</p>
        <p className="text-lg font-semibold text-red-600 mt-1">
          {formatPrice(item.price)}
        </p>
      </div>

      <div className="flex items-center space-x-2">
        <button
          onClick={() => handleQuantityChange(item.quantity - 1)}
          className="w-8 h-8 rounded-full border border-gray-300 flex items-center justify-center hover:bg-gray-100"
          disabled={item.quantity <= 1}
        >
          <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M20 12H4" />
          </svg>
        </button>

        <span className="w-12 text-center font-medium">{item.quantity}</span>

        <button
          onClick={() => handleQuantityChange(item.quantity + 1)}
          className="w-8 h-8 rounded-full border border-gray-300 flex items-center justify-center hover:bg-gray-100"
          disabled={item.quantity >= item.product.stock}
        >
          <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 6v6m0 0v6m0-6h6m-6 0H6" />
          </svg>
        </button>
      </div>

      <div className="text-right">
        <p className="text-lg font-semibold text-gray-900">
          {formatPrice(item.price * item.quantity)}
        </p>
        <button
          onClick={() => onRemove(item.id)}
          className="text-red-600 hover:text-red-800 text-sm mt-1"
        >
          Xóa
        </button>
      </div>
    </div>
  );
};

export default CartItem;
