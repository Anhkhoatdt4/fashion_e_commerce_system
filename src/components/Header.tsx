import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from '../store';
import { logoutUser } from '../store/authSlice';

const Header: React.FC = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const { isAuthenticated, user } = useSelector((state: RootState) => state.auth);
  const { itemCount } = useSelector((state: RootState) => state.cart);

  const handleLogout = () => {
    dispatch(logoutUser() as any);
    navigate('/');
  };

  return (
    <header className="bg-white shadow-sm border-b border-gray-200">
      <div className="container mx-auto px-4 py-4">
        <div className="flex justify-between items-center">
          {/* Logo */}
          <Link to="/" className="text-2xl font-bold text-gray-900 hover:text-gray-700">
            CloShop
          </Link>

          {/* Navigation */}
          <nav className="hidden md:flex space-x-6">
            <Link to="/" className="text-gray-800 hover:text-black border-b-2 border-transparent hover:border-black transition-all">
              Trang chủ
            </Link>
            <Link to="/products" className="text-gray-800 hover:text-black border-b-2 border-transparent hover:border-black transition-all">
              Sản phẩm
            </Link>
            <Link to="/about" className="text-gray-800 hover:text-black border-b-2 border-transparent hover:border-black transition-all">
              Về chúng tôi
            </Link>
            <Link to="/contact" className="text-gray-800 hover:text-black border-b-2 border-transparent hover:border-black transition-all">
              Liên hệ
            </Link>
          </nav>

          {/* Search */}
          <div className="hidden md:flex flex-1 max-w-md mx-8">
            <input
              type="text"
              placeholder="Tìm kiếm sản phẩm..."
              className="w-full px-4 py-2 border border-gray-300 rounded-l-md focus:outline-none focus:ring-2 focus:ring-gray-600"
            />
            <button className="px-4 py-2 bg-gray-900 text-white rounded-r-md border border-gray-900 hover:bg-gray-700">
              <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" 
                  d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
              </svg>
            </button>
          </div>

          {/* User actions */}
          <div className="flex items-center space-x-4">
            {/* Cart */}
            <Link to="/cart" className="relative text-gray-800 hover:text-black">
              <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" 
                  d="M3 3h2l.4 2M7 13h10l4-8H5.4m0 0L7 13m0 0l-1.5 6H19M7 13v6a2 2 0 002 2h6a2 2 0 002-2v-6" />
              </svg>
              {itemCount > 0 && (
                <span className="absolute -top-2 -right-2 bg-black text-white text-xs rounded-full h-5 w-5 flex items-center justify-center border border-white">
                  {itemCount}
                </span>
              )}
            </Link>

            {/* User menu */}
            {isAuthenticated ? (
              <div className="relative group">
                <button className="flex items-center space-x-1 text-gray-800 hover:text-black">
                  <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" 
                      d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                  </svg>
                  <span>{user?.firstName}</span>
                </button>
                
                <div className="absolute right-0 mt-2 w-48 bg-white border border-gray-200 rounded-md shadow-lg py-1 z-10 opacity-0 invisible group-hover:opacity-100 group-hover:visible transition-all duration-200">
                  <Link to="/profile" className="block px-4 py-2 text-sm text-gray-800 hover:bg-gray-100">
                    Thông tin cá nhân
                  </Link>
                  <Link to="/orders" className="w-64 p-6 border-r border-gray-200 hidden md:block">
                    Đơn hàng của tôi
                  </Link>
                  <button
                    onClick={handleLogout}
                    className="block w-full text-left px-4 py-2 text-sm text-gray-800 hover:bg-gray-100"
                  >
                    Đăng xuất
                  </button>
                </div>
              </div>
            ) : (
              <div className="flex space-x-2">
                <Link
                  to="/login"
                  className="px-4 py-2 text-gray-900 border border-gray-900 rounded-md hover:bg-gray-100"
                >
                  Đăng nhập
                </Link>
                <Link
                  to="/register"
                  className="px-4 py-2 bg-gray-900 text-white border border-gray-900 rounded-md hover:bg-gray-700"
                >
                  Đăng ký
                </Link>
              </div>
            )}
          </div>
        </div>
      </div>
    </header>
  );
};

export default Header;
