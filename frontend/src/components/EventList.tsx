import React, { useEffect, useState } from 'react';
import { eventApi } from '../api/axiosConfig';
import type { Event } from '../types';
import { useNavigate } from 'react-router-dom';
import { EventModal } from './EventModal';

export const EventList: React.FC = () => {
    const [events, setEvents] = useState<Event[]>([]);
    const [message, setMessage] = useState('');
    const [selectedEvent, setSelectedEvent] = useState<any>(null);
    const storedUser = localStorage.getItem('user');
    const user = storedUser ? JSON.parse(storedUser) : null;

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
    }, [selectedEvent]);

   

    return(
        <div>

        <h1 style={{fontSize:'30px'}}>Bienvenido, <span style={{color:'rgb(57, 85, 85)'}}> {user.email}</span></h1>
        <p style={{fontSize:'20px', color:'rgb(90, 132, 132)'}}>¡Consulta los eventos disponibles!</p>

        <hr />
        <br />
        <div id="events" style={{ padding: '30px', margin: '0 auto', display:'flex', justifyContent:'center', background:'rgb(225, 236, 236)', borderRadius:'16px', alignItems:'center'}}>
            <div style={{flex: 0.6}}>
            <h2 style={{fontSize:'27px'}}>Catálogo de eventos</h2>
            <br />
            {message && <p style={{padding: '10px', background: '#e0f7fa', borderRadius: '4px'}}>{message}</p>}
                <div style={{display: 'grid', gap: '15px'}}>
                    
                    {events.map((evt) => (
                        <div key={evt.id} style={{ border: '1px solid #ddd', padding: '15px', borderRadius: '16px', display: 'flex', justifyContent: 'center', alignItems: 'center', background:'#fff' }}>
                            <div>
                                <h3>{evt.title}</h3>
                                <p>Aforo Disponible: <strong>{evt.availableCapacity} / {evt.totalCapacity}</strong></p>    
                                <br />                        
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
        </div>
    </div>
    );
};

