import { ComponentProps } from "react";

type ButtonProps = ComponentProps<"button">;

export const Button = (props: ButtonProps) => {
  return (
    <button
      className="shadow-sm border bg-slate-600 hover:bg-slate-500 hover:transition-all text-white font-bold rounded-md p-2"
      {...props}
    >
      {props.children}
    </button>
  );
};
