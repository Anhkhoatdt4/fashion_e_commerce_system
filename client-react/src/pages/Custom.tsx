import React from 'react';

const Custom: React.FC = () => {
  return (
    <div className="min-h-screen bg-white">
      <div className="max-w-6xl mx-auto px-4 py-16">
        <h1 className="text-4xl font-light text-center mb-8">Custom Collection</h1>
        <p className="text-center text-gray-600 mb-16 text-lg">
          Create something uniquely yours with our custom design service.
        </p>
        
        <div className="grid md:grid-cols-3 gap-8 mb-16">
          <div className="text-center">
            <div className="w-16 h-16 mx-auto mb-4 rounded-full bg-gray-100 flex items-center justify-center">
              <span className="text-2xl">✏️</span>
            </div>
            <h3 className="text-xl font-medium mb-2">Design</h3>
            <p className="text-gray-600">
              Work with our designers to create your perfect piece
            </p>
          </div>
          
          <div className="text-center">
            <div className="w-16 h-16 mx-auto mb-4 rounded-full bg-gray-100 flex items-center justify-center">
              <span className="text-2xl">🧵</span>
            </div>
            <h3 className="text-xl font-medium mb-2">Craft</h3>
            <p className="text-gray-600">
              Made with premium materials in our partner factories
            </p>
          </div>
          
          <div className="text-center">
            <div className="w-16 h-16 mx-auto mb-4 rounded-full bg-gray-100 flex items-center justify-center">
              <span className="text-2xl">📦</span>
            </div>
            <h3 className="text-xl font-medium mb-2">Deliver</h3>
            <p className="text-gray-600">
              Receive your one-of-a-kind piece in 4-6 weeks
            </p>
          </div>
        </div>
        
        <div className="text-center">
          <button className="bg-black text-white px-8 py-3 font-medium hover:bg-gray-800 transition-colors">
            Start Custom Order
          </button>
        </div>
      </div>
    </div>
  );
};

export default Custom;