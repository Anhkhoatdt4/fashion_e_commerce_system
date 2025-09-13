import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store";
import { fetchFeaturedProducts } from "../store/productSlice";
import { addToCart } from "../store/cartSlice";
import { ProductCard } from "../components";
import { Link } from "react-router-dom";
import pexelsImage from '../assets/pexels-nicole-avagliano-1132392-2749481.jpg';

const Home: React.FC = () => {
  const dispatch = useDispatch();
  const { featuredProducts, isLoading } = useSelector(
    (state: RootState) => state.products
  );
  const { isAuthenticated } = useSelector((state: RootState) => state.auth);

  useEffect(() => {
    dispatch(fetchFeaturedProducts() as any);
  }, [dispatch]);

  const handleAddToCart = (productId: number) => {
    if (!isAuthenticated) {
      alert("Vui lòng đăng nhập để thêm sản phẩm vào giỏ hàng");
      return;
    }
    dispatch(addToCart({ productId, quantity: 1 }) as any);
  };

  return (
    <div className="min-h-screen bg-white">
      {/* Hero Section */}
      <section className="relative h-[80vh] bg-cover bg-center flex items-center justify-center"
        style={{ 
          backgroundImage: "url('https://i.pinimg.com/736x/0b/a6/f1/0ba6f158707462f47f02b231e30dbbc1.jpg')", 
          backgroundSize: 'contain',
          backgroundPosition: "center",
          backgroundRepeat: "no-repeat"
         }}
      >
        <div className="text-center text-white">
          <h1 className="text-4xl md:text-6xl font-bold mb-4">Your Cozy Era</h1>
          <p className="text-lg md:text-xl mb-6">
            Get peak comfy-chic with new winter essentials.
          </p>
          <Link
            to="/products"
            className="px-8 py-3 bg-white text-black font-semibold hover:bg-gray-100 transition"
          >
            SHOP NOW
          </Link>
        </div>
      </section>

      {/* Categories */}
      <section className="py-12">
        <h2 className="text-center text-xl font-semibold mb-8">
          Shop by Category
        </h2>
        <div className="flex flex-wrap justify-center gap-8 px-4">
          {["Shirts", "Denim", "Tees", "Pants", "Sweaters", "Outerwear"].map(
            (cat, idx) => (
              <div key={idx} className="text-center">
                <img
                  src={`https://img.sonofatailor.com/images/customizer/product/extra-heavy-cotton/ss/Black.jpg`}
                  alt={cat}
                  className="w-40 h-52 object-cover mb-2"
                />
                <p className="uppercase text-sm font-semibold hover:underline cursor-pointer">
                  {cat}
                </p>
              </div>
            )
          )}
        </div>
      </section>

      {/* Highlight Sections */}
      <section className="grid grid-cols-1 md:grid-cols-3 gap-4 px-4 mb-16">
        {[
          { title: "New Arrivals", btn: "SHOP THE LATEST" },
          { title: "Best-Sellers", btn: "SHOP YOUR FAVORITES" },
          { title: "The Holiday Outfit", btn: "SHOP OCCASION" },
        ].map((item, idx) => (
          <div
            key={idx}
            className="relative h-96 bg-gray-200 flex items-center justify-center text-white"
            style={{
              backgroundImage: `url(https://everlane.com/path-${idx}.jpg)`,
              backgroundSize: "cover",
              backgroundPosition: "center",
            }}
          >
            <div className="text-center">
              <h3 className="text-2xl font-bold mb-4">{item.title}</h3>
              <button className="px-6 py-2 bg-white text-black font-semibold hover:bg-gray-100">
                {item.btn}
              </button>
            </div>
          </div>
        ))}
      </section>

      {/* Featured Products Carousel */}
      <section className="px-4 mb-20">
        <h2 className="text-center text-2xl font-semibold mb-2">
          Everlane Favorites
        </h2>
        <p className="text-center text-gray-500 mb-8">
          Beautifully Functional. Purposefully Designed. Consciously Crafted.
        </p>

        {isLoading ? (
          <div className="flex justify-center py-12">
            <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-black"></div>
          </div>
        ) : (
          <div className="flex gap-6 overflow-x-auto no-scrollbar">
            {featuredProducts.slice(0, 10).map((product) => (
              <div key={product.id} className="min-w-[220px]">
                <ProductCard
                  product={product}
                  onAddToCart={handleAddToCart}
                />
              </div>
            ))}
          </div>
        )}
      </section>
    </div>
  );
};

export default Home;