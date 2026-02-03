import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  async function login() {
    try {
        const res = await axios.post(
        "http://localhost:8080/api/auth/login",
        { email, password }
        );
        console.log("LOGIN RESPONSE:", res.data);
        localStorage.setItem("token", res.data.token);
        alert("Logged in ✅");
        navigate("/");

    } catch (err) {
        alert("Login failed");
    }
    }

  return (
    <div className="p-8 max-w-md mx-auto">
      <h1 className="text-2xl font-bold mb-4">Login</h1>

      <input
        className="border p-2 w-full mb-3"
        placeholder="Email"
        onChange={(e) => setEmail(e.target.value)}
      />

      <input
        type="password"
        className="border p-2 w-full mb-3"
        placeholder="Password"
        onChange={(e) => setPassword(e.target.value)}
      />

      <button
        onClick={login}
        className="bg-black text-white px-4 py-2 rounded"
      >
        Login
      </button>

      <p className="mt-4 text-center">
        New user?{" "}
        <a href="/register" className="text-blue-600 underline">
            Create account
        </a>
        </p>
    </div>
  );
}