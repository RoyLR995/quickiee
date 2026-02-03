import { Link, useNavigate } from "react-router-dom";

export default function Navbar() {
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  function logout() {
    localStorage.removeItem("token");
    navigate("/login");
  }

  return (
    
    <div className="flex gap-4 p-4 shadow bg-white">
        <div className="text-red-600 text-3xl">TEST</div>
      <Link to="/">Products</Link>
      <Link to="/cart">Cart</Link>

      {!token ? (
        <Link to="/login">Login</Link>
      ) : (
        <button onClick={logout}>Logout</button>
      )}
    </div>
  );
}

