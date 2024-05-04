import React, { useRef, useEffect } from 'react';
import { Marker, Popup } from 'react-leaflet';

const CustomMarker = ({ point, movIcon }) => {
    const markerRef = useRef(null);
  
    useEffect(() => {
      if (markerRef.current) {
        markerRef.current.setRotationAngle(point.angle);
      }
    }, [point]);

    var realprogress;

    return (
    realprogress = point.direction > 0 ? (point.progress / 10) : ((1000 - point.progress) / 10),
    <Marker
        position={point.point}
        icon={movIcon}
        rotationAngle={point.angle}
        rotationOrigin='center center'
        ref={markerRef}
    >
        <Popup className="mapPopUp1">
            <div className="content">
                <img src={point.logo} height="30px" width="30px" alt="Logo" />
                <span className="number">   {point.number}</span>
            </div>
            {point.route}<br/>
            Departed: {point.deptime}<br/>
            Arriving: {point.arrtime}<br/>
            Progress: {realprogress}%<br/>
            Airplane: {point.airplane}<br/>
        </Popup>
    </Marker>
    );
  };

export default CustomMarker;