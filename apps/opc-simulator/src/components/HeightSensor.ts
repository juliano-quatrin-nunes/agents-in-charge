import { UAVariable } from "node-opcua";
import Sensor from "./Sensor";
import { isPieceAtPosition } from "../lib/utils";
import { Simulation } from "../simulation";

export class HeightSensor extends Sensor {
  constructor(node: UAVariable, position: { x: number; y: number }) {
    super(node, position);
  }

  checkState(simulation: Simulation) {
    const isHigh = simulation.pieces.some(
      (p) => isPieceAtPosition(p, this.position) && p.isHigh
    );
    this.update(isHigh);
  }
}
