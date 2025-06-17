const { OPCUAServer, Variant, DataType } = require("node-opcua");

(async () => {
    const server = new OPCUAServer({
        port: 4840,
        resourcePath: "/UA/MyLittleServer",
        buildInfo: {
            productName: "FESTO Simulator",
            buildNumber: "1.0",
            buildDate: new Date()
        }
    });

    await server.initialize();
    console.log("OPC-UA Server initialized");

    const addressSpace = server.engine.addressSpace;
    const namespace = addressSpace.getOwnNamespace();

    // Create a simple device object
    const device = namespace.addObject({
        organizedBy: addressSpace.rootFolder.objects,
        browseName: "MyDevice"
    });

    // Add variables
    const variable1 = namespace.addVariable({
        componentOf: device,
        browseName: "Temperature",
        dataType: "Double",
        value: new Variant({ dataType: DataType.Double, value: 22.0 })
    });

    console.log("Variables created");

    // Start simulation
    setInterval(() => {
        const newValue = 20 + Math.random() * 10;
        variable1.setValueFromSource(new Variant({ 
            dataType: DataType.Double, 
            value: newValue 
        }));
        console.log(`Temperature: ${newValue.toFixed(2)}`);
    }, 2000);

    await server.start();
    console.log("🚀 OPC-UA Server is now listening on port 4840");
    console.log("🔗 Connect using: opc.tcp://opc-simulator:4840/UA/MyLittleServer");
})().catch(console.error); 