const Sensor = require('./Sensor');

class StoppedSensor extends Sensor {
    checkState(simulation) {
        // Active if any piece is at the lock position on the main conveyor.
        const isActive = simulation.pieces.some(p => 
            p.y === 0 &&
            p.x >= simulation.positions.stopped && 
            p.x < simulation.positions.stopped + 20 // 20mm tolerance
        );
        this.update(isActive);
    }
}

module.exports = StoppedSensor; 