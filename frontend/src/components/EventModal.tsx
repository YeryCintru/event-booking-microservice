import { useState} from "react";
import { useNavigate } from "react-router-dom";



interface EventModalProps {
    event: any;
    closeModal: () => void;
}

export const EventModal = ({event, closeModal}: EventModalProps) => {
    
    const [quantities, setQuantities] = useState<{ [key: number]: number }>({});
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    setMessage('');

    return (
    <div key={event.id} style={{ 
    position: 'relative', // 1. IMPORTANTE: Esto sirve de "ancla" para el botón absoluto 
    padding: '10px', 
    borderRadius: '8px', 
    backgroundColor: '#fff' 
    }}>
            
    {/* Botón de cerrar arriba a la derecha */}
    <button style={{ 
        position: 'absolute', 
        top: '15px', 
        right: '15px',
        background: 'none',
        border: 'none',
        fontSize: '20px',
        cursor: 'pointer',
        fontWeight: 'bold',
        color: '#000'
    }}
    onClick={closeModal}>
        ✕
    </button>

    {/* Contenido de la tarjeta */}
    <div>
        <h3>{event.title}</h3>
        <p>Aforo Disponible: <strong>{event.availableCapacity} / {event.totalCapacity}</strong></p>
        <p>{event.description}</p>      
        <p>Fecha de publicación: {new Date(event.dateTime).toDateString()}</p>  
        <br />
        <div>
            <input type="number" min="1" max={event.availableCapacity} value={quantities[event.id] || 1}
                onChange={(e) => setQuantities ({... quantities, [event.id]: parseInt(e.target.value) || 1})}
                style={{ width: '50px', marginRight: '10px' }}
            />
            <button onClick={() => navigate('/checkout', {
                state: {
                    event: event,
                    ticketAmount: quantities[event.id]
                }
            })} 
            disabled={event.availableCapacity <= 0}>
                Ir a pagar
            </button>
        </div>   
        

        {message && (
            <p>{message}</p>
        )}                
    </div>
</div>

    );
};