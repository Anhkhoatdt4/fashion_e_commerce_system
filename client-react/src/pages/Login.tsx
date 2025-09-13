import React from "react";

export default function Login() {
  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-50">
      <div className="w-full max-w-md p-8 bg-white rounded-xl shadow-md">
        {/* Tiêu đề */}
        <h2 className="text-2xl font-bold text-center text-gray-900">
          Đăng nhập vào tài khoản
        </h2>
        <p className="mt-2 text-center text-gray-500">
          Hoặc{" "}
          <a href="#" className="text-blue-600 hover:underline">
            tạo tài khoản mới
          </a>
        </p>

        {/* Form */}
        <form className="mt-6 space-y-4">
          <div>
            <label className="block mb-1 text-gray-700">Email</label>
            <input
              type="email"
              placeholder="Nhập email của bạn"
              className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <label className="block mb-1 text-gray-700">Mật khẩu</label>
            <input
              type="password"
              placeholder="Nhập mật khẩu"
              className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div className="flex items-center justify-between text-sm">
            <label className="flex items-center gap-2">
              <input type="checkbox" className="rounded" />
              Ghi nhớ đăng nhập
            </label>
            <a href="#" className="text-blue-600 hover:underline">
              Quên mật khẩu?
            </a>
          </div>

          <button
            type="submit"
            className="w-full py-2 text-white bg-blue-600 rounded-lg hover:bg-blue-700"
          >
            Đăng nhập
          </button>
        </form>

        {/* Hoặc đăng nhập với */}
        <div className="flex items-center my-6">
          <div className="flex-1 h-px bg-gray-300"></div>
          <span className="px-2 text-sm text-gray-500">
            Hoặc đăng nhập với
          </span>
          <div className="flex-1 h-px bg-gray-300"></div>
        </div>

        {/* Google & Facebook */}
        <div className="flex gap-4">
          <button className="flex-1 flex items-center justify-center gap-2 py-2 border rounded-lg hover:bg-gray-100">
            <img
              src="https://www.svgrepo.com/show/355037/google.svg"
              alt="Google"
              className="w-5 h-5"
            />
            Google
          </button>
          <button className="flex-1 flex items-center justify-center gap-2 py-2 border rounded-lg hover:bg-gray-100">
            <img
              src="https://www.svgrepo.com/show/303114/facebook-3-logo.svg"
              alt="Facebook"
              className="w-5 h-5"
            />
            Facebook
          </button>
        </div>
      </div>
    </div>
  );
}
