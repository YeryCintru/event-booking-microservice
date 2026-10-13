import React from "react";

export const Profile = () => {
    const storedUser = localStorage.getItem('user');
    const user = storedUser ? JSON.parse(storedUser) : null;
    

    return (
        <div style={{display:'flex', justifyContent:'center'}}>
            <div style={{border: 'solid 1px #ddd', borderRadius:'8px', flex:1, padding:'20px',maxWidth:'700px' }}>
                <h3>Información del usuario</h3>
                <p><strong>Id del usuario: </strong>{user.id}</p>
                <p> <strong> Email: </strong>{user.email}</p>
                <p> <strong>Rol:</strong> {user.rol}</p>
            </div>
        </div>
    )

};