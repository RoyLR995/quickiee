import { Link, useNavigate } from "react-router-dom";
import { useCart } from "../context/CartContext";

export default function Navbar() {
  const navigate = useNavigate();
  const token = localStorage.getItem("token");
  const { cartCount, refreshCartCount } = useCart();

  function logout() {
    localStorage.removeItem("token");
    refreshCartCount();
    navigate("/login");
  }

  return (
    
    <nav className="flex gap-6 p-4 border-b">
      <Link to="/products">Products</Link>

      <div className="relative">
        <Link to="/cart">Cart</Link>

        {cartCount > 0 && (
          <span className="absolute -top-2 -right-4 bg-red-600 text-white text-xs px-2 py-0.5 rounded-full">
            {cartCount}
          </span>
        )}
      </div>

      {!token ? (
        <Link to="/login">Login</Link>
      ) : (
        <button onClick={logout}>Logout</button>
      )}
    </nav>
  );
}

