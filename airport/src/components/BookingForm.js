import React, { useState, useEffect } from 'react';
import axios from 'axios'
import { useContext } from 'react';
import { AuthContext } from './AuthComponent';
import { useNavigate } from 'react-router-dom';
import { ShoppingCartContext } from './ShoppingCartContext';

const BookingForm = () => {

    const { cartItems, addToCart, removeFromCart, getCartItems } = useContext(ShoppingCartContext);

    const [coords, setCoords] = useState([]);
    const [results, setResults] = useState([]);
    const [number, setNumber] = useState([]);
    const [code, setCode] = useState([]);

    var yu = "pakd";
    const [showBookingForm, setBookingForm] = useState(true);
    const [showSearch, setSearch] = useState(false);
    const navigate = useNavigate();
    const { user, setUser } = useContext(AuthContext)

    const makeRequest = (destination, amount, date, origin) => {
        const params = {
            destination: destination,
            amount: amount,
            date: date,
            origin: origin,
        };
        console.log(params);
        return new Promise((resolve, reject) => {
            axios.get('http://localhost:8080/api/airport/bookingSearch', { params })
                .then(response => {
                    setResults(response.data);
                    console.log("got ", results)
                    resolve(response.data);
                })
                .catch(error => {
                    reject(error);
                });
        });
    };

    const checkState = () => {
        // console.log(coords);
        // console.log(tta);
    }

    useEffect(() => {
        checkState();
    }, []);

    const [bookingData, setBookingData] = useState({
        destination: '',
        amount: '',
        date: '',
        origin: '',
    });

    const [errors, setErrors] = useState({
        destination: '',
        amount: '',
        date: '',
    });

    const handleChange = (event) => {
        const { name, value } = event.target;
        setBookingData({ ...bookingData, [name]: value });
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        const destError = bookingData.destination.length == 0 ? true : false;
        const amountError = !/^\d+$/.test(bookingData.amount) || bookingData.amount <= 0;
        const dateError = isValidDate(bookingData.date);

        setErrors({
            destination: destError ? 'Please enter destination' : '',
            amount: amountError ? 'Amount must be a positive integer' : '',
            date: dateError ? 'Invalid date' : '',
        });

        if (!amountError && !dateError && !destError) {
            var checkedorigin;
            var checkbox = document.getElementsByName("origin")[0];
            if (checkbox.checked) {
                checkedorigin = -1;
            } else {
                checkedorigin = 1;
            }
            makeRequest(bookingData.destination, bookingData.amount, bookingData.date, checkedorigin).then(data => {
                console.log("Request successful:", data);
                setCoords(data);
                if (!data) {
                    setErrors({
                        destination: 'No matches found',
                    });
                } else
                    if (data.length > 0) {
                        setBookingForm(false);
                        setSearch(true);
                    } else
                        if (data.length == 0) {
                            setErrors({
                                destination: 'No flights found',
                            });
                        }
            })
                .catch(error => {
                    console.error("Request failed:", error);
                });
        }
    };

    const handleBookSubmit = (object, event) => {
        event.preventDefault();
        console.log(object);
        addToCart(object);
        setBookingForm(true);
        setSearch(false);
    };

    const isValidDate = (dateString) => {
        if (!dateString) return true;

        const today = new Date();
        const threeMonthsFromToday = new Date();
        threeMonthsFromToday.setMonth(today.getMonth() + 3);
        const yesterday = new Date();
        yesterday.setDate(today.getDate() - 1);
        const bookingDate = new Date(bookingData.date);

        if (bookingDate < yesterday) {
            return true;
        }

        if (bookingDate > threeMonthsFromToday) {
            return true;
        }
    };

    if (showBookingForm) {
        return (
            <div className="booking-form">
                <div className="form-header">
                    <h2 className="form-title">Booking</h2>
                </div>

                <form className="booking-form-body">
                    <div className="form-field">
                        <label className="form-label" htmlFor="destination">Destination:</label>
                        <input type="text" id="destination" name="destination" className='form-input' value={bookingData.destination}
                            onChange={handleChange} />
                        <span className="error-message">{errors.destination}</span>
                    </div>

                    <div className="form-field">
                        <label className="form-label" htmlFor="amount">Amount:</label>
                        <input type="number" id="amount" name="amount" className='form-input' value={bookingData.amount}
                            onChange={handleChange} />
                        <span className="error-message">{errors.amount}</span>
                    </div>

                    <div className="form-field">
                        <label className="form-label" htmlFor="date">Date:</label>
                        <input type="date" id="date" name="date" className='form-input' value={bookingData.date}
                            onChange={handleChange} lang="en-US" />
                        <span className="error-message">{errors.date}</span>
                    </div>

                    <div className='form-field'>
                        <label className="form-label">
                            <input type="checkbox" name="origin" value="1" onChange={handleChange} />
                            {" Other Origin"}
                        </label>
                    </div>

                    <button type="submit" className="search-button" onClick={handleSubmit}>
                        Search
                    </button>
                </form>
            </div>
        );
    } else {
        return (
            <div className="booking-form">
                <div className="form-header">
                    <h2 className="form-title">Results</h2>
                    <div className="reswrap">
                        {
                            results.map(result => (
                                <div className="inp_f">
                                    {result.destination}{' ('}
                                    {result.code}{')'}<br />
                                    {result.airline}{' ('}{result.number}{')'}<br />
                                    {result.airplane}<br />
                                    {'Departure: '}{result.time}<br />
                                    {'Flight time: '}{result.flighttime}<br />
                                    {result.price}{'$'}<br />
                                    <img className="srcimg" src={result.logo}></img><br />
                                    <button className="src-button" type="submit" onClick={(e) => handleBookSubmit(result, e)}>
                                        Book
                                    </button>
                                </div>
                            ))
                        }
                    </div>
                </div>
            </div>
        );
    };
};

export default BookingForm;
