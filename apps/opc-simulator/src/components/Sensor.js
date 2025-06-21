const { Variant, DataType } = require("node-opcua");
const OpcuaNode = require('./OpcuaNode');
const { logEvent } = require('../Logger');

class Sensor extends OpcuaNode {
    constructor(node) {
        super(node);
        this.active = false;
    }

    update(newValue) {
        if (this.active !== newValue) {
            this.active = newValue;
            this.node.setValueFromSource(new Variant({ dataType: DataType.Boolean, value: this.active }));
            logEvent('Sensor', this.browseName, this.active);
        }
    }

    // This method will be implemented by specific sensor classes
    checkState(simulation) {
        throw new Error(`checkState() must be implemented by subclass ${this.browseName}`);
    }
}

module.exports = Sensor; 