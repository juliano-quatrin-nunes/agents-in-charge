"use client";

import Actuator from "@/components/Actuator";
import Sensor from "@/components/Sensor";
import { useTd } from "@/hooks/useTd";
import { ComponentActions } from "@/lib/types";

export default function Home() {
  const { data, isLoading, isSuccess } = useTd();
  if (isLoading) return null;

  if (data && isSuccess)
    return (
      <div className="flex-1 gap-4">
        {data.properties.map((property) => (
          <Sensor key={property.id} property={property} />
        ))}
      </div>
    );
}
