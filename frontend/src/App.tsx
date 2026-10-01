import { useState } from "react";
import { EventList } from "./components/EventList";
import { Login} from "./components/Login";

export function App() {
  const [token, setToken] = useState<string | null>(localStorage.getItem('jwt_token'));

  const handleLogout = () => {
    localStorage.removeItem('jwt_token');
    setToken(null);
  };

  return (
    <div className="App">
      <header style={{ padding: '10px 20px', background: '#333', color: '#fff', display: 'flex', justifyContent: 'space-between', alignItems: 'center'}}>
       <h1>Sistema de Reservas de eventos</h1> 
       {token && <button onClick={handleLogout} style={{ padding: '5px 10px'}}>Cerrar sesión</button>}
      </header>

      {!token ? (
        <Login onLoginSuccess={(newToken) => setToken(newToken)} />
      ) : (
        <EventList />
      )}
    </div>
  )
};

export default App;