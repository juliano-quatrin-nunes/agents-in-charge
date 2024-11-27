"use client";

import Actuator from "@/components/Actuator";
import { RefetchIntervalSetter } from "@/components/RefetchIntervalSetter";
import Sensor from "@/components/Sensor";
import { useTd } from "@/hooks/useTd";
import { useState } from "react";

export default function Home() {
  const { data, isLoading, isSuccess } = useTd();

  const [refetchInterval, setRefetchInterval] = useState(1000);

  if (isLoading) return null;

  if (data && isSuccess)
    return (
      <div className="flex gap-4">
        <div className="flex-col gap-4">
          {data.properties.map(
            (property) =>
              property.objectType == "sensor" && (
                <Sensor key={property.id} property={property} refetchInterval={refetchInterval} />
              )
          )}
        </div>
        <div className="flex-col gap-4">
          {data.actions.map((action) => (
            <Actuator key={action.id} action={action} />
          ))}
        </div>
        <RefetchIntervalSetter refetchInterval={refetchInterval} setRefetchInterval={setRefetchInterval} />
      </div>
    );
}
