"use client";

import Actuator from "@/components/Actuator";
import Sensor from "@/components/Sensor";
import { useTd } from "@/hooks/useTd";

export default function Home() {
  const { data, isLoading, isSuccess } = useTd();
  if (isLoading) return null;

  if (data && isSuccess)
    return (
      <div className="flex gap-4">
        <div className="flex-col gap-4">
          {data.properties.map((property) => (
            <Sensor key={property.id} property={property} />
          ))}
        </div>
        <div className="flex-col gap-4">
          {data.actions.map((action) => (
            <Actuator key={action.id} action={action} />
          ))}
        </div>  
      </div>
    );
}
