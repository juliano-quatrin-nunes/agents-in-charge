import { Variant, DataType, UAVariable } from "node-opcua";
import OpcuaNode from "./OpcuaNode";
import { logEvent } from "./Logger";
import { Simulation } from "../simulation";
import { isPieceAtPosition } from "../lib/utils";

export default class Sensor extends OpcuaNode {
  active: boolean;
  position: { x: number; y: number };

  constructor(node: UAVariable, position: { x: number; y: number }) {
    super(node);
    this.active = false;
    this.position = position;
  }

  update(newValue: boolean) {
    if (this.active !== newValue) {
      this.active = newValue;
      this.node.setValueFromSource(
        new Variant({ dataType: DataType.Boolean, value: this.active })
      );
      logEvent("Sensor", this.browseName, this.active);
    }
  }

  checkState(simulation: Simulation) {
    const newValue = simulation.pieces.some((p) => isPieceAtPosition(p, this.position));
    this.update(newValue);
  }
}
