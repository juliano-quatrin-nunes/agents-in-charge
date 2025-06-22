import Actuator from "../components/Actuator";
import Sensor from "../components/Sensor";
import { NodeActuator, NodeInfo, NodeSensor } from "./types";

const ACTUATOR_NODES = {
  discardDiverter: {
    name: "Discard Diverter",
    nodeId: 2,
    type: "actuator",
    class: Actuator,
  },
  lock: { name: "Lock", nodeId: 3, type: "actuator", class: Actuator },
  mainConveyor: {
    name: "Main Conveyor",
    nodeId: 4,
    type: "actuator",
    class: Actuator,
  },
  discardConveyor: {
    name: "Discard Conveyor",
    nodeId: 5,
    type: "actuator",
    class: Actuator,
  },
  communicator: {
    name: "Communicator",
    nodeId: 13,
    type: "actuator",
    class: Actuator,
  },
} as const;

const SENSOR_NODES = {
  arrivalSensor: {
    name: "Arrival Sensor",
    nodeId: 6,
    type: "sensor",
    class: Sensor,
    position: { x: 0, y: 0 },
  },
  stoppedSensor: {
    name: "Stopped Sensor",
    nodeId: 7,
    type: "sensor",
    class: Sensor,
    position: { x: 200, y: 0 },
  },
  heightSensor: {
    name: "Height Sensor",
    nodeId: 8,
    type: "sensor",
    class: Sensor,
    position: { x: 200, y: 0 },
  },
  discardSensor: {
    name: "Discard Sensor",
    nodeId: 10,
    type: "sensor",
    class: Sensor,
    position: { x: 400, y: 200 },
  },
  exitSensor: {
    name: "Exit Sensor",
    nodeId: 9,
    type: "sensor",
    class: Sensor,
    position: { x: 750, y: 0 },
  },
  nextBench: {
    name: "Next Bench",
    nodeId: 12,
    type: "sensor",
    class: Sensor,
    position: { x: 750, y: 0 },
  },
} as const;

const NODE_INFOS = {
  ...ACTUATOR_NODES,
  ...SENSOR_NODES,
};

export { ACTUATOR_NODES, SENSOR_NODES };

export default NODE_INFOS;
