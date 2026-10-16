import { useState} from "react";
import { bookingApi } from "../api/axiosConfig";
import { CheckoutForm } from "./CheckoutForm";
import { Elements } from "@stripe/react-stripe-js";
import { loadStripe } from "@stripe/stripe-js";


interface EventModalProps {
    event: any;
    closeModal: () => void;
}

const stripePromise = loadStripe(import.meta.env.VITE_STRIPE_PUBLIC_KEY);

export const EventModal = ({event, closeModal}: EventModalProps) => {
    
    const [quantities, setQuantities] = useState<{ [key: number]: number }>({});
    const [message, setMessage] = useState('');
    const storedUser = localStorage.getItem('user');
    const user = storedUser ? JSON.parse(storedUser) : null;
    const [clientSecret,setclientSecret] = useState<string>('');

     const handleBooking = async (eventId: number) => {

        const quantity = quantities[eventId] || 1;
        try{
            await bookingApi.post('',{
                userId: user.id,
                eventId,
                ticketsCount: quantity,
            });
            setMessage(`¡Reserva de ${quantity} entrada(s) confirmada!`);
        } catch(err: any) {
            setMessage(err.response?.data?.message || 'Error al procesar la reserva');
        }
    };

    const handleCompra = async () => {
        
        try{
            const response = await bookingApi.post('create-payment-intent', {
                amount:5,
            });
            setMessage('Procesando pago');
            setclientSecret(response.data.clientSecret);
        } catch (err:any) {
            setMessage('Error rocesando el pago');
        }

    }


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
            <button onClick={() => handleBooking(event.id)} disabled={event.availableCapacity <= 0}>
                {event.availableCapacity > 0 ? 'Reservar' : 'Agotado'}
            </button>
            <button onClick={() => handleCompra()} disabled={event.availableCapacity <= 0}>
                Ir a pagar
            </button>
        </div>   
        {clientSecret && (
            <Elements stripe={stripePromise} options={{clientSecret}}>
            <CheckoutForm clientSecret={clientSecret}/>
            </Elements>
        )}

        {message && (
            <p>{message}</p>
        )}                
    </div>
</div>

    );
};