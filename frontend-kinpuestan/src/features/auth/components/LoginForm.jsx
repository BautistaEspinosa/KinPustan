import {useState} from 'react';
import {useNavigate} from 'react-router-dom';


export default function LoginForm({ onSubmit}){
  const [form,setForm] = useState({
    correo: "",
    contrasenia: "",
    });
const navigate =  useNavigate();

  const handleChange = (e) =>{
    const{name,value} = e.target;
    setForm({...form, [name]: value});
  };
const handleSubmit = (e)=>{
  e.preventDefault();
  onSubmit(form);
  };
const handleClickRegister = () =>{
  navigate('/register');
  };
return(
  <form onSubmit={handleSubmit}
  className="flex flex-col gap-5 p-6 bg-yellow-50 rounded-2xl shadow-lg w-full max-w-md mx-auto" >
  <div className="text-center">
<img src="/logo.png" alt="Logo KinPustan" />
          <h2 className="text-xl font-bold text-yellow-800">Bienvenido a KinPustan</h2>
        </div>
    <div className="flex flex-col">
            <label className="mb-1 font-medium text-yellow-900">Correo: </label>
      <input
      type="email"
      name="correo"
      value={form.correo}
      onChange={handleChange}
      required
      className="border border-yellow-300 rounded-md p-2 focus:outline-none focus:ring-2 focus:ring-yellow-500"
      />
      </div>
      <div className="flex flex-col">
              <label className="mb-1 font-medium text-yellow-900">Contraseña: </label>
        <input
        type="password"
        name="contrasenia"
        value={form.contrasenia}
        onChange={handleChange}
        required
        className="border border-yellow-300 rounded-md p-2 focus:outline-none focus:ring-2 focus:ring-yellow-500"
               />
        </div>
        <button type="submit"
          className="bg-yellow-600 text-white py-2 px-4 rounded-md hover:bg-yellow-700 transition"
              >Inciar Sesion</button>
        <button type="button"
            className="text-yellow-700 underline hover:text-yellow-900 text-sm text-center"
              onClick={handleClickRegister}>Registrase</button>
    </form>);
    }