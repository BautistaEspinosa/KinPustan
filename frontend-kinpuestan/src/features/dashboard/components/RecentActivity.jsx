import React from 'react';

const recentlyActivity = [
  { id: 1, description: 'Venta de 3 unidades de Coca-Cola', date: '2025-06-18 10:30' },
  { id: 2, description: 'Registro de 3 usuarios', date: '2025-06-18 10:50' },
  { id: 3, description: 'Venta de 10 kilos de frijoles', date: '2025-06-19 10:30' },
  { id: 4, description: 'Eliminación de 3 usuarios', date: '2025-06-22 10:30' },
  ];

export default function RecentActivity(){
  return(
    <div className="bg-white p-4 shadow rounded">
          <h2 className="text-lg font-semibold mb-4">Actividad Reciente</h2>
          <ul className="divide-y divide-gray-200">
            {recentlyActivity.map(({ id, description, date }) => (
              <li key={id} className="py-2 text-sm">
                <p>{description}</p>
                <span className="text-gray-500 text-xs">{date}</span>
              </li>
            ))}
          </ul>
        </div>
    );
  }