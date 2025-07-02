import LoginForm from '../components/LoginForm';
import {useNavigate} from 'react-router-dom';


export default function LoginPage() {
  const navigate = useNavigate();
  const handleLogin = async (formData) => {
    try{
      const response = await fetch("http://localhost:8082/auth/login",{
        method: "POST",
        headers:{
          "Content-Type":"application/json"
          },
        body: JSON.stringify({
          correo: formData.correo,
          contrasenia: formData.contrasenia
          })
        });
const data = await response.json();
const token = data.token;

    if(response.ok && token !== "Usuario no existe"){
      console.log("Login existoso. Token: ",token);
      localStorage.setItem("token", token);
      navigate("/dashboard");
      }else{
            alert("Error: "+data.error);
        }
      }catch (error){
        console.error("Error en el login: ",error);
        alert("Fallo la conexión con el servidor");
        }
      };


  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-yellow-100 to-yellow-200">
      <LoginForm onSubmit={handleLogin} />
    </div>
  );
}
