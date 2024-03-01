import React, { useState } from 'react';
import axios from 'axios'
import { useContext } from 'react';
import { AuthContext } from './AuthComponent';
import { useNavigate } from 'react-router-dom';

const makeRequest = (code) => {
    const params = {
        country: code,
    };
    return new Promise((resolve, reject) => {
        axios.get('http://localhost:8080/api/airport/deleteairport', { params })
            .then(response => {
                resolve(response.data);
            })
            .catch(error => {
                reject(error);
            });
    });
};

const AirportDelForm = () => {
    const navigate = useNavigate();
    const { user, setUser } = useContext(AuthContext)

    const [airportData, setAirportData] = useState({
        code: '',
    });

    const [errors, setErrors] = useState({
        code: '',
    });

    const handleChange = (event) => {
        const { name, value } = event.target;
        setAirportData({ ...airportData, [name]: value });
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        const codeError = airportData.code.length != 3 ? true : false;

        setErrors({
            code: codeError ? 'Please enter a valid code' : '',
        });

        if (!codeError) {
            console.log('Deleting airport:', airportData);
            makeRequest(airportData.code)
                .then(data => {
                    console.log("Request successful:", data);
                    if (data[0]) {
                        setErrors({
                            code: 'Airport does not exist',
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
                <h2 className='form-title'>Delete An Airport</h2>
            </div>

            <form className="form-body">
                <div className='form-field'>
                    <label className="form-label" htmlFor="code">Code:</label>
                    <input type="text" id="code" name="code" className="login-input" value={airportData.code}
                        onChange={handleChange} />
                    <span className="error-message">{errors.code}</span>
                </div>

                <button className="airp-button" type="submit" onClick={handleSubmit}>
                    Delete Airport
                </button>
            </form>
        </div>
    );
};

export default AirportDelForm;
