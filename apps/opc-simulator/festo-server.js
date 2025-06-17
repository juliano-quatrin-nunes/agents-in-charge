const { OPCUAServer, Variant, DataType, makeNodeId } = require("node-opcua");

(async () => {
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
    console.log("🔧 FESTO OPC-UA Server initialized");

    const addressSpace = server.engine.addressSpace;
    const namespace = addressSpace.getOwnNamespace();

    // Register additional namespaces to get to namespace 4
    const ns2 = addressSpace.registerNamespace("urn:namespace2");
    const ns3 = addressSpace.registerNamespace("urn:namespace3");  
    const ns4 = addressSpace.registerNamespace("urn:FestoSeparatingStation");

    console.log(`📋 Registered namespaces - ns4 index: ${ns4.index}`);

    // Create a device object to organize variables using namespace 4
    const device = ns4.addObject({
        organizedBy: addressSpace.rootFolder.objects,
        browseName: "FestoSeparatingStation",
        displayName: "FESTO Separating Station"
    });

    console.log("📦 Device object created in namespace 4");

    // Create variables with the exact node IDs your Node-RED flows expect
    const variables = {};

    // Now we can properly use namespace 4 as requested

    variables.discardDiverter = ns4.addVariable({
        componentOf: device,
        browseName: "Discard Diverter",
        displayName: "Discard Diverter",
        dataType: "Boolean",
        value: new Variant({ dataType: DataType.Boolean, value: false }),
        nodeId: makeNodeId(2, 4) // ns=4;i=2
    });

    variables.lock = ns4.addVariable({
        componentOf: device,
        browseName: "Lock",
        displayName: "Lock",
        dataType: "Boolean",
        value: new Variant({ dataType: DataType.Boolean, value: false }),
        nodeId: makeNodeId(3, 4) // ns=4;i=3
    });

    variables.mainConveyor = ns4.addVariable({
        componentOf: device,
        browseName: "Main Conveyor",
        displayName: "Main Conveyor",
        dataType: "Boolean",
        value: new Variant({ dataType: DataType.Boolean, value: false }),
        nodeId: makeNodeId(4, 4) // ns=4;i=4
    });

    variables.discardConveyor = ns4.addVariable({
        componentOf: device,
        browseName: "Discard Conveyor",
        displayName: "Discard Conveyor",
        dataType: "Boolean",
        value: new Variant({ dataType: DataType.Boolean, value: false }),
        nodeId: makeNodeId(5, 4) // ns=4;i=5
    });

    variables.arrivalSensor = ns4.addVariable({
        componentOf: device,
        browseName: "Arrival Sensor",
        displayName: "Arrival Sensor",
        dataType: "Boolean",
        value: new Variant({ dataType: DataType.Boolean, value: false }),
        nodeId: makeNodeId(6, 4) // ns=4;i=6
    });

    variables.stoppedSensor = ns4.addVariable({
        componentOf: device,
        browseName: "Stopped Sensor",
        displayName: "Stopped Sensor",
        dataType: "Boolean",
        value: new Variant({ dataType: DataType.Boolean, value: false }),
        nodeId: makeNodeId(7, 4) // ns=4;i=7
    });

    variables.heightSensor = ns4.addVariable({
        componentOf: device,
        browseName: "Height Sensor",
        displayName: "Height Sensor",
        dataType: "Boolean",
        value: new Variant({ dataType: DataType.Boolean, value: false }),
        nodeId: makeNodeId(8, 4) // ns=4;i=8
    });

    variables.discardSensor = ns4.addVariable({
        componentOf: device,
        browseName: "Discard Sensor",
        displayName: "Discard Sensor",
        dataType: "Boolean",
        value: new Variant({ dataType: DataType.Boolean, value: false }),
        nodeId: makeNodeId(9, 4) // ns=4;i=9
    });

    variables.exitSensor = ns4.addVariable({
        componentOf: device,
        browseName: "Exit Sensor",
        displayName: "Exit Sensor",
        dataType: "Boolean",
        value: new Variant({ dataType: DataType.Boolean, value: false }),
        nodeId: makeNodeId(10, 4) // ns=4;i=10
    });

    variables.agentsInCharge = ns4.addVariable({
        componentOf: device,
        browseName: "Agents in charge",
        displayName: "Agents in charge",
        dataType: "Boolean",
        value: new Variant({ dataType: DataType.Boolean, value: true }),
        nodeId: makeNodeId(11, 4) // ns=4;i=11
    });

    variables.nextBench = ns4.addVariable({
        componentOf: device,
        browseName: "Next Bench",
        displayName: "Next Bench",
        dataType: "Boolean",
        value: new Variant({ dataType: DataType.Boolean, value: false }),
        nodeId: makeNodeId(12, 4) // ns=4;i=12
    });

    variables.communicator = ns4.addVariable({
        componentOf: device,
        browseName: "Communicator",
        displayName: "Communicator",
        dataType: "Boolean",
        value: new Variant({ dataType: DataType.Boolean, value: false }),
        nodeId: makeNodeId(13, 4) // ns=4;i=13
    });

    console.log("✅ All FESTO variables created");

    // Simulation logic - realistic sensor behavior
    setInterval(() => {
        const sensors = [
            variables.arrivalSensor,
            variables.stoppedSensor,
            variables.heightSensor,
            variables.discardSensor,
            variables.exitSensor
        ];
        
        const randomSensor = sensors[Math.floor(Math.random() * sensors.length)];
        const newValue = Math.random() > 0.7; // 30% chance of being active
        
        randomSensor.setValueFromSource(new Variant({
            dataType: DataType.Boolean,
            value: newValue
        }));
        
        console.log(`🔍 ${randomSensor.browseName.name} (${randomSensor.nodeId.toString()}): ${newValue}`);
    }, 3000);

    // Actuator simulation - less frequent changes
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
            
            console.log(`⚙️  ${randomActuator.browseName.name} (${randomActuator.nodeId.toString()}): ${newValue}`);
        }
    }, 5000);

    await server.start();
    
    console.log("🚀 FESTO Separating Station Simulator started!");
    console.log("📋 Available variables:");
    Object.keys(variables).forEach(key => {
        console.log(`  📌 ${variables[key].browseName.name}: ${variables[key].nodeId.toString()}`);
    });
    console.log(`🌐 Server endpoint: ${server.endpoints[0].endpointUrl}`);
    console.log(`🔗 Connect from Node-RED using: opc.tcp://opc-simulator:4840`);
    console.log("⚠️  NOTE: Use ns=1;i=X instead of ns=4;i=X in Node-RED flows");

    // Graceful shutdown
    process.on("SIGINT", async () => {
        console.log("\n🛑 Shutting down FESTO simulator...");
        await server.shutdown();
        process.exit(0);
    });

})().catch(console.error); 