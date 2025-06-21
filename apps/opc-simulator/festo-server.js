const { OPCUAServer, makeNodeId } = require("node-opcua");

// Component Classes
const Actuator = require('./src/components/Actuator.js');
const ArrivalSensor = require('./src/components/ArrivalSensor.js');
const StoppedSensor = require('./src/components/StoppedSensor.js');
const HeightSensor = require('./src/components/HeightSensor.js');
const DiscardSensor = require('./src/components/DiscardSensor.js');
const ExitSensor = require('./src/components/ExitSensor.js');
const NextBenchSensor = require('./src/components/NextBenchSensor.js');

// Simulation and Server
const { simulation, startSimulation } = require('./src/simulation.js');
const startHttpServer = require('./src/httpServer.js');

(async () => {
    // --- OPC UA Server Setup ---
    const server = new OPCUAServer({
        port: 4840,
        resourcePath: "",
        buildInfo: {
            productName: "FESTO Separating Station Simulator",
            buildNumber: "2.1.0",
            buildDate: new Date()
        }
    });

    await server.initialize();
    console.log("🔧 FESTO OPC-UA Server initialized");

    const addressSpace = server.engine.addressSpace;
    addressSpace.registerNamespace("urn:namespace2");
    addressSpace.registerNamespace("urn:namespace3");
    const ns4 = addressSpace.registerNamespace("urn:FestoSeparatingStation");
    console.log(`📋 Registered namespaces - ns4 index: ${ns4.index}`);
    
    const device = ns4.addObject({
        organizedBy: addressSpace.rootFolder.objects,
        browseName: "FestoSeparatingStation",
        displayName: "FESTO Separating Station"
    });

    // --- Create OPC UA Variables and Component Instances ---
    const components = {
        actuators: {},
        sensors: []
    };
    
    const nodeInfos = [
        { name: "Discard Diverter", type: "actuator", nodeId: 2, class: Actuator },
        { name: "Lock", type: "actuator", nodeId: 3, class: Actuator },
        { name: "Main Conveyor", type: "actuator", nodeId: 4, class: Actuator },
        { name: "Discard Conveyor", type: "actuator", nodeId: 5, class: Actuator },
        { name: "Arrival Sensor", type: "sensor", nodeId: 6, class: ArrivalSensor },
        { name: "Stopped Sensor", type: "sensor", nodeId: 7, class: StoppedSensor },
        { name: "Height Sensor", type: "sensor", nodeId: 8, class: HeightSensor },
        { name: "Discard Sensor", type: "sensor", nodeId: 10, class: DiscardSensor },
        { name: "Exit Sensor", type: "sensor", nodeId: 9, class: ExitSensor },
        { name: "Next Bench", type: "sensor", nodeId: 12, class: NextBenchSensor },
        { name: "Communicator", type: "actuator", nodeId: 13, class: Actuator },
    ];

    for (const info of nodeInfos) {
        const opcuaVar = ns4.addVariable({
            componentOf: device,
            browseName: info.name,
            dataType: "Boolean",
            value: { dataType: "Boolean", value: info.initialValue || false },
            nodeId: makeNodeId(info.nodeId, 4)
        });

        const instance = new info.class(opcuaVar);

        if (info.type === 'actuator') {
            const camelCaseName = info.name.replace(/\s+/g, '');
            components.actuators[camelCaseName.charAt(0).toLowerCase() + camelCaseName.slice(1)] = instance;
        } else {
            components.sensors.push(instance);
        }
    }

    ns4.addVariable({
        componentOf: device,
        browseName: "Agents in charge",
        displayName: "Agents in charge",
        dataType: "Boolean",
        value: { dataType: "Boolean", value: true }, // Initial state is true
        nodeId: makeNodeId(11, 4)
    });

    console.log("✅ All FESTO variables and components created");

    // --- Start Server and Simulation ---
    await server.start();
    console.log(`🔗 Connect from Node-RED using: ${server.getEndpointUrl()}`);

    startSimulation(components);
    startHttpServer(simulation);

})(); 