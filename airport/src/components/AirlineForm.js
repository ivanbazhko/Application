import React, { useState } from 'react';
import axios from 'axios'
import { useContext } from 'react';
import { AuthContext } from './AuthComponent';
import { useNavigate } from 'react-router-dom';

const makeRequest = (name, logo, code) => {
    const params = {
        name: name,
        logo: logo,
        code: code,
    };
    return new Promise((resolve, reject) => {
        axios.get('http://localhost:8080/api/airport/addairline', { params })
            .then(response => {
                resolve(response.data);
            })
            .catch(error => {
                reject(error);
            });
    });
};

const AirlineForm = () => {
    const navigate = useNavigate();
    const { user, setUser } = useContext(AuthContext)

    const [airlineData, setAirlineData] = useState({
        name: '',
        code: '',
        logo: '',
    });

    const [errors, setErrors] = useState({
        name: '',
        code: '',
        logo: '',
    });

    const handleChange = (event) => {
        const { name, value } = event.target;
        setAirlineData({ ...airlineData, [name]: value });
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        const nameError = airlineData.name.length == 0 ? true : false;
        const codeError = airlineData.code.length != 2 ? true : false;
        const logoError = airlineData.logo.length == 0 ? true : false;

        setErrors({
            name: nameError ? 'Please enter a name' : '',
            code: codeError ? 'Please enter a valid code' : '',
            logo: logoError ? 'Please enter the valid logo URL' : '',
        });

        if (!nameError && !codeError && !logoError) {
            console.log('Adding airline:', airlineData);
            makeRequest(airlineData.name, airlineData.logo, airlineData.code)
                .then(data => {
                    console.log("Request successful:", data);
                    if (data[0]) {
                        setErrors({
                            name: 'Airline already exists',
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
                <h2 className='form-title'>Add An Airline</h2>
            </div>

            <form className="form-body">
                <div className='form-field'>
                    <label className="form-label" htmlFor="name">Name:</label>
                    <input type="text" id="name" name="name" className="login-input" value={airlineData.name}
                        onChange={handleChange} />
                    <span className="error-message">{errors.name}</span>
                </div>

                <div className='form-field'>
                    <label className="form-label" htmlFor="code">Code:</label>
                    <input type="text" id="code" name="code" className="login-input" value={airlineData.code}
                        onChange={handleChange} />
                    <span className="error-message">{errors.code}</span>
                </div>

                <div className='form-field'>
                    <label className="form-label" htmlFor="logo">Logo URL:</label>
                    <input type="text" id="logo" name="logo" className="login-input" value={airlineData.logo}
                        onChange={handleChange} />
                    <span className="error-message">{errors.logo}</span>
                </div>

                <button className="airp-button" type="submit" onClick={handleSubmit}>
                    Add Airline
                </button>
            </form>
        </div>
    );
};

export default AirlineForm;
