// RegisterPage.jsx
import RegisterForm from "../components/RegisterForm";
import {useNavigate} from 'react-router-dom';

export default function RegisterPage() {
  const navigate = useNavigate();
  const handleRegister = async (formData) => {
    try {
      const response = await fetch("http://localhost:8082/auth/register", {
        method: "POST",    // Aquí faltaba abrir las llaves para el objeto
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          nombre: formData.nombre,
          correo: formData.correo,       // Corregido 'conrreo' a 'correo'
          contrasenia: formData.contrasenia,
          roles: ["USER"]               // ¡IMPORTANTE! Backend espera roles, agrégalos
        })
      });

      const data = await response.json();

      if (response.ok) {
        alert("Registro exitoso!");
        navigate("/login");
      } else {
        alert("Error: " + (data.message || "Algo salió mal"));
      }
    } catch (error) {
      console.error(error);
      alert("Error en la conexión con el servidor");
    }
  };

  return (
    <div>
      <h1>Crear cuenta</h1>
      <RegisterForm onSubmit={handleRegister} />
    </div>
  );
}
