import { useEffect, useState } from "react";
import { getLowStockProducts } from "../services/dashboardService";

export default function LowStockAlerts() {
  const [products, setProducts] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    getLowStockProducts()
      .then(setProducts)
      .catch((error) => {
        console.error("Error:", error.message);
        setError(error.message);
      });
  }, []);

  return (
    <div className="bg-white p-4 shadow rounded">
      <h2 className="text-lg font-semibold mb-4">Productos con bajo stock</h2>

      {error && (
        <p className="text-red-500 font-semibold mb-2">
          ⚠️ {error}
        </p>
      )}

      {products.length === 0 && !error ? (
        <p className="text-gray-500">Todo en orden 🎉</p>
      ) : (
        <ul className="list-disc pl-5 space-y-2">
          {products.map((p) => (
            <li key={p.id}>
              <span className="font-medium">{p.nombre}</span> — Stock: {p.stock}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
