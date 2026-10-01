import axios from 'axios';

export const authApi = axios.create({
    baseURL: 'http://localhost:8081/api/auth'
});

export const eventApi = axios.create({
    baseURL: 'http://localhost:8082/api/events'
});

export const bookingApi = axios.create({
    baseURL: 'http://localhost:8083/api/bookings'
});


// Interceptor para inyectar el token JWT en las reservas
bookingApi.interceptors.request.use((config) => {
    const token = localStorage.getItem('jwt_token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
})
