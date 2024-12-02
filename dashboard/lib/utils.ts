export const addIdToComponents = <T extends { id: string }>(component: Record<string, T>) => {
  return Object.entries(component).map(([key, value]) => {
    value.id = key;
    return value;
  });
};

export const benchEndpointToUrl = (benchEndpoint: string) => {
  const match = benchEndpoint.match(/[a-z]+:\/\/.*\/(.*)\/td/);

  if (!match) return null;

  const path = match[1];

  return path;
};

export const BASE_URL = "http://localhost:1880";

export const INITIAL_ENDPOINT = "http://localhost:1880/listAllBenches";
