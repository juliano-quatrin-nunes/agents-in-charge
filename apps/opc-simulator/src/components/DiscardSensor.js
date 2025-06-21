const Sensor = require('./Sensor');

class DiscardSensor extends Sensor {
    checkState(simulation) {
        // Active if any piece is on the discard path, near the start of that path.
        const isActive = simulation.pieces.some(p => 
            p.y > 30 && p.y < 50 // Piece is on the discard conveyor (y > 0) and within sensor range
        );
        this.update(isActive);
    }
}

module.exports = DiscardSensor; 