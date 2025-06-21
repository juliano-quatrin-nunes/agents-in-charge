class OpcuaNode {
    constructor(node) {
        this.node = node;
        this.browseName = node.browseName.name.toString();
    }
}

module.exports = OpcuaNode; 