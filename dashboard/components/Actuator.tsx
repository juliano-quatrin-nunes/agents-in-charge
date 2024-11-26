"use client";

import { useFetchProperty } from "@/hooks/useFetchComponent";
import { ComponentActions } from "@/lib/types";

const Actuator = ({ actuator }: { actuator: ComponentActions }) => {
  const { data } = useFetchProperty(actuator.id);

  console.log(data);

  return (
    <div>
      <h3>{actuator.title}</h3>
      <p>{actuator.description}</p>
      <button>Toggle</button>
    </div>
  );
};

export default Actuator;
