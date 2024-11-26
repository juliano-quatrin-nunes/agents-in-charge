"use client";

import { getComponentState } from "@/services/componentApiService";
import { useQuery } from "@tanstack/react-query";

export const useFetchProperty = (href: string) => {
  return useQuery({
    queryKey: ["componentState", href],
    queryFn: () => getComponentState(href as string),
  });
};
