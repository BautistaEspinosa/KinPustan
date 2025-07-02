export async function getLowStockProducts() {
  const token = localStorage.getItem("token"); // Recupera el JWT

  const response = await fetch("http://localhost:8082/api/products/low-stock", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`, // Agrega el token al header
    },
  });

  if (!response.ok) {
    if (response.status === 401) {
      throw new Error("No autorizado. Tu sesión ha expirado.");
    }
    throw new Error("Error al obtener productos con bajo stock");
  }

  return await response.json();
}
