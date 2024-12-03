"use client";

import { Card } from "@/components/Card";
import useListBenches from "@/hooks/useListBenches";

export default function Home() {
  const { data, isLoading, isSuccess } = useListBenches();

  if (isLoading || !isSuccess) return null;

  return (
    <div className="flex flex-col gap-4 justify-center">
      {Object.entries(data).map(([key, value], id) => (
        <Card benchName={key} url={value as string} key={id} />
      ))}
    </div>
  );
}
