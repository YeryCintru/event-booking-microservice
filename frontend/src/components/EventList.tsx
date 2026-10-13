import React, { useEffect, useState } from 'react';
import { eventApi, bookingApi } from '../api/axiosConfig';
import type { Event } from '../types';
import { Link, Router, useNavigate } from 'react-router-dom';
import { EventModal } from './EventModal';

export const EventList: React.FC = () => {
    const [events, setEvents] = useState<Event[]>([]);
    const [message, setMessage] = useState('');
    const [selectedEvent, setSelectedEvent] = useState<any>(null);
    const navigate = useNavigate();

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

   

    return(
        <div id="events" style={{ padding: '20px', gap:'200px', margin: '0 auto', display:'flex', justifyContent:'center'}}>
            <div style={{flex: 0.6}}>
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
                                <button onClick={() => setSelectedEvent(evt)} style={{width:'80px', height:'30px',fontSize:'14px'}}>
                                    Ver más
                                </button>
                                
                            </div>
                            
                        </div>

                    ))}
                </div>
            </div>
            <div>
                {selectedEvent && (
                        <div className='modal-overlay'>
                            <div className='modal-content'>
                                <EventModal 
                                event={selectedEvent}
                                closeModal={() => setSelectedEvent(null)}
                                />
                            </div>
                        </div>
                            )}
            </div>
            <div style={{display:'flex', flexDirection:'column'}}>
                <h2>Gestión de eventos</h2>
                <div style={{border:'1px solid #ddd',padding: '15px', borderRadius: '8px', display: 'flex',flexDirection:'column', justifyContent: 'space-between', alignItems: 'center'}}>
                    <button onClick={() => navigate('/bookings')} style={{width:'300px', height:'50px',fontSize:'18px'}}>Ver mis reservas</button>

                </div>
            </div>
        </div>
    );
};

