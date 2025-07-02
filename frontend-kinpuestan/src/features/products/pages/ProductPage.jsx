import React, { useState } from "react";
import ProductTable from "../components/ProductTable";
import ProductForm from "../components/ProductForm";
import { useProducts } from "../hooks/useProducts";
import * as productService from "../services/productService";

export default function ProductPage() {
  const { productos, loading, addProduct, editProduct, removeProduct } = useProducts();

  const [form, setForm] = useState({
    id: null,
    nombre: "",
    categoriaId: "",
    stock: "",
    precio: "",
  });
  const [modoEdicion, setModoEdicion] = useState(false);

  function handleChange(e) {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  }

  async function handleSubmit(e) {
    e.preventDefault();

    if (!form.nombre || !form.categoriaId || !form.stock || !form.precio) {
      alert("Por favor llena todos los campos");
      return;
    }

    try {
      if (modoEdicion) {
        const updateDTO = {
          nombre: form.nombre,
          stock: Number(form.stock),
          precio: Number(form.precio),
          categoriaId: Number(form.categoriaId),
        };
        await editProduct(form.id, updateDTO);
      } else {
        const nuevoProducto = {
          nombre: form.nombre,
          categoria: { id: Number(form.categoriaId) },
          stock: Number(form.stock),
          precio: Number(form.precio),
        };
        await addProduct(nuevoProducto);
      }

      setForm({ id: null, nombre: "", categoriaId: "", stock: "", precio: "" });
      setModoEdicion(false);
    } catch (error) {
      alert(error.message);
    }
  }

  async function handleEdit(id) {
    try {
      const producto = await productService.fetchProductById(id);
      setForm({
        id: producto.id,
        nombre: producto.nombre,
        categoriaId: producto.categoria?.id || "",
        stock: producto.stock,
        precio: producto.precio,
      });
      setModoEdicion(true);
    } catch (error) {
      alert(error.message);
    }
  }

  async function handleDelete(id) {
    if (window.confirm("¿Seguro que quieres eliminar este producto?")) {
      try {
        await removeProduct(id);
        if (modoEdicion && form.id === id) {
          setForm({ id: null, nombre: "", categoriaId: "", stock: "", precio: "" });
          setModoEdicion(false);
        }
      } catch (error) {
        alert(error.message);
      }
    }
  }

  return (
    <div className="p-4 bg-gray-900 text-white min-h-screen rounded shadow">
      <h1 className="text-2xl font-bold mb-4">Gestión de Productos</h1>
      <ProductForm form={form} onChange={handleChange} onSubmit={handleSubmit} modoEdicion={modoEdicion} />
      {loading ? <p>Cargando productos...</p> : <ProductTable productos={productos} onEdit={handleEdit} onDelete={handleDelete} />}
    </div>
  );
}
