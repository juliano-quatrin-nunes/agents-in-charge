"use client";

import { useFetchProperty, useMutateAction } from "@/hooks/useFetchComponent";
import { ComponentActions } from "@/lib/types";
import { Button } from "./ui/Button";

interface ActuatorProps {
  action: ComponentActions;
  refetchInterval: number;
}

const Actuator = ({ action, refetchInterval }: ActuatorProps) => {
  const { data } = useFetchProperty(action.forms[0].href, refetchInterval);

  const { mutate } = useMutateAction(action.forms[0].href);

  const styleOn = "bg-yellow-200 border-yellow-500";
  const styleOff = "bg-black border-black";

  const toggleActuatorValue = () => mutate({ value: !data?.value });

  return (
    data && (
      <div className="border p-4 flex flex-col items-center gap-2 rounded-md shadow-md">
        <h3 className="text-center text-lg font-semibold">{action.title}</h3>
        <div className="flex justify-center gap-3">
          <Button onClick={toggleActuatorValue}>Toggle Value</Button>
          <div className={`w-12 h-12  rounded-full border-2 ${data.value ? styleOn : styleOff}`} />
        </div>
      </div>
    )
  );
};

export default Actuator;
