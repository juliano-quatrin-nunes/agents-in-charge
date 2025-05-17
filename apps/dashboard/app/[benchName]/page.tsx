import { BenchDashboard } from "@/components/BenchDashboard";
import { use } from "react";

interface PageProps {
  params: Promise<{ benchName: string }>;
}

const Page = (props: PageProps) => {
  const { benchName } = use(props.params);

  return <BenchDashboard benchEndpoint={benchName} />;
};

export default Page;
