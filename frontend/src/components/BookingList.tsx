import React, { useEffect, useState } from 'react';
import { eventApi, bookingApi } from '../api/axiosConfig';
import type { Event, Booking } from '../types';
import { EventModalBooking } from './EventModalbooking';


export const BookingList: React.FC = () => {
    const [bookings, setBookings] = useState<Booking[]>([]);
    const [selectedEvent, setSelectedEvent] = useState<any>(null);
    const [message, setMessage] = useState('');
    const storedUser = localStorage.getItem('user');
    const user = storedUser ? JSON.parse(storedUser) : null;

    const fetchBookings = async () => {
        try{
            const response = await bookingApi.get<Booking[]>(`/user/${user.id}`);
            setBookings(response.data);
        } catch (err) {
            setMessage('Error al cargar la lista de eventos');
        }
    };

    const fetchEvent = async (booking:any) => {
        try{
            const event = await eventApi.get<Event>(`/${booking.eventId}`);
            setSelectedEvent(event.data);
        } catch (err){
            setMessage('No se ha podido recuperar la información del evento');
        }
    }
    
    const handleTicket = async (booking:any) => {
        try{
            const response = await bookingApi.get<any>(`/${booking.id}/ticket`, {
                responseType: 'blob'
            });
            const blob = new Blob([response.data], {type: 'application/pdf'});
            const url = window.URL.createObjectURL(blob);

            const a = document.createElement('a');
            a.href = url;
            a.download = `ticket-${booking.id}.pdf`;
            document.body.appendChild(a);
            a.click();

            a.remove();
            window.URL.revokeObjectURL(url);

        } catch (err){
            setMessage('No se ha podido descargar la entrada');
        } 
    }

    

    useEffect(() => {
        fetchBookings();
    }, []);

 

    return(
        <div>
         <h2 style={{fontSize:'27px'}}>Reservas</h2>
          <p style={{fontSize:'20px', color:'rgb(90, 132, 132)'}}>Consulta tus reservas</p>


         <hr />
        <br />            
        <div id="events" style={{ padding: '20px', gap:'200px', margin: '0 auto', display:'flex', justifyContent:'center'}}>
            <div style={{flex: 0.5}}>
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
                        <div style={{display:'flex',flexDirection:'column', gap:'5px'}}>
                            <button onClick={() => fetchEvent(book)}>
                                Ver evento</button>
                                
                            <button onClick={() => handleTicket(book)}>
                                Descargar entrada</button>

                        </div>
                    </div>

                ))}
                 <div>
                    {selectedEvent && (
                            <div className='modal-overlay'>
                                <div className='modal-content'>
                                    <EventModalBooking 
                                    event={selectedEvent}
                                    closeModal={() => setSelectedEvent(null)}
                                    />
                                </div>
                            </div>
                                )}
                </div>
            </div>
            </div>
            
        </div>
        </div>
    );
};

