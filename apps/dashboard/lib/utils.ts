import { clsx, type ClassValue } from "clsx";
import { twMerge } from "tailwind-merge";

export const addIdToComponents = <T extends { id: string }>(
  component: Record<string, T>
) => {
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

export const INITIAL_ENDPOINT = "/api/listAllBenches";

export const DEFAULT_HEADER = {
  Authorization: `${process.env.API_AUTHORIZATION_TOKEN}`,
  "ngrok-skip-browser-warning": "69420",
};

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}
