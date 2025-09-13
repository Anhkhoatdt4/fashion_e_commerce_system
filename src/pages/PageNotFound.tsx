
const PageNotFound = () => {
  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gray-50">
      <img
        src="/page_not_found.jpg"
        alt="Logo"
        className="w-40 h-40 mb-6 animate-bounce "
      />

      <div className="text-center">
        <p className="text-lg text-gray-600 mb-6">Trang bạn tìm không tồn tại</p>

        <a
          href="/"
          className="px-6 py-3 bg-blue-600 text-white font-medium rounded-lg shadow hover:bg-blue-700 transition"
        >
          Về trang chủ
        </a>
      </div>
    </div>
  )
}

export default PageNotFound
