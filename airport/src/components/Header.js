import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom';
import axios from 'axios'

export default function Header() {

  const [flights, setFlights] = useState([]);

  const getFlights = () => {
    axios
      .get('http://localhost:8080/api/alpinomini/viewhistory')
        .then(response => {
          const years = response.data.map(item => item.year);
          console.log(years);
          setFlights(response.data);
        })
        .catch(error => {
          console.error(error);
        });
  };

  return (
    <header>
      <div>
        <span className='logo'>
          Airport
        </span>
        <ul className='nav'>
          <li><Link to="/" style={{ textDecoration: 'none', color: 'white' }}>Main Page</Link></li>
          <li><Link to="/account" style={{ textDecoration: 'none', color: 'white' }}>Account</Link></li>
          <li><Link to="/flights" style={{ textDecoration: 'none', color: 'white' }}>Our Flights</Link></li>
          <li><Link to="/about" style={{ textDecoration: 'none', color: 'white' }}>About</Link></li>
          {/* <li onClick={getFlights}>About</li> */}
        </ul>
      </div>
    </header>
  )
}
