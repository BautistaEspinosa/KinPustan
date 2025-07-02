import {useState} from 'react'

export default function RegisterForm({onSubmit}){
  const[form,setForm] = useState({
    nombre: "",
    correo:"",
    contrasenia: "",
    });
  const handleChange = (e) =>{
    const{name,value} = e.target;
    setForm({...form,[name]:value});
  };
const handleSubmit = (e)=>{
  e.preventDefault();
  onSubmit(form);};

  return(
    <form onSubmit={handleSubmit} className="flex flex-col gap-4">
      <div>
        <label>Nombre: </label>
        <input
        type="text"
        name="nombre"
        value={form.nombre}
        onChange={handleChange}
        required
        />
        </div>
      <div>
            <label>Correo: </label>
            <input
            type="email"
            name="correo"
            value={form.correo}
            onChange={handleChange}
            required
            />
            </div>
            <div>
              <label>Contraseña: </label>
              <input
              type="password"
              name="contrasenia"
              value={form.contrasenia}
              onChange={handleChange}
              required
              />
              </div>
              <button type="submit">Crear Cuenta</button>
      </form>);
      }