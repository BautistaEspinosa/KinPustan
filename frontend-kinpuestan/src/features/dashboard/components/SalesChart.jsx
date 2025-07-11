import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';

const data = [
  { fecha: '12', ventas: 4 },
  { fecha: '13', ventas: 6 },
  { fecha: '14', ventas: 7 },
  { fecha: '15', ventas: 9 },
  { fecha: '16', ventas: 7 },
];

export default function SalesChart() {
  return (
    <div className="bg-white p-4 shadow rounded">
      <ResponsiveContainer width="90%" height={300}>
        <BarChart data={data}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="fecha" />
          <YAxis allowDecimals={false} />
          <Tooltip />
          <Bar dataKey="ventas" fill="#3B82F6" radius={[3, 4, 0, 0]} />
        </BarChart>
      </ResponsiveContainer>
    </div>
  );
}







