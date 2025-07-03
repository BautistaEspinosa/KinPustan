# KinPustan

**De lo tradicional a lo digital, sin perder el alma.**

KinPustan es una aplicación pensada para modernizar la gestión de negocios pequeños y medianos, llevando la eficiencia digital a un entorno tradicional sin perder la esencia que hace únicos a los comercios locales.

---

## 🌱 Historia

Inspirada en el legado de un negocio familiar, KinPustan es una herramienta creada para apoyar a las pequeñas tiendas, como la que comenzó un padre en su pueblo natal. Con un enfoque en facilitar la gestión de inventarios, ventas y más, esta aplicación combina tecnología moderna con el corazón de la tradición.

---

## 🛠️ Características

### Funcionalidades actuales
- Gestión de inventarios: CRUD completo de productos y categorías.
- Autenticación y autorización: Registro, login y manejo de JWT para sesiones seguras.
- Dashboard con reportes visuales:  
  - Ventas por día (gráfico de barras).  
  - Productos más vendidos (gráfico de pastel).  
  - Alertas de productos con bajo stock.  
  - Actividad reciente.
- Frontend moderno con React, estilizado con Tailwind CSS.
- Documentación de API con Swagger (OpenAPI).

### Funcionalidades en desarrollo
- Gestión avanzada de usuarios y roles (administradores, vendedores).
- Control y registro completo de ventas y clientes.
- Reportes detallados e historial de movimientos.
- Mejoras en la experiencia móvil y accesibilidad.
- Integración con bases de datos externas para producción.

---

## 📦 Estructura del proyecto

### Backend (Spring Boot)
- Controladores REST para productos, categorías, autenticación y dashboard.
- Servicios con lógica de negocio bien separada.
- Repositorios con Spring Data JPA para acceso a base de datos H2 en memoria.
- Seguridad configurada con Spring Security y JWT.
- Documentación generada con SpringDoc OpenAPI (Swagger UI).
- Análisis de código estático con SpotBugs y cobertura con JaCoCo.

### Frontend (React)
- Ruteo con React Router DOM.
- Formularios controlados para login, registro y gestión de productos.
- Hooks personalizados para consumir APIs y manejar estado.
- Componentes reutilizables para tablas, formularios y dashboards.
- Gráficos con Recharts para visualización de datos.
- Estilizado con Tailwind CSS para diseño responsivo y moderno.

---

## 🚀 Tecnologías usadas

- Java 17, Spring Boot 3.2, Spring Security, JPA, H2, Lombok  
- React 18, React Router, Tailwind CSS, Recharts  
- JWT para autenticación  
- Maven para gestión de dependencias y plugins  
- Swagger OpenAPI para documentación REST  

---

## 🛠️ Cómo correr el proyecto localmente

### Backend

1. Clonar el repositorio  
2. Entrar al directorio `/backend`  
3. Ejecutar:  
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```
4. El backend estará disponible en http://localhost:8082
5. Documentación Swagger: http://localhost:8082/swagger-ui.html
   
### Frontend

1. Entrar al directorio /frontend-kinpuestan
2. Instalar dependencias
   ```bash
   npm install
   ```
3. Correr la app React
    ```bash
   npm run dev
   ```
4. Abrir en el navegador http://localhost:5173 (u otro puerto que indique Vite)

## 🔐 Autenticación

- Registro y login con correo y contraseña
- Token JWT guardado en localStorage para atorizar peticiones al backend.

## 🤝 Contribuciones
Las contribuciones y sugerencias son bienvenidas.
Para colaborar, abre un issue o envía un pull request.

## 📞 Contacto
Roman Bautisat Espinosa [bautistaespinosaroman@gmail.com]
GitHub: [BautistaEspinosa](https://github.com/BautistaEspinosa)
LinkedIn: [Roman Bautista](https://www.linkedin.com/in/roman-bautista-espinosa-b04304170/)
