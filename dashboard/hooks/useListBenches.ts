import { INITIAL_ENDPOINT } from "@/lib/utils";
import { useQuery } from "@tanstack/react-query";

const useListBenches = () => {
  return useQuery({
    queryKey: ["allBenches"],
    queryFn: () => fetch(INITIAL_ENDPOINT).then((res) => res.json()),
  });
};

export default useListBenches;
