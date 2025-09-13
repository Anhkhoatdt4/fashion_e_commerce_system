import React, { useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Provider } from 'react-redux';
import { store } from './store';
import { Header, Footer } from './components';
import { Home, Login, Register, Women, Men, About, Custom } from './pages';
import { useDispatch, useSelector } from 'react-redux';
import { verifyToken } from './store/authSlice';
import { fetchCart } from './store/cartSlice';
import { RootState } from './store';
import PageNotFound from './pages/PageNotFound';
import './App.css';

// Component wrapper để sử dụng Redux hooks
const AppContent: React.FC = () => {
  const dispatch = useDispatch();
  const { isAuthenticated, token } = useSelector((state: RootState) => state.auth);

  useEffect(() => {
    // Verify token on app load
    if (token) {
      dispatch(verifyToken() as any);
    }
  }, [dispatch, token]);

  useEffect(() => {
    // Fetch cart if user is authenticated
    if (isAuthenticated) {
      dispatch(fetchCart() as any);
    }
  }, [dispatch, isAuthenticated]);

  return (
    <Router>
      <div className="App min-h-screen flex flex-col">
        <Header />
        <main className="flex-1">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/women" element={<Women />} />
            <Route path="/men" element={<Men />} />
            <Route path="/about" element={<About />} />
            <Route path="/custom" element={<Custom />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            {/* 
            // Uncomment these routes as you create the corresponding pages
            <Route path="/products" element={<Products />} />
            <Route path="/products/:id" element={<ProductDetail />} />
            <Route path="/cart" element={<Cart />} />
            <Route path="/checkout" element={<Checkout />} />
            <Route path="/profile" element={<Profile />} />
            */}


            <Route path="*" element={<PageNotFound/>} />
          </Routes>
        </main>
        <Footer />
      </div>
    </Router>
  );
};

function App() {
  return (
    <Provider store={store}>
      <AppContent />
    </Provider>
  );
}

export default App;
