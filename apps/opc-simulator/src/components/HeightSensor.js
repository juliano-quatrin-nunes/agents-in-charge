const Sensor = require('./Sensor');

class HeightSensor extends Sensor {
    checkState(simulation) {
        // Active if a high piece is at the lock position on the main conveyor.
        const isActive = simulation.pieces.some(p => 
            p.isHigh &&
            p.y === 0 &&
            p.x >= simulation.positions.height &&
            p.x < simulation.positions.height + 10 // 10mm tolerance
        );
        this.update(isActive);
    }
}

module.exports = HeightSensor; 