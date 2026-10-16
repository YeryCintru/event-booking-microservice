import React, { useState} from 'react';
import { useStripe, CardElement, useElements } from '@stripe/react-stripe-js';
import { __unstable__loadDesignSystem } from 'tailwindcss';

interface CheckoutProps {
    clientSecret: string;
    onPaymentSuccess: (paymentId:string) => void;
}

export const CheckoutForm = ({clientSecret, onPaymentSuccess}: CheckoutProps) => {
    const stripe = useStripe();
    const elements = useElements();
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    
   

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!stripe || !elements) return;

        setLoading(true);
        setError('');

        const cardElement = elements.getElement(CardElement);

        if (!cardElement) {
            setError('No se ha cargado correctamente el formulario');
            return;
        }

        const result = await stripe.confirmCardPayment (clientSecret, {
            payment_method: {
                card: cardElement,
            },
        });

        if (result.error) {
            setError(result.error.message || 'Error en el proceso de pago');
            setLoading(false);
        } else if (result.paymentIntent.status === 'succeeded') {
            setLoading(false);
            onPaymentSuccess(result.paymentIntent.id);
        }
    }

    
         

    return(
        /*<div className='modal-overlay'>       
            <div className='modal-content'>*/
                <form onSubmit={handleSubmit} >
                    <h3>Detalles de Pago</h3>
                    <hr />
                    <div>
                        <p>Pago con tarjeta</p>
                        <br />
                        <CardElement />
                    </div>
                    <br /><br />
                    {error && <p>{error}</p>}
                    <button type='submit' disabled={!stripe || loading}style={{width:'300px'}}>
                    {loading ? 'Procesando Pago...' : 'Pagar'}
                    </button>
              </form>
            /*</div>
        </div>*/
 
    );
};


