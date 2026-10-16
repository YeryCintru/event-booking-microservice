import React, {useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { bookingApi } from "../api/axiosConfig";
import { loadStripe } from "@stripe/stripe-js";
import { Elements } from "@stripe/react-stripe-js";
import { CheckoutForm } from "./CheckoutForm";

const stripePromise = loadStripe(import.meta.env.VITE_STRIPE_PUBLIC_KEY);

export const CheckoutPage = () => {
    const location = useLocation();
    const quantity = location.state?.ticketAmount || 1;
    const price = 5;
    const event = location.state?.event || null;
    const [message, setMessage] = useState('');
    const storedUser = localStorage.getItem('user');
    const user = storedUser ? JSON.parse(storedUser) : null;
    const [clientSecret,setclientSecret] = useState<string>('');
    const [paymentId, setPaymentId] = useState('');

    const handleBooking = async (eventId: number, paymentId:string) => {
    
            try{
                await bookingApi.post('',{
                    userId: user.id,
                    eventId,
                    ticketsCount: quantity,
                });
                setMessage(`¡Reserva de ${quantity} entrada(s) confirmada!`);
                setPaymentId(paymentId);
                setclientSecret('');
            } catch(err: any) {
                setMessage(err.response?.data?.message || 'Error al procesar la reserva');
            }
    };
    
    useEffect(() => {
        const initCheckout = async () => {
            try{
            const response = await bookingApi.post('create-payment-intent', {
                amount:quantity*price+1.25,
            });
            setMessage('Procesando pago');
            setclientSecret(response.data.clientSecret);
            } catch (err:any) {
                setMessage('Error rocesando el pago');
            }
        }
        initCheckout();
    },[]);

    return(
        <div>
            <div style={{display:'flex', justifyContent:'center', alignItems:'center', gap:'100px',padding:'20px'}}>
                
                {clientSecret && (
                <div style={{flex:0.5, border:'solid 1px #ddd', borderRadius:'8px',padding:'10px'}}>
                <Elements stripe={stripePromise} options={{clientSecret}}>
                <CheckoutForm clientSecret={clientSecret} onPaymentSuccess={(paymentId) => handleBooking(event.id,paymentId)}/>
                </Elements>
                </div>
                )}
                
                <div style={{flex:0.5, border:'solid 1px #ddd', borderRadius:'8px',padding:'10px'}}>
                    <h3>Resumen de la compra</h3>
                    <hr />
                    <h4>Productos</h4>
                    <p>{event.title} ({price}€) x {quantity} | {quantity*price}€</p>
                    
                    <h4>Total</h4>
                    <p>Productos: {quantity*price}€</p>
                    <p>Trámite de gestión: 1.25€</p>
                    <h4>Importe total: {quantity*price + 1.25}€</h4>
                </div>
                
            </div>
            <h4>Estado</h4>
            <p>{message}</p>
            <p>{paymentId}</p>
        </div>
    );
};