import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from '../store';
import { logoutUser } from '../store/authSlice';

const Header: React.FC = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const { isAuthenticated } = useSelector((state: RootState) => state.auth);
  const { itemCount } = useSelector((state: RootState) => state.cart);

  const handleLogout = () => {
    dispatch(logoutUser() as any);
    navigate('/');
  };

  return (
    <>
      {/* Top promotional bar */}
      <div className="bg-black text-white text-center py-2 text-sm">
        <p>
          Get early access on launches and offers. 
          <Link to="/signup" className="underline ml-1 hover:text-gray-300">
            Sign Up For Texts
          </Link>
          <span className="ml-2">→</span>
        </p>
      </div>

      {/* Main Header */}
      <header className="bg-white border-b border-gray-100">
        <div className="max-w-7xl mx-auto">
          {/* Top navigation row */}
          <div className="flex items-center justify-between px-4 py-3 text-sm">
            {/* Left nav */}
            <nav className="hidden md:flex items-center space-x-6">
              <Link to="/women" className="text-gray-700 hover:text-black font-medium">
                Women
              </Link>
              <Link to="/men" className="text-gray-700 hover:text-black font-medium">
                Men
              </Link>
              <Link to="/about" className="text-gray-700 hover:text-black font-medium">
                About
              </Link>
              <Link to="/stories" className="text-gray-700 hover:text-black font-medium">
                Everlane Stories
              </Link>
            </nav>

            {/* Center logo */}
            <Link to="/" className="text-xl font-semibold tracking-[0.3em] text-black">
              EVERLANE
            </Link>

            {/* Right actions */}
            <div className="flex items-center space-x-4">
              {/* Currency selector */}
              <div className="hidden md:flex items-center space-x-1 text-sm">
                <div className="w-4 h-3 bg-red-600 rounded-sm flex items-center justify-center">
                  <div className="w-2 h-1 bg-white rounded-sm"></div>
                </div>
                <span>USD</span>
              </div>

              {/* Search */}
              <button className="p-1 hover:bg-gray-100 rounded">
                <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="1.5" d="m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z" />
                </svg>
              </button>

              {/* Account */}
              {isAuthenticated ? (
                <div className="relative group">
                  <button className="p-1 hover:bg-gray-100 rounded">
                    <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="1.5" d="M15.75 6a3.75 3.75 0 1 1-7.5 0 3.75 3.75 0 0 1 7.5 0ZM4.501 20.118a7.5 7.5 0 0 1 14.998 0A17.933 17.933 0 0 1 12 21.75c-2.676 0-5.216-.584-7.499-1.632Z" />
                    </svg>
                  </button>
                  
                  <div className="absolute right-0 mt-2 w-48 bg-white rounded-lg shadow-lg py-2 z-50 opacity-0 invisible group-hover:opacity-100 group-hover:visible transition-all duration-200 border">
                    <Link to="/profile" className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-50">
                      My Account
                    </Link>
                    <Link to="/orders" className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-50">
                      Orders
                    </Link>
                    <hr className="my-1" />
                    <button
                      onClick={handleLogout}
                      className="block w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-50"
                    >
                      Sign Out
                    </button>
                  </div>
                </div>
              ) : (
                <Link to="/login" className="p-1 hover:bg-gray-100 rounded">
                  <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="1.5" d="M15.75 6a3.75 3.75 0 1 1-7.5 0 3.75 3.75 0 0 1 7.5 0ZM4.501 20.118a7.5 7.5 0 0 1 14.998 0A17.933 17.933 0 0 1 12 21.75c-2.676 0-5.216-.584-7.499-1.632Z" />
                  </svg>
                </Link>
              )}

              {/* Cart */}
              <Link to="/cart" className="relative p-1 hover:bg-gray-100 rounded">
                <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="1.5" d="M15.75 10.5V6a3.75 3.75 0 1 0-7.5 0v4.5m11.356-1.993 1.263 12c.07.665-.45 1.243-1.119 1.243H4.25a1.125 1.125 0 0 1-1.12-1.243l1.264-12A1.125 1.125 0 0 1 5.513 7.5h12.974c.576 0 1.059.435 1.119 1.007ZM8.625 10.5a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Zm7.5 0a.375.375 0 1 1-.75 0 .375.375 0 0 1 .75 0Z" />
                </svg>
                {itemCount > 0 && (
                  <span className="absolute -top-1 -right-1 bg-black text-white text-xs rounded-full h-4 w-4 flex items-center justify-center text-[10px]">
                    {itemCount}
                  </span>
                )}
              </Link>

              {/* Mobile menu button */}
              <button 
                onClick={() => setIsMenuOpen(!isMenuOpen)}
                className="md:hidden p-1 hover:bg-gray-100 rounded"
              >
                <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="1.5" d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" />
                </svg>
              </button>
            </div>
          </div>

          {/* Secondary navigation */}
          <div className="border-t border-gray-100">
            <nav className="flex items-center justify-center space-x-8 px-4 py-3 text-sm overflow-x-auto">
              <Link to="/holiday-gifting" className="text-gray-700 hover:text-black whitespace-nowrap">
                Holiday Gifting
              </Link>
              <Link to="/new-arrivals" className="text-gray-700 hover:text-black whitespace-nowrap">
                New Arrivals
              </Link>
              <Link to="/best-sellers" className="text-gray-700 hover:text-black whitespace-nowrap">
                Best Sellers
              </Link>
              <Link to="/clothing" className="text-gray-700 hover:text-black whitespace-nowrap">
                Clothing
              </Link>
              <Link to="/tops-sweaters" className="text-gray-700 hover:text-black whitespace-nowrap">
                Tops & Sweaters
              </Link>
              <Link to="/pants-jeans" className="text-gray-700 hover:text-black whitespace-nowrap">
                Pants & Jeans
              </Link>
              <Link to="/outerwear" className="text-gray-700 hover:text-black whitespace-nowrap">
                Outerwear
              </Link>
              <Link to="/shoes-bags" className="text-gray-700 hover:text-black whitespace-nowrap">
                Shoes & Bags
              </Link>
              <Link to="/sale" className="text-red-600 hover:text-red-700 font-medium whitespace-nowrap">
                Sale
              </Link>
            </nav>
          </div>

          {/* Mobile Navigation */}
          {isMenuOpen && (
            <div className="md:hidden border-t border-gray-100 bg-white">
              <div className="px-4 py-4 space-y-4">
                <Link 
                  to="/women" 
                  className="block text-gray-700 hover:text-black font-medium"
                  onClick={() => setIsMenuOpen(false)}
                >
                  Women
                </Link>
                <Link 
                  to="/men" 
                  className="block text-gray-700 hover:text-black font-medium"
                  onClick={() => setIsMenuOpen(false)}
                >
                  Men
                </Link>
                <Link 
                  to="/about" 
                  className="block text-gray-700 hover:text-black font-medium"
                  onClick={() => setIsMenuOpen(false)}
                >
                  About
                </Link>
                <Link 
                  to="/stories" 
                  className="block text-gray-700 hover:text-black font-medium"
                  onClick={() => setIsMenuOpen(false)}
                >
                  Everlane Stories
                </Link>
                
                <hr className="border-gray-200" />
                
                <Link 
                  to="/holiday-gifting" 
                  className="block text-gray-700 hover:text-black"
                  onClick={() => setIsMenuOpen(false)}
                >
                  Holiday Gifting
                </Link>
                <Link 
                  to="/new-arrivals" 
                  className="block text-gray-700 hover:text-black"
                  onClick={() => setIsMenuOpen(false)}
                >
                  New Arrivals
                </Link>
                <Link 
                  to="/best-sellers" 
                  className="block text-gray-700 hover:text-black"
                  onClick={() => setIsMenuOpen(false)}
                >
                  Best Sellers
                </Link>
                <Link 
                  to="/clothing" 
                  className="block text-gray-700 hover:text-black"
                  onClick={() => setIsMenuOpen(false)}
                >
                  Clothing
                </Link>
                <Link 
                  to="/sale" 
                  className="block text-red-600 hover:text-red-700 font-medium"
                  onClick={() => setIsMenuOpen(false)}
                >
                  Sale
                </Link>
              </div>
            </div>
          )}
        </div>
      </header>
    </>
  );
};

export default Header;
