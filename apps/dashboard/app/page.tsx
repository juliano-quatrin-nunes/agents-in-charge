"use client";

import { Card } from "@/components/Card";
import { useListBenches } from "@/hooks/useListBenches";

export default function Home() {
  const { data, isLoading, isSuccess } = useListBenches();

  if (isLoading || !isSuccess) return null;

  return (
    <div className="flex flex-col py-20 w-full h-screen text-center">
      <h1 className="text-2xl font-bold">Agents in Charge Dashboard</h1>
      <div className="flex flex-col gap-4 justify-center w-full h-full items-center text-center">
        <h2 className="text-lg font-semibold">Bancadas</h2>
        {Object.entries(data).map(([key, value], id) => (
          <Card benchName={key} url={value as string} key={id} />
        ))}
      </div>
    </div>
  );
}
