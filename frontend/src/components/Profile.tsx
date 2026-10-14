

export const Profile = () => {
    const storedUser = localStorage.getItem('user');
    const user = storedUser ? JSON.parse(storedUser) : null;
    

    return (
        <div>
        <h2 style={{fontSize:'27px'}}>Perfil</h2>
        <p>Consulta tu información principal</p>
        <hr />
        <div style={{display:'flex', justifyContent:'center'}}>
            <div style={{border: 'solid 1px #ddd', borderRadius:'8px', flex:1, padding:'20px',maxWidth:'700px' }}>
                <h3>Información principal</h3>  
                <hr />
                <p><strong>Id del usuario: </strong>{user.id}</p>
                <p> <strong> Email: </strong>{user.email}</p>
                <p> <strong>Rol:</strong> {user.rol}</p>
            </div>
        </div>
        </div>
    )

};