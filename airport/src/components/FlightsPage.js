import React, { useEffect, useState } from 'react'
import axios from 'axios'
import WorldMap from './WorldMap.js'

export default function FlightsPage() {

  // const [flights, setFlights] = useState([]);

  // const getFlights = () => {
  //   axios
  //     .get('http://localhost:8080/api/airport/flights')
  //     .then(response => {
  //       setFlights(response.data.map(item => item));
  //     })
  //     .catch(error => {
  //       console.error(error);
  //     });
  // };

  // const checkState = () => {
  //   console.log(flights);
  // }

  // useEffect(() => {
  //   getFlights();
  // }, []);

  // useEffect(() => {
  //   checkState();
  // }, [flights]);

  return (
    <div className="flcontainer">
      <div className="flleft-div">
        <WorldMap />
      </div>
      {/* <div className="flright-div">
        {flights.map((flight) => (
          <div className="one-flight">
            {flight.destination}, {flight.country}
          </div>
        ))}
      </div> */}
    </div>
  )
}
