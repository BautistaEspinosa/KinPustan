import SalesChart from "../components/SalesChart";
import TopProductsChart from "../components/TopProductsChart";
import LowStockAlert from "../components/LowStockAlert";
import RecentActivity from "../components/RecentActivity";
import SummaryCard from "../components/SummaryCard";

export default function DashboardPage() {
  return (
    <div >
      <div className="max-w-7xl mx-auto">
        {/* Título principal */}
        <h1 className="text-4xl font-bold text-gray-800 mb-10 text-center">📊 Dashboard</h1>

        {/* Tarjetas resumen */}
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 gap-6 mb-10">
          <SummaryCard title="Productos registrados" value="68" link="/products" />
          <SummaryCard title="Usuarios activos" value="12" link="/users" />
          {/* Puedes agregar más tarjetas si lo necesitas */}
        </div>

        {/* Gráficas */}
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6 mb-10">
          <div className="bg-white p-6 rounded-2xl shadow-xl col-span-2">
            <h2 className="text-xl font-semibold mb-4 text-gray-700">Ventas por día</h2>
            <SalesChart />
          </div>
          <div className="bg-white p-6 rounded-2xl shadow-xl">
            <h2 className="text-xl font-semibold mb-4 text-gray-700">Productos más vendidos</h2>
            <TopProductsChart />
          </div>
           <div className="bg-white p-6 rounded-2xl shadow-xl">
                      <h2 className="text-xl font-semibold mb-4 text-gray-700">Actividad reciente</h2>
                      <RecentActivity />
                    </div>
        </div>

        {/* Secciones finales */}
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6 mb-10">
          <div className="bg-white p-6 rounded-2xl shadow-xl">
            <h2 className="text-xl font-semibold mb-4 text-gray-700">Productos con bajo stock</h2>
            <LowStockAlert />
          </div>

        </div>
      </div>
    </div>
  );
}
