"use client";

import { useFetchProperty } from "@/hooks/useFetchComponent";
import { ComponentProperties } from "@/lib/types";

const Sensor = ({ property }: { property: ComponentProperties }) => {
  const { data } = useFetchProperty(property.forms[0].href);

  const styleOn = "bg-yellow-200 border-yellow-500";
  const styleOff = "bg-black border-black";

  return (
    data && (
      <div className="border p-4 flex flex-col items-center gap-2 rounded-md shadow-md">
        <h3 className="text-center text-lg font-semibold">{property.title}</h3>
        <div className={`w-12 h-12  rounded-full border-2 ${data.value ? styleOn : styleOff}`} />
      </div>
    )
  );
};

export default Sensor;
