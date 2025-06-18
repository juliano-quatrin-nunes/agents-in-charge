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
    const _ns1 = addressSpace.getOwnNamespace();
    const _ns2 = addressSpace.registerNamespace("urn:namespace2");
    const _ns3 = addressSpace.registerNamespace("urn:namespace3");  
    const ns4 = addressSpace.registerNamespace("urn:FestoSeparatingStation");

    console.log(`📋 Registered namespaces - ns4 index: ${ns4.index}`);

    const device = ns4.addObject({
        organizedBy: addressSpace.rootFolder.objects,
        browseName: "FestoSeparatingStation",
        displayName: "FESTO Separating Station"
    });

    console.log("📦 Device object created in namespace 4");

    const variables = {};

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

    await server.start();
    
    console.log("🚀 FESTO Separating Station Simulator started!");
    console.log("📋 Available variables:");
    Object.keys(variables).forEach(key => {
        console.log(`  📌 ${variables[key].browseName.name}: ${variables[key].nodeId.toString()}`);
    });
    console.log(`🌐 Server endpoint: ${server.endpoints[0].endpointUrl}`);
    console.log(`🔗 Connect from Node-RED using: opc.tcp://opc-simulator:4840`);

    process.on("SIGINT", async () => {
        console.log("\n🛑 Shutting down FESTO simulator...");
        await server.shutdown();
        process.exit(0);
    });

})().catch(console.error); 