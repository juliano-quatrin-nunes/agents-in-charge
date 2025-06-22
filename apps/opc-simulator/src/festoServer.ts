import { OPCUAServer, makeNodeId } from "node-opcua";

// Component Classes
import Actuator from "./components/Actuator";

// Simulation and Server
import { simulation, startSimulation } from "./simulation";
import { startHttpServer } from "./httpServer";
import Sensor from "./components/Sensor";
import { toCamelCase } from "./lib/utils";
import NODE_INFOS from "./lib/components";

(async () => {
  // --- OPC UA Server Setup ---
  const server = new OPCUAServer({
    port: 4840,
    resourcePath: "",
    buildInfo: {
      productName: "FESTO Separating Station Simulator",
      buildNumber: "2.1.0",
      buildDate: new Date(),
    },
  });

  await server.initialize();
  console.log("🔧 FESTO OPC-UA Server initialized");

  const addressSpace = server.engine.addressSpace;
  addressSpace?.registerNamespace("urn:namespace2");
  addressSpace?.registerNamespace("urn:namespace3");
  const ns4 = addressSpace?.registerNamespace("urn:FestoSeparatingStation");
  console.log(`📋 Registered namespaces - ns4 index: ${ns4?.index}`);

  const device = ns4?.addObject({
    organizedBy: addressSpace?.rootFolder.objects,
    browseName: "FestoSeparatingStation",
    displayName: "FESTO Separating Station",
  });

  // --- Create OPC UA Variables and Component Instances ---
  const components = {
    actuators: {} as Record<string, Actuator>,
    sensors: {} as Record<string, Sensor>,
  };

  for (const [key, info] of Object.entries(NODE_INFOS)) {
    const opcuaVar = ns4?.addVariable({
      componentOf: device,
      browseName: info.name,
      dataType: "Boolean",
      value: { dataType: "Boolean", value: false },
      nodeId: makeNodeId(info.nodeId, 4),
    });

    if (!opcuaVar) {
      console.error(`❌ Failed to create variable for ${info.name}`);
      continue;
    }

    if (info.type === "actuator") {
      const instance = new info.class(opcuaVar);
      components.actuators[key] = instance;
    }
    if (info.type === "sensor") {
      const instance = new info.class(opcuaVar, info.position);
      components.sensors[key] = instance;
    }
  }

  ns4?.addVariable({
    componentOf: device,
    browseName: "Agents in charge",
    displayName: "Agents in charge",
    dataType: "Boolean",
    value: { dataType: "Boolean", value: true }, // Initial state is true
    nodeId: makeNodeId(11, 4),
  });

  console.log("✅ All FESTO variables and components created");

  // --- Start Server and Simulation ---
  await server.start();
  console.log(`🔗 Connect from Node-RED using: ${server.getEndpointUrl()}`);

  console.log(`
  📊 Stats Legend:
  -----------------
  T: Total pieces
  H: High pieces
  L: Low pieces
  C: Completed (Correctly/Incorrectly)
  D: Discarded (Correctly/Incorrectly)
  -----------------
  `);

  startSimulation(components);
  startHttpServer(simulation);
})();
