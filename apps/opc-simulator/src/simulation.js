const TICK_INTERVAL_MS = 10; // 50Hz update rate
const { logEvent } = require('./Logger')

const simulation = {
    pieces: [], // Array of pieces in the system
    conveyorSpeed: 100, // mm/s
    piecesCompleted: 0,
    piecesDiscarded: 0,
    positions: {
        arrival: 0,
        stopped: 200,
        height: 200,
        discard: 450,
        exit: 800,
        discardSensor: 450
    },
    pieceIdCounter: 1
};

function runSimulationStep(components) {
  const { actuators, sensors } = components;
  const deltaSeconds = TICK_INTERVAL_MS / 1000;
  
  // Read actuator states once per tick
  const mainConveyorOn = actuators.mainConveyor.isOn();
  const discardConveyorOn = actuators.discardConveyor.isOn();
  const lockEngaged = actuators.lock.isOn();
  const diverterActive = actuators.discardDiverter.isOn();
  
  // --- Piece movement logic ---
  simulation.pieces.forEach(piece => {
        const atLockPosition = piece.x >= simulation.positions.stopped && piece.x < simulation.positions.stopped + 10;
        const atDiverterPosition = piece.x >= simulation.positions.discard && piece.x < simulation.positions.discard + 10;

        // If y > 0, the piece is on the discard conveyor path
        if (piece.y > 0) {
            if (discardConveyorOn) {
                piece.y += simulation.conveyorSpeed * deltaSeconds;
            }
        } 
        // Else, it's on the main conveyor path
        else {
            // Check for diversion: must be at the diverter, and diverter must be active
            if (atDiverterPosition && diverterActive) {
                piece.y = 1; // Start moving on the Y axis
            }
            
            // Move along X axis if main conveyor is on AND (piece is not at the lock OR the lock is off)
            if (mainConveyorOn && (!atLockPosition || !lockEngaged)) {
                piece.x += simulation.conveyorSpeed * deltaSeconds;
            }
        }
    });

    // --- Piece Counting Logic ---
    simulation.pieces.forEach(piece => {
        if (piece.counted) return;

        // Count completed pieces
        if (piece.y === 0 && piece.x >= simulation.positions.exit) {
            simulation.piecesCompleted++;
            piece.counted = true;
            logEvent('System', 'Piece Completed', simulation.piecesCompleted, `(ID: ${piece.id})`);
        }
        // Count discarded pieces (let's say after they travel 50mm on the y-axis)
        else if (piece.y > 50) {
            simulation.piecesDiscarded++;
            piece.counted = true;
            logEvent('System', 'Piece Discarded', simulation.piecesDiscarded, `(ID: ${piece.id})`);
        }
    });

    // --- Remove pieces that are out of bounds ---
    simulation.pieces = simulation.pieces.filter(p => p.x < 1000 && p.y < 1000);

    // --- Update all sensors ---
    sensors.forEach(sensor => sensor.checkState(simulation));
}

function startSimulation(components) {
    console.log("🎯 Physics simulation enabled. Actuators are controlled externally.");
    setInterval(() => runSimulationStep(components), TICK_INTERVAL_MS);
}

module.exports = { simulation, startSimulation }; 