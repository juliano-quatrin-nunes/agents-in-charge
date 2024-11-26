export const addIdToComponents = <T extends { id: string }>(component: Record<string, T>) => {
  return Object.entries(component).map(([key, value]) => {
    value.id = key;
    return value;
  });
};

export const BASE_URL = "http://localhost:1880";
