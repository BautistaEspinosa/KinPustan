import { useState, useEffect } from "react";
import * as productService from "../services/productService";

export function useProducts() {
  const [productos, setProductos] = useState([]);
  const [loading, setLoading] = useState(false);

  async function loadProducts() {
    setLoading(true);
    try {
      const data = await productService.fetchProducts();
      setProductos(data);
    } catch (error) {
      alert(error.message);
    } finally {
      setLoading(false);
    }
  }

  async function addProduct(producto) {
    await productService.createProduct(producto);
    await loadProducts();
  }

  async function editProduct(id, updateDTO) {
    await productService.updateProduct(id, updateDTO);
    await loadProducts();
  }

  async function removeProduct(id) {
    await productService.deleteProduct(id);
    await loadProducts();
  }

  useEffect(() => {
    loadProducts();
  }, []);

  return {
    productos,
    loading,
    addProduct,
    editProduct,
    removeProduct,
    reload: loadProducts,
  };
}
