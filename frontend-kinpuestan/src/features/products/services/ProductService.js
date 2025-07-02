const API_URL = "http://localhost:8082/api/products";

export async function fetchProducts(page = 0, size = 10) {
  const res = await fetch(`${API_URL}?page=${page}&size=${size}`);
  if (!res.ok) throw new Error("Error cargando productos");
  return res.json();
}

export async function fetchProductById(id) {
  const res = await fetch(`${API_URL}/${id}`);
  if (!res.ok) throw new Error("Producto no encontrado");
  return res.json();
}

export async function createProduct(producto) {
  const res = await fetch(API_URL, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(producto),
  });
  if (!res.ok) throw new Error("Error creando producto");
  return res.json();
}

export async function updateProduct(id, updateDTO) {
  const res = await fetch(`${API_URL}/id/${id}`, {
    method: "PATCH",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(updateDTO),
  });
  if (!res.ok) throw new Error("Error actualizando producto");
  return res.json();
}

export async function deleteProduct(id) {
  const res = await fetch(`${API_URL}/${id}`, { method: "DELETE" });
  if (!res.ok) throw new Error("Error eliminando producto");
}
