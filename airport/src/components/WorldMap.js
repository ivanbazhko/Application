import React, { useEffect, useState, useRef } from 'react'
import axios from 'axios'
import "leaflet/dist/leaflet.css"
import { MapContainer, Marker, TileLayer, Popup, Polyline } from "react-leaflet"
import { Icon, L } from "leaflet"
import GetCoordinates from './Coordinates'
import "leaflet-rotatedmarker"
import { getRandomPoint, getPoints1 } from './Coordinates'
import CustomMarker from './CustomMarker'

export default function WorldMap() {

  const [coords, setCoords] = useState([]);
  const [homes, setHomes] = useState([]);
  const [activePlanes, setActivePlanes] = useState([]);
  var coords_n, home_n, active_n

  var home = {
    coord1: 0.0,
    coord2: 0.0,
    name: "",
    country: ""
  }

  var arr1, arr2, arr3;

  const getCoords = () => {
    axios
      .get('http://localhost:8080/api/airport/airports')
      .then(response => {
        coords_n = response.data.map(item => item);
        setCoords(response.data)
      })
      .catch(error => {
        console.error(error);
      });
  };

  const getHome = () => {
    axios
      .get('http://localhost:8080/api/airport/home')
      .then(response => {
        home_n = response.data.map(item => item);
        setHomes(response.data);
      })
      .catch(error => {
        console.error(error);
      });
  };

  const getActives = () => {
    axios
      .get('http://localhost:8080/api/airport/getactiveflights')
      .then(response => {
        active_n = response.data.map(item => item);
        setActivePlanes(response.data);
      })
      .catch(error => {
        console.error(error);
      });
  };

  useEffect(() => {
    getCoords();
    getHome();
    getActives();
  }, []);

  useEffect(() => {
    checkState();
  }, [coords, homes]);

  const checkState = () => {
    // console.log(coords);
    // console.log(homes);
  }

  useEffect(() => {
    const interval = setInterval(() => {
      getActives();
    }, 15000);

    return () => {
        clearInterval(interval);
    };
}, []);

  const homeIcon = new Icon({
    iconUrl: "https://www.iconpacks.net/icons/2/free-airport-location-icon-2959-thumb.png",
    iconSize: [50, 50],
    iconAnchor: [25, 25],
    className: "homeIcon"
  })

  const customIcon = new Icon({
    iconUrl: "https://cdn-icons-png.flaticon.com/512/6618/6618280.png",
    iconSize: [33, 33]
  })

  const movIcon = new Icon({
    iconUrl: "https://images.vexels.com/media/users/3/157085/isolated/preview/96b2c67a9d3d2d78379107717f60f3b7-transport-airplane-top-view-silhouette.png",
    iconSize: [30, 30],
    iconAnchor: [15, 15],
    className: "airplaneIcon"
  })

  return (
    <MapContainer center={[20.0000, 0.0000]} zoom={2} minZoom={1} maxBounds={[[90, -180], [-90, 180]]}>
      <TileLayer
        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
      />

      {homes.map(coord => (
        home.coord1 = coord.coord1,
        home.coord2 = coord.coord2,
        <Marker position={[coord.coord1, coord.coord2]} icon={homeIcon}>
          <Popup className="mapPopUp">{coord.name}, {coord.country}</Popup>
        </Marker>
      ))}

      {coords.map(coord => (
        <Marker position={[coord.coord1, coord.coord2]} icon={customIcon}>
          <Popup className="mapPopUp">{coord.name}, {coord.country}</Popup>
        </Marker>
      ))}

      {coords.map(coord => (
        arr1 = GetCoordinates([home.coord1, home.coord2], [coord.coord1, coord.coord2]).r1,
        homes.length > 0 ?
        <Polyline positions={arr1} color='blue'>
          <Popup className="mapPopUp2">{homes[0].name} - {coord.name}</Popup>
        </Polyline> : null
      ))}

      {coords.map(coord => (
        arr2 = GetCoordinates([home.coord1, home.coord2], [coord.coord1, coord.coord2]).r2,
        homes.length > 0 ?
        <Polyline positions={arr2} color='blue'>
          <Popup className="mapPopUp2">{homes[0].name} - {coord.name}</Popup>
        </Polyline> : null
      ))}

      {activePlanes.map(point => (
        arr3 = homes.length > 0 ? getPoints1(home.coord1, home.coord2, point) : null,
        homes.length > 0 ? <CustomMarker key={arr3.id} point={arr3} movIcon={movIcon} /> : null
      ))}

    </MapContainer>
  )
}