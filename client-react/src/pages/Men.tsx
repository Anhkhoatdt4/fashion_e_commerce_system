import React from 'react';

const Men: React.FC = () => {
  return (
    <div className="min-h-screen bg-white">
      <div className="max-w-7xl mx-auto px-4 py-8">
        <h1 className="text-3xl font-light text-center mb-8">Men</h1>
        <p className="text-center text-gray-600 mb-12">
          Discover our collection of men's clothing, shoes, and accessories.
        </p>
        
        {/* Placeholder for products grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-8">
          {/* Products will be loaded here */}
          <div className="text-center text-gray-500 col-span-full py-12">
            Men's products coming soon...
          </div>
        </div>
      </div>
    </div>
  );
};

export default Men;