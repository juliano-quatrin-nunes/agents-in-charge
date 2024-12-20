"use client";

import { ParsedThingDescription, ThingDescription } from "@/lib/types";
import { addIdToComponents } from "@/lib/utils";
import { useQuery } from "@tanstack/react-query";

export const useTd = (benchEndpoint: string) => {
  return useQuery({
    queryKey: ["td", benchEndpoint],
    queryFn: () => fetch(`/api/${benchEndpoint}/td`).then((res) => res.json()),
    select: (data: ThingDescription): ParsedThingDescription => {
      const properties = addIdToComponents(data.properties);
      const actions = addIdToComponents(data.actions);

      return { ...data, properties, actions };
    },
  });
};
