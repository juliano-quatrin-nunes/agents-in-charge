import { ComponentProps } from "react";

interface ButtonProps extends ComponentProps<"button"> {
  loading?: boolean;
}

export const Button = ({
  loading = false,
  children,
  ...props
}: ButtonProps) => {
  return (
    <button
      className="shadow-sm border bg-slate-600 hover:bg-slate-500 hover:transition-all text-white font-bold rounded-md p-2"
      {...props}
    >
      {loading ? (
        <div className="h-12 w-12 animate-spin rounded-full border-4 border-solid border-blue-500 border-t-transparent" />
      ) : (
        children
      )}
    </button>
  );
};
