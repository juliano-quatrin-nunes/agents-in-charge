import { UAVariable } from "node-opcua";

export default class OpcuaNode {
  node: UAVariable;
  browseName: string;

  constructor(node: UAVariable) {
    this.node = node;
    this.browseName = node.browseName?.name?.toString() || "";
  }
}

module.exports = OpcuaNode;
