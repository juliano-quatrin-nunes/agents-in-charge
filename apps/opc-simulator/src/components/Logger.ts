export function logEvent(
  componentType: string,
  componentName: string,
  state: boolean | number | string,
  details = ""
) {
  const timestamp = new Date().toISOString();
  const stateString =
    typeof state === "boolean" ? (state ? "ON" : "OFF") : state;
  console.log(
    `[${timestamp}] [${componentType.padEnd(8)}] ${componentName.padEnd(
      30
    )} | State: ${stateString.toString().padEnd(10)} | ${details}`
  );
}
