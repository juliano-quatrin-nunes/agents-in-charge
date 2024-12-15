"use client";

import { useFetchProperty, useMutateAction } from "@/hooks/useFetchComponent";
import { ComponentActions } from "@/lib/types";
import { Button } from "./ui/Button";

interface ActuatorProps {
  action: ComponentActions;
  refetchInterval: number;
}

const Actuator = ({ action, refetchInterval }: ActuatorProps) => {
  const { data, isSuccess } = useFetchProperty(
    action.forms[0].href,
    refetchInterval
  );

  const { mutate, isError } = useMutateAction(action.forms[0].href);

  const styleOn = "bg-yellow-200 border-yellow-500";
  const styleOff = "bg-black border-black";

  const toggleActuatorValue = () => mutate({ value: !data?.value });

  return (
    <div className="border p-4 flex flex-col items-center gap-2 rounded-md shadow-md">
      <h3 className="text-center text-lg font-semibold">{action.title}</h3>
      <div className="flex justify-center gap-3">
        {isSuccess && data ? (
          <>
            <div className="flex flex-col gap-2 justify-center align-start">
              <Button onClick={toggleActuatorValue}>Toggle Value</Button>
              {isError && <div>Não foi possível alterar o valor</div>}
            </div>
            <div
              className={`w-12 h-12  rounded-full border-2 ${
                data.value ? styleOn : styleOff
              }`}
            />
          </>
        ) : (
          <div>Carregando...</div>
        )}
      </div>
    </div>
  );
};

export default Actuator;
