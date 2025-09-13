import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
import { RootState } from '../store';
import { registerUser } from '../store/authSlice';

const Register: React.FC = () => {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
    confirmPassword: '',
    firstName: '',
    lastName: '',
    phone: '',
  });

  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { isLoading, error } = useSelector((state: RootState) => state.auth);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (formData.password !== formData.confirmPassword) {
      alert('Mật khẩu xác nhận không khớp');
      return;
    }

    try {
      const { confirmPassword, ...userData } = formData;
      await dispatch(registerUser(userData) as any).unwrap();
      navigate('/');
    } catch (error) {}
  };

  return (
    <div className="min-h-screen bg-gradient-to-r from-gray-100 via-white to-gray-100 flex flex-col justify-center py-12 px-4 sm:px-6 lg:px-8">
      <div className="sm:mx-auto sm:w-full sm:max-w-md text-center">
        <h2 className="text-3xl font-extrabold text-gray-900">Đăng ký CloShop</h2>
        <p className="mt-2 text-sm text-gray-600">
          Đã có tài khoản?{' '}
          <Link to="/login" className="font-medium text-black hover:underline">
            Đăng nhập ngay
          </Link>
        </p>
      </div>

      <div className="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
        <div className="bg-white py-8 px-6 shadow-lg border border-gray-200 rounded-xl sm:px-10">
          <form className="space-y-5" onSubmit={handleSubmit}>
            {error && (
              <div className="bg-red-50 border border-red-200 text-red-600 px-4 py-3 rounded-md">
                {error}
              </div>
            )}

            <div className="grid grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-medium text-gray-700">Họ</label>
                <input
                  name="firstName"
                  type="text"
                  required
                  value={formData.firstName}
                  onChange={handleChange}
                  placeholder="Họ"
                  className="mt-1 block w-full rounded-lg border border-gray-300 px-3 py-2 focus:border-black focus:ring-1 focus:ring-black"
                />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700">Tên</label>
                <input
                  name="lastName"
                  type="text"
                  required
                  value={formData.lastName}
                  onChange={handleChange}
                  placeholder="Tên"
                  className="mt-1 block w-full rounded-lg border border-gray-300 px-3 py-2 focus:border-black focus:ring-1 focus:ring-black"
                />
              </div>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">Email</label>
              <input
                name="email"
                type="email"
                required
                value={formData.email}
                onChange={handleChange}
                placeholder="Nhập email của bạn"
                className="mt-1 block w-full rounded-lg border border-gray-300 px-3 py-2 focus:border-black focus:ring-1 focus:ring-black"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">Số điện thoại</label>
              <input
                name="phone"
                type="tel"
                value={formData.phone}
                onChange={handleChange}
                placeholder="Nhập số điện thoại"
                className="mt-1 block w-full rounded-lg border border-gray-300 px-3 py-2 focus:border-black focus:ring-1 focus:ring-black"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">Mật khẩu</label>
              <input
                name="password"
                type="password"
                required
                value={formData.password}
                onChange={handleChange}
                placeholder="Nhập mật khẩu"
                className="mt-1 block w-full rounded-lg border border-gray-300 px-3 py-2 focus:border-black focus:ring-1 focus:ring-black"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">Xác nhận mật khẩu</label>
              <input
                name="confirmPassword"
                type="password"
                required
                value={formData.confirmPassword}
                onChange={handleChange}
                placeholder="Nhập lại mật khẩu"
                className="mt-1 block w-full rounded-lg border border-gray-300 px-3 py-2 focus:border-black focus:ring-1 focus:ring-black"
              />
            </div>

            <div className="flex items-center">
              <input
                id="agree-terms"
                name="agree-terms"
                type="checkbox"
                required
                className="h-4 w-4 text-black focus:ring-black border-gray-300 rounded"
              />
              <label htmlFor="agree-terms" className="ml-2 text-sm text-gray-700">
                Tôi đồng ý với{' '}
                <Link to="/terms" className="text-black hover:underline">
                  Điều khoản dịch vụ
                </Link>{' '}
                và{' '}
                <Link to="/privacy" className="text-black hover:underline">
                  Chính sách bảo mật
                </Link>
              </label>
            </div>

            <div>
              <button
                type="submit"
                disabled={isLoading}
                className="w-full flex justify-center py-2 px-4 border border-black text-sm font-medium rounded-lg text-white bg-black hover:bg-gray-800 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-black disabled:opacity-50"
              >
                {isLoading ? (
                  <div className="animate-spin rounded-full h-4 w-4 border-b-2 border-white"></div>
                ) : (
                  'Tạo tài khoản'
                )}
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default Register;
