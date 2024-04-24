import { getDistance, computeDestinationPoint, getGreatCircleBearing } from 'geolib';

export default function getPointsBetweenCoordinates(startCoordinate, endCoordinate) {
    var distance = getDistance({latitude: startCoordinate[0], longitude: startCoordinate[1]}, {latitude: endCoordinate[0], longitude: endCoordinate[1]});
    var steps = 100; // Количество шагов

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
