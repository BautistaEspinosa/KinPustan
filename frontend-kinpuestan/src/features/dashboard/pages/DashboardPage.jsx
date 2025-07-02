import SalesChart from "../components/SalesChart";
import TopProductsChart from "../components/TopProductsChart";
import LowStockAlert from "../components/LowStockAlert";
import RecentActivity from "../components/RecentActivity";
import SummaryCard from "../components/SummaryCard";

export default function DashboardPage() {
  return (
    <div className="p-6 bg-gray-50 min-h-screen">
      <h1 className="text-3xl font-bold mb-6">Dashboard</h1>

      {/* Primera fila con 4 componentes en cuadrícula */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 mb-6">
        <div className="bg-white p-4 rounded-lg shadow">
          <SalesChart />
        </div>
        <div className="bg-white p-4 rounded-lg shadow">
          <TopProductsChart />
        </div>
        <div className="bg-white p-4 rounded-lg shadow">
          <LowStockAlert />
        </div>
        <div className="bg-white p-4 rounded-lg shadow">
          <RecentActivity />
        </div>
      </div>
<div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 gap-6 my-6">
      <SummaryCard title="Productos registrados" value="58" link="/products" />
      <SummaryCard title="Usuarios activos" value="12" link="/users" />
      {/* Agrega más tarjetas  */}
    </div>

    </div>
  );
}