"use client";

import { useTd } from "@/hooks/useTd";
import { useState } from "react";
import Actuator from "./Actuator";
import { RefetchIntervalSetter } from "./RefetchIntervalSetter";
import Sensor from "./Sensor";

interface BenchDashboardProps {
  benchEndpoint: string;
}

export const BenchDashboard = ({ benchEndpoint }: BenchDashboardProps) => {
  const { data, isLoading, isSuccess } = useTd(benchEndpoint);

  const [refetchInterval, setRefetchInterval] = useState(1000);

  if (isLoading) return null;

  if (data && isSuccess)
    return (
      <div className="flex flex-col gap-4 justify-center">
        <div className="flex gap-4">
          {data.properties.map(
            (property) =>
              property.objectType == "sensor" && (
                <Sensor key={property.id} property={property} refetchInterval={refetchInterval} />
              )
          )}
        </div>
        <div className="grid grid-cols-2 gap-2">
          {data.actions.map((action) => (
            <Actuator key={action.id} action={action} refetchInterval={refetchInterval} />
          ))}
        </div>
        <div className="flex justify-center">
          <RefetchIntervalSetter refetchInterval={refetchInterval} setRefetchInterval={setRefetchInterval} />
        </div>
      </div>
    );
};
