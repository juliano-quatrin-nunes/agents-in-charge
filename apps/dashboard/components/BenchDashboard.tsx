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
      <div className="flex flex-col gap-10 justify-center p-10">
        <div>
          <h2 className="text-center w-full pb-2 text-xl font-bold">
            Sensors:
          </h2>
          <div className="grid md:grid-cols-6 sm:grid-cols-3 xs:grid-cols-2 gap-2">
            {data.properties.map(
              (property) =>
                property.objectType == "sensor" && (
                  <Sensor
                    key={property.id}
                    property={property}
                    refetchInterval={refetchInterval}
                  />
                )
            )}
          </div>
        </div>
        <div>
          <h2 className="text-center w-full pb-2 text-xl font-bold">
            Actuators:
          </h2>
          <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-2">
            {data.actions.map((action) => (
              <Actuator
                key={action.id}
                action={action}
                refetchInterval={refetchInterval}
              />
            ))}
          </div>
        </div>
        <div className="flex justify-center">
          <RefetchIntervalSetter
            refetchInterval={refetchInterval}
            setRefetchInterval={setRefetchInterval}
          />
        </div>
      </div>
    );
};
