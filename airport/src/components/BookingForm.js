import React, { useState, useEffect } from 'react';
import axios from 'axios'
import { useContext } from 'react';
import { AuthContext } from './AuthComponent';
import { useNavigate } from 'react-router-dom';

const BookingForm = () => {
    const [coords, setCoords] = useState([]);
    const [tta, setTta] = useState([]);

    const [city, setCity] = useState([]);
    const [airline, setAirline] = useState([]);
    const [number, setNumber] = useState([]);
    const [code, setCode] = useState([]);
    const [plane, setPlane] = useState([]);
    const [logo, setLogo] = useState([]);

    var yu = "pakd";
    const [showBookingForm, setBookingForm] = useState(true);
    const [showSearch, setSearch] = useState(false);
    const navigate = useNavigate();
    const { user, setUser } = useContext(AuthContext)

    const makeRequest = (destination, amount, date) => {
        const params = {
            destination: destination,
            amount: amount,
            date: date
        };
        console.log(params);
        return new Promise((resolve, reject) => {
            axios.get('http://localhost:8080/api/airport/bookingSearch', { params })
                .then(response => {
                    setTta(response.data[0]);
                    setAirline(response.data[0].airline.name)
                    setCode(response.data[0].airline.code)
                    setNumber(response.data[0].number)
                    setCity(response.data[0].destination.name)
                    setPlane(response.data[0].airplane)
                    setLogo(response.data[0].airline.logo)
                    resolve(response.data);
                })
                .catch(error => {
                    reject(error);
                });
        });
    };

    const makeBookingRequest = (email, airline, number, date) => {
        const params = {
            email: email,
            airline: airline,
            number: number,
            date: date,
        };
        console.log(params);
        return new Promise((resolve, reject) => {
            axios.get('http://localhost:8080/api/airport/addbooking', { params })
                .then(response => {
                })
                .catch(error => {
                    reject(error);
                });
        });
    };

    const checkState = () => {
        console.log(coords);
        console.log(tta);
    }

    useEffect(() => {
        checkState();
    }, []);

    const [bookingData, setBookingData] = useState({
        destination: '',
        amount: '',
        date: '',
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
            makeRequest(bookingData.destination, bookingData.amount, bookingData.date).then(data => {
                console.log("Request successful:", data);
                setCoords(data);
                console.log(coords);
                console.log(tta);
                if (!data[1]) {
                    setErrors({
                        destination: 'No matches found',
                    });
                };
                if (data[1]) {
                    setBookingForm(false);
                    setSearch(true);
                }
            })
                .catch(error => {
                    console.error("Request failed:", error);
                });
        }
    };

    const handleBookSubmit = (event) => {
        event.preventDefault();
        if (user) {
            makeBookingRequest(user.email, code, number, bookingData.date).then(data => {
            });
        }
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
                    {(
                        <div className="inp_f">
                            {city}<br />
                            {airline}<br />
                            {plane}<br />
                            {code}{number}<br />
                            <img className="srcimg" src={logo}></img><br />
                            <button className="src-button" type="submit" onClick={handleBookSubmit}>
                                Book
                            </button>
                        </div>
                    )}
                </div>
            </div>
        );
    };
};

export default BookingForm;
