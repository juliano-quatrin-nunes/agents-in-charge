"use client";
import { useQuery } from "@tanstack/react-query";
import { listBenches } from "@/services/componentApiService";

export const useListBenches = () => {
  console.log(process.env.NEXT_PUBLIC_API_AUTHORIZATION_TOKEN);
  return useQuery({
    queryKey: ["listBenches"],
    queryFn: listBenches,
  });
};
