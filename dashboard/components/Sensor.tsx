"use client";

import { useFetchProperty } from "@/hooks/useFetchComponent";
import { ComponentProperties } from "@/lib/types";

const Sensor = ({ property }: { property: ComponentProperties }) => {
  const { data } = useFetchProperty(property.forms[0].href);

  return (
    data && (
      <div>
        <h3>{property.title}</h3>
        <p>{property.description}</p>
        <p>{String(data.value)}</p>
        <button>Toggle</button>
      </div>
    )
  );
};

export default Sensor;
