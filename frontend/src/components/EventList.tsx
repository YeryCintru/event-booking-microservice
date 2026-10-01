import React, { useEffect, useState } from 'react';
import { eventApi, bookingApi } from '../api/axiosConfig';
import type { Event } from '../types';

export const EventList: React.FC = () => {
    const [events, setEvents] = useState<Event[]>([]);
    const [quantities, setQuantities] = useState<{ [key: number]: number }>({});
    const [message, setMessage] = useState('');

    const fetchEvents = async () => {
        try{
            const response = await eventApi.get<Event[]>('');
            setEvents(response.data);
        } catch (err) {
            setMessage('Error al cargar la lista de eventos');
        }
    };

    useEffect(() => {
        fetchEvents();
    }, []);

    const handleBooking = async (eventId: number) => {
        const quantity = quantities[eventId] || 1;
        try{
            await bookingApi.post('',{
                userId: 1,
                eventId,
                ticketsCount: quantity,
            });
            setMessage(`¡Reserva de ${quantity} entrada(s) confirmada!`);
            fetchEvents(); //Recargar aforo en tiempo real
        } catch(err: any) {
            setMessage(err.response?.data?.message || 'Error al procesar la reserva');
        }
    };

    return(
        <div style={{ padding: '20px', maxWidth: '800px', margin: '0 auto'}}>
            <h2>Catálogo de eventos</h2>
            {message && <p style={{padding: '10px', background: '#e0f7fa', borderRadius: '4px'}}>{message}</p>}
            <div style={{display: 'grid', gap: '15px'}}>
                {events.map((evt) => (
                    <div key={evt.id} style={{ border: '1px solid #ddd', padding: '15px', borderRadius: '8px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                        <div>
                            <h3>{evt.title}</h3>
                            <p>Aforo Disponible: <strong>{evt.availableCapacity} / {evt.totalCapacity}</strong></p>                            
                        </div>
                        <div>
                            <input type="number" min="1" max={evt.availableCapacity} value={quantities[evt.id] || 1}
                                onChange={(e) => setQuantities ({... quantities, [evt.id]: parseInt(e.target.value) || 1})}
                                style={{ width: '50px', marginRight: '10px' }}
                             />
                             <button onClick={() => handleBooking(evt.id)} disabled={evt.availableCapacity <= 0}>
                                {evt.availableCapacity > 0 ? 'Reservar' : 'Agotado'}
                            </button>
                        </div>
                    </div>

                ))}
                <div></div>
            </div>
        </div>
    );
};


