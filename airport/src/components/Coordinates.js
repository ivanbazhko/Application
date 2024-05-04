import { getDistance, computeDestinationPoint, getGreatCircleBearing } from 'geolib';
import React, { useEffect, useState, useRef } from 'react'
import axios from 'axios'

export default function getPointsBetweenCoordinates(startCoordinate, endCoordinate) {
    var distance = getDistance({latitude: startCoordinate[0], longitude: startCoordinate[1]}, {latitude: endCoordinate[0], longitude: endCoordinate[1]});
    var steps = 1000;

    var coordinates1 = [];
    var coordinates2 = [];
    var flag = 0;
    var lastlonsign = 0;
    lastlonsign = startCoordinate[1] > 0 ? 1 : -1;
    coordinates1.push(startCoordinate);

    var bearing = getGreatCircleBearing({latitude: startCoordinate[0], longitude: startCoordinate[1]}, {latitude: endCoordinate[0], longitude: endCoordinate[1]});

    for (var i = 1; i < steps; i++) {
        var ratio = i / steps;
        var intermediateCoordinate = computeDestinationPoint({latitude: startCoordinate[0], longitude: startCoordinate[1]}, distance * ratio, bearing);
        
        if(intermediateCoordinate.longitude * lastlonsign < 0 && intermediateCoordinate.longitude * lastlonsign < -100) flag = 1
        lastlonsign = intermediateCoordinate.longitude > 0 ? 1 : -1;

        if(flag == 0){
            coordinates1.push([intermediateCoordinate.latitude, intermediateCoordinate.longitude]);
        } else {
            coordinates2.push([intermediateCoordinate.latitude, intermediateCoordinate.longitude]);
        }
    }
    
    if(flag == 0) {
        coordinates1.push(endCoordinate);
    } else {
        coordinates2.push(endCoordinate);
    }

    return {r1: coordinates1, r2: coordinates2};
}

export function getSpecificPoint(startCoordinate, endCoordinate, direction = 1, index) {
    var patharr1, patharr2, patharr3;
    patharr1 = getPointsBetweenCoordinates(startCoordinate, endCoordinate).r1;
    patharr2 = getPointsBetweenCoordinates(startCoordinate, endCoordinate).r2;
    patharr3 = patharr1.concat(patharr2);
    var bearing;
    var activity = true;
    if(direction == 1) bearing = getGreatCircleBearing({latitude: patharr3[index][0], longitude: patharr3[index][1]}, {latitude: patharr3[index + 1][0], longitude: patharr3[index + 1][1]});
    else bearing = getGreatCircleBearing({latitude: patharr3[index][0], longitude: patharr3[index][1]}, {latitude: patharr3[index - 1][0], longitude: patharr3[index - 1][1]});
    
    return {coord: patharr3[index], angle: bearing};
}

export function getRandomPoint(startCoordinate, endCoordinate, direction = 1) {
    var patharr1, patharr2, patharr3;
    patharr1 = getPointsBetweenCoordinates(startCoordinate, endCoordinate).r1;
    patharr2 = getPointsBetweenCoordinates(startCoordinate, endCoordinate).r2;
    patharr3 = patharr1.concat(patharr2);
    var randomIndex = 1 + Math.floor(Math.random() * (patharr3.length - 2));
    var bearing;
    var activity = true;
    if(direction == 1) bearing = getGreatCircleBearing({latitude: patharr3[randomIndex][0], longitude: patharr3[randomIndex][1]}, {latitude: patharr3[randomIndex + 1][0], longitude: patharr3[randomIndex + 1][1]});
    else bearing = getGreatCircleBearing({latitude: patharr3[randomIndex][0], longitude: patharr3[randomIndex][1]}, {latitude: patharr3[randomIndex - 1][0], longitude: patharr3[randomIndex - 1][1]});
    return {coord: patharr3[randomIndex], angle: bearing, active: activity, direction: direction};
}

export function getPoints1 (homecoord1, homecoord2, plane) {

    var planewithcoords;

    var u = getSpecificPoint([homecoord1, homecoord2], [plane.coord1, plane.coord2], plane.direction, plane.progress);
    var n = {
        point: u.coord,
        angle: u.angle,
        deptime: plane.departure,
        arrtime: plane.arrival,
        logo: plane.logo,
        direction: plane.direction,
        route: plane.route,
        number: plane.number,
        progress: plane.progress,
        airplane: plane.airplane
    }
    planewithcoords = n;
      
    return planewithcoords
}
