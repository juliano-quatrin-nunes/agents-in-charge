import { DEFAULT_HEADER, INITIAL_ENDPOINT } from "@/lib/utils";
import { useQuery } from "@tanstack/react-query";

const useListBenches = () => {
  console.log(process.env.API_AUTHORIZATION_TOKEN);
  return useQuery({
    queryKey: ["allBenches"],
    queryFn: () =>
      fetch(INITIAL_ENDPOINT, {
        method: "GET",
        headers: DEFAULT_HEADER,
      }).then((res) => res.json()),
  });
};

export default useListBenches;
