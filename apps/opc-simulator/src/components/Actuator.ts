import { UAVariable } from "node-opcua";
import OpcuaNode from "./OpcuaNode";
import { logEvent } from "./Logger";

export default class Actuator extends OpcuaNode{
  lastKnownState: boolean;

  constructor(node: UAVariable) {
    super(node);
    this.lastKnownState = this.node.readValue().value.value;
  }

  isOn() {
    const currentState = this.node.readValue().value.value;
    if (currentState !== this.lastKnownState) {
      logEvent("Actuator", this.browseName, currentState);
      this.lastKnownState = currentState;
    }
    return currentState;
  }
} 
