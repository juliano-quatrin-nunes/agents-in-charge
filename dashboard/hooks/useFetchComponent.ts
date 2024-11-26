"use client";

import { getComponentState } from "@/services/componentApiService";
import { useMutation, useQuery } from "@tanstack/react-query";

export const useFetchProperty = (href: string) => {
  return useQuery({
    queryKey: ["componentState", href],
    queryFn: () => getComponentState(href as string),
    refetchInterval: 100,
  });
};

export const useMutateAction = (href: string) => {
  return useMutation({
    mutationFn: (data: { value: boolean }) => fetch(href, { body: JSON.stringify(data), method: "POST" }),
  });
};
