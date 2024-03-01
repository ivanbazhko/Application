import React, { useEffect, useState } from 'react'
import axios from 'axios'
import BookingForm from './BookingForm'

export default function MainPage() {

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
    <div className="mainlogo">
      <div className="mainleft-div">
      </div>
      <div className="mainright-div">
        <BookingForm />
      </div>
    </div>
  )
}
