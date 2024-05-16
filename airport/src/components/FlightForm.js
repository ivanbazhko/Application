import React, { useState } from 'react';
import axios from 'axios'
import { useContext } from 'react';
import { AuthContext } from './AuthComponent';
import { useNavigate } from 'react-router-dom';

const makeRequest = (destination, airline, airplane, days, time, number, origin, price) => {
    const params = {
        destination: destination,
        airline: airline,
        airplane: airplane,
        days: days,
        time: time,
        number: number,
        origin: origin,
        price: price,
    };
    return new Promise((resolve, reject) => {
        console.log("PARAMS: ", params);
        axios.get('http://localhost:8080/api/airport/addflight', { params })
            .then(response => {
                resolve(response.data);
            })
            .catch(error => {
                reject(error);
            });
    });
};

const FlightForm = () => {
    const navigate = useNavigate();
    const { user, setUser } = useContext(AuthContext)

    const [flightData, setFlightData] = useState({
        destination: '',
        airline: '',
        airplane: '',
        days: '',
        time: '',
        number: '',
        origin: '',
        price: '',
    });

    const [errors, setErrors] = useState({
        destination: '',
        airline: '',
        airplane: '',
        days: '',
        time: '',
        number: '',
        price: '',
    });

    const handleChange = (event) => {

        const { name, value } = event.target;
        setFlightData({ ...flightData, [name]: value });

    };

    const handleSubmit = (event) => {
        event.preventDefault();

        console.log(flightData);

        const isValidNumber = (number) => {
            if (!number) return true;
            if(number <= 999) return true;
            if(number >= 9999) return true;
            const amountError = !/^\d+$/.test(flightData.number);
            if(amountError) return true;
            return false;
        };

        const destinationError = flightData.destination.length == 0 ? true : false;
        const airlineError = flightData.airline.length == 0 ? true : false;
        const airplaneError = flightData.airplane.length == 0 ? true : false;
        const daysError = (flightData.days.length > 7 || flightData.days.length <= 0) ? true : false;
        const timeError = flightData.time.length == 0 ? true : false;
        const numberError = isValidNumber(flightData.number);
        const priceError = flightData.price <= 0 ? true : false;

        setErrors({
            destination: destinationError ? 'Please enter a valid destination' : '',
            airline: airlineError ? 'Please enter a valid airline' : '',
            airplane: airplaneError ? 'Please enter a valid airplane' : '',
            days: daysError ? 'Please enter the days' : '',
            time: timeError ? 'Please enter the time' : '',
            number: numberError ? 'Please enter a valid number' : '',
            price: priceError ? 'Please enter a valid price' : '',
        });

        if (!destinationError && !airlineError && !airplaneError && !numberError && !daysError && !timeError && !priceError) {
            console.log('Adding flight:', flightData);
            var checkedorigin;
            var checkbox = document.getElementsByName("origin")[0];
            if (checkbox.checked) {
                checkedorigin = -1;
            } else {
                checkedorigin = 1;
            }
            makeRequest(flightData.destination, flightData.airline, flightData.airplane,
                flightData.days, flightData.time, flightData.number, checkedorigin, flightData.price)
                .then(data => {
                    console.log("Request successful:", data);
                    if (!data[0]) {
                        setErrors({
                            destination: 'Destination does not exist',
                        });
                    };
                    if (!data[1]) {
                        setErrors({
                            airline: 'Airline does not exist',
                        });
                    };
                    if (!data[2]) {
                        setErrors({
                            number: 'Flight already exists',
                        });
                    };
                    if(data[0] && data[1] && data[2]) {
                        navigate("/");
                    }
                })
                .catch(error => {
                    console.error("Request failed:", error);
                });
        };
    };

    return (
        <div className="flight-form">
            <div className='form-header'>
                <h2 className='form-title'>Add A Flight</h2>
            </div>

            <form className="form-body">

                <div className='form-field'>
                    <label className="form-label" htmlFor="code">Destination:</label>
                    <input type="text" id="destination" name="destination" className="login-input" value={flightData.destination}
                        onChange={handleChange} />
                    <span className="error-message">{errors.destination}</span>
                </div>

                <div className='form-field'>
                    <label className="form-label" htmlFor="code">Airline:</label>
                    <input type="text" id="airline" name="airline" className="login-input" value={flightData.airline}
                        onChange={handleChange} />
                    <span className="error-message">{errors.airline}</span>
                </div>

                <div className='form-field'>
                    <label className="form-label" htmlFor="code">Airplane:</label>
                    <input type="text" id="airplane" name="airplane" className="login-input" value={flightData.airplane}
                        onChange={handleChange} />
                    <span className="error-message">{errors.airplane}</span>
                </div>

                <div className='form-field'>
                    <label className="form-label" htmlFor="longitude">Days:</label>
                    <input type="number" id="days" name="days" className="login-input" value={flightData.days}
                        onChange={handleChange} />
                    <span className="error-message">{errors.days}</span>
                </div>

                <div className='form-field'>
                    <label className="form-label" htmlFor="longitude">Time:</label>
                    <input type="time" id="time" name="time" className="login-input" value={flightData.time}
                        onChange={handleChange} />
                    <span className="error-message">{errors.time}</span>
                </div>

                <div className='form-field'>
                    <label className="form-label" htmlFor="latitude">Number:</label>
                    <input type="number" id="number" name="number" className="login-input" value={flightData.number}
                        onChange={handleChange} />
                    <span className="error-message">{errors.number}</span>
                </div>

                <div className='form-field'>
                    <label className="form-label" htmlFor="latitude">Price:</label>
                    <input type="number" id="price" name="price" className="login-input" value={flightData.price}
                        onChange={handleChange} />
                    <span className="error-message">{errors.price}</span>
                </div>

                <div className='form-field'>
                    <label className="form-label">
                        <input type="checkbox" name="origin" value="1" onChange={handleChange} />
                        {" Other Origin"}
                    </label>
                </div>

                <button className="airp-button" type="submit" onClick={handleSubmit}>
                    Add Flight
                </button>
            </form>
        </div>
    );
};

export default FlightForm;
