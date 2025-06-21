const OpcuaNode = require('./OpcuaNode');
const { logEvent } = require('../Logger');

class Actuator extends OpcuaNode {
    constructor(node) {
        super(node);
        this.lastKnownState = this.node.readValue().value.value;
    }

    isOn() {
        const currentState = this.node.readValue().value.value;
        if (currentState !== this.lastKnownState) {
            logEvent('Actuator', this.browseName, currentState);
            this.lastKnownState = currentState;
        }
        return currentState;
    }
}

module.exports = Actuator; 