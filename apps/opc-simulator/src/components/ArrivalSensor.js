const Sensor = require('./Sensor');

class ArrivalSensor extends Sensor {
    checkState(simulation) {
        // Active if any piece is on the main conveyor, before the lock.
        const isActive = simulation.pieces.some(p => 
            p.y === 0 && p.x > simulation.positions.arrival && p.x < simulation.positions.arrival + 20
        );
        this.update(isActive);
    }
}

module.exports = ArrivalSensor; 