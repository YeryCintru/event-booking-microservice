import {useState} from "react";


interface EventModalProps {
    event: any;
    closeModal: () => void;
}

export const EventModalBooking = ({event, closeModal}: EventModalProps) => {

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
        <p>{event.description}</p>      
        <p>Fecha de publicación: {new Date(event.dateTime).toDateString()}</p>  
        <br />                 
    </div>
</div>

    );
};