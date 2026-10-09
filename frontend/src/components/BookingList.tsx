import React, { useEffect, useState } from 'react';
import { eventApi, bookingApi } from '../api/axiosConfig';
import type { Event, Booking } from '../types';

export const BookingList: React.FC = () => {
    const [bookings, setBookings] = useState<Booking[]>([]);
    const [quantities, setQuantities] = useState<{ [key: number]: number }>({});
    const [message, setMessage] = useState('');

    const fetchBookings = async () => {
        try{
            const response = await bookingApi.get<Booking[]>(`/user/1`);
            setBookings(response.data);
        } catch (err) {
            setMessage('Error al cargar la lista de eventos');
        }
    };

    useEffect(() => {
        fetchBookings();
    }, []);

 

    return(
        <div id="events" style={{ padding: '20px', gap:'200px', margin: '0 auto', display:'flex', justifyContent:'center'}}>
            <div style={{flex: 0.5}}>
            <h2>Reservas</h2>
            {message && <p style={{padding: '10px', background: '#e0f7fa', borderRadius: '4px'}}>{message}</p>}
            <div style={{display: 'grid', gap: '15px'}}>
                {bookings.map((book) => (
                    <div key={book.id} style={{ border: '1px solid #ddd', padding: '15px', borderRadius: '8px', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                        <div>
                            <h5>{book.id}</h5>                           
                        </div>
                        <div>
                             <p>Id del evento: {book.eventId}</p>
                             <p>Fecha de creación: { new Date(book.createdAt).toDateString()}</p>
                        </div>
                        <div>
                            <p>Número de tickets: {book.ticketsCount}</p>
                        </div>
                        <div>
                            <button>Ver evento</button>
                        </div>
                    </div>

                ))}
                <div></div>
            </div>
            </div>
            
        </div>
    );
};

