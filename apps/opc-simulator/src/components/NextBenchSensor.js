const Sensor = require('./Sensor');

class NextBenchSensor extends Sensor {
    checkState(simulation) {
        // Active if a piece is at the exit, ready for the next bench.
        const isActive = simulation.pieces.some(p => 
            p.y === 0 &&
            p.x >= simulation.positions.exit &&
            p.x < simulation.positions.exit + 20 // 20mm tolerance
        );
        this.update(isActive);
    }
}

module.exports = NextBenchSensor; 