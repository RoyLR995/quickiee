import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api";

export default function ProductList() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [cartMap, setCartMap] = useState({});

  const navigate = useNavigate();

  useEffect(() => {
    fetchProducts();
    const token = localStorage.getItem("token");
    if (token) {
      fetchCart();
    }
  }, []);

  async function fetchProducts() {
    try {
      const res = await api.get("/products");
      setProducts(res.data);
    } catch (err) {
      console.error(err);
      setError("Failed to load products");
    } finally {
      setLoading(false);
    }
  }

  async function fetchCart() {
    try {
      const res = await api.get("/cart");

      const map = {};
      res.data.forEach(item => {
        map[item.product.id] = item.quantity;
      });

      setCartMap(map);
    } catch (err) {
      console.error("Cart load failed", err);
    }
  }

  async function increment(productId) {
    await api.post(`/cart/add?productId=${productId}`);
    fetchCart();
  }

  async function decrement(productId) {
    await api.post(`/cart/decrement?productId=${productId}`);
    fetchCart();
  }

  async function addToCart(productId) {
    const token = localStorage.getItem("token");

    if (!token) {
      alert("Please login first");
      navigate("/login");
      return;
    }

    try {
      await api.post(`/cart/add?productId=${productId}`);
      alert("Added to cart ✅");
    } catch (err) {
      console.error(err);

      if (err.response?.status === 403) {
        alert("Session expired — login again");
        navigate("/login");
      } else {
        alert("Add to cart failed ❌");
      }
    }
  }

  if (loading) return <div className="p-8">Loading products...</div>;
  if (error) return <div className="p-8 text-red-600">{error}</div>;

  return (
    <div className="min-h-screen bg-gray-50 p-8">
      {/* Header */}
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold">Products</h1>

        <button
          onClick={() => navigate("/cart")}
          className="px-5 py-2 rounded-xl bg-black text-white font-semibold shadow hover:shadow-lg transition"
        >
          View Cart
        </button>
      </div>

      {/* Grid */}
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
        {products.map((p) => (
          <div
            key={p.id}
            className="bg-white rounded-2xl shadow-md p-4 flex flex-col hover:shadow-lg transition"
          >
            <img
              src={p.imageUrl}
              alt={p.name}
              onError={(e) => (e.target.src = "https://via.placeholder.com/300")}
              className="h-40 w-full object-cover rounded-xl mb-3"
            />

            <h2 className="text-lg font-semibold">{p.name}</h2>
            <p className="text-sm text-gray-600 mb-2">{p.category}</p>

            <div className="mt-auto">
              <p className="text-xl font-bold mb-3">₹{p.price}</p>

              {cartMap[p.id] ? (

                <div className="flex items-center gap-3">
                  <button
                    onClick={() => decrement(p.id)}
                    className="px-3 py-1 bg-red-600 text-white rounded"
                  >
                    −
                  </button>

                  <span className="font-semibold">
                    {cartMap[p.id]}
                  </span>

                  <button
                    onClick={() => increment(p.id)}
                    className="px-3 py-1 bg-green-600 text-white rounded"
                  >
                    +
                  </button>
                </div>

              ) : (

                <button
                  onClick={() => increment(p.id)}
                  className="w-full rounded-xl px-4 py-2 font-semibold shadow hover:shadow-lg bg-black text-white"
                >
                  Add to Cart
                </button>

              )}

            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
