import { useEffect, useState } from "react";
import axios from "axios";

const API_BASE = "http://localhost:8080/api";

export default function ProductList() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchProducts();
  }, []);

  async function fetchProducts() {
    try {
    const token = localStorage.getItem("token");
    console.log("TOKEN =", token);
    const res = await axios.get(`${API_BASE}/products`,{
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
    setProducts(res.data);
    } catch (err) {
      console.error(err);
      setError("Failed to load products");
    } finally {
      setLoading(false);
    }
  }

  async function addToCart(productId) {
    try {
      const token = localStorage.getItem("token");

      await axios.post(
        `${API_BASE}/cart/add?productId=${productId}`,
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      alert("Added to cart ✅");
    } catch (err) {
      console.error(err);
      alert("Add to cart failed ❌");
    }
  }

  if (loading) return <div className="p-8">Loading products...</div>;
  if (error) return <div className="p-8 text-red-600">{error}</div>;

  return (
    <div className="min-h-screen bg-gray-50 p-8">
      <h1 className="text-3xl font-bold mb-6">Products</h1>

      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
        {products.map((p) => (
          <div
            key={p.id}
            className="bg-white rounded-2xl shadow-md p-4 flex flex-col"
          >
            {p.imageUrl && (
              <img
                src={p.imageUrl}
                alt={p.name}
                onError={(e) => {
                  e.target.src = "https://via.placeholder.com/300";
                }}
                className="h-40 w-full object-cover rounded-xl mb-3"
              />
            )}

            <h2 className="text-lg font-semibold">{p.name}</h2>
            <p className="text-sm text-gray-600 mb-2">{p.category}</p>

            <div className="mt-auto">
              <p className="text-xl font-bold mb-3">₹{p.price}</p>

              <button
                onClick={() => addToCart(p.id)}
                className="w-full rounded-xl px-4 py-2 font-semibold shadow hover:shadow-lg transition"
              >
                Add to Cart
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
