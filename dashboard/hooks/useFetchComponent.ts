"use client";

import { RequestBody, ResponseData } from "@/lib/types";
import { getComponentState, postComponentState } from "@/services/componentApiService";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";

export const useFetchProperty = (href: string, refetchInterval: number) => {
  return useQuery<ResponseData>({
    queryKey: ["componentState", href],
    queryFn: () => getComponentState(href as string),
    refetchInterval,
  });
};

export const useMutateAction = (href: string) => {
  const queryClient = useQueryClient();

  return useMutation<ResponseData, Error, RequestBody>({
    mutationFn: (data) => postComponentState(href, data),
    onSuccess: (data) => {
      queryClient.setQueryData(["componentState", href], data);
    },
  });
};
