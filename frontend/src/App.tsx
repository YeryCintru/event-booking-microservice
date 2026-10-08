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
    <div className="app-shell" id="top">
      <header className="site-header">
        <div className="site-nav">
          <a className="site-brand" href="#top" aria-label="EventBooking, inicio">
            <span className="site-brand-mark" aria-hidden="true">E</span>
            <span>EventBooking</span>
          </a>
          <nav className="site-nav-links" aria-label="Navegación principal">
            <a className="site-nav-link" href={token ? "#events" : "#login"}>
              {token ? "Eventos" : "Iniciar sesión"}
            </a>
          </nav>
          {token && (
            <button className="logout-button" onClick={handleLogout}>
              Cerrar sesión
            </button>
          )}
        </div>
      </header>

      <main className="app-main">
        {!token ? (
          <Login onLoginSuccess={(newToken) => setToken(newToken)} />
        ) : (
          <EventList />
        )}
      </main>
      <footer className="site-footer">
        Yeray Navascués Trincado <br />
        Sistema de gestión de eventos
      </footer>
    </div>
  )
};

export default App;