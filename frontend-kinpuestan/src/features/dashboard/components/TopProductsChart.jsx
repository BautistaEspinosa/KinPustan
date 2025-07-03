import React from 'react';
import { PieChart, Pie, Cell, Tooltip, ResponsiveContainer, Legend }from 'recharts';

const data = [
  { nombre: 'Coca-Cola', ventas: 120 },
  { nombre: 'Pan Bimbo', ventas: 90 },
  { nombre: 'Sabritas', ventas: 75 },
  { nombre: 'Huevos', ventas: 60 },
  { nombre: 'Leche Lala', ventas: 50 },
];
const COLORS = ['#3B82F6', '#10B981', '#F59E0B', '#EF4444', '#8B5CF6'];

export default function TopProductsChart(){
return (
  <div className="bg-white p-4 shadow rounded">
       <ResponsiveContainer width="80%" height={270}>
         <PieChart>
           <Pie
           data = {data}
           dataKey = "ventas"
           nameKey = "nombre"
           cx="50%"
           cy = "50%"
           outerRadius = {90}
           label
           >
           {data.map((entry, index) => (
                               <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                             ))}
                           </Pie>
                           <Tooltip/>
                           <Legend/>
           </PieChart>

       </ResponsiveContainer>
     </div>
    );

}