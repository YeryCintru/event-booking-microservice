import axios from 'axios';

export const authApi = axios.create({
    baseURL: `${import.meta.env.VITE_API_URL}/api/auth`
});

export const eventApi = axios.create({
    baseURL: `${import.meta.env.VITE_API_URL}/api/events`
});

export const bookingApi = axios.create({
    baseURL: `${import.meta.env.VITE_API_URL}/api/bookings`
});

// Interceptor para inyectar el token JWT en las reservas
bookingApi.interceptors.request.use((config) => {
    const token = localStorage.getItem('jwt_token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
})
