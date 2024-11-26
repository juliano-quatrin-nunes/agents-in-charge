"use client";

import { useFetchProperty, useMutateAction } from "@/hooks/useFetchComponent";
import { ComponentActions } from "@/lib/types";

const Actuator = ({ action }: { action: ComponentActions }) => {
  const { data } = useFetchProperty(action.forms[0].href);

  const { mutate } = useMutateAction(action.forms[0].href);

  const styleOn = "bg-yellow-200 border-yellow-500";
  const styleOff = "bg-black border-black";

  return (
    data && (
      <div className="border p-4 flex flex-col items-center gap-2 rounded-md shadow-md">
        <h3 className="text-center text-lg font-semibold">{action.title}</h3>
        <div className="flex justify-center gap-3">
          <button
            className="w-20 h-10 rounded-lg bg-red-200 border-2 border-red-500"
            onClick={() => mutate({ value: !data.value })}
          />
          <div className={`w-12 h-12  rounded-full border-2 ${data.value ? styleOn : styleOff}`} />
        </div>
      </div>
    )
  );
};

export default Actuator;
