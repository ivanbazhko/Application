import React, { useState } from 'react';
import axios from 'axios'
import bcrypt from 'bcryptjs';
import { useContext } from 'react';
import { AuthContext } from './AuthComponent';

const makeRequest = (name, password, rawpassword) => {
    const params = {
        email: name,
        password: password,
        login: 0,
    };
    return new Promise((resolve, reject) => {
        axios.get('http://localhost:8080/api/airport/adduser', { params })
            .then(response => {
                const isMatch = bcrypt.compareSync(rawpassword, response.data[2]);
                response.data.isMatch = isMatch;
                resolve(response.data);
            })
            .catch(error => {
                reject(error);
            });
    });
};

const makeMailRequest = (name) => {
    const params = {
        email: name,
    };
    return new Promise((resolve, reject) => {
        axios.get('http://localhost:8080/api/airport/sendconfirmationmail', { params })
            .then(response => {
                resolve(response.data);
            })
            .catch(error => {
                reject(error);
            });
    });
};

const SignUpForm = () => {
    const { user, setUser } = useContext(AuthContext)
    if (!user) console.log("Not logged")

    const [loginData, setLoginData] = useState({
        email: '',
        password: '',
        confirmationCode: '',
    });

    const [errors, setErrors] = useState({
        email: '',
        password: '',
        confirmationCode: '',
    });

    const [showConfirmationInput, setShowConfirmationInput] = useState(false);
    const [confirmationCode, setConfirmationCode] = useState('');

    const handleChange = (event) => {
        const { name, value } = event.target;
        setLoginData({ ...loginData, [name]: value });
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        const emailError = isValidEmail(loginData.email);
        const passwordError = loginData.password.length === 0;

        setErrors({
            email: emailError ? 'Please enter a valid email address' : '',
            password: passwordError ? 'Please enter a password' : '',
            confirmationCode: '',
        });

        if (!emailError && !passwordError) {
            console.log('Sending confirmation email:', loginData.email);
            makeMailRequest(loginData.email)
                .then(data => {
                    console.log("Confirmation email sent:", data);
                    setConfirmationCode(data);
                    setShowConfirmationInput(true);
                })
                .catch(error => {
                    console.error("Failed to send confirmation email:", error);
                });
        }
    };

    const handleConfirmationSubmit = (event) => {
        event.preventDefault();

        if (loginData.confirmationCode == confirmationCode) {
            console.log('Signing up:', loginData);
            const salt = bcrypt.genSaltSync(10);
            const hashedPassword = bcrypt.hashSync(loginData.password, salt);
            makeRequest(loginData.email, hashedPassword, loginData.password)
                .then(data => {
                    console.log("Request successful:", data);
                    if (!data[4]) {
                        setErrors({
                            email: 'User with this e-mail already exists',
                            password: '',
                            confirmationCode: '',
                        });
                    } else {
                        console.log(data);
                        setUser({
                            email: data[0],
                            id: data[1],
                            isAdmin: data[3]
                        })
                    }
                })
                .catch(error => {
                    console.error("Request failed:", error);
                });
        } else {
            setErrors({
                confirmationCode: 'Incorrect confirmation code',
            });
        }
    };

    const isValidEmail = (email) => {
        console.log(email.length);
        if (email.length === 0) return true
        const regex = /^(([^<>()[\]\\.,;:\s@"]+)@([a-zA-Z0-9-]+).([a-zA-Z]{2,}))$/;
        return !regex.test(email);
    };

    return (
        <div className="login-form">
            <div className='login-form-header'>
                <h2 className='login-form-title'>Sign Up</h2>
            </div>

            <form className="login-form-body">
                <div className='login-field'>
                    <label className="login-label" htmlFor="email">Email:</label>
                    <input id="email" className="login-input" type="email" name="email" value={loginData.email}
                        onChange={handleChange} />
                    <span className="error-message">{errors.email}</span>
                </div>

                <div className='login-field'>
                    <label className="login-label" htmlFor="password">Password:</label>
                    <input id="password" className="login-input" type="password" name="password" value={loginData.password}
                        onChange={handleChange} />
                    <span className="error-message">{errors.password}</span>
                </div>

                {showConfirmationInput && (
                    <div className='login-field'>
                        <label className="login-label" htmlFor="confirmationCode">Confirmation Code:</label>
                        <input id="confirmationCode" className="login-input" type="text" name="confirmationCode" value={loginData.confirmationCode}
                            onChange={handleChange} />
                        <span className="error-message">{errors.confirmationCode}</span>
                    </div>
                )}

                {!showConfirmationInput ? (
                    <button className="login-button" type="submit" onClick={handleSubmit}>
                        Submit
                    </button>
                ) : (
                    <button className="login-button" type="submit" onClick={handleConfirmationSubmit}>
                        Confirm
                    </button>
                )}
            </form>
        </div>
    );
};

export default SignUpForm;