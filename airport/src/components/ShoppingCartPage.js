import React, { useEffect, useState, useContext } from 'react'
import axios from 'axios'
import PaymentForm from './PaymentForm'
import { ShoppingCartContext } from './ShoppingCartContext';
import { AuthContext } from './AuthComponent';
import { useNavigate } from 'react-router-dom';

export default function ShoppingCartPage() {
    const { cartItems, addToCart, removeFromCart, getCartItems } = useContext(ShoppingCartContext);
    const [showBookingForm, setShowBookingForm] = useState(false);
    const [success, setSuccess] = useState(false);
    const { user, setUser } = useContext(AuthContext);
    const [transactionError, setTransactionError] = useState(null);
    const navigate = useNavigate();

    const handleBookSubmit = (object, event) => {
        event.preventDefault();
        console.log(object);
        removeFromCart(object);
    };

    const calculateTotal = () => {
        let total = 0;
        cartItems.forEach(element => {
            total += element.price;
        });
        return total;
    }

    const handleBookClick = () => {
        const total = calculateTotal();
        if (total > 0) {
            setShowBookingForm(true);
        }
    };

    const handleBookingConfirm = (cardNumber, cardCode) => {
        const total = calculateTotal();

        makeTransactionRequest(cardNumber, cardCode, total)
            .then((response) => {
                if (response.data == 1) {
                    const email = user.email;

                    Promise.all(
                        cartItems.map((item) => {
                            makeBookingRequest(
                                email,
                                item.number,
                                item.date,
                                item.time,
                                item.price,
                                item.code,
                            );
                            removeFromCart(item.number)
                        })
                    )
                        .then(() => {
                            setSuccess(true);
                            setShowBookingForm(false);
                            navigate("/");
                        })
                        .catch((error) => {
                            console.error("Error making booking requests:", error);
                            setTransactionError("There was an error processing your booking. Please try again later.");
                            setShowBookingForm(false);
                        });
                } else {
                    setTransactionError("There was an error processing your payment. Please check your card details and try again.");
                    setShowBookingForm(false);
                }
            })
            .catch((error) => {
                console.error("Error making transaction request:", error);
                setTransactionError("There was an error processing your payment. Please try again later.");
                setShowBookingForm(false);
            });
    };

    const handleBookingCancel = () => {
        setShowBookingForm(false);
    };

    const makeBookingRequest = (email, number, date, time, price, route) => {
        const params = {
            email: email,
            number: number,
            date: date,
            time: time,
            price: price,
            route: route,
        };
        console.log(params);
        return new Promise((resolve, reject) => {
            axios.get('http://localhost:8080/api/airport/addbooking', { params })
                .then(response => {
                    resolve(response);
                })
                .catch(error => {
                    reject(error);
                });
        });
    };

    const makeTransactionRequest = (cardnum, code, amount) => {
        const params = {
            cardnum: cardnum,
            code: code,
            amount: amount,
            airpacc: "AAAABBBBCCCCDDDD"
        };
        console.log(params);
        return new Promise((resolve, reject) => {
            axios.get('http://localhost:8080/api/airport/transaction', { params })
                .then(response => {
                    resolve(response);
                })
                .catch(error => {
                    reject(error);
                });
        });
    };

    return (
        <div className="cartwrapper">
            {!showBookingForm && <div className="cartitem">
                <br /><br /><br />{"Total: "}{calculateTotal()}<br />
                {user && <button className="src-button" type="submit" onClick={handleBookClick}>
                    Book
                </button>}
                {!user && <p>Please Log In</p>}
                {transactionError && <div className="error">{transactionError}</div>}
            </div>}
            {!showBookingForm && cartItems.map((result) =>
                <div className="cartitem" key={result.number}>
                    {result.destination}{' ('}
                    {result.code}{')'}<br />
                    {result.airline}{' ('}{result.number}{')'}<br />
                    {result.airplane}<br />
                    {'Departure: '}{result.date}{", "}{result.time}<br />
                    {'Flight time: '}{result.flighttime}<br />
                    {result.price}{'$'}<br />
                    <img className="srcimg" src={result.logo}></img><br />
                    <button className="src-button" type="submit" onClick={(e) => handleBookSubmit(result.number, e)}>
                        Remove
                    </button>
                </div>
            )}
            {showBookingForm && (
                <PaymentForm
                    onConfirm={handleBookingConfirm}
                    onCancel={handleBookingCancel}
                    sum={calculateTotal()}
                />
            )}
        </div>
    )
}