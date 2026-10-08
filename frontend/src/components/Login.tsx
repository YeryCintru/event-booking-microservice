import React, { useState } from 'react';
import { authApi } from '../api/axiosConfig';

interface LoginProps{
    onLoginSuccess: (token: string) => void;
}

export const Login: React.FC<LoginProps> = ({ onLoginSuccess }) => {
    const [email, setEmail] = useState('usuarioTest');
    const [password, setPassword] = useState('123456');
    const [error, setError] = useState('');

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try{
            const response = await authApi.post('/login', {email, password});
            const token = response.data.token;
            localStorage.setItem('jwt_token',token);
            onLoginSuccess(token);
        } catch (err){
            setError('Credenciales inválidas o servicio inaccesible');
        }
    };
   
return (
    <div id="login" style={{ maxWidth: '500px', margin: '20px auto', padding: '40px', border: '1px solid #ccc', borderRadius: '8px'}}>
        <h2>Iniciar Sesión</h2>
        {error && <p style={{color: 'red'}}>{error}</p>}
        <form onSubmit={handleSubmit}>
            <div>
                <label>Usuario:</label>
                <input type="text" value={email} onChange={(e) => setEmail(e.target.value)} style={{ width: '100%', marginBottom: '10px' }}></input>
            </div>
            <div>
                <label>Contraseña:</label>
                <input type="text" value={password} onChange={(e) => setPassword(e.target.value)} style={{ width: '100%', marginBottom: '10px' }}></input>
            </div>
            <button type="submit" style={{ width: '100%', padding: '8px'}}>Ingresar</button>
        </form>
    </div>
);
 
};
