import { RequestBody, ResponseData } from "@/lib/types";
import { DEFAULT_HEADER, INITIAL_ENDPOINT } from "@/lib/utils";

export const listBenches = (): Promise<ResponseData> =>
  fetch(INITIAL_ENDPOINT, {
    method: "GET",
    headers: DEFAULT_HEADER,
  }).then((res) => res.json());

export const getComponentState = (endpoint: string): Promise<ResponseData> =>
  fetch(endpoint, {
    method: "GET",
    headers: DEFAULT_HEADER,
  }).then((res) => res.json());

export const postComponentState = (
  endpoint: string,
  data: RequestBody
): Promise<ResponseData> =>
  fetch(endpoint, {
    body: JSON.stringify(data),
    headers: {
      ...DEFAULT_HEADER,
      "Content-Type": "application/json",
      Accept: "application/json",
    },
    method: "POST",
  }).then((res) => res.json());
