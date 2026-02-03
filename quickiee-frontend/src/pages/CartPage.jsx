import { useEffect, useState } from "react";
import api from "../api";

export default function CartPage() {
  const [cart, setCart] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchCart();
  }, []);

  async function fetchCart() {
    try {
      const res = await api.get("/cart");
      setCart(res.data);
    } catch (err) {
      console.error(err);
      setError("Failed to load cart");
    } finally {
      setLoading(false);
    }
  }

  // ✅ increment quantity
  async function increment(productId) {
    try {
      await api.post(`/cart/add?productId=${productId}`);
      fetchCart(); // refresh
    } catch (err) {
      console.error(err);
      alert("Increase failed");
    }
  }

  // ✅ decrement quantity
  async function decrement(productId) {
    try {
      await api.post(`/cart/decrement?productId=${productId}`);
      fetchCart(); // refresh
    } catch (err) {
      console.error(err);
      alert("Decrease failed");
    }
  }

  function getTotal() {
    return cart.reduce(
      (sum, item) => sum + item.product.price * item.quantity,
      0
    );
  }

  if (loading) return <div className="p-8">Loading cart...</div>;
  if (error) return <div className="p-8 text-red-600">{error}</div>;

  return (
    <div className="min-h-screen bg-gray-50 p-8">
      <h1 className="text-3xl font-bold mb-6">Your Cart</h1>

      {cart.length === 0 ? (
        <p className="text-gray-600">Cart is empty</p>
      ) : (
        <div className="space-y-4">
          {cart.map((item) => (
            <div
              key={item.product.id}
              className="bg-white rounded-2xl shadow p-4 flex gap-4 items-center"
            >
              <img
                src={item.product.imageUrl}
                alt={item.product.name}
                className="h-24 w-24 object-cover rounded-xl"
                onError={(e) => {
                  e.target.src = "https://via.placeholder.com/150";
                }}
              />

              <div className="flex-1">
                <h2 className="font-semibold text-lg">
                  {item.product.name}
                </h2>

                <p className="font-bold">₹{item.product.price}</p>

                {/* ✅ quantity controls */}
                <div className="flex items-center gap-3 mt-2">
                  <button
                    onClick={() => decrement(item.product.id)}
                    className="px-3 py-1 bg-red-600 text-white rounded"
                  >
                    −
                  </button>

                  <span className="font-semibold">
                    {item.quantity}
                  </span>

                  <button
                    onClick={() => increment(item.product.id)}
                    className="px-3 py-1 bg-green-600 text-white rounded"
                  >
                    +
                  </button>
                </div>
              </div>
            </div>
          ))}

          <div className="text-right text-2xl font-bold mt-6">
            Total: ₹{getTotal()}
          </div>

          <button className="mt-4 px-6 py-3 rounded-2xl bg-black text-white font-semibold hover:shadow-lg">
            Checkout
          </button>
        </div>
      )}
    </div>
  );
}
