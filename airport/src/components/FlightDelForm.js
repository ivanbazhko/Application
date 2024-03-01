import React, { useState } from 'react';
import axios from 'axios'
import { useContext } from 'react';
import { AuthContext } from './AuthComponent';
import { useNavigate } from 'react-router-dom';

const makeRequest = (airline, number) => {
    const params = {
        airline: airline,
        number: number,
    };
    return new Promise((resolve, reject) => {
        axios.get('http://localhost:8080/api/airport/deleteflight', { params })
            .then(response => {
                resolve(response.data);
            })
            .catch(error => {
                reject(error);
            });
    });
};

const FlightDelForm = () => {
    const navigate = useNavigate();
    const { user, setUser } = useContext(AuthContext)

    const [flightData, setFlightData] = useState({
        airline: '',
        number: '',
    });

    const [errors, setErrors] = useState({
        airline: '',
        number: '',
    });

    const handleChange = (event) => {
        const { name, value } = event.target;
        setFlightData({ ...flightData, [name]: value });
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        const isValidNumber = (number) => {
            if (!number) return true;
            if(number <= 999) return true;
            if(number >= 10000) return true;
            const amountError = !/^\d+$/.test(flightData.number);
            if(amountError) return true;
            return false;
        };

        const airlineError = flightData.airline.length == 0 ? true : false;
        const numberError = isValidNumber(flightData.number);

        setErrors({
            airline: airlineError ? 'Please enter a valid airline' : '',
            number: numberError ? 'Please enter a valid number' : '',
        });

        if (!airlineError && !numberError) {
            console.log('Deleting flight:', flightData);
            makeRequest(flightData.airline, flightData.number)
                .then(data => {
                    console.log("Request successful:", data);
                    if (data[0]) {
                        setErrors({
                            number: 'Flight does not exist',
                        });
                    } else {
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
                <h2 className='form-title'>Delete A Flight</h2>
            </div>

            <form className="form-body">

                <div className='form-field'>
                    <label className="form-label" htmlFor="code">Airline:</label>
                    <input type="text" id="airline" name="airline" className="login-input" value={flightData.airline}
                        onChange={handleChange} />
                    <span className="error-message">{errors.airline}</span>
                </div>

                <div className='form-field'>
                    <label className="form-label" htmlFor="latitude">Number:</label>
                    <input type="number" id="number" name="number" className="login-input" value={flightData.number}
                        onChange={handleChange} />
                    <span className="error-message">{errors.number}</span>
                </div>

                <button className="airp-button" type="submit" onClick={handleSubmit}>
                    Delete Flight
                </button>
            </form>
        </div>
    );
};

export default FlightDelForm;
