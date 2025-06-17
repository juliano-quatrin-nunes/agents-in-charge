const {
    OPCUAServer,
    Variant,
    DataType,
    makeNodeId
} = require("node-opcua");

async function main() {
    // Create the OPC-UA server
    const server = new OPCUAServer({
        port: 4840,
        resourcePath: "",
        buildInfo: {
            productName: "FESTO Separating Station Simulator",
            buildNumber: "1.0.0",
            buildDate: new Date()
        }
    });

    await server.initialize();

    const addressSpace = server.engine.addressSpace;
    const namespace = addressSpace.getOwnNamespace();

    // Create a device object first, then add variables to it
    const deviceObject = namespace.addObject({
        organizedBy: addressSpace.rootFolder.objects,
        browseName: "SeparatingStation",
        displayName: "FESTO Separating Station",
        nodeId: makeNodeId(1000, 1)
    });

    const variables = {};

    // Add variables with exact node IDs matching Node-RED flows
    variables.discardDiverter = namespace.addVariable({
        componentOf: deviceObject,
        browseName: "Discard Diverter",
        displayName: "Discard Diverter",
        dataType: "Boolean",
        value: new Variant({ dataType: DataType.Boolean, value: false }),
        nodeId: makeNodeId(2, 1)
    });

    variables.lock = namespace.addVariable({
        componentOf: deviceObject,
        browseName: "Lock",
        displayName: "Lock",
        dataType: "Boolean", 
        value: new Variant({ dataType: DataType.Boolean, value: false }),
        nodeId: makeNodeId(3, 1)
    });

    variables.mainConveyor = namespace.addVariable({
        componentOf: deviceObject,
        browseName: "Main Conveyor",
        displayName: "Main Conveyor",
        dataType: "Boolean",
        value: new Variant({ dataType: DataType.Boolean, value: false }),
        nodeId: makeNodeId(4, 1)
    });

    variables.discardConveyor = namespace.addVariable({
        componentOf: deviceObject,
        browseName: "Discard Conveyor", 
        displayName: "Discard Conveyor",
        dataType: "Boolean",
        value: new Variant({ dataType: DataType.Boolean, value: false }),
        nodeId: makeNodeId(5, 1)
    });

    variables.arrivalSensor = namespace.addVariable({
        componentOf: deviceObject,
        browseName: "Arrival Sensor",
        displayName: "Arrival Sensor",
        dataType: "Boolean",
        value: new Variant({ dataType: DataType.Boolean, value: false }),
        nodeId: makeNodeId(6, 1)
    });

    variables.stoppedSensor = namespace.addVariable({
        componentOf: deviceObject,
        browseName: "Stopped Sensor", 
        displayName: "Stopped Sensor",
        dataType: "Boolean",
        value: new Variant({ dataType: DataType.Boolean, value: false }),
        nodeId: makeNodeId(7, 1)
    });

    variables.heightSensor = namespace.addVariable({
        componentOf: deviceObject,
        browseName: "Height Sensor",
        displayName: "Height Sensor", 
        dataType: "Boolean",
        value: new Variant({ dataType: DataType.Boolean, value: false }),
        nodeId: makeNodeId(8, 1)
    });

    variables.discardSensor = namespace.addVariable({
        componentOf: deviceObject,
        browseName: "Discard Sensor",
        displayName: "Discard Sensor",
        dataType: "Boolean",
        value: new Variant({ dataType: DataType.Boolean, value: false }),
        nodeId: makeNodeId(9, 1)
    });

    variables.exitSensor = namespace.addVariable({
        componentOf: deviceObject,
        browseName: "Exit Sensor",
        displayName: "Exit Sensor",
        dataType: "Boolean", 
        value: new Variant({ dataType: DataType.Boolean, value: false }),
        nodeId: makeNodeId(10, 1)
    });

    variables.agentsInCharge = namespace.addVariable({
        componentOf: deviceObject,
        browseName: "Agents in charge",
        displayName: "Agents in charge",
        dataType: "Boolean",
        value: new Variant({ dataType: DataType.Boolean, value: true }),
        nodeId: makeNodeId(11, 1)
    });

    variables.nextBench = namespace.addVariable({
        componentOf: deviceObject,
        browseName: "Next Bench",
        displayName: "Next Bench",
        dataType: "Boolean",
        value: new Variant({ dataType: DataType.Boolean, value: false }),
        nodeId: makeNodeId(12, 1)
    });

    variables.communicator = namespace.addVariable({
        componentOf: deviceObject,
        browseName: "Communicator",
        displayName: "Communicator",
        dataType: "Boolean",
        value: new Variant({ dataType: DataType.Boolean, value: false }),
        nodeId: makeNodeId(13, 1)
    });

    // Add simulation logic - simulate sensor activity
    setInterval(() => {
        const sensors = [
            variables.arrivalSensor, 
            variables.stoppedSensor, 
            variables.heightSensor, 
            variables.discardSensor, 
            variables.exitSensor
        ];
        const randomSensor = sensors[Math.floor(Math.random() * sensors.length)];
        const newValue = Math.random() > 0.7; // 30% chance of being true
        
        randomSensor.setValueFromSource(new Variant({ 
            dataType: DataType.Boolean, 
            value: newValue 
        }));
        
        console.log(`${randomSensor.browseName.name} (${randomSensor.nodeId.toString()}): ${newValue}`);
    }, 2000);

    // Simulate some actuator responses
    setInterval(() => {
        const actuators = [
            variables.mainConveyor, 
            variables.discardConveyor, 
            variables.lock, 
            variables.discardDiverter
        ];
        if (Math.random() > 0.8) { // 20% chance
            const randomActuator = actuators[Math.floor(Math.random() * actuators.length)];
            const newValue = Math.random() > 0.5;
            
            randomActuator.setValueFromSource(new Variant({ 
                dataType: DataType.Boolean, 
                value: newValue 
            }));
            
            console.log(`${randomActuator.browseName.name} (${randomActuator.nodeId.toString()}): ${newValue}`);
        }
    }, 3000);

    // Start the server
    await server.start();
    
    console.log("🚀 OPC-UA Simulation Server started successfully!");
    console.log("📋 Available variables:");
    Object.keys(variables).forEach(key => {
        console.log(`  📌 ${variables[key].browseName.name}: ${variables[key].nodeId.toString()}`);
    });
    console.log(`🌐 Server endpoint: ${server.endpoints[0].endpointUrl}`);
    console.log(`🔗 Connect from Node-RED using: opc.tcp://opc-simulator:4840`);
    
    // Handle graceful shutdown
    process.on("SIGINT", async () => {
        console.log("\n🛑 Shutting down server...");
        await server.shutdown();
        process.exit(0);
    });
}

main().catch(console.error); 