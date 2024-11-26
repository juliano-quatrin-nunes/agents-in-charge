"use client";

import { ParsedThingDescription, ThingDescription } from "@/lib/types";
import { addIdToComponents, BASE_URL } from "@/lib/utils";
import { useQuery } from "@tanstack/react-query";

export const useTd = () => {
  return useQuery({
    queryKey: ["td"],
    queryFn: () => fetch(`${BASE_URL}/td`).then((res) => res.json()),
    select: (data: ThingDescription): ParsedThingDescription => {
      const properties = addIdToComponents(data.properties);
      const actions = addIdToComponents(data.actions);

      return { ...data, properties, actions };
    },
  });
};
