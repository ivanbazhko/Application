import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom';
import axios from 'axios'

export default function Header() {

  // const [flights, setFlights] = useState([]);

  // const getFlights = () => {
  //   axios
  //     .get('http://localhost:8080/api/alpinomini/viewhistory')
  //       .then(response => {
  //         const years = response.data.map(item => item.year);
  //         console.log(years);
  //         setFlights(response.data);
  //       })
  //       .catch(error => {
  //         console.error(error);
  //       });
  // };

  return (
    <header>
      <div>
        <span className='logo'>
          Airport
        </span>
        <ul className='nav' style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
          <li style={{ display: 'inline-flex', alignItems: 'center' }}><Link to="/" style={{ textDecoration: 'none', color: 'white' }}>Main Page</Link></li>
          <li style={{ display: 'inline-flex', alignItems: 'center' }}><Link to="/account" style={{ textDecoration: 'none', color: 'white' }}>Account</Link></li>
          <li style={{ display: 'inline-flex', alignItems: 'center' }}><Link to="/timetable" style={{ textDecoration: 'none', color: 'white' }}>Timetable</Link></li>
          <li style={{ display: 'inline-flex', alignItems: 'center' }}><Link to="/flights" style={{ textDecoration: 'none', color: 'white' }}>Tracker</Link></li>
          <li style={{ display: 'inline-flex', alignItems: 'center' }}><Link to="/about" style={{ textDecoration: 'none', color: 'white' }}>About</Link></li>
          <li style={{ display: 'inline-flex', alignItems: 'center' }}>
            <Link to="/cart" style={{ textDecoration: 'none', color: 'white' }}>
              <img src="https://www.freeiconspng.com/thumbs/cart-icon/basket-cart-icon-27.png" alt="Cart" style={{ width: '40px', height: '40px' }}/>
            </Link>
          </li>
        </ul>
      </div>
    </header>
  )
}
