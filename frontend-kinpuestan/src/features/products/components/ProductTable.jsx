import React from "react";

export default function ProductTable({ productos, onEdit, onDelete }) {
  if (productos.length === 0)
    return <p className="text-center p-4 text-gray-400">No hay productos.</p>;

  return (
    <table className="min-w-full table-auto text-white">
      <thead className="bg-gray-700 text-gray-300">
        <tr>
          <th className="px-4 py-2 text-left">Nombre</th>
          <th className="px-4 py-2 text-left">ID Categoría</th>
          <th className="px-4 py-2 text-right">Stock</th>
          <th className="px-4 py-2 text-right">Precio</th>
          <th className="px-4 py-2 text-center">Acciones</th>
        </tr>
      </thead>
      <tbody>
        {productos.map((p) => (
          <tr key={p.id} className="border-t border-gray-700">
            <td className="px-4 py-2">{p.nombre}</td>
            <td className="px-4 py-2">{p.categoria?.id || "-"}</td>
            <td className="px-4 py-2 text-right">{p.stock}</td>
            <td className="px-4 py-2 text-right">${p.precio.toFixed(2)}</td>
            <td className="px-4 py-2 text-center">
              <button
                onClick={() => onEdit(p.id)}
                className="text-blue-400 hover:underline mr-2"
              >
                Editar
              </button>
              <button
                onClick={() => onDelete(p.id)}
                className="text-red-400 hover:underline"
              >
                Eliminar
              </button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}
