import { createContext, useContext, useEffect, useState } from "react";
import api from "../api";

const CartContext = createContext();

export function CartProvider({ children }) {
  const [cartCount, setCartCount] = useState(0);

  async function refreshCartCount() {
    const token = localStorage.getItem("token");
    if (!token) {
      setCartCount(0);
      return;
    }

    try {
      const res = await api.get("/cart");
      const totalQty = res.data.reduce(
        (sum, item) => sum + item.quantity,
        0
      );
      setCartCount(totalQty);
    } catch (err) {
      console.error("Cart count fetch failed");
      setCartCount(0);
    }
  }

  useEffect(() => {
    refreshCartCount();
  }, []);

  return (
    <CartContext.Provider value={{ cartCount, refreshCartCount }}>
      {children}
    </CartContext.Provider>
  );
}

export function useCart() {
  return useContext(CartContext);
}
