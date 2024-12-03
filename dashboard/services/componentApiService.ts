import { RequestBody, ResponseData } from "@/lib/types";

export const getComponentState = (endpoint: string): Promise<ResponseData> => fetch(endpoint).then((res) => res.json());

export const postComponentState = (endpoint: string, data: RequestBody): Promise<ResponseData> =>
  fetch(endpoint, {
    body: JSON.stringify(data),
    method: "POST",
  }).then((res) => res.json());
