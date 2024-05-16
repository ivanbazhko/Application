import React, { useContext, useEffect, useState } from 'react'
import axios from 'axios'
import LoginForm from './LogInForm'
import SignUpForm from './SignUpForm'
import AirportForm from './AirportForm.js'
import AuthComponent, { AuthContext } from './AuthComponent'
import AirportDelForm from './AirportDelForm.js'
import AirlineForm from './AirlineForm.js'
import AirlineDelForm from './AirlineDelForm.js'
import FlightForm from './FlightForm.js'
import FlightDelForm from './FlightDelForm.js'
import ImageForm from './ImageForm.js'

export default function AccountPage() {

    console.log("xxxyyy");
    console.log(useContext(AuthContext));

    const { user, setUser } = useContext(AuthContext);

    const [coords, setCoords] = useState([]);

    var mailing = 0;

    const getBookings = (email) => {
        const params = {
            email: user.email,
        };
        console.log(params);
        return new Promise((resolve, reject) => {
            axios.get('http://localhost:8080/api/airport/bookings', { params })
                .then(response => {
                    setCoords(response.data);
                    resolve(response.data);
                })
                .catch(error => {
                    reject(error);
                });
        });
    };

    const HandleViewClick = (event) => {
        event.preventDefault();
        getBookings(user.email).then(data => {
            console.log(data);
            console.log(coords);
        });
    };

    const checkState = () => {
        console.log(coords);
    }

    useEffect(() => {
        checkState();
    }, [coords]);

    const [isClicked, setIsClicked] = useState(false);
    const [showLoginForm, setShowLoginForm] = useState(true);
    const [showSignUpForm, setShowSignUpForm] = useState(false);
    const handleLoginClick = () => {
        setShowLoginForm(true);
        setShowSignUpForm(false);
    };
    const handleSignUpClick = () => {
        setShowLoginForm(false);
        setShowSignUpForm(true);
    };

    if (!user) console.log("Not logged")
    else console.log(user)
    return (
        <div>
            {!user && (
                <div className="acccontainer">
                    <button className="authbutton" onClick={handleLoginClick}>Log In</button>
                    <button className="authbutton" onClick={handleSignUpClick}>Sign Up</button>
                    {showLoginForm && <LoginForm />}
                    {showSignUpForm && <SignUpForm />}
                </div>
            )}
            {user && !user.isAdmin && (
                <div className="acccontainer">
                    <div>
                        <h1 className='welcome'>Welcome, {user.email}!</h1>
                        <button className="authbutton" type="submit" onClick={() => {
                            if (!isClicked) {
                                setIsClicked(true);
                                setUser(null);
                            }
                        }}>
                            Log Out
                        </button>
                        <button className="authbutton" type="submit" onClick={HandleViewClick}>
                            View Bookings
                        </button><br />
                    </div>
                    <div className="wrap_a">
                        {coords.map(coord => (
                            <div className="inp_f_a">
                                {coord.userid}<br /><br />
                                {coord.date}<br /><br />
                                {coord.flightid}<br /><br />
                            </div>
                        ))}
                    </div>
                </div>
            )}
            {user && user.isAdmin && (
                <div className="admincontainer">
                    {/* Welcome, {user.email} */}
                    <div className="airportaction">
                        <AirportForm />
                        <AirportDelForm />
                        <button className="authbutton" type="submit" onClick={() => {
                            if (!isClicked) {
                                setIsClicked(true);
                                setUser(null);
                            }
                        }}>
                            Log Out
                        </button>
                    </div>
                    <div className="airportaction">
                        <AirlineForm />
                        <AirlineDelForm />
                    </div>
                    <div className="airportaction">
                        <FlightForm />
                        <FlightDelForm />
                    </div>
                    <div className="airportaction">
                        <ImageForm />
                    </div>
                </div>
            )}
        </div>
    );
}
