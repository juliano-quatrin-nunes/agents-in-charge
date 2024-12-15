import { RequestBody, ResponseData } from "@/lib/types";
import { DEFAULT_HEADER } from "@/lib/utils";

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
