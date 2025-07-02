import React from "react";

export default function ProductForm({ form, onChange, onSubmit, modoEdicion }) {
  return (
    <form onSubmit={onSubmit} className="mb-6 grid grid-cols-1 md:grid-cols-5 gap-4">
      <input
        type="text"
        name="nombre"
        placeholder="Nombre"
        value={form.nombre}
        onChange={onChange}
        className="p-2 rounded text-black"
        required
      />
      <input
        type="number"
        name="categoriaId"
        placeholder="ID Categoría"
        value={form.categoriaId}
        onChange={onChange}
        className="p-2 rounded text-black"
        required
        min={1}
      />
      <input
        type="number"
        name="stock"
        placeholder="Stock"
        value={form.stock}
        onChange={onChange}
        className="p-2 rounded text-black"
        required
        min={0}
      />
      <input
        type="number"
        step="0.01"
        name="precio"
        placeholder="Precio"
        value={form.precio}
        onChange={onChange}
        className="p-2 rounded text-black"
        required
        min={0}
      />
      <button type="submit" className="bg-blue-600 hover:bg-blue-700 rounded text-white font-bold px-4">
        {modoEdicion ? "Actualizar" : "Agregar"}
      </button>
    </form>
  );
}
