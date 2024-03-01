import React, { useState } from 'react';
import axios from 'axios'
import { useContext } from 'react';
import { AuthContext } from './AuthComponent';
import { useNavigate } from 'react-router-dom';

const makeRequest = (name, code, longitude, latitude) => {
    const params = {
        name: name,
        country: code,
        coord1: longitude,
        coord2: latitude,
    };
    return new Promise((resolve, reject) => {
        axios.get('http://localhost:8080/api/airport/addairport', { params })
            .then(response => {
                resolve(response.data);
            })
            .catch(error => {
                reject(error);
            });
    });
};

const makeBookingRequest = (email, date, flight) => {
    const params = {
        email: email,
        date: date,
        flight: flight
    };
    return new Promise((resolve, reject) => {
        axios.get('http://localhost:8080/api/airport/addBooking', { params })
            .then(response => {
                resolve(response.data);
            })
            .catch(error => {
                reject(error);
            });
    });
};

const AirportForm = () => {
    const navigate = useNavigate();
    const { user, setUser } = useContext(AuthContext)

    const [airportData, setAirportData] = useState({
        name: '',
        code: '',
        longitude: '',
        latitude: '',
    });

    const [errors, setErrors] = useState({
        name: '',
        code: '',
        longitude: '',
        latitude: '',
    });

    const handleChange = (event) => {
        const { name, value } = event.target;
        setAirportData({ ...airportData, [name]: value });
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        const nameError = airportData.name.length == 0 ? true : false;
        const codeError = airportData.code.length != 3 ? true : false;
        const longitudeError = (airportData.longitude < -90 || airportData.longitude > 90 || !airportData.longitude) ? true : false;
        const latitudeError = (airportData.latitude < -180 || airportData.latitude > 180 || !airportData.latitude) ? true : false;

        setErrors({
            name: nameError ? 'Please enter a name' : '',
            code: codeError ? 'Please enter a valid code' : '',
            longitude: longitudeError ? 'Please enter the valid longitude' : '',
            latitude: latitudeError ? 'Please enter the valid latitude' : '',
        });

        if (!nameError && !codeError && !longitudeError && !latitudeError) {
            console.log('Adding airport:', airportData);
            makeRequest(airportData.name, airportData.code, airportData.longitude, airportData.latitude)
                .then(data => {
                    console.log("Request successful:", data);
                    if (data[0]) {
                        setErrors({
                            code: 'Airport already exists',
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
        <div className="airport-form">
            <div className='form-header'>
                <h2 className='form-title'>Add An Airport</h2>
            </div>

            <form className="form-body">
                <div className='form-field'>
                    <label className="form-label" htmlFor="name">Name:</label>
                    <input type="text" id="name" name="name" className="login-input" value={airportData.name}
                        onChange={handleChange} />
                    <span className="error-message">{errors.name}</span>
                </div>

                <div className='form-field'>
                    <label className="form-label" htmlFor="code">Code:</label>
                    <input type="text" id="code" name="code" className="login-input" value={airportData.code}
                        onChange={handleChange} />
                    <span className="error-message">{errors.code}</span>
                </div>

                <div className='form-field'>
                    <label className="form-label" htmlFor="longitude">Longitude:</label>
                    <input type="number" id="longitude" name="longitude" className="login-input" value={airportData.longitude}
                        onChange={handleChange} />
                    <span className="error-message">{errors.longitude}</span>
                </div>

                <div className='form-field'>
                    <label className="form-label" htmlFor="latitude">Latitude:</label>
                    <input type="number" id="latitude" name="latitude" className="login-input" value={airportData.latitude}
                        onChange={handleChange} />
                    <span className="error-message">{errors.latitude}</span>
                </div>

                <button className="airp-button" type="submit" onClick={handleSubmit}>
                    Add Airport
                </button>
            </form>
        </div>
    );
};

export default AirportForm;
