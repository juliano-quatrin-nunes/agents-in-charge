import { BASE_URL } from "@/lib/utils";

export const getComponentState = (endpoint: string) => fetch(endpoint).then((res) => res.json());

export const postComponentState = (endpoint: string, data: Record<string, string>) =>
  fetch(endpoint, {
    body: JSON.stringify(data),
    method: "POST",
  });
