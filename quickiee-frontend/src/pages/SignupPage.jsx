import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api";

export default function SignupPage() {
  const [email, setEmail] = useState("");
  const [name, setName] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();

  async function register() {
    try {
      const res = await api.post("hhttps://quickiee-zd1a.onrender.com/api/auth/signup", {
        email,
        name,
        password,
      });

      alert("Registered successfully. Please login.");

      navigate("/login");
    } catch (err) {
      console.error(err);
      alert("Register failed ❌");
    }
  }

  return (
    <div className="p-8 max-w-md mx-auto">
      <h1 className="text-2xl font-bold mb-4">Create Account</h1>

      <input
        className="border p-2 w-full mb-3"
        placeholder="Email"
        onChange={(e) => setEmail(e.target.value)}
      />

      <input
        className="border p-2 w-full mb-3"
        placeholder="Name"
        onChange={(e) => setName(e.target.value)}
      />

      <input
        type="password"
        className="border p-2 w-full mb-3"
        placeholder="Password"
        onChange={(e) => setPassword(e.target.value)}
      />

      <button
        onClick={register}
        className="bg-black text-white px-4 py-2 rounded w-full"
      >
        Register
      </button>
    </div>
  );
}
