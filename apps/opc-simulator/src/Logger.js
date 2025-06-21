function logEvent(componentType, componentName, state, details = '') {
    const timestamp = new Date().toISOString();
    const stateString = typeof state === 'boolean' ? (state ? 'ON' : 'OFF') : state;
    console.log(`[${timestamp}] [${componentType.padEnd(8)}] ${componentName.padEnd(20)} | State: ${stateString.padEnd(5)} | ${details}`);
}

module.exports = { logEvent }; 