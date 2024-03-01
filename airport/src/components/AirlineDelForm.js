import React, { useState } from 'react';
import axios from 'axios'
import { useContext } from 'react';
import { AuthContext } from './AuthComponent';
import { useNavigate } from 'react-router-dom';

const makeRequest = (name) => {
    const params = {
        name: name,
    };
    return new Promise((resolve, reject) => {
        axios.get('http://localhost:8080/api/airport/deleteairline', { params })
            .then(response => {
                resolve(response.data);
            })
            .catch(error => {
                reject(error);
            });
    });
};

const AirlineDelForm = () => {
    const navigate = useNavigate();
    const { user, setUser } = useContext(AuthContext)

    const [airlineData, setAirlineData] = useState({
        name: '',
    });

    const [errors, setErrors] = useState({
        name: '',
    });

    const handleChange = (event) => {
        const { name, value } = event.target;
        setAirlineData({ ...airlineData, [name]: value });
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        const nameError = airlineData.name.length == 0 ? true : false;

        setErrors({
            name: nameError ? 'Please enter a valid name' : '',
        });

        if (!nameError) {
            console.log('Deleting airline:', airlineData);
            makeRequest(airlineData.name)
                .then(data => {
                    console.log("Request successful:", data);
                    if (data[0]) {
                        setErrors({
                            name: 'Airline does not exist',
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
                <h2 className='form-title'>Delete An Airline</h2>
            </div>

            <form className="form-body">
                <div className='form-field'>
                    <label className="form-label" htmlFor="name">Name:</label>
                    <input type="text" id="name" name="name" className="login-input" value={airlineData.name}
                        onChange={handleChange} />
                    <span className="error-message">{errors.name}</span>
                </div>

                <button className="airp-button" type="submit" onClick={handleSubmit}>
                    Delete Airline
                </button>
            </form>
        </div>
    );
};

export default AirlineDelForm;
