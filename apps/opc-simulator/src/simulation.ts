import { isPieceAtPosition } from "./lib/utils";
import Piece from "./components/Piece";
import { Components } from "./lib/types";
import { logEvent } from "./components/Logger";

const TICK_INTERVAL_MS = 10; // 100Hz update rate

const CONVEYOR_SPEED = 100; // mm/s

export type SimulationStats = {
  totalPieces: number;
  highPieces: number;
  lowPieces: number;
  completedCorrectly: number;
  completedIncorrectly: number;
  discardedCorrectly: number;
  discardedIncorrectly: number;
};

export type Simulation = {
  pieces: Piece[];
  conveyorSpeed: number;
  stats: SimulationStats;
  positions: Record<string, { x: number; y: number }>;
  pieceIdCounter: number;
};

const simulation: Simulation = {
  pieces: [], // Array of pieces in the system
  conveyorSpeed: CONVEYOR_SPEED, // mm/s
  stats: {
    totalPieces: 0,
    highPieces: 0,
    lowPieces: 0,
    completedCorrectly: 0,
    completedIncorrectly: 0,
    discardedCorrectly: 0,
    discardedIncorrectly: 0,
  },
  positions: {
    arrivalSensor: { x: 0, y: 0 },
    stoppedSensor: { x: 200, y: 0 },
    heightSensor: { x: 200, y: 0 },
    discardSensor: { x: 400, y: 200 },
    exitSensor: { x: 750, y: 0 },
    discardDiverter: { x: 400, y: 0 },
    endOfMainConveyor: { x: 800, y: 0 },
    endOfDiscardConveyor: { x: 400, y: 400 },
  },
  pieceIdCounter: 1,
};

function getSimulationStats(simulation: Simulation) {
  const { stats } = simulation;
  const totalPieces = stats.totalPieces;
  const highPieces = stats.highPieces;
  const lowPieces = stats.lowPieces;
  const completed = stats.completedCorrectly + stats.completedIncorrectly;
  const discarded = stats.discardedCorrectly + stats.discardedIncorrectly;

  return {
    total: totalPieces,
    high: highPieces,
    low: lowPieces,
    completed,
    discarded,
    completedCorrectly: stats.completedCorrectly,
    completedIncorrectly: stats.completedIncorrectly,
    discardedCorrectly: stats.discardedCorrectly,
    discardedIncorrectly: stats.discardedIncorrectly,
  };
}

function runSimulationStep(components: Components) {
  const { actuators, sensors } = components;
  const deltaSeconds = TICK_INTERVAL_MS / 1000;

  // Read actuator states once per tick
  const mainConveyorOn = actuators.mainConveyor.isOn();
  const discardConveyorOn = actuators.discardConveyor.isOn();
  const lockEngaged = actuators.lock.isOn();
  const diverterActive = actuators.discardDiverter.isOn();

  // --- Piece movement logic ---
  simulation.pieces.forEach((piece: Piece) => {
    const atLockPosition = isPieceAtPosition(
      piece,
      simulation.positions.stoppedSensor
    );

    const atDiverterPosition = isPieceAtPosition(
      piece,
      simulation.positions.discardDiverter
    );

    const isOnDiscardConveyor =
      piece.y > 0 && piece.y < simulation.positions.endOfDiscardConveyor.y;

    const isOnMainConveyor =
      piece.y === 0 && piece.x < simulation.positions.endOfMainConveyor.x;

    const pieceStoppedInLock = atLockPosition && lockEngaged;

    if (isOnDiscardConveyor) {
      if (discardConveyorOn) {
        piece.y += simulation.conveyorSpeed * deltaSeconds;
      }
    }
    if (isOnMainConveyor) {
      if (atDiverterPosition && diverterActive) {
        piece.y = 1;
        piece.x = simulation.positions.discardDiverter.x;
      }

      if (mainConveyorOn && !pieceStoppedInLock) {
        piece.x += simulation.conveyorSpeed * deltaSeconds;
      }
    }
  });

  // --- Piece Counting Logic ---
  simulation.pieces.forEach((piece: Piece) => {
    if (piece.counted) return;

    const hasPieceReachedExitSensor =
      piece.y === 0 && piece.x >= simulation.positions.exitSensor.x;

    const hasPieceReachedDiscardSensor =
      piece.y > 0 && piece.y >= simulation.positions.discardSensor.y;

    if (hasPieceReachedExitSensor) {
      piece.counted = true;
      const correctness = !piece.isHigh ? "Correctly" : "Incorrectly";
      if (!piece.isHigh) {
        simulation.stats.completedCorrectly++;
      } else {
        simulation.stats.completedIncorrectly++;
      }
      const stats = getSimulationStats(simulation);
      const statsString = `T:${stats.total}, H:${stats.high}, L:${stats.low}, C:${stats.completed} (${stats.completedCorrectly}/${stats.completedIncorrectly}), D:${stats.discarded} (${stats.discardedCorrectly}/${stats.discardedIncorrectly})`;
      logEvent(
        "System",
        `Piece Completed ${correctness}`,
        `ID: ${piece.id}`,
        `Type: ${piece.isHigh ? "High" : "Low"}`
      );
      logEvent("System", "Overall Stats", "📈", statsString);
    }
    if (hasPieceReachedDiscardSensor) {
      piece.counted = true;
      const correctness = piece.isHigh ? "Correctly" : "Incorrectly";
      if (piece.isHigh) {
        simulation.stats.discardedCorrectly++;
      } else {
        simulation.stats.discardedIncorrectly++;
      }
      const stats = getSimulationStats(simulation);
      const statsString = `T:${stats.total}, H:${stats.high}, L:${stats.low}, C:${stats.completed} (${stats.completedCorrectly}/${stats.completedIncorrectly}), D:${stats.discarded} (${stats.discardedCorrectly}/${stats.discardedIncorrectly})`;
      logEvent(
        "System",
        `Piece Discarded ${correctness}`,
        `ID: ${piece.id}`,
        `Type: ${piece.isHigh ? "High" : "Low"}`
      );
      logEvent("System", "Overall Stats", "📈", statsString);
    }
  });

  // --- Remove pieces that are out of bounds ---
  simulation.pieces = simulation.pieces.filter(
    (p: Piece) =>
      p.x < simulation.positions.endOfMainConveyor.x &&
      p.y < simulation.positions.endOfDiscardConveyor.y
  );

  // --- Update all sensors ---
  Object.values(sensors).forEach((sensor) => sensor.checkState(simulation));
}

function startSimulation(components: Components) {
  console.log(
    "🎯 Physics simulation enabled. Actuators are controlled externally."
  );
  setInterval(() => runSimulationStep(components), TICK_INTERVAL_MS);
}

export { simulation, startSimulation };
