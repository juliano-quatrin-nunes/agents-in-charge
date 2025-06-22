import Actuator from "../components/Actuator";
import Sensor from "../components/Sensor";
import { SENSOR_NODES, ACTUATOR_NODES } from "./components";

export type NodeCommon = {
  name: string;
  nodeId: number;
};

export type NodeSensor = NodeCommon & {
  type: "sensor";
  class: typeof Sensor;
  position: { x: number; y: number };
};

export type NodeActuator = NodeCommon & {
  type: "actuator";
  class: typeof Actuator;
};

export type NodeInfo = NodeSensor | NodeActuator;

export type Components = {
  actuators: Record<keyof typeof ACTUATOR_NODES, Actuator>;
  sensors: Record<keyof typeof SENSOR_NODES, Sensor>;
};
