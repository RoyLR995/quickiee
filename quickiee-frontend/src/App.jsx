import { Routes, Route } from "react-router-dom";
import ProductList from "./components/ProductList";
import CartPage from "./pages/CartPage";
import LoginPage from "./pages/LoginPage";
import Navbar from "./components/Navbar";
import ProtectedRoute from "./components/ProtectedRoute";

export default function App() {
  return (
    <>
      <Navbar />
      <Routes>
        <Route path="/" element={<ProductList />} />
        <Route path="/login" element={<LoginPage />} />
        <Route
          path="/cart"
          element={
            <ProtectedRoute>
              <CartPage />
            </ProtectedRoute>
          }
        />
      </Routes>
    </>
  );
}
