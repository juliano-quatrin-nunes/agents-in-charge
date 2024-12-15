"use client";

import { useFetchProperty } from "@/hooks/useFetchComponent";
import { ComponentProperties } from "@/lib/types";

interface SensorProps {
  property: ComponentProperties;
  refetchInterval: number;
}

const Sensor = ({ property, refetchInterval }: SensorProps) => {
  const { data, isSuccess } = useFetchProperty(
    property.forms[0].href,
    refetchInterval
  );

  const styleOn = "bg-yellow-200 border-yellow-500";
  const styleOff = "bg-black border-black";

  return (
    <div className="border p-4 flex flex-col items-center gap-2 rounded-md shadow-md w-full">
      <h3 className="text-center text-lg font-semibold">{property.title}</h3>
      {isSuccess && data ? (
        <div
          className={`w-12 h-12  rounded-full border-2 ${
            data.value ? styleOn : styleOff
          }`}
        />
      ) : (
        <div>Carregando...</div>
      )}
    </div>
  );
};

export default Sensor;
