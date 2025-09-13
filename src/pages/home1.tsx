import React from "react";

const categories = [
  "Everyone - All Gender Collection",
  "Accessories & Gift Cards",
  "Backpacks, Weekenders & Duffel Bags",
  "Denim Shirts & Button Downs",
  "Loungewear & Sweatshirts",
];

const colors = [
  { name: "Black", hex: "bg-black" },
  { name: "Blue", hex: "bg-blue-600" },
  { name: "Brown", hex: "bg-yellow-800" },
  { name: "Green", hex: "bg-green-600" },
  { name: "Grey", hex: "bg-gray-500" },
  { name: "Orange", hex: "bg-orange-500" },
  { name: "Pink", hex: "bg-pink-400" },
  { name: "Red", hex: "bg-red-600" },
  { name: "Tan", hex: "bg-amber-500" },
];

const sizes = ["XS", "S", "M", "L", "XL", "XXL", "XXXL"];

const products = [
  {
    id: 1,
    name: "The Cloud Relaxed Cardigan",
    image: "https://via.placeholder.com/300x400",
    price: 132,
    oldPrice: 188,
    color: "Black",
    sale: "-30% off",
    tags: [],
  },
  {
    id: 2,
    name: "The Organic Cotton Long-Sleeve Turtleneck",
    image: "https://via.placeholder.com/300x400",
    price: 35,
    oldPrice: 50,
    color: "Black",
    sale: "-30% off",
    tags: ["ORGANIC COTTON"],
  },
  {
    id: 3,
    name: "The Wool Flannel Pant",
    image: "https://via.placeholder.com/300x400",
    price: 97,
    oldPrice: 138,
    color: "Heather Charcoal",
    sale: "-30% off",
    tags: ["RENEWED MATERIALS", "CLEANER CHEMISTRY"],
  },
];

const ProductGrid: React.FC = () => {
  return (
    <div className="flex bg-white text-black">
      {/* Sidebar */}
      <aside className="w-64 p-6 border-r border-gray-200 hidden md:block">
        <h3 className="font-semibold mb-4">Category</h3>
        <ul className="space-y-2 mb-6">
          {categories.map((c, idx) => (
            <li key={idx} className="text-sm text-gray-700 cursor-pointer hover:underline">
              {c}
            </li>
          ))}
        </ul>

        <h3 className="font-semibold mb-4">Color</h3>
        <div className="grid grid-cols-5 gap-2 mb-6">
          {colors.map((c, idx) => (
            <div key={idx} className="flex flex-col items-center">
              <div
                className={`w-6 h-6 rounded-full border ${c.hex}`}
                title={c.name}
              ></div>
              <span className="text-xs text-gray-500">{c.name}</span>
            </div>
          ))}
        </div>

        <h3 className="font-semibold mb-4">Size</h3>
        <div className="flex flex-wrap gap-2">
          {sizes.map((s, idx) => (
            <button
              key={idx}
              className="px-2 py-1 border text-xs rounded hover:bg-black hover:text-white"
            >
              {s}
            </button>
          ))}
        </div>
      </aside>

      {/* Product Grid */}
      <main className="flex-1 p-6">
        <h2 className="text-2xl font-semibold mb-6">
          Men’s Clothing & Apparel - New Arrivals
        </h2>

        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
          {products.map((p) => (
            <div key={p.id} className="border border-gray-200 rounded-md p-2 relative">
              {p.sale && (
                <span className="absolute top-2 left-2 bg-red-500 text-white text-xs px-2 py-1 rounded">
                  {p.sale}
                </span>
              )}
              <img
                src={p.image}
                alt={p.name}
                className="w-full h-80 object-cover mb-3"
              />
              <h3 className="text-sm font-medium">{p.name}</h3>
              <p className="text-xs text-gray-500">{p.color}</p>
              <div className="flex items-center gap-2 mt-1">
                <span className="text-red-600 font-semibold">${p.price}</span>
                <span className="line-through text-gray-400 text-sm">
                  ${p.oldPrice}
                </span>
              </div>
              <div className="flex flex-wrap gap-2 mt-2">
                {p.tags.map((tag, idx) => (
                  <span
                    key={idx}
                    className="text-xs border border-gray-300 px-2 py-1 rounded"
                  >
                    {tag}
                  </span>
                ))}
              </div>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
};

export default ProductGrid;
