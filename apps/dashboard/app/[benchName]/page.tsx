"use client";

import { AddPiece } from "@/components/AddPiece";
import { BenchDashboard } from "@/components/BenchDashboard";
import { use } from "react";

interface PageProps {
  params: Promise<{ benchName: string }>;
}

const Page = (props: PageProps) => {
  const { benchName } = use(props.params);

  const isDev = process.env.NEXT_PUBLIC_ENVIRONMENT !== "production";
  return (
    <div>
      <BenchDashboard benchEndpoint={benchName} />
      {isDev && <AddPiece />}
    </div>
  );
};

export default Page;
